package com.lanchui.commonBiz.manager.ext;

import com.lanchui.commonBiz.bean.RedstarCommunity;
import com.lanchui.commonBiz.manager.RedstarCommunityManager;
import com.lanchui.commonBiz.util.DoubleUtil;
import com.lanchui.commonBiz.util.InsertInformionUtil;
import com.lanchui.commonBiz.util.RateUtil;
import com.xiwa.base.bean.SimplePaginationDescribe;
import com.xiwa.base.manager.AbstractBasicManager;
import com.xiwa.base.manager.ManagerException;

import com.xiwa.base.util.StringUtil;
import org.hibernate.*;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2015/6/3.
 */
public class SimpleRedstarCommunityManager extends AbstractBasicManager implements RedstarCommunityManager {


    public SimpleRedstarCommunityManager() {
        super(RedstarCommunity.class);
    }

    @Override
    public List getDistanceDataList(Double longitude, Double latitude, Integer distance) throws ManagerException {

        Session session = this.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();


            String sql = "select id, name, roomMount , alreadyInputAmount, " + "sqrt( ( ((" + longitude + "-longitude)*PI()*12656*cos(((" + latitude + "+latitude)/2)*PI()/180)/180) * ((" + longitude + "-longitude)*PI()*12656*cos (((" + latitude + "+latitude)/2)*PI()/180)/180) ) + ( ((" + latitude + "-latitude)*PI()*12656/180) * ((" + latitude + "-latitude)*PI()*12656/180) ) )" + "  as distance " + " from xiwa_redstar_community HAVING distance <" + distance;

            Query query = session.createSQLQuery(sql);
            List list = query.list();

            return list;
        } catch (Exception var10) {
            if (tx != null) {
                tx.rollback();
            }
            throw new ManagerException("error_bean_list", var10);
        } finally {
            this.releaseSession(session);
        }
    }

    @Override
    public List<RedstarCommunity> getDataList(Collection<Object> idList, String orderColumn, Boolean desc, Integer page, Integer pageSize) {

        Session session = getSession();
        try {
            Criteria criteria = session.createCriteria(RedstarCommunity.class);
            criteria.add(Restrictions.in("id", idList));
            if (StringUtil.isValid(orderColumn)) {
                if (desc) {
                    criteria.addOrder(Order.desc(orderColumn));
                } else {
                    criteria.addOrder(Order.asc(orderColumn));
                }
            }
            criteria.setFirstResult((page - 1) * pageSize);
            criteria.setMaxResults(pageSize);
            return criteria.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            releaseSession(session);
        }
        return new ArrayList<RedstarCommunity>();
    }

    @Override
    public SimplePaginationDescribe<RedstarCommunity> getRedstarCommunity(String mallId, final int page, final int pageSize, String orderColumn, String isAscStr) throws ManagerException {
        Session session = this.getSession();
        Transaction tx = null;
        List resultList = null;

        try {
            tx = session.beginTransaction();
            final StringBuffer hqlSb = new StringBuffer();
            //sql语句
            hqlSb.append("SELECT c.*  ")
                    .append("FROM xiwa_redstar_community c inner ")
                    .append(" join xiwa_redstar_mall_community m on c.id=m.communityId ")
                    .append(" where m.shoppingMallId=" + mallId + " ")
                    .append(" order by " + orderColumn + " ")
                    .append(isAscStr);

/*
            String sql = "SELECT c.id,c.city,c.province,c.merchantId,c.name,c.area,c.provinceCode, " +
                    " c.cityCode,c.areaCode,c.address,c.logo,c.currentNo,c.hotline,c.pickupAddress," +
                    "c.freeDistribution,c.programFeatures,c.areaMonut, roomMount," +
                    "c.buildingAmount,c.alreadyCheckAmount,c.priceSection,c.buildingDate,c.developers," +
                    "c.createEmployeeId,c.updateEmployeeId,c.createXingMing, c.updateEmployeeXingMing," +
                    "c.updateDate,longitude,c.latitude,c.createDate, c.ownerId,c.ownerXingMing,c.source " +
                    "FROM xiwa_redstar_community c inner " +
                    "join xiwa_redstar_mall_community m on c.id=m.communityId " +
                    "where m.shoppingMallId=" + mallId + " ";*/

            resultList = getHibernateTemplate().executeFind(
                    new HibernateCallback() {
                        public Object doInHibernate(Session arg0) throws HibernateException, SQLException {
                            //映射到实体类里面（可以避免数据库里的字段名字和实体类里面的一样）
                            Query query = arg0.createSQLQuery(hqlSb.toString()).addEntity(RedstarCommunity.class);

                            //设定结果结果集中的每个对象为Map类型
                            //query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
                            //query.setResultTransformer(Transformers.aliasToBean(RedstarCommunity.class));
                            query.setFirstResult(pageSize * (page - 1));
                            query.setMaxResults(pageSize);
                            return query.list();
                        }
                    });

        } catch (Exception var10) {
            var10.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
            throw new ManagerException("error_bean_list", var10);

        } finally {
            this.releaseSession(session);
        }

        List<RedstarCommunity> dataList = new ArrayList<RedstarCommunity>();

        //录入率和入住率
        for (Object obj : resultList) {
            double occupanyRate;
            double inputRate;

            RedstarCommunity r = (RedstarCommunity) obj;

            //计算入住率
            if (r.getAlreadyCheckAmount() != null && r.getRoomMount() != null && r.getRoomMount() > 0) {
                occupanyRate = DoubleUtil.div(r.getAlreadyCheckAmount(), r.getRoomMount(), 2);
                r.setOccupanyRate(RateUtil.getDoubleValue(occupanyRate * 100));
            } else {
                r.setOccupanyRate(0.0);
            }
            //计算录入率
            if (r.getAlreadyInputAmount() != null && r.getRoomMount() != null && r.getRoomMount() > 0) {
                inputRate = DoubleUtil.div(r.getAlreadyInputAmount(), r.getRoomMount(), 2);
                r.setInputRate(RateUtil.getDoubleValue(inputRate * 100));
            } else {
                r.setInputRate(0.0);
            }

            dataList.add(r);
        }

        //总记录数
        int totalRecords = this.getCommunityCount(mallId);

        SimplePaginationDescribe<RedstarCommunity> communityResult = InsertInformionUtil.InsertInformion(page, pageSize, totalRecords, dataList);

        return communityResult;
    }

    public int getCommunityCount(String mallId) throws ManagerException {
        Session session = this.getSession();
        int count = 0;
        try {

            StringBuilder hqlSb = new StringBuilder();

            //sql语句
            String sql = "SELECT count(*) from xiwa_redstar_community c inner JOIN " +
                    " xiwa_redstar_mall_community m ON c.id=m.communityId WHERE m.shoppingMallId=" + mallId;

            //执行sql
            Object object = session.createSQLQuery(sql).uniqueResult();
            return Integer.parseInt(object.toString());

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            this.releaseSession(session);
        }
        return 0;
    }

    @Override
    public List getDataList(String sql, List<Object> paramList, Integer page, Integer pageSize) throws ManagerException {
        Session session = getSession();
        try {
            SQLQuery sqlQuery = session.createSQLQuery(sql);
            if (paramList != null && paramList.size() > 0) {
                for (int index = 0; index < paramList.size(); index++) {
                    sqlQuery.setParameter(index, paramList.get(index));
                }
            }
            sqlQuery.addEntity(RedstarCommunity.class);
            sqlQuery.setFirstResult((page - 1) * pageSize);
            sqlQuery.setMaxResults(pageSize);
            List list = sqlQuery.list();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ManagerException(e.getMessage());
        } finally {
            releaseSession(session);
        }
    }

    @Override
    public int getCountByOwnerIdAndDate(int ownerId, String startDate, String endDate) {
        final Session session = this.getSession();
        int count = 0;
        try {
            StringBuffer hqlSb = new StringBuffer();
            hqlSb.append("  select count(*) from xiwa_redstar_community ");
            hqlSb.append("  where ( ownerId = " + ownerId + " or createEmployeeId = " + ownerId + " ) ");
            if (StringUtil.isValid(startDate)) {
                hqlSb.append(" and createDate >= '" + startDate + "' and createDate <= '" + endDate + "'");
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
