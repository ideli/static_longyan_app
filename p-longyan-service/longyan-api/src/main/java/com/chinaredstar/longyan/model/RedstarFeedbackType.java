package com.chinaredstar.longyan.model;


import java.io.Serializable;


public class RedstarFeedbackType implements Serializable {
    private static final long serialVersionUID = -3483503248872234987L;

    private Integer id;

    private String name;

    private String alias;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias == null ? null : alias.trim();
    }
}