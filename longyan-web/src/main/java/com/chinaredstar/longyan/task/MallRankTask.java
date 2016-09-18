package com.chinaredstar.longyan.task;

import com.chinaredstar.commonBiz.bean.RedstarEmployee;
import com.chinaredstar.commonBiz.bean.RedstarMallEmployee;
import com.chinaredstar.longyan.bean.constant.LanchuiConstant;
import com.chinaredstar.commonBiz.bean.RedstarShoppingMall;
import com.chinaredstar.commonBiz.bean.RedstarTaskLog;
import com.chinaredstar.commonBiz.manager.DispatchDriver;
import com.chinaredstar.commonBiz.manager.RedstarTaskLogManager;
import com.xiwa.base.manager.ManagerException;
import com.xiwa.base.util.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * REDSTAR-93
 * 陈成 2016-04-28
 * 商场排名
 */
@Component(value = "mallRankTask")
public class MallRankTask implements LanchuiConstant {

    @Autowired
    private DispatchDriver dispatchDriver;

    @Autowired
    private RedstarTaskLogManager redstarTaskLogManager;


    /**
     * 计算商场排名
     *
     * @throws ManagerException
     */
    public void mallRank() throws ManagerException {

        Long startTime = System.currentTimeMillis();

        try {
            {
                RedstarTaskLog redstarTaskLog = new RedstarTaskLog();
                redstarTaskLog.setTaskClassName(this.getClass().getSimpleName());
                redstarTaskLog.setLogDatetime(new Date());
                redstarTaskLog.setRemark("商场排名统计");
                redstarTaskLog.setAction(taskLogStart);
                redstarTaskLogManager.addBean(redstarTaskLog);
                //计算出商场下属员工录入住户总和，录入社区总和。分别写入到，inputMemberAmount 和 inputCommunityAmount
                List<RedstarShoppingMall> shoppingMallList = dispatchDriver.getRedstarShoppingMallManager().getBeanList();
                for (RedstarShoppingMall redstarShoppingMall : shoppingMallList) {
                    try {
                        int inputMemberAmount = 0;
                        int inputCommunityAmount = 0;
                        int inputCommunityRoomAmount = 0;

                        List<RedstarMallEmployee> mallEmployeeList = dispatchDriver.getRedstarMallEmployeeManager().getBeanListByColumn("shoppingMallId", redstarShoppingMall.getId());
                        if (CollectionUtil.isValid(mallEmployeeList)) {
                            //如果该商场有员工

                            for (RedstarMallEmployee mallEmployee : mallEmployeeList) {
                                try {
                                    //列出该员工信息
                                    RedstarEmployee employee = (RedstarEmployee) dispatchDriver.getRedstarEmployeeManager().getBean(mallEmployee.getEmployeeId());
                                    if (employee != null) {
                                        inputMemberAmount = inputMemberAmount + employee.getInputMemberAmount();
                                        inputCommunityAmount = inputCommunityAmount + employee.getInputCommunityAmount();
                                        inputCommunityRoomAmount=inputCommunityRoomAmount+employee.getInputCommunityRoomAmount();
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        redstarShoppingMall.setInputCommunityAmount(inputCommunityAmount);
                        redstarShoppingMall.setInputMemberAmount(inputMemberAmount);
                        redstarShoppingMall.setInputCommunityRoomAmount(inputCommunityRoomAmount);

                        dispatchDriver.getRedstarShoppingMallManager().updateBean(redstarShoppingMall);

//                        //根据商场ID去除全部小区数量,redstar_mall_community
//                        List<RedstarMallCommunity> redstarMallCommunityList = dispatchDriver.getRedstarMallCommunityManager().getBeanListByColumn("shoppingMallId", redstarShoppingMall.getId());
//                        if (redstarMallCommunityList.size() > 0) {
//                            for (RedstarMallCommunity redstarMallCommunity : redstarMallCommunityList) {
//                                inputMemberAmount += redstarMallCommunity.getMemberInputAmount();//累加商场小区录入住户数
//                                inputCommunityAmount++;//累加商场小区
//                            }
//                        }
////                        redstarShoppingMall.setInputMemberAmount(inputMemberAmount);
////                        redstarShoppingMall.setInputCommunityAmount(inputCommunityAmount);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            //目前不需要计算排名
//            {
//                //计算 商场的录入住户排名，写入到 inputMemberAmountRank
//                List<RedstarShoppingMall> redstarShoppingMallList = dispatchDriver.getRedstarShoppingMallManager().getBeanList("inputMemberAmount", false);
//                int i = 1;
//                for (RedstarShoppingMall redstarShoppingMall : redstarShoppingMallList) {
//                    try {
//                        redstarShoppingMall.setInputMemberAmountRank(i);
//                        dispatchDriver.getRedstarShoppingMallManager().updateBean(redstarShoppingMall);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    } finally {
//                        i++;
//                    }
//                }
//            }
//            {
//                //计算 商场的录入社区排名，写入inputCommunityAmountRank
//                List<RedstarShoppingMall> redstarShoppingMallList = dispatchDriver.getRedstarShoppingMallManager().getBeanList("inputCommunityAmount", false);
//                int i = 1;
//                for (RedstarShoppingMall redstarShoppingMall : redstarShoppingMallList) {
//                    try {
//                        redstarShoppingMall.setInputCommunityAmountRank(i);
//                        dispatchDriver.getRedstarShoppingMallManager().updateBean(redstarShoppingMall);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    } finally {
//                        i++;
//                    }
//                }


            RedstarTaskLog redstarTaskLog = new RedstarTaskLog();
            redstarTaskLog.setTaskClassName(this.getClass().getSimpleName());
            redstarTaskLog.setLogDatetime(new Date());
            redstarTaskLog.setRemark("商场排名统计");
            redstarTaskLog.setAction(taskLogEnd);
            redstarTaskLog.setExecuteTime(System.currentTimeMillis()-startTime);
            redstarTaskLogManager.addBean(redstarTaskLog);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
