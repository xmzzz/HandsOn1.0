package com.xmz.handson10.deviceconnect;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.FrameLayout;

import com.xmz.handson10.R;
import com.xmz.handson10.data.River;
import com.xmz.handson10.data.source.devicedescription.DeviceDescriptionLocalSource;
import com.xmz.handson10.data.source.devicesocket.DeviceSocketLocalSource;
import com.xmz.handson10.util.RiverParser;

import java.io.File;
import java.util.List;

/**
 * Created by xmz on 2016/5/30.
 */
public class DeviceConnectActivity extends AppCompatActivity {

    private DeviceConnectPresenter mPresenter;

    private FrameLayout mFrameLayout;

    private static final String TAG = "XML";

    private RiverParser parser;
    private List<River> rivers;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        ActionBar ab = getSupportActionBar();
//        ab.setDisplayHomeAsUpEnabled(true);
        createFolder();
        Log.d("create folder","create folder");

//        try {
//            String filename = "/sdcard/HandsOn1.0/resource/rivers.xml";
//            InputStream is = new FileInputStream(filename);
//
//            parser = new PullRiverParser();
//            rivers = parser.parse(is);
//            for (River river : rivers){
//                Log.i(TAG,river.toString());
//            }
//
//            is.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        mFrameLayout = (FrameLayout) findViewById(R.id.frame_act);
        DeviceConnectFragment deviceConnectFragment =
                (DeviceConnectFragment) getSupportFragmentManager().findFragmentById(R.id.frame_act);
        if (deviceConnectFragment == null) {
            deviceConnectFragment = DeviceConnectFragment.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.frame_act, deviceConnectFragment);
            transaction.commit();
        }

        mPresenter = new DeviceConnectPresenter(
                DeviceDescriptionLocalSource.getInstance(this, "00"),
                DeviceSocketLocalSource.getInstance(this, "00"),
                deviceConnectFragment);
    }


    @Override
    protected void onStart() {
        super.onStart();
        this.getSupportActionBar();
    }

    private void createFolder() {
        String filePath = "/sdcard/HandsOn1.0/resource/";

        File file = new File(filePath);
        if (!file.exists()) {
            try {
                //按照指定的路径创建文件夹
                file.mkdirs();
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }
}
