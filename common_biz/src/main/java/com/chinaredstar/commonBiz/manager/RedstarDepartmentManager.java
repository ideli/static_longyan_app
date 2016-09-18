package com.chinaredstar.commonBiz.manager;

import com.chinaredstar.commonBiz.bean.RedstarDepartment;
import com.xiwa.base.manager.BasicManager;
import com.xiwa.base.manager.ManagerException;

/**
 * Created by usagizhang on 16-5-2.
 */
public interface RedstarDepartmentManager extends BasicManager {
    public RedstarDepartment getDepartmentByCode(String code) throws ManagerException;
}
