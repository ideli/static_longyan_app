package com.lanchui.commonBiz.manager.ext;

import com.lanchui.commonBiz.bean.RedstarScoreRule;
import com.lanchui.commonBiz.manager.RedstarScoreRuleManager;
import com.xiwa.base.manager.AbstractBasicManager;

/**
 * Created by mdc on 2016/7/13.
 */
public class SimpleRedstarScoreRuleManager extends AbstractBasicManager implements RedstarScoreRuleManager {
    public SimpleRedstarScoreRuleManager() {
        super(RedstarScoreRule.class);
    }
}
