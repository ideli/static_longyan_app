package com.greatbee.web.controller;

import com.greatbee.bean.constant.LanchuiConstant;
import com.greatbee.exception.BusinessException;
import com.greatbee.exception.constant.BasicExceptionType;
import com.greatbee.exception.constant.CommonExceptionType;
import com.greatbee.util.VerifyCommunitySession;
import com.greatbee.util.VerifySession;
import com.lanchui.commonBiz.bean.DispatchCommunityAuthorized;
import com.lanchui.commonBiz.bean.DispatchMerchant;
import com.lanchui.commonBiz.bean.RedstarCommunity;
import com.lanchui.commonBiz.bean.constant.CommonBizConstant;
import com.xiwa.base.bean.Response;
import com.xiwa.base.manager.ManagerException;
import com.xiwa.base.pipeline.PipelineContext;
import com.xiwa.base.pipeline.PipelineException;
import com.xiwa.base.util.DataUtil;
import com.xiwa.zeus.trinity.bean.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.support.AdvanceControllerSupport;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangj on 2015/6/24.
 */
public class BaseController extends AdvanceControllerSupport implements LanchuiConstant {

    @Autowired
    private VerifySession verifySession;
    @Autowired
    private VerifyCommunitySession verifyCommunitySession;

    protected void _error(String message, PipelineContext context) {
        this._error(new Exception(message), context);
    }

    /**
     * 错误信息，供其它controller调用
     *
     * @param e
     * @param context
     */
    protected void _error(Exception e, PipelineContext context) {
        e.printStackTrace();
        context.getResponse().setOk(false);
        context.getResponse().setMessage(e.getMessage());
        logger.error(context.getRequest().getHttpServletRequest().getRequestURL());
        logger.error(e);
        logger.error(e.getMessage());
        logger.error(e.toString());
    }


    protected void setErrMsg(Response res,String message) {
        res.setCode(HTTP_ERROR_CODE);
        res.setOk(Boolean.FALSE);
        res.setMessage(message);
    }

    protected void setErrCodeAndMsg(Response res,int code,String message) {
        res.setCode(code);
        res.setOk(Boolean.FALSE);
        res.setMessage(message);
    }

    protected void setExceptionMsg(Response res) {
        res.setCode(HTTP_ERROR_CODE);
        res.setOk(Boolean.FALSE);
        res.setMessage(CommonBizConstant.Http_Server_Exception_Message);
    }

    protected void setInvokeExceptionMsg(Response res) {
        res.setCode(500);
        res.setOk(Boolean.FALSE);
        res.setMessage(CommonBizConstant.Http_Server_Exception_Message);
    }

    protected void setSuccessMsg(Response res) {
        res.setCode(HTTP_SUCCESS_CODE);
        res.setOk(Boolean.TRUE);
        res.setMessage(CommonBizConstant.Http_Query_Success_Message);
    }

    protected void setSuccessMsg(Response res,String message) {
        res.setCode(HTTP_SUCCESS_CODE);
        res.setOk(Boolean.TRUE);
        res.setMessage(message);
    }

    protected void setUnknowException(Exception e,Response res){
        e.printStackTrace();
        res.setCode(DataUtil.getInt(CommonExceptionType.Unknow.getCode(), 0));
        res.setMessage(CommonExceptionType.Unknow.getMessage());
        res.setOk(Boolean.FALSE);
    }
    protected void setBusinessException(BusinessException e,Response res){
        res.setCode(DataUtil.getInt(e.getErrorCode(), 0));
        res.setMessage(e.getMessage());
        res.setOk(Boolean.FALSE);
    }
    /**
     * 获取session 中的员工信息
     *
     * @return
     * @throws ManagerException
     */
    protected Employee getEmployeeromSession() throws ManagerException {
        HttpServletRequest request = this.getRequest();
        Employee employee= (Employee) getObjectFromSession(SESSION_EMPLOYEE);

//        Employee employee =new Employee();
//        employee.setId(11722);
//        employee.setXingMing("张学超");
        return employee;
    }

    /**
     * 获取session 中的商户信息
     *
     * @return
     * @throws ManagerException
     */
    protected DispatchMerchant getMerchantFromSession() throws ManagerException {
        try {
            verifySession.execute(this.buildPipelineContent());
        } catch (PipelineException e) {
            logger.error(e.getMessage());
            return null;
            //不做其他处理
        }
        return (DispatchMerchant) this.getObjectFromSession(Merchant_Session);
    }

    /**
     * 获取session 中的社区信息
     *
     * @return
     * @throws ManagerException
     */
    protected RedstarCommunity getCommunityFromSession() throws ManagerException {
        try {
            verifyCommunitySession.execute(this.buildPipelineContent());
        } catch (PipelineException e) {
            logger.error(e.getMessage());
            return null;
            //不做其他处理
        }
        return (RedstarCommunity) this.getObjectFromSession(Community_Session);
    }

    /**
     * 获取session 中的社区用户信息
     *
     * @return
     * @throws ManagerException
     */
    protected DispatchCommunityAuthorized getCommunityAuthorizedFromSession() throws ManagerException {
        try {
            verifyCommunitySession.execute(this.buildPipelineContent());
        } catch (PipelineException e) {
            logger.error(e.getMessage());
            return null;
            //不做其他处理
        }
        return (DispatchCommunityAuthorized) this.getObjectFromSession(Community_Auth_Session);
    }

    /**
     * 获取session 中的值
     *
     * @param key
     * @return
     * @throws ManagerException
     */
    protected Object getObjectFromSession(String key) throws ManagerException {
        HttpServletRequest request = this.getRequest();
        if (request.getSession().getAttribute("session_data_map") != null) {
            Map<String, Object> map = (Map<String, Object>) request.getSession().getAttribute("session_data_map");
            return map.get(key);
        }
        return null;
    }

    /**
     * 设置session 值
     *
     * @param key
     * @param value
     * @throws ManagerException
     */
    protected void addObjectToSession(String key, Object value) throws ManagerException {
        HttpServletRequest request = this.getRequest();
        if (request.getSession().getAttribute("session_data_map") == null) {
            request.getSession().setAttribute("session_data_map", new HashMap<String, Object>());
        }
        Map<String, Object> map = (Map<String, Object>) request.getSession().getAttribute("session_data_map");
        map.put(key, value);
    }

}