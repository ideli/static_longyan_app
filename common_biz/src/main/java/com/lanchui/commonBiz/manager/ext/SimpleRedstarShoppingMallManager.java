package com.lanchui.commonBiz.manager.ext;

import com.lanchui.commonBiz.bean.RedStarMember;
import com.lanchui.commonBiz.bean.RedstarEmployee;
import com.lanchui.commonBiz.bean.RedstarShoppingMall;
import com.lanchui.commonBiz.manager.RedstarShoppingMallManager;
import com.xiwa.base.manager.AbstractBasicManager;
import com.xiwa.base.manager.ManagerException;
import com.xiwa.base.util.CollectionUtil;
import com.xiwa.base.util.StringUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 * Created by lenovo on 2016/4/26.
 */
public class SimpleRedstarShoppingMallManager extends AbstractBasicManager implements RedstarShoppingMallManager {
    public SimpleRedstarShoppingMallManager() {
        super(RedstarShoppingMall.class);
    }

    @Override
    public List getBeanList(Collection operationBeanIds, String queryColumn, String orderColumn, Boolean desc,Map<String,Object> eqMapParams) throws ManagerException {
        if(CollectionUtil.isValid(operationBeanIds)) {
            Session session = this.getSession();
            Transaction tx = null;
            List var5;
            try {

                if(CollectionUtils.isEmpty(operationBeanIds)){
                    return null;
                }

                tx = session.beginTransaction();
                Criteria e = session.createCriteria(this.beanClass);
                e.add(Restrictions.in(queryColumn, operationBeanIds));

                if(eqMapParams!=null&&eqMapParams.size()>0){
                    for (String key : eqMapParams.keySet()){
                        e.add(Restrictions.eq(key,eqMapParams.get(key)));
                    }
                }

                if(StringUtil.isValid(orderColumn)) {
                    Order order = null;
                    if(desc) {
                        order = Order.desc(orderColumn);
                    } else {
                        order = Order.asc(orderColumn);
                    }
                    e.addOrder(order);
                }
                var5 = e.list();
            } catch (Exception var9) {
                if(tx != null) {
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

    @Override
    public RedstarShoppingMall getShoppingMallByCode(String code) throws ManagerException {
        List<RedstarShoppingMall> list = this.getBeanListByColumn("mallCode", code);
        if (CollectionUtil.isValid(list)) {
            return list.get(0);
        }
        return null;
    }
}
