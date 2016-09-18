package com.chinaredstar.commonBiz.manager.ext;

import com.chinaredstar.commonBiz.bean.RedstarDepartment;
import com.chinaredstar.commonBiz.manager.RedstarDepartmentManager;
import com.xiwa.base.manager.AbstractBasicManager;
import com.xiwa.base.manager.ManagerException;
import com.xiwa.base.util.CollectionUtil;

import java.util.List;

/**
 * Created by usagizhang on 16-5-2.
 */
public class SimpleRedstarDepartmentManager extends AbstractBasicManager implements RedstarDepartmentManager {
    public SimpleRedstarDepartmentManager() {
        super(RedstarDepartment.class);
    }

    /**
     * 根据红星内部系统code获取部门信息
     *
     * @param code
     * @return
     * @throws ManagerException
     */
    public RedstarDepartment getDepartmentByCode(String code) throws ManagerException {
        List<RedstarDepartment> list = this.getBeanListByColumn("departmentCode", code);
        if (CollectionUtil.isValid(list)) {
            return list.get(0);
        }
        return null;
    }
}
