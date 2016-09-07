package com.lanchui.commonBiz.manager;

import com.lanchui.commonBiz.bean.DispatchWorker;
import com.xiwa.base.manager.BasicManager;
import com.xiwa.base.manager.ManagerException;

import java.util.List;

/**
 * Created by wangj on 2015/4/8.
 */
public interface DispatchWorkerManager extends BasicManager {

    public DispatchWorker getWorkerByWeixinID(String weixinId) throws ManagerException;
    public List<DispatchWorker> getWorkerByShowData() throws ManagerException;
    public void updateWorkerByConstruction(DispatchWorker worker) throws ManagerException;
}
