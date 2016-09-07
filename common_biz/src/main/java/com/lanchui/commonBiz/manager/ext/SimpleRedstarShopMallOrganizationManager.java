package com.lanchui.commonBiz.manager.ext;

import com.lanchui.commonBiz.bean.RedstarShopMallOrganization;
import com.lanchui.commonBiz.manager.RedstarShopMallOrganizationManager;
import com.xiwa.base.manager.AbstractBasicManager;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/4/27.
 */
public class SimpleRedstarShopMallOrganizationManager extends AbstractBasicManager implements RedstarShopMallOrganizationManager {
    public SimpleRedstarShopMallOrganizationManager() {
        super(RedstarShopMallOrganization.class);
    }

    @Override
    public List<RedstarShopMallOrganization> getChildList(Class c, String column, Boolean desc, String type) {
        //inputMemberAmount
        Session session = this.getSession();
        try {
            Criteria e = session.createCriteria(c);
            if ("root".equals(type)) {
                e.add(Restrictions.eq("parentId", -1));
            } else {
                e.add(Restrictions.ne("parentId", 0));
                e.add(Restrictions.ne("parentId", -1));
            }
            if (desc) {
                e.addOrder(Order.desc(column));
            } else {
                e.addOrder(Order.asc(column));
            }

            //排名相同时按照录入小区排名
            e.addOrder(Order.desc("inputCommunityAmount"));
            return e.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.releaseSession(session);
        }
        return new ArrayList<RedstarShopMallOrganization>();
    }

    @Override
    public Object[] getParentId(int shopId) {
        final Session session = this.getSession();
        Object[] result = new Object[2];
        try {
            final StringBuffer sql = new StringBuffer("SELECT b.id,b.name from (");
            sql.append(" SELECT s.id, s.organizationId, so.parentId as parentId, so.name FROM xiwa_redstar_shopping_mall s")
                    .append("  LEFT JOIN xiwa_redstar_shopping_mall_organization so ON s.organizationId = so.id ")
                    .append("  where s.mallType = 'real' and s.id = " + shopId + ") a")
                    .append(" LEFT JOIN (SELECT id, parentId, name FROM xiwa_redstar_shopping_mall_organization sos")
                    .append("  WHERE parentId = -1)b ON a.parentId = b.id");
            Query query = session.createSQLQuery(sql.toString());
            result = (Object[]) query.uniqueResult();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

}
