package com.chinaredstar.commonBiz.bean;

import com.xiwa.base.bean.Identified;

import java.util.Date;

/**
 * Created by wangj on 2015/6/19.
 */
public class RedstarTaskLog implements Identified {

    private int id;

    private String taskClassName;//   工作类名称

    private Date logDatetime;//   日志日期时间

    private String action;//   活动

    private String remark;//   备注

    private Long executeTime;//   执行时间


    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskClassName() {
        return taskClassName;
    }

    public void setTaskClassName(String taskClassName) {
        this.taskClassName = taskClassName;
    }

    public Date getLogDatetime() {
        return logDatetime;
    }

    public void setLogDatetime(Date logDatetime) {
        this.logDatetime = logDatetime;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(Long executeTime) {
        this.executeTime = executeTime;
    }
}
