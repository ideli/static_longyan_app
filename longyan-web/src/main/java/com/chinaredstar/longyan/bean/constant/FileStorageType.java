package com.chinaredstar.longyan.bean.constant;

import com.xiwa.base.util.StringUtil;

/**
 * Created by wangj on 2015/4/16.
 */
public enum FileStorageType {

    Temp("temp"),//临时存储
    Normal("normal");//正常存储

    private String type;

    private FileStorageType(String type) {
        this.type = type;
    }

    /**
     * 获取枚举值
     *
     * @param type
     * @return
     */
    public static final FileStorageType getFileStorageType(String type) {
        if (StringUtil.isValid(type)) {
            FileStorageType[] fsTypes = FileStorageType.values();
            for (FileStorageType filetype : fsTypes) {
                if (filetype.getType().equals(type)) {
                    return filetype;
                }
            }
        }
        return null;
    }

    public String getType() {
        return type;
    }
}
