package com.chinaredstar.commonBiz.bean;

import com.xiwa.base.bean.Identified;

import java.util.Date;

/**
 * Created by yueyq on 2015/3/26.
 */
public class Storage implements Identified {

    private int id;
    private String storeName;//    店铺名称
    private String fileType;//    文件类型
    private String objectId;
    private String objectIdentified;
    private String fileName;//    文件名
    private String description; //  文件描述
    private Date createDate;//    创建日期
    private String fileLength;//   文件大小
    private String storageFolderId; //  文件夹ID
    private String serialNumber;//    序列号
    private String width;//    宽度
    private String height;//   高度
    private String icon;//    图标
    private String objectDesc;
    private String belongedId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getObjectIdentified() {
        return objectIdentified;
    }

    public void setObjectIdentified(String objectIdentified) {
        this.objectIdentified = objectIdentified;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getObjectDesc() {
        return objectDesc;
    }

    public void setObjectDesc(String objectDesc) {
        this.objectDesc = objectDesc;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getFileLength() {
        return fileLength;
    }

    public void setFileLength(String fileLength) {
        this.fileLength = fileLength;
    }

    public String getStorageFolderId() {
        return storageFolderId;
    }

    public void setStorageFolderId(String storageFolderId) {
        this.storageFolderId = storageFolderId;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getBelongedId() {
        return belongedId;
    }

    public void setBelongedId(String belongedId) {
        this.belongedId = belongedId;
    }
}
