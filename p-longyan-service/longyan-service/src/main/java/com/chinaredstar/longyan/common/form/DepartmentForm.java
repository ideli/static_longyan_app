package com.chinaredstar.longyan.common.form;

import java.io.Serializable;

/**
 * Created by leiyun 2016/8/3.
 */
public class DepartmentForm implements Serializable {

    private static final long serialVersionUID = -1949600679194868071L;

    private String departmentCode;//      部门code

    private String name; //     部门名称

    private long number = 0;//     部门总人数

    private boolean hasChild = false;// 是否存在子部门


    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public boolean isHasChild() {
        return hasChild;
    }

    public void setHasChild(boolean hasChild) {
        this.hasChild = hasChild;
    }

}

