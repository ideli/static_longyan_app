package com.chinaredstar.nvwaBiz.manager;


import com.xiwa.base.bean.CreateDateBean;
import com.xiwa.base.bean.Identified;
import com.xiwa.base.bean.PaginationDescribe;
import com.xiwa.base.bean.ShowAble;
import com.xiwa.base.bean.SimplePaginationDescribe;
import com.xiwa.base.bean.Validatable;
import com.xiwa.base.bean.search.OrderBean;
import com.xiwa.base.bean.search.SearchBean;
import com.xiwa.base.manager.Manager;
import com.xiwa.base.manager.ManagerException;
import com.xiwa.base.util.ArrayUtil;
import com.xiwa.base.util.CollectionUtil;
import com.xiwa.base.util.DataUtil;
import com.xiwa.base.util.StringUtil;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.impl.CriteriaImpl;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public abstract class NvwaAbstractManager<T> extends HibernateDaoSupport implements Manager {
    private static Logger logger = Logger.getLogger(NvwaAbstractManager.class);

    public NvwaAbstractManager() {
    }

    protected Criteria getSearchCriteria(String entityName, SearchBean searchBean, Session session) throws ManagerException {
        if (searchBean.isValidSearchBean()) {
            Criteria criteria = session.createCriteria(entityName);
            criteria.add(searchBean.getSearchCriterion());
            return criteria;
        } else {
            throw new ManagerException("error_search_invalid");
        }
    }

    public List searchIdentify(Class beanTable, SearchBean searchBean) throws ManagerException {
        return this.searchIdentify(beanTable.getName(), searchBean);
    }

    public List searchIdentify(String entityName, SearchBean searchBean) throws ManagerException {
        return this.searchIdentify(entityName, searchBean, (String) null, false);
    }

    public List searchIdentify(String entityName, SearchBean searchBean, String orderColumn, boolean isAsc) throws ManagerException {
        Session session = this.getSession();
        Transaction tx = null;

        List var8;
        try {
            tx = session.beginTransaction();
            Criteria e = this.getSearchCriteria(entityName, searchBean, session);
            if (StringUtil.isValid(orderColumn)) {
                e.addOrder(isAsc ? Order.asc(orderColumn) : Order.desc(orderColumn));
            }

            var8 = e.list();
        } catch (ManagerException var13) {
            var13.printStackTrace();
            logger.error(var13.getMessage());
            logger.error(var13.toString());
            throw var13;
        } catch (Exception var14) {
            var14.printStackTrace();
            logger.error(var14.getMessage());
            logger.error(var14.toString());
            if (tx != null) {
                tx.rollback();
            }

            throw new ManagerException("error_bean_search", var14);
        } finally {
            this.releaseSession(session);
        }

        return var8;
    }

    public PaginationDescribe<T> searchIdentify(int page, int pageSize, Class beanTable, SearchBean searchBean) throws ManagerException {
        return this.searchIdentify(page, pageSize, (Class) beanTable, searchBean, (String) null, false);
    }

    public PaginationDescribe<T> searchIdentify(int page, int pageSize, Class beanTable, SearchBean searchBean, String orderColumn, boolean isAsc) throws ManagerException {
        return this.searchIdentify(page, pageSize, beanTable.getName(), searchBean, orderColumn, isAsc);
    }

    public PaginationDescribe<T> searchIdentify(int page, int pageSize, String entityName, SearchBean searchBean, String orderColumn, boolean isAsc) throws ManagerException {
        return this.searchIdentify(page, pageSize, entityName, searchBean, new String[]{orderColumn}, new boolean[]{isAsc});
    }

    public PaginationDescribe<T> searchIdentify(int page, int pageSize, String entityName, SearchBean searchBean, String[] orderColumns, boolean[] isAscs) throws ManagerException {
        page = DataUtil.getInt(Integer.valueOf(page), 1);
        Session session = this.getSession();
        Transaction tx = null;

        PaginationDescribe var19;
        try {
            tx = session.beginTransaction();
            Criteria e = this.getSearchCriteria(entityName, searchBean, session);
            if (ArrayUtil.isValid(orderColumns)) {
                int orderNum = orderColumns.length;

                for (int i = 0; i < orderNum; ++i) {
                    String orderColumn = orderColumns[i];
                    if (StringUtil.isValid(orderColumn)) {
                        boolean isAsc = isAscs[i];
                        e.addOrder(isAsc ? Order.asc(orderColumn) : Order.desc(orderColumn));
                    }
                }
            }

            var19 = this.getPaginationDescribe(e, page, pageSize);
        } catch (Exception var17) {
            var17.printStackTrace();
            logger.error(var17.getMessage());
            logger.error(var17.toString());
            if (tx != null) {
                tx.rollback();
            }

            throw new ManagerException("error_bean_search", var17);
        } finally {
            this.releaseSession(session);
        }

        return var19;
    }

    public PaginationDescribe<T> searchIdentify(int page, int pageSize, String entityName, SearchBean searchBean, List<OrderBean> orderBeans) throws ManagerException {
        page = DataUtil.getInt(Integer.valueOf(page), 1);
        Session session = this.getSession();
        Transaction tx = null;

        PaginationDescribe i$1;
        try {
            tx = session.beginTransaction();
            Criteria e = this.getSearchCriteria(entityName, searchBean, session);
            if (CollectionUtil.isValid(orderBeans)) {
                Iterator i$ = orderBeans.iterator();

                while (i$.hasNext()) {
                    OrderBean orderBean = (OrderBean) i$.next();
                    if (com.xiwa.base.bean.search.Order.ASC.equals(orderBean.getOrder())) {
                        e.addOrder(Order.asc(orderBean.getOrderName()));
                    }

                    if (com.xiwa.base.bean.search.Order.DESC.equals(orderBean.getOrder())) {
                        e.addOrder(Order.desc(orderBean.getOrderName()));
                    }
                }
            }

            i$1 = this.getPaginationDescribe(e, page, pageSize);
        } catch (Exception var14) {
            var14.printStackTrace();
            logger.error(var14.getMessage());
            logger.error(var14.toString());
            if (tx != null) {
                tx.rollback();
            }

            throw new ManagerException("error_bean_search", var14);
        } finally {
            this.releaseSession(session);
        }

        return i$1;
    }

    protected PaginationDescribe<T> getPaginationDescribe(Criteria searchC, int page, int pageSize) {
        SimplePaginationDescribe pageDesc = new SimplePaginationDescribe();
        pageDesc.setPageSize(pageSize);
        pageDesc.setCurrentPage(page);
        CriteriaImpl tmpCri = (CriteriaImpl) searchC;
        Projection tmpProjection = tmpCri.getProjection();
        int totalRecords = Integer.valueOf(String.valueOf((Long) searchC.setProjection(Projections.rowCount()).list().get(0))).intValue();
        searchC.setProjection(tmpProjection);
        if (tmpProjection == null) {
            searchC.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        }

        int totalPages = totalRecords / pageSize + (totalRecords % pageSize == 0 ? 0 : 1);
        pageDesc.setTotalPages(totalPages);
        pageDesc.setTotalRecords(totalRecords);
        searchC.setFirstResult(pageSize * (page - 1));
        searchC.setMaxResults(pageSize);
        List searchList = searchC.list();
        pageDesc.setCurrentRecords(searchList);
        return pageDesc;
    }

    public int addIdentified(Object bean) throws ManagerException {
        ArrayList list = new ArrayList();
        list.add(bean);
        return this.addIdentified(list, bean.getClass().getName());
    }

    public int addIdentifieds(List<Object> beans) throws ManagerException {
        if (CollectionUtil.isValid(beans)) {
            return this.addIdentified(beans, beans.get(0).getClass().getName());
        } else {
            throw new ManagerException("beans length error");
        }
    }

    public int addIdentified(List<Object> beans, String entityName) throws ManagerException {
        Session session = this.getSession();
        Transaction tx = null;

        try {
            int e = -1;
            tx = session.beginTransaction();

            Object item;
            for (Iterator i$ = beans.iterator(); i$.hasNext(); e = ((Integer) session.getIdentifier(item)).intValue()) {
                item = i$.next();
                if (item instanceof Validatable) {
                    ((Validatable) item).validate();
                }

                session.save(entityName, item);
            }

            tx.commit();
            int i$1 = e;
            return i$1;
        } catch (Exception var11) {
            var11.printStackTrace();
            logger.error(var11.getMessage());
            logger.error(var11.toString());
            if (tx != null) {
                tx.rollback();
            }

            throw new ManagerException("error_bean_add", var11);
        } finally {
            this.releaseSession(session);
        }
    }

    public void updateIdentified(Object o) throws ManagerException {
        this.updateIdentified(o, o.getClass().getName());
    }

    public void updateIdentified(Object o, String entityName) throws ManagerException {
        Session session = this.getSession();
        Transaction tx = null;

        try {
            if (o instanceof Validatable) {
                ((Validatable) o).validate();
            }

            tx = session.beginTransaction();
            session.update(entityName, o);
            tx.commit();
        } catch (Exception var9) {
            var9.printStackTrace();
            logger.error(var9.getMessage());
            logger.error(var9.toString());
            if (tx != null) {
                tx.rollback();
            }

            throw new ManagerException("error_bean_update", var9);
        } finally {
            this.releaseSession(session);
        }

    }

    public Identified getIdentify(int id, Class c) throws ManagerException {
        return this.getIdentify(id, c.getName());
    }

    public Identified getIdentify(int id, String entityName) throws ManagerException {
        return this.getIdentify(id, entityName, false);
    }

    public Identified getIdentify(int id, String entityName, boolean isNullAble) throws ManagerException {
        Session session = this.getSession();
        Transaction tx = null;

        Identified var7;
        try {
            tx = session.beginTransaction();
            Identified e = null;
            if (isNullAble) {
                e = (Identified) session.get(entityName, Integer.valueOf(id));
            } else {
                e = (Identified) session.load(entityName, Integer.valueOf(id));
            }

            var7 = e;
        } catch (Exception var11) {
            var11.printStackTrace();
            logger.error(var11.getMessage());
            logger.error(var11.toString());
            if (tx != null) {
                tx.rollback();
            }

            throw new ManagerException("error_bean_get", var11);
        } finally {
            this.releaseSession(session);
        }

        return var7;
    }

    public List<Identified> getIdentifyByColumn(String columnName, Object columnValue, Class c) throws ManagerException {
        return this.getIdentifyByColumn(columnName, columnValue, c, (String) null, false);
    }

    public List<Identified> getIdentifyByColumn(String columnName, Object columnValue, Class c, String orderColumn, boolean isAsc) throws ManagerException {
        Session session = this.getSession();

        List var8;
        try {
            Criteria e = session.createCriteria(c);
            e.add(Expression.eq(columnName, columnValue));
            if (StringUtil.isValid(orderColumn)) {
                e.addOrder(isAsc ? Order.asc(orderColumn) : Order.desc(orderColumn));
            }

            var8 = e.list();
        } catch (Exception var12) {
            var12.printStackTrace();
            logger.error(var12.getMessage());
            logger.error(var12.toString());
            throw new ManagerException("error_bean_get", var12);
        } finally {
            this.releaseSession(session);
        }

        return var8;
    }

    public List<Identified> getIdentifyList(int[] ids, Class c) throws ManagerException {
        return this.getIdentifyList(ids, c.getName());
    }

    public List<Identified> getIdentifyList(int[] ids, String entityName) throws ManagerException {
        if (ArrayUtil.isValid(ids)) {
            Session session = this.getSession();
            Transaction tx = null;

            try {
                tx = session.beginTransaction();
                Criteria e = session.createCriteria(entityName);
                ArrayList idList = new ArrayList();
                int[] arr$ = ids;
                int len$ = ids.length;

                for (int i$ = 0; i$ < len$; ++i$) {
                    int id = arr$[i$];
                    idList.add(Integer.valueOf(id));
                }

                e.add(Expression.in("id", idList));
                List var16 = e.list();
                return var16;
            } catch (Exception var14) {
                var14.printStackTrace();
                logger.error(var14.getMessage());
                logger.error(var14.toString());
                if (tx != null) {
                    tx.rollback();
                }

                throw new ManagerException("error_bean_get_list", var14);
            } finally {
                this.releaseSession(session);
            }
        } else {
            return CollectionUtil.emptyList();
        }
    }

    public List<Identified> getIdentifyList(Class c) throws ManagerException {
        Session session = this.getSession();
        Transaction tx = null;

        List order1;
        try {
            tx = session.beginTransaction();
            String e;
            String order;
            if (ShowAble.class.isAssignableFrom(c)) {
                order = " WHERE showData=1 ORDER BY id";
                e = "FROM " + c.getName() + order;
            } else {
                order = " ORDER BY id";
                e = "FROM " + c.getName() + order;
            }

            order1 = session.createQuery(e).list();
        } catch (Exception var9) {
            var9.printStackTrace();
            logger.error(var9.getMessage());
            logger.error(var9.toString());
            if (tx != null) {
                tx.rollback();
            }

            throw new ManagerException("error_bean_list", var9);
        } finally {
            this.releaseSession(session);
        }

        return order1;
    }

    public PaginationDescribe<T> getIdentifyByPage(int page, int pageSize, Class beanTable) throws ManagerException {
        return CreateDateBean.class.isAssignableFrom(beanTable) ? this.getIdentifyByPage(page, pageSize, beanTable, "createDate", true) : this.getIdentifyByPage(page, pageSize, beanTable, "id", false);
    }

    public PaginationDescribe<T> getIdentifyByPage(int page, int pageSize, Class beanTable, String order, boolean isDesc) throws ManagerException {
        return this.getIdentifyByPage(page, pageSize, beanTable.getName(), beanTable, order, isDesc);
    }

    public PaginationDescribe<T> getIdentifyByPage(int page, int pageSize, String entityName, Class beanTable, String order, boolean isDesc) throws ManagerException {
        return this.getIdentifyByPage(page, pageSize, entityName, beanTable, (Criterion) null, order, isDesc);
    }

    public PaginationDescribe<T> getIdentifyByPage(int page, int pageSize, Class beanTable, Criterion additional) throws ManagerException {
        return CreateDateBean.class.isAssignableFrom(beanTable) ? this.getIdentifyByPage(page, pageSize, beanTable.getName(), beanTable, additional, "createDate", true) : this.getIdentifyByPage(page, pageSize, beanTable.getName(), beanTable, additional, "id", true);
    }

    public PaginationDescribe<T> getIdentifyByPage(int page, int pageSize, String entityName, Class beanTable, Criterion additional, String order, boolean isDesc) throws ManagerException {
        page = DataUtil.getInt(Integer.valueOf(page), 1);
        page = page <= 0 ? 1 : page;
        Session session = this.getSession();
        Transaction tx = null;

        PaginationDescribe var11;
        try {
            tx = session.beginTransaction();
            Criteria e = session.createCriteria(entityName);
            if (StringUtil.isValid(order)) {
                e.addOrder(isDesc ? Order.desc(order) : Order.asc(order));
            }

            if (ShowAble.class.isAssignableFrom(beanTable)) {
                e.add(Expression.eq("showData", Boolean.valueOf(true)));
            }

            if (additional != null) {
                e.add(additional);
            }

            var11 = this.getPaginationDescribe(e, page, pageSize);
        } catch (Exception var15) {
            var15.printStackTrace();
            logger.error(var15.getMessage());
            logger.error(var15.toString());
            if (tx != null) {
                tx.rollback();
            }

            throw new ManagerException("error_bean_list_by_page", String.valueOf(page), var15);
        } finally {
            this.releaseSession(session);
        }

        return var11;
    }

    public void deleteIdentifyBySQL(int id, String tableName) throws ManagerException {
        Session session = this.getSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.connection().createStatement().execute("DELETE FROM " + tableName + " WHERE id=" + id);
            tx.commit();
        } catch (Exception var9) {
            var9.printStackTrace();
            logger.error(var9.getMessage());
            logger.error(var9.toString());
            if (tx != null) {
                tx.rollback();
            }

            throw new ManagerException("error_bean_delete", var9);
        } finally {
            this.releaseSession(session);
        }

    }

    public void deleteIdentify(int id, Class c) throws ManagerException {
        Session session = this.getSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Identified e = (Identified) session.load(c, Integer.valueOf(id));
            session.delete(e);
            tx.commit();
        } catch (Exception var9) {
            var9.printStackTrace();
            logger.error(var9.getMessage());
            logger.error(var9.toString());
            if (tx != null) {
                tx.rollback();
            }

            throw new ManagerException("error_bean_delete", var9);
        } finally {
            this.releaseSession(session);
        }

    }

    public void deleteIdentify(int id, String entityName) throws ManagerException {
        Session session = this.getSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Identified e = (Identified) session.load(entityName, Integer.valueOf(id));
            session.delete(e);
            tx.commit();
        } catch (Exception var9) {
            var9.printStackTrace();
            logger.error(var9.getMessage());
            logger.error(var9.toString());
            if (tx != null) {
                tx.rollback();
            }

            throw new ManagerException("error_bean_delete", var9);
        } finally {
            this.releaseSession(session);
        }

    }

    public void deleteIdentifyList(int[] idList, Class c) throws ManagerException {
        Session session = this.getSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            int[] e = idList;
            int len$ = idList.length;

            for (int i$ = 0; i$ < len$; ++i$) {
                int id = e[i$];
                Identified identified = (Identified) session.load(c, Integer.valueOf(id));
                session.delete(identified);
            }

            tx.commit();
        } catch (Exception var13) {
            var13.printStackTrace();
            logger.error(var13.getMessage());
            logger.error(var13.toString());
            if (tx != null) {
                tx.rollback();
            }

            throw new ManagerException("error_bean_delete", var13);
        } finally {
            this.releaseSession(session);
        }
    }

    public void deleteIdentifyList(int[] idList, String entityName) throws ManagerException {
        Session session = this.getSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            int[] e = idList;
            int len$ = idList.length;

            for (int i$ = 0; i$ < len$; ++i$) {
                int id = e[i$];
                Identified identified = (Identified) session.load(entityName, Integer.valueOf(id));
                session.delete(identified);
            }

            tx.commit();
        } catch (Exception var13) {
            var13.printStackTrace();
            logger.error(var13.getMessage());
            logger.error(var13.toString());
            if (tx != null) {
                tx.rollback();
            }

            throw new ManagerException("error_bean_delete", var13);
        } finally {
            this.releaseSession(session);
        }
    }

    public void hiddenIdentify(int id, Class c) throws ManagerException {
        this.hiddenIdentify(id, c.getName());
    }

    public void hiddenIdentify(int id, String entityName) throws ManagerException {
        Session session = this.getSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            ShowAble e = (ShowAble) session.load(entityName, Integer.valueOf(id));
            e.setShowData(false);
            session.saveOrUpdate(e);
            tx.commit();
        } catch (Exception var9) {
            var9.printStackTrace();
            logger.error(var9.getMessage());
            logger.error(var9.toString());
            throw new ManagerException("error_bean_hidden", var9);
        } finally {
            this.releaseSession(session);
        }

    }

    public void hiddenIdentifyList(int[] idList, Class c) throws ManagerException {
        this.hiddenIdentifyList(idList, c.getName());
    }

    public void hiddenIdentifyList(int[] idList, String entityName) throws ManagerException {
        Session session = this.getSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            StringBuilder e = new StringBuilder();
            e.append(" Update ").append(entityName).append(" SET showData=0 ").append(" WHERE id IN (").append(ArrayUtil.toString(idList, ',')).append(")");
            Query q = session.createQuery(e.toString());
            q.executeUpdate();
            tx.commit();
        } catch (Exception var10) {
            var10.printStackTrace();
            logger.error(var10.getMessage());
            logger.error(var10.toString());
            throw new ManagerException("error_bean_hidden", var10);
        } finally {
            this.releaseSession(session);
        }

    }

    public void deleteByOperationObject(String tableName, int objectId, String identified) throws ManagerException {
        Session session = this.getSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            StringBuffer e = new StringBuffer();
            e.append("DELETE FROM ");
            e.append(tableName);
            e.append(" WHERE objectId = ? AND objectIdentified = ? ");
            PreparedStatement ps = session.connection().prepareStatement(e.toString());
            ps.setInt(1, objectId);
            ps.setString(2, identified);
            ps.execute();
            tx.commit();
        } catch (Exception var11) {
            var11.printStackTrace();
            logger.error(var11.getMessage());
            logger.error(var11.toString());
            if (tx != null) {
                tx.rollback();
            }

            throw new ManagerException("error_delete_by_objectIdentified", var11);
        } finally {
            this.releaseSession(session);
        }

    }
}