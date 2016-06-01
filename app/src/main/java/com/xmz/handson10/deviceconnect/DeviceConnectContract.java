package com.xmz.handson10.deviceconnect;

import com.xmz.handson10.BasePresenter;
import com.xmz.handson10.BaseView;
import com.xmz.handson10.data.DeviceAvailable;
import com.xmz.handson10.data.DeviceDescription;
import com.xmz.handson10.data.DeviceSocket;

import java.util.List;

/**
 * Created by xmz on 2016/5/30.
 */
public interface DeviceConnectContract {

    interface View extends BaseView<Presenter> {

        void showDeviceSockets(List<DeviceSocket> deviceSockets);

        void showAvailableDevices(List<DeviceAvailable> availableDevices);

        void showDeviceDescriptions(List<DeviceDescription> deviceDescriptions);

        void showEditControl();

        void showDeviceDescriptionsDrawer();
    }

    interface Presenter extends BasePresenter {

        void initData();

        void loadDeviceSockets();

        void loadDeviceSocket(String socketId);

        void moveDeviceSocket(String socketId, int x, int y);

        void updateDeviceSocket(DeviceSocket deviceSocket);

        void createDeviceSocket(DeviceSocket deviceSocket);

        void loadAvailableDevices();

        void loadDeviceDescription();

        void saveDeviceSocket(DeviceSocket deviceSocket);

        void connectDevice(String deviceId);

        void disConnectDevice(String socketId);
    }
}
