package com.xmz.handson10.data.source.devicedescription;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xmz.handson10.R;
import com.xmz.handson10.data.DeviceAvailable;
import com.xmz.handson10.data.DeviceDescription;
import com.xmz.handson10.data.EventDevice;
import com.xmz.handson10.data.source.DbHelper;
import com.xmz.handson10.data.source.DeviceDescriptionSource;
import com.xmz.handson10.data.source.devicedescription.DeviceDescriptionPersistenceContract.AvailableEventDeviceEntry;
import com.xmz.handson10.data.source.devicedescription.DeviceDescriptionPersistenceContract.DeviceAvailableEntry;
import com.xmz.handson10.data.source.devicedescription.DeviceDescriptionPersistenceContract.DeviceDescriptionEntry;
import com.xmz.handson10.data.source.devicedescription.DeviceDescriptionPersistenceContract.DeviceFunctionEntry;
import com.xmz.handson10.data.source.devicedescription.DeviceDescriptionPersistenceContract.EventDevicePictureEntry;

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
                DeviceFunctionEntry.COLUMN_NAME_TYPE_NAME,
                DeviceFunctionEntry.COLUMN_NAME_FUNCTION_ID,
                DeviceFunctionEntry.COLUMN_NAME_FUNCTION_NAME
        };

        Cursor c = db.query(
                DeviceDescriptionEntry.TABLE_NAME, projection, null, null, null, null, null);

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                int typeId = c.getInt(c.getColumnIndexOrThrow(DeviceDescriptionEntry.COLUMN_NAME_TYPE_ID));
                String typeName = c.getString(c.getColumnIndexOrThrow(DeviceDescriptionEntry.COLUMN_NAME_TYPE_NAME));
                String deviceName = c.getString(c.getColumnIndexOrThrow(DeviceDescriptionEntry.COLUMN_NAME_DEVICE_NAME));
                int funcCount = c.getInt(c.getColumnIndexOrThrow(DeviceDescriptionEntry.COLUMN_NAME_FUNCTION_COUNT));
                int picId = c.getInt(c.getColumnIndexOrThrow(DeviceDescriptionEntry.COLUMN_NAME_PIC_ID));
                String featureId = c.getString(c.getColumnIndexOrThrow(DeviceDescriptionEntry.COLUMN_NAME_FEATURE_ID));

                String selection = DeviceFunctionEntry.COLUMN_NAME_TYPE_NAME + " LIKE ?";
                String[] selectionArgs = {typeName};
                String[] funcName = new String[funcCount];

                Cursor c_func = db.query(
                        DeviceFunctionEntry.TABLE_NAME, projectionFunc, selection, selectionArgs, null, null, null);
                if (c_func != null && c_func.getCount() > 0) {
                    int i = 0;
                    while (c_func.moveToNext()) {
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
    public void getDeviceDescription(int typeId, GetDeviceDescriptionCallback callback) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String selectionArg = String.valueOf(typeId);
        String[] projection = {
                DeviceDescriptionEntry.COLUMN_NAME_TYPE_ID,
                DeviceDescriptionEntry.COLUMN_NAME_TYPE_NAME,
                DeviceDescriptionEntry.COLUMN_NAME_DEVICE_NAME,
                DeviceDescriptionEntry.COLUMN_NAME_FUNCTION_COUNT,
                DeviceDescriptionEntry.COLUMN_NAME_PIC_ID,
                DeviceDescriptionEntry.COLUMN_NAME_FEATURE_ID
        };

        String[] projectionFunc = {
                DeviceFunctionEntry.COLUMN_NAME_TYPE_NAME,
                DeviceFunctionEntry.COLUMN_NAME_FUNCTION_ID,
                DeviceFunctionEntry.COLUMN_NAME_FUNCTION_NAME
        };

        String selection = DeviceDescriptionEntry.COLUMN_NAME_TYPE_ID + " LIKE ?";
        String[] selectionArgs = {selectionArg};

        Cursor c = db.query(DeviceDescriptionEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);
        DeviceDescription deviceDescription = null;

        if (c != null && c.getCount() > 0) {
            c.moveToLast();
            int mTypeId = c.getInt(c.getColumnIndexOrThrow(DeviceDescriptionEntry.COLUMN_NAME_TYPE_ID));
            String typeName = c.getString(c.getColumnIndexOrThrow(DeviceDescriptionEntry.COLUMN_NAME_TYPE_NAME));
            String deviceName = c.getString(c.getColumnIndexOrThrow(DeviceDescriptionEntry.COLUMN_NAME_DEVICE_NAME));
            int funcCount = c.getInt(c.getColumnIndexOrThrow(DeviceDescriptionEntry.COLUMN_NAME_FUNCTION_COUNT));
            int picId = c.getInt(c.getColumnIndexOrThrow(DeviceDescriptionEntry.COLUMN_NAME_PIC_ID));
            String featureId = c.getString(c.getColumnIndexOrThrow(DeviceDescriptionEntry.COLUMN_NAME_FEATURE_ID));

            String selectionFunc = DeviceFunctionEntry.COLUMN_NAME_TYPE_NAME + " LIKE ?";
            String[] selectionArgsFunc = {typeName};
            String[] funcName = new String[funcCount];

            Cursor c_func = db.query(
                    DeviceFunctionEntry.TABLE_NAME, projectionFunc, selectionFunc, selectionArgsFunc, null, null, null);
            if (c_func != null && c_func.getCount() > 0) {
                int i = 0;
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
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DeviceDescriptionEntry.COLUMN_NAME_TYPE_NAME, deviceDescription.getTypeName());
        values.put(DeviceDescriptionEntry.COLUMN_NAME_DEVICE_NAME, deviceDescription.getDeviceName());
        values.put(DeviceDescriptionEntry.COLUMN_NAME_FUNCTION_COUNT, deviceDescription.getFuncCount());
        values.put(DeviceDescriptionEntry.COLUMN_NAME_PIC_ID, deviceDescription.getDevicePicSrcId());
        values.put(DeviceDescriptionEntry.COLUMN_NAME_FEATURE_ID, deviceDescription.getTypeFeatureId());

        db.insert(DeviceDescriptionEntry.TABLE_NAME, null, values);

        ContentValues values_func = new ContentValues();
        int funcCount = deviceDescription.getFuncCount();
        for (int i = 0; i < funcCount; i++) {
            values_func.put(DeviceFunctionEntry.COLUMN_NAME_TYPE_NAME, deviceDescription.getTypeName());
            values_func.put(DeviceFunctionEntry.COLUMN_NAME_FUNCTION_ID, i);
            values_func.put(DeviceFunctionEntry.COLUMN_NAME_FUNCTION_NAME, deviceDescription.getFuncName()[i]);
        }
        db.insert(DeviceFunctionEntry.TABLE_NAME, null, values_func);
        db.close();
    }

    @Override
    public void deleteAllDeviceDescription() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.delete(DeviceDescriptionEntry.TABLE_NAME, null, null);
        db.delete(DeviceFunctionEntry.TABLE_NAME, null, null);
        db.close();
    }

    @Override
    public void deleteDeviceDescription(String typeId) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        String selection = DeviceDescriptionEntry.COLUMN_NAME_TYPE_ID + " LIKE ?";
        String[] selectionArgs = {typeId};
        db.delete(DeviceDescriptionEntry.TABLE_NAME, selection, selectionArgs);
        db.delete(DeviceFunctionEntry.TABLE_NAME, selection, selectionArgs);
    }

    @Override
    public void getAvailableDevices(LoadAvailableDevicesCallback callback) {
        List<DeviceAvailable> deviceAvailables = new ArrayList<DeviceAvailable>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                DeviceAvailableEntry.COLUMN_NAME_DEVICE_ID,
                DeviceAvailableEntry.COLUMN_NAME_TYPE_ID,
                DeviceAvailableEntry.COLUMN_NAME_TYPE_FEATURE_ID
        };

        Cursor c = db.query(
                DeviceAvailableEntry.TABLE_NAME, projection, null, null, null, null, null);

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                int deviceId = c.getInt(c.getColumnIndexOrThrow(DeviceAvailableEntry.COLUMN_NAME_DEVICE_ID));
                int typeId = c.getInt(c.getColumnIndexOrThrow(DeviceAvailableEntry.COLUMN_NAME_TYPE_ID));
                String featureId = c.getString(c.getColumnIndexOrThrow(DeviceAvailableEntry.COLUMN_NAME_TYPE_FEATURE_ID));
                DeviceAvailable deviceAvailable = new DeviceAvailable(deviceId, typeId, featureId);
                deviceAvailables.add(deviceAvailable);
            }
        }

        if (c != null) {
            c.close();
        }
        db.close();
        if (deviceAvailables.isEmpty()) {
            callback.onDataNotAvailable();
        } else {
            callback.onAvailableDevicesLoaded(deviceAvailables);
        }
    }

    @Override
    public void getAvailableDevice(int deviceId, GetAvailableDeviceCallback callback) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String selectionArg = String.valueOf(deviceId);

        String[] projection = {
                DeviceAvailableEntry.COLUMN_NAME_DEVICE_ID,
                DeviceAvailableEntry.COLUMN_NAME_TYPE_ID,
                DeviceAvailableEntry.COLUMN_NAME_TYPE_FEATURE_ID
        };

        String selection = DeviceAvailableEntry.COLUMN_NAME_DEVICE_ID + " LIKE ?";
        String[] selectionArgs = {selectionArg};

        Cursor c = db.query(DeviceAvailableEntry.TABLE_NAME, projection, selection, selectionArgs,
                null, null, null);
        DeviceAvailable deviceAvailable = null;

        if (c != null && c.getCount() > 0) {
            c.moveToLast();
            int mDeviceId = c.getInt(c.getColumnIndexOrThrow(DeviceAvailableEntry.COLUMN_NAME_DEVICE_ID));
            int typeId = c.getInt(c.getColumnIndexOrThrow(DeviceAvailableEntry.COLUMN_NAME_TYPE_ID));
            String featureId = c.getString(c.getColumnIndexOrThrow(DeviceAvailableEntry.COLUMN_NAME_TYPE_FEATURE_ID));
            deviceAvailable = new DeviceAvailable(mDeviceId, typeId, featureId);
        }
        if (c != null) {
            c.close();
        }
        db.close();
        if (deviceAvailable != null) {
            callback.onAvailableDeviceLoaded(deviceAvailable);
        } else {
            callback.onDataNotAvailable();
        }
    }

    private int getLastAvailableDeviceId() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String[] projection = {
                DeviceAvailableEntry.COLUMN_NAME_DEVICE_ID,
                DeviceAvailableEntry.COLUMN_NAME_TYPE_ID,
                DeviceAvailableEntry.COLUMN_NAME_TYPE_FEATURE_ID
        };

        Cursor c = db.query(DeviceAvailableEntry.TABLE_NAME, projection, null, null, null, null, null);
        c.moveToLast();
        return c.getInt(c.getColumnIndexOrThrow(DeviceAvailableEntry.COLUMN_NAME_DEVICE_ID));
    }

    @Override
    public int saveDeviceAvailable(DeviceAvailable deviceAvailable) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int deviceId;

        ContentValues values = new ContentValues();
        values.put(DeviceAvailableEntry.COLUMN_NAME_TYPE_ID, deviceAvailable.getDeviceTypeId());
        values.put(DeviceAvailableEntry.COLUMN_NAME_TYPE_FEATURE_ID, deviceAvailable.getTypeFeatureId());

        db.insert(DeviceAvailableEntry.TABLE_NAME, null, values);
        db.close();

        deviceId = getLastAvailableDeviceId();
        return deviceId;
    }

    @Override
    public void deleteAllAvailableDevice() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.delete(DeviceAvailableEntry.TABLE_NAME, null, null);
        db.close();
    }

    @Override
    public void deleteAvailableDevice(int deviceId) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        String selectionArg = String.valueOf(deviceId);

        String selection = DeviceAvailableEntry.COLUMN_NAME_DEVICE_ID + " LIKE ?";
        String[] selectionArgs = {selectionArg};
        db.delete(DeviceAvailableEntry.TABLE_NAME, selection, selectionArgs);
    }


    @Override
    public void getAvailableButton(int deviceId, GetAvailableDeviceCallback callback) {

    }

    @Override
    public void getEventDevices(LoadDeviceDescriptionsCallback callback) {
        List<DeviceDescription> deviceDescriptions = new ArrayList<DeviceDescription>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String selection = DeviceDescriptionEntry.COLUMN_NAME_FEATURE_ID + " LIKE ?";
        String[] selectionArgs = {"EVENT"};

        String[] projection = {
                DeviceDescriptionEntry.COLUMN_NAME_TYPE_ID,
                DeviceDescriptionEntry.COLUMN_NAME_TYPE_NAME,
                DeviceDescriptionEntry.COLUMN_NAME_DEVICE_NAME,
                DeviceDescriptionEntry.COLUMN_NAME_FUNCTION_COUNT,
                DeviceDescriptionEntry.COLUMN_NAME_PIC_ID,
                DeviceDescriptionEntry.COLUMN_NAME_FEATURE_ID
        };

        String[] projectionFunc = {
                DeviceFunctionEntry.COLUMN_NAME_TYPE_NAME,
                DeviceFunctionEntry.COLUMN_NAME_FUNCTION_ID,
                DeviceFunctionEntry.COLUMN_NAME_FUNCTION_NAME
        };

        Cursor c = db.query(
                DeviceDescriptionEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                int typeId = c.getInt(c.getColumnIndexOrThrow(DeviceDescriptionEntry.COLUMN_NAME_TYPE_ID));
                String typeName = c.getString(c.getColumnIndexOrThrow(DeviceDescriptionEntry.COLUMN_NAME_TYPE_NAME));
                String deviceName = c.getString(c.getColumnIndexOrThrow(DeviceDescriptionEntry.COLUMN_NAME_DEVICE_NAME));
                int funcCount = c.getInt(c.getColumnIndexOrThrow(DeviceDescriptionEntry.COLUMN_NAME_FUNCTION_COUNT));
                int picId = c.getInt(c.getColumnIndexOrThrow(DeviceDescriptionEntry.COLUMN_NAME_PIC_ID));
                String featureId = c.getString(c.getColumnIndexOrThrow(DeviceDescriptionEntry.COLUMN_NAME_FEATURE_ID));

                String func_selection = DeviceFunctionEntry.COLUMN_NAME_TYPE_NAME + " LIKE ?";
                String[] func_selectionArgs = {typeName};
                String[] funcName = new String[funcCount];

                Cursor c_func = db.query(
                        DeviceFunctionEntry.TABLE_NAME, projectionFunc, func_selection, func_selectionArgs, null, null, null);
                if (c_func != null && c_func.getCount() > 0) {
                    int i = 0;
                    while (c_func.moveToNext()) {
                        funcName[i] = c_func.getString(c_func.getColumnIndexOrThrow(DeviceFunctionEntry.COLUMN_NAME_FUNCTION_NAME));
                        i++;
                    }
                }
                DeviceDescription deviceDescription = new DeviceDescription(typeId, typeName, deviceName, funcCount, funcName, picId, featureId);
                deviceDescriptions.add(deviceDescription);

                if (c_func != null) {
                    c_func.close();
                }
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
    public void updateAvailableEventDevice(EventDevice eventDevice) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        String selectionArg = String.valueOf(eventDevice.getDeviceId());

        ContentValues contentValues = new ContentValues();
        String selection = AvailableEventDeviceEntry.COLUMN_NAME_DEVICE_ID + " LIKE ?";
        String[] selectionArgs = { selectionArg };
        contentValues.put(AvailableEventDeviceEntry.COLUMN_NAME_DEVICE_ID, eventDevice.getDeviceId());
        contentValues.put(AvailableEventDeviceEntry.COLUMN_NAME_TYPE_NAME, eventDevice.getTypeName());
        contentValues.put(AvailableEventDeviceEntry.COLUMN_NAME_DEVICE_NAME, eventDevice.getDeviceName());
        contentValues.put(AvailableEventDeviceEntry.COLUMN_NAME_PIC_ID, eventDevice.getDevicePicSrcId());
        contentValues.put(AvailableEventDeviceEntry.COLUMN_NAME_FUNCTION_COUNT, eventDevice.getFuncCount());
        contentValues.put(AvailableEventDeviceEntry.COLUMN_NAME_X, eventDevice.getCoordinate_x());
        contentValues.put(AvailableEventDeviceEntry.COLUMN_NAME_Y, eventDevice.getCoordinate_y());

        db.update(AvailableEventDeviceEntry.TABLE_NAME, contentValues, selection, selectionArgs);
        db.close();
    }

    @Override
    public void removeAvailableEventDevice(int deviceId) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        String selection = AvailableEventDeviceEntry.COLUMN_NAME_DEVICE_ID + " LIKE ?";

        String[] selectionArgs = {deviceId + ""};
        db.delete(AvailableEventDeviceEntry.TABLE_NAME, selection, selectionArgs);
    }

    @Override
    public int saveAvailableEventDevice(EventDevice eventDevice) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(AvailableEventDeviceEntry.COLUMN_NAME_TYPE_NAME, eventDevice.getTypeName());
        contentValues.put(AvailableEventDeviceEntry.COLUMN_NAME_DEVICE_NAME, eventDevice.getDeviceName());
        contentValues.put(AvailableEventDeviceEntry.COLUMN_NAME_PIC_ID, eventDevice.getDevicePicSrcId());
        contentValues.put(AvailableEventDeviceEntry.COLUMN_NAME_FUNCTION_COUNT, eventDevice.getFuncCount());
        contentValues.put(AvailableEventDeviceEntry.COLUMN_NAME_X, eventDevice.getCoordinate_x());
        contentValues.put(AvailableEventDeviceEntry.COLUMN_NAME_Y, eventDevice.getCoordinate_y());

        db.insert(AvailableEventDeviceEntry.TABLE_NAME, null, contentValues);

        Cursor cursor = db.rawQuery("select last_insert_rowid() from " + AvailableEventDeviceEntry.TABLE_NAME, null);
        int newDeviceId = -1;
        if (cursor.moveToFirst()) {
//            newDeviceId = cursor.getInt(cursor.getColumnIndexOrThrow(AvailableEventDeviceEntry.COLUMN_NAME_DEVICE_ID));
        }
        db.close();
        return newDeviceId;
    }

    @Override
    public void getAvailableEventDevice(int deviceId, GetAvailableEventDeviceCallback callback) {
        EventDevice eventDevice = null;
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String selectionArg = String.valueOf(deviceId);
        String[] projection = {
                AvailableEventDeviceEntry.COLUMN_NAME_DEVICE_ID,
                AvailableEventDeviceEntry.COLUMN_NAME_TYPE_NAME,
                AvailableEventDeviceEntry.COLUMN_NAME_DEVICE_NAME,
                AvailableEventDeviceEntry.COLUMN_NAME_PIC_ID,
                AvailableEventDeviceEntry.COLUMN_NAME_FUNCTION_COUNT,
                AvailableEventDeviceEntry.COLUMN_NAME_X,
                AvailableEventDeviceEntry.COLUMN_NAME_Y
        };

        String selection = AvailableEventDeviceEntry.COLUMN_NAME_DEVICE_ID + " LIKE ?";
        String[] selectionArgs = {selectionArg};

        Cursor c = db.query(AvailableEventDeviceEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);
        if (c != null && c.getColumnCount() > 0) {
            c.moveToLast();
            String typeName = c.getString(c.getColumnIndexOrThrow(AvailableEventDeviceEntry.COLUMN_NAME_TYPE_NAME));
            String deviceName = c.getString(c.getColumnIndexOrThrow(AvailableEventDeviceEntry.COLUMN_NAME_DEVICE_NAME));
            int picId = c.getInt(c.getColumnIndexOrThrow(AvailableEventDeviceEntry.COLUMN_NAME_PIC_ID));
            int functionCount = c.getInt(c.getColumnIndexOrThrow(AvailableEventDeviceEntry.COLUMN_NAME_FUNCTION_COUNT));
            int x = c.getInt(c.getColumnIndexOrThrow(AvailableEventDeviceEntry.COLUMN_NAME_X));
            int y = c.getInt(c.getColumnIndexOrThrow(AvailableEventDeviceEntry.COLUMN_NAME_Y));

            String[] func_projection = {
                    DeviceFunctionEntry.COLUMN_NAME_TYPE_NAME,
                    DeviceFunctionEntry.COLUMN_NAME_FUNCTION_ID,
                    DeviceFunctionEntry.COLUMN_NAME_FUNCTION_NAME
            };
            String func_selection = DeviceFunctionEntry.COLUMN_NAME_TYPE_NAME + " LIKE ?";
            String[] func_selectionArgs = {typeName};
            String[] functionName = new String[functionCount];
            Cursor func_c = db.query(DeviceFunctionEntry.TABLE_NAME, func_projection, func_selection, func_selectionArgs, null, null, null);
            if (func_c != null && func_c.getCount()>0) {
                int i = 0;
                while (func_c.moveToNext()) {
                    functionName[i] = func_c.getString(func_c.getColumnIndexOrThrow(DeviceFunctionEntry.COLUMN_NAME_FUNCTION_NAME));
                    i++;
                }
            }
            if (func_c != null) {
                func_c.close();
            }
            eventDevice = new EventDevice(deviceId, typeName, deviceName, functionCount, functionName, picId, x, y);
        }
        if (c != null) {
            c.close();
        }
        db.close();

        if (eventDevice != null) {
            callback.onAvailableEventDeviceLoaded(eventDevice);
        } else {
            callback.onDataNotAvailable();
        }

    }

    @Override
    public void getAvailableEventDevices(LoadAvailableEventDevicesCallback callback) {
        List<EventDevice> eventDevices = new ArrayList<EventDevice>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String[] projection = {
                AvailableEventDeviceEntry.COLUMN_NAME_DEVICE_ID,
                AvailableEventDeviceEntry.COLUMN_NAME_TYPE_NAME,
                AvailableEventDeviceEntry.COLUMN_NAME_DEVICE_NAME,
                AvailableEventDeviceEntry.COLUMN_NAME_PIC_ID,
                AvailableEventDeviceEntry.COLUMN_NAME_FUNCTION_COUNT,
                AvailableEventDeviceEntry.COLUMN_NAME_X,
                AvailableEventDeviceEntry.COLUMN_NAME_Y
        };

        Cursor c = db.query(AvailableEventDeviceEntry.TABLE_NAME, projection, null, null, null, null, null);
        if (c != null && c.getColumnCount() > 0) {
            while (c.moveToNext()) {
                int deviceId = c.getInt(c.getColumnIndexOrThrow(AvailableEventDeviceEntry.COLUMN_NAME_DEVICE_ID));
                String typeName = c.getString(c.getColumnIndexOrThrow(AvailableEventDeviceEntry.COLUMN_NAME_TYPE_NAME));
                String deviceName = c.getString(c.getColumnIndexOrThrow(AvailableEventDeviceEntry.COLUMN_NAME_DEVICE_NAME));
                int picId = c.getInt(c.getColumnIndexOrThrow(AvailableEventDeviceEntry.COLUMN_NAME_PIC_ID));
                int function_count = c.getInt(c.getColumnIndexOrThrow(AvailableEventDeviceEntry.COLUMN_NAME_FUNCTION_COUNT));
                int x = c.getInt(c.getColumnIndexOrThrow(AvailableEventDeviceEntry.COLUMN_NAME_X));
                int y = c.getInt(c.getColumnIndexOrThrow(AvailableEventDeviceEntry.COLUMN_NAME_Y));

                String[] func_projection = {
                        DeviceFunctionEntry.COLUMN_NAME_TYPE_NAME,
                        DeviceFunctionEntry.COLUMN_NAME_FUNCTION_ID,
                        DeviceFunctionEntry.COLUMN_NAME_FUNCTION_NAME
                };

                String func_selection = DeviceFunctionEntry.COLUMN_NAME_TYPE_NAME + " LIKE ?";
                String[] func_selection_args = {typeName};
                String[] function_name = new String[function_count];

                Cursor func_c = db.query(DeviceFunctionEntry.TABLE_NAME, func_projection, func_selection, func_selection_args,
                        null, null, null);
                if (func_c != null && func_c.getColumnCount() > 0) {
                    for (int i = 0; func_c.moveToNext(); i++) {
                        function_name[i] = func_c.getString(func_c.getColumnIndexOrThrow(DeviceFunctionEntry.COLUMN_NAME_FUNCTION_NAME));
                    }
                }

                EventDevice eventDevice = new EventDevice(deviceId, typeName, deviceName, function_count, function_name, picId, x, y);
                eventDevices.add(eventDevice);
            }

            db.close();
            if (eventDevices != null) {
                callback.onAvailableEventDevicesLoaded(eventDevices);
            } else {
                callback.onDataNotAvailable();
            }
        }
    }

    @Override
    public int[] getEventDevicePictureId() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String[] projection = {
                EventDevicePictureEntry.COLUMN_NAME_PIC_RES_ID
        };

        Cursor c = db.query(EventDevicePictureEntry.TABLE_NAME, projection, null, null, null, null, null);
        if (c!=null && c.getCount()>0) {
            int[] eventDevicePicSrcId = new int[c.getCount()];
            int i=0;
            while (c.moveToNext()) {
                eventDevicePicSrcId[i] = c.getInt(c.getColumnIndexOrThrow(EventDevicePictureEntry.COLUMN_NAME_PIC_RES_ID));
                i++;
            }
            return eventDevicePicSrcId;
        }
        return null;
    }

    @Override
    public void saveEventDevicePicture() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        int[] eventDevicePicSrcId = {
                R.drawable.arrow_up,
                R.drawable.arrow_down,
                R.drawable.arrow_left,
                R.drawable.arrow_right
        };

        for (int i=0; i<eventDevicePicSrcId.length; i++) {
            contentValues.put(EventDevicePictureEntry.COLUMN_NAME_PIC_RES_ID, eventDevicePicSrcId[i]);
        }

        db.insert(EventDevicePictureEntry.TABLE_NAME, null, contentValues);
        db.close();
    }

    @Override
    public void updateAvailableEventDevicePicture(int deviceId, int newPicId) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        String selectionArg = String.valueOf(deviceId);
        String selection = AvailableEventDeviceEntry.COLUMN_NAME_DEVICE_ID + " LIKE ?";

        String[] selectionArgs = { selectionArg };
        ContentValues contentValues = new ContentValues();

        contentValues.put(AvailableEventDeviceEntry.COLUMN_NAME_PIC_ID, newPicId);

        db.update(AvailableEventDeviceEntry.TABLE_NAME, contentValues, selection, selectionArgs);
        db.close();

    }
}
