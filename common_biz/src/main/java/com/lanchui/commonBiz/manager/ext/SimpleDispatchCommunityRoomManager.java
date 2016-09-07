package com.lanchui.commonBiz.manager.ext;

import com.lanchui.commonBiz.bean.DispatchCommunityRoom;
import com.lanchui.commonBiz.manager.DispatchCommunityRoomManager;
import com.xiwa.base.manager.AbstractBasicManager;

/**
 * Created by wangj on 2015/7/21.
 */
public class SimpleDispatchCommunityRoomManager extends AbstractBasicManager implements DispatchCommunityRoomManager {
    public SimpleDispatchCommunityRoomManager() {
        super(DispatchCommunityRoom.class);
    }
}
