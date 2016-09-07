package com.lanchui.commonBiz.manager.ext;

import com.lanchui.commonBiz.bean.RedstarAppAd;
import com.lanchui.commonBiz.manager.RedstarAppAdManager;
import com.xiwa.base.manager.AbstractBasicManager;

/**
 * Created by lenovo on 2016/7/13.
 */
public class SimpleRedstarAppAdManager extends AbstractBasicManager implements RedstarAppAdManager {

    public SimpleRedstarAppAdManager(){

        super(RedstarAppAd.class);
    }

}

