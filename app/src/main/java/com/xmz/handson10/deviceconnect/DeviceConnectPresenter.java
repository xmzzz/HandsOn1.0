package com.xmz.handson10.deviceconnect;

import com.xmz.handson10.R;
import com.xmz.handson10.data.DeviceDescription;
import com.xmz.handson10.data.DeviceSocket;
import com.xmz.handson10.data.source.DeviceDescriptionSource;
import com.xmz.handson10.data.source.DeviceSocketSource;
import com.xmz.handson10.data.source.devicedescription.DeviceDescriptionLocalSource;
import com.xmz.handson10.data.source.devicesocket.DeviceSocketLocalSource;
import com.xmz.handson10.util.IdFactory;

import java.util.List;

/**
 * Created by xmz on 2016/5/30.
 */
public class DeviceConnectPresenter implements DeviceConnectContract.Presenter,
        DeviceSocketLocalSource.GetSocketCallback {

    private final DeviceDescriptionLocalSource mDeviceDescriptionLocalSource;

    private final DeviceSocketLocalSource mDeviceSocketLocalSource;

    private final DeviceConnectContract.View mDeviceConnectView;

    private DeviceSocket mDeviceSocket;

    public DeviceConnectPresenter(DeviceDescriptionLocalSource deviceDescriptionLocalSource,
                                  DeviceSocketLocalSource deviceSocketLocalSource,
                                  DeviceConnectContract.View deviceConnectView) {
        mDeviceDescriptionLocalSource = deviceDescriptionLocalSource;
        mDeviceSocketLocalSource = deviceSocketLocalSource;
        mDeviceConnectView = deviceConnectView;

        mDeviceConnectView.setPresenter(this);
        initData();
    }

    @Override
    public void start() {

        loadDeviceDescription();
        loadDeviceSockets();
    }

    @Override
    public void initData() {
        DeviceDescription deviceDescription = new DeviceDescription(
                IdFactory.LIGHT_TYPE_ID, "LIGHT", "灯泡", 3, new String[]{"灯亮", "灯灭", "灯闪"},
                R.drawable.light, "ACTION"
        );
        mDeviceDescriptionLocalSource.saveDeviceDescription(deviceDescription);
        deviceDescription = new DeviceDescription(
                IdFactory.MOTOR_TYPE_ID, "MOTOR", "马达", 2, new String[]{"顺时针转", "逆时针转"},
                R.drawable.motor, "ACTION"
        );
        mDeviceDescriptionLocalSource.saveDeviceDescription(deviceDescription);

        DeviceSocket deviceSocket = new DeviceSocket(IdFactory.SOCKET1_ID, R.drawable.socket, null);
        mDeviceSocketLocalSource.saveDeviceSocket(deviceSocket);
        deviceSocket = new DeviceSocket(IdFactory.SOCKET2_ID, R.drawable.socket, null);
        mDeviceSocketLocalSource.saveDeviceSocket(deviceSocket);
        deviceSocket = new DeviceSocket(IdFactory.SOCKET3_ID, R.drawable.socket, null);
        mDeviceSocketLocalSource.saveDeviceSocket(deviceSocket);
        deviceSocket = new DeviceSocket(IdFactory.SOCKET4_ID, R.drawable.socket, null);
        mDeviceSocketLocalSource.saveDeviceSocket(deviceSocket);

    }

    @Override
    public void loadDeviceDescription() {
        mDeviceDescriptionLocalSource.getDeviceDescriptions(new DeviceDescriptionSource.LoadDeviceDescriptionsCallback() {
            @Override
            public void onDeviceDescriptionsLoaded(List<DeviceDescription> deviceDescriptions) {
                processDeviceDescriptions(deviceDescriptions);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public void loadDeviceSockets() {
        mDeviceSocketLocalSource.getDeviceSockets(new DeviceSocketSource.LoadSocketsCallback() {
            @Override
            public void onSocketsLoaded(List<DeviceSocket> deviceSockets) {
                processDeviceSockets(deviceSockets);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public void loadDeviceSocket(String socketId) {
        mDeviceSocketLocalSource.getDeviceSocket(socketId, this);
    }

    private void processDeviceDescriptions(List<DeviceDescription> deviceDescriptions) {
        mDeviceConnectView.showDeviceDescriptions(deviceDescriptions);

    }

    private void processDeviceSockets(List<DeviceSocket> deviceSockets) {
        mDeviceConnectView.showDeviceSockets(deviceSockets);
    }

    @Override
    public void loadAvailableDevices() {

    }

    @Override
    public void saveDeviceSocket(DeviceSocket deviceSocket) {

    }

    @Override
    public void connectDevice(String deviceId) {

    }

    @Override
    public void disConnectDevice(String socketId) {

    }

    @Override
    public void moveDeviceSocket(String socketId, int x, int y) {
        loadDeviceSocket(socketId);
        mDeviceSocket.setCoordinate_x(x);
        mDeviceSocket.setCoordinate_y(y);
        updateDeviceSocket(mDeviceSocket);
        loadDeviceSockets();
    }

    @Override
    public void onSocketLoaded(DeviceSocket deviceSocket) {
        mDeviceSocket = deviceSocket;
    }

    @Override
    public void onDataNotAvailable() {

    }

    @Override
    public void updateDeviceSocket(DeviceSocket deviceSocket) {
        mDeviceSocketLocalSource.updateDeviceSocket(deviceSocket);
    }

    @Override
    public void createDeviceSocket(DeviceSocket deviceSocket) {

    }
}
