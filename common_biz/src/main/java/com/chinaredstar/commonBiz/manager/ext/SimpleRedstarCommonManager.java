package com.chinaredstar.commonBiz.manager.ext;

import com.chinaredstar.nvwaBiz.bean.NvwaEmployee;
import com.chinaredstar.commonBiz.bean.RedstarReportCountrywideDaily;
import com.chinaredstar.commonBiz.manager.RedstarCommonManager;
import com.xiwa.base.bean.Identified;
import com.xiwa.base.bean.PaginationDescribe;
import com.xiwa.base.bean.SimplePaginationDescribe;
import com.xiwa.base.bean.Validatable;
import com.xiwa.base.bean.search.SearchBean;
import com.xiwa.base.manager.AbstractBasicManager;
import com.xiwa.base.manager.ManagerException;
import com.xiwa.base.util.CollectionUtil;
import com.xiwa.base.util.DataUtil;
import com.xiwa.base.util.StringUtil;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.*;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lenovo on 2016/5/5.
 */
public class SimpleRedstarCommonManager extends AbstractBasicManager implements RedstarCommonManager {

    private static Logger logger = Logger.getLogger(SimpleRedstarCommonManager.class);

    @Override
    public List getDataList(Class beanClass) throws ManagerException {
        return this.getIdentifyList(beanClass);
    }

    @Override
    public List getDataList(Class beanClass, SearchBean searchBean) throws ManagerException {
        if (searchBean == null) {
            return this.getIdentifyList(beanClass);
        }
        return this.searchIdentify(beanClass, searchBean);
    }

    public Identified getDataById(int id, Class _thisClass) throws ManagerException {
        return this.getIdentify(id, _thisClass);
    }

    @Override
    public Identified getDataById(int id, String beanName) throws ManagerException {
        return this.getIdentify(id, beanName, Boolean.TRUE);
    }

    @Override
    public void addData(Object object) throws ManagerException {
        this.addIdentified(object);
    }

    @Override
    public void updateData(Object object) throws ManagerException {
        this.updateIdentified(object);
    }

    @Override
    public List<Object[]> queryBySql(String sql) throws ManagerException {
        Session session = getSession();
        try {
            List<Object[]> list = session.createSQLQuery(sql).list();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ManagerException("query error", e);
        } finally {
            releaseSession(session);
        }
    }

    @Override
    public List queryUniqueBySql(String sql) throws ManagerException {
        Session session = getSession();
        try {
            List list = session.createSQLQuery(sql).list();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ManagerException("query error", e);
        } finally {
            releaseSession(session);
        }
    }

    @Override
    public int excuteBySql(String sql) throws ManagerException {
        int result;
        Session session = getSession();
        try {
            SQLQuery query = session.createSQLQuery(sql);
            result = query.executeUpdate();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ManagerException("execute sql error", e);
        } finally {
            releaseSession(session);
        }
    }

    @Override
    public List excuteBySql(String sql, List<Object> paramList) throws ManagerException {
        Session session = getSession();
        try {
            SQLQuery sqlQuery = session.createSQLQuery(sql);
            if (paramList != null && paramList.size() > 0) {
                for (int index = 0; index < paramList.size(); index++) {
                    sqlQuery.setParameter(index, paramList.get(index));
                }
            }
            List list = sqlQuery.list();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            //throw new ManagerException("query error", e);
        } finally {
            releaseSession(session);
        }
        return null;
    }

