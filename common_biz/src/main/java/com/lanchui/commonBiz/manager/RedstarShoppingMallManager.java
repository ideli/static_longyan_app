package com.lanchui.commonBiz.manager;

import com.lanchui.commonBiz.bean.RedstarShoppingMall;
import com.xiwa.base.manager.BasicManager;
import com.xiwa.base.manager.ManagerException;

import java.util.Collection;
import java.util.List;
import java.util.ListResourceBundle;
import java.util.Map;

/**
 * Created by lenovo on 2016/4/26.
 */
public interface RedstarShoppingMallManager extends BasicManager {

    public List getBeanList(Collection operationBeanIds, String queryColumn, String orderColumn, Boolean desc,Map<String,Object> eqMapParams) throws ManagerException;

    public RedstarShoppingMall getShoppingMallByCode(String code) throws ManagerException;
}
