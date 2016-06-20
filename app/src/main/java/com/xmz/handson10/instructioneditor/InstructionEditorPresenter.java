package com.xmz.handson10.instructioneditor;

/**
 * Created by xmz on 2016/6/20.
 */
public class InstructionEditorPresenter implements InstructionEditorContract.Presenter {

    private final InstructionEditorContract.View mView;

    public InstructionEditorPresenter(InstructionEditorContract.View instructionEditorView) {
        mView = instructionEditorView;

        mView.setPresenter(this);
    }

    @Override
    public void start() {
        loadAvailableDevices();
    }

    @Override
    public void loadAvailableDevices() {

    }
}
