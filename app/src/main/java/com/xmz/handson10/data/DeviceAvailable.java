package com.xmz.handson10.data;

/**
 * Created by xmz on 2016/5/26.
 */

/*
* 当前可用的设备
*/
public class DeviceAvailable {

    private int mDeviceId;              // 该设备的唯一编号

    private int mTypeId;                // 该设备的类型编号，如：灯，马达，按钮，

    private int mTypeFeatureId;         // 设备的特征编号，如：事件类型设备（按钮、感应器）、动作类型设备（灯、马达）

    public DeviceAvailable(int deviceId, int typeId, int typeFeatureId) {
        mDeviceId = deviceId;
        mTypeId = typeId;
        mTypeFeatureId = typeFeatureId;
    }

    public int getDeviceId() {
        return mDeviceId;
    }

    public void setDeviceId(int deviceId) {
        mDeviceId = deviceId;
    }

    public int getTypeId() {
        return mTypeId;
    }

    public void setTypeId(int typeId) {
        mTypeId = typeId;
    }

    public int getTypeFeatureId() {
        return mTypeFeatureId;
    }

    public void setTypeFeatureId(int typeFeatureId) {
        mTypeFeatureId = typeFeatureId;
    }
}
