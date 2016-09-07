package com.chinaredstar.longyan.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.JOINED)
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = -1L;

    @Id
    @GeneratedValue(generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(columnDefinition = "BIGINT")
    private int id;

    @CreatedBy
    private String createEmployeeXingMing;

    @CreatedDate
//    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private Date createDate = DateTime.now().toDate();

    @LastModifiedBy
    private String updateEmployeeXingMing;

    @LastModifiedDate
    private Date updateDate = DateTime.now().toDate();

    @Column(columnDefinition = "TINYINT(2)")
    private boolean showData = true;

    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreateEmployeeXingMing() {
        return createEmployeeXingMing;
    }

    public void setCreateEmployeeXingMing(String createEmployeeXingMing) {
        this.createEmployeeXingMing = createEmployeeXingMing;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateEmployeeXingMing() {
        return updateEmployeeXingMing;
    }

    public void setUpdateEmployeeXingMing(String updateEmployeeXingMing) {
        this.updateEmployeeXingMing = updateEmployeeXingMing;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public boolean isShowData() {
        return showData;
    }

    public void setShowData(boolean showData) {
        this.showData = showData;
    }
}