package com.greatbee.util;

import com.greatbee.bean.constant.LanchuiConstant;
import com.chinaredstar.commonBiz.bean.ExtCountryData;
import com.chinaredstar.commonBiz.bean.RedstarReportCountrywideDaily;
import com.chinaredstar.commonBiz.bean.RedstarShopMallOrganization;
import com.chinaredstar.commonBiz.manager.RedstarCommonManager;
import com.xiwa.base.bean.Response;
import com.xiwa.base.bean.search.ext.IntSearch;
import com.xiwa.base.bean.search.ext.MultiSearchBean;
import com.xiwa.base.manager.ManagerException;
import org.springframework.util.CollectionUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by usagizhang on 16-5-17.
 */
public class ReportUtil implements LanchuiConstant {
    public static ExtCountryData getAllDataBySum(RedstarCommonManager redstarCommonManager,Response response) throws ManagerException {
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        MultiSearchBean multiSearchBean;

        IntSearch yearSearch;
        IntSearch monthSearch;
        IntSearch daySearch;

        response.addKey("today",new RedstarReportCountrywideDaily());
        response.addKey("yesterday",new RedstarReportCountrywideDaily());

        for(int index = 0;index<2;index++){
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH,0-index);
            multiSearchBean= new MultiSearchBean();
            yearSearch =new IntSearch("year");
            yearSearch.setSearchValue(String.valueOf(calendar.get(Calendar.YEAR)));
            multiSearchBean.addSearchBean(yearSearch);

            monthSearch = new IntSearch("month");
            monthSearch.setSearchValue(String.valueOf(calendar.get(Calendar.MONTH)+1));
            multiSearchBean.addSearchBean(monthSearch);

            daySearch = new IntSearch("day");
            daySearch.setSearchValue(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
            multiSearchBean.addSearchBean(daySearch);

            List<RedstarReportCountrywideDaily> extList = redstarCommonManager.getDataList(RedstarReportCountrywideDaily.class,multiSearchBean);
            if(!CollectionUtils.isEmpty(extList)&&index==0){
                response.addKey("today",extList.get(0));
            }
            if(!CollectionUtils.isEmpty(extList)&&index==1){
                response.addKey("yesterday",extList.get(0));
            }
        }

        //List<RedstarReportCountrywideDaily> dataList = redstarCommonManager.getDataList(RedstarReportCountrywideDaily.class,null);

/*        Integer communityInputAmount=0;
        Integer memberInputAmount=0;
        if(!CollectionUtils.isEmpty(dataList)){
            for (RedstarReportCountrywideDaily data : dataList){
                newUserCount+=(data.getNewUserCount()==null?0:data.getNewUserCount());
                //activeUserCount+=(data.getActiveUserCount()==null?0:data.getActiveUserCount());
                communityInputAmount+=(data.getCommunityInputAmount()==null?0:data.getCommunityInputAmount());
                memberInputAmount+=(data.getMemberInputAmount()==null?0:data.getMemberInputAmount());
            }
        }*/

/*
        String communitySql = "SELECT COUNT(*) FROM xiwa_redstar_community WHERE ownerId>0";
        String memberSql = "SELECT COUNT(*) FROM xiwa_redstar_member WHERE ownerId>0";

        Integer communityInputAmount =Integer.parseInt(String.valueOf(redstarCommonManager.queryUniqueBySql(communitySql).get(0)));
        Integer memberInputAmount = Integer.parseInt(String.valueOf(redstarCommonManager.queryUniqueBySql(memberSql).get(0)));
*/

        IntSearch idSearch = new IntSearch("id");
        idSearch.setSearchValue(Default_Root_Id);

        //根节点数据
        List<RedstarShopMallOrganization> orgList = redstarCommonManager.getDataList(RedstarShopMallOrganization.class,idSearch);
        RedstarShopMallOrganization org = orgList.get(0);


        String userCountSql = "SELECT COUNT(*) FROM xiwa_security_authorized WHERE belongedId=10944";
        Integer newUserCount =Integer.parseInt(String.valueOf(redstarCommonManager.queryUniqueBySql(userCountSql).get(0)));

        ExtCountryData countryData = new ExtCountryData();
        countryData.setMemberInputAmount(org.getInputMemberAmount());
        countryData.setCommunityInputAmount(org.getInputCommunityAmount());
        countryData.setNewUserCount(newUserCount);
        return countryData;
    }
}
