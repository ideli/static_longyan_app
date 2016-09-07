package com.greatbee.bean;

/**
 * 存储配置
 * Created by wangj on 15-7-30.
 */
public class StorageConfig {
    private String storage_config_store_path;
    private String storage_config_store_temp_path;

    public String getStorage_config_store_path() {
        return storage_config_store_path;
    }

    public void setStorage_config_store_path(String storage_config_store_path) {
        this.storage_config_store_path = storage_config_store_path;
    }

    public String getStorage_config_store_temp_path() {
        return storage_config_store_temp_path;
    }

    public void setStorage_config_store_temp_path(String storage_config_store_temp_path) {
        this.storage_config_store_temp_path = storage_config_store_temp_path;
    }
}
