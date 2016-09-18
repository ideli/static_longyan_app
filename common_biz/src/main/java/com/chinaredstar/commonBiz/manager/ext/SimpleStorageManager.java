package com.chinaredstar.commonBiz.manager.ext;

import com.chinaredstar.commonBiz.bean.Storage;
import com.chinaredstar.commonBiz.manager.StorageManager;
import com.xiwa.base.manager.AbstractBasicManager;

/**
 * Created by Administrator on 2015/3/26.
 */
public class SimpleStorageManager extends AbstractBasicManager implements StorageManager {

    public SimpleStorageManager() {
        super(Storage.class);
    }

    private String storagePath;
    private String tempPath;

    /**
     * 获取文件存储路径
     * @return
     */
    @Override
    public String getApplicationStoreDataPath() {
        return this.getStoragePath();
    }

    /**
     * 获取临时文件存储路径
     * @return
     */
    @Override
    public String getApplicationTempDataPath() {
        return this.getTempPath();
    }

    //=======================================geter and seter================================================
    public String getStoragePath() {
        return storagePath;
    }

    public void setStoragePath(String storagePath) {
        this.storagePath = storagePath;
    }

    public String getTempPath() {
        return tempPath;
    }

    public void setTempPath(String tempPath) {
        this.tempPath = tempPath;
    }

}
