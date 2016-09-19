package com.chinaredstar.nvwaBiz.manager.ext;

import com.chinaredstar.nvwaBiz.bean.NvwaRole;
import com.chinaredstar.nvwaBiz.manager.NvwaEmployeeManager;
import com.chinaredstar.nvwaBiz.bean.NvwaEmployee;
import com.xiwa.base.bean.search.SearchBean;
import com.xiwa.base.bean.search.ext.TextSearch;
import com.xiwa.base.manager.AbstractBasicManager;
import com.xiwa.base.manager.ManagerException;
import com.xiwa.base.util.CollectionUtil;
import com.xiwa.base.util.DataUtil;
import com.xiwa.base.util.StringUtil;
import com.xiwa.security.bean.Authorized;
import com.xiwa.security.bean.ext.SimpleAuthorized;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2016/4/22.
 */
public class SimpleNvwaEmployeeManager extends AbstractBasicManager implements NvwaEmployeeManager {

    public SimpleNvwaEmployeeManager() {
        super(NvwaEmployee.class);
    }

    @Override
    public NvwaEmployee getEmployeeByPhone(String phone) throws ManagerException {
        TextSearch phoneSearch = new TextSearch("mobilePhone");
        phoneSearch.setSearchValue(phone);
        List<NvwaEmployee> list = this.searchIdentify(phoneSearch);
        if (CollectionUtil.isValid(list)) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public boolean isRegister(String phone) throws ManagerException {
        NvwaEmployee employee = this.getEmployeeByPhone(phone);
        if (employee != null) {
            //验证是否有账号
            TextSearch phoneSearch = new TextSearch("account");
            phoneSearch.setSearchValue(phone);
            List<Authorized> authorizedList = this.searchIdentify(Authorized.class, phoneSearch);
            if (CollectionUtil.isValid(authorizedList)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<SimpleAuthorized> searchSimpleAuthorized(SearchBean multiSearchBean) throws ManagerException {
        return this.searchIdentify(SimpleAuthorized.class, multiSearchBean);
    }

    public void addAuthorized(SimpleAuthorized authorized) throws ManagerException {
        this.addIdentified(authorized);
    }

    @Override
    public void updateAuthorized(SimpleAuthorized authorized) throws ManagerException {
        this.updateIdentified(authorized);
    }

    @Override
    public List getBeanList(Collection operationBeanIds, String queryColumn, String orderColumn, Boolean desc) throws ManagerException {
        if (CollectionUtil.isValid(operationBeanIds)) {
            Session session = this.getSession();
            Transaction tx = null;
            List var5;
            try {
                tx = session.beginTransaction();
                Criteria e = session.createCriteria(this.beanClass);
                e.add(Restrictions.in(queryColumn, operationBeanIds));

                if (StringUtil.isValid(orderColumn)) {
                    Order order = null;
                    if (desc) {
                        order = Order.desc(orderColumn);
                    } else {
                        order = Order.asc(orderColumn);
                    }
                    e.addOrder(order);
                }
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

    /**
     * 根据红星内部code获取员工信息
     *
     * @param code
     * @return
     * @throws ManagerException
     */
    public NvwaEmployee getEmployeeByCode(String code) throws ManagerException {
        List<NvwaEmployee> list = this.getBeanListByColumn("employeeCode", code);
        if (CollectionUtil.isValid(list)) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<NvwaRole> getRoleByEmployeeId(int employeeId) throws ManagerException {
        //获取权限
        List<SimpleAuthorized> authorizedList = this.getIdentifyByColumn("objectId", employeeId, SimpleAuthorized.class);
        SimpleAuthorized authorized = null;
        if (CollectionUtil.isValid(authorizedList)) {
            authorized = authorizedList.get(0);
        }
        //获取角色
        if (authorized == null) {
            throw new ManagerException("没有权限");
        }
        if (StringUtil.isInvalid(authorized.getRoles())) {
//            throw new ManagerException("没有角色权限");
            return null;
        }
        List<NvwaRole> roleList = this.getIdentifyList(DataUtil.getIntArray(authorized.getRoles()), NvwaRole.class);



        return roleList;
    }

    @Override
    public Map<String,Object> getDataListByIds(Integer page, Integer pageSize, Collection operationBeanIds, String queryColumn, String orderColumn, Boolean desc) throws ManagerException {
        Map<String,Object> result = new HashMap<String, Object>();
        if (CollectionUtil.isValid(operationBeanIds)) {
            Session session = this.getSession();
            Transaction tx = null;
            List var5;
            try {
                tx = session.beginTransaction();
                Criteria e = session.createCriteria(this.beanClass);
                e.add(Restrictions.in(queryColumn, operationBeanIds));

                Criteria criteria = session.createCriteria(this.beanClass);
                criteria.add(Restrictions.in(queryColumn, operationBeanIds));
                criteria.setProjection(Projections.rowCount());
                Integer totalRecords = Integer.valueOf(
                      String.valueOf(criteria.list().get(0)));
                result.put("total",totalRecords);
                e.setFirstResult((page-1)*pageSize);
                e.setMaxResults(pageSize);
                if (StringUtil.isValid(orderColumn)) {
                    Order order = null;
                    if (desc) {
                        order = Order.desc(orderColumn);
                    } else {
                        order = Order.asc(orderColumn);
                    }
                    e.addOrder(order);
                }
                var5 = e.list();
            } catch (Exception var9) {
                if (tx != null) {
                    tx.rollback();
                }
                throw new ManagerException("error_bean_get_list", var9);
            } finally {
                this.releaseSession(session);
            }
            result.put("data",var5);
            return result;
        } else {
            return null;
        }
    }
};
