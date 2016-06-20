package com.xmz.handson10.data.source;

import com.xmz.handson10.data.Instruction;

/**
 * Created by xmz on 2016/6/20.
 */
public interface InstructionSource {

    interface GetInstructionCallback {

        void onInstructionLoaded(Instruction instruction);

        void onDataNotAvailable();
    }

    void getInstruction(int instructionId, GetInstructionCallback callback);

    void getInstruction(int eventDeviceId, int eventDeviceFuncId, GetInstructionCallback callback);

    void saveInstruction(Instruction instruction);

    void updateInstruction(Instruction instruction);

    void deleteInstruction(int instructionId);

    void deleteInstructionsForEventDevice(int eventDeviceId);

    void deleteInstructionsForEventDeviceFunction(int eventDeviceId, int eventDeviceFuncId);

}
