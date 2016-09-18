package com.chinaredstar.longyan.task;

import com.chinaredstar.commonBiz.bean.*;
import com.chinaredstar.longyan.bean.constant.LanchuiConstant;
import com.chinaredstar.commonBiz.bean.constant.CommonBizConstant;
import com.chinaredstar.commonBiz.manager.RedstarCommonManager;
import com.chinaredstar.commonBiz.manager.RedstarShopMallOrganizationManager;
import com.chinaredstar.commonBiz.manager.RedstarTaskLogManager;
import com.xiwa.base.bean.search.SearchBean;
import com.xiwa.base.bean.search.ext.IntSearch;
import com.xiwa.base.bean.search.ext.MultiSearchBean;
import com.xiwa.base.bean.search.ext.TextSearch;
import com.xiwa.base.manager.ManagerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Created by niu on 2016/6/1.
 */
@Component(value = "organizationInputDailyTask")
public class OrganizationInputDailyTask implements LanchuiConstant,CommonBizConstant {

    @Autowired
    private RedstarCommonManager redstarCommonManager;

    @Autowired
    private RedstarTaskLogManager redstarTaskLogManager;

    @Autowired
    private RedstarShopMallOrganizationManager redstarShopMallOrganizationManager;


    public void excute() throws ManagerException {

        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);




        String remark = "组织录入日报";

/*任务开始日志*/
        Long startTime = System.currentTimeMillis();
        RedstarTaskLog redstarTaskLog = new RedstarTaskLog();
        redstarTaskLog.setTaskClassName(this.getClass().getSimpleName());
        redstarTaskLog.setLogDatetime(new Date());
        redstarTaskLog.setRemark(remark);
        redstarTaskLog.setAction(taskLogStart);
        redstarTaskLogManager.addBean(redstarTaskLog);
/*over*/




/*        String organizationId = "organizationId";
        MultiSearchBean multiSearchBean;*/

        Integer year = calendar.get(Calendar.YEAR);
        Integer month = calendar.get(Calendar.MONTH) + 1;
        Integer day = calendar.get(Calendar.DAY_OF_MONTH);

        IntSearch yearSearch = new IntSearch("year");
        yearSearch.setSearchValue(String.valueOf(year));

        IntSearch monthSearch = new IntSearch("month");
        monthSearch.setSearchValue(String.valueOf(month));

        IntSearch daySearch = new IntSearch("day");
        daySearch.setSearchValue(String.valueOf(day));

        TextSearch typeSearch = new TextSearch("mallType");
        typeSearch.setSearchValue(Default_MallType);






        IntSearch parentIdSearch = new IntSearch("parentId");
        //根节点
        parentIdSearch.setSearchValue(Default_Root_Id);
        //父节点为-1集合
        List<RedstarShopMallOrganization> redstarShopMallOrganizationList = redstarShopMallOrganizationManager.searchIdentify(parentIdSearch);
/*
        IntSearch organizationIdSearch;

        RedstarOrganizationDayInput redstarOrganizationDayInput;*/

        taskProcess(redstarShopMallOrganizationList,parentIdSearch,typeSearch,yearSearch,monthSearch,daySearch,year,month,day);


