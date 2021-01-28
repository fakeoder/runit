package com.fakeoder.runit.core.action;

/**
 * @author zhuo
 */
public abstract class AbstractAction<T> {

    protected State state = State.NOT_START;

    protected AbstractAction pre;

    protected AbstractAction next;

    /**
     * run from pre, now and next actions
     * @return T
     */
    public T execute() {
        //TODO call doExecute
        return null;
    }

    /**
     * main execute method
     * @return T
     */
    abstract T doExecute();

    /**
     * async
     * @return
     */
    abstract T doExecuteAsync();

    /**
     * if action is sync
     * @return
     */
    abstract boolean isSync();


    /**
     * callback when finished
     */
    abstract void onFinished();
}
