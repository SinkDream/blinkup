package cn.blinkup.orm.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangguosheng
 * 视图对象
 */
public class View {
    private final String path;

    private final Map<String, Object> model;

    public View(String path){
        this.path = path;
        model = new HashMap<>();
    }

    public View addModel(String key, Object value){
        model.put(key, value);
        return this;
    }

    public String getPath(){
        return path;
    }

    public Map<String, Object> getModel(){
        return model;
    }
}
