package com.lanchui.commonBiz.manager.ext;

import com.lanchui.commonBiz.bean.DispatchShopCategory;
import com.lanchui.commonBiz.manager.DispatchShopCategoryManager;
import com.xiwa.base.manager.AbstractBasicManager;

/**
 * Created by bingcheng on 2015/9/19.
 */
public class SimpleDispatchShopCategoryManager extends AbstractBasicManager implements DispatchShopCategoryManager {

    public SimpleDispatchShopCategoryManager() {
        super(DispatchShopCategory.class);
    }
}
