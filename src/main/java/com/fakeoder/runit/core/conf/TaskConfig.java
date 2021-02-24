package com.fakeoder.runit.core.conf;

import com.fakeoder.runit.core.action.Action;
import com.fakeoder.runit.core.arrange.ArrangerRule;

import java.util.List;
import java.util.Map;

/**
 * @author zhuo
 */
public class TaskConfig {

    private String id;

    private String name;

    private String description;

    private Map<String,String> context;

    private List<Action> actions;

    private Map<String, ArrangerRule> arrangerMap;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, String> getContext() {
        return context;
    }

    public void setContext(Map<String, String> context) {
        this.context = context;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public Map<String, ArrangerRule> getArrangerMap() {
        return arrangerMap;
    }

    public void setArrangerMap(Map<String, ArrangerRule> arrangerMap) {
        this.arrangerMap = arrangerMap;
    }
}
