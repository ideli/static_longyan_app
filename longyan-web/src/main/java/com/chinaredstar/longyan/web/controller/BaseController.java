package com.chinaredstar.longyan.web.controller;

import com.alibaba.dubbo.common.json.JSONObject;
import com.chinaredstar.commonBiz.bean.constant.CommonBizConstant;
import com.chinaredstar.longyan.bean.constant.LanchuiConstant;
import com.chinaredstar.longyan.exception.BusinessException;
import com.chinaredstar.longyan.exception.NoSessionException;
import com.chinaredstar.longyan.exception.constant.CommonExceptionType;
//import com.chinaredstar.uc.dubbo.core.api.IEmployeeService;
import com.chinaredstar.nvwaBiz.bean.NvwaEmployee;
import com.xiwa.base.bean.Response;
import com.xiwa.base.manager.ManagerException;
import com.xiwa.base.pipeline.PipelineContext;
import com.xiwa.base.util.DataUtil;
import com.xiwa.base.util.StringUtil;
import com.xiwa.zeus.trinity.bean.Employee;
import com.xiwa.zeus.util.SessionTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.support.AdvanceControllerSupport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangj on 2015/6/24.
 */
public class BaseController extends AdvanceControllerSupport implements LanchuiConstant {

//    @Autowired
//    private IEmployeeService ucIEmployeeService;

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


    protected void setErrMsg(Response res, String message) {
        res.setCode(HTTP_ERROR_CODE);
        res.setOk(Boolean.FALSE);
        res.setMessage(message);
    }

    protected void setErrCodeAndMsg(Response res, int code, String message) {
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

    protected void setSuccessMsg(Response res, String message) {
        res.setCode(HTTP_SUCCESS_CODE);
        res.setOk(Boolean.TRUE);
        res.setMessage(message);
    }

    protected void setUnknowException(Exception e, Response res) {
        e.printStackTrace();
        res.setCode(DataUtil.getInt(CommonExceptionType.Unknow.getCode(), 0));
        res.setMessage(CommonExceptionType.Unknow.getMessage());
        res.setOk(Boolean.FALSE);
    }

    protected void setBusinessException(BusinessException e, Response res) {
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
    protected NvwaEmployee getEmployeeromSession() throws ManagerException {
        HttpServletRequest request = this.getRequest();
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute(SessionTool.SESSION_EMPLOYEE) != null && StringUtil.isValid(StringUtil.getString(session.getAttribute(SessionTool.SESSION_EMPLOYEE)))) {
            String employeeStr = StringUtil.getString(session.getAttribute(SessionTool.SESSION_EMPLOYEE));
            NvwaEmployee nvwaEmployee = (NvwaEmployee) net.sf.json.JSONObject.toBean(net.sf.json.JSONObject.fromObject(employeeStr),NvwaEmployee.class);
            return nvwaEmployee;
        }
//        Employee employee= (Employee) getObjectFromSession(SESSION_EMPLOYEE);

//        Employee employee = new Employee();
//        employee.setId(11722);
//        employee.setXingMing("张学超");
        return null;
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
