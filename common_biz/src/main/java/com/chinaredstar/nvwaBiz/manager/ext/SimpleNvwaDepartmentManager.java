package com.chinaredstar.nvwaBiz.manager.ext;

import com.chinaredstar.nvwaBiz.bean.NvwaDepartment;
import com.chinaredstar.nvwaBiz.manager.NvwaDepartmentManager;
import com.xiwa.base.manager.AbstractBasicManager;
import com.xiwa.base.manager.ManagerException;
import com.xiwa.base.util.CollectionUtil;

import java.util.List;

/**
 * Created by usagizhang on 16-5-2.
 */
public class SimpleNvwaDepartmentManager extends AbstractBasicManager implements NvwaDepartmentManager {
    public SimpleNvwaDepartmentManager() {
        super(NvwaDepartment.class);
    }

    /**
     * 根据红星内部系统code获取部门信息
     *
     * @param code
     * @return
     * @throws ManagerException
     */
    public NvwaDepartment getDepartmentByCode(String code) throws ManagerException {
        List<NvwaDepartment> list = this.getBeanListByColumn("departmentCode", code);
        if (CollectionUtil.isValid(list)) {
            return list.get(0);
        }
        return null;
    }
}
