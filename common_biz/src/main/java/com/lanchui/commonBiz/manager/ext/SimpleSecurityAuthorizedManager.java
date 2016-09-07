package com.lanchui.commonBiz.manager.ext;

import com.lanchui.commonBiz.bean.SecurityAuthorized;
import com.lanchui.commonBiz.manager.SecurityAuthorizedManager;
import com.xiwa.base.manager.AbstractBasicManager;
import com.xiwa.base.util.StringUtil;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Created by mdc on 2016/7/13.
 */
public class SimpleSecurityAuthorizedManager extends AbstractBasicManager implements SecurityAuthorizedManager {
    public SimpleSecurityAuthorizedManager() {
        super(SecurityAuthorized.class);
    }

    @Override
    public int getAuthorizeCount(String roles, String roleName) {
        final Session session = this.getSession();
        int count = 0;
        try {
            StringBuffer hqlSb = new StringBuffer();
            hqlSb.append(" select count(*) FROM xiwa_security_role ");
            hqlSb.append(" WHERE id in (" + roles + ")");
            if(StringUtil.isValid(roleName)){
                hqlSb.append(" AND alias = '" + roleName + "'");
            }
            Query query = session.createSQLQuery(hqlSb.toString());
            count = Integer.parseInt(query.uniqueResult().toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            this.releaseSession(session);
        }
        return count;
    }
}
