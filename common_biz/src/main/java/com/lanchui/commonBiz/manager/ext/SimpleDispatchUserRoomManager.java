package com.lanchui.commonBiz.manager.ext;

import com.lanchui.commonBiz.bean.DispatchUserRoom;
import com.lanchui.commonBiz.manager.DispatchUserRoomManager;
import com.xiwa.base.manager.AbstractBasicManager;

/**
 * Created by wangj on 2015/9/7.
 */
public class SimpleDispatchUserRoomManager extends AbstractBasicManager implements DispatchUserRoomManager {
    public SimpleDispatchUserRoomManager() {
        super(DispatchUserRoom.class);
    }
}
