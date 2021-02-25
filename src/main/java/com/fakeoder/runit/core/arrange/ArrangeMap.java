package com.fakeoder.runit.core.arrange;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * load config and build map
 * @author zhuo
 */
public class ArrangeMap {
    private final static String DELIMITER = "@";

    private List<Flow> flow;
    /**
     * key is from-to
     */
    private Map<String,ArrangerRule> arrangerMap;

    public List<ArrangerRule> getArrangeRulesByPre(String preId){
        List<ArrangerRule> rules = new ArrayList<>();
        for(Map.Entry<String,ArrangerRule> entry : arrangerMap.entrySet()){
            if(entry.getKey().startsWith(preId+DELIMITER)){
                rules.add(entry.getValue());
            }
        }
        return rules;
    }


    public ArrangerRule getArrangeRuleByKey(String key){
        return arrangerMap.get(key);
    }


    public List<String> getPreActionIds(String to) {
        int size = to.length();
        List<String> ids = new ArrayList<>();
        for(String key : arrangerMap.keySet()){
            if(key.endsWith(DELIMITER+to)){
                ids.add(key.substring(0, key.length()-size-1));
            }
        }
        return ids;
    }

    public List<String> getRunnableActionIds(String from) {
        int size = from.length();
        List<String> ids = new ArrayList<>();
        for(String key : arrangerMap.keySet()){
            if(key.startsWith(from+DELIMITER)){
                ids.add(key.substring(size+1));
            }
        }
        return ids;
    }

    public List<Flow> getFlow() {
        return flow;
    }

    public void setFlow(List<Flow> flow) {
        this.flow = flow;
        flow2Map();
    }

    private void flow2Map() {
        arrangerMap = new HashMap<>(32);
        this.flow.forEach(f->{
            ArrangerRule rule = new ArrangerRule(f.from,f.to, f.conditionExpression);
            arrangerMap.put(String.join(DELIMITER,f.from,f.to),rule);
        });
    }

    public static class Flow{
        private String from;
        private String to;
        private String conditionExpression;

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public String getConditionExpression() {
            return conditionExpression;
        }

        public void setConditionExpression(String conditionExpression) {
            this.conditionExpression = conditionExpression;
        }
    }
}
