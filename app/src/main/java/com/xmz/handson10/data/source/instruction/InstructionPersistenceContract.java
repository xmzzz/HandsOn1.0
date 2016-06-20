package com.xmz.handson10.data.source.instruction;

/**
 * Created by xmz on 2016/6/20.
 */
public final class InstructionPersistenceContract {

    public InstructionPersistenceContract() {}

    public static abstract class InstructionEntry {
        public static final String TABLE_NAME = "instructions";
        public static final String COLUMN_NAME_INSTRUCTION_ID = "instruction_id";
        public static final String COLUMN_NAME_EVENT_DEVICE_ID = "event_device_id";
        public static final String COLUMN_NAME_EVENT_DEVICE_FUNC_ID = "event_device_func_id";
        public static final String COLUMN_NAME_DEVICE_ID = "device_id";
        public static final String COLUMN_NAME_FUNCTION_ID = "function_id";
        public static final String COLUMN_NAME_FUNCTION_DURATION = "function_duration";
        public static final String COLUMN_NAME_X = "x";
        public static final String COLUMN_NAME_Y = "y";
    }
}
