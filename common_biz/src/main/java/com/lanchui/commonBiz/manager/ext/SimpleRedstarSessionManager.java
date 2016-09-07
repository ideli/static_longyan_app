package com.lanchui.commonBiz.manager.ext;

import com.lanchui.commonBiz.bean.RedstarSession;
import com.lanchui.commonBiz.manager.RedstarSessionManager;
import com.xiwa.base.bean.search.ext.BooleanSearch;
import com.xiwa.base.bean.search.ext.MultiSearchBean;
import com.xiwa.base.bean.search.ext.TextSearch;
import com.xiwa.base.manager.AbstractBasicManager;
import com.xiwa.base.manager.ManagerException;
import com.xiwa.base.util.CollectionUtil;

import java.util.List;

/**
 * Created by usagizhang on 16-4-19.
 */
public class SimpleRedstarSessionManager extends AbstractBasicManager implements RedstarSessionManager {
    public SimpleRedstarSessionManager() {
        super(RedstarSession.class);
    }

    @Override
    public void createSession(RedstarSession session) throws ManagerException {
        if (session == null) {
            throw new ManagerException("Session不能为空");
        }
        addBean(session);
    }

    @Override
    public RedstarSession getActivatedSession(String token) throws ManagerException {
        MultiSearchBean multiSearchBean = new MultiSearchBean();
        BooleanSearch activatedSearch = new BooleanSearch("activated");
        activatedSearch.setValue("1");
        multiSearchBean.addSearchBean(activatedSearch);
        TextSearch tokenSearch = new TextSearch("token");
        tokenSearch.setSearchValue(token);
        multiSearchBean.addSearchBean(tokenSearch);

        List<RedstarSession> list = searchIdentify(multiSearchBean);
        if (CollectionUtil.isValid(list)) {
            return list.get(0);
        }
        return null;
    }
 /*   @Override
    public RedstarSession getActivatedSessio(String openId) throws ManagerException {
        MultiSearchBean multiSearchBean = new MultiSearchBean();
        BooleanSearch activatedSearch = new BooleanSearch("activated");
        activatedSearch.setValue("1");
        multiSearchBean.addSearchBean(activatedSearch);
        TextSearch tokenSearch = new TextSearch("openId");
        tokenSearch.setSearchValue(openId);
        multiSearchBean.addSearchBean(tokenSearch);

        List<RedstarSession> list = searchIdentify(multiSearchBean);
        if (CollectionUtil.isValid(list)) {
            return list.get(0);
        }
        return null;
    }*/
}