    @Override
    public Integer getCountBySql(String sql, List<Object> paramList) throws ManagerException {
        Session session = getSession();
        try {
            SQLQuery sqlQuery = session.createSQLQuery(sql);
            if (paramList != null && paramList.size() > 0) {
                for (int index = 0; index < paramList.size(); index++) {
                    sqlQuery.setParameter(index, paramList.get(index));
                }
            }
            return Integer.parseInt(String.valueOf(sqlQuery.uniqueResult()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new ManagerException("query error", e);
        } finally {
            releaseSession(session);
        }
    }

    @Override
    public List getDayList(String sortColumn, Boolean desc, Date beginDay, Integer limit) {
        Session session = this.getSession();
        try {
            Criteria e = session.createCriteria(RedstarReportCountrywideDaily.class);
            e.add(Restrictions.gt("createDate", beginDay));
            if (StringUtil.isValid(sortColumn)) {
                if (desc) {
                    e.addOrder(Order.desc(sortColumn));
                } else {
                    e.addOrder(Order.asc(sortColumn));
                }
            }
            e.setFirstResult(0).setMaxResults(limit);
            return e.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.releaseSession(session);
        }
        return null;
    }

    @Override
    public Integer getHistoryUserCount() {

        int result = 0;
        Session session = getSession();
        try {
            Criteria criteria = session.createCriteria(NvwaEmployee.class);

            String column = "lastActiveTime";

            criteria.add(Restrictions.isNotNull(column));

            criteria.add(Restrictions.gt(column, new SimpleDateFormat("yyyy-MM-dd").parse("2016-05-20")));

            criteria.setProjection(Projections.rowCount());

            result = Integer.parseInt(String.valueOf(criteria.uniqueResult()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            releaseSession(session);
        }
        return result;
    }

    @Override
    public List getDayReportDataList(Class beanClass, SearchBean searchBean, Integer limit) {
        Session session = getSession();
        try {
            Criteria criteria;
            if (searchBean == null) {
                criteria = session.createCriteria(beanClass);
            } else {
                criteria = getSearchCriteria(beanClass.getName(), searchBean, session);
            }
/*            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());

            Integer year = calendar.get(Calendar.YEAR);
            Integer month = calendar.get(Calendar.MONTH) + 1;
            Integer day = calendar.get(Calendar.DATE);

            Criterion c= Restrictions.or(Restrictions.ne("year",year), Restrictions.ne("month",month));
            criteria.add(Restrictions.or(c,Restrictions.ne("day",day)));*/

            criteria.addOrder(Order.desc("year"));
            criteria.addOrder(Order.desc("month"));
            criteria.addOrder(Order.desc("day"));
            criteria.setFirstResult(0).setMaxResults(limit);
            return criteria.list();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            releaseSession(session);
        }
        return Collections.emptyList();
    }

    @Override
    public List getDataList(Class c, SearchBean searchBean, String[] orderColumns, Boolean[] orderDesc) {
        Session session = getSession();
        try {
            Criteria criteria;
            if (searchBean == null) {
                criteria = session.createCriteria(c);
            } else {
                criteria = getSearchCriteria(c.getName(), searchBean, session);
            }
            if (orderColumns != null && orderColumns.length > 0) {
                for (int index = 0; index < orderColumns.length; index++) {
                    if (orderDesc[index]) {
                        criteria.addOrder(Order.desc(orderColumns[index]));
                    } else {
                        criteria.addOrder(Order.asc(orderColumns[index]));
                    }
                }
            }
            return criteria.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            releaseSession(session);
        }
        return Collections.emptyList();
    }

    /**
     * 批量更新
     *
     * @param c
     * @param objects
     */
    @Override
    public void batchUpdateIdentified(Class c, List<Object> objects) throws ManagerException {
        if (CollectionUtil.isInvalid(objects)) {
            throw new ManagerException("没有更新的元素");
        }
        if (c == null) {
            throw new ManagerException("没有更新的Class");
        }
        Session session = getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            for (Object item : objects) {
                if (item instanceof Validatable) {
                    ((Validatable) item).validate();
                }
                session.update(c.getName(), item);
            }
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            logger.error(e.toString());
            if (tx != null) {
                tx.rollback();
            }
            throw new ManagerException("error_bean_update", e);
        } finally {
            releaseSession(session);
        }


    }

    @Override
    public List getDataList(Class c, SearchBean searchBean, String[] orderColumns, Boolean[] orderDesc, Integer first, Integer max) {
        Session session = getSession();
        try {
            Criteria criteria;
            if (searchBean == null) {
                criteria = session.createCriteria(c);
            } else {
                criteria = getSearchCriteria(c.getName(), searchBean, session);
            }
            if (orderColumns != null && orderColumns.length > 0) {
                for (int index = 0; index < orderColumns.length; index++) {
                    if (orderDesc[index]) {
                        criteria.addOrder(Order.desc(orderColumns[index]));
                    } else {
                        criteria.addOrder(Order.asc(orderColumns[index]));
                    }
                }
            }
            if (first != null && max != null) {
                criteria.setFirstResult(first).setMaxResults(max);
            }
            return criteria.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            releaseSession(session);
        }
        return Collections.emptyList();
    }

    @Override
    public Map<String, Object> queryBySql(String sql, String countSql, List<Object> paramList, Class T, Integer page, Integer pageSize) throws ManagerException {
        Session session = getSession();
        try {
            Integer offSet = (page - 1) * pageSize;
            SQLQuery sqlQuery = session.createSQLQuery(sql);
            SQLQuery countQuery = session.createSQLQuery(countSql);
            if (paramList != null && paramList.size() > 0) {
                for (int index = 0; index < paramList.size(); index++) {
                    sqlQuery.setParameter(index, paramList.get(index));
                    countQuery.setParameter(index, paramList.get(index));
                }
            }
            Integer dataCount = Integer.parseInt(countQuery.uniqueResult().toString());
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("dataCount", dataCount);
            if (dataCount != 0) {
                sqlQuery.addEntity(T);
                sqlQuery.setFirstResult(offSet).setMaxResults(pageSize);
                List list = sqlQuery.list();
                dataMap.put("dataList", list);
            } else {
                dataMap.put("dataList", Collections.emptyList());
            }
            return dataMap;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ManagerException(e.getMessage());
        } finally {
            releaseSession(session);
        }
    }

    @Override
    public PaginationDescribe searchPage(int page, int pageSize, SearchBean searchBean, String orderColumn, boolean isAsc, Class _thisClass) throws ManagerException {
        return this.searchIdentify(page, pageSize, _thisClass, searchBean, orderColumn, isAsc);
    }

    @Override
    public <T> List<T> getDataList(Collection<Object> idList, Class<T> targetClass, SearchBean searchBean, String orderColumn, Boolean desc) throws ManagerException {
        Session session = getSession();
        try {
            Criteria criteria;
            if (searchBean == null) {
                criteria = session.createCriteria(targetClass);
            } else {
                criteria = getSearchCriteria(targetClass.getName(), searchBean, session);
            }

            criteria.add(Restrictions.in("id", idList));

            if (StringUtil.isValid(orderColumn)) {
                if (desc) {
                    criteria.addOrder(Order.desc(orderColumn));
                } else {
                    criteria.addOrder(Order.asc(orderColumn));
                }
            }
            criteria.setFirstResult(0);
            criteria.setMaxResults(idList.size());
            return criteria.list();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ManagerException(e.getMessage());
        } finally {
            releaseSession(session);
        }
    }

    @Override
    public <T> List<T> getDataList(Collection<Object> idList, Class<T> targetClass, String queryColumn, SearchBean searchBean, String orderColumn, Boolean desc) throws ManagerException {
        Session session = getSession();
        try {
            Criteria criteria;
            if (searchBean == null) {
                criteria = session.createCriteria(targetClass);
            } else {
                criteria = getSearchCriteria(targetClass.getName(), searchBean, session);
            }

            criteria.add(Restrictions.in(queryColumn, idList));

            if (StringUtil.isValid(orderColumn)) {
                if (desc) {
                    criteria.addOrder(Order.desc(orderColumn));
                } else {
                    criteria.addOrder(Order.asc(orderColumn));
                }
            }
            criteria.setFirstResult(0);
            criteria.setMaxResults(idList.size());
            return criteria.list();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ManagerException(e.getMessage());
        } finally {
            releaseSession(session);
        }
    }

    public SimplePaginationDescribe getIdentifiedPageList(Class c, String selectColumnNames, String sql,String orderByColumns, List<Object> paramList, Integer page, Integer pageSize) throws ManagerException {
        Session session = getSession();
        try {
            StringBuilder sqlBuilder = new StringBuilder("Select");
            if (StringUtil.isValid(selectColumnNames)) {
                sqlBuilder.append(" ").append(selectColumnNames).append(" ");
            } else {
                sqlBuilder.append(" * ");
            }
            sqlBuilder.append(sql);
            if(StringUtil.isValid(orderByColumns)){
                sqlBuilder.append(" order by ").append(orderByColumns);
            }

            StringBuilder countSqlBuilder = new StringBuilder("Select count(*) ");
            countSqlBuilder.append(sql);
            List<Object[]> queryResult = queryBySql(countSqlBuilder.toString());
            int totalRecords = 0;
            if (CollectionUtil.isValid(queryResult)) {
                totalRecords = DataUtil.getInt(queryResult.get(0), 0);
            }


            SQLQuery sqlQuery = session.createSQLQuery(sqlBuilder.toString()).addEntity(c);
            if (paramList != null && paramList.size() > 0) {
                for (int index = 0; index < paramList.size(); index++) {
                    sqlQuery.setParameter(index, paramList.get(index));
                }
            }
//            sqlQuery.addEntity(RedstarCommunity.class);
            sqlQuery.setFirstResult((page - 1) * pageSize);
            sqlQuery.setMaxResults(pageSize);

            List list = sqlQuery.list();
            SimplePaginationDescribe result = new SimplePaginationDescribe();
            result.setCurrentRecords(list);
//            result.setTotalPages(0);//总页数
            result.setTotalRecords(totalRecords);//总记录数
            result.setCurrentPage(page);//当前页数
            result.setPageSize(pageSize);//当前返回记录数
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ManagerException(e.getMessage());
        } finally {
            releaseSession(session);
        }
    }

    @Override
    public int excuteSql(String sql, List<Object> paramList) throws ManagerException {
        Session session = getSession();
        try {
            SQLQuery sqlQuery = session.createSQLQuery(sql);
            if (paramList != null && paramList.size() > 0) {
                for (int index = 0; index < paramList.size(); index++) {
                    sqlQuery.setParameter(index, paramList.get(index));
                }
            }
            return sqlQuery.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ManagerException("query error", e);
        } finally {
            releaseSession(session);
        }
    }

    @Override
    public <T> List<T> excuteSql(String sql, List<Object> paramList, Class<T> targetClass) throws ManagerException {
        Session session = getSession();
        try {
            SQLQuery sqlQuery = session.createSQLQuery(sql);
            if (paramList != null && paramList.size() > 0) {
                for (int index = 0; index < paramList.size(); index++) {
                    sqlQuery.setParameter(index, paramList.get(index));
                }
            }
            sqlQuery.addEntity(targetClass);
            return sqlQuery.list();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ManagerException("query error", e);
        } finally {
            releaseSession(session);
        }
    }


}
