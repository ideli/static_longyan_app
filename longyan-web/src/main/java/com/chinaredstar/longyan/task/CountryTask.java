package com.chinaredstar.longyan.task;

import com.chinaredstar.commonBiz.bean.*;
import com.greatbee.bean.constant.LanchuiConstant;
import com.chinaredstar.commonBiz.bean.constant.CommonBizConstant;
import com.chinaredstar.commonBiz.manager.DispatchDriver;
import com.chinaredstar.commonBiz.manager.RedstarCommonManager;
import com.chinaredstar.commonBiz.manager.RedstarTaskLogManager;
import com.xiwa.base.bean.search.ext.IntSearch;
import com.xiwa.base.bean.search.ext.MultiSearchBean;
import com.xiwa.base.manager.ManagerException;
import com.xiwa.security.bean.ext.SimpleAuthorized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by niu on 2016/5/16.
 */
@Component(value = "countryTask")
public class CountryTask implements LanchuiConstant,CommonBizConstant {


    @Autowired
    private RedstarCommonManager redstarCommonManager;

    @Autowired
    private DispatchDriver dispatchDriver;

    @Autowired
    private RedstarTaskLogManager redstarTaskLogManager;


    public void countryTask() throws ManagerException {

        Date date =  new Date();
        Calendar calendar = Calendar.getInstance();

        Calendar thisCalendar = Calendar.getInstance();

        calendar.setTime(date);
        thisCalendar.setTime(date);

        Long startTime = System.currentTimeMillis();
        RedstarTaskLog redstarTaskLog = new RedstarTaskLog();
        redstarTaskLog.setTaskClassName(this.getClass().getSimpleName());
        redstarTaskLog.setLogDatetime(new Date());
        redstarTaskLog.setRemark("全国数据每日统计");
        redstarTaskLog.setAction(taskLogStart);
        redstarTaskLogManager.addBean(redstarTaskLog);

        String compareDate = "createDate";

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String today =dateFormat.format(calendar.getTime());

        thisCalendar.add(Calendar.DATE,-1);
        String yesterday = dateFormat.format(thisCalendar.getTime());

        IntSearch belongedId = new IntSearch("belongedId");
        belongedId.setSearchValue(String.valueOf(LOG_BELONG_ID));

/*        MultiSearchBean communityCountSearch = new MultiSearchBean();

        IntSearch belongedId = new IntSearch("belongedId");
        belongedId.setSearchValue(String.valueOf(LOG_BELONG_ID));
        communityCountSearch.addSearchBean(belongedId);

        TextSearch textSearch = new TextSearch("operateResource");
        textSearch.setSearchValue(Community_Operate_Resource);
        communityCountSearch.addSearchBean(textSearch);

        TextSearch textSearch2 = new TextSearch("operationTypeField");
        textSearch2.setSearchValue(ADD_OPERATION);
        communityCountSearch.addSearchBean(textSearch2);*/

        Integer communityCount = dispatchDriver.getSecurityOperationLogManager().getCountryCount(RedstarCommunity.class, today, compareDate, null);

/*
        MultiSearchBean memberCountSearch = new MultiSearchBean();
        textSearch = new TextSearch("operateResource");
        textSearch.setSearchValue(Member_Operate_Resource);
        memberCountSearch.addSearchBean(textSearch);
        memberCountSearch.addSearchBean(belongedId);
        memberCountSearch.addSearchBean(textSearch2);*/

        Integer memberCount = dispatchDriver.getSecurityOperationLogManager().getCountryCount(RedStarMember.class, today, compareDate, null);


        // lastActiveTime
        Integer activeUserCount = dispatchDriver.getSecurityOperationLogManager().getCount(RedstarEmployee.class, today, "lastActiveTime", belongedId);

        //userCount  2015 05 23 add
        Integer userCount = dispatchDriver.getSecurityOperationLogManager().getAllCount(SimpleAuthorized.class, belongedId);

        //查询昨天的统计结果
        //thisCalendar.add(Calendar.DATE, -1);
        Integer beforeCount = dispatchDriver.getSecurityOperationLogManager().getLtSum(RedstarReportCountrywideDaily.class,today,compareDate,null);
        //减去昨天的统计结果
        userCount -= beforeCount;

        //避免数据问题造成负数
        if(userCount<0){
            userCount=0;
        }

        RedstarReportCountrywideDaily data;
        MultiSearchBean multiSearchBean = getMultiSearchBean(calendar);
        List<RedstarReportCountrywideDaily> dataList = redstarCommonManager.getDataList(RedstarReportCountrywideDaily.class, multiSearchBean);

        if (CollectionUtils.isEmpty(dataList)) {
            data = new RedstarReportCountrywideDaily();
            data.setCommunityInputAmount(communityCount);
            data.setMemberInputAmount(memberCount);
            data.setYear(calendar.get(Calendar.YEAR));
            data.setMonth(calendar.get(Calendar.MONTH) + 1);
            data.setDay(calendar.get(Calendar.DAY_OF_MONTH));
            data.setActiveUserCount(activeUserCount);
            data.setNewUserCount(userCount);
            data.setCreateDate(new Date());
            redstarCommonManager.addData(data);
        } else {
            data = dataList.get(0);
            data.setCommunityInputAmount(communityCount);
            data.setMemberInputAmount(memberCount);
            data.setActiveUserCount(activeUserCount);
            data.setNewUserCount(userCount);
            redstarCommonManager.updateData(data);
        }


        //更新昨天数据
        String communitySql ="SELECT count(*) FROM xiwa_redstar_community WHERE ownerid > 0 AND unix_timestamp(createDate) >= unix_timestamp(?) AND unix_timestamp(createDate) < unix_timestamp(?)";
        String memberSql ="SELECT count(*) FROM xiwa_redstar_member WHERE ownerid > 0 AND unix_timestamp(createDate) >= unix_timestamp(?) AND unix_timestamp(createDate) < unix_timestamp(?)";
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(yesterday);
        paramList.add(today);
        Integer _communityCount =  redstarCommonManager.getCountBySql(communitySql, paramList);
        Integer _memberCount = redstarCommonManager.getCountBySql(memberSql, paramList);
        multiSearchBean = getMultiSearchBean(thisCalendar);
        List<RedstarReportCountrywideDaily> _dataList = redstarCommonManager.getDataList(RedstarReportCountrywideDaily.class, multiSearchBean);
        if(!CollectionUtils.isEmpty(_dataList)){
            RedstarReportCountrywideDaily thisData = _dataList.get(0);
            thisData.setCommunityInputAmount(_communityCount);
            thisData.setMemberInputAmount(_memberCount);
            redstarCommonManager.updateData(thisData);
        }

        //执行完成日志
        redstarTaskLog = new RedstarTaskLog();
        redstarTaskLog.setTaskClassName(this.getClass().getSimpleName());
        redstarTaskLog.setLogDatetime(new Date());
        redstarTaskLog.setRemark("全国数据每日统计");
        redstarTaskLog.setAction(taskLogEnd);
        redstarTaskLog.setExecuteTime(System.currentTimeMillis() - startTime);
        redstarTaskLogManager.addBean(redstarTaskLog);

    }


    private MultiSearchBean getMultiSearchBean(Calendar calendar) {

        MultiSearchBean multiSearchBean = new MultiSearchBean();
        IntSearch yearSearch = new IntSearch("year");
        yearSearch.setSearchValue(String.valueOf(calendar.get(Calendar.YEAR)));
        multiSearchBean.addSearchBean(yearSearch);

        IntSearch monthSearch = new IntSearch("month");
        monthSearch.setSearchValue(String.valueOf(calendar.get(Calendar.MONTH) + 1));
        multiSearchBean.addSearchBean(monthSearch);

        IntSearch daySearch = new IntSearch("day");
        daySearch.setSearchValue(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
        multiSearchBean.addSearchBean(daySearch);
        return  multiSearchBean;
    }

}
