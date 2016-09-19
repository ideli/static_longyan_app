package com.chinaredstar.longyan.web.controller;


import com.chinaredstar.commonBiz.bean.*;
import com.chinaredstar.longyan.security.util.SecurityTool;
import com.chinaredstar.longyan.util.HttpClientUtil;
import com.chinaredstar.commonBiz.bean.constant.CommonBizConstant;
import com.chinaredstar.commonBiz.manager.DispatchDriver;
import com.chinaredstar.commonBiz.util.CookieUtil;
import com.chinaredstar.nvwaBiz.bean.NvwaDepartment;
import com.chinaredstar.nvwaBiz.bean.NvwaEmployee;
import com.chinaredstar.nvwaBiz.manager.NvwaDriver;
import com.xiwa.base.bean.Request;
import com.xiwa.base.bean.Response;
import com.xiwa.base.bean.search.ext.IntSearch;
import com.xiwa.base.bean.search.ext.MultiSearchBean;
import com.xiwa.base.bean.search.ext.TextSearch;
import com.xiwa.base.manager.ManagerException;
import com.xiwa.base.pipeline.PipelineContext;
import com.xiwa.base.util.CollectionUtil;
import com.xiwa.base.util.MD5Util;
import com.xiwa.base.util.StringUtil;
import com.xiwa.security.bean.constant.AuthTarget;
import com.xiwa.security.bean.ext.SimpleAuthorized;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 社区授权
 * Created by wangj on 2015/7/21.
 */
@Controller
@RequestMapping(value = "/auth")
public class AuthController extends BaseController implements CommonBizConstant {

    @Autowired
    private DispatchDriver dispatchDriver;
    @Autowired
    private NvwaDriver nvwaDriver;

    /**
     * 用户中心新接口 调用ad登录
     *
     * @param resp
     * @return
     */
    @RequestMapping(value = "/login")
    public
    @ResponseBody
    Response login(HttpServletResponse resp, HttpServletRequest httpServletRequest) {

        //Long loginStart =System.currentTimeMillis();


        PipelineContext pipelineContext = buildPipelineContent();
        logger.info("[AuthController][login][request] pipelineContext=" + JSONObject.fromObject(pipelineContext.getRequest().getDataMap()).toString());
        Request request = this.buildPipelineContent().getRequest();
        Response res = this.buildPipelineContent().getResponse();
        String username = request.getString("username");
        String password = request.getString("password");

        //调用授权接口
        this._auth(username, password, pipelineContext);

        return pipelineContext.getResponse();
    }

