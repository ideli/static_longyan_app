package com.lanchui.commonBiz.bean;

import com.xiwa.base.bean.Identified;

import java.util.Date;

/**
 * Created by lenovo on 2016/7/8.
 */
public class WxUserInfo implements Identified {

    /*
    *   `id` int(11) NOT NULL AUTO_INCREMENT,
  `openId` varchar(255) NOT NULL COMMENT '微信的openId',
 `wxInfo` varchar(1024) NOT NULL COMMENT '微信信息，按照json存储',
 `createDate` datetime DEFAULT NULL COMMENT '创建时间',
 `unionId` varchar(255) NOT NULL COMMENT '微信的uninonId',*/

    private  int id;

    private String openId;//    微信的openId

    private Date createDate;//  创建时间

    private String unionId;//   微信的uninonId

    private String wxInfo;//    微信信息，按照json存储

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getWxInfo() {
        return wxInfo;
    }

    public void setWxInfo(String wxInfo) {
        this.wxInfo = wxInfo;
    }
}
