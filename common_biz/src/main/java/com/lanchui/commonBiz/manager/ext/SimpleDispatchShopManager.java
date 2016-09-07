package com.lanchui.commonBiz.manager.ext;

import com.lanchui.commonBiz.bean.DispatchShop;
import com.lanchui.commonBiz.manager.DispatchShopManager;
import com.xiwa.base.manager.AbstractBasicManager;

/**
 * Created by wangj on 2015/9/19.
 */
public class SimpleDispatchShopManager extends AbstractBasicManager implements DispatchShopManager {
    public SimpleDispatchShopManager() {
        super(DispatchShop.class);
    }
}
