package com.nowcoder.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by rainday on 16/6/30.
 */
public class ViewObject {
    private Map<String, Object> objs = new HashMap<String, Object>();
    public void set(String key, Object value) {//注意！这不是常规的get、set方法
        objs.put(key, value);
    }
    public Object get(String key) {
        return objs.get(key);
    }
}
