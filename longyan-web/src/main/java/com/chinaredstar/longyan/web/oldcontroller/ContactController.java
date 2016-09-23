package com.chinaredstar.longyan.web.oldcontroller;

import com.chinaredstar.longyan.api.ContactService;
import com.chinaredstar.commonBiz.bean.ContactVo;
import com.chinaredstar.commonBiz.bean.constant.CommonBizConstant;
import com.chinaredstar.longyan.web.controller.BaseController;
import com.xiwa.base.bean.Response;
import com.xiwa.base.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by niu on 2016/8/2.
 * 通讯录
 */
//@RestController
//@RequestMapping(value = "/contact")
public class ContactController extends BaseController implements CommonBizConstant {
//
//
//    @Autowired
//    private ContactService contactService;
//
//    /**
//     * 获取部门列表
//     * @param contactVo
//     * @return
//     */
//    @RequestMapping(value = "/deptList",method = RequestMethod.GET)
//    public  Object getDeptDataList(ContactVo contactVo){
//        Response res = buildPipelineContent().getResponse();
//        try{
//            //部门id
//            return contactService.getDepartmentList(contactVo.getDepartmentId());
//        }catch (Exception e){
//            e.printStackTrace();
//            setInvokeExceptionMsg(res);
//        }
//
//        return  res;
//    }
//
//    /**
//     * 获取部门员工
//     * @param contactVo
//     * @return
//     */
//    @RequestMapping(value = "/deptEmployeeList",method = RequestMethod.POST)
//    public  Object getDeptEmployeeList(ContactVo contactVo){
//
//        Response res = buildPipelineContent().getResponse();
//
//        //搜索关键词
//        String  keyWord = contactVo.getKeyWord();
//
//        //部门id和关键字必须存在一个
//        if (StringUtil.isInvalid(keyWord)){
//            setErrMsg(res,"参数缺失");
//            return  res;
//        }
//
//        try{
//            return contactService.getEmployeeByName(keyWord);
//        }catch (Exception e){
//            e.printStackTrace();
//            setInvokeExceptionMsg(res);
//        }
//
//        return  res;
//    }
//
//    /**
//     * 根据员工id获取员工详情
//     * @param contactVo
//     * @return
//     */
//    @RequestMapping(value = "/employeeInfo",method = RequestMethod.POST)
//    public  Object getEmployeeInfo(ContactVo contactVo){
//
//        Response res = buildPipelineContent().getResponse();
//
//        if (StringUtil.isInvalid(contactVo.getEmployeeId())){
//            setErrMsg(res,"参数缺失");
//            return  res;
//        }
//
//        try{
//            return  contactService.getEmployeeInfo(contactVo.getEmployeeId());
//        }catch (Exception e){
//            e.printStackTrace();
//            setInvokeExceptionMsg(res);
//        }
//        return  res;
//    }

}
