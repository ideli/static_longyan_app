package com.lanchui.commonBiz.manager.ext;

import com.lanchui.commonBiz.bean.DispatchMerchantAuthorized;
import com.lanchui.commonBiz.manager.DispatchMerchantAuthorizedManager;
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
 * Created by wangj on 2015/5/4.
 */
public class SimpleDispatchMerchantAuthorizedManager extends AbstractBasicManager implements DispatchMerchantAuthorizedManager {

    private static final Logger logger = Logger.getLogger(SimpleDispatchMerchantAuthorizedManager.class);

    public SimpleDispatchMerchantAuthorizedManager() {
        super(DispatchMerchantAuthorized.class);
    }

    /**
     * 根据登录名和密码找出商铺登陆用户对象
     *
     * @param account
     * @param password
     * @return
     */
    @Override
    public DispatchMerchantAuthorized getDispatchMerchantAuthorized(String account, String password) throws ManagerException {
        MultiSearchBean multiSearchBean = new MultiSearchBean();
        TextSearch accountSearch = new TextSearch("account");
        accountSearch.setSearchValue(account);
        TextSearch passwdSearch = new TextSearch("password");
        passwdSearch.setSearchValue(password);
        multiSearchBean.addSearchBean(accountSearch);
        multiSearchBean.addSearchBean(passwdSearch);
        logger.info("[MerchantAction][merchantAuth] account=" + account + "password=" + password);
        List<DispatchMerchantAuthorized> merchantAuthorizedList = this.searchIdentify(multiSearchBean, Identified.BEAN_NAME, true);
        logger.info("[MerchantAction][merchantAuth] merchantAuthorizedList=" + JSONArray.fromObject(merchantAuthorizedList).toString());
        logger.info("[MerchantAction][merchantAuth] authTarget='merchant '");

        if (CollectionUtil.isValid(merchantAuthorizedList)) {
            return merchantAuthorizedList.get(0);
        } else {
            return null;
        }
    }
}
