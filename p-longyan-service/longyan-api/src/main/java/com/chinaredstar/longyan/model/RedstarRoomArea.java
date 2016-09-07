package com.chinaredstar.longyan.model;

import java.io.Serializable;

public class RedstarRoomArea implements Serializable {
    private static final long serialVersionUID = 5687494506867948470L;

    private Integer id;

    private String remark;

    private String alias;

    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias == null ? null : alias.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}