    /**
     * 授权
     *
     * @param username
     * @param password
     * @param pipelineContext
     */
    private void _auth(String username, String password, PipelineContext pipelineContext) {
        try {
            //校验电话号码格式
            Pattern pattern = Pattern.compile(Reg_Phone);
            Matcher matcher = pattern.matcher(username);
/*            if (StringUtil.isInvalid(username) || !matcher.matches()) {
                throw new ManagerException("手机号码格式错误,请重新输入");
            }*/
            if (StringUtil.isInvalid(username)) {
                setErrMsg(pipelineContext.getResponse(), "用户名不能为空");
                return;
            }
            //得到配置参数
            HttpServletRequest httpRequest = this.getRequest();
            WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(httpRequest.getSession().getServletContext());
            HashMap systemConfig = (HashMap) context.getBean("systemConfig");
            String appId = systemConfig.get("appId").toString();
            String appSecret = systemConfig.get("appSecret").toString();
            String userCenterUrl = systemConfig.get("userCenterUrl").toString();

            Map<String, String> params = new HashMap<String, String>();
            params.put("appId", appId);
            params.put("appSecret", appSecret);
            params.put("username", username);
            params.put("password", password);
            params.put("loginIP", pipelineContext.getRequest().getHttpServletRequest().getRemoteAddr());

            //调用新接口
            JSONObject jsonObje = HttpClientUtil.post(userCenterUrl + "/employee/login/ad", params);
            logger.info("----->结束调用用户中心接口时间--" + System.currentTimeMillis());
            logger.info(jsonObje.toString());

            if (jsonObje.getInt(UserCenter_Res_ErrorCode) != 0) {
                //用户中心返回的错误信息
                setErrMsg(pipelineContext.getResponse(), jsonObje.getString(UserCenter_Res_ErrorMsg));
                return;
            }

            Map<String, String> employee = (Map) jsonObje.get("data");

            String emplId = employee.get("emplid");
            String name = employee.get("name");
            String sex = employee.get("sex");
            String hr_status = employee.get("hrStatus");

            if ("I".equals(hr_status)) {
                setErrMsg(pipelineContext.getResponse(), "无效用户");
                return;
            }

/*            String status=employee.get("status");
            if ("1".equals(status)){
                setErrMsg(res,"用户状态不正常");
                return  res;
            }*/

            String deptid = employee.get("deptid");
            String market_id = employee.get("marketId");
            String market_descr = employee.get("marketDescr");
            String moblie = employee.get("phone");

            //根据employeeCode查询员工id
            NvwaEmployee redstarEmployee = nvwaDriver.getNvwaEmployeeManager().getEmployeeByCode(emplId);
            //如果不存在添加员工信息
            if (redstarEmployee == null) {
                //添加员工信息
                redstarEmployee = new NvwaEmployee();
                redstarEmployee.setEmployeeCode(emplId);
                redstarEmployee.setXingMing(name);
                redstarEmployee.setBelongedId(10944);
                redstarEmployee.setShowData(true);

                if (sex.equals("男")) {
                    redstarEmployee.setGender("male");
                }
                if (sex.equals("女")) {
                    redstarEmployee.setGender("female");
                }
                redstarEmployee.setHrStatus(hr_status);
                if (StringUtil.isValid(deptid)) {
                    List<NvwaDepartment> departments = nvwaDriver.getNvwaDepartmentManager().getBeanListByColumn("departmentCode", deptid);
                    if (CollectionUtil.isValid(departments)) {
                        NvwaDepartment department = departments.get(0);
                        redstarEmployee.setDepartmentId(department.getId());
                    } else {
                        //没有部门,添加部门数据
                        setErrMsg(pipelineContext.getResponse(), "部门异常,请联系管理员");
                        return;
                    }
                }
                redstarEmployee.setOpenId(emplId);
                Integer objectId = nvwaDriver.getNvwaEmployeeManager().addBean(redstarEmployee);

                if (StringUtil.isValid(market_id)) {
                    //商场是否存在
                    TextSearch textSearch = new TextSearch("mallCode");
                    textSearch.setSearchValue(market_id);
                    List<RedstarShoppingMall> mallList = dispatchDriver.getRedstarShoppingMallManager().searchIdentify(textSearch);

                    Integer mallId = 0;
                    if (CollectionUtils.isEmpty(mallList)) {
                        RedstarShoppingMall shoppingMall = new RedstarShoppingMall();
                        shoppingMall.setName(market_descr);
                        shoppingMall.setMallCode(String.valueOf(market_id));
                        mallId = dispatchDriver.getRedstarShoppingMallManager().addBean(shoppingMall);
                    } else {
                        RedstarShoppingMall mall = mallList.get(0);
                        mallId = mall.getId();
                    }

                    //检查员工商场关系是否存在
                    MultiSearchBean relationSearch = new MultiSearchBean();
                    IntSearch mallIdSearch = new IntSearch("employeeId");
                    mallIdSearch.setSearchValue(String.valueOf(objectId));
                    IntSearch empSearch = new IntSearch("shoppingMallId");
                    empSearch.setSearchValue(String.valueOf(mallId));
                    relationSearch.addSearchBean(mallIdSearch);
                    relationSearch.addSearchBean(empSearch);

                    if (CollectionUtils.isEmpty(dispatchDriver.getRedstarMallEmployeeManager().searchIdentify(relationSearch))) {
                        //添加员工商场表
                        RedstarMallEmployee redstarMallEmployee = new RedstarMallEmployee();
                        redstarMallEmployee.setEmployeeId(objectId);
                        redstarMallEmployee.setEmployeeXingMing(name);
                        redstarMallEmployee.setShoppingMallId(mallId);
                        redstarMallEmployee.setShoppingMallName(market_descr);
                        dispatchDriver.getRedstarMallEmployeeManager().addBean(redstarMallEmployee);
                    }
                }

                //添加员工授权表
                String roles = systemConfig.get("roles").toString();//需要在config.properties里配置
                SimpleAuthorized authorized = new SimpleAuthorized();
                authorized.setBelongedId(10944);
                authorized.setObjectId(objectId);
                authorized.setObjectIdentified("employee");
                authorized.setObjectDesc("员工注册时新增");
                authorized.setAccount(moblie);
                authorized.setActive(true);
                authorized.setRoles(roles);
                authorized.setPassword("");
                authorized.setHaveSetPassword(true);
                nvwaDriver.getNvwaEmployeeManager().addAuthorized(authorized);
            }


        } catch (ManagerException e) {
            _error(e, pipelineContext);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @RequestMapping(value = "/logincus")
    public
    @ResponseBody
    @Deprecated
    Response login_cus(HttpServletResponse resp) {

        Long loginStart = System.currentTimeMillis();
        logger.info("----->用户登录,开始时间--" + loginStart);

        PipelineContext pipelineContext = buildPipelineContent();
        logger.info("[AuthController][login][request] pipelineContext=" + JSONObject.fromObject(pipelineContext.getRequest().getDataMap()).toString());
        Request request = this.buildPipelineContent().getRequest();
        Response res = this.buildPipelineContent().getResponse();
        String username = request.getString("username");
        String password = request.getString("password");

        try {
            //校验电话号码格式
            Pattern pattern = Pattern.compile(Reg_Phone);
            Matcher matcher = pattern.matcher(username);
            if (StringUtil.isInvalid(username) || !matcher.matches()) {
                throw new ManagerException("手机号码格式错误,请重新输入");
            }
            //得到配置参数
            HttpServletRequest httpRequest = this.getRequest();
            WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(httpRequest.getSession().getServletContext());
            HashMap systemConfig = (HashMap) context.getBean("systemConfig");
            //通过oauth2 client 验证模式获取token
            String grantType = "client_credentials";
            String appId = systemConfig.get("appId").toString();
            String appSecret = systemConfig.get("appSecret").toString();
            String userCenterUrl = systemConfig.get("userCenterUrl").toString();
            Map<String, String> params = new HashMap<String, String>();
            params.put("grantType", grantType);
            params.put("appId", appId);
            params.put("appSecret", appSecret);

            Long start = System.currentTimeMillis();
            logger.info("----->开始调用用户中心接口时间--" + start);

            //获取token
            JSONObject jsonObject = HttpClientUtil.post(userCenterUrl + "/employee/getTokenByClient", params);
            //判断errorCode返回类型
            if (jsonObject.getInt("errorCode") == 40002) {
                setErrMsg(res, "授权类型必须是authorization_code");
                res.setCode(40002);
                return res;
            }
            String accessToken = jsonObject.getJSONObject("data").get("accessToken").toString();
            //调用用户中心接口通过用户登入获取员工信息
            params.put("username", username);
            params.put("password", MD5Util.getMD5(password));
            params.put("accessToken", accessToken);
            params.put("rememberMe", "false");
            JSONObject jsonObje = HttpClientUtil.post(userCenterUrl + "/employee/login", params);
            logger.info("----->结束调用用户中心接口时间--" + System.currentTimeMillis());
            logger.info("----->调用用户中心接口结束耗时--" + (System.currentTimeMillis() - start));


            String emplId = "50014408";

            String openid = "50014408";

            String userToken = "509d0ea4-21c3-426c-bb76-ef78ac2fa262";
            String refreshToken = "09f05b37-aacf-4b50-88ff-2fc7f38314c6";
            String moblie = "18523295297";

            //根据employeeCode查询员工id
            NvwaEmployee redstarEmployee = nvwaDriver.getNvwaEmployeeManager().getEmployeeByCode(emplId);

//            Authorized authorized = this.securityDriver.getAuthorizedManager().getAuthorized(10944,redstarEmployee.getId(),"employee");
//            if (authorized != null) {
//                if (authorized.isActive()) {
//                    Employee loginEmployee = (Employee) this.trinityDriver.getBasicEmployeeManager().getBean(authorized.getObjectId());
//                    this._auth(authorized.getBelongedId(), authorized.getRoles(), loginEmployee,openid,userToken,refreshToken,resp);
//                    loginEmployee.setDepartment((Department) null);
//                    pipelineContext.getResponse().addKey("my_info", loginEmployee);
//                    pipelineContext.getResponse().addKey("accessToken",userToken);
//                    pipelineContext.getResponse().addKey("openid",openid);
//                } else {
//                    pipelineContext.getResponse().setOk(false);
//                    pipelineContext.getResponse().setMessage("登陆失败");
//                }
//            } else {
//                String roles = systemConfig.get("roles").toString();//需要在config.properties里配置
//                SimpleAuthorized authoriz = new SimpleAuthorized();
//                authoriz.setBelongedId(10944);
//                authoriz.setObjectId(redstarEmployee.getId());
//                authoriz.setObjectIdentified("employee");
//                authoriz.setObjectDesc("员工登录时新增");
//                authoriz.setAccount(moblie);
//                authoriz.setActive(true);
//                authoriz.setRoles(roles);
//                authoriz.setPassword("");
//                authoriz.setHaveSetPassword(true);
//                //添加员工授权表
//                dispatchDriver.getRedstarEmployeeManager().addAuthorized(authoriz);
//                Employee loginEmployee = (Employee) this.trinityDriver.getBasicEmployeeManager().getBean(redstarEmployee.getId());
//                this._auth(authoriz.getBelongedId(), authoriz.getRoles(), loginEmployee,openid,userToken,refreshToken,resp);
//            }

            String ua = pipelineContext.getRequest().getHttpServletRequest().getHeader("User-Agent");

        } catch (ManagerException e) {
            _error(e, pipelineContext);
        } catch (Exception e) {
            e.printStackTrace();
        }

        logger.info("----->用户登录,结束时间--" + System.currentTimeMillis());
        logger.info("----->用户登录完成,耗时--" + (System.currentTimeMillis() - loginStart));

        return pipelineContext.getResponse();
    }


    /**
     * 注销账号
     *
     * @param resp
     * @return
     */
    @RequestMapping(value = "/logout")
    public
    @ResponseBody
    Response logout(HttpServletResponse resp) {
        PipelineContext pipelineContext = buildPipelineContent();
        logger.info("[AuthController][logout][request] pipelineContext=" + JSONObject.fromObject(pipelineContext.getRequest().getDataMap()).toString());

        try {
            if (SecurityTool.isSecurityLogin(AuthTarget.Employee)) {
                //已经登陆过了,注销流程
//                HttpServletRequest request = pipelineContext.getRequest().getHttpServletRequest();
//                Map<String, Object> map = (Map<String, Object>) request.getSession().getAttribute(SESSION_DATA_MAP);
//                map.remove(SESSION_EMPLOYEE);
                //清理session
                SecurityTool.destroySecuritySession();
                //清理token
                CookieUtil.setCookie("_token", "", 0, "/", resp);
            }
        } catch (Exception e) {
            _error(e, pipelineContext);
        }
        return pipelineContext.getResponse();
    }

    @RequestMapping(value = "/logout_pc")
    public
    @ResponseBody
    Response logoutPC(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        PipelineContext pipelineContext = buildPipelineContent();

        try {
            if (SecurityTool.isSecurityLogin(AuthTarget.Employee)) {
                //已经登陆过了,注销流程
//                HttpServletRequest request = pipelineContext.getRequest().getHttpServletRequest();
//                Map<String, Object> map = (Map<String, Object>) request.getSession().getAttribute(SESSION_DATA_MAP);
//                map.remove(SESSION_EMPLOYEE);
                //清理session
                SecurityTool.destroySecuritySession();
                //清理token
                CookieUtil.setCookie("_token", "", 0, "/", httpServletResponse);
            }

            httpServletResponse.sendRedirect("/longyan.jsp#login");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //跳转到登录界面

        return pipelineContext.getResponse();
    }


    private void _checkPhone(String phone) throws ManagerException {
        String regExp = "^[0-9]*$";
        Pattern p = Pattern.compile(regExp);
        String checkphone = phone.replace("-", "");
        Matcher m = p.matcher(checkphone);
        if (!m.find()) { //注意：m.find只能用一次，第二次调用后都为false
            throw new ManagerException("手机号码格式不正确");
        }
    }
}
