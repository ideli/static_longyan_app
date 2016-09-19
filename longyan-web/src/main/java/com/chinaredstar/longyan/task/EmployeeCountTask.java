package com.chinaredstar.longyan.task;
import com.chinaredstar.commonBiz.bean.*;
import com.chinaredstar.longyan.bean.constant.LanchuiConstant;
import com.chinaredstar.commonBiz.manager.DispatchDriver;
import com.chinaredstar.commonBiz.manager.RedstarCommonManager;
import com.chinaredstar.commonBiz.manager.RedstarTaskLogManager;
import com.chinaredstar.nvwaBiz.bean.NvwaEmployee;
import com.xiwa.base.bean.search.ext.BooleanSearch;
import com.xiwa.base.bean.search.ext.IntSearch;
import com.xiwa.base.bean.search.ext.MultiSearchBean;
import com.xiwa.base.manager.ManagerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by niu on 2016/5/7.
 */
@Component(value = "employeeCountTask")
public class EmployeeCountTask implements LanchuiConstant {

    @Autowired
    private DispatchDriver dispatchDriver;

    @Autowired
    private RedstarCommonManager redstarCommonManager;

    @Autowired
    private RedstarTaskLogManager redstarTaskLogManager;

    public void  countTask() throws ManagerException {

        Long startTime = System.currentTimeMillis();
        RedstarTaskLog redstarTaskLog = new RedstarTaskLog();
        redstarTaskLog.setTaskClassName(this.getClass().getSimpleName());
        redstarTaskLog.setLogDatetime(new Date());
        redstarTaskLog.setRemark("员工总数统计");
        redstarTaskLog.setAction(taskLogStart);
        redstarTaskLogManager.addBean(redstarTaskLog);

        //商场员工数量
       List<RedstarShoppingMall>  mallList =  dispatchDriver.getRedstarShoppingMallManager().getBeanList();
       IntSearch   mallSearch;
       for(RedstarShoppingMall mall:mallList){
           mallSearch = new IntSearch("shoppingMallId");
           mallSearch.setSearchValue(String.valueOf(mall.getId()));
           List<RedstarMallEmployee> meList =  dispatchDriver.getRedstarMallEmployeeManager().searchIdentify(mallSearch);
           if(CollectionUtils.isEmpty(meList)){
              mall.setEmployeeCount(0);
           }else{
              mall.setEmployeeCount(meList.size());
           }
           //更新数量
           dispatchDriver.getRedstarShoppingMallManager().updateBean(mall);
       }



       //大区员工总数量
       IntSearch  parentSearch = new IntSearch("parentId");
       parentSearch.setSearchValue(Default_Root_Id);
       IntSearch _organizationSearch;
       //查到大区
       List<RedstarShopMallOrganization> redstarShopMallOrganizationList = dispatchDriver.getRedstarShopMallOrganizationManager().searchIdentify(parentSearch);

        //查到大区下的区
        for (RedstarShopMallOrganization data:redstarShopMallOrganizationList){
             //大区总数
             Integer dataCount=0;
             parentSearch = new IntSearch("parentId");
             parentSearch.setSearchValue(String.valueOf(data.getId()));
            //大区下的小区
            List<RedstarShopMallOrganization> childList = dispatchDriver.getRedstarShopMallOrganizationManager().searchIdentify(parentSearch);

            if (CollectionUtils.isEmpty(childList)){
                data.setEmployeeCount(dataCount);
                dispatchDriver.getRedstarShopMallOrganizationManager().updateBean(data);
                continue;
            }

            for (RedstarShopMallOrganization child:childList){
                //根据组织id查询组织下的所有商场
                _organizationSearch = new IntSearch("organizationId");
                _organizationSearch.setSearchValue(String.valueOf(child.getId()));
                List<RedstarShoppingMall> shoppingMallList = dispatchDriver.getRedstarShoppingMallManager().searchIdentify(_organizationSearch);
                //当前区
                Integer childCount=0;
                //区下的所有商场的员
                for (RedstarShoppingMall redstarShoppingMall:shoppingMallList){
                    childCount+=redstarShoppingMall.getEmployeeCount();
                }

                child.setEmployeeCount(childCount);

                dataCount+=childCount;

                dispatchDriver.getRedstarShopMallOrganizationManager().updateBean(child);
            }


            data.setEmployeeCount(dataCount);
            dispatchDriver.getRedstarShopMallOrganizationManager().updateBean(data);
        }

        //总数据
        RedstarShopMallOrganization totalData = (RedstarShopMallOrganization) dispatchDriver.getRedstarShopMallOrganizationManager().getBean(Integer.parseInt(Default_Root_Id));


        //belongId和showData做为条件 2016.07.07
        MultiSearchBean multiSearchBean = new MultiSearchBean();

        IntSearch belongIdSearch = new IntSearch("belongedId");
        belongIdSearch.setSearchValue(String.valueOf(LOG_BELONG_ID));
        multiSearchBean.addSearchBean(belongIdSearch);

        BooleanSearch showDataSearch = new BooleanSearch("showData");
        showDataSearch.setValue("1");
        multiSearchBean.addSearchBean(showDataSearch);
        //List<RedstarEmployee> empList = dispatchDriver.getRedstarEmployeeManager().searchIdentify(belongIdSearch);
        List<NvwaEmployee> empList = redstarCommonManager.getDataList(NvwaEmployee.class,multiSearchBean);
        if(CollectionUtils.isEmpty(empList)){
            totalData.setEmployeeCount(0);
        }else{
            totalData.setEmployeeCount(empList.size());
        }
        dispatchDriver.getRedstarShopMallOrganizationManager().updateBean(totalData);

        redstarTaskLog = new RedstarTaskLog();
        redstarTaskLog.setTaskClassName(this.getClass().getSimpleName());
        redstarTaskLog.setLogDatetime(new Date());
        redstarTaskLog.setRemark("员工总数统计");
        redstarTaskLog.setAction(taskLogEnd);
        redstarTaskLog.setExecuteTime(System.currentTimeMillis()-startTime);
        redstarTaskLogManager.addBean(redstarTaskLog);
    }



}
