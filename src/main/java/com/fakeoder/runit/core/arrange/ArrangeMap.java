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
    private Map<String,List<ArrangerRule>> arrangerMap;

    public List<ArrangerRule> getArrangeRulesByPre(String preId){
        List<ArrangerRule> rules = new ArrayList<>();
        for(Map.Entry<String,List<ArrangerRule>> entry : arrangerMap.entrySet()){
            if(entry.getKey().toString().startsWith(preId+"-")){
                rules.addAll(entry.getValue());
            }
        }
        return rules;
    }


}
