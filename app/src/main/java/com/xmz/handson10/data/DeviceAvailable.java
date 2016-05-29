package com.xmz.handson10.data;

/**
 * Created by xmz on 2016/5/26.
 */

/*
* 当前可用的设备
*/
public class DeviceAvailable {

    private String mDeviceId;              // 该设备的唯一编号

    private String mTypeId;                // 该设备的类型编号，如：灯，马达，按钮，

    private String mTypeFeatureId;         // 设备的特征编号，如：事件类型设备（按钮、感应器）、动作类型设备（灯、马达）

    public DeviceAvailable(String deviceId, String typeId, String typeFeatureId) {
        mDeviceId = deviceId;
        mTypeId = typeId;
        mTypeFeatureId = typeFeatureId;
    }

    public String getDeviceId() {
        return mDeviceId;
    }

    public void setDeviceId(String deviceId) {
        mDeviceId = deviceId;
    }

    public String getTypeId() {
        return mTypeId;
    }

    public void setTypeId(String typeId) {
        mTypeId = typeId;
    }

    public String getTypeFeatureId() {
        return mTypeFeatureId;
    }

    public void setTypeFeatureId(String typeFeatureId) {
        mTypeFeatureId = typeFeatureId;
    }
}
