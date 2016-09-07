package com.lanchui.commonBiz.manager.ext;

import com.lanchui.commonBiz.bean.work.RedstarRestaurantOrder;
import com.lanchui.commonBiz.manager.RedstarCommonManager;
import com.lanchui.commonBiz.manager.RedstarRestaurantOrderManager;
import com.xiwa.base.manager.AbstractBasicManager;
import com.xiwa.base.manager.ManagerException;
import com.xiwa.base.util.StringUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.joda.time.DateTime;
import org.springframework.orm.hibernate3.HibernateCallback;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by lenovo on 2016/7/8.
 */
public class SimpleRedstarRestaurantOrderManager extends AbstractBasicManager implements RedstarRestaurantOrderManager {
    public SimpleRedstarRestaurantOrderManager() {
        super(RedstarRestaurantOrder.class);
    }

    @Override
    public boolean findByRestaurantIdAndEmployeeIdAndDay(int restaurantId, int employeeId, String status, RedstarCommonManager redstarCommonManager) throws ManagerException {
        Session session = this.getSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            final StringBuffer hqlSb = new StringBuffer();
            DateTime now = DateTime.now();
            int year = now.getYear();
            int month = now.getMonthOfYear();
            int day = now.getDayOfMonth();
            //sql语句
            hqlSb.append("SELECT count(*) from xiwa_redstar_restaurant_order WHERE ")
                    .append(" restaurantId = " + restaurantId)
                    .append(" and employeeId = " + employeeId)
                    .append(" and status = '" + status + "'")
                    .append(" and year = " + year)
                    .append(" and month = " + month)
                    .append(" and day = " + day);
            BigInteger result = (BigInteger) redstarCommonManager.queryUniqueBySql(hqlSb.toString()).get(0);
            if (result.intValue() == 0) {
                return false;
            }
        } catch (Exception var10) {
            if (transaction != null) {
                transaction.rollback();
            }
            var10.printStackTrace();
        } finally {
            this.releaseSession(session);
        }
        return true;
    }

    @Override
    public List<RedstarRestaurantOrder> findByRestaurantIdAndEmployeeIdAndDayInfo(int restaurantId, int employeeId, String status) throws ManagerException {
        Session session = this.getSession();
        Transaction transaction = null;
        List result;
        try {
            transaction = session.beginTransaction();
            final StringBuffer hqlSb = new StringBuffer();
            DateTime now = DateTime.now();
            int year = now.getYear();
            int month = now.getMonthOfYear();
            int day = now.getDayOfMonth();
            //sql语句
            hqlSb.append("SELECT * from xiwa_redstar_restaurant_order WHERE ")
                    .append(" restaurantId = " + restaurantId)
                    .append(" and employeeId = " + employeeId);
            if (StringUtil.isValid(status)) {
                hqlSb.append(" and status = '" + status + "'");
            }
            hqlSb.append(" and year = " + year)
                    .append(" and month = " + month)
                    .append(" and day = " + day);
            result = getHibernateTemplate().executeFind(new HibernateCallback() {
                public Object doInHibernate(Session arg0) throws HibernateException, SQLException {
                    //映射到实体类里面（可以避免数据库里的字段名字和实体类里面的一样）
                    Query query = arg0.createSQLQuery(hqlSb.toString()).addEntity(RedstarRestaurantOrder.class);
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
        return result;
    }


}
