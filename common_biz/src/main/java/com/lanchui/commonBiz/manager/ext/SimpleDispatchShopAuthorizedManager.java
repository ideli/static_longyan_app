package com.lanchui.commonBiz.manager.ext;

import com.lanchui.commonBiz.bean.DispatchCommunityAuthorized;
import com.lanchui.commonBiz.bean.DispatchShop;
import com.lanchui.commonBiz.bean.DispatchShopAuthorized;
import com.lanchui.commonBiz.manager.DispatchShopAuthorizedManager;
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
 * Created by usagizhang on 15-12-7.
 */
public class SimpleDispatchShopAuthorizedManager extends AbstractBasicManager implements DispatchShopAuthorizedManager {
    public SimpleDispatchShopAuthorizedManager() {
        super(DispatchShopAuthorized.class);
    }

    private static final Logger logger = Logger.getLogger(SimpleDispatchShopAuthorizedManager.class);

    /**
     * 获取店铺授权
     *
     * @param account
     * @param password
     * @return
     * @throws ManagerException
     */
    @Override
    public DispatchShopAuthorized getShopAuthorized(String account, String password) throws ManagerException {

        MultiSearchBean multiSearchBean = new MultiSearchBean();
        TextSearch accountSearch = new TextSearch("account");
        accountSearch.setSearchValue(account);
        TextSearch passwdSearch = new TextSearch("password");
        passwdSearch.setSearchValue(password);
        multiSearchBean.addSearchBean(accountSearch);
        multiSearchBean.addSearchBean(passwdSearch);
        logger.info("[SimpleDispatchShopAuthorizedManager] account=" + account + "password=" + password);
        List<DispatchShopAuthorized> shopAuthorizeds = this.searchIdentify(multiSearchBean, Identified.BEAN_NAME, true);
        logger.info("[SimpleDispatchShopAuthorizedManager] merchantAuthorizedList=" + shopAuthorizeds.size());
        logger.info("[SimpleDispatchShopAuthorizedManager] authTarget='community '");

        if (CollectionUtil.isValid(shopAuthorizeds)) {
            return shopAuthorizeds.get(0);
        } else {
            return null;
        }
    }
}
