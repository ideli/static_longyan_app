package com.chinaredstar.longyan.model;


import java.io.Serializable;

public class RedstarRenovationStatus implements Serializable {
    private static final long serialVersionUID = -7994777699159697826L;

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