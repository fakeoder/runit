package com.fakeoder.runit.action.savetofile;

import com.fakeoder.runit.core.action.Action;
import com.fakeoder.runit.core.action.ActionResult;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * save data to file
 * @author zhuo
 */
public class SaveToFile extends Action {

    private final static String FILE_PATH = "path";
    private final static String FILE_CONTENT = "content";

    @Override
    public ActionResult run() {
        Object filePath = context.get(FILE_PATH);
        if(filePath==null){
            throw new RuntimeException("file path is null");
        }
        FileWriter fw = null;
        try {
            String path = filePath.toString();
            File file = new File(path);
            fw = new FileWriter(file);
            Object content = context.get(FILE_CONTENT);
            if (content == null) {
                content = global.get(FILE_CONTENT);
            }
            fw.write(content.toString());
            fw.flush();
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            if(fw!=null){
                try {
                    fw.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return new ActionResult(this.getId(),"true");
    }
}
