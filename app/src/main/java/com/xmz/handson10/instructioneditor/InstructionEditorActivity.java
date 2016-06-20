package com.xmz.handson10.instructioneditor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.xmz.handson10.R;

/**
 * Created by xmz on 2016/6/20.
 */
public class InstructionEditorActivity extends AppCompatActivity {

    private InstructionEditorPresenter mPresenter;

    private FrameLayout mFrameLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFrameLayout = (FrameLayout) findViewById(R.id.frame_act);
        InstructionEditorFragment instructionEditorFragment =
                (InstructionEditorFragment) getSupportFragmentManager().findFragmentById(R.id.frame_act);
        if (instructionEditorFragment == null) {
            instructionEditorFragment = InstructionEditorFragment.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.frame_act, instructionEditorFragment);
            transaction.commit();
        }

        mPresenter = new InstructionEditorPresenter(instructionEditorFragment);
    }
}
