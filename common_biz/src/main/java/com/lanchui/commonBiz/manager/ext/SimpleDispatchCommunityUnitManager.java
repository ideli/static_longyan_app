package com.lanchui.commonBiz.manager.ext;

import com.lanchui.commonBiz.bean.DispatchCommunityUnit;
import com.lanchui.commonBiz.manager.DispatchCommunityUnitManager;
import com.xiwa.base.manager.AbstractBasicManager;

/**
 * Created by wangj on 2015/7/21.
 */
public class SimpleDispatchCommunityUnitManager extends AbstractBasicManager implements DispatchCommunityUnitManager {

    public SimpleDispatchCommunityUnitManager() {
        super(DispatchCommunityUnit.class);
    }
}
