package com.chinaredstar.commonBiz.manager.ext;

import com.chinaredstar.commonBiz.bean.RedStarMember;
import com.chinaredstar.commonBiz.manager.RedstarMemberManager;
import com.xiwa.base.manager.AbstractBasicManager;

/**
 * Created by lenovo on 2016/4/22.
 */
public class SimpleRedstarMemberManager extends AbstractBasicManager implements RedstarMemberManager {
    public SimpleRedstarMemberManager() {
        super(RedStarMember.class);
    }
}
