package com.lanchui.commonBiz.manager;

import com.xiwa.base.manager.BasicManager;

/**
 * Created by lenovo on 2016/5/31.
 */
public interface RedstarReviewManager extends BasicManager {

    //根据条件查找结果
    public int getCountByCondition(String objectId,String userId,String objectCode);
    
}
