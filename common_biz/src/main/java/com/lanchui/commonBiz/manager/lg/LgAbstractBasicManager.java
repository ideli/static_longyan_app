package com.lanchui.commonBiz.manager.lg;

import com.xiwa.base.bean.search.SearchBean;
import com.xiwa.base.manager.BasicManager;
import com.xiwa.base.manager.ManagerException;
import com.xiwa.base.util.ArrayUtil;
import com.xiwa.base.util.CollectionUtil;
import com.xiwa.base.util.StringUtil;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import java.util.Collection;
import java.util.List;
import com.xiwa.base.bean.Identified;
import com.xiwa.base.bean.PaginationDescribe;
import com.xiwa.base.bean.ShowAble;
import com.xiwa.base.bean.StartBean;
import com.xiwa.base.bean.UrlBean;


public class LgAbstractBasicManager<T extends Identified> extends LgAbstractManager<T> implements BasicManager<T> {
    protected Class<T> beanClass;

    public LgAbstractBasicManager() {
    }

//    public LgAbstractBasicManager(String className) throws ClassNotFoundException {
//        this(Class.forName(className));
//    }

    public LgAbstractBasicManager(Class<T> beanClass) {
        this.beanClass = beanClass;
    }

    public int addBean(Object bean) throws ManagerException {
        return this.addIdentified(bean);
    }

    public int addBeans(List<Object> beans) throws ManagerException {
        return this.addIdentifieds(beans);
    }

    public void updateBean(Object bean) throws ManagerException {
        this.updateIdentified(bean);
    }

