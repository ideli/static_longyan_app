package com.lanchui.commonBiz.manager.ext;

import com.lanchui.commonBiz.bean.RedstarVerifyCode;
import com.lanchui.commonBiz.manager.RedstarVerifyCodeManager;
import com.xiwa.base.manager.AbstractBasicManager;

/**
 * Created by usagizhang on 16-4-25.
 */
public class SimpleRedstarVerifyCodeManager extends AbstractBasicManager implements RedstarVerifyCodeManager {
    public SimpleRedstarVerifyCodeManager() {
        super(RedstarVerifyCode.class);
    }
}
