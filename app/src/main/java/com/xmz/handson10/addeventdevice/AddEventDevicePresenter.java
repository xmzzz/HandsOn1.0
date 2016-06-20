package com.xmz.handson10.addeventdevice;

import com.xmz.handson10.data.DeviceDescription;
import com.xmz.handson10.data.EventDevice;
import com.xmz.handson10.data.source.DeviceDescriptionSource;
import com.xmz.handson10.data.source.DeviceDescriptionSource.GetAvailableEventDeviceCallback;
import com.xmz.handson10.data.source.DeviceDescriptionSource.GetDeviceDescriptionCallback;
import com.xmz.handson10.data.source.devicedescription.DeviceDescriptionLocalSource;

import java.util.List;

/**
 * Created by xmz on 2016/6/8.
 */
public class AddEventDevicePresenter implements AddEventDeviceContract.Presenter {

    private final AddEventDeviceContract.View mView;

    private final DeviceDescriptionLocalSource mDeviceDescriptionLocalSource;

    private DeviceDescription mDeviceDescription;

    private EventDevice mEventDevice;

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
        mDeviceDescriptionLocalSource.getAvailableEventDevices(new DeviceDescriptionSource.LoadAvailableEventDevicesCallback() {
            @Override
            public void onAvailableEventDevicesLoaded(List<EventDevice> eventDevices) {
                processAvailableEventDevices(eventDevices);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    private void processAvailableEventDevices(List<EventDevice> eventDevices) {
        mView.showAvailableEventDevices(eventDevices);
    }

    @Override
    public void loadAvailableEventDevice(int deviceId) {
        mDeviceDescriptionLocalSource.getAvailableEventDevice(deviceId, mGetAvailableEventDeviceCallback);

    }

    @Override
    public void addAvailableEventDevice(int deviceTypeId, int x, int y) {
        EventDevice eventDevice;

        mDeviceDescriptionLocalSource.getDeviceDescription(deviceTypeId, mGetDeviceDescriptionCallback);
        eventDevice = new EventDevice(
                mDeviceDescription.getTypeName(),
                mDeviceDescription.getDeviceName(),
                mDeviceDescription.getFuncCount(),
                mDeviceDescription.getFuncName(),
                mDeviceDescription.getDevicePicSrcId(),
                x, y);

        mDeviceDescriptionLocalSource.saveAvailableEventDevice(eventDevice);

        loadAvailableEventDevices();

    }

    @Override
    public void updateAvailableEventDevice(int deviceId, int x, int y) {
        loadAvailableEventDevice(deviceId);
        mEventDevice.setCoordinate_x(x);
        mEventDevice.setCoordinate_y(y);
        mDeviceDescriptionLocalSource.updateAvailableEventDevice(mEventDevice);
        loadAvailableEventDevices();
    }

    @Override
    public void deleteAvailableEventDevice(int eventDeviceId) {
        mDeviceDescriptionLocalSource.removeAvailableEventDevice(eventDeviceId);
        loadAvailableEventDevices();
    }

    GetDeviceDescriptionCallback mGetDeviceDescriptionCallback = new GetDeviceDescriptionCallback() {
        @Override
        public void onDeviceDescriptionLoaded(DeviceDescription deviceDescription) {
            mDeviceDescription = deviceDescription;
        }

        @Override
        public void onDataNotAvailable() {

        }
    };

    GetAvailableEventDeviceCallback mGetAvailableEventDeviceCallback = new GetAvailableEventDeviceCallback() {
        @Override
        public void onAvailableEventDeviceLoaded(EventDevice eventDevice) {
            mEventDevice = eventDevice;
        }

        @Override
        public void onDataNotAvailable() {

        }
    };

    @Override
    public void loadEventDevicePicture() {

    }

    @Override
    public void updateAvailableEventDevice(int deviceId, int newPicId) {
        mDeviceDescriptionLocalSource.updateAvailableEventDevicePicture(deviceId, newPicId);
        loadAvailableEventDevices();
    }
}
