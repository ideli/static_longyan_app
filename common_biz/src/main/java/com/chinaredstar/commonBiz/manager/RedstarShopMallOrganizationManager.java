package com.chinaredstar.commonBiz.manager;

import com.chinaredstar.commonBiz.bean.RedstarShopMallOrganization;
import com.xiwa.base.manager.BasicManager;

import java.util.List;

/**
 * Created by lenovo on 2016/4/27.
 */
public interface RedstarShopMallOrganizationManager extends BasicManager {
    //public List getBeanList(Collection operationBeanIds,String queryColumn, String orderColumn, Boolean desc) throws ManagerException;

    public List<RedstarShopMallOrganization> getChildList (Class c,String column,Boolean desc,String type);

    public Object[]  getParentId(int shopId);
}
