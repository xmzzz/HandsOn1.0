package com.xmz.handson10.data.source;

import com.xmz.handson10.data.DeviceAvailable;
import com.xmz.handson10.data.DeviceDescription;
import com.xmz.handson10.data.EventDevice;

import java.util.List;

/**
 * Created by xmz on 2016/5/27.
 */
public interface DeviceDescriptionSource {

    interface LoadDeviceDescriptionsCallback {

        void onDeviceDescriptionsLoaded(List<DeviceDescription> deviceDescriptions);

        void onDataNotAvailable();
    }

    interface GetDeviceDescriptionCallback {

        void onDeviceDescriptionLoaded(DeviceDescription deviceDescription);

        void onDataNotAvailable();
    }

    interface LoadAvailableDevicesCallback {

        void onAvailableDevicesLoaded(List<DeviceAvailable> deviceAvailables);

        void onDataNotAvailable();
    }

    interface GetAvailableDeviceCallback {

        void onAvailableDeviceLoaded(DeviceAvailable deviceAvailable);

        void onDataNotAvailable();
    }

    interface LoadAvailableEventDevicesCallback {

        void onAvailableEventDevicesLoaded(List<EventDevice> eventDevices);

        void onDataNotAvailable();
    }

    interface GetAvailableEventDeviceCallback {

        void onAvailableEventDeviceLoaded(EventDevice eventDevice);

        void onDataNotAvailable();
    }


    void getDeviceDescriptions(LoadDeviceDescriptionsCallback callback);

    void getDeviceDescription(int typeId, GetDeviceDescriptionCallback callback);

    void saveDeviceDescription(DeviceDescription deviceDescription);

    void deleteAllDeviceDescription();

    void deleteDeviceDescription(String typeId);

    void getAvailableDevices(LoadAvailableDevicesCallback callback);

    void getAvailableDevice(int deviceId, GetAvailableDeviceCallback callback);

    int saveDeviceAvailable(DeviceAvailable deviceAvailable);

    void deleteAllAvailableDevice();

    void deleteAvailableDevice(int deviceId);

    void getAvailableButton(int deviceId, GetAvailableDeviceCallback callback);

    void getEventDevices(LoadDeviceDescriptionsCallback callback);

    void getAvailableEventDevices(LoadAvailableEventDevicesCallback callback);

    void getAvailableEventDevice(int deviceId, GetAvailableEventDeviceCallback callback);

    void removeAvailableEventDevice(int deviceId);

    int saveAvailableEventDevice(EventDevice eventDevice);

    void updateAvailableEventDevice(EventDevice eventDevice);

    void updateAvailableEventDevicePicture(int deviceId, int newPicId);

    int[] getEventDevicePictureId();

    void saveEventDevicePicture();

}
