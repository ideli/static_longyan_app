package com.lanchui.commonBiz.bean.work;

import com.xiwa.base.bean.Identified;

/**
 * Created by lenovo on 2016/7/8.
 */
public class RedstarAttendanceDepartment implements Identified {

    /*
    *   `id` int(11) NOT NULL AUTO_INCREMENT,
  `checkpointId` int(11) DEFAULT NULL COMMENT '考勤点ID',
  `checkpointName` varchar(64) DEFAULT NULL COMMENT '考勤点名称',
  `departmentId` int(11) DEFAULT NULL COMMENT '部门ID',
  `departmentName` varchar(64) DEFAULT NULL COMMENT '部门名称',
  `departmentCode` varchar(64) DEFAULT NULL COMMENT '人资部门ID',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',*/

   private  int id;

   private  Integer checkpointId;
   private  Integer departmentId;

   private String checkpointName;
   private String departmentName;
   private String departmentCode;
   private String remark;


    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getCheckpointId() {
        return checkpointId;
    }

    public void setCheckpointId(Integer checkpointId) {
        this.checkpointId = checkpointId;
    }

    public String getCheckpointName() {
        return checkpointName;
    }

    public void setCheckpointName(String checkpointName) {
        this.checkpointName = checkpointName;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
