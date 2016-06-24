package com.xmz.handson10.deviceconnect;

import android.util.Log;

import com.xmz.handson10.R;
import com.xmz.handson10.data.DeviceAvailable;
import com.xmz.handson10.data.DeviceDescription;
import com.xmz.handson10.data.DeviceSocket;
import com.xmz.handson10.data.River;
import com.xmz.handson10.data.devices.DeviceLight;
import com.xmz.handson10.data.devices.DeviceMotor;
import com.xmz.handson10.data.devices.EventButton;
import com.xmz.handson10.data.source.DeviceDescriptionSource;
import com.xmz.handson10.data.source.DeviceSocketSource;
import com.xmz.handson10.data.source.devicedescription.DeviceDescriptionLocalSource;
import com.xmz.handson10.data.source.devicesocket.DeviceSocketLocalSource;
import com.xmz.handson10.util.RiverParser;

import java.util.List;

/**
 * Created by xmz on 2016/5/30.
 */
public class DeviceConnectPresenter implements DeviceConnectContract.Presenter,
        DeviceSocketLocalSource.GetSocketCallback, DeviceDescriptionSource.GetAvailableDeviceCallback,
        DeviceDescriptionSource.GetDeviceDescriptionCallback {

    private final DeviceDescriptionLocalSource mDeviceDescriptionLocalSource;

    private final DeviceSocketLocalSource mDeviceSocketLocalSource;

    private final DeviceConnectContract.View mDeviceConnectView;

    private DeviceSocket mDeviceSocket;

    private DeviceAvailable mDeviceAvailable;

    private DeviceDescription mDeviceDescription;

    private static final String TAG = "XML";

    private RiverParser parser;
    private List<River> rivers;

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

//        try {
//            String filename = "/sdcard/HandsOn1.0/resource/rivers.xml";
//            InputStream is = new FileInputStream(filename);
//
//            parser = new PullRiverParser();
//            rivers = parser.parse(is);
//            for (River river : rivers) {
////                Log.i(TAG,river.toString());
//            }
//
//            is.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        String[] river_0 = rivers.get(0).toString().split(" ");
//        String[] river_1 = rivers.get(1).toString().split(" ");
//        String[] river_2 = rivers.get(2).toString().split(" ");
//        DeviceDescription deviceDescription = new DeviceLight(river_0[0], river_0[1], Integer.valueOf(river_0[2]), new String[]{river_0[3], river_0[4], river_0[5]},
//                Integer.valueOf(river_0[6]), river_0[7]);
//        mDeviceDescriptionLocalSource.saveDeviceDescription(deviceDescription);
//
//        deviceDescription = new DeviceMotor(river_1[0],river_1[1],Integer.valueOf(river_1[2]),new String[]{river_1[3],river_1[4],river_1[5]},
//                Integer.valueOf(river_1[6]),river_1[7]);
//        mDeviceDescriptionLocalSource.saveDeviceDescription(deviceDescription);
//
//        deviceDescription = new EventButton(river_2[0],river_2[1],Integer.valueOf(river_2[2]),new String[]{river_2[3],river_2[4],river_2[5]},
//                Integer.valueOf(river_2[6]),river_2[7]);
//        mDeviceDescriptionLocalSource.saveDeviceDescription(deviceDescription);*/
        /************************************************************************************************************************/

//        DeviceDescription deviceDescription = new DeviceLight(
//                "LIGHT", "灯泡", 3, new String[]{"灯亮", "灯灭", "灯闪"},
//                R.drawable.light, "ACTION"
//        );
//        mDeviceDescriptionLocalSource.saveDeviceDescription(deviceDescription);
//        deviceDescription = new DeviceMotor(
//                "MOTOR", "马达", 2, new String[]{"顺时针转", "逆时针转"},
//                R.drawable.motor, "ACTION"
//        );
//
//        mDeviceDescriptionLocalSource.saveDeviceDescription(deviceDescription);
//        deviceDescription = new EventButton("BUTTON", "按键", 3, new String[]{"点击", "长按", "单击并保持长按状态"},
//                R.drawable.button_hand, "EVENT");
//
//        mDeviceDescriptionLocalSource.saveDeviceDescription(deviceDescription);
//
//        DeviceSocket deviceSocket = new DeviceSocket(R.drawable.socket, null, 100, 100);
//        mDeviceSocketLocalSource.saveDeviceSocket(deviceSocket);
//        deviceSocket = new DeviceSocket(R.drawable.socket, null, 200, 200);
//        mDeviceSocketLocalSource.saveDeviceSocket(deviceSocket);
//        deviceSocket = new DeviceSocket(R.drawable.socket, null, 300, 300);
//        mDeviceSocketLocalSource.saveDeviceSocket(deviceSocket);
//        deviceSocket = new DeviceSocket(R.drawable.socket, null, 400, 400);
//        mDeviceSocketLocalSource.saveDeviceSocket(deviceSocket);

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
    public void loadDeviceSocket(int socketId) {
        mDeviceSocketLocalSource.getDeviceSocket(socketId, this);
    }

    @Override
    public void loadDeviceSocketsAnimation() {
        mDeviceSocketLocalSource.getDeviceSockets(new DeviceSocketSource.LoadSocketsCallback() {
            @Override
            public void onSocketsLoaded(List<DeviceSocket> deviceSockets) {
                processDeviceSocketsAnimation(deviceSockets);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    public void processDeviceSocketsAnimation(List<DeviceSocket> deviceSockets) {
        mDeviceConnectView.showAvailableSocketsAnimation(deviceSockets);
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
    public void loadAvailableDevice(int deviceId) {
        mDeviceDescriptionLocalSource.getAvailableDevice(deviceId, this);
    }

    @Override
    public void saveDeviceSocket(DeviceSocket deviceSocket) {

    }

    @Override
    public void connectDevice(int deviceSocketId, int deviceTypeId, String deviceTypeFeature) {
        int deviceId;
        DeviceAvailable mDeviceAvailable = new DeviceAvailable(deviceTypeId, deviceTypeFeature);
        deviceId = mDeviceDescriptionLocalSource.saveDeviceAvailable(mDeviceAvailable);

        loadDeviceSocket(deviceSocketId);
        mDeviceDescriptionLocalSource.getDeviceDescription(deviceTypeId, this);
        if (mDeviceSocket.getConnectedDeviceId() != -1) {
//            Log.d("delete", "disconnected");
            disConnectDevice(deviceSocketId);
        }
        mDeviceSocket.setConnectedDeviceId(deviceId);
        mDeviceSocket.setPicSrcId(mDeviceDescription.getDevicePicSrcId());
        mDeviceSocketLocalSource.updateDeviceSocket(mDeviceSocket);

    }

    @Override
    public void disConnectDevice(int socketId) {
        loadDeviceSocket(socketId);
        mDeviceDescriptionLocalSource.deleteAvailableDevice(mDeviceSocket.getConnectedDeviceId());
        mDeviceSocket.setConnectedDeviceId(-1);
        mDeviceSocketLocalSource.updateDeviceSocket(mDeviceSocket);
        Log.d("update", String.valueOf(mDeviceSocket.getSocketId()));
        loadDeviceSockets();
    }

    @Override
    public void moveDeviceSocket(int socketId, int x, int y) {
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
    public void onAvailableDeviceLoaded(DeviceAvailable deviceAvailable) {
        mDeviceAvailable = deviceAvailable;
    }

    @Override
    public void onDeviceDescriptionLoaded(DeviceDescription deviceDescription) {
        mDeviceDescription = deviceDescription;
    }

    @Override
    public void updateDeviceSocket(DeviceSocket deviceSocket) {
        mDeviceSocketLocalSource.updateDeviceSocket(deviceSocket);
    }

    @Override
    public void createDeviceSocket(DeviceSocket deviceSocket) {

    }
}
