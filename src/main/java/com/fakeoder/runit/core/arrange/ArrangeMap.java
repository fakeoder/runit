package com.fakeoder.runit.core.arrange;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * load config and build map
 * @author zhuo
 */
public class ArrangeMap {
    /**
     * key is from-to
     */
    private Map<String,ArrangerRule> arrangerMap;

    public List<ArrangerRule> getArrangeRulesByPre(String preId){
        List<ArrangerRule> rules = new ArrayList<>();
        for(Map.Entry<String,ArrangerRule> entry : arrangerMap.entrySet()){
            if(entry.getKey().startsWith(preId+"-")){
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
            if(key.endsWith("-"+to)){
                ids.add(key.substring(0, key.length()-size));
            }
        }
        return ids;
    }

    public List<String> getRunnableActionIds(String from) {
        int size = from.length();
        List<String> ids = new ArrayList<>();
        for(String key : arrangerMap.keySet()){
            if(key.startsWith(from+"-")){
                ids.add(key.substring(size));
            }
        }
        return ids;
    }
}
