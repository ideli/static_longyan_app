package com.lanchui.commonBiz.manager.ext;

import com.lanchui.commonBiz.bean.RedstarStoreFavorite;
import com.lanchui.commonBiz.manager.RedstarStoreFavoriteManager;
import com.xiwa.base.manager.AbstractBasicManager;

/**
 * Created by mdc on 2016/6/30.
 */
public class SimpleRedstarStoreFavoriteManager  extends AbstractBasicManager implements RedstarStoreFavoriteManager {
    public SimpleRedstarStoreFavoriteManager() {
        super(RedstarStoreFavorite.class);
    }
}
