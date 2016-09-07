package com.lanchui.commonBiz.manager.ext;

import com.lanchui.commonBiz.bean.RedstarStoreUV;
import com.lanchui.commonBiz.manager.RedStarUvManager;
import com.xiwa.base.manager.AbstractBasicManager;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;


/**
 * Created by LeiYun on 2016/6/30.
 */
public class SimpleRedStarUvManager extends AbstractBasicManager implements RedStarUvManager {

    public SimpleRedStarUvManager() {
        super(RedstarStoreUV.class);
    }

    @Override
    public int getCountByDataSK(String dataSK) {
        final Session session = this.getSession();
       /* Transaction transaction = null;*/

        try {

            /*//打开事物
            transaction = session.beginTransaction();*/

            final StringBuffer sql = new StringBuffer("select count(*) from xiwa_redstar_store_uv ");
            sql.append("where dataSK = ?");

            Query query = session.createSQLQuery(sql.toString());
            query.setParameter(0, dataSK);
            return Integer.parseInt(query.uniqueResult().toString());

            //查询条数
            //Integer count = (Integer) getHibernateTemplate().find(String.valueOf(sql)).listIterator().next();
            //return count .intValue();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.releaseSession(session);
        }


        return 0;
    }
}
