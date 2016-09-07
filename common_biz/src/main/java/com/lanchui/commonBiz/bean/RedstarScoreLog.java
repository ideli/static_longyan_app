package com.lanchui.commonBiz.bean;

import com.xiwa.base.bean.Identified;

import java.util.Date;

/**
 * Created by lenovo on 2016/4/26.
 */
public class RedstarScoreLog implements Identified {

    private int id;

    private String serialnumber;//  序列号

    private Integer userId;//   用户ID

    private String userObject;//    用户类型，写死 employee

    private Date createDate;//  创建时间

    private Integer ruleId;//   规则ID

    private String ruleName;//  规则名称

    private Integer deltaValue;//   变更数值
    
    private String remark;//    备注


    @Override
    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getSerialnumber() {
        return serialnumber;
    }

    public void setSerialnumber(String serialnumber) {
        this.serialnumber = serialnumber;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserObject() {
        return userObject;
    }

    public void setUserObject(String userObject) {
        this.userObject = userObject;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getRuleId() {
        return ruleId;
    }

    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public Integer getDeltaValue() {
        return deltaValue;
    }

    public void setDeltaValue(Integer deltaValue) {
        this.deltaValue = deltaValue;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
