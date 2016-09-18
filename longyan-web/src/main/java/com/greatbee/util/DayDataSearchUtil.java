package com.greatbee.util;

import com.chinaredstar.commonBiz.bean.*;
import com.greatbee.bean.constant.LanchuiConstant;
import com.chinaredstar.commonBiz.manager.DispatchDriver;
import com.chinaredstar.commonBiz.manager.RedstarCommonManager;
import com.xiwa.base.bean.search.ext.IntSearch;
import com.xiwa.base.bean.search.ext.MultiSearchBean;
import com.xiwa.base.manager.ManagerException;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by niu on 2016/6/21.
 */
public class DayDataSearchUtil implements LanchuiConstant {

    //根据创建时间得到年月或年月日searchBean
    public  static MultiSearchBean getMultiSearchBean(Calendar calendar,String reportType){

        MultiSearchBean multiSearchBean = new MultiSearchBean();

        Integer year = calendar.get(Calendar.YEAR);
        Integer month = calendar.get(Calendar.MONTH)+1;
        Integer day=calendar.get(Calendar.DATE);

        IntSearch yearSearch = new IntSearch(Query_Column_Year);
        yearSearch.setSearchValue(String.valueOf(year));
        multiSearchBean.addSearchBean(yearSearch);

        IntSearch monthSearch = new IntSearch(Query_Column_Month);
        yearSearch.setSearchValue(String.valueOf(month));
        multiSearchBean.addSearchBean(monthSearch);

        if(Report_Type_Day.equals(reportType)){
            IntSearch daySearch = new IntSearch(Query_Column_Day);
            yearSearch.setSearchValue(String.valueOf(day));
            multiSearchBean.addSearchBean(daySearch);
        }

        multiSearchBean.addSearchBean(yearSearch);
        multiSearchBean.addSearchBean(monthSearch);

        return multiSearchBean;
    }

    //组织日报月报
    public  static  void  reduceOrgInfo(Integer organizationId,RedstarCommonManager redstarCommonManager,DispatchDriver dispatchDriver,Calendar calendar,String type) throws ManagerException {

        //查找当前组织
        RedstarShopMallOrganization redstarShopMallOrganization = (RedstarShopMallOrganization) dispatchDriver.getRedstarShopMallOrganizationManager().getBean(organizationId);

        MultiSearchBean orgMultiSearchBean =getMultiSearchBean(calendar,Report_Type_Day);
        IntSearch orgIdSearch = new IntSearch(Query_Column_OrganizationId);
        orgIdSearch.setSearchValue(String.valueOf(redstarShopMallOrganization.getId()));

        orgMultiSearchBean.addSearchBean(orgIdSearch);
        //组织日报
        List<RedstarOrganizationDayInput> orgDayInputList = redstarCommonManager.getDataList(RedstarOrganizationDayInput.class, orgMultiSearchBean, null, null, 0, 1);
        if(!CollectionUtils.isEmpty(orgDayInputList)){
            RedstarOrganizationDayInput data = orgDayInputList.get(0);
            if (Report_Reduce_Community.equals(type)){
                data.setInputCommunityAmount(data.getInputCommunityAmount()-1);
            }else if(Report_Reduce_Member.equals(type)){
                data.setInputMemberAmount(data.getInputMemberAmount() - 1);
            }
            redstarCommonManager.updateData(data);
        }

        //组织月报
        MultiSearchBean orgMonthMultiSearchBean =getMultiSearchBean(calendar,Report_Type_Month);
        orgMonthMultiSearchBean.addSearchBean(orgIdSearch);
        List<RedstarOrganizationMonth> orgMonthInputList = redstarCommonManager.getDataList(RedstarMallMonth.class, orgMonthMultiSearchBean,null,null,0,1);
        if(!CollectionUtils.isEmpty(orgMonthInputList)){
            RedstarOrganizationMonth data = orgMonthInputList.get(0);
            if (Report_Reduce_Community.equals(type)){
                data.setInputCommunityAmount(data.getInputCommunityAmount()-1);
            }else if(Report_Reduce_Member.equals(type)){
                data.setInputMemberAmount(data.getInputMemberAmount() - 1);
            }
            redstarCommonManager.updateData(data);
        }

        //非根节时点向上递归
        if(!Default_Root_Id.equals(String.valueOf(redstarShopMallOrganization.getParentId())) && !Default_Root_Id.equals(String.valueOf(organizationId))){
            reduceOrgInfo(redstarShopMallOrganization.getParentId(), redstarCommonManager, dispatchDriver, calendar,type);
        }
    }

