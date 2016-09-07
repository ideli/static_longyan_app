package com.chinaredstar.longyan.model;


import java.io.Serializable;

public class RedstarRoomLayout implements Serializable {
    private static final long serialVersionUID = 6350347761745829022L;

    private Integer id;

    private String alias;

    private String remark;

    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias == null ? null : alias.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}