package com.xmz.handson10.addeventdevice;

import com.xmz.handson10.data.DeviceDescription;
import com.xmz.handson10.data.source.DeviceDescriptionSource;
import com.xmz.handson10.data.source.devicedescription.DeviceDescriptionLocalSource;

import java.util.List;

/**
 * Created by xmz on 2016/6/8.
 */
public class AddEventDevicePresenter implements AddEventDeviceContract.Presenter {

    private final AddEventDeviceContract.View mView;

    private final DeviceDescriptionLocalSource mDeviceDescriptionLocalSource;

    public AddEventDevicePresenter(DeviceDescriptionLocalSource deviceDescriptionLocalSource,
                                   AddEventDeviceContract.View view) {
        mDeviceDescriptionLocalSource = deviceDescriptionLocalSource;
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        loadEventDevices();
        loadAvailableEventDevices();
    }

    @Override
    public void loadEventDevices() {
        mDeviceDescriptionLocalSource.getEventDevices(new DeviceDescriptionSource.LoadDeviceDescriptionsCallback() {
            @Override
            public void onDeviceDescriptionsLoaded(List<DeviceDescription> deviceDescriptions) {
                processEventDevices(deviceDescriptions);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    private void processEventDevices(List<DeviceDescription> deviceDescriptions) {
        mView.showEventDevices(deviceDescriptions);
    }

    @Override
    public void loadEventDevice(int eventDeviceId) {

    }

    @Override
    public void loadAvailableEventDevices() {

    }

    @Override
    public void loadDeviceDescription(int deviceId) {

    }

    @Override
    public int addAvailableEventDevice(int deviceTypeId) {
        return 0;
    }

    @Override
    public void deleteAvailableEventDevice(int eventDeviceId) {

    }

    @Override
    public void updateAvailableEventDevice(DeviceDescription deviceDescription) {

    }

}
