package com.chinaredstar.commonBiz.manager;

import net.sf.json.JSONObject;

/**
 * Created by usagizhang on 16-5-5.
 */
public interface CacheManager {

    /**
     * 设置缓存
     *
     * @param key
     * @param value
     */
    public void setCache(String key, JSONObject value);

    /**
     * 获取缓存数据
     *
     * @param key
     * @return
     */
    public JSONObject getCache(String key);

    /**
     * 判断是否存在
     *
     * @param key
     * @return
     */
    public boolean isExist(String key);

    /**
     * 删除缓存数据
     *
     * @param key
     */
    public void remove(String key);
}
