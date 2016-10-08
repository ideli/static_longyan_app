package com.chinaredstar.longyan.task.old;

import com.chinaredstar.commonBiz.manager.*;
import com.chinaredstar.longyan.bean.constant.LanchuiConstant;
import com.chinaredstar.commonBiz.bean.RedstarEmployeeDayInput;
import com.chinaredstar.commonBiz.bean.RedstarEmployeeMonth;
import com.chinaredstar.commonBiz.bean.RedstarTaskLog;
import com.chinaredstar.commonBiz.manager.RedstarEmployeeDayInputManager;
import com.xiwa.base.manager.ManagerException;
import com.xiwa.base.util.DataUtil;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mdc on 2016/7/12.
 */
@Component(value = "scoreLogTask")
public class ScoreLogTask implements LanchuiConstant {

    @Autowired
    private RedstarScoreLogManager redstarScoreLogManager;


    @Autowired
    private RedstarCommonManager redstarCommonManager;

    @Autowired
    private RedstarEmployeeMonthManager redstarEmployeeMonthManager;

    @Autowired
    private RedstarEmployeeDayInputManager redstarEmployeeDayInputManager;

    @Autowired
    private RedstarTaskLogManager redstarTaskLogManager;

    /**
     * 1.拉取 xiwa_redstar_score_log 获取积分变更历史记录
     * 2.分别计算“今天”和“昨天”的增加的积分总和（scoreAmount），以及排名情况（scoreRank），写入 xiwa_redstar_report_employee_input_daily。
     * 3.分别计算“当月”和“上月”的增加的积分总和（scoreAmount），以及排名情况（scoreRank），写入 xiwa_redstar_report_employee_input_month。
     */


    public void execute() {

        Long startTime = System.currentTimeMillis();
        RedstarTaskLog redstarTaskLog = new RedstarTaskLog();
        redstarTaskLog.setTaskClassName(this.getClass().getSimpleName());
        redstarTaskLog.setLogDatetime(new Date());
        redstarTaskLog.setRemark("积分排名统计");
        redstarTaskLog.setAction(taskLogStart);
        try {
            redstarTaskLogManager.addBean(redstarTaskLog);
        } catch (ManagerException e) {
            e.printStackTrace();
        }

        DateTime today = DateTime.now();
        DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:SS");
        today = DateTime.parse(today.toString("yyyy-MM-dd") + " 00:00:00", format);
        DateTime yesterday = today.minusDays(1);
        DateTime tomorrow = today.plusDays(1);
        String date = today.getYear() + "-" + today.getMonthOfYear() + "-01 00:00:00";
        DateTime nowMonth = DateTime.parse(date, format);
        DateTime lastMonth = nowMonth.minusMonths(1);
        DateTime nextMonth = nowMonth.plusMonths(1);
        List<Object> dailyUpdate = new ArrayList<Object>();
        List<Object> monthUpdate = new ArrayList<Object>();

        List<Object[]> yesterdayList = redstarScoreLogManager.getScoreLogInfo(yesterday.toString("yyyy-MM-dd HH:mm:SS"), today.toString("yyyy-MM-dd HH:mm:SS"));
        List<RedstarEmployeeDayInput> yesterdayDailyList = redstarEmployeeDayInputManager.getRedstarEmployeeDayInputInfo(yesterday.getYear(), yesterday.getMonthOfYear(), yesterday.getDayOfMonth());

        for (int i = 0; i < yesterdayList.size(); i++) {
            Object[] objects = yesterdayList.get(i);
            for (RedstarEmployeeDayInput input : yesterdayDailyList) {
                if (input.getEmployeeId() == DataUtil.getInt(objects[1], 0)) {
                    input.setScoreAmount(DataUtil.getInt(objects[0], 0));
                    input.setScoreRank(i + 1);
                    dailyUpdate.add(input);
                    break;
                }
            }
        }
        List<Object[]> todayList = redstarScoreLogManager.getScoreLogInfo(today.toString("yyyy-MM-dd HH:mm:SS"), tomorrow.toString("yyyy-MM-dd HH:mm:SS"));
        List<RedstarEmployeeDayInput> todayDailyList = redstarEmployeeDayInputManager.getRedstarEmployeeDayInputInfo(today.getYear(), today.getMonthOfYear(), today.getDayOfMonth());
        for (int i = 0; i < todayList.size(); i++) {
            Object[] objects = todayList.get(i);
            for (RedstarEmployeeDayInput input : todayDailyList) {
                if (input.getEmployeeId() == DataUtil.getInt(objects[1], 0)) {
                    input.setScoreAmount(DataUtil.getInt(objects[0], 0));
                    input.setScoreRank(i + 1);
                    dailyUpdate.add(input);
                    break;
                }
            }
        }

        List<Object[]> lastMonthList = redstarScoreLogManager.getScoreLogInfo(lastMonth.toString("yyyy-MM-dd HH:mm:SS"), nowMonth.toString("yyyy-MM-dd HH:mm:SS"));
        List<RedstarEmployeeMonth> lastMonths = redstarEmployeeMonthManager.getRedstarEmployeeDayInputInfo(lastMonth.getYear(), lastMonth.getMonthOfYear());
        for (int i = 0; i < lastMonthList.size(); i++) {
            Object[] objects = lastMonthList.get(i);
            for (RedstarEmployeeMonth input : lastMonths) {
                if (input.getEmployeeId() == DataUtil.getInt(objects[1], 0)) {
                    input.setScoreAmount(DataUtil.getInt(objects[0], 0));
                    input.setScoreRank(i + 1);
                    monthUpdate.add(input);
                    break;
                }
            }
        }

        List<Object[]> nowMonthList = redstarScoreLogManager.getScoreLogInfo(nowMonth.toString("yyyy-MM-dd HH:mm:SS"), nextMonth.toString("yyyy-MM-dd HH:mm:SS"));
        List<RedstarEmployeeMonth> nowMonths = redstarEmployeeMonthManager.getRedstarEmployeeDayInputInfo(nowMonth.getYear(), nowMonth.getMonthOfYear());
        for (int i = 0; i < nowMonthList.size(); i++) {
            Object[] objects = nowMonthList.get(i);
            for (RedstarEmployeeMonth input : nowMonths) {
                if (input.getEmployeeId() == DataUtil.getInt(objects[1], 0)) {
                    input.setScoreAmount(DataUtil.getInt(objects[0], 0));
                    input.setScoreRank(i + 1);
                    monthUpdate.add(input);
                    break;
                }
            }
        }
//        yesterdayDailyList.addAll(todayDailyList);
//        dailyUpdate.addAll(yesterdayDailyList);
//        lastMonths.addAll(nowMonths);
//        monthUpdate.addAll(lastMonths);
        try {
            redstarCommonManager.batchUpdateIdentified(RedstarEmployeeDayInput.class,dailyUpdate);
            redstarCommonManager.batchUpdateIdentified(RedstarEmployeeMonth.class,monthUpdate);
        } catch (ManagerException e) {
            e.printStackTrace();
        }

        redstarTaskLog = new RedstarTaskLog();
        redstarTaskLog.setTaskClassName(this.getClass().getSimpleName());
        redstarTaskLog.setLogDatetime(new Date());
        redstarTaskLog.setRemark("积分排名统计");
        redstarTaskLog.setAction(taskLogEnd);
        redstarTaskLog.setExecuteTime(System.currentTimeMillis() - startTime);
        try {
            redstarTaskLogManager.addBean(redstarTaskLog);
        } catch (ManagerException e) {
            e.printStackTrace();
        }
    }

}
