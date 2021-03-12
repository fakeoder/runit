package com.fakeoder.runit.core.action;

/**
 * action running status
 * @author zhuo
 */
public enum State {
    NOT_START(0),
    RUNNING(1),
    FINISHED(2),
    ERROR(3);

    private int value;
    State(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }

}
