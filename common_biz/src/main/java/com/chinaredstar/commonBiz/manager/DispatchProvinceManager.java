package com.chinaredstar.commonBiz.manager;

import com.chinaredstar.commonBiz.bean.DispatchProvince;
import com.xiwa.base.manager.BasicManager;
import com.xiwa.base.manager.ManagerException;

/**
 * Created by wangj on 2015/4/21.
 */
public interface DispatchProvinceManager extends BasicManager {
    public DispatchProvince getProvince(String provinceCode) throws ManagerException;
}
