package com.lanchui.commonBiz.manager.ext;

import com.lanchui.commonBiz.manager.CacheManager;
import com.xiwa.base.util.StringUtil;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by usagizhang on 16-5-5.
 */
public class SimpleLocalCacheManager implements CacheManager {

    private Map<String, String> cacheMap;

    public SimpleLocalCacheManager() {
        this.cacheMap = new HashMap<String, String>();
    }

    @Override
    public void setCache(String key, JSONObject value) {
        cacheMap.put(key, value.toString());
    }

    @Override
    public JSONObject getCache(String key) {
        if (StringUtil.isValid(key) && cacheMap.containsKey(key)) {
            return JSONObject.fromObject(cacheMap.get(key));
        }
        return null;
    }

    @Override
    public boolean isExist(String key) {
        return StringUtil.isValid(key) && cacheMap.containsKey(key);
    }

    @Override
    public void remove(String key) {
        if (StringUtil.isValid(key) && cacheMap.containsKey(key)) {
            this.cacheMap.remove(key);
        }
    }
}
