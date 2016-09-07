package com.lanchui.commonBiz.manager.ext;

import com.lanchui.commonBiz.bean.RedstarCommunityBuilding;
import com.lanchui.commonBiz.manager.RedstarCommunityBuildingManager;
import com.xiwa.base.manager.AbstractBasicManager;

/**
 * Created by LeiYun on 2016/7/12.
 */
public class SimpleRedstarCommunityBuildingManager extends AbstractBasicManager implements RedstarCommunityBuildingManager {

    public SimpleRedstarCommunityBuildingManager(){
        super(RedstarCommunityBuilding.class);
    }


}
