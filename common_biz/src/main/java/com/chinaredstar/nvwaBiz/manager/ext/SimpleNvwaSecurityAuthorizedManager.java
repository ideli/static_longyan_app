package com.chinaredstar.nvwaBiz.manager.ext;

import com.chinaredstar.nvwaBiz.bean.NvwaSecurityAuthorized;
import com.chinaredstar.nvwaBiz.manager.NvwaSecurityAuthorizedManager;
import com.xiwa.base.manager.AbstractBasicManager;
import com.xiwa.base.util.StringUtil;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Created by mdc on 2016/7/13.
 */
public class SimpleNvwaSecurityAuthorizedManager extends AbstractBasicManager implements NvwaSecurityAuthorizedManager {
    public SimpleNvwaSecurityAuthorizedManager() {
        super(NvwaSecurityAuthorized.class);
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
