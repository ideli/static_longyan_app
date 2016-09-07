package com.lanchui.commonBiz.manager.ext;

import com.lanchui.commonBiz.bean.RedStarMember;
import com.lanchui.commonBiz.manager.RedstarMemberManager;
import com.xiwa.base.manager.AbstractBasicManager;

/**
 * Created by lenovo on 2016/4/22.
 */
public class SimpleRedstarMemberManager extends AbstractBasicManager implements RedstarMemberManager {
    public SimpleRedstarMemberManager() {
        super(RedStarMember.class);
    }
}
