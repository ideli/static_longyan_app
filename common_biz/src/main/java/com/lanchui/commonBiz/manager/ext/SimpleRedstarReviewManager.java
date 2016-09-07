package com.lanchui.commonBiz.manager.ext;

import com.lanchui.commonBiz.bean.RedstarReview;
import com.lanchui.commonBiz.manager.RedstarReviewManager;
import com.xiwa.base.manager.AbstractBasicManager;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Created by lenovo on 2016/6/2.
 */
public class SimpleRedstarReviewManager extends AbstractBasicManager implements RedstarReviewManager {
    public SimpleRedstarReviewManager() {
        super(RedstarReview.class);
    }

    @Override
    public int getCountByCondition( String objectId, String userId,String objectCode) {

        final Session session = this.getSession();

        try {

            final StringBuffer sql = new StringBuffer("select count(*) from xiwa_redstar_review ");
            sql.append("where objectId = '" + objectId+"'")
                    .append(" and userId = '" + userId+"'")
                    .append(" and objectCode = '" + objectCode + "'")
                    .append(" and showData = true");

            Query query = session.createSQLQuery(sql.toString());
            return Integer.parseInt(query.uniqueResult().toString());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.releaseSession(session);
        }


        return 0;
    }
}
