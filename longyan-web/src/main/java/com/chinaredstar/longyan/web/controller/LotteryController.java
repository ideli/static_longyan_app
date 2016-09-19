package com.chinaredstar.longyan.web.controller;

import com.chinaredstar.longyan.api.LotteryService;
import com.chinaredstar.nvwaBiz.bean.NvwaEmployee;
import com.chinaredstar.commonBiz.bean.constant.CommonBizConstant;
import com.chinaredstar.commonBiz.manager.DispatchDriver;
import com.chinaredstar.nvwaBiz.manager.NvwaDriver;
import com.xiwa.base.bean.Response;
import com.xiwa.base.manager.ManagerException;
import com.xiwa.zeus.trinity.bean.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by niu on 2016/8/3
 * 砸蛋抽奖
 */
@RestController
@RequestMapping(value = "/lottery")
public class LotteryController extends BaseController implements CommonBizConstant {

    @Autowired
    private LotteryService lotteryService;

    @Autowired
    private DispatchDriver dispatchDriver;
    @Autowired
    NvwaDriver nvwaDriver;

    //砸蛋
    @RequestMapping(value = "/drawLottery",method = RequestMethod.POST)
    public Object lottery(){
        Response res = buildPipelineContent().getResponse();
        try{
            return  lotteryService.drawLottery(getCurrentEmployee().getEmployeeCode());
        }catch (Exception e){
            e.printStackTrace();
            setExceptionMsg(res);
        }
        return  res;
    }

    //是否可以砸蛋
    @RequestMapping(value = "/validLottery")
    public Object validLottery(){
        Response res = buildPipelineContent().getResponse();
        try{
            return  lotteryService.validLotteryAuth(getCurrentEmployee().getEmployeeCode());
        }catch (Exception e){
            e.printStackTrace();
            setExceptionMsg(res);
        }
        return  res;
    }


    //获取我的奖品
    @RequestMapping(value = "/myPresent")
    public Object getMyPresent(){
        Response res = buildPipelineContent().getResponse();
        try{
            return lotteryService.getMyWinningList(getCurrentEmployee().getEmployeeCode());
        }catch (Exception e){
            e.printStackTrace();
            setExceptionMsg(res);
        }
        return  res;
    }

    //获奖列表
    @RequestMapping(value = "/presentList")
    public Object getPresentList(){
        Response res = buildPipelineContent().getResponse();
        try{
            return lotteryService.getWinningList();
        }catch (Exception e){
            e.printStackTrace();
            setExceptionMsg(res);
        }
        return  res;
    }

    //获取砸蛋地点信息
    @RequestMapping(value = "/location")
    public Object getLocation(){
        Response res = buildPipelineContent().getResponse();
        try{
            return lotteryService.getLotteryLocation();
        }catch (Exception e){
            e.printStackTrace();
            setExceptionMsg(res);
        }
        return  res;
    }


    private NvwaEmployee getCurrentEmployee() throws ManagerException {

/*        RedstarEmployee redstarEmployee = new RedstarEmployee();
        redstarEmployee.setEmployeeCode("51004893");
        return  redstarEmployee;*/

        Employee employee = getEmployeeromSession();
        return (NvwaEmployee) nvwaDriver.getNvwaEmployeeManager().getBean(employee.getId());
    }

}
