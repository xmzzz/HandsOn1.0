package com.xmz.handson10.data.source.instruction;

import android.content.Context;

import com.xmz.handson10.data.Instruction;
import com.xmz.handson10.data.source.DbHelper;
import com.xmz.handson10.data.source.InstructionSource;

/**
 * Created by xmz on 2016/6/20.
 */
public class InstructionLocalSource implements InstructionSource {

    private static InstructionLocalSource INSTANCE;

    private DbHelper mDbHelper;

    private InstructionLocalSource(Context context, String databaseIndex) {
        mDbHelper = new DbHelper(context, databaseIndex);
    }

    public static InstructionLocalSource getInstance(Context context, String databaseIndex) {
        if (INSTANCE == null) {
            INSTANCE = new InstructionLocalSource(context, databaseIndex);
        }
        return INSTANCE;
    }

    @Override
    public void getInstruction(int instructionId, GetInstructionCallback callback) {


    }

    @Override
    public void getInstruction(int eventDeviceId, int eventDeviceFuncId, GetInstructionCallback callback) {

    }

    @Override
    public void saveInstruction(Instruction instruction) {

    }

    @Override
    public void updateInstruction(Instruction instruction) {

    }

    @Override
    public void deleteInstruction(int instructionId) {

    }

    @Override
    public void deleteInstructionsForEventDevice(int eventDeviceId) {

    }

    @Override
    public void deleteInstructionsForEventDeviceFunction(int eventDeviceId, int eventDeviceFuncId) {

    }
}
