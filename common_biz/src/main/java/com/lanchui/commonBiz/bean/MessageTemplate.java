package com.lanchui.commonBiz.bean;

import com.xiwa.base.bean.Identified;

/**
 * 发送短消息模板类
 * Created by bingcheng on 2015/3/23.
 */
public class MessageTemplate implements Identified{

    //实现Identified并且绑定到表xiwa_message_template
    private int id;
    private  String name;//名称
    private  String description;//描述
    private  String content;//内容
    private  String title;//标题
    private  String typeField;//邮件=mail，短消息=sms，微信=wx
    private  boolean active;//是否激活
    private  String alias;//别名，全局唯一
    private  String templateFileName;//模板名称

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTypeField() {
        return typeField;
    }

    public void setTypeField(String typeField) {
        this.typeField = typeField;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getTemplateFileName() {
        return templateFileName;
    }

    public void setTemplateFileName(String templateFileName) {
        this.templateFileName = templateFileName;
    }
}
