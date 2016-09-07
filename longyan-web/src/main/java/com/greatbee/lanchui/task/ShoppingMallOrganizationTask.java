package com.greatbee.lanchui.task;

import com.greatbee.bean.constant.LanchuiConstant;
import com.lanchui.commonBiz.bean.*;
import com.lanchui.commonBiz.manager.DispatchDriver;
import com.lanchui.commonBiz.manager.RedstarCommonManager;
import com.lanchui.commonBiz.manager.RedstarTaskLogManager;
import com.xiwa.base.bean.search.ext.IntSearch;
import com.xiwa.base.bean.search.ext.MultiSearchBean;
import com.xiwa.base.bean.search.ext.TextSearch;
import com.xiwa.base.manager.ManagerException;
import com.xiwa.base.util.CollectionUtil;
import com.xiwa.base.util.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

/**
 * REDSTAR-97
 * 陈成 2016-04-28
 * 集团录入情况
 */
@Component(value = "shoppingMallOrganizationTask")
public class ShoppingMallOrganizationTask implements LanchuiConstant {

    @Autowired
    private DispatchDriver dispatchDriver;
    @Autowired
    private RedstarCommonManager redstarCommonManager;

    @Autowired
    private RedstarTaskLogManager redstarTaskLogManager;

    /**
     * 集团录入情况
     *
     * @throws ManagerException
     */
    public void shoppingMallOrganization() throws ManagerException {
        Long startTime = System.currentTimeMillis();
        try {
            {


                RedstarTaskLog redstarTaskLog = new RedstarTaskLog();
                redstarTaskLog.setTaskClassName(this.getClass().getSimpleName());
                redstarTaskLog.setLogDatetime(new Date());
                redstarTaskLog.setRemark("集团录入情况");
                redstarTaskLog.setAction(taskLogStart);
                redstarTaskLogManager.addBean(redstarTaskLog);

                TextSearch typeSearch = new TextSearch("mallType");
                typeSearch.setSearchValue(Default_MallType);
                MultiSearchBean multiSearchBean;

                IntSearch organizationSearch;

                //遍历中区小区节点
                List<RedstarShopMallOrganization> redstarShopMallOrganizationList = dispatchDriver.getRedstarShopMallOrganizationManager().getBeanList();
                for (RedstarShopMallOrganization redstarShopMallOrganization : redstarShopMallOrganizationList) {
                    if (redstarShopMallOrganization.getParentId() != -1 && redstarShopMallOrganization.getParentId() != 0) {
                        int inputMemberAmount = 0;//已录入户数
                        int inputCommunityAmount = 0;//录入小区总数
                        int inputCommunityRoomAmount = 0;//商场录入社区总户数
                        try {
                            multiSearchBean = new MultiSearchBean();

                            organizationSearch = new IntSearch("organizationId");
                            organizationSearch.setSearchValue(String.valueOf(redstarShopMallOrganization.getId()));

                            multiSearchBean.addSearchBean(typeSearch);
                            multiSearchBean.addSearchBean(organizationSearch);

                            //找到二级下面的商场
                            //List<RedstarShoppingMall> redstarShoppingMallList = dispatchDriver.getRedstarShoppingMallManager().getBeanListByColumn("organizationId", redstarShopMallOrganization.getId());
                            List<RedstarShoppingMall> redstarShoppingMallList = dispatchDriver.getRedstarShoppingMallManager().searchIdentify(multiSearchBean);
                            if (CollectionUtil.isValid(redstarShoppingMallList)) {
                                for (RedstarShoppingMall redstarShoppingMall : redstarShoppingMallList) {
                                    //找到商场下面已录入的户数
                                    inputCommunityAmount = inputCommunityAmount + redstarShoppingMall.getInputCommunityAmount();
                                    inputMemberAmount = inputMemberAmount + redstarShoppingMall.getInputMemberAmount();
                                    inputCommunityRoomAmount = inputCommunityRoomAmount + redstarShoppingMall.getInputCommunityRoomAmount();
                                }
                            }
                            redstarShopMallOrganization.setInputMemberAmount(inputMemberAmount);
                            redstarShopMallOrganization.setInputCommunityAmount(inputCommunityAmount);
                            redstarShopMallOrganization.setInputCommunityRoomAmount(inputCommunityRoomAmount);
                            dispatchDriver.getRedstarShopMallOrganizationManager().updateBean(redstarShopMallOrganization);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                //遍历所有大区节点
                List<RedstarShopMallOrganization> bigareaOrganization = dispatchDriver.getRedstarShopMallOrganizationManager().getBeanListByColumn("parentId", -1);
                if (CollectionUtil.isValid(bigareaOrganization)) {
                    for (RedstarShopMallOrganization organization : bigareaOrganization) {
                        int _inputMemberAmount = 0;//已录入户数
                        int _inputCommunityAmount = 0;//录入小区总数
                        int _inputCommunityRoomAmount = 0;//商场录入社区总户数
                        //找去大区对应的小区
                        List<RedstarShopMallOrganization> subOrganizations = dispatchDriver.getRedstarShopMallOrganizationManager().getBeanListByColumn("parentId", organization.getId());
                        if (CollectionUtil.isValid(subOrganizations)) {
                            //遍历大区对应的小区、中区
                            for (RedstarShopMallOrganization subOrganization : subOrganizations) {
                                if (subOrganization != null) {
                                    _inputCommunityAmount = _inputCommunityAmount + subOrganization.getInputCommunityAmount();
                                    _inputCommunityRoomAmount = _inputCommunityRoomAmount + subOrganization.getInputCommunityRoomAmount();
                                    _inputMemberAmount = _inputMemberAmount + subOrganization.getInputMemberAmount();
                                }
                            }
                        }
                        organization.setInputCommunityAmount(_inputCommunityAmount);
                        organization.setInputCommunityRoomAmount(_inputCommunityRoomAmount);
                        organization.setInputMemberAmount(_inputMemberAmount);
                        //更新大区节点
                        dispatchDriver.getRedstarShopMallOrganizationManager().updateBean(organization);
                    }
                }

                //拉出所有商场的列表
//                List<RedstarShoppingMall> shoppingMalls = dispatchDriver.getRedstarShoppingMallManager().getBeanList();
//                int sum_inputMemberAmount = 0;//已录入户数
//                int sum_inputCommunityAmount = 0;//录入小区总数
//                int sum_inputCommunityRoomAmount = 0;//商场录入社区总户数
//                if (CollectionUtil.isValid(shoppingMalls)) {
//                    for (RedstarShoppingMall mall : shoppingMalls) {
//                        if (mall != null) {
//                            sum_inputMemberAmount = sum_inputMemberAmount + mall.getInputMemberAmount();
//                            sum_inputCommunityAmount = sum_inputCommunityAmount + mall.getInputCommunityAmount();
//                            sum_inputCommunityRoomAmount = sum_inputCommunityRoomAmount + mall.getInputCommunityRoomAmount();
//                        }
//                    }
//                }
                //拉出所有员工的列表
                int sum_inputMemberAmount = 0;//已录入户数
                int sum_inputCommunityAmount = 0;//录入小区总数
                int sum_inputCommunityRoomAmount = 0;//商场录入社区总户数
//                List<RedstarEmployee> employeeList = dispatchDriver.getRedstarEmployeeManager().getBeanList();

//                if (CollectionUtil.isValid(employeeList)) {
//                    for (RedstarEmployee employee : employeeList) {
//                        if (employee != null) {
//                            sum_inputMemberAmount = sum_inputMemberAmount + DataUtil.getInt(employee.getInputMemberAmount(), 0);
//                            sum_inputCommunityAmount = sum_inputCommunityAmount + DataUtil.getInt(employee.getInputCommunityAmount(), 0);
//                            sum_inputCommunityRoomAmount = sum_inputCommunityRoomAmount + DataUtil.getInt(employee.getInputCommunityRoomAmount(), 0);
//                        }
//                    }
//                }

                List<Object[]> communityList = redstarCommonManager.queryBySql("select ifnull(count(*),0),ifnull(sum(roomMount),0) from xiwa_redstar_community where ownerid>0");
                if (CollectionUtil.isValid(communityList)) {
                    sum_inputCommunityAmount = DataUtil.getInt(communityList.get(0)[0], 0);
                    sum_inputCommunityRoomAmount = DataUtil.getInt(communityList.get(0)[1], 0);
                }
                List<Object[]> memberList = redstarCommonManager.queryBySql("select ifnull(count(*),0) from xiwa_redstar_member where ownerid>0;");
                if (CollectionUtil.isValid(memberList)) {
                    sum_inputMemberAmount = DataUtil.getInt(memberList.get(0), 0);
                }

                //更新集团节点
                RedstarShopMallOrganization topOrganization = (RedstarShopMallOrganization) dispatchDriver.getRedstarShopMallOrganizationManager().getBean(-1);
                topOrganization.setInputMemberAmount(sum_inputMemberAmount);
                topOrganization.setInputCommunityAmount(sum_inputCommunityAmount);
                topOrganization.setInputCommunityRoomAmount(sum_inputCommunityRoomAmount);
                dispatchDriver.getRedstarShopMallOrganizationManager().updateBean(topOrganization);


            }
            RedstarTaskLog redstarTaskLog = new RedstarTaskLog();
            redstarTaskLog.setTaskClassName(this.getClass().getSimpleName());
            redstarTaskLog.setLogDatetime(new Date());
            redstarTaskLog.setRemark("集团录入情况");
            redstarTaskLog.setAction(taskLogEnd);
            redstarTaskLog.setExecuteTime(System.currentTimeMillis() - startTime);
            redstarTaskLogManager.addBean(redstarTaskLog);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
