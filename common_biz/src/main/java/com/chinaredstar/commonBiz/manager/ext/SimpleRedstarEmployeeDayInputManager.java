package com.chinaredstar.commonBiz.manager.ext;

import com.chinaredstar.commonBiz.bean.RedstarEmployeeDayInput;
import com.chinaredstar.commonBiz.manager.RedstarEmployeeDayInputManager;
import com.xiwa.base.manager.AbstractBasicManager;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/7/12.
 */
public class SimpleRedstarEmployeeDayInputManager extends AbstractBasicManager implements RedstarEmployeeDayInputManager {
    public SimpleRedstarEmployeeDayInputManager() {
        super(RedstarEmployeeDayInput.class);
    }

    @Override
    public List<RedstarEmployeeDayInput> getRedstarEmployeeDayInputInfo(int year, int month, int day) {
        List<RedstarEmployeeDayInput> result = new ArrayList<RedstarEmployeeDayInput>();
        final Session session = this.getSession();
        try {
            StringBuffer hqlSb = new StringBuffer();
            hqlSb.append("SELECT * FROM xiwa_redstar_report_employee_input_daily ");
            hqlSb.append("WHERE year = " + year + " AND month = " + month + " AND day = " + day);
            Query query = session.createSQLQuery(hqlSb.toString()).addEntity(RedstarEmployeeDayInput.class);
            result = query.list();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            this.releaseSession(session);
        }
        return result;
    }
}
