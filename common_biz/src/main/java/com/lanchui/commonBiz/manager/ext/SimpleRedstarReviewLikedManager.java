package com.lanchui.commonBiz.manager.ext;

import com.lanchui.commonBiz.bean.RedstarReviewLiked;
import com.lanchui.commonBiz.manager.RedstarReviewLikedManager;
import com.xiwa.base.manager.AbstractBasicManager;
import com.xiwa.base.util.StringUtil;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Created by lenovo on 2016/6/30.
 */
public class SimpleRedstarReviewLikedManager extends AbstractBasicManager implements RedstarReviewLikedManager {
    public SimpleRedstarReviewLikedManager() {
        super(RedstarReviewLiked.class);
    }

    @Override
    public int getCountByCondition(String userId, int reviewId) {

        final Session session = this.getSession();

        try {

            final StringBuffer sql = new StringBuffer("select count(*) from xiwa_redstar_review_liked ");
            sql.append("where reviewId= " + reviewId+"");
                    if(StringUtil.isValid(userId)){
                        sql.append(" and userId= '" + userId+"'");
                    }

            Query query = session.createSQLQuery(sql.toString());
            return Integer.parseInt(query.uniqueResult().toString());

        } catch (Exception e) {
            e.printStackTrace();
        }


        return 0;
    }
}
