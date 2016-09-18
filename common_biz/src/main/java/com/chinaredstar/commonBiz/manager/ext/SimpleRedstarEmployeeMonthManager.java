package com.chinaredstar.commonBiz.manager.ext;


import com.chinaredstar.commonBiz.manager.RedstarEmployeeMonthManager;
import com.chinaredstar.commonBiz.bean.RedstarEmployeeMonth;
import com.xiwa.base.manager.AbstractBasicManager;
import com.xiwa.base.manager.ManagerException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/4/26.
 */
public class SimpleRedstarEmployeeMonthManager  extends AbstractBasicManager implements RedstarEmployeeMonthManager {
    public SimpleRedstarEmployeeMonthManager() {
        super(RedstarEmployeeMonth.class);
    }

    @Override
    public List<RedstarEmployeeMonth> getRedstarEmployeeDayInputInfo(int year, int month) {
        List<RedstarEmployeeMonth> result = new ArrayList<RedstarEmployeeMonth>();
        final Session session = this.getSession();
        try {
            StringBuffer hqlSb = new StringBuffer();
            hqlSb.append("SELECT * FROM xiwa_redstar_report_employee_input_month ");
            hqlSb.append("WHERE year = " + year + " AND month = " + month );
            Query query = session.createSQLQuery(hqlSb.toString()).addEntity(RedstarEmployeeMonth.class);
            result = query.list();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            this.releaseSession(session);
        }
        return result;
    }

    @Override
    public <T> List<T> getRankDataList(List<Object> paramList, Class<T> targetClass) throws ManagerException {
        Session session = getSession();
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT e.*, em.shoppingMallName mallName ,c.orgName organizationName ");
        sb.append(" FROM( ( SELECT * FROM xiwa_redstar_report_employee_input_month WHERE YEAR =? AND MONTH =? AND scoreRank > 0 ORDER BY scoreRank ) e  ");
        sb.append(" JOIN xiwa_redstar_mall_employee em ON e.employeeId = em.employeeId ");
        sb.append(" JOIN xiwa_redstar_shopping_mall m ON em.shoppingMallId = m.id AND m.mallType = 'real' ");
        sb.append(" JOIN ( SELECT a.id mallId,a.name mallName,b.id orgId,b.name orgName FROM ( SELECT s.id, s.organizationId,so.parentId AS parentId,so.name ");
        sb.append(" FROM xiwa_redstar_shopping_mall s");
        sb.append(" LEFT JOIN xiwa_redstar_shopping_mall_organization so ON s.organizationId = so.id");
        sb.append(" WHERE s.mallType = 'real' ) a");
        sb.append(" LEFT JOIN ( SELECT id,parentId,name FROM xiwa_redstar_shopping_mall_organization sos WHERE parentId =- 1 ) b ON a.parentId = b.id ) c");
        sb.append(" ON m.id=c.mallId ) LIMIT 200 ");
        try {
            SQLQuery sqlQuery = session.createSQLQuery(sb.toString());
            if (paramList != null && paramList.size() > 0) {
                for (int index = 0; index < paramList.size(); index++) {
                    sqlQuery.setParameter(index, paramList.get(index));
                }
            }
            sqlQuery.addEntity(targetClass);
            return sqlQuery.list();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ManagerException(e.getMessage());
        } finally {
            releaseSession(session);
        }
    }

    @Override
    public <T> List<T> getCurrentUserDataList(List<Object> paramList, Class<T> targetClass) throws ManagerException {


        Session session = getSession();


        StringBuffer sb2 = new StringBuffer();

        sb2.append("SELECT e.*, em.shoppingMallName mallName ,c.orgName organizationName ");
        sb2.append(" FROM( ( SELECT * FROM xiwa_redstar_report_employee_input_month WHERE YEAR =? AND MONTH =? And employeeId=? ) e  ");
        sb2.append(" LEFT JOIN xiwa_redstar_mall_employee em ON e.employeeId = em.employeeId ");
        sb2.append(" LEFT JOIN xiwa_redstar_shopping_mall m ON em.shoppingMallId = m.id AND m.mallType = 'real' ");
        sb2.append(" LEFT JOIN ( SELECT a.id mallId,a.name mallName,b.id orgId,b.name orgName FROM ( SELECT s.id, s.organizationId,so.parentId AS parentId,so.name ");
        sb2.append(" FROM xiwa_redstar_shopping_mall s");
        sb2.append(" LEFT JOIN xiwa_redstar_shopping_mall_organization so ON s.organizationId = so.id");
        sb2.append(" WHERE s.mallType = 'real' ) a");
        sb2.append(" LEFT JOIN ( SELECT id,parentId,name FROM xiwa_redstar_shopping_mall_organization sos WHERE parentId =- 1 ) b ON a.parentId = b.id ) c");
        sb2.append(" ON m.id=c.mallId ) LIMIT 1 ");

        try {
            SQLQuery sqlQuery = session.createSQLQuery(sb2.toString());
            if (paramList != null && paramList.size() > 0) {
                for (int index = 0; index < paramList.size(); index++) {
                    sqlQuery.setParameter(index, paramList.get(index));
                }
            }
            sqlQuery.addEntity(targetClass);
            return sqlQuery.list();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ManagerException(e.getMessage());
        } finally {
            releaseSession(session);
        }
    }

    @Override
    public <T> List<T> getHistoryList(List<Object> paramList, Class<T> targetClass) throws ManagerException {
        Session session = getSession();
        StringBuffer sb = new StringBuffer();

        sb.append("SELECT e.*, em.shoppingMallName mallName ,c.orgName organizationName ");
        sb.append(" FROM( ( SELECT * FROM xiwa_redstar_report_employee_input_month WHERE scoreRank=1 ORDER BY  year Desc,month Desc ) e  ");
        sb.append(" JOIN xiwa_redstar_mall_employee em ON e.employeeId = em.employeeId ");
        sb.append(" JOIN xiwa_redstar_shopping_mall m ON em.shoppingMallId = m.id AND m.mallType = 'real' ");
        sb.append(" JOIN ( SELECT a.id mallId,a.name mallName,b.id orgId,b.name orgName FROM ( SELECT s.id, s.organizationId,so.parentId AS parentId,so.name ");
        sb.append(" FROM xiwa_redstar_shopping_mall s");
        sb.append(" LEFT JOIN xiwa_redstar_shopping_mall_organization so ON s.organizationId = so.id");
        sb.append(" WHERE s.mallType = 'real' ) a");
        sb.append(" LEFT JOIN ( SELECT id,parentId,name FROM xiwa_redstar_shopping_mall_organization sos WHERE parentId =- 1 ) b ON a.parentId = b.id ) c");
        sb.append(" ON m.id=c.mallId ) ");
        try {
            SQLQuery sqlQuery = session.createSQLQuery(sb.toString());
            if (paramList != null && paramList.size() > 0) {
                for (int index = 0; index < paramList.size(); index++) {
                    sqlQuery.setParameter(index, paramList.get(index));
                }
            }
            sqlQuery.addEntity(targetClass);
            return sqlQuery.list();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ManagerException(e.getMessage());
        } finally {
            releaseSession(session);
        }
    }
}
