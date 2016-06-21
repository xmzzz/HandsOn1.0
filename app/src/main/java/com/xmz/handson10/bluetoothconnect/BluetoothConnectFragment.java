package com.xmz.handson10.bluetoothconnect;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jinxu on 2016/6/20.
 */
public class BluetoothConnectFragment extends Fragment implements BluetoothConnectContract.View{

    private BluetoothConnectContract.Presenter mPresenter;

    public BluetoothConnectFragment() {

    }

    public static BluetoothConnectFragment newInstance(){
        return new BluetoothConnectFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(BluetoothConnectContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
