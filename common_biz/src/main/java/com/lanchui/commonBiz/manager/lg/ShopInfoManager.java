package com.lanchui.commonBiz.manager.lg;

import com.lanchui.commonBiz.bean.lg.ShopInfo;
import com.xiwa.base.manager.BasicManager;
import com.xiwa.base.manager.ManagerException;

import java.util.List;

/**
 * Created by lenovo on 2016/7/6.
 */
public interface ShopInfoManager extends BasicManager {
    List<ShopInfo> getShopInfo() throws ManagerException;
}
