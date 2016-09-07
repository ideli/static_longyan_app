package com.lanchui.commonBiz.manager.ext;

import com.lanchui.commonBiz.bean.DispatchCommunityRoomStatus;
import com.lanchui.commonBiz.manager.DispatchCommunityRoomStatusManager;
import com.xiwa.base.manager.AbstractBasicManager;

/**
 * Created by Administrator on 2015/8/26.
 */
public class SimpleDispatchCommunityRoomStatusManager extends AbstractBasicManager implements DispatchCommunityRoomStatusManager {
    public SimpleDispatchCommunityRoomStatusManager() {
        super(DispatchCommunityRoomStatus.class);
    }
}
