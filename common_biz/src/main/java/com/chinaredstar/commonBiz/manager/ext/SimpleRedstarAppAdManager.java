package com.chinaredstar.commonBiz.manager.ext;

import com.chinaredstar.commonBiz.bean.RedstarAppAd;
import com.chinaredstar.commonBiz.manager.RedstarAppAdManager;
import com.xiwa.base.manager.AbstractBasicManager;

/**
 * Created by lenovo on 2016/7/13.
 */
public class SimpleRedstarAppAdManager extends AbstractBasicManager implements RedstarAppAdManager {

    public SimpleRedstarAppAdManager(){

        super(RedstarAppAd.class);
    }

}