    //减员工日报
    public static void reduceEmployeeDayInput(RedstarCommonManager redstarCommonManager,MultiSearchBean multiSearchBean,String type) throws ManagerException {
        List<RedstarEmployeeDayInput> dayInputList =   redstarCommonManager.getDataList(RedstarEmployeeDayInput.class, multiSearchBean,null,null,0,1);
        if(!CollectionUtils.isEmpty(dayInputList)){
            RedstarEmployeeDayInput data = dayInputList.get(0);
            if (Report_Reduce_Community.equals(type)){
                data.setInputCommunityAmount(data.getInputCommunityAmount()-1);
            }else if(Report_Reduce_Member.equals(type)){
                data.setInputMemberAmount(data.getInputMemberAmount() - 1);
            }
            redstarCommonManager.updateData(data);
        }
    }

    //减员工月报
    public static void reduceEmployeeMonthInput(RedstarCommonManager redstarCommonManager,MultiSearchBean multiSearchBean,String type) throws ManagerException {
        List<RedstarEmployeeMonth> monthInputList =   redstarCommonManager.getDataList(RedstarEmployeeMonth.class, multiSearchBean,null,null,0,1);
        if(!CollectionUtils.isEmpty(monthInputList)){
            RedstarEmployeeMonth data = monthInputList.get(0);
            if(Report_Reduce_Community.equals(type)){
                data.setInputCommunityAmount(data.getInputCommunityAmount()-1);
            }else if(Report_Reduce_Member.equals(type)){
                data.setInputMemberAmount(data.getInputMemberAmount() - 1);
            }
            redstarCommonManager.updateData(data);
        }
    }


    //减商场日报
    public static void reduceMallDayInput(RedstarCommonManager redstarCommonManager,MultiSearchBean multiSearchBean,String type) throws ManagerException {
        List<RedstarShoppingMallDayInput> mallDayInputList = redstarCommonManager.getDataList(RedstarShoppingMallDayInput.class, multiSearchBean, null, null, 0, 1);
        if(!CollectionUtils.isEmpty(mallDayInputList)){
            RedstarShoppingMallDayInput data = mallDayInputList.get(0);
            if (Report_Reduce_Community.equals(type)){
                data.setInputCommunityAmount(data.getInputCommunityAmount()-1);
            }else if(Report_Reduce_Member.equals(type)){
                data.setInputMemberAmount(data.getInputMemberAmount() - 1);
            }
            redstarCommonManager.updateData(data);
        }
    }


    //减商场月报
    public static void reduceMallMonthInput(RedstarCommonManager redstarCommonManager,MultiSearchBean multiSearchBean,String type) throws ManagerException {
        List<RedstarMallMonth> mallMonthInputList = redstarCommonManager.getDataList(RedstarMallMonth.class, multiSearchBean,null,null,0,1);
        if(!CollectionUtils.isEmpty(mallMonthInputList)){
            RedstarMallMonth data = mallMonthInputList.get(0);
            if (Report_Reduce_Community.equals(type)){
                data.setInputCommunityAmount(data.getInputCommunityAmount()-1);
            }else if(Report_Reduce_Member.equals(type)){
                data.setInputMemberAmount(data.getInputMemberAmount() - 1);
            }
            redstarCommonManager.updateData(data);
        }
    }


    public  static MultiSearchBean getMultiSearchBean(Calendar calendar){

        MultiSearchBean multiSearchBean = new MultiSearchBean();

        Integer year = calendar.get(Calendar.YEAR);
        Integer month = calendar.get(Calendar.MONTH)+1;
        Integer day=calendar.get(Calendar.DATE);

        IntSearch yearSearch = new IntSearch(Query_Column_Year);
        yearSearch.setSearchValue(String.valueOf(year));

        IntSearch monthSearch = new IntSearch(Query_Column_Month);
        monthSearch.setSearchValue(String.valueOf(month));


        IntSearch daySearch = new IntSearch(Query_Column_Day);
        daySearch.setSearchValue(String.valueOf(day));

        multiSearchBean.addSearchBean(daySearch);
        multiSearchBean.addSearchBean(yearSearch);
        multiSearchBean.addSearchBean(monthSearch);

        return multiSearchBean;
    }


    public  static List<Object> getParamList(Calendar calendar){
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(calendar.get(Calendar.YEAR));
        paramList.add(calendar.get(Calendar.MONTH)+1);
        paramList.add(calendar.get(Calendar.DATE));
        return paramList;
    }

}
