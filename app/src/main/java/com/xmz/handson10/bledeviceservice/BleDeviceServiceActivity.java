package com.xmz.handson10.bledeviceservice;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.xmz.handson10.R;
import com.xmz.handson10.data.source.devicedescription.DeviceDescriptionLocalSource;
import com.xmz.handson10.data.source.devicesocket.DeviceSocketLocalSource;

/**
 * Created by jinxu on 2016/6/20.
 */
public class BleDeviceServiceActivity extends AppCompatActivity {

    private BleDeviceServicePresenter mPresenter;

    private FrameLayout mFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

//        mFrameLayout = (FrameLayout) findViewById(R.id.frame_act);
//        BleDeviceServiceFragment bleDeviceServiceFragment =
//                (BleDeviceServiceFragment) getFragmentManager().findFragmentById(R.id.frame_act);
//        if (bleDeviceServiceFragment == null) {
//            bleDeviceServiceFragment = BleDeviceServiceFragment.newIntance();
//            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            transaction.add(R.id.frame_act,bleDeviceServiceFragment);
//            transaction.commit();
//        }
//
//        mPresenter = new BleDeviceServicePresenter(DeviceDescriptionLocalSource.getInstance(this, "00"),
//                DeviceSocketLocalSource.getInstance(this, "00"),
//                );

    }

    @Override
    protected void onStart() {
        super.onStart();
        this.getSupportActionBar();
    }
}
