package com.chinaredstar.nvwaBiz.util;

import com.chinaredstar.commonBiz.bean.RedstarShoppingMall;
import com.chinaredstar.commonBiz.manager.DispatchDriver;
import com.chinaredstar.nvwaBiz.bean.NvwaDepartment;
import com.chinaredstar.nvwaBiz.bean.NvwaEmployee;
import com.chinaredstar.nvwaBiz.manager.NvwaDriver;
import com.xiwa.base.manager.ManagerException;
import com.xiwa.base.util.CollectionUtil;
import com.xiwa.base.util.StringUtil;

import java.util.List;

/**
 * Created by usagizhang on 16/9/26.
 */
public class EmployeeUtil {

    /**
     * 获取员工所属商场对象
     *
     * @param employee
     * @param dispatchDriver
     * @param nvwaDriver
     * @return
     */
    public static RedstarShoppingMall getMall(NvwaEmployee employee, DispatchDriver dispatchDriver, NvwaDriver nvwaDriver) {
        String mallCode = getMallCode(employee, nvwaDriver);
        if (StringUtil.isValid(mallCode)) {
            try {
                List<RedstarShoppingMall> mallList = dispatchDriver.getRedstarShoppingMallManager().getBeanListByColumn("mallCode", mallCode);
                if (CollectionUtil.isValid(mallList)) {
                    return mallList.get(0);
                }
            } catch (ManagerException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取员工所属商场Code
     *
     * @param employee
     * @return
     */
    public static String getMallCode(NvwaEmployee employee, NvwaDriver nvwaDriver) {
        int departmentId = employee.getDepartmentId();
        try {
            if (departmentId > 0) {
                List<NvwaDepartment> departmentList = nvwaDriver.getNvwaDepartmentManager().getBeanListByColumn("id", departmentId);
                if (CollectionUtil.isValid(departmentList)) {
                    NvwaDepartment nvwaDepartment = departmentList.get(0);
                    return getMallDepartment(nvwaDepartment, nvwaDriver);
                }
            }
        } catch (ManagerException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 遍历出商场的组织架构
     *
     * @param department
     * @param nvwaDriver
     * @return
     */
    public static String getMallDepartment(NvwaDepartment department, NvwaDriver nvwaDriver) throws ManagerException {
        if (department.getName().trim().endsWith("商场")) {
            return department.getDepartmentCode();
        } else if (department != null) {
            //获取父级数据
            List<NvwaDepartment> departmentList = nvwaDriver.getNvwaDepartmentManager().getBeanListByColumn("id", department.getParentId());
            if (CollectionUtil.isValid(departmentList)) {
                NvwaDepartment nvwaDepartment = departmentList.get(0);
                return getMallDepartment(nvwaDepartment, nvwaDriver);
            }
        }
        return null;
    }
}
