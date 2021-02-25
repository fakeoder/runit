package com.fakeoder.runit.core.arrange;

import com.fakeoder.evall.core.Expression;

import java.util.List;
import java.util.Map;

/**
 * arrange all actions to run with defined sequence
 * @author zhuo
 */
public class Arranger {

    private static final String DELIMITER = "@";
    /**
     * the map to guide how to arrange
     */
    protected ArrangeMap arrangeMap;

    public ArrangeMap getArrangeMap() {
        return arrangeMap;
    }

    public void setArrangeMap(ArrangeMap arrangeMap) {
        this.arrangeMap = arrangeMap;
    }

    /**
     * under the map, judge which action can be run
     * @param from
     * @return List<String> actions' id
     */
    public List<String> getRunnableActionIds(String from){
        return arrangeMap.getRunnableActionIds(from);
    }

    /**
     * get preActionIds, judge which action can be run
     * @param id
     * @return List<String> actions' id
     */
    public List<String> getPreActionIds(String id){
        return arrangeMap.getPreActionIds(id);
    }

    /**
     * get arrange rule by from and to actions
     * @param from
     * @param to
     * @return
     */
    public ArrangerRule getArrangeRule(String from,String to){
        String key = String.join(DELIMITER,from,to);
        return arrangeMap.getArrangeRuleByKey(key);
    }

    /**
     * judge if result can pass th rule
     *
     * @return
     */
    public boolean canArrangePass(String from, String to, Map<String,Object> context){
        ArrangerRule arrangerRule = getArrangeRule(from,to);
        String condition = arrangerRule.getPassCondition();
        Object result  = Expression.eval(condition,context);
        return Boolean.valueOf(result.toString());
    }

    /**
     * @param preId
     * @return
     */
    public List<ArrangerRule> getArrangeRulesByPre(String preId){
        return arrangeMap.getArrangeRulesByPre(preId);
    }
}
