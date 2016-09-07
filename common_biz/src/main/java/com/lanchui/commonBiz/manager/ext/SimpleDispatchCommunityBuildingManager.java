package com.lanchui.commonBiz.manager.ext;

import com.lanchui.commonBiz.bean.DispatchCommunityBuilding;
import com.lanchui.commonBiz.manager.DispatchCommunityBuildingManager;
import com.xiwa.base.manager.AbstractBasicManager;

/**
 * Created by wangj on 2015/7/21.
 */
public class SimpleDispatchCommunityBuildingManager extends AbstractBasicManager implements DispatchCommunityBuildingManager {

    public SimpleDispatchCommunityBuildingManager() {
        super(DispatchCommunityBuilding.class);
    }
}
