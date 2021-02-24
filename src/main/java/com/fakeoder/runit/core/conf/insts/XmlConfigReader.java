package com.fakeoder.runit.core.conf.insts;

import com.fakeoder.runit.core.action.Action;
import com.fakeoder.runit.core.conf.ConfigReader;
import com.fakeoder.runit.core.conf.TaskConfig;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhuo
 */
public class XmlConfigReader extends ConfigReader {

    private String path;

    public XmlConfigReader(String path){
        this.path = path;
    }

    @Override
    public TaskConfig read() throws URISyntaxException, DocumentException {

        final TaskConfig taskConfig = new TaskConfig();

        //transform uri from classpath
        URI uri = XmlConfigReader.class.getClassLoader().getResource(path).toURI();

        //1.create Reader object
        SAXReader reader = new SAXReader();
        //2.load xml
        Document document = reader.read(new File(uri));
        //3.get root element
        Element rootElement = document.getRootElement();

        //4.fill task attributes
        fillTaskAttribute(rootElement.attributes(), taskConfig);

        //5.subElements
        List<Element> elementList = rootElement.elements();

        elementList.stream().forEach(ele->{
            String name = ele.getName();
            switch (name){
                case "context":
                    fillContext(ele, taskConfig);
                    break;
                case "actions":
                    fillActions(ele, taskConfig);
                    break;
                case "arrange":
                    fillArrange(ele, taskConfig);
                    break;
                default:
                    throw new RuntimeException("no match element: " + name);
            }

        });


        return null;
    }

    private void fillTaskAttribute(List<Attribute> attributes, TaskConfig task) {
        attributes.stream().forEach(attr->{
            String name = attr.getName();
            String value = attr.getValue();
            switch (name){
                case "id":
                    task.setId(value);
                    break;
                case "name":
                    task.setName(value);
                    break;
                case "description":
                    task.setDescription(value);
                    break;
                default:
                    throw new RuntimeException("invalid attribute: " + name);
            }
        });
    }

    private void fillContext(Element element, TaskConfig task) {
        final Map<String,String> context = new HashMap<>(32);
        element.elements().stream().forEach(ele->{
            List<Element> elements = ele.elements();
            Element ea = elements.get(0);
            Element eb = elements.get(1);
            String eaName = ea.getName();
            switch (eaName){
                case "key":
                    context.put(ea.getStringValue(),eb.getStringValue());
                    break;
                case "value":
                    context.put(eb.getStringValue(),ea.getStringValue());
                    break;
                default:
                    throw new UnsupportedOperationException("no match element:" + eaName);

            }
        });
        task.setContext(context);
    }

    private void fillActions(Element element, TaskConfig taskConfig) {
        final List<Action> actions = new ArrayList<>();
        element.elements().stream().forEach(ele->{
            final Action action = new Action();
            //fill attributes

            ele.elements().stream().forEach(e->{
                //fill init

                //fill timeout processor

                //fill expection processor


            });
            actions.add(action);


        });
        taskConfig.setActions(actions);
    }

    private void fillArrange(Element element, TaskConfig taskConfig) {
        final List<Action> actions = new ArrayList<>();
        element.elements().stream().forEach(ele->{
            final Action action = new Action();
            //fill attributes

            ele.elements().stream().forEach(e->{
                //fill init

                //fill timeout processor

                //fill expection processor


            });
            actions.add(action);


        });
        taskConfig.setActions(actions);
    }




}
