package com.lanchui.commonBiz.manager.ext;


import com.lanchui.commonBiz.bean.SecurityOperationLog;
import com.lanchui.commonBiz.manager.DispatchSecurityOperationLogManager;
import com.xiwa.base.bean.search.SearchBean;
import com.xiwa.base.manager.AbstractBasicManager;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.text.SimpleDateFormat;

/**
 * Created by lenovo on 2016/4/21.
 */
public class SimpleDispatchSecurityOperationLogManager extends AbstractBasicManager implements DispatchSecurityOperationLogManager {
    public SimpleDispatchSecurityOperationLogManager() {
        super(SecurityOperationLog.class);
    }

    @Override
    public Integer getCount(Class c,String _today,String compareColumn,SearchBean searchBean) {
            Session session = this.getSession();
            Integer var5=0;
            try {
                Criteria e;
                if(searchBean==null){
                    e = session.createCriteria(c.getName());
                }else{
                    e = getSearchCriteria(c.getName(),searchBean,session);
                }
                e.add(Restrictions.gt(compareColumn,new SimpleDateFormat("yyyy-MM-dd").parse(_today)));
                e.setProjection(Projections.rowCount());
                var5 =Integer.parseInt(String.valueOf(e.uniqueResult()));
            } catch (Exception var9) {
                var9.printStackTrace();
            } finally {
                this.releaseSession(session);
            }
            return var5;
        }

    @Override
    public Integer getLtSum(Class c,String _today,String compareColumn,SearchBean searchBean) {
        Session session = this.getSession();
        Integer count=0;
        try {
            Criteria e;
            if(searchBean==null){
                e = session.createCriteria(c);
            }else{
                e = getSearchCriteria(c.getName(),searchBean,session);
            }
            e.add(Restrictions.lt(compareColumn, new SimpleDateFormat("yyyy-MM-dd").parse(_today)));
            e.setProjection(Projections.sum("newUserCount"));
            count =Integer.parseInt(String.valueOf(e.uniqueResult()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.releaseSession(session);
        }
        return count;
    }

    @Override
    public Integer getAllCount(Class c,SearchBean searchBean) {
        Session session = this.getSession();
        Integer count=0;
        try {
            Criteria e;
            if(searchBean==null){
                e = session.createCriteria(c);
            }else{
                e = getSearchCriteria(c.getName(),searchBean,session);
            }
            e.setProjection(Projections.rowCount());
            count =Integer.parseInt(String.valueOf(e.uniqueResult()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.releaseSession(session);
        }
        return count;
    }

    @Override
    public Integer getSum(Class c, String _today, String compareColumn, String sumColumn, SearchBean searchBean, Boolean gt) {
        Session session = this.getSession();
        Integer count=0;
        try {
            Criteria criteria;
            if(searchBean==null){
                criteria = session.createCriteria(c);
            }else{
                criteria = getSearchCriteria(c.getName(),searchBean,session);
            }
            if(gt){
                criteria.add(Restrictions.gt(compareColumn, new SimpleDateFormat("yyyy-MM-dd").parse(_today)));
            }else{
                criteria.add(Restrictions.lt(compareColumn, new SimpleDateFormat("yyyy-MM-dd").parse(_today)));
            }
            criteria.setProjection(Projections.sum(sumColumn));
            count =Integer.parseInt(String.valueOf(criteria.uniqueResult()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.releaseSession(session);
        }
        return count;
    }

    @Override
    public Integer getCountryCount(Class c, String _today, String compareColumn, SearchBean searchBean) {
        Session session = this.getSession();
        Integer var5=0;
        try {
            Criteria e;
            if(searchBean==null){
                e = session.createCriteria(c.getName());
            }else{
                e = getSearchCriteria(c.getName(),searchBean,session);
            }
            e.add(Restrictions.gt("ownerId",0));
            e.add(Restrictions.gt(compareColumn,new SimpleDateFormat("yyyy-MM-dd").parse(_today)));
            e.setProjection(Projections.rowCount());
            var5 =Integer.parseInt(String.valueOf(e.uniqueResult()));
        } catch (Exception var9) {
            var9.printStackTrace();
        } finally {
            this.releaseSession(session);
        }
        return var5;
    }

    @Override
    public Integer getLastDayData(Class c, String compareColumn, String beginDay, String endDay, SearchBean searchBean) {
        Session session = this.getSession();
        Integer count=0;
        try {
            Criteria criteria;
            if(searchBean==null){
                criteria = session.createCriteria(c);
            }else{
                criteria = getSearchCriteria(c.getName(),searchBean,session);
            }
            criteria.add(Restrictions.gt("ownerId",0));

            criteria.add(Restrictions.ge(compareColumn,new SimpleDateFormat("yyyy-MM-dd").parse(beginDay)));

            criteria.add(Restrictions.lt(compareColumn, new SimpleDateFormat("yyyy-MM-dd").parse(endDay)));

            criteria.setProjection(Projections.rowCount());

            count =Integer.parseInt(String.valueOf(criteria.uniqueResult()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.releaseSession(session);
        }
        return count;
    }

}