    public void updateBeanByColumn(int operationId, String columnName, Object columnValue) throws ManagerException {
        Session session = this.getSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            StringBuilder e = new StringBuilder();
            e.append("UPDATE ").append(this.beanClass.getName()).append(" SET ");
            e.append(columnName).append(" = ? ");
            e.append(" WHERE id=?");
            Query q = session.createQuery(e.toString()).setParameter(0, columnValue).setInteger(1, operationId);
            q.executeUpdate();
            tx.commit();
        } catch (Exception var11) {
            if (tx != null) {
                tx.rollback();
            }

            throw new ManagerException(var11.getMessage(), var11);
        } finally {
            this.releaseSession(session);
        }

    }

    public void updateBeansByColumn(int[] operationIds, String columnName, Object columnValue) throws ManagerException {
        Session session = this.getSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            StringBuilder e = new StringBuilder();
            e.append("UPDATE ").append(this.beanClass.getName()).append(" SET ");
            e.append(columnName).append(" = ? ");
            e.append(" WHERE id IN (").append(ArrayUtil.toString(operationIds, ',')).append(")");
            Query q = session.createQuery(e.toString()).setParameter(0, columnValue);
            q.executeUpdate();
            tx.commit();
        } catch (Exception var11) {
            if (tx != null) {
                tx.rollback();
            }

            throw new ManagerException(var11.getMessage(), var11);
        } finally {
            this.releaseSession(session);
        }

    }

    public Object getBean(int operationBeanId) throws ManagerException {
        return this.getIdentify(operationBeanId, this.beanClass);
    }

    public UrlBean getUrlBean(String url) throws ManagerException {
        Session session = this.getSession();

        UrlBean var5;
        try {
            session.beginTransaction();
            Criteria e = session.createCriteria(this.beanClass);
            e.add(Expression.eq("url", url));
            List _list = e.list();
            if (CollectionUtil.isValid(_list)) {
                var5 = (UrlBean) _list.get(0);
                return var5;
            }

            var5 = null;
        } catch (Exception var9) {
            throw new ManagerException(var9.getMessage(), var9);
        } finally {
            this.releaseSession(session);
        }

        return var5;
    }

    public List getBeanList(int[] operationBeanIds) throws ManagerException {
        return this.getIdentifyList(operationBeanIds, this.beanClass);
    }

    public List getBeanList(Collection operationBeanIds) throws ManagerException {
        if (CollectionUtil.isValid(operationBeanIds)) {
            Session session = this.getSession();
            Transaction tx = null;

            List var5;
            try {
                tx = session.beginTransaction();
                Criteria e = session.createCriteria(this.beanClass);
                e.add(Expression.in("id", operationBeanIds));
                var5 = e.list();
            } catch (Exception var9) {
                if (tx != null) {
                    tx.rollback();
                }

                throw new ManagerException("error_bean_get_list", var9);
            } finally {
                this.releaseSession(session);
            }

            return var5;
        } else {
            return CollectionUtil.emptyList();
        }
    }

    public List getBeanList() throws ManagerException {
        return this.getIdentifyList(this.beanClass);
    }

    public List getBeanListByColumn(String columnName, Object columnValue) throws ManagerException {
        Session session = this.getSession();

        List var5;
        try {
            session.beginTransaction();
            Criteria e = session.createCriteria(this.beanClass);
            e.add(Expression.eq(columnName, columnValue));
            var5 = e.list();
        } catch (Exception var9) {
            throw new ManagerException("error_bean_list", var9);
        } finally {
            this.releaseSession(session);
        }

        return var5;
    }

    public List getBeanListByColumn(String columnName, Object columnValue, String orderColumn, boolean asc) throws ManagerException {
        Session session = this.getSession();

        List order1;
        try {
            session.beginTransaction();
            Criteria e = session.createCriteria(this.beanClass);
            e.add(Expression.eq(columnName, columnValue));
            if (StringUtil.isValid(orderColumn)) {
                Order order = null;
                if (asc) {
                    order = Order.asc(orderColumn);
                } else {
                    order = Order.desc(orderColumn);
                }

                e.addOrder(order);
            }

            order1 = e.list();
        } catch (Exception var11) {
            throw new ManagerException("error_bean_list", var11);
        } finally {
            this.releaseSession(session);
        }

        return order1;
    }

    public List getBeanList(String orderColumn, boolean asc) throws ManagerException {
        Session session = this.getSession();
        Transaction tx = null;

        List var6;
        try {
            tx = session.beginTransaction();
            StringBuilder e = new StringBuilder();
            e.append("FROM ").append(this.beanClass.getName());
            if (ShowAble.class.isAssignableFrom(this.beanClass)) {
                e.append(" WHERE showData=1 ");
            }

            e.append(" ORDER BY ").append(orderColumn);
            if (!asc) {
                e.append(" DESC");
            }

            var6 = session.createQuery(e.toString()).list();
        } catch (Exception var10) {
            if (tx != null) {
                tx.rollback();
            }

            throw new ManagerException("error_bean_list", var10);
        } finally {
            this.releaseSession(session);
        }

        return var6;
    }

    public List getBeanList(boolean isStart) throws ManagerException {
        Session session = this.getSession();
        Transaction tx = null;

        List order1;
        try {
            tx = session.beginTransaction();
            int start = isStart ? 1 : 0;
            String e;
            String order;
            if (ShowAble.class.isAssignableFrom(this.beanClass)) {
                order = " WHERE showData=1 and start=" + start + " ORDER BY id";
                e = "FROM " + this.beanClass.getName() + order;
            } else {
                order = " WHERE start=" + start + " ORDER BY id";
                e = "FROM " + this.beanClass.getName() + order;
            }

            order1 = session.createQuery(e).list();
        } catch (Exception var10) {
            if (tx != null) {
                tx.rollback();
            }

            throw new ManagerException("error_bean_list", var10);
        } finally {
            this.releaseSession(session);
        }

        return order1;
    }

    public void settingBeanStart(int id, boolean start) throws ManagerException {
        Session session = this.getSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            StartBean e = (StartBean) session.load(this.beanClass, Integer.valueOf(id));
            e.setStart(start);
            session.update(e);
            tx.commit();
        } catch (Exception var9) {
            if (tx != null) {
                tx.rollback();
            }

            throw new ManagerException(var9.getMessage(), var9);
        } finally {
            this.releaseSession(session);
        }

    }

    public PaginationDescribe getBeanPage(int page, int pageSize) throws ManagerException {
        return this.getIdentifyByPage(page, pageSize, this.beanClass);
    }

    public PaginationDescribe searchBeanPage(int page, int pageSize, SearchBean searchBean) throws ManagerException {
        return this.searchIdentify(page, pageSize, this.beanClass, searchBean);
    }

    public PaginationDescribe searchBeanPage(int page, int pageSize, SearchBean searchBean, String orderColumn, boolean isAsc) throws ManagerException {
        return this.searchIdentify(page, pageSize, this.beanClass, searchBean, orderColumn, isAsc);
    }

    public PaginationDescribe searchBeanPage(int page, int pageSize, SearchBean searchBean, String[] orderColumns, boolean[] isAscs) throws ManagerException {
        return this.searchIdentify(page, pageSize, this.beanClass.getName(), searchBean, orderColumns, isAscs);
    }

    public List searchIdentify(SearchBean searchBean) throws ManagerException {
        return this.searchIdentify(this.beanClass, searchBean);
    }

    public List searchIdentify(SearchBean searchBean, String orderColumn, boolean isAsc) throws ManagerException {
        return this.searchIdentify(this.beanClass.getName(), searchBean, orderColumn, isAsc);
    }

    public void removeBean(int operationBeanId) throws ManagerException {
        this.deleteIdentify(operationBeanId, this.beanClass);
    }

    public void removeBeans(int[] operationBeanIds) throws ManagerException {
        this.deleteIdentifyList(operationBeanIds, this.beanClass);
    }

    public void hiddenBeans(int[] operationBeanIds) throws ManagerException {
        this.hiddenIdentifyList(operationBeanIds, this.beanClass);
    }

    public void hiddenBean(int operationBeanId) throws ManagerException {
        this.hiddenIdentify(operationBeanId, this.beanClass);
    }
}

