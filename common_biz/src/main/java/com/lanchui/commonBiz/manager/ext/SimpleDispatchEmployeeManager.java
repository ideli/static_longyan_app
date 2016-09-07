package com.lanchui.commonBiz.manager.ext;

import com.lanchui.commonBiz.bean.DispatchEmployee;
import com.lanchui.commonBiz.bean.RedstarEmployee;
import com.lanchui.commonBiz.manager.DispatchEmployeeManager;
import com.xiwa.base.bean.PaginationDescribe;
import com.xiwa.base.manager.AbstractBasicManager;
import com.xiwa.base.manager.ManagerException;
import org.apache.poi.ss.formula.functions.T;

/**
 * Created by wangj on 2015/5/19.
 */
public class SimpleDispatchEmployeeManager extends AbstractBasicManager implements DispatchEmployeeManager {
    public SimpleDispatchEmployeeManager() {
        super(DispatchEmployee.class);
    }

    @Override
    public PaginationDescribe getByPages(int page, int pageSize, Class c, String order, boolean desc) throws ManagerException {
        return  this.getIdentifyByPage(page,pageSize,c,order,desc);
    }
}
