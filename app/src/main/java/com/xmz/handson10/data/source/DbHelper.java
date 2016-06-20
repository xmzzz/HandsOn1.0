package com.xmz.handson10.data.source;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.xmz.handson10.data.DocumentedDatabaseNameFactory;
import com.xmz.handson10.data.source.devicedescription.DeviceDescriptionPersistenceContract.AvailableEventDeviceEntry;
import com.xmz.handson10.data.source.devicedescription.DeviceDescriptionPersistenceContract.DeviceAvailableEntry;
import com.xmz.handson10.data.source.devicedescription.DeviceDescriptionPersistenceContract.DeviceDescriptionEntry;
import com.xmz.handson10.data.source.devicedescription.DeviceDescriptionPersistenceContract.DeviceFunctionEntry;
import com.xmz.handson10.data.source.devicedescription.DeviceDescriptionPersistenceContract.EventDevicePictureEntry;
import com.xmz.handson10.data.source.devicesocket.DeviceSocketPersistenceContract.DeviceSocketEntry;
import com.xmz.handson10.data.source.instruction.InstructionPersistenceContract.InstructionEntry;

/**
 * Created by xmz on 2016/5/28.
 */
public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String TEXT_TYPE = " TEXT";

    private static final String INT_TYPE = " INTEGER";

    private static final String REAL_TYPE = " REAL";

    private static final String COMMA_SEP = ",";

    // 设备插口
    private static final String SQL_SOCKET_CREATE_ENTRIES =
            "CREATE TABLE " + DeviceSocketEntry.TABLE_NAME + " (" +
                    DeviceSocketEntry.COLUMN_NAME_SOCKET_ID + INT_TYPE + " PRIMARY KEY AUTOINCREMENT," +
                    DeviceSocketEntry.COLUMN_NAME_PIC_ID + INT_TYPE + COMMA_SEP +
                    DeviceSocketEntry.COLUMN_NAME_TYPE + TEXT_TYPE + COMMA_SEP +
                    DeviceSocketEntry.COLUMN_NAME_X + INT_TYPE + " DEFAULT -1" + COMMA_SEP +
                    DeviceSocketEntry.COLUMN_NAME_Y + INT_TYPE + " DEFAULT -1" + COMMA_SEP +
                    DeviceSocketEntry.COLUMN_NAME_CONNECTED_ID + INT_TYPE + " DEFAULT -1" +
                    " )";

    // 设备类型
    private static final String SQL_DEVICE_TYPE_CREATE_ENTRIES =
            "CREATE TABLE " + DeviceDescriptionEntry.TABLE_NAME + " (" +
                    DeviceDescriptionEntry.COLUMN_NAME_TYPE_ID + INT_TYPE + " PRIMARY KEY AUTOINCREMENT," +
                    DeviceDescriptionEntry.COLUMN_NAME_TYPE_NAME + TEXT_TYPE + COMMA_SEP +
                    DeviceDescriptionEntry.COLUMN_NAME_DEVICE_NAME + TEXT_TYPE + COMMA_SEP +
                    DeviceDescriptionEntry.COLUMN_NAME_FUNCTION_COUNT + INT_TYPE + COMMA_SEP +
                    DeviceDescriptionEntry.COLUMN_NAME_PIC_ID + TEXT_TYPE + COMMA_SEP +
                    DeviceDescriptionEntry.COLUMN_NAME_FEATURE_ID + TEXT_TYPE +
                    " )";

    //当前可用设备
    private static final String SQL_DEVICE_AVAILABLE_CREATE_ENTRIES =
            "CREATE TABLE " + DeviceAvailableEntry.TABLE_NAME + " (" +
                    DeviceAvailableEntry.COLUMN_NAME_DEVICE_ID + INT_TYPE + " PRIMARY KEY AUTOINCREMENT," +
                    DeviceAvailableEntry.COLUMN_NAME_TYPE_ID + TEXT_TYPE + COMMA_SEP +
                    DeviceAvailableEntry.COLUMN_NAME_TYPE_FEATURE_ID + TEXT_TYPE +
                    " )";

    // 设备的功能列表
    private static final String SQL_FUNC_CREATE_ENTRIES =
            "CREATE TABLE " + DeviceFunctionEntry.TABLE_NAME + " (" +
                    DeviceFunctionEntry.COLUMN_NAME_TYPE_NAME + TEXT_TYPE + COMMA_SEP +
                    DeviceFunctionEntry.COLUMN_NAME_FUNCTION_ID + INT_TYPE + COMMA_SEP +
                    DeviceFunctionEntry.COLUMN_NAME_FUNCTION_NAME + TEXT_TYPE + COMMA_SEP +
                    "constraint pk_func primary key " + "(" +
                    DeviceFunctionEntry.COLUMN_NAME_TYPE_NAME + "," +
                    DeviceFunctionEntry.COLUMN_NAME_FUNCTION_ID + ")" +
                    " )";


    // 可用的事件触发设备
    private static final String SQL_AVAILABLE_EVENT_DEVICE_CREATE_ENTRIES =
            "CREATE TABLE " + AvailableEventDeviceEntry.TABLE_NAME + " (" +
                    AvailableEventDeviceEntry.COLUMN_NAME_DEVICE_ID + INT_TYPE + " PRIMARY KEY AUTOINCREMENT," +
                    AvailableEventDeviceEntry.COLUMN_NAME_TYPE_NAME + TEXT_TYPE + COMMA_SEP +
                    AvailableEventDeviceEntry.COLUMN_NAME_DEVICE_NAME + TEXT_TYPE + COMMA_SEP +
                    AvailableEventDeviceEntry.COLUMN_NAME_PIC_ID + INT_TYPE + COMMA_SEP +
                    AvailableEventDeviceEntry.COLUMN_NAME_FUNCTION_COUNT + INT_TYPE + COMMA_SEP +
                    AvailableEventDeviceEntry.COLUMN_NAME_X + INT_TYPE + COMMA_SEP +
                    AvailableEventDeviceEntry.COLUMN_NAME_Y + INT_TYPE +
                    " )";

    // 事件类型设备可供选择的背景图
    private static final String SQL_EVENT_DEVICE_PICTURE_CREATE_ENTRIES =
            "CREATE TABLE " + EventDevicePictureEntry.TABLE_NAME + " (" +
                    EventDevicePictureEntry.COLUMN_NAME_PIC_ID + INT_TYPE + " PRIMARY KEY AUTOINCREMENT," +
                    EventDevicePictureEntry.COLUMN_NAME_PIC_RES_ID + INT_TYPE +
                    " )";

    // 用户编辑的控制命令
    private static final String SQL_INSTRUCTIONS_CREATE_ENTRIES =
            "CREATE TABLE " + InstructionEntry.TABLE_NAME + " (" +
                    InstructionEntry.COLUMN_NAME_INSTRUCTION_ID + INT_TYPE + " PRIMARY KEY AUTOINCREMENT," +
                    InstructionEntry.COLUMN_NAME_EVENT_DEVICE_ID + INT_TYPE + COMMA_SEP +
                    InstructionEntry.COLUMN_NAME_EVENT_DEVICE_FUNC_ID + INT_TYPE + COMMA_SEP +
                    InstructionEntry.COLUMN_NAME_DEVICE_ID + INT_TYPE + COMMA_SEP +
                    InstructionEntry.COLUMN_NAME_FUNCTION_ID + INT_TYPE + COMMA_SEP +
                    InstructionEntry.COLUMN_NAME_FUNCTION_DURATION + INT_TYPE + COMMA_SEP +
                    InstructionEntry.COLUMN_NAME_X + INT_TYPE + COMMA_SEP +
                    InstructionEntry.COLUMN_NAME_Y + INT_TYPE +
                    " )";

    public DbHelper(Context context, String databaseIndex) {
        super(context,
                DocumentedDatabaseNameFactory.getDatabaseName(databaseIndex),
                null,
                DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_SOCKET_CREATE_ENTRIES);
        db.execSQL(SQL_DEVICE_TYPE_CREATE_ENTRIES);
        db.execSQL(SQL_DEVICE_AVAILABLE_CREATE_ENTRIES);
        db.execSQL(SQL_FUNC_CREATE_ENTRIES);
        db.execSQL(SQL_AVAILABLE_EVENT_DEVICE_CREATE_ENTRIES);
        db.execSQL(SQL_EVENT_DEVICE_PICTURE_CREATE_ENTRIES);
        db.execSQL(SQL_INSTRUCTIONS_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //      super.onDowngrade(db, oldVersion, newVersion);
    }
}
