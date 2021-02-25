package com.fakeoder.runit.core.action;

import com.fakeoder.runit.core.processor.ExceptionProcessor;
import com.fakeoder.runit.core.processor.InitDataProcessor;
import com.fakeoder.runit.core.processor.TimeoutProcessor;

import java.util.Locale;
import java.util.Map;

/**
 * abstract action define
 * @author zhuo
 */
public class Action {

    /**
     * action id
     */
    protected String id;

    protected String clazz;

    /**
     * params
     */
    protected Map<String,Object> params;

    /**
     * action result
     */
    protected ActionResult actionResult;

    /**
     * action state
     * @see com.fakeoder.runit.core.action.State
     */
    protected State state = State.NOT_START;

    /**
     * pre actions result condition to know how to run this action
     * @see com.fakeoder.runit.core.action.PreCondition
     */
    protected PreCondition preCondition;

    protected String preConditionStr;

    /**
     * timeout
     */
    protected int timeout;

    /**
     * required parameters init processor
     */
    protected InitDataProcessor initDataProcessor;

    protected InitDataProcessor initDataProcessorClass;

    /**
     * what should do when timeout
     */
    protected TimeoutProcessor timeoutProcessor;

    protected String timeoutProcessorClass;

    /**
     * what should do when exception appears
     */
    protected ExceptionProcessor exceptionProcessor;

    protected String exceptionProcessorClass;



    /**
     * main logic
     * @return JSON String
     * @throws Exception
     */
    public ActionResult run(){
        throw new UnsupportedOperationException("not support! no ");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public PreCondition getPreCondition() {
        return preCondition;
    }

    public void setPreCondition(PreCondition preCondition) {
        this.preCondition = preCondition;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public InitDataProcessor getInitDataProcessor() {
        return initDataProcessor;
    }

    public void setInitDataProcessor(InitDataProcessor initDataProcessor) {
        this.initDataProcessor = initDataProcessor;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public TimeoutProcessor getTimeoutProcessor() {
        return timeoutProcessor;
    }

    public void setTimeoutProcessor(TimeoutProcessor timeoutProcessor) {
        this.timeoutProcessor = timeoutProcessor;
    }

    public ExceptionProcessor getExceptionProcessor() {
        return exceptionProcessor;
    }

    public void setExceptionProcessor(ExceptionProcessor exceptionProcessor) {
        this.exceptionProcessor = exceptionProcessor;
    }

    public ActionResult getActionResult() {
        return actionResult;
    }

    public void setActionResult(ActionResult actionResult) {
        this.actionResult = actionResult;
    }

    public InitDataProcessor getInitDataProcessorClass() {
        return initDataProcessorClass;
    }

    public void setInitDataProcessorClass(InitDataProcessor initDataProcessorClass) {
        this.initDataProcessorClass = initDataProcessorClass;
    }

    public String getTimeoutProcessorClass() {
        return timeoutProcessorClass;
    }

    public void setTimeoutProcessorClass(String timeoutProcessorClass) {
        this.timeoutProcessorClass = timeoutProcessorClass;
    }

    public String getExceptionProcessorClass() {
        return exceptionProcessorClass;
    }

    public void setExceptionProcessorClass(String exceptionProcessorClass) {
        this.exceptionProcessorClass = exceptionProcessorClass;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getPreConditionStr() {
        return preConditionStr;
    }

    public void setPreConditionStr(String preConditionStr) {
        this.preConditionStr = preConditionStr;
        this.preCondition = PreCondition.valueOf(preConditionStr.toUpperCase(Locale.ROOT));
    }

    public static Action getActionInstance(String clazz){
        try {
            Class cls = Class.forName(clazz.replace(".class",""));
            return (Action) cls.newInstance();
        } catch (ClassNotFoundException| IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    public void setRunningStatus(){
        this.setState(State.RUNNING);
    }

    public void setFinishedStatus(){
        this.setState(State.FINISHED);
    }

    public void setErrorStatus(){
        this.setState(State.ERROR);
    }
}
