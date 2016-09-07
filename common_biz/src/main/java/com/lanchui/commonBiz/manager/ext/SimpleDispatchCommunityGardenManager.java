package com.lanchui.commonBiz.manager.ext;

import com.lanchui.commonBiz.bean.DispatchCommunityGarden;
import com.lanchui.commonBiz.manager.DispatchCommunityGardenManager;
import com.xiwa.base.manager.AbstractBasicManager;

/**
 * Created by Administrator on 2015/7/21.
 */
public class SimpleDispatchCommunityGardenManager extends AbstractBasicManager implements DispatchCommunityGardenManager {
    public SimpleDispatchCommunityGardenManager() {
        super(DispatchCommunityGarden.class);
    }
}
