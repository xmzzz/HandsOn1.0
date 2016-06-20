package com.xmz.handson10.data;

/**
 * Created by xmz on 2016/6/20.
 */
public class Instruction {

    private int mInstructionId;

    private int mEventDeviceId;

    private int mEventDeviceFuncId;

    private int mDeviceId;

    private int mFunctionId;

    private int mFunctionDuration;

    private int mCoordinateX;

    private int mCoordinateY;

    public Instruction(int instructionId, int eventDeviceId, int eventDeviceFuncId, int deviceId, int functionId, int functionDuration, int coordinateX, int coordinateY) {
        mInstructionId = instructionId;
        mEventDeviceId = eventDeviceId;
        mEventDeviceFuncId = eventDeviceFuncId;
        mDeviceId = deviceId;
        mFunctionId = functionId;
        mFunctionDuration = functionDuration;
        mCoordinateX = coordinateX;
        mCoordinateY = coordinateY;
    }

    public Instruction(int eventDeviceId, int eventDeviceFuncId, int deviceId, int functionId, int functionDuration, int coordinateX, int coordinateY) {
        mEventDeviceId = eventDeviceId;
        mEventDeviceFuncId = eventDeviceFuncId;
        mDeviceId = deviceId;
        mFunctionId = functionId;
        mFunctionDuration = functionDuration;
        mCoordinateX = coordinateX;
        mCoordinateY = coordinateY;
    }

    public Instruction(int instructionId, int eventDeviceId, int eventDeviceFuncId, int deviceId, int functionId, int functionDuration) {
        mInstructionId = instructionId;
        mEventDeviceId = eventDeviceId;
        mEventDeviceFuncId = eventDeviceFuncId;
        mDeviceId = deviceId;
        mFunctionId = functionId;
        mFunctionDuration = functionDuration;
    }

    public Instruction(int eventDeviceId, int eventDeviceFuncId, int deviceId, int functionId, int functionDuration) {
        mEventDeviceId = eventDeviceId;
        mEventDeviceFuncId = eventDeviceFuncId;
        mDeviceId = deviceId;
        mFunctionId = functionId;
        mFunctionDuration = functionDuration;
    }

    public int getInstructionId() {
        return mInstructionId;
    }

    public void setInstructionId(int instructionId) {
        mInstructionId = instructionId;
    }

    public int getEventDeviceId() {
        return mEventDeviceId;
    }

    public void setEventDeviceId(int eventDeviceId) {
        mEventDeviceId = eventDeviceId;
    }

    public int getEventDeviceFuncId() {
        return mEventDeviceFuncId;
    }

    public void setEventDeviceFuncId(int eventDeviceFuncId) {
        mEventDeviceFuncId = eventDeviceFuncId;
    }

    public int getDeviceId() {
        return mDeviceId;
    }

    public void setDeviceId(int deviceId) {
        mDeviceId = deviceId;
    }

    public int getFunctionId() {
        return mFunctionId;
    }

    public void setFunctionId(int functionId) {
        mFunctionId = functionId;
    }

    public int getFunctionDuration() {
        return mFunctionDuration;
    }

    public void setFunctionDuration(int functionDuration) {
        mFunctionDuration = functionDuration;
    }

    public int getCoordinateX() {
        return mCoordinateX;
    }

    public void setCoordinateX(int coordinateX) {
        mCoordinateX = coordinateX;
    }

    public int getCoordinateY() {
        return mCoordinateY;
    }

    public void setCoordinateY(int coordinateY) {
        mCoordinateY = coordinateY;
    }
}
