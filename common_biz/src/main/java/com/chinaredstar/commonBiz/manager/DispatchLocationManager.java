package com.chinaredstar.commonBiz.manager;

import com.chinaredstar.commonBiz.bean.DispatchLocation;
import com.xiwa.base.manager.BasicManager;
import com.xiwa.base.manager.ManagerException;

/**
 * Created by wangj on 2015/4/21.
 */
public interface DispatchLocationManager extends BasicManager{
    public DispatchLocation getLoction(String code) throws ManagerException;
}
