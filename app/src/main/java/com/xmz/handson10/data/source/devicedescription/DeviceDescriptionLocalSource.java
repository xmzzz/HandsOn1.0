package com.xmz.handson10.data.source.devicedescription;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xmz.handson10.data.DeviceAvailable;
import com.xmz.handson10.data.DeviceDescription;
import com.xmz.handson10.data.source.DbHelper;
import com.xmz.handson10.data.source.DeviceDescriptionSource;
import com.xmz.handson10.data.source.devicedescription.DeviceDescriptionPersistenceContract.DeviceDescriptionEntry;
import com.xmz.handson10.data.source.devicedescription.DeviceDescriptionPersistenceContract.DeviceFunctionEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xmz on 2016/5/28.
 */
public class DeviceDescriptionLocalSource implements DeviceDescriptionSource {

    private static DeviceDescriptionLocalSource INSTANCE;

    private DbHelper mDbHelper;

    private DeviceDescriptionLocalSource(Context context, String databaseIndex) {
        mDbHelper = new DbHelper(context, databaseIndex);
    }

    public static DeviceDescriptionLocalSource getInstance(Context context, String databaseIndex) {
        if (INSTANCE == null) {
            INSTANCE = new DeviceDescriptionLocalSource(context, databaseIndex);
        }
        return INSTANCE;
    }

    @Override
    public void getDeviceDescriptions(LoadDeviceDescriptionsCallback callback) {
        List<DeviceDescription> deviceDescriptions = new ArrayList<DeviceDescription>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                DeviceDescriptionEntry.COLUMN_NAME_TYPE_ID,
                DeviceDescriptionEntry.COLUMN_NAME_TYPE_NAME,
                DeviceDescriptionEntry.COLUMN_NAME_DEVICE_NAME,
                DeviceDescriptionEntry.COLUMN_NAME_FUNCTION_COUNT,
                DeviceDescriptionEntry.COLUMN_NAME_PIC_ID,
                DeviceDescriptionEntry.COLUMN_NAME_FEATURE_ID
        };

        String[] projectionFunc = {
                DeviceFunctionEntry.COLUMN_NAME_TYPE_ID,
                DeviceFunctionEntry.COLUMN_NAME_FUNCTION_ID,
                DeviceFunctionEntry.COLUMN_NAME_FUNCTION_NAME
        };

        Cursor c = db.query(
                DeviceDescriptionEntry.TABLE_NAME, projection, null, null, null, null, null);

        if (c != null && c.getCount()>0) {
            while (c.moveToNext()) {
                String typeId = c.getString(c.getColumnIndexOrThrow(DeviceDescriptionEntry.COLUMN_NAME_TYPE_ID));
                String typeName = c.getString(c.getColumnIndexOrThrow(DeviceDescriptionEntry.COLUMN_NAME_TYPE_NAME));
                String deviceName = c.getString(c.getColumnIndexOrThrow(DeviceDescriptionEntry.COLUMN_NAME_DEVICE_NAME));
                int funcCount = c.getInt(c.getColumnIndexOrThrow(DeviceDescriptionEntry.COLUMN_NAME_FUNCTION_COUNT));
                int picId = c.getInt(c.getColumnIndexOrThrow(DeviceDescriptionEntry.COLUMN_NAME_PIC_ID));
                String featureId = c.getString(c.getColumnIndexOrThrow(DeviceDescriptionEntry.COLUMN_NAME_FEATURE_ID));

                String selection = DeviceFunctionEntry.COLUMN_NAME_TYPE_ID + " LIKE ?";
                String[] selectionArgs = { typeId };
                String[] funcName = new String[funcCount];

                Cursor c_func = db.query(
                        DeviceFunctionEntry.TABLE_NAME, projectionFunc, selection, selectionArgs, null, null, null);
                if (c_func != null && c_func.getCount()>0) {
                    int i=0;
                    while (c.moveToNext()) {
                        funcName[i] = c_func.getString(c_func.getColumnIndexOrThrow(DeviceFunctionEntry.COLUMN_NAME_FUNCTION_NAME));
                        i++;
                    }
                }
                DeviceDescription deviceDescription = new DeviceDescription(typeId, typeName, deviceName, funcCount, funcName, picId, featureId);
                deviceDescriptions.add(deviceDescription);
            }
        }

