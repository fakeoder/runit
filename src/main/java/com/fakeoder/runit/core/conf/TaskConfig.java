package com.fakeoder.runit.core.conf;

import com.fakeoder.runit.core.action.Action;
import com.fakeoder.runit.core.arrange.Arranger;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author zhuo
 */
public class TaskConfig {

    private String id;

    private String name;

    private String beginAction;

    private String description;

    private Map<String,Object> context;

    private List<Action> actions;

    private Arranger arranger;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBeginAction(String beginAction) {
        this.beginAction = beginAction;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, Object> getContext() {
        return context;
    }

    public void setContext(Map<String, Object> context) {
        this.context = context;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public Arranger getArranger() {
        return arranger;
    }

    public void setArranger(Arranger arranger) {
        this.arranger = arranger;
    }

    public String getBeginAction() {
        return beginAction;
    }

    public ConcurrentHashMap<String, Action> getActionsAsMap() {
        return new ConcurrentHashMap<>(this.actions.stream().collect(Collectors.toMap(Action::getId, Function.identity(), (key1, key2)->(key1))));
    }
}
