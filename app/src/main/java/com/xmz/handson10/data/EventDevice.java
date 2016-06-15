package com.xmz.handson10.data;

/**
 * Created by xmz on 2016/6/12.
 */
public class EventDevice {

    private int mDeviceId;

    private String mTypeName;

    private String mDeviceName;

    private int mFuncCount;

    private String[] mFuncName;

    private int mDevicePicSrcId;

    private int mCoordinate_x;            // 用于用户显示，x坐标

    private int mCoordinate_y;            // 用于用户显示，y坐标

    public EventDevice(int deviceId, String typeName, String deviceName, int funcCount, String[] funcName, int devicePicSrcId) {
        mDeviceId = deviceId;
        mTypeName = typeName;
        mDeviceName = deviceName;
        mFuncCount = funcCount;
        mFuncName = funcName;
        mDevicePicSrcId = devicePicSrcId;
    }

    public EventDevice(String typeName, String deviceName, int funcCount, String[] funcName, int devicePicSrcId) {
        mTypeName = typeName;
        mDeviceName = deviceName;
        mFuncCount = funcCount;
        mFuncName = funcName;
        mDevicePicSrcId = devicePicSrcId;
    }

    public EventDevice(String typeName, String deviceName, int funcCount, String[] funcName, int devicePicSrcId, int coordinate_x, int coordinate_y) {
        mTypeName = typeName;
        mDeviceName = deviceName;
        mFuncCount = funcCount;
        mFuncName = funcName;
        mDevicePicSrcId = devicePicSrcId;
        mCoordinate_x = coordinate_x;
        mCoordinate_y = coordinate_y;
    }

    public EventDevice(int deviceId, String typeName, String deviceName, int funcCount, String[] funcName, int devicePicSrcId, int coordinate_x, int coordinate_y) {
        mDeviceId = deviceId;
        mTypeName = typeName;
        mDeviceName = deviceName;
        mFuncCount = funcCount;
        mFuncName = funcName;
        mDevicePicSrcId = devicePicSrcId;
        mCoordinate_x = coordinate_x;
        mCoordinate_y = coordinate_y;
    }

    public int getDeviceId() {
        return mDeviceId;
    }

    public void setDeviceId(int deviceId) {
        mDeviceId = deviceId;
    }

    public String getTypeName() {
        return mTypeName;
    }

    public void setTypeName(String typeName) {
        mTypeName = typeName;
    }

    public String getDeviceName() {
        return mDeviceName;
    }

    public void setDeviceName(String deviceName) {
        mDeviceName = deviceName;
    }

    public int getFuncCount() {
        return mFuncCount;
    }

    public void setFuncCount(int funcCount) {
        mFuncCount = funcCount;
    }

    public String[] getFuncName() {
        return mFuncName;
    }

    public void setFuncName(String[] funcName) {
        mFuncName = funcName;
    }

    public int getDevicePicSrcId() {
        return mDevicePicSrcId;
    }

    public void setDevicePicSrcId(int devicePicSrcId) {
        mDevicePicSrcId = devicePicSrcId;
    }

    public int getCoordinate_x() {
        return mCoordinate_x;
    }

    public void setCoordinate_x(int coordinate_x) {
        mCoordinate_x = coordinate_x;
    }

    public int getCoordinate_y() {
        return mCoordinate_y;
    }

    public void setCoordinate_y(int coordinate_y) {
        mCoordinate_y = coordinate_y;
    }
}
