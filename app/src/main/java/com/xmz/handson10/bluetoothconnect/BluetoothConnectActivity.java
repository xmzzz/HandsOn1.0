package com.xmz.handson10.bluetoothconnect;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.xmz.handson10.R;

/**
 * Created by jinxu on 2016/6/20.
 */
public class BluetoothConnectActivity extends AppCompatActivity {

    private BluetoothConnectPresenter mPresenter;

    private FrameLayout mFrameLayout;

    private BluetoothConnectFragment bluetoothConnectFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

//        mFrameLayout = (FrameLayout) findViewById(R.id.frame_act);
//        if (bluetoothConnectFragment == null) {
//            bluetoothConnectFragment = BluetoothConnectFragment.newInstance();
//            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            transaction.add(R.id.frame_act, bluetoothConnectFragment);
//            transaction.commit();
//        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        this.getSupportActionBar();
    }
}
