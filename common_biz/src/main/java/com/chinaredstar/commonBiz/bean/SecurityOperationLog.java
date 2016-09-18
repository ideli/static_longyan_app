package com.chinaredstar.commonBiz.bean;

import com.xiwa.base.bean.Identified;

import java.util.Date;

/**
 * Created by lenovo on 2016/4/21.
 */
public class SecurityOperationLog implements Identified {

/*
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `operator` varchar(32) COLLATE utf8_bin DEFAULT NULL,
    `operatorId` int(11) DEFAULT NULL,
    `operateResource` varchar(256) COLLATE utf8_bin DEFAULT NULL,
    `operateResourceId` varchar(45) COLLATE utf8_bin DEFAULT NULL,
    `content` text COLLATE utf8_bin,
            `createDate` datetime DEFAULT NULL,
            `belongedId` int(11) DEFAULT NULL,
    `remark` text COLLATE utf8_bin,
            `operationTypeField` varchar(45) COLLATE utf8_bin DEFAULT NULL,*/

    private int id;

    private Integer operatorId;//  运营商识别码

    private String operator;//  运营商

    private String operateResource;//   经营资源

    private String content; //  内容

    private String operateResourceId;// 运营资源ID

    private String remark;//  备注

    private Integer belongedId; //

    private String operationTypeField;  //操作类型字段



    private Date createDate;

    @Override
    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOperateResource() {
        return operateResource;
    }

    public void setOperateResource(String operateResource) {
        this.operateResource = operateResource;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOperateResourceId() {
        return operateResourceId;
    }

    public void setOperateResourceId(String operateResourceId) {
        this.operateResourceId = operateResourceId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getBelongedId() {
        return belongedId;
    }

    public void setBelongedId(Integer belongedId) {
        this.belongedId = belongedId;
    }

    public String getOperationTypeField() {
        return operationTypeField;
    }

    public void setOperationTypeField(String operationTypeField) {
        this.operationTypeField = operationTypeField;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }


}