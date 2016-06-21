package com.xmz.handson10.addeventdevice;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xmz.handson10.R;
import com.xmz.handson10.data.DeviceDescription;
import com.xmz.handson10.data.EventDevice;

import java.util.List;

/**
 * Created by xmz on 2016/6/8.
 */
public class AddEventDeviceFragment extends Fragment implements AddEventDeviceContract.View, PictureSelectorDialog.PictureSelectorInterface{

    private AddEventDeviceContract.Presenter mPresenter;  //

    private DrawerLayout mDrawerLayout;

    private LinearLayout mEventDeviceLL;

    private FrameLayout mContentFL;

    private HorizontalScrollView horizontalScrollView;

    private FloatingActionButton fabStartPlayActivity;

    private FloatingActionButton fabOpenDrawer;

    private FloatingActionButton fabDeviceTrash;

    private int mNewEventDeviceTypeId;

    private int mPictureSelectDeviceId;

    private AvailableEventDeviceTouchMoveListener mAvailableEventDeviceTouchMoveListener;

    public AddEventDeviceFragment() {}

    public static AddEventDeviceFragment newInstance() {
        return new AddEventDeviceFragment();
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
    public void setPresenter(AddEventDeviceContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.add_event_frag, container, false);
        horizontalScrollView = (HorizontalScrollView) root.findViewById(R.id.horizontalScrollView);
        mDrawerLayout = (DrawerLayout) root.findViewById(R.id.drawer_layout_frag);
        mEventDeviceLL = (LinearLayout) root.findViewById(R.id.event_devices_linear_layout);
        mContentFL = (FrameLayout) root.findViewById(R.id.contentFL);

        mContentFL.setOnDragListener(new AddEventDeviceOnDragListener());
        mContentFL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.loadAvailableEventDevices();
            }
        });

        fabOpenDrawer =
                (FloatingActionButton) root.findViewById(R.id.fab_open_drawer);
        fabOpenDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEventDevicesDrawer();
            }
        });

        fabStartPlayActivity =
                (FloatingActionButton) root.findViewById(R.id.fab_play);
        fabStartPlayActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        fabDeviceTrash = (FloatingActionButton) root.findViewById(R.id.fab_device_trash);
        fabDeviceTrash.setVisibility(View.GONE);

        mAvailableEventDeviceTouchMoveListener = new AvailableEventDeviceTouchMoveListener();

        return root;
    }

    public class AddEventDeviceOnDragListener implements View.OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (action) {
                case DragEvent.ACTION_DRAG_STARTED:
                    mContentFL.setBackgroundResource(R.color.colorEventDeviceFLStarted);
                    closeEventDevicesDrawer();
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    mContentFL.setBackgroundResource(R.color.colorEventDeviceFLOriginal);
                    break;
                case DragEvent.ACTION_DRAG_LOCATION:
                    mContentFL.setBackgroundResource(R.color.colorEventDeviceFLLocation);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    mContentFL.setBackgroundResource(R.color.colorEventDeviceFLStarted);
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    break;
                case DragEvent.ACTION_DROP:
                    LinearLayout availableEventDeviceLL =
                            (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.available_event_device_comp, null);
                    int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                    int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                    availableEventDeviceLL.measure(w, h);
                    int width = availableEventDeviceLL.getMeasuredWidth();
                    int height = availableEventDeviceLL.getMeasuredHeight();

                    mPresenter.addAvailableEventDevice(mNewEventDeviceTypeId, (int)(event.getX() - width/2), (int)(event.getY() - height/2));

                    break;

            }
            return true;
        }
    }

    @Override
    public void showEventDevices(List<DeviceDescription> deviceDescriptions) {
        mEventDeviceLL.removeAllViews();
        for (DeviceDescription deviceDescription : deviceDescriptions) {
            LinearLayout compLL = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.event_device_comp, null);
            TextView deviceName = (TextView) compLL.getChildAt(0);
            ImageView devicePic = (ImageView) compLL.getChildAt(1);

            deviceName.setText(deviceDescription.getDeviceName());
            devicePic.setImageResource(deviceDescription.getDevicePicSrcId());

            int newId = View.generateViewId();
            compLL.setId(newId);
            compLL.setTag(deviceDescription);

            compLL.setOnTouchListener(new EventDeviceTouchListener());
            mEventDeviceLL.addView(compLL);
        }
    }

    private class EventDeviceTouchListener implements View.OnTouchListener {
        DeviceDescription deviceDescription;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            deviceDescription = (DeviceDescription) v.getTag();
            mNewEventDeviceTypeId = deviceDescription.getTypeId();

            View.DragShadowBuilder myShadow = new View.DragShadowBuilder(v);
            v.startDrag(null, myShadow, null, 0);

            return true;
        }
    }

    @Override
    public void showAvailableEventDevices(List<EventDevice> eventDevices) {
        mContentFL.removeAllViews();
        for (final EventDevice eventDevice : eventDevices) {
            LinearLayout availableEventDeviceLL =
                    (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.available_event_device_comp, null);
            ImageView availableEventDeviceIV = (ImageView) availableEventDeviceLL.getChildAt(0);
            LinearLayout settingLL = (LinearLayout) availableEventDeviceLL.getChildAt(1);
            ImageView deviceLookIV = (ImageView) settingLL.getChildAt(0);
            ImageView settingIV = (ImageView) settingLL.getChildAt(1);
            deviceLookIV.setVisibility(View.GONE);
            settingIV.setVisibility(View.GONE);

            availableEventDeviceIV.setImageResource(eventDevice.getDevicePicSrcId());

            int x = eventDevice.getCoordinate_x();
            int y = eventDevice.getCoordinate_y();

            FrameLayout.LayoutParams layoutParams =
                    new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.leftMargin = x;
            layoutParams.topMargin = y;

            int newId = View.generateViewId();
            availableEventDeviceLL.setId(newId);
            availableEventDeviceLL.setTag(eventDevice);

            availableEventDeviceLL.setOnTouchListener(mAvailableEventDeviceTouchMoveListener);

            mContentFL.addView(availableEventDeviceLL, layoutParams);
        }
    }

    private void showAvailableEventDevicesWithTrash() {

    }

    private class AvailableEventDeviceTouchMoveListener implements View.OnTouchListener {
        FrameLayout.LayoutParams layoutParams;
        boolean isMove = false;
        int viewWidth = 0;
        int viewHeight = 0;

        DisplayMetrics dm = getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            Rect outRect = new Rect();
            getActivity().getWindow().findViewById(Window.ID_ANDROID_CONTENT).getDrawingRect(outRect);

            EventDevice eventDevice = (EventDevice) v.getTag();
            int deviceId = eventDevice.getDeviceId();
            int X = (int) event.getRawX();
            int Y = (int) event.getRawY();

            int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            v.measure(w, h);
            int width = v.getMeasuredWidth();
            int height = v.getMeasuredHeight();
            horizontalScrollView.measure(w,h);
            int h_height = horizontalScrollView.getMeasuredHeight();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    isMove = false;
                    layoutParams = (FrameLayout.LayoutParams) v.getLayoutParams();
                    viewWidth = X - layoutParams.leftMargin;
                    viewHeight = Y - layoutParams.topMargin;

                    processTrashEventStart(X, Y);

                    return true;
                case MotionEvent.ACTION_UP:
                    /*避免控件脱离目标区域*/
                    if (layoutParams.leftMargin < 0) {
                        layoutParams.leftMargin = 0;
                    }
                    if (layoutParams.topMargin < 10) {
                        Log.d("layoutParams.topMargin", String.valueOf(layoutParams.topMargin));
                        layoutParams.topMargin = 10;
                    }
                    if (layoutParams.leftMargin > screenWidth - width) {
                        Log.d("layoutParams.leftMargin", String.valueOf(layoutParams.leftMargin));
                        layoutParams.leftMargin = screenWidth - width;
                    }
                    if (layoutParams.topMargin > screenHeight - h_height - outRect.top - 1.4*height) {
                        Log.d("outRect.top", String.valueOf(outRect));
                        layoutParams.topMargin = (int) (screenHeight - h_height - outRect.top - 1.4*height);
                    }

                    mAvailableEventDeviceProcessor.onAvailableEventDeviceMove(deviceId, layoutParams.leftMargin, layoutParams.topMargin);
                    if (!isMove) {
                        showAvailableEventDeviceSettingBar(deviceId);
                    }
                    processTrashEventEnd(deviceId, X, Y);
                    return true;
                case MotionEvent.ACTION_MOVE:
                    isMove = true;
                    layoutParams.leftMargin = X - viewWidth;
                    layoutParams.topMargin = Y - viewHeight;
                    v.setLayoutParams(layoutParams);

                    processTrashEventStart(X, Y);
                    return true;
            }
            mContentFL.invalidate();
            return true;
        }
    }

    private void processTrashEventStart(int X, int Y) {
        fabDeviceTrash.setVisibility(View.VISIBLE);
        int[] location = new int[2];
        fabDeviceTrash.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
//        Log.d("processT", String.valueOf(x) + " " + String.valueOf(y));
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        fabDeviceTrash.measure(w, h);
        int width = fabDeviceTrash.getMeasuredWidth();
        int height = fabDeviceTrash.getMeasuredHeight();
//        Log.d("processTrashEvent", String.valueOf(width) + " " + String.valueOf(height));

        if ((X > x && X < x + width) && (Y > y && Y < y + height)) {
            fabDeviceTrash.setImageResource(R.drawable.trash_open);
        } else {
            fabDeviceTrash.setImageResource(R.drawable.trash_closed);
        }
    }

    private void processTrashEventEnd(int deviceId, int X, int Y) {
        fabDeviceTrash.setVisibility(View.GONE);
        int[] location = new int[2];
        fabDeviceTrash.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
//        Log.d("processT", String.valueOf(x) + " " + String.valueOf(y));
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        fabDeviceTrash.measure(w, h);
        int width = fabDeviceTrash.getMeasuredWidth();
        int height = fabDeviceTrash.getMeasuredHeight();
//        Log.d("processTrashEvent", String.valueOf(width) + " " + String.valueOf(height));

        if ((X > x && X < x + width) && (Y > y && Y < y + height)) {
            mPresenter.deleteAvailableEventDevice(deviceId);
        }
    }


    @Override
    public void showAvailableEventDeviceSettingBar(int deviceId) {
        for (int i = 0; i < mContentFL.getChildCount(); i++) {
            LinearLayout availableEventDeviceLL = (LinearLayout) mContentFL.getChildAt(i);
            int nowId = ((EventDevice) availableEventDeviceLL.getTag()).getDeviceId();
            if (nowId == deviceId) {
                ImageView availableEventDeviceIV = (ImageView) availableEventDeviceLL.getChildAt(0);
                LinearLayout settingLL = (LinearLayout) availableEventDeviceLL.getChildAt(1);
                ImageView deviceLookIV = (ImageView) settingLL.getChildAt(0);
                ImageView settingIV = (ImageView) settingLL.getChildAt(1);
                deviceLookIV.setVisibility(View.VISIBLE);
                deviceLookIV.setTag(deviceId);
                settingIV.setVisibility(View.VISIBLE);
                deviceLookIV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showEventDevicePicSelector((int)v.getTag());
                    }
                });
                settingIV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        }
    }

    @Override
    public void showEventDevicePicSelector(int deviceId) {
        mPictureSelectDeviceId = deviceId;
        PictureSelectorDialog pictureSelectorDialog = PictureSelectorDialog.getInstance(AddEventDeviceFragment.this);
        pictureSelectorDialog.show(getActivity().getSupportFragmentManager(), "dialog");
        Log.d("show", "selector");
    }

    @Override
    public void onSelected(int picId) {

        mPresenter.updateAvailableEventDevice(mPictureSelectDeviceId, picId);
    }

    public interface AvailableEventDeviceProcessor {

        void onAvailableEventDeviceMove(int deviceId, int x, int y);

    }

    AvailableEventDeviceProcessor mAvailableEventDeviceProcessor = new AvailableEventDeviceProcessor() {
        @Override
        public void onAvailableEventDeviceMove(int deviceId, int x, int y) {
            mPresenter.updateAvailableEventDevice(deviceId, x, y);
        }
    };
    @Override
    public void showEventDevicesEditor(int eventDeviceId) {

    }

    @Override
    public void showInstructionEditor(int eventDeviceId) {

    }

    @Override
    public void showEventDevicesDrawer() {
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public void closeEventDevicesDrawer() {
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }
}
