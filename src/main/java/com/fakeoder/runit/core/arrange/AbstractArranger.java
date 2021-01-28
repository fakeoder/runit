package com.fakeoder.runit.core.arrange;

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

}
