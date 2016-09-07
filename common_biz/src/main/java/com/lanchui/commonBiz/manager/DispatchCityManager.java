package com.lanchui.commonBiz.manager;

import com.lanchui.commonBiz.bean.DispatchCity;
import com.xiwa.base.manager.BasicManager;
import com.xiwa.base.manager.ManagerException;

/**
 * Created by wangj on 2015/4/15.
 */
public interface DispatchCityManager extends BasicManager {
    public DispatchCity getCity(String provinceCode) throws ManagerException;
}
