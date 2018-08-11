package com.aliang.wenda.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName ViewObject
 * @Author Aliang
 * @Date 2018/8/8 15:29
 * @Version 1.0
 **/
public class ViewObject {

    private Map<String, Object> objs = new HashMap<>();

    public void set(String key, Object value) {
        objs.put(key, value);
    }

    public Object get(String key) {
        return objs.get(key);
    }
}
