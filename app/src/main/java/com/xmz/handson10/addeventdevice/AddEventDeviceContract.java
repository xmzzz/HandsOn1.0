package com.xmz.handson10.addeventdevice;

import com.xmz.handson10.BasePresenter;
import com.xmz.handson10.BaseView;
import com.xmz.handson10.data.DeviceDescription;
import com.xmz.handson10.data.EventDevice;

import java.util.List;

/**
 * Created by xmz on 2016/6/8.
 */
public interface AddEventDeviceContract {

    interface View extends BaseView<Presenter> {

        void showEventDevices(List<DeviceDescription> deviceDescriptions);

        void showAvailableEventDevices(List<EventDevice> eventDevices);

        void showAvailableEventDeviceSettingBar(int deviceId);

        void showEventDevicesEditor(int eventDeviceId);

        void showInstructionEditor(int eventDeviceId);

        void showEventDevicePicSelector(int deviceId);

        void showEventDevicesDrawer();

        void closeEventDevicesDrawer();
    }

    interface Presenter extends BasePresenter {

        void loadEventDevices();

        void loadEventDevice(int eventDeviceId);

        void loadAvailableEventDevices();

        void loadAvailableEventDevice(int deviceId);

        void addAvailableEventDevice(int deviceTypeId, int x, int y);

        void updateAvailableEventDevice(int deviceId, int x, int y);

        void updateAvailableEventDevice(int deviceId, int newPicId);

        void deleteAvailableEventDevice(int eventDeviceId);

        void loadEventDevicePicture();

    }
}
