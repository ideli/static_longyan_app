package com.lanchui.commonBiz.manager;

import com.lanchui.commonBiz.bean.RedstarMallEmployee;
import com.xiwa.base.manager.BasicManager;
import com.xiwa.base.manager.ManagerException;

/**
 * Created by lenovo on 2016/4/26.
 */
public interface RedstarMallEmployeeManager extends BasicManager {
    public RedstarMallEmployee getRedstarMallEmployee(int employeeId, int mallId) throws ManagerException;
}
