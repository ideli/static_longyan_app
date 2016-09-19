package com.chinaredstar.nvwaBiz.manager;

import com.chinaredstar.nvwaBiz.bean.NvwaDepartment;
import com.xiwa.base.manager.BasicManager;
import com.xiwa.base.manager.ManagerException;

/**
 * Created by usagizhang on 16-5-2.
 */
public interface NvwaDepartmentManager extends BasicManager {
    public NvwaDepartment getDepartmentByCode(String code) throws ManagerException;
}
