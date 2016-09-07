package com.chinaredstar.longyan.model;

import java.io.Serializable;
import java.util.Date;

public class RedstarSharedLink  implements Serializable {
    private static final long serialVersionUID = 3444121998131427985L;
    private Integer id;

    private String userId;

    private String userObject;

    private String sharedUrl;

    private Date createDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getUserObject() {
        return userObject;
    }

    public void setUserObject(String userObject) {
        this.userObject = userObject == null ? null : userObject.trim();
    }

    public String getSharedUrl() {
        return sharedUrl;
    }

    public void setSharedUrl(String sharedUrl) {
        this.sharedUrl = sharedUrl == null ? null : sharedUrl.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}