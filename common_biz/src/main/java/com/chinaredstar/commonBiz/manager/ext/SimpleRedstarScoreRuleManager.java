package com.chinaredstar.commonBiz.manager.ext;

import com.chinaredstar.commonBiz.manager.RedstarScoreRuleManager;
import com.chinaredstar.commonBiz.bean.RedstarScoreRule;
import com.xiwa.base.manager.AbstractBasicManager;

/**
 * Created by mdc on 2016/7/13.
 */
public class SimpleRedstarScoreRuleManager extends AbstractBasicManager implements RedstarScoreRuleManager {
    public SimpleRedstarScoreRuleManager() {
        super(RedstarScoreRule.class);
    }
}
