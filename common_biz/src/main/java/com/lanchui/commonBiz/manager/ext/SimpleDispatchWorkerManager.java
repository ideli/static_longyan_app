package com.lanchui.commonBiz.manager.ext;

import com.lanchui.commonBiz.bean.DispatchWorker;
import com.lanchui.commonBiz.manager.DispatchWorkerManager;
import com.xiwa.base.manager.AbstractBasicManager;
import com.xiwa.base.manager.ManagerException;
import com.xiwa.base.util.CollectionUtil;
import com.xiwa.base.util.DataUtil;
import com.xiwa.base.util.StringUtil;

import javax.print.attribute.standard.MediaName;
import java.util.List;

/**
 * Created by wangj on 2015/4/8.
 */
public class SimpleDispatchWorkerManager extends AbstractBasicManager implements DispatchWorkerManager {

    public SimpleDispatchWorkerManager() {
        super(DispatchWorker.class);
    }

    @Override
    public DispatchWorker getWorkerByWeixinID(String weixinId) throws ManagerException {
        //weixinID不为空
        if (StringUtil.isValid(weixinId)) {
            List<DispatchWorker> list = this.getBeanListByColumn("weixinId", weixinId);
            if (CollectionUtil.isValid(list)) {
                return list.get(0);
            }
        } else {
            //weixinID为空
            throw new ManagerException("weixinID is null");
        }
        return null;
    }

    /**
     * 根据showData查询
     *
     * @return
     */
    @Override
    public List<DispatchWorker> getWorkerByShowData() throws ManagerException {

        List<DispatchWorker> list = this.getBeanListByColumn("showData", true);
        if (CollectionUtil.isValid(list)) {
            return list;
        }
        return null;
    }

    /**
     * 更新Worker
     *
     * @param worker
     * @throws ManagerException
     */
    @Override
    public void updateWorkerByConstruction(DispatchWorker worker) throws ManagerException {
        //woker不为null
        if (worker != null) {
            this.updateBean(worker);
        }else {
            //woker为null
            throw new ManagerException("worker is null");
        }
    }

}
