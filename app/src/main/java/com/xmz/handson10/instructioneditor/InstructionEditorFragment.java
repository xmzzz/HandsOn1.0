package com.xmz.handson10.instructioneditor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xmz.handson10.data.DeviceAvailable;

import java.util.List;

/**
 * Created by xmz on 2016/6/20.
 */
public class InstructionEditorFragment extends Fragment implements InstructionEditorContract.View{

    InstructionEditorContract.Presenter mPresenter;

    public InstructionEditorFragment() {

    }

    public static InstructionEditorFragment newInstance() {
        return new InstructionEditorFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(InstructionEditorContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    public void showAvailableDevices(List<DeviceAvailable> availableDevices) {

    }
}
