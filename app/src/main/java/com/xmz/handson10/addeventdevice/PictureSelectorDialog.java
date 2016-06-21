package com.xmz.handson10.addeventdevice;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.xmz.handson10.R;

import java.io.Serializable;

/**
 * Created by xmz on 2016/6/17.
 */
public class PictureSelectorDialog extends DialogFragment {

    private int mNowSelected;

    ButtonOnClickListener mButtonOnClickListener;

    ImageView[] mButtonPic;

    public interface PictureSelectorInterface extends Serializable {
        void onSelected(int picId);
    }

    PictureSelectorInterface callbackListener;

    public static PictureSelectorDialog getInstance(PictureSelectorInterface pictureSelectorInterface) {
        PictureSelectorDialog pictureSelectorDialog = new PictureSelectorDialog();
        Bundle args = new Bundle();
        args.putSerializable("pictureSelectorInterface", pictureSelectorInterface);
        pictureSelectorDialog.setArguments(args);

        return pictureSelectorDialog;
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mButtonOnClickListener = new ButtonOnClickListener();
        callbackListener = (PictureSelectorInterface) getArguments().getSerializable("pictureSelectorInterface");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View pictureSelectorDialog = inflater.inflate(R.layout.button_picture_selector_dialog, null);

        mButtonPic = new ImageView[8];
        mButtonPic[0] = (ImageView) pictureSelectorDialog.findViewById(R.id.button_up);
        mButtonPic[0].setTag(R.drawable.arrow_up);
        mButtonPic[1] = (ImageView) pictureSelectorDialog.findViewById(R.id.button_down);
        mButtonPic[1].setTag(R.drawable.arrow_down);
        mButtonPic[2] = (ImageView) pictureSelectorDialog.findViewById(R.id.button_left);
        mButtonPic[2].setTag(R.drawable.arrow_left);
        mButtonPic[3] = (ImageView) pictureSelectorDialog.findViewById(R.id.button_right);
        mButtonPic[3].setTag(R.drawable.arrow_right);
        mButtonPic[4] = (ImageView) pictureSelectorDialog.findViewById(R.id.button1);
        mButtonPic[4].setTag(R.drawable.button1);
        mButtonPic[5] = (ImageView) pictureSelectorDialog.findViewById(R.id.button2);
        mButtonPic[5].setTag(R.drawable.button2);
        mButtonPic[6] = (ImageView) pictureSelectorDialog.findViewById(R.id.button3);
        mButtonPic[6].setTag(R.drawable.button3);
        mButtonPic[7] = (ImageView) pictureSelectorDialog.findViewById(R.id.button4);
        mButtonPic[7].setTag(R.drawable.button4);

        for (int i = 0; i < mButtonPic.length; i++) {
            mButtonPic[i].setOnClickListener(mButtonOnClickListener);
        }

        builder.setPositiveButton(R.string.dialog_positive_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callbackListener.onSelected(mNowSelected);
            }
        }).setNegativeButton(R.string.dialog_negative_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.setView(pictureSelectorDialog);
        return builder.create();
    }

    private class ButtonOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            for (int i = 0; i < mButtonPic.length; i++) {
                mButtonPic[i].setAlpha(0.6f);
            }
            v.setAlpha(1f);
            mNowSelected = (int) v.getTag();
        }
    }
}
