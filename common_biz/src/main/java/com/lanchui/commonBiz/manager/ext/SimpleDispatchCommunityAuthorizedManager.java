package com.lanchui.commonBiz.manager.ext;

import com.lanchui.commonBiz.bean.DispatchCommunityAuthorized;
import com.lanchui.commonBiz.manager.DispatchCommunityAuthorizedManager;
import com.xiwa.base.bean.Identified;
import com.xiwa.base.bean.search.ext.MultiSearchBean;
import com.xiwa.base.bean.search.ext.TextSearch;
import com.xiwa.base.manager.AbstractBasicManager;
import com.xiwa.base.manager.ManagerException;
import com.xiwa.base.util.CollectionUtil;
import net.sf.json.JSONArray;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by wangj on 2015/6/3.
 */
public class SimpleDispatchCommunityAuthorizedManager extends AbstractBasicManager implements DispatchCommunityAuthorizedManager {

    private static final Logger logger = Logger.getLogger(SimpleDispatchCommunityAuthorizedManager.class);

    public SimpleDispatchCommunityAuthorizedManager() {
        super(DispatchCommunityAuthorized.class);
    }

    /**
     * 根据登录名和密码找出社区登陆用户对象
     *
     * @param account
     * @param password
     * @return
     */
    @Override
    public DispatchCommunityAuthorized getDispatchCommunityAuthorized(String account, String password) throws ManagerException {

        MultiSearchBean multiSearchBean = new MultiSearchBean();
        TextSearch accountSearch = new TextSearch("account");
        accountSearch.setSearchValue(account);
        TextSearch passwdSearch = new TextSearch("password");
        passwdSearch.setSearchValue(password);
        multiSearchBean.addSearchBean(accountSearch);
        multiSearchBean.addSearchBean(passwdSearch);
        logger.info("[DispatchCommunityAuthorized] account=" + account + "password=" + password);
        List<DispatchCommunityAuthorized> communityAuthorizedList = this.searchIdentify(multiSearchBean, Identified.BEAN_NAME, true);
        logger.info("[DispatchCommunityAuthorized] merchantAuthorizedList=" + JSONArray.fromObject(communityAuthorizedList).toString());
        logger.info("[DispatchCommunityAuthorized] authTarget='community '");

        if (CollectionUtil.isValid(communityAuthorizedList)) {
            return communityAuthorizedList.get(0);
        } else {
            return null;
        }
    }
}
