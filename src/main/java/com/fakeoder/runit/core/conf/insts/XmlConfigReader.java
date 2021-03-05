package com.fakeoder.runit.core.conf.insts;

import com.fakeoder.runit.core.action.Action;
import com.fakeoder.runit.core.arrange.ArrangeMap;
import com.fakeoder.runit.core.arrange.Arranger;
import com.fakeoder.runit.core.conf.ConfigReader;
import com.fakeoder.runit.core.conf.TaskConfig;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * @author zhuo
 */
public class XmlConfigReader extends ConfigReader {

    private final String path;

    public XmlConfigReader(String path){
        this.path = path;
    }

    @Override
    public TaskConfig read() throws URISyntaxException, DocumentException {

        final TaskConfig taskConfig = new TaskConfig();

        //transform uri from classpath
        URI uri = Objects.requireNonNull(XmlConfigReader.class.getClassLoader().getResource(path)).toURI();

        //1.create Reader object
        SAXReader reader = new SAXReader();
        //2.load xml
        Document document = reader.read(new File(uri));
        //3.get root element
        Element rootElement = document.getRootElement();

        //4.fill task attributes
        fillTaskAttribute(rootElement, taskConfig);

        //5.subElements
        fillContext(rootElement.element("context"), taskConfig);
        fillActions(rootElement.element("actions"), taskConfig);
        fillArrange(rootElement.element("arranger"), taskConfig);

        return taskConfig;
    }

    private void fillTaskAttribute(Element element, TaskConfig task) {
        task.setId(element.attributeValue("id"));
        task.setName(element.attributeValue("name"));
        task.setBeginAction(element.attributeValue("beginAction"));
        task.setDescription(element.attributeValue("description"));
    }

    private void fillContext(Element element, TaskConfig task) {
        final Map<String,Object> context = new HashMap<>(32);
        element.elements().forEach(ele-> context.put(ele.element("key").getStringValue(), ele.element("value").getStringValue()));
        task.setContext(context);
    }

    private void fillActions(Element element, TaskConfig taskConfig) {
        final List<Action> actions = new ArrayList<>();
        element.elements().forEach(ele->{
            String actionClass = ele.attributeValue("class");
            final Action action = Action.getActionInstance(actionClass);

            //fill attribute
            action.setId(ele.attributeValue("id"));
            action.setPreConditionStr(ele.attributeValue("preCondition"));
            action.setTimeout(Integer.parseInt(ele.attributeValue("timeout")));
            action.setClazz(ele.attributeValue("class"));

            //fill init
            fillActionContext(ele.element("initParameters"), action);

            //fill timeout processor
            fillActionTimeoutProcessor(ele.element("timeoutProcessor"), action);

            //fill exception processor
            fillActionExceptionProcessor(ele.element("exceptionProcessor"), action);

            //fill initData processor
            fillActionInitDataProcessor(ele.element("initDataProcessor"), action);

            //fill initData processor
            fillActionPostDataProcessor(ele.element("postDataProcessor"), action);

            actions.add(action);
        });
        taskConfig.setActions(actions);
    }

    private void fillActionContext(Element element, Action action) {
        Map<String,Object> context = new HashMap<>(32);
        element.elements().forEach(ele->{
            context.put(ele.element("name").getStringValue(),ele.element("expression").getStringValue());
        });
        action.setContext(context);
    }

    private void fillActionTimeoutProcessor(Element element, Action action) {
        action.setTimeoutProcessorClass(element.attributeValue("class"));
    }

    private void fillActionExceptionProcessor(Element element, Action action) {
        action.setExceptionProcessorClass(element.attributeValue("class"));
    }

    private void fillActionInitDataProcessor(Element element, Action action) {
        action.setInitDataProcessorClass(element.attributeValue("class"), element.attributeValue("expression"));
    }

    private void fillActionPostDataProcessor(Element element, Action action) {
        action.setPostDataProcessorClass(element.attributeValue("class"),element.attributeValue("expression"));
    }

    private void fillArrange(Element element, TaskConfig taskConfig) {
        Arranger arranger = new Arranger();
        final List<ArrangeMap.Flow> flows = new ArrayList<>();
        element.elements().forEach(ele->{
            final ArrangeMap.Flow flow = new ArrangeMap.Flow();

            flow.setFrom(ele.attributeValue("from"));
            flow.setTo(ele.attributeValue("to"));
            flow.setConditionExpression(ele.element("condition").attributeValue("expression"));

            flows.add(flow);

        });

        ArrangeMap arrangeMap = new ArrangeMap();
        arrangeMap.setFlow(flows);

        arranger.setArrangeMap(arrangeMap);
        taskConfig.setArranger(arranger);
    }
}
