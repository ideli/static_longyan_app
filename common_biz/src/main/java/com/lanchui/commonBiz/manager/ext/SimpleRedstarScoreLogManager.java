package com.lanchui.commonBiz.manager.ext;

import com.lanchui.commonBiz.bean.RedstarScoreLog;
import com.lanchui.commonBiz.manager.RedstarScoreLogManager;
import com.xiwa.base.manager.AbstractBasicManager;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zhangxuechao on 2016/6/30.
 */
public class SimpleRedstarScoreLogManager extends AbstractBasicManager implements RedstarScoreLogManager {

    public SimpleRedstarScoreLogManager() {
        super(RedstarScoreLog.class);
    }

    @Override
    public List<Object[]> getScoreLogInfo(String startDate, String endDate) {

        final Session session = this.getSession();
        List<Object[]> redstarScoreLogs = new ArrayList<Object[]>();
        try {
            StringBuffer hqlSb = new StringBuffer();
            hqlSb.append("SELECT SUM(deltaValue) as deltaValue, userId FROM xiwa_redstar_score_log ");
            hqlSb.append("WHERE createDate >= '" + startDate + "' AND createDate <= '" + endDate + "'");
            hqlSb.append(" GROUP BY userId ORDER BY deltaValue DESC");
            Query query = session.createSQLQuery(hqlSb.toString());
            redstarScoreLogs = query.list();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            this.releaseSession(session);
        }
        return redstarScoreLogs;
    }
}