        calendar.add(Calendar.DAY_OF_MONTH,-1);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        yearSearch = new IntSearch("year");
        yearSearch.setSearchValue(String.valueOf(year));
        monthSearch = new IntSearch("month");
        monthSearch.setSearchValue(String.valueOf(month));
        daySearch = new IntSearch("day");
        daySearch.setSearchValue(String.valueOf(day));
        taskProcess(redstarShopMallOrganizationList,parentIdSearch,typeSearch,yearSearch,monthSearch,daySearch,year,month,day);

/*任务结束日志*/
        redstarTaskLog = new RedstarTaskLog();
        redstarTaskLog.setTaskClassName(this.getClass().getSimpleName());
        redstarTaskLog.setLogDatetime(new Date());
        redstarTaskLog.setRemark(remark);
        redstarTaskLog.setAction(taskLogEnd);
        redstarTaskLog.setExecuteTime(System.currentTimeMillis() - startTime);
        redstarTaskLogManager.addBean(redstarTaskLog);
/*over*/
    }

    private  Object getDayData(MultiSearchBean multiSearchBean,Class c) throws ManagerException {
       List<Object> dataList = redstarCommonManager.getDataList(c, multiSearchBean);
       if (CollectionUtils.isEmpty(dataList)){
           return null;
       }else{
           return  dataList.get(0);
       }
    }

    private MultiSearchBean getMultiSearchBean(SearchBean[] searchBeanArr){
        MultiSearchBean multiSearchBean = new MultiSearchBean();
        for(int index=0;index<searchBeanArr.length;index++){
            multiSearchBean.addSearchBean(searchBeanArr[index]);
        }
        return  multiSearchBean;
    }

    private void dataProcess (RedstarOrganizationDayInput data, RedstarShopMallOrganization organization, Integer year, Integer month, Integer day,
                              Integer communityCount, Integer memberCount) throws ManagerException {
        if(data==null){
            data=new RedstarOrganizationDayInput();
            data.setOrganizationId(organization.getId());
            data.setOrganizationName(organization.getName());
            data.setYear(year);
            data.setMonth(month);
            data.setDay(day);
            data.setInputCommunityAmount(communityCount);
            data.setInputMemberAmount(memberCount);
            redstarCommonManager.addData(data);
        }else{
            if(data.getInputCommunityAmount()==communityCount&&data.getInputMemberAmount()==memberCount){

            }else {
                data.setInputCommunityAmount(communityCount);
                data.setInputMemberAmount(memberCount);
                redstarCommonManager.updateData(data);
            }
        }
    }

    //根据组织id查询组织下的所以商场数据
    private Map<String,Integer> getDayMap(MultiSearchBean multiSearchBean,IntSearch yearSearch,IntSearch monthSearch,IntSearch daySearch) throws ManagerException {
        String shoppingMallId ="shoppingMallId";
        Map<String,Integer> result = new HashMap<String, Integer>();
        Integer communityCount = 0;
        Integer memberCount = 0;

        List<RedstarShoppingMall> mallList=redstarCommonManager.getDataList(RedstarShoppingMall.class,multiSearchBean);
        IntSearch shoppingMallIdSearch;

        if(!CollectionUtils.isEmpty(mallList)){
            for (RedstarShoppingMall mall:mallList){
                shoppingMallIdSearch = new IntSearch(shoppingMallId);
                shoppingMallIdSearch.setSearchValue(String.valueOf(mall.getId()));
                MultiSearchBean _thisSearchBean = getMultiSearchBean(new SearchBean[]{shoppingMallIdSearch,monthSearch,yearSearch,daySearch});
                List<RedstarShoppingMallDayInput> dayInputList = redstarCommonManager.getDataList(RedstarShoppingMallDayInput.class,_thisSearchBean);
                if(!CollectionUtils.isEmpty(dayInputList)){
                    for (RedstarShoppingMallDayInput data:dayInputList){
                        communityCount+=data.getInputCommunityAmount();
                        memberCount+=data.getInputMemberAmount();
                    }
                }
            }
        }
        result.put("communityCount",communityCount);
        result.put("memberCount",memberCount);
        return  result;
    }



    private void  taskProcess(List<RedstarShopMallOrganization> redstarShopMallOrganizationList,
                              IntSearch parentIdSearch,TextSearch typeSearch,
                              IntSearch yearSearch,IntSearch monthSearch,IntSearch daySearch,
                              Integer year,Integer month,Integer day) throws ManagerException {


        IntSearch organizationIdSearch;

        RedstarOrganizationDayInput redstarOrganizationDayInput;

        String organizationId = "organizationId";

        MultiSearchBean multiSearchBean;

        for (RedstarShopMallOrganization organization:redstarShopMallOrganizationList){

            Integer bigCommunityCount=0;
            Integer bigMemberCount=0;

/*            Integer _bigCommunityCount=0;
            Integer _bigMemberCount=0;*/

            //当前中区
            parentIdSearch.setSearchValue(String.valueOf(organization.getId()));
            List<RedstarShopMallOrganization> childList = redstarShopMallOrganizationManager.searchIdentify(parentIdSearch);

            if(CollectionUtils.isEmpty(childList)){
                continue;
            }

            for (RedstarShopMallOrganization org:childList){
                Integer childCommunityCount;
                Integer childMemberCount;

/*                Integer _childCommunityCount;
                Integer _childMemberCount;*/

                organizationIdSearch = new IntSearch(organizationId);
                organizationIdSearch.setSearchValue(String.valueOf(org.getId()));
                multiSearchBean = getMultiSearchBean(new SearchBean[]{typeSearch,organizationIdSearch});

                Map<String,Integer> resultMap = getDayMap(multiSearchBean,yearSearch,monthSearch,daySearch);
                childCommunityCount = resultMap.get("communityCount");
                childMemberCount = resultMap.get("memberCount");

                //昨天
/*                Map<String,Integer> _resultMap = getDayMap(multiSearchBean,_yearSearch,_monthSearch,_daySearch);
                _childCommunityCount = resultMap.get("communityCount");
                _childMemberCount = resultMap.get("memberCount");*/

/*                List<RedstarShoppingMall> mallList=redstarCommonManager.getDataList(RedstarShoppingMall.class,multiSearchBean);
                if (CollectionUtils.isEmpty(mallList)){
                    continue;
                }
                for (RedstarShoppingMall mall:mallList){
                    shoppingMallIdSearch = new IntSearch(shoppingMallId);
                    shoppingMallIdSearch.setSearchValue(String.valueOf(mall.getId()));
                    multiSearchBean = getMultiSearchBean(new SearchBean[]{shoppingMallIdSearch,monthSearch,yearSearch,daySearch});
                    List<RedstarShoppingMallDayInput> dayInputList = redstarCommonManager.getDataList(RedstarShoppingMallDayInput.class,multiSearchBean);
                    if(!CollectionUtils.isEmpty(dayInputList)){
                        for (RedstarShoppingMallDayInput data:dayInputList){
                            childCommunityCount+=data.getInputCommunityAmount();
                            childMemberCount+=data.getInputMemberAmount();
                        }
                    }
                }*/

                //当前区数据是否存在
                multiSearchBean=getMultiSearchBean(new SearchBean[]{organizationIdSearch,yearSearch,monthSearch,daySearch});
                redstarOrganizationDayInput = (RedstarOrganizationDayInput) getDayData(multiSearchBean,RedstarOrganizationDayInput.class);
                dataProcess(redstarOrganizationDayInput,org,year,month,day,childCommunityCount,childMemberCount);
                bigCommunityCount+=childCommunityCount;
                bigMemberCount+=childMemberCount;

                //昨天
/*                multiSearchBean=getMultiSearchBean(new SearchBean[]{organizationIdSearch,_yearSearch,_monthSearch,_daySearch});
                redstarOrganizationDayInput = (RedstarOrganizationDayInput) getDayData(multiSearchBean,RedstarOrganizationDayInput.class);
                dataProcess(redstarOrganizationDayInput,org,_year,_month,_day,_childCommunityCount,_childMemberCount);
                _bigCommunityCount+=_childCommunityCount;
                _bigMemberCount+=_childMemberCount;*/

            }

            //查询该大区下是否是商场
            organizationIdSearch = new IntSearch(organizationId);
            organizationIdSearch.setSearchValue(String.valueOf(organization.getId()));
            multiSearchBean = getMultiSearchBean(new SearchBean[]{typeSearch,organizationIdSearch});

            Map<String,Integer> resultMap = getDayMap(multiSearchBean,yearSearch,monthSearch,daySearch);
            bigCommunityCount+=resultMap.get("communityCount");
            bigMemberCount+=resultMap.get("memberCount");

            //昨天
/*            Map<String,Integer> _resultMap = getDayMap(multiSearchBean,_yearSearch,_monthSearch,_daySearch);
            _bigCommunityCount+=_resultMap.get("communityCount");
            _bigMemberCount+=_resultMap.get("memberCount");*/

/*            List<RedstarShoppingMall> mallList=redstarCommonManager.getDataList(RedstarShoppingMall.class,multiSearchBean);
            if (CollectionUtils.isEmpty(mallList)){
                for (RedstarShoppingMall mall:mallList){
                    shoppingMallIdSearch = new IntSearch(shoppingMallId);
                    shoppingMallIdSearch.setSearchValue(String.valueOf(mall.getId()));
                    multiSearchBean = getMultiSearchBean(new SearchBean[]{shoppingMallIdSearch,monthSearch,yearSearch,daySearch});
                    List<RedstarShoppingMallDayInput> dayInputList = redstarCommonManager.getDataList(RedstarShoppingMallDayInput.class,multiSearchBean);
                    if(!CollectionUtils.isEmpty(dayInputList)){
                        for (RedstarShoppingMallDayInput data:dayInputList){
                            bigCommunityCount+=data.getInputCommunityAmount();
                            bigMemberCount+=data.getInputMemberAmount();
                        }
                    }
                }
            }*/


            //当前区数据是否存在
            organizationIdSearch = new IntSearch(organizationId);
            organizationIdSearch.setSearchValue(String.valueOf(organization.getId()));

            multiSearchBean=getMultiSearchBean(new SearchBean[]{organizationIdSearch,yearSearch,monthSearch,daySearch});
            redstarOrganizationDayInput = (RedstarOrganizationDayInput) getDayData(multiSearchBean,RedstarOrganizationDayInput.class);
            dataProcess(redstarOrganizationDayInput,organization,year,month,day,bigCommunityCount,bigMemberCount);

            //昨天
/*            multiSearchBean=getMultiSearchBean(new SearchBean[]{organizationIdSearch,_yearSearch,_monthSearch,_daySearch});
            redstarOrganizationDayInput = (RedstarOrganizationDayInput) getDayData(multiSearchBean,RedstarOrganizationDayInput.class);
            dataProcess(redstarOrganizationDayInput,organization,_year,_month,_day,_bigCommunityCount,_bigMemberCount);*/
        }


        organizationIdSearch = new IntSearch(organizationId);
        organizationIdSearch.setSearchValue(Default_Root_Id);
        RedstarShopMallOrganization rootOrg = (RedstarShopMallOrganization) redstarShopMallOrganizationManager.getBean(Integer.parseInt(Default_Root_Id));
        rootOrg.setName("全国");


        //查询当日所有员工 做为全国数据
        String sql = "SELECT  IFNULL(SUM(inputCommunityAmount),0),IFNULL(SUM(inputMemberAmount),0)  FROM xiwa_redstar_report_employee_input_daily WHERE year=? AND month=? AND day=?";
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(year);
        paramList.add(month);
        paramList.add(day);
        List<Object[]> allList = redstarCommonManager.excuteBySql(sql,paramList);

        Integer allCommunityCount=Integer.parseInt(String.valueOf(allList.get(0)[0]));
        Integer allMemberCount=Integer.parseInt(String.valueOf(allList.get(0)[1]));

        multiSearchBean=getMultiSearchBean(new SearchBean[]{organizationIdSearch,yearSearch,monthSearch,daySearch});
        redstarOrganizationDayInput = (RedstarOrganizationDayInput) getDayData(multiSearchBean,RedstarOrganizationDayInput.class);
        dataProcess(redstarOrganizationDayInput,rootOrg,year,month,day,allCommunityCount,allMemberCount);



    }

}
