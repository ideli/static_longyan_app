package com.lanchui.commonBiz.manager.ext;

import com.lanchui.commonBiz.bean.RedstarStore;
import com.lanchui.commonBiz.bean.constant.AvgScore;
import com.lanchui.commonBiz.manager.RedstarStoreManager;
import com.xiwa.base.manager.AbstractBasicManager;
import com.xiwa.base.manager.ManagerException;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.HibernateCallback;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by mdc on 2016/6/29.
 */
public class SimpleRedstarStoreManager extends AbstractBasicManager implements RedstarStoreManager {
    public SimpleRedstarStoreManager() {
        super(RedstarStore.class);
    }

    @Override
    public List<Object> calDetail(int storeId) throws ManagerException {
        Session session = this.getSession();
        Transaction transaction = null;
        List resultList = null;
        try {
            transaction = session.beginTransaction();
            final StringBuffer hqlSb = new StringBuffer();
            //sql语句
            hqlSb.append("SELECT xrrd.labelCode as labelCode,format(IFNULL((sum(xrrd.score) / count(xrrd.labelCode)),0),1) AS average")
                    .append(" FROM xiwa_redstar_review_detail xrrd")
                    .append(" LEFT JOIN xiwa_redstar_review xrr ON xrrd.reviewId = xrr.id")
                    .append(" LEFT JOIN xiwa_redstar_store xrs ON xrr.objectId = xrs.id")
                    .append(" WHERE xrs.id = " + storeId + " GROUP BY xrrd.labelCode ");

            resultList = getHibernateTemplate().executeFind(new HibernateCallback() {
                public Object doInHibernate(Session arg0) throws HibernateException, SQLException {
                    //映射到实体类里面（可以避免数据库里的字段名字和实体类里面的一样）
                    Query query = arg0.createSQLQuery(hqlSb.toString());
                    return query.list();
                }
            });

        } catch (Exception var10) {
            var10.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
            throw new ManagerException("error_bean_list", var10);

        } finally {
            this.releaseSession(session);
        }
        return resultList;
    }
}
