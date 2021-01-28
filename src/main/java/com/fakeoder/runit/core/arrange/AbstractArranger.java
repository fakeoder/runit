package com.fakeoder.runit.core.arrange;

import com.fakeoder.runit.core.action.AbstractAction;
import com.fakeoder.runit.core.action.ActionResult;
import com.fakeoder.runit.core.pojo.ArrangeResult;

import java.util.List;
import java.util.Map;

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
    public List<String> getRunnableActionId(ActionResult result){

        return null;
    }

}
