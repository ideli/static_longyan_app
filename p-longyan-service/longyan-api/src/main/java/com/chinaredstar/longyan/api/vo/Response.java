package com.chinaredstar.longyan.api.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mdc on 2016/7/28.
 */
public class Response implements Serializable {

    private static final long serialVersionUID = -757795784879485443L;
    protected boolean ok = true;
    protected String message;
    protected int code;
    protected Map<String, Object> dataMap = new HashMap();

    public Response() {
    }

    public void addKey(String key, Object value) {
        if (this.dataMap.get(key) != null) {
            this.dataMap.remove(key);
        }

        this.dataMap.put(key, value);
    }

    public void clearContent() {
        this.dataMap.clear();
    }

    public boolean isContainsKey(String key) {
        return this.dataMap.containsKey(key);
    }

    public void removeKey(String key) {
        this.dataMap.remove(key);
    }

    public Map<String, Object> getDataMap() {
        return this.dataMap;
    }

    public void setDataMap(Map<String, Object> dataMap) {
        this.dataMap = dataMap;
    }

    public boolean isOk() {
        return this.ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public String getMessage() {
        return this.message;
    }

    public Object getDataValue(String key) {
        return this.dataMap.get(key);
    }

//    public boolean getBoolean(String key) {
//        Object v = this.getDataValue(key);
//        return v != null && BooleanUtil.toBoolean(v.toString()).booleanValue();
//    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
