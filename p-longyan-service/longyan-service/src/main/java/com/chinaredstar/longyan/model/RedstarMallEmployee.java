package com.chinaredstar.longyan.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "xiwa_redstar_mall_employee")
public class RedstarMallEmployee implements Serializable {
    private static final long serialVersionUID = 2383556705286056016L;
    @Id
    @GeneratedValue(generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(columnDefinition = "BIGINT")
    private Integer id;

    private Integer employeeId;

    private String employeeXingMing;

    private Integer shoppingMallId;

    private String shoppingMallName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeXingMing() {
        return employeeXingMing;
    }

    public void setEmployeeXingMing(String employeeXingMing) {
        this.employeeXingMing = employeeXingMing == null ? null : employeeXingMing.trim();
    }

    public Integer getShoppingMallId() {
        return shoppingMallId;
    }

    public void setShoppingMallId(Integer shoppingMallId) {
        this.shoppingMallId = shoppingMallId;
    }

    public String getShoppingMallName() {
        return shoppingMallName;
    }

    public void setShoppingMallName(String shoppingMallName) {
        this.shoppingMallName = shoppingMallName == null ? null : shoppingMallName.trim();
    }
}