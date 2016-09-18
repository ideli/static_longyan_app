package com.chinaredstar.commonBiz.manager.ext;

import com.chinaredstar.commonBiz.bean.work.RedstarRestaurantMenu;
import com.chinaredstar.commonBiz.manager.RedstarRestaurantMenuManager;
import com.xiwa.base.manager.AbstractBasicManager;
import com.xiwa.base.manager.ManagerException;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.joda.time.DateTime;
import org.springframework.orm.hibernate3.HibernateCallback;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by lenovo on 2016/7/8.
 */
public class SimpleRedstarRestaurantMenuManager extends AbstractBasicManager implements RedstarRestaurantMenuManager {
    public SimpleRedstarRestaurantMenuManager() {
        super(RedstarRestaurantMenu.class);
    }

    @Override
    public List<RedstarRestaurantMenu> findByRestaurantIdAndDate(int restaurantId) throws ManagerException {
        Session session = this.getSession();
        Transaction transaction = null;
        List resultList = null;
        try {
            transaction = session.beginTransaction();
            final StringBuffer hqlSb = new StringBuffer();
            String startTime = DateTime.now().toString("yyyy-MM-dd hh:MM:ss");
            String endTime = DateTime.now().plusDays(1).toString("yyyy-MM-dd hh:MM:ss");
            //sql语句
            hqlSb.append("SELECT * ")
                    .append(" FROM xiwa_redstar_restaurant_menu ")
                    .append(" WHERE restaurantId = " + restaurantId + " AND createDate >= '" + startTime + "'")
                    .append(" AND createDate <= '" + endTime + "'");
            resultList = getHibernateTemplate().executeFind(new HibernateCallback() {
                public Object doInHibernate(Session arg0) throws HibernateException, SQLException {
                    //映射到实体类里面（可以避免数据库里的字段名字和实体类里面的一样）
                    Query query = arg0.createSQLQuery(hqlSb.toString()).addEntity(RedstarRestaurantMenu.class);
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