        if (c != null) {
            c.close();
        }
        db.close();
        if (deviceDescriptions.isEmpty()) {
            callback.onDataNotAvailable();
        } else {
            callback.onDeviceDescriptionsLoaded(deviceDescriptions);
        }
    }

    @Override
    public void getDeviceDescription(String typeId, GetDeviceDescriptionCallback callback) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String[] projection = {
                DeviceDescriptionEntry.COLUMN_NAME_TYPE_ID,
                DeviceDescriptionEntry.COLUMN_NAME_TYPE_NAME,
                DeviceDescriptionEntry.COLUMN_NAME_DEVICE_NAME,
                DeviceDescriptionEntry.COLUMN_NAME_FUNCTION_COUNT,
                DeviceDescriptionEntry.COLUMN_NAME_PIC_ID,
                DeviceDescriptionEntry.COLUMN_NAME_FEATURE_ID
        };

        String[] projectionFunc = {
                DeviceFunctionEntry.COLUMN_NAME_TYPE_ID,
                DeviceFunctionEntry.COLUMN_NAME_FUNCTION_ID,
                DeviceFunctionEntry.COLUMN_NAME_FUNCTION_NAME
        };

        String selection = DeviceDescriptionEntry.COLUMN_NAME_TYPE_ID + " LIKE ?";
        String[] selectionArgs = { typeId };

        Cursor c = db.query(DeviceDescriptionEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);
        DeviceDescription deviceDescription = null;

        if (c != null && c.getCount() > 0) {
            c.moveToLast();
            String mTypeId = c.getString(c.getColumnIndexOrThrow(DeviceDescriptionEntry.COLUMN_NAME_TYPE_ID));
            String typeName = c.getString(c.getColumnIndexOrThrow(DeviceDescriptionEntry.COLUMN_NAME_TYPE_NAME));
            String deviceName = c.getString(c.getColumnIndexOrThrow(DeviceDescriptionEntry.COLUMN_NAME_DEVICE_NAME));
            int funcCount = c.getInt(c.getColumnIndexOrThrow(DeviceDescriptionEntry.COLUMN_NAME_FUNCTION_COUNT));
            int picId = c.getInt(c.getColumnIndexOrThrow(DeviceDescriptionEntry.COLUMN_NAME_PIC_ID));
            String featureId = c.getString(c.getColumnIndexOrThrow(DeviceDescriptionEntry.COLUMN_NAME_FEATURE_ID));

            String selectionFunc = DeviceFunctionEntry.COLUMN_NAME_TYPE_ID + " LIKE ?";
            String[] selectionArgsFunc = { typeId };
            String[] funcName = new String[funcCount];

            Cursor c_func = db.query(
                    DeviceFunctionEntry.TABLE_NAME, projectionFunc, selectionFunc, selectionArgsFunc, null, null, null);
            if (c_func != null && c_func.getCount()>0) {
                int i=0;
                while (c.moveToNext()) {
                    funcName[i] = c_func.getString(c_func.getColumnIndexOrThrow(DeviceFunctionEntry.COLUMN_NAME_FUNCTION_NAME));
                    i++;
                }
            }
            deviceDescription = new DeviceDescription(mTypeId, typeName, deviceName, funcCount, funcName, picId, featureId);
        }
        if (c != null) {
            c.close();
        }

        db.close();

        if (deviceDescription != null) {
            callback.onDeviceDescriptionLoaded(deviceDescription);
        } else {
            callback.onDataNotAvailable();
        }
    }

    @Override
    public void saveDeviceDescription(DeviceDescription deviceDescription) {

    }

    @Override
    public void deleteAllDeviceDescription() {

    }

    @Override
    public void deleteDeviceDescription(String typeId) {

    }

    @Override
    public void getAvailableDevices(LoadAvailableDevicesCallback callback) {

    }

    @Override
    public void getAvailableDevice(String deviceId, GetAvailableDeviceCallback callback) {

    }

    @Override
    public void saveDeviceAvailable(DeviceAvailable deviceAvailable) {

    }

    @Override
    public void deleteAllAvailableDevice() {

    }

    @Override
    public void deleteAvailableDevice(String deviceId) {

    }
}
