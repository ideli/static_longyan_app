package com.lanchui.commonBiz.manager;

import com.xiwa.base.manager.BasicManager;

/**
 * Created by yueyq on 2015/3/26.
 */
public interface StorageManager extends BasicManager {

    /**
     * 获取文件存储路径
     * @return
     */
    public String getApplicationStoreDataPath();

    /**
     * 获取临时文件存储路径
     * @return
     */
    public String getApplicationTempDataPath();

}
