package com.chinaredstar.commonBiz.bean;

import com.xiwa.base.bean.Identified;

/**
 * Created by lenovo on 2016/4/26.
 */
public class RedstarMallEmployee implements Identified  {

    private int id;

    private  Integer employeeId;//  员工ID

    private  String employeeXingMing;//  员工姓名

    private  Integer shoppingMallId;//  商场ID

    private  String shoppingMallName;//  商场名称

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getEmployeeXingMing() {
        return employeeXingMing;
    }

    public void setEmployeeXingMing(String employeeXingMing) {
        this.employeeXingMing = employeeXingMing;
    }


    public String getShoppingMallName() {
        return shoppingMallName;
    }

    public void setShoppingMallName(String shoppingMallName) {
        this.shoppingMallName = shoppingMallName;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getShoppingMallId() {
        return shoppingMallId;
    }

    public void setShoppingMallId(Integer shoppingMallId) {
        this.shoppingMallId = shoppingMallId;
    }
}
