package com.xmz.handson10.instructioneditor;

import com.xmz.handson10.BasePresenter;
import com.xmz.handson10.BaseView;
import com.xmz.handson10.data.DeviceAvailable;

import java.util.List;

/**
 * Created by xmz on 2016/6/20.
 */
public interface InstructionEditorContract {

    interface View extends BaseView<Presenter> {

        void showAvailableDevices(List<DeviceAvailable> availableDevices);

    }

    interface Presenter extends BasePresenter {

        void loadAvailableDevices();
    }
}
