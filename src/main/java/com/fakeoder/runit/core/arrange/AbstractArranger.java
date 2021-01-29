package com.fakeoder.runit.core.arrange;

import com.alibaba.fastjson.JSONObject;
import com.fakeoder.runit.core.action.ActionResult;

import java.util.List;

/**
 * arrange all actions to run with defined sequence
 * @author zhuo
 */
public abstract class AbstractArranger {

    /**
     * the map to guide how to arrange
     */
    private ArrangeMap arrangeMap;

    /**
     * under the map, judge which action can be run
     * @param result
     * @return List<String> actions' id
     */
    public List<String> getRunnableActionIds(ActionResult result){

        return null;
    }

    /**
     * get preActionIds, judge which action can be run
     * @param id
     * @return List<String> actions' id
     */
    public List<String> getPreActionIds(String id){

        return null;
    }

    /**
     * get arrange rule by from and to actions
     * @param from
     * @param to
     * @return
     */
    public ArrangerRule getArrangeRule(String from,String to){
        String key = String.join(from,to,"-");
        return null;
    }

    /**
     * judge if result can pass th rule
     * @param result
     * @return
     */
    public boolean canArrangePass(String from, String to, String result){
        ArrangerRule arrangerRule = getArrangeRule(from,to);
        //TODO condition build to a class
        String condition = arrangerRule.getPassCondition();

        JSONObject resJson = JSONObject.parseObject(result);


        return false;
    }

    public List<ArrangerRule> getArrangeRulesByPre(String preId){
        return arrangeMap.getArrangeRulesByPre(preId);
    }
}
