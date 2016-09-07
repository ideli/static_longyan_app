package com.lanchui.commonBiz.manager.ext;

import com.lanchui.commonBiz.bean.DispatchMerchant;
import com.lanchui.commonBiz.manager.DispatchMerchantManager;
import com.xiwa.base.manager.AbstractBasicManager;

/**
 * Created by wangj on 2015/5/4.
 */
public class SimpleDispatchMerchantManager extends AbstractBasicManager implements DispatchMerchantManager {

    public SimpleDispatchMerchantManager() {
        super(DispatchMerchant.class);
    }
}
