package com.lanchui.commonBiz.manager.ext;

import com.lanchui.commonBiz.bean.RedstarReviewLabel;
import com.lanchui.commonBiz.manager.RedstarReviewLabelManager;
import com.xiwa.base.manager.AbstractBasicManager;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Created by lenovo on 2016/6/2.
 */
public class SimpleRedstarReviewLabelManager extends AbstractBasicManager implements RedstarReviewLabelManager {
    public SimpleRedstarReviewLabelManager() {
        super(RedstarReviewLabel.class);
    }

    @Override
    public int getCountByCode(String code) {
         final Session session = this.getSession();

        try {

            final StringBuffer sql = new StringBuffer("select count(*) from xiwa_redstar_review_label ");
            sql.append("where code = '" + code+"'");

            Query query = session.createSQLQuery(sql.toString());

            System.out.println(query.uniqueResult().toString());

            return Integer.parseInt(query.uniqueResult().toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}
