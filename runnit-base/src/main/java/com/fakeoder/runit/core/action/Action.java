package com.fakeoder.runit.core.action;

import com.fakeoder.evall.core.Expression;
import com.fakeoder.runit.core.processor.ExceptionProcessor;
import com.fakeoder.runit.core.processor.InitDataProcessor;
import com.fakeoder.runit.core.processor.PostDataProcessor;
import com.fakeoder.runit.core.processor.TimeoutProcessor;
import org.apache.log4j.Logger;

import java.util.Locale;
import java.util.Map;

/**
 * abstract action define
 * @author zhuo
 */
public class Action {
    private static final Logger log = Logger.getLogger(Action.class);

    /**
     * action id
     */
    protected String id;

    protected String clazz;

    protected boolean isDaemon;

    protected Map<String,Object> global;

    protected Map<String,Object> context;


    /**
     * action result
     */
    protected ActionResult actionResult;

    /**
     * action state
     * @see State
     */
    protected State state = State.NOT_START;

    /**
     * pre actions result condition to know how to run this action
     * @see PreCondition
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

    protected String initDataProcessorClass;

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
     * required parameters postData processor
     */
    protected PostDataProcessor postDataProcessor;

    protected String postDataProcessorClass;


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

    public boolean isDaemon() {
        return isDaemon;
    }

    public void setDaemon(boolean daemon) {
        isDaemon = daemon;
    }

    public Map<String, Object> getContext() {
        return context;
    }

    public void setContext(Map<String, Object> context) {
        this.context = context;
    }

    public Map<String, Object> getGlobal() {
        return global;
    }

    public void setGlobal(Map<String, Object> global) {
        this.global = global;
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

    public PostDataProcessor getPostDataProcessor() {
        return postDataProcessor;
    }

    public void setPostDataProcessor(PostDataProcessor postDataProcessor) {
        this.postDataProcessor = postDataProcessor;
    }

    public String getPostDataProcessorClass() {
        return postDataProcessorClass;
    }

    public void setPostDataProcessorClass(String postDataProcessorClass,String expression) {
        this.postDataProcessorClass = postDataProcessorClass;
        try {
            this.postDataProcessor = (PostDataProcessor) Class.forName(this.postDataProcessorClass).newInstance();
            this.postDataProcessor.setExpression(expression);
        } catch (InstantiationException|IllegalAccessException|ClassNotFoundException e) {
            log.error(e.toString());
        }
    }

    public ActionResult getActionResult() {
        return actionResult;
    }

    public void setActionResult(ActionResult actionResult) {
        this.actionResult = actionResult;
    }

    public InitDataProcessor getInitDataProcessorClass() {
        return initDataProcessor;
    }

    public void setInitDataProcessorClass(String initDataProcessorClass, String expression) {
        this.initDataProcessorClass = initDataProcessorClass;
        try {
            this.initDataProcessor = (InitDataProcessor) Class.forName(this.initDataProcessorClass).newInstance();
            this.initDataProcessor.setInitExpression(expression);
        } catch (InstantiationException|IllegalAccessException|ClassNotFoundException e) {
            log.error(e.toString());
        }
    }

    public String getTimeoutProcessorClass() {
        return timeoutProcessorClass;
    }

    public void setTimeoutProcessorClass(String timeoutProcessorClass) {
        this.timeoutProcessorClass = timeoutProcessorClass;
        try {
            this.timeoutProcessor = (TimeoutProcessor) Class.forName(this.timeoutProcessorClass).newInstance();
        } catch (InstantiationException|IllegalAccessException|ClassNotFoundException e) {
            log.error(e.toString());
        }
    }

    public String getExceptionProcessorClass() {
        return exceptionProcessorClass;
    }

    public void setExceptionProcessorClass(String exceptionProcessorClass){
        this.exceptionProcessorClass = exceptionProcessorClass;
        try {
            this.exceptionProcessor = (ExceptionProcessor) Class.forName(this.exceptionProcessorClass).newInstance();
        } catch (InstantiationException|IllegalAccessException|ClassNotFoundException e) {
            log.error(e.toString());
        }
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

    public void init() {
        for(Map.Entry entry : context.entrySet()){
            String value = entry.getValue().toString();
            Object newValue = Expression.eval(value,this.global);
            entry.setValue(newValue);
        }
        for(Map.Entry<String,Object> entry : this.global.entrySet()){
            this.context.putIfAbsent(entry.getKey(),entry.getValue());
        }
        this.initDataProcessor.init(context);
    }

    //todo give some method to release resource, like "onTaskFinished"
}
