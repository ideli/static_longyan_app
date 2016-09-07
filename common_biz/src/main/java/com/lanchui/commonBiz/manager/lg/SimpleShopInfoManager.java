package com.lanchui.commonBiz.manager.lg;

import com.lanchui.commonBiz.bean.lg.ShopInfo;
import com.xiwa.base.manager.ManagerException;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.HibernateCallback;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by lenovo on 2016/7/6.
 */
public class SimpleShopInfoManager extends LgAbstractBasicManager implements ShopInfoManager {

    public SimpleShopInfoManager() {
        super(ShopInfo.class);
    }

    @Override
    public List<ShopInfo> getShopInfo() throws ManagerException{
        Session session = this.getSession();
        Transaction transaction = null;
        List resultList = null;
        try {
            transaction = session.beginTransaction();
            final StringBuffer hqlSb = new StringBuffer();
            //sql语句
            hqlSb.append("SELECT  s.id, s.shop_number, s.market_id, s.market_id_uuid, s.market_name, s.company_id, s.dealer_id, s.brand_id,")
                    .append(" s.brand_name, s.series_id,mapping.ps_market_id AS series_name, s.is_del, s.create_date, s.status, s.market_booth_number,")
                    .append(" s.market_floor,  market.market_address as shop_tel, s.sales_start_time, s.sales_end_time,s.shop_name, s.shop_introduction, s.supplier_info")
                    .append(" FROM oms_shop_info s ")
                    .append(" LEFT JOIN oms_market_info market ON s.market_id = market.id")
                    .append(" LEFT JOIN market_mapping mapping ON market.market_number = mapping.rem_market_id");

            resultList = getHibernateTemplate().executeFind(new HibernateCallback() {
                public Object doInHibernate(Session arg0) throws HibernateException, SQLException {
                    //映射到实体类里面（可以避免数据库里的字段名字和实体类里面的一样）
                    Query query = arg0.createSQLQuery(hqlSb.toString()).addEntity(ShopInfo.class);
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
