package com.chinaredstar.longyan.api;

import com.chinaredstar.longyan.api.vo.Response;

/**
 * Created by mdc on 2016/8/2.
 */
public interface ContactService {
    Response getDepartmentList(String departmentCode);

    Response getEmployeeByName(String name);

    Response getEmployeeInfo(String employeeCode);
}
