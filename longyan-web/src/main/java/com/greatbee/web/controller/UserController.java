

package com.greatbee.web.controller;

import com.greatbee.bean.StorageConfig;
import com.greatbee.bean.constant.LanchuiConstant;
import com.greatbee.util.HttpClientUtil;
import com.greatbee.util.ReportUtil;
import com.greatbee.util.SpringUtil;
import com.greatbee.util.UploadFileUtil;
import com.lanchui.commonBiz.bean.*;
import com.lanchui.commonBiz.bean.constant.CommonBizConstant;
import com.lanchui.commonBiz.manager.*;
import com.lanchui.commonBiz.util.CodeUtil;
import com.lanchui.commonBiz.util.CookieUtil;
import com.xiwa.base.bean.Identified;
import com.xiwa.base.bean.Request;
import com.xiwa.base.bean.Response;
import com.xiwa.base.bean.search.ext.BooleanSearch;
import com.xiwa.base.bean.search.ext.IntSearch;
import com.xiwa.base.bean.search.ext.MultiSearchBean;
import com.xiwa.base.bean.search.ext.TextSearch;
import com.xiwa.base.manager.ManagerException;
import com.xiwa.base.pipeline.PipelineContext;
import com.xiwa.base.util.*;
import com.xiwa.security.bean.Authorized;
import com.xiwa.security.bean.ext.SimpleAuthorized;
import com.xiwa.security.driver.SecurityDriver;
import com.xiwa.zeus.trinity.bean.Employee;

import net.sf.json.JSONObject;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lenovo on 2016/4/21.
 * <p/>
 * 2016.08.03 旧接口 暂时弃用
 */
@Controller
@RequestMapping("/oldUser")
public class UserController extends BaseController implements CommonBizConstant {

    @Autowired
    private DispatchDriver dispatchDriver;

    @Autowired
    private RedstarShoppingMallManager redstarShoppingMallManager;

    @Autowired
    private RedstarMallEmployeeManager redstarMallEmployeeManager;

    @Autowired
    private SecurityAuthorizedManager securityAuthorizedManager;

    @Autowired
    private RedstarCommonManager redstarCommonManager;

    @Autowired
    private StorageConfig storageConfig;
    @Autowired
    protected SecurityDriver securityDriver;


    //弃用
    @RequestMapping(value = "/----login-username-password", method = RequestMethod.POST)
    @ResponseBody
    public Response login() {
        Request request = this.buildPipelineContent().getRequest();
        Response res = this.buildPipelineContent().getResponse();
        try {
            String username = request.getString("username");
            String password = request.getString("password");
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
            if (jsonObje.getInt("errorCode") == 46010) {
                setErrMsg(res, "密码错误");
                res.setCode(46010);
                return res;
            }
            if (jsonObje.getInt("errorCode") == 46004) {
                setErrMsg(res, "用户名不存在");
                res.setCode(46004);
                return res;
            }
            Object employee = jsonObje.getJSONObject("data").get("employee").toString();
            res.addKey("employee", employee);
            res.setCode(HTTP_SUCCESS_CODE);
            res.setMessage("登录成功");
            res.setOk(true);
        } catch (Exception e) {
            e.printStackTrace();
            setErrMsg(res, "服务器响应异常");
        }
        return res;
    }


    //弃用
    @RequestMapping(value = "/----reset-login-password-send-otp", method = RequestMethod.POST)
    @ResponseBody
    public Response sendForgetSms() {

        Request request = this.buildPipelineContent().getRequest();
        Response res = this.buildPipelineContent().getResponse();
        try {

            String phone = request.getString("phone");

            if (StringUtil.isInvalid(phone)) {
                setErrMsg(res, "手机号不能为空");
                return res;
            }
            Pattern pattern = Pattern.compile(Reg_Phone);
            Matcher matcher = pattern.matcher(phone);
            if (!matcher.matches()) {
                throw new ManagerException("手机号码格式错误,请重新输入");
            }


            //根据手机号码查询用户信息
            RedstarEmployee redstarEmployee = dispatchDriver.getRedstarEmployeeManager().getEmployeeByPhone(phone);
            if (redstarEmployee == null) {
                setErrMsg(res, "用户不存在");
                return res;
            }


            //插入短信验证码表
            RedstarVerifyCode verifyCode = new RedstarVerifyCode();
            verifyCode.setCreateDate(new Date(System.currentTimeMillis()));
            verifyCode.setPhone(phone);
            verifyCode.setCode(CodeUtil.getVerifyCode());
            dispatchDriver.getRedstarVerifyCodeManager().addBean(verifyCode);
            res.setCode(HTTP_SUCCESS_CODE);
            res.setMessage("发送验证码短信成功");

            //用户存在 执行发送短信

        } catch (Exception e) {
            setErrMsg(res, "短信验证码发送失败");
        }
        return res;
    }

    /**********************用户中心新接口 重置密码*******************************/
    @RequestMapping(value = "/reset-password", method = RequestMethod.POST)
    @ResponseBody
    public Response resetPassWord() {
        Request request = this.buildPipelineContent().getRequest();
        Response res = this.buildPipelineContent().getResponse();
        try {
            String smsCode = request.getString("code");
            String mobile = request.getString("phone");
            String newPassword = request.getString("password");
            if (StringUtil.isInvalid(mobile)) {
                setErrMsg(res, "手机号不能为空");
                return res;
            }
            if (StringUtil.isInvalid(newPassword)) {
                setErrMsg(res, "密码不能为空");
                return res;
            }
            if (StringUtil.isInvalid(smsCode)) {
                setErrMsg(res, "验证码不能为空");
                return res;
            }
            HashMap systemConfig = (HashMap)SpringUtil.getContext().getBean("systemConfig");

            String appId = systemConfig.get("appId").toString();
            String appSecret = systemConfig.get("appSecret").toString();
            String userCenterUrl = systemConfig.get("userCenterUrl").toString();

            Map<String, String> params = new HashMap<String, String>();
            params.put("appId",appId);
            params.put("appSecret",appSecret);
            params.put("mobile", mobile);
            params.put("smsCode", smsCode);
            params.put("password",newPassword);

            JSONObject jsonObject = HttpClientUtil.post(new StringBuffer(userCenterUrl).append("/employee/password/retrieve").toString(), params);
            if (jsonObject.getInt(UserCenter_Res_ErrorCode) != 0) {
                setErrMsg(res, jsonObject.getString(UserCenter_Res_ErrorMsg));
                return res;
            }

            res.setOk(Boolean.TRUE);
            res.setMessage("密码设置成功");
            res.setCode(HTTP_SUCCESS_CODE);
        } catch (Exception e) {
            setErrMsg(res, "密码设置失败");
        }
        return res;
    }
    /**********************用户中心新接口 重置密码*******************************/

    //忘记密码(用户中心对接成功) 重置密码
    @RequestMapping(value = "/----reset-password2", method = RequestMethod.POST)
    @ResponseBody
    public Response resetPassWord2() {
        Request request = this.buildPipelineContent().getRequest();
        Response res = this.buildPipelineContent().getResponse();
        try {
            String smsCode = request.getString("code");
            String mobile = request.getString("phone");
            String newPassword = request.getString("password");
            if (StringUtil.isInvalid(newPassword)) {
                setErrMsg(res, "密码不能为空");
                return res;
            }
            if (StringUtil.isInvalid(smsCode)) {
                setErrMsg(res, "验证码不能为空");
                return res;
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
            //获取token
            JSONObject jsonObject = HttpClientUtil.post(userCenterUrl + "/employee/getTokenByClient", params);
            //判断errorCode返回类型
            if (jsonObject.getInt("errorCode") == 40002) {
                setErrMsg(res, "授权类型必须是authorization_code");
                res.setCode(40002);
                return res;
            }
            String accessToken = jsonObject.getJSONObject("data").get("accessToken").toString();
            //System.out.println("--pass"+newPassword);
            //System.out.println("-->md5pass"+MD5Util.getMD5(newPassword));
            //判断errorCode返回类型
            params.put("accessToken", accessToken);
            params.put("mobile", mobile);
            params.put("smsCode", smsCode);
            params.put("newPassword", MD5Util.getMD5(newPassword));

            JSONObject jsonObjec = HttpClientUtil.post(userCenterUrl + "/employee/resetPassword", params);
            if (jsonObjec.getInt("errorCode") == 46004) {
                setErrMsg(res, "不存在的用户");
                res.setCode(46004);
                return res;
            }

            if (jsonObjec.getInt("errorCode") == 46007) {
                setErrMsg(res, "用户不一致");
                res.setCode(46007);
                return res;
            }


            if (jsonObjec.getInt("errorCode") == 46011) {
                setErrMsg(res, "验证码验证失败");
                return res;
            }

            if (jsonObjec.getInt("errorCode") != 0) {
                setErrMsg(res, "密码设置失败");
                return res;
            }

            res.setOk(true);
            res.setMessage("密码设置成功");
            res.setCode(HTTP_SUCCESS_CODE);
        } catch (Exception e) {
            setErrMsg(res, "密码设置失败");
        }
        return res;
    }


    //员工注册设置密码 第四步(用户中心已对接) 弃用
    @RequestMapping(value = "/-----init-password", method = RequestMethod.POST)
    @ResponseBody
    public Response initPassWord() {
        Request request = this.buildPipelineContent().getRequest();
        Response res = this.buildPipelineContent().getResponse();
        try {
            String accessToken = request.getString("accessToken");
            String mobile = request.getString("phone");
            String newPassword = request.getString("password");
            if (StringUtil.isInvalid(newPassword)) {
                setErrMsg(res, "密码不能为空");
                return res;
            }
            //得到配置参数
            HttpServletRequest httpRequest = this.getRequest();
            WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(httpRequest.getSession().getServletContext());
            HashMap systemConfig = (HashMap) context.getBean("systemConfig");
            Map<String, String> params = new HashMap<String, String>();
            params.put("accessToken", accessToken);
            params.put("mobile", mobile);
            params.put("newPassword", MD5Util.getMD5(newPassword));
            String userCenterUrl = systemConfig.get("userCenterUrl").toString();
            JSONObject jsonObject = HttpClientUtil.post(userCenterUrl + "/employee/initPassword", params);
            if (jsonObject.getInt("errorCode") == 46004) {
                setErrMsg(res, "不存在的用户");
                res.setCode(46004);
                return res;
            }
            if (jsonObject.getInt("errorCode") == 46007) {
                setErrMsg(res, "用户不一致");
                res.setCode(46007);
                return res;
            }

            if (jsonObject.getInt("errorCode") != 0) {
                setErrMsg(res, "密码设置失败");
                return res;
            }
            res.setOk(true);
            res.setMessage("密码设置成功");
            res.setCode(HTTP_SUCCESS_CODE);
        } catch (Exception e) {
            setErrMsg(res, "密码设置失败");
        }
        return res;
    }

    //新接口 ad接口
    //根据原密码修改密码（用户中心对接）
    @RequestMapping(value = "/change-login-password", method = RequestMethod.POST)
    @ResponseBody
    public Response changePassWord(HttpServletRequest httpServletRequest) {
        Request request = this.buildPipelineContent().getRequest();
        Response res = this.buildPipelineContent().getResponse();
        try {

            String oldPassword = request.getString("originPassword");
            String newPassword = request.getString("newPassword");
            if (StringUtil.isInvalid(newPassword) || StringUtil.isInvalid(oldPassword)) {
                setErrMsg(res, "密码不能为空");
                return res;
            }

            //获取openid accessToken
            String token = CookieUtil.getCookieValue("_token", httpServletRequest);

            if(StringUtil.isInvalid(token)){
                token=httpServletRequest.getHeader(LanchuiConstant.Request_Header_Token);
            }

            String openId = CookieUtil.getCookieValue("_openId", httpServletRequest);
            if (StringUtil.isInvalid(openId)){
                TextSearch textSearch = new TextSearch("token");
                textSearch.setSearchValue(token);
                List<RedstarSession> sessionList = redstarCommonManager.getDataList(RedstarSession.class,textSearch);
                openId = sessionList.get(0).getOpenId();
            }


            //从cookie取不到token或openId
            if (StringUtil.isInvalid(token) || StringUtil.isInvalid(openId)) {
                setErrMsg(res, "登录超时!");
                return res;
            }



            //得到配置参数
            HashMap systemConfig = (HashMap)SpringUtil.getContext().getBean("systemConfig");

            String appId = systemConfig.get("appId").toString();
            String appSecret = systemConfig.get("appSecret").toString();


            Map<String, String> params = new HashMap<String, String>();
            params.put("oldPassword",oldPassword);
            params.put("newPassword",newPassword);
            params.put("emplid", openId);

            params.put("appId", appId);
            params.put("appSecret", appSecret);

            String userCenterUrl = systemConfig.get("userCenterUrl").toString();
            JSONObject jsonObject = HttpClientUtil.post(userCenterUrl + "/employee/password/modify", params);
            if (jsonObject.getInt(UserCenter_Res_ErrorCode) !=0) {
                setErrMsg(res, jsonObject.getString(UserCenter_Res_ErrorMsg));
                return res;
            }
            res.setOk(true);
            res.setMessage("密码修改成功");
            res.setCode(HTTP_SUCCESS_CODE);
        } catch (Exception e) {
            setErrMsg(res, "密码修改失败");
        }
        return res;
    }


    //根据原密码修改密码（用户中心对接）
    @RequestMapping(value = "/----change-login-password2", method = RequestMethod.POST)
    @ResponseBody
    public Response changePassWord2(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Request request = this.buildPipelineContent().getRequest();
        Response res = this.buildPipelineContent().getResponse();
        try {


            String oldPassword = request.getString("originPassword");
            String newPassword = request.getString("newPassword");
            if (StringUtil.isInvalid(newPassword) || StringUtil.isInvalid(oldPassword)) {
                setErrMsg(res, "密码不能为空");
                return res;
            }

            //获取openid accessToken
            String token = CookieUtil.getCookieValue("_token", httpServletRequest);

            if(StringUtil.isInvalid(token)){
                token=httpServletRequest.getHeader(LanchuiConstant.Request_Header_Token);
            }

            String openId = CookieUtil.getCookieValue("_openId", httpServletRequest);

            if (StringUtil.isInvalid(openId)){
                TextSearch textSearch = new TextSearch("token");
                textSearch.setSearchValue(token);
                List<RedstarSession> sessionList = redstarCommonManager.getDataList(RedstarSession.class,textSearch);
                openId = sessionList.get(0).getOpenId();
            }


/*
            if(StringUtil.isInvalid(openId)){
                openId=httpServletRequest.getHeader(LanchuiConstant.Request_Header_OpenId);
            }
*/

            //RedstarSession redstarSession=dispatchDriver.getRedstarSessionManager().getActivatedSessio(openid);

            //从cookie取不到token或openId
            if (StringUtil.isInvalid(token) || StringUtil.isInvalid(openId)) {
                setErrMsg(res, "登录超时!");
                return res;
            }

            //根据客户的token获取session 获取唯一session
            RedstarSession redstarSession = dispatchDriver.getRedstarSessionManager().getActivatedSession(token);

            String accessToken;

            if (redstarSession != null) {
                //判断userToken是否过期
                Date usertokenExpiredDate = redstarSession.getUserTokenExpiredDate();
                if (new Date().before(usertokenExpiredDate)) {
                    //如果没有过期
                    accessToken = redstarSession.getUserToken();
                } else {
                    //如果已经过期从新调取用户中心usertoken  刷新token
/*                    WebApplicationContext contexts = WebApplicationContextUtils.getWebApplicationContext(httpRequest.getSession().getServletContext());*/
                    //获取配置url
                    HashMap systemConfig = (HashMap) SpringUtil.getContext().getBean("systemConfig");
                    //用户中心更新accessToken
                    String refreshToken = redstarSession.getUserRefreshToken();
                    String grantType = "refresh_token";
                    String appId = systemConfig.get("appId").toString();
                    String appSecret = systemConfig.get("appSecret").toString();
                    String userCenterUrl = systemConfig.get("userCenterUrl").toString();
                    Map<String, String> param = new HashMap<String, String>();
                    param.put("appId", appId);
                    param.put("grantType", grantType);
                    param.put("appSecret", appSecret);
                    param.put("refreshToken", refreshToken);
                    JSONObject jsonObject = HttpClientUtil.post(userCenterUrl + "/employee/oauth/refreshToken", param);
                    if (jsonObject.getInt("errorCode") != 0) {
                        setErrMsg(res, "用户中心获取token失败");
                        return res;
                    }
                    //
                    accessToken = jsonObject.getJSONObject("data").get("accessToken").toString();
                    String refresh = jsonObject.getJSONObject("data").get("refreshToken").toString();

                    Long expireDate = System.currentTimeMillis() + 7200 * 1000;
                    //更新该数据
                    redstarSession.setUserToken(accessToken);
                    redstarSession.setUserRefreshToken(refresh);
                    redstarSession.setUserTokenExpiredDate(new Date(expireDate));
                    dispatchDriver.getRedstarSessionManager().updateBean(redstarSession);
                    //更新cookie时间
                    CookieUtil.setCookie("_tokenExpiredDate", String.valueOf(expireDate), 3600 * 720, "/", httpServletResponse);
                }
            } else {
                setErrMsg(res, "登录超时!");
                return res;
            }

            //得到配置参数
            HttpServletRequest httpRequest = this.getRequest();
            WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(httpRequest.getSession().getServletContext());
            HashMap systemConfig = (HashMap) context.getBean("systemConfig");
            Map<String, String> params = new HashMap<String, String>();
            params.put("accessToken", accessToken);
            params.put("oldPassword", MD5Util.getMD5(oldPassword));
            params.put("newPassword", MD5Util.getMD5(newPassword));
            params.put("openid", openId);
            String userCenterUrl = systemConfig.get("userCenterUrl").toString();
            JSONObject jsonObject = HttpClientUtil.post(userCenterUrl + "/employee/setPassword", params);
            if (jsonObject.getInt("errorCode") == 46004) {
                setErrMsg(res, "不存在的用户");
                res.setCode(46004);
                return res;
            }
            if (jsonObject.getInt("errorCode") == 46007) {
                setErrMsg(res, "用户不一致");
                res.setCode(46007);
                return res;
            }

            if (jsonObject.getInt("errorCode") != 0) {
                setErrMsg(res, "原密码错误");
                return res;
            }

            res.setOk(true);
            res.setMessage("密码修改成功");
            res.setCode(HTTP_SUCCESS_CODE);
        } catch (Exception e) {
            setErrMsg(res, "密码修改失败");
        }
        return res;
    }

    //查询用户信息
    @RequestMapping(value = "/user-info")
    @ResponseBody
    public Response getUserInfo() {
        Response res = this.buildPipelineContent().getResponse();
        try {
            //Employee employee = (Employee) session.getAttribute(SESSION_EMPLOYEE);
            Employee employee = getEmployeeromSession();
            if (employee == null) {
                setErrMsg(res, "登录超时");
                return res;
            }

            RedstarEmployee employeeObject = (RedstarEmployee) dispatchDriver.getRedstarEmployeeManager().getBean(employee.getId());

            IntSearch dataIdSearch = new IntSearch("employeeId");
            dataIdSearch.setSearchValue(String.valueOf(employeeObject.getId()));

            List<RedstarMallEmployee> dataList = redstarMallEmployeeManager.searchIdentify(dataIdSearch);
            if (CollectionUtil.isValid(dataList)) {
                RedstarMallEmployee redstarMallEmployee = dataList.get(0);
                List<RedstarShoppingMall> shoppingMalls = redstarShoppingMallManager.getBeanListByColumn(Identified.BEAN_NAME, redstarMallEmployee.getShoppingMallId());
                //员工所属商场信息
                res.addKey("shoppingMalls", shoppingMalls);
            }


            List<RedstarRole> redstarRole = dispatchDriver.getRedstarEmployeeManager().getRoleByEmployeeId(employee.getId());
            if (CollectionUtil.isValid(redstarRole)) {
                employeeObject.setRole(redstarRole.get(0).getName());
            }
            ExtCountryData countryData = ReportUtil.getAllDataBySum(redstarCommonManager, res);
            res.addKey("userInfo", employeeObject);
            res.addKey("roleInfo", redstarRole);
            res.addKey("dashboard", countryData);
        } catch (Exception e) {
            e.printStackTrace();
            setErrMsg(res, "系统异常");
        }
        return res;
    }


    //员工注册，注册前校验 第一步 校验成功或者失败 成功则第二步 弃用
    @RequestMapping(value = "/---employee-pre-check")
    @ResponseBody
    public Response employeePreCheck(HttpServletRequest request) {
        PipelineContext context = buildPipelineContent();
        Request req = context.getRequest();
        Response res = context.getResponse();
        try {
            String ip = request.getRemoteAddr();
            String mobile = req.getString("mobile");
            //校验电话号码格式
            Pattern pattern = Pattern.compile(Reg_Phone);
            Matcher matcher = pattern.matcher(mobile);
            if (StringUtil.isInvalid(mobile)) {
                throw new ManagerException("手机号码不能为空!");
            }
            if (StringUtil.isInvalid(mobile) || !matcher.matches()) {
                throw new ManagerException("手机号码格式错误,请重新输入!");
            }
            //获取配置参数
/*            HttpServletRequest httpRequest = this.getRequest();
            WebApplicationContext contexts = WebApplicationContextUtils.getWebApplicationContext(httpRequest.getSession().getServletContext());*/
            HashMap systemConfig = (HashMap) SpringUtil.getContext().getBean("systemConfig");
            //获取用户中心手机号校验地址
            String grantType = "client_credentials";
            String appId = systemConfig.get("appId").toString();
            String appSecret = systemConfig.get("appSecret").toString();
            String userCenterUrl = systemConfig.get("userCenterUrl").toString();
            Map<String, String> params = new HashMap<String, String>();
            params.put("appId", appId);
            params.put("grantType", grantType);
            params.put("appSecret", appSecret);
            JSONObject jsonObject = HttpClientUtil.post(userCenterUrl + "/employee/getTokenByClient", params);
            //判断errorCode返回类型
            if (jsonObject.getInt("errorCode") == 40002) {
                res.setCode(40002);
                setErrMsg(res, "授权类型必须是authorization_code");
                return res;
            }

            if (jsonObject.getInt("errorCode") != 0) {
                setErrMsg(res, "用户中心数据获取失败,请稍后重试");
                return res;
            }

            String accessToken = jsonObject.getJSONObject("data").get("accessToken").toString();
            //进行员工校验
            params.put("ip", ip);
            params.put("mobile", mobile);
            params.put("accessToken", accessToken);
            JSONObject jsonObjec = HttpClientUtil.post(userCenterUrl + "/employee/checkMobile", params);

            //如果校验成功发送短信
            if (jsonObjec.getInt("errorCode") != 0) {
                if (jsonObjec.getInt("errorCode") == 60001) {
                    setErrMsg(res, "非常抱歉,该手机号在系统中未找到!请联系人力资源变更手机号!");
                    res.setCode(60001);
                    return res;
                }
                if (jsonObjec.getInt("errorCode") == 46006) {
                    setErrMsg(res, "该用户已注册!");
                    res.setCode(46006);
                    return res;
                }
                if (jsonObjec.getInt("errorCode") == 60002) {
                    setErrMsg(res, "离职员工!");
                    res.setCode(60002);
                    return res;
                }
                if (jsonObjec.getInt("errorCode") == 90001) {
                    setErrMsg(res, "人力资源系统繁忙,请稍后重试");
                    return res;
                }

                if (jsonObjec.getInt("errorCode") == 90002) {
                    setErrMsg(res, "参数不合法");
                    return res;
                }

                if (jsonObjec.getInt("errorCode") == 90003) {
                    setErrMsg(res, "无访问权限,访问服务器不在可信范围内");
                    return res;
                }
                //不继续向下进行
                setErrMsg(res, "人力资源系统繁忙,请稍后重试");
                return res;
            }
            //发送短信
            String msgType = "10002";
            params.clear();
            params.put("mobiles", mobile);
            params.put("appId", appId);
            params.put("appSecret", appSecret);
            params.put("msgType", msgType);
            JSONObject jsonObje = HttpClientUtil.post(userCenterUrl + "/sendMsg", params);
            if (jsonObje.getInt("errorCode") != 0) {
                setErrMsg(res, "验证码发送失败!");
                return res;
            }
/*            if (jsonObje.getInt("errorCode") == 40002) {
                setErrMsg(res, "验证码发送失败!");
                res.setCode(40002);
                return res;
            }*/
            res.setMessage("短信发送成功!");
            res.setOk(true);
            res.setCode(HTTP_SUCCESS_CODE);
        } catch (Exception e) {
            setErrMsg(res, "人力资源系统繁忙,请稍后重试");
            return res;
        }
        return res;
    }


    /********************************发送找回密码验证码 新接口*******************************/
    @RequestMapping(value = "/employee-register-send-otp", method = RequestMethod.POST)
    @ResponseBody
    public Response employeeRegisterSendOtp(String phone) {
        Response res = this.buildPipelineContent().getResponse();
        try {
            //校验电话号码
            Pattern pattern = Pattern.compile(Reg_Phone);
            Matcher matcher = pattern.matcher(phone);
            if (StringUtil.isInvalid(phone) || !matcher.matches()) {
                throw new ManagerException("手机号码格式错误,请重新输入");
            }
            HashMap systemConfig = (HashMap) SpringUtil.getContext().getBean("systemConfig");
            String appId = systemConfig.get("appId").toString();
            String appSecret = systemConfig.get("appSecret").toString();
            String userCenterUrl = systemConfig.get("userCenterUrl").toString();

            /*******检查用户名是否存在********/
            Map<String, String> checkParams = new HashMap<String, String>();
            checkParams.put("appId", appId);
            checkParams.put("appSecret", appSecret);
            checkParams.put("username", phone);

            JSONObject mobileJson = HttpClientUtil.httpGet(new StringBuffer(userCenterUrl).append("/employee/check/username?").append("appId=").append(appId).append("&appSecret=").append(appSecret).append("&username=").append(phone).toString());

            int errorCode = mobileJson.getInt(UserCenter_Res_ErrorCode);
            if (errorCode != 0) {
               setErrMsg(res,mobileJson.getString(UserCenter_Res_ErrorMsg));
               return res;
            }

            /*******用户名存在时再发送验证码********/
            String msgType = "10003";//短信验证码模板代号
            Map<String, String> params = new HashMap<String, String>();
            params.put("mobile", phone);
            params.put("appId", appId);
            params.put("appSecret", appSecret);
            params.put("smsTemplateId", msgType);

            JSONObject smsJson = HttpClientUtil.post(new StringBuffer(userCenterUrl).append("/sms/send").toString(), params);
            if (smsJson.getInt(UserCenter_Res_ErrorCode) != 0) {
                setErrMsg(res, "验证码发送失败!");
                return res;
            }

            res.setOk(true);
            res.setMessage("验证码发送成功");
            res.setCode(HTTP_SUCCESS_CODE);
        } catch (ManagerException e) {
            setErrMsg(res, "验证码发送失败!");
            return res;
        }
        return res;
    }
    /********************************发送找回密码验证码 新接口*******************************/

    //发送找回密码验证码
    @RequestMapping(value = "/----employee-register-send-otp2", method = RequestMethod.POST)
    @ResponseBody
    public Response employeeRegisterSendOtp2(HttpServletRequest request) {
        Response res = this.buildPipelineContent().getResponse();
        PipelineContext context = buildPipelineContent();
        Request req = context.getRequest();
        String phone = context.getRequest().getString("phone");
        try {
            //校验电话号码
            Pattern pattern = Pattern.compile(Reg_Phone);
            Matcher matcher = pattern.matcher(phone);
            if (StringUtil.isInvalid(phone) || !matcher.matches()) {
                throw new ManagerException("手机号码格式错误,请重新输入");
            }

            HashMap systemConfig = (HashMap) SpringUtil.getContext().getBean("systemConfig");

            String grantType = "client_credentials";
            String appId = systemConfig.get("appId").toString();
            String appSecret = systemConfig.get("appSecret").toString();
            String userCenterUrl = systemConfig.get("userCenterUrl").toString();
            /*******chechMobile********/
            Map<String, String> checkParams = new HashMap<String, String>();
            checkParams.put("appId", appId);
            checkParams.put("grantType", grantType);
            checkParams.put("appSecret", appSecret);
            JSONObject jsonObject = HttpClientUtil.post(userCenterUrl + "/employee/getTokenByClient", checkParams);
            if (jsonObject.getInt("errorCode") == 40002) {
                res.setCode(40002);
                setErrMsg(res, "授权类型必须是authorization_code");
                return res;
            }

            if (jsonObject.getInt("errorCode") != 0) {
                setErrMsg(res, "用户中心数据获取失败,请稍后重试");
                return res;
            }
            String accessToken = jsonObject.getJSONObject("data").get("accessToken").toString();
            //进行员工校验
            checkParams.put("ip", request.getRemoteAddr());
            checkParams.put("mobile", phone);
            checkParams.put("accessToken", accessToken);
            JSONObject mobileJson = HttpClientUtil.post(userCenterUrl + "/employee/checkMobile", checkParams);
            int errorCode = mobileJson.getInt("errorCode");

            if (errorCode != 0) {
                if (errorCode == 60002) {
                    setErrMsg(res, "离职员工!");
                    res.setCode(60002);
                    return res;
                }
            }

            /***************/
            String reqFrom = req.getString("req");
            if (StringUtil.isValid(reqFrom) && "forget".equals(reqFrom)) {
                //找回密码验证该员工状态
                if (errorCode != 46006) {
                    setErrMsg(res, "该用户未注册!");
                    return res;
                }
            }


            if (errorCode != 0 && errorCode != 46006) {
                setErrMsg(res, "用户校验失败!");
                return res;
            }

            //获取配置参数
/*            HttpServletRequest httpRequest = this.getRequest();
            WebApplicationContext contexts = WebApplicationContextUtils.getWebApplicationContext(httpRequest.getSession().getServletContext());*/
            //HashMap systemConfig = (HashMap) SpringUtil.getContext().getBean("systemConfig");
            //发送验证码
            //String appId = systemConfig.get("appId").toString();
            //String appSecret = systemConfig.get("appSecret").toString();
            String msgType = "10002";
            Map<String, String> params = new HashMap<String, String>();
            params.put("mobiles", phone);
            params.put("appId", appId);
            params.put("appSecret", appSecret);
            params.put("msgType", msgType);
            //String userCenterUrl = systemConfig.get("userCenterUrl").toString();
            JSONObject smsJson = HttpClientUtil.post(userCenterUrl + "/sendMsg", params);
            if (smsJson.getInt("errorCode") != 0) {
                setErrMsg(res, "验证码发送失败!");
                res.setCode(40002);
                return res;
            }
            res.setOk(true);
            res.setMessage("验证码发送成功");
            res.setCode(HTTP_SUCCESS_CODE);
        } catch (ManagerException e) {
            setErrMsg(res, "验证码发送失败!");
            return res;
            //_error(e, context);
        }

        return res;
    }


    //员工注册，手机认证 返回token openid 用户信息 弃用
    @RequestMapping(value = "/---employee-register", method = RequestMethod.POST)
    @ResponseBody
    public Response employeeRegister() {
        PipelineContext context = buildPipelineContent();
        Request req = context.getRequest();
        Response res = context.getResponse();
        try {
            String phone = req.getString("phone");
            String code = req.getString("code");
            if (StringUtil.isInvalid(phone)) {
                setErrMsg(res, "手机号码不能为空");
                return res;
            }
            if (StringUtil.isInvalid(code)) {
                setErrMsg(res, "验证码不能为空");
                return res;
            }
            //获取配置参数
            HttpServletRequest httpRequest = this.getRequest();
            WebApplicationContext contexts = WebApplicationContextUtils.getWebApplicationContext(httpRequest.getSession().getServletContext());
            HashMap systemConfig = (HashMap) contexts.getBean("systemConfig");
            //服务端验证获取token
            String appId = systemConfig.get("appId").toString();
            String appSecret = systemConfig.get("appSecret").toString();
            String grantType = "client_credentials";
            String userCenterUrl = systemConfig.get("userCenterUrl").toString();
            Map<String, String> params = new HashMap<String, String>();
            params.put("appId", appId);
            params.put("appSecret", appSecret);
            params.put("grantType", grantType);
            JSONObject jsonObject = HttpClientUtil.post(userCenterUrl + "/employee/getTokenByClient", params);
            if (jsonObject.getInt("errorCode") == 40002) {
                setErrMsg(res, "获取token失败");
                res.setCode(40002);
                return res;
            }
            //获取token
            String accessToken = jsonObject.getJSONObject("data").get("accessToken").toString();
            //进行用户中心手机号认证
            params.put("accessToken", accessToken);
            params.put("mobile", phone);
            params.put("smsCode", code);
            JSONObject jsonObjec = HttpClientUtil.post(userCenterUrl + "/employee/validateMobile", params);
            if (jsonObjec.getInt("errorCode") == 46006) {
                setErrMsg(res, "手机号已经注册");
                return res;
            }
            if (jsonObjec.getInt("errorCode") == 60001) {
                setErrMsg(res, "用户不是我们员工");
                return res;
            }
            if (jsonObjec.getInt("errorCode") != 0) {
                //setErrMsg(res, jsonObjec.getString("errorMsg"));
                setErrMsg(res, jsonObjec.getString("errorMsg"));
                return res;
            }
            params.remove("accessToken");


/*            //获取自己的信息
            String accessToke = jsonObjec.getJSONObject("data").get("accessToken").toString();
            String openid = jsonObjec.getJSONObject("data").get("openid").toString();*/

            JSONObject _jsonObject = jsonObjec.getJSONObject("data").getJSONObject("accessToken");

            String accessToke = _jsonObject.get("accessToken").toString();
            String openid = _jsonObject.get("openid").toString();

            params.put("accessToken", accessToke);
            params.put("openid", openid);
            JSONObject jsonObj = HttpClientUtil.post(userCenterUrl + "/employee/me", params);
            if (jsonObj.getInt("errorCode") != 0) {
                setErrMsg(res, "信息获取失败,请重试");
                return res;
            }
            //System.out.println("return me--> jsonObj:"+jsonObj);
            Map<String, Object> data = jsonObj.getJSONObject("data");

            String name = data.get("name").toString();
            String emplid = data.get("emplId").toString();
            String sex = data.get("sex").toString();
            String hr_status = data.get("hrStatus").toString();
            String moblie = data.get("mobile").toString();
            Integer market_id = 0;
            try {
                market_id = Integer.parseInt(data.get("market_id").toString());//商场id
            } catch (Exception e) {

            }
            String deptid = "";
            try {
                deptid = data.get("deptid").toString();//部门id
            } catch (Exception e) {

            }
            String dept_descr = data.get("dept_descr").toString();
            String market_descr = data.get("market_descr").toString();//商场
            //查询员工是否存在
            RedstarEmployee redstarEmployee = dispatchDriver.getRedstarEmployeeManager().getEmployeeByCode(openid);
            //如果不存在添加员工信息
            if (redstarEmployee == null) {
                //添加员工信息
                redstarEmployee = new RedstarEmployee();
                redstarEmployee.setEmployeeCode(emplid);
                redstarEmployee.setBelongedId(10944);
                redstarEmployee.setShowData(true);
                redstarEmployee.setXingMing(name);
                if (sex.equals("男")) {
                    redstarEmployee.setGender("male");
                }
                if (sex.equals("女")) {
                    redstarEmployee.setGender("female");
                }
                redstarEmployee.setHrStatus(hr_status);
                redstarEmployee.setOpenId(openid);
                if (StringUtil.isValid(deptid)) {
                    List<RedstarDepartment> departments = dispatchDriver.getRedstarDepartmentManager().getBeanListByColumn("departmentCode", deptid);
                    if (CollectionUtil.isValid(departments)) {
                        RedstarDepartment department = departments.get(0);
                        redstarEmployee.setDepartmentId(department.getId());
                    } else {
                        RedstarDepartment department = new RedstarDepartment();
                        department.setDepartmentCode(deptid);
                        if (StringUtil.isValid(dept_descr)) {
                            department.setName(dept_descr);
                        }
                        department.setBelongedId(10944);
                        department.setCreateDate(new Date(System.currentTimeMillis()));
                        department.setParentId(-1);
                        department.setPinYin("");
                        department.setShowData(true);
                        int departmentId = dispatchDriver.getRedstarDepartmentManager().addBean(department);
                        redstarEmployee.setDepartmentId(departmentId);
                    }
                }
                Integer objectId = dispatchDriver.getRedstarEmployeeManager().addBean(redstarEmployee);
                //添加员工商场表
                RedstarMallEmployee redstarMallEmployee = new RedstarMallEmployee();
                redstarMallEmployee.setEmployeeId(objectId);
                redstarMallEmployee.setEmployeeXingMing(name);
                redstarMallEmployee.setShoppingMallId(market_id);
                redstarMallEmployee.setShoppingMallName(market_descr);
                if (objectId == 0 || objectId == null) {
                    setErrMsg(res, "新增员工表失败");
                    res.setCode(10001);
                    return res;
                }
                Integer emid = dispatchDriver.getRedstarMallEmployeeManager().addBean(redstarMallEmployee);
                if (emid == 0 || emid == null) {
                    setErrMsg(res, "新增员工商场表失败");
                    res.setCode(10002);
                    return res;
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
                dispatchDriver.getRedstarEmployeeManager().addAuthorized(authorized);
            } else {
                Authorized authorized = this.securityDriver.getAuthorizedManager().getAuthorized(10944, redstarEmployee.getId(), "employee");
                //如果员工授权表中没有数据则添加
                if (authorized == null) {
                    String roles = systemConfig.get("roles").toString();//需要在config.properties里配置
                    SimpleAuthorized authoriz = new SimpleAuthorized();
                    authoriz.setBelongedId(10944);
                    authoriz.setObjectId(redstarEmployee.getId());
                    authoriz.setObjectIdentified("employee");
                    authoriz.setObjectDesc("员工注册时新增");
                    authoriz.setAccount(moblie);
                    authoriz.setActive(true);
                    authoriz.setRoles(roles);
                    authoriz.setPassword("");
                    authoriz.setHaveSetPassword(true);
                    //添加员工授权表
                    dispatchDriver.getRedstarEmployeeManager().addAuthorized(authoriz);
                }
            }
            RedstarEmployee redstarEmployee1 = new RedstarEmployee();
            redstarEmployee1.setUserName(name);
            redstarEmployee1.setMobilePhone(moblie);
            redstarEmployee1.setOpenId(openid);
            redstarEmployee1.setGender(sex);
            redstarEmployee1.setDepartmentCode(dept_descr);
            redstarEmployee1.setBusinessUnitCode(market_id.toString());
            redstarEmployee1.setBusinessUnitName(market_descr);
            redstarEmployee1.setBelongedId(10944);
            redstarEmployee1.setShowData(true);
            res.setOk(true);
            res.setMessage("信息获取成功");
            res.addKey("accessToken", accessToke);
            //System.out.print(accessToke+"-------------------------------------------------------");
            res.addKey("employee", redstarEmployee1);
            res.setCode(HTTP_SUCCESS_CODE);
        } catch (Exception e) {
            e.printStackTrace();
            setErrMsg(res, "服务器响应异常");
        }
        return res;
    }


    //会员注册，发送验证码 弃用
    @RequestMapping(value = "/---user-register-send-otp")
    @ResponseBody
    public Response userRegisterSendOtp() {
        PipelineContext context = buildPipelineContent();
        String phone = context.getRequest().getString("phone");
        try {
            //校验电话号码
            Pattern pattern = Pattern.compile(Reg_Phone);
            Matcher matcher = pattern.matcher(phone);
            if (StringUtil.isInvalid(phone) || !matcher.matches()) {
                throw new ManagerException("输入的手机号码无效，请重新输入");
            }
            //查询该员工是否存在

/*            TextSearch textSearch = new TextSearch("mobilePhone");
            textSearch.setSearchValue(phone);
            List<RedstarEmployee> dataList  = dispatchDriver.getRedstarEmployeeManager().searchIdentify(textSearch);*/

            RedstarEmployee redstarEmployee = dispatchDriver.getRedstarEmployeeManager().getEmployeeByPhone(phone);

            //当前查询的用户集合不为空 会员已注册
            if (redstarEmployee != null) {
                throw new ManagerException("该手机号已注册,请更换手机号");
            }

/*          if (dispatchDriver.getRedstarEmployeeManager().isRegister(phone)) {
                //已经注册过
                throw new ManagerException("该手机号已注册,请更换手机号");
            }*/
            //发送短信流程

            //插入短信验证码表
            RedstarVerifyCode verifyCode = new RedstarVerifyCode();
            verifyCode.setCreateDate(new Date(System.currentTimeMillis()));
            verifyCode.setPhone(phone);
            verifyCode.setCode(CodeUtil.getVerifyCode());
            dispatchDriver.getRedstarVerifyCodeManager().addBean(verifyCode);
        } catch (ManagerException e) {
            _error(e, context);
        }
        return context.getResponse();
    }


    //会员注册，确认注册 弃用
    @RequestMapping(value = "/----user-register")
    @ResponseBody
    public Response userRegister() {
        PipelineContext context = buildPipelineContent();
        Request req = context.getRequest();
        Response res = context.getResponse();

        try {
            String phone = req.getString("phone");
            String code = req.getString("code");
            String passWord = req.getString("password");
            if (StringUtil.isInvalid(phone)) {
                setErrMsg(res, "手机号码不能为空");
                return res;
            }
            if (StringUtil.isInvalid(code)) {
                setErrMsg(res, "验证码不能为空");
                return res;
            }
            if (StringUtil.isInvalid(passWord)) {
                setErrMsg(res, "密码不能为空");
                return res;
            }
            // 查询code是否正确
            MultiSearchBean codeSearchBean = new MultiSearchBean();
            TextSearch phoneSearch = new TextSearch("phone");
            phoneSearch.setSearchValue(phone);

            TextSearch codeSearch = new TextSearch("code");
            codeSearch.setSearchValue(code);

            BooleanSearch expiredSearch = new BooleanSearch("expired");
            expiredSearch.setValue(Boolean.FALSE.toString());

            codeSearchBean.addSearchBean(phoneSearch);
            codeSearchBean.addSearchBean(codeSearch);
            codeSearchBean.addSearchBean(expiredSearch);

            List<RedstarVerifyCode> codeList = dispatchDriver.getRedstarVerifyCodeManager().searchIdentify(codeSearchBean);

            //根据code找不到 验证码验证失败
            if (CollectionUtils.isEmpty(codeList)) {
                throw new ManagerException("验证码填写错误");
            }


            //List<RedstarEmployee> dataList  = dispatchDriver.getRedstarEmployeeManager().searchIdentify(textSearch);

            RedstarEmployee redstarEmployee = dispatchDriver.getRedstarEmployeeManager().getEmployeeByPhone(phone);


            //当前查询的用户集合不为空 会员已注册
            if (redstarEmployee != null) {
                throw new ManagerException("该手机号已注册,请更换手机号");
            }

            RedstarEmployee employee = new RedstarEmployee();
            employee.setMobilePhone(phone);
            employee.setShowData(Boolean.TRUE);
            employee.setCreateDate(new Date());
            employee.setBelongedId(LOG_BELONG_ID);
            employee.setSource("register");
            Integer dataId = dispatchDriver.getRedstarEmployeeManager().addBean(employee);

            //增加authrize记录
            SimpleAuthorized simpleAuthorized = new SimpleAuthorized();
            simpleAuthorized.setAccount(phone);
            simpleAuthorized.setPassword(passWord);
            simpleAuthorized.setBelongedId(LOG_BELONG_ID);
            simpleAuthorized.setRoles("1833");
            simpleAuthorized.setObjectId(dataId);
            simpleAuthorized.setActive(Boolean.TRUE);
            simpleAuthorized.setObjectIdentified(Emp_Identify_Str);
            simpleAuthorized.setHaveSetPassword(Boolean.TRUE);
            dispatchDriver.getRedstarEmployeeManager().addAuthorized(simpleAuthorized);
            RedstarVerifyCode verifyCode = codeList.get(0);
            verifyCode.setExpired(Boolean.TRUE);
            dispatchDriver.getRedstarVerifyCodeManager().updateBean(verifyCode);
        } catch (Exception e) {
            e.printStackTrace();
            setErrMsg(res, "服务器响应错误");
        }

        return res;
    }


    //用户信息，设置头像
    @RequestMapping(value = "/---setting-avatar", method = RequestMethod.POST)
    @ResponseBody
    public Response settingAvatar(@RequestParam("file") MultipartFile file) {
        PipelineContext context = buildPipelineContent();
        Response res = context.getResponse();
        try {
            if (file == null) {
                setErrMsg(res, "上传图片为空");
                return res;
            }

            File f = new File(storageConfig.getStorage_config_store_path().toString());
            if (!f.exists()) {
                f.mkdirs();
            }

            String imgSuffix = UploadFileUtil.getImgSuffix(file);
            String fileName = RandomGUIDUtil.getRawGUID() + imgSuffix;
            file.transferTo(new File(storageConfig.getStorage_config_store_path(), fileName));


            Employee employee = getEmployeeromSession();

            RedstarEmployee redstarEmployee = (RedstarEmployee) dispatchDriver.getRedstarEmployeeManager().getBean(employee.getId());

            redstarEmployee.setPhotos(fileName);
            redstarEmployee.setHead(fileName);
            redstarMallEmployeeManager.updateBean(redstarEmployee);

            res.setCode(HTTP_SUCCESS_CODE);
            res.setMessage("上传成功");
        } catch (Exception e) {
            e.printStackTrace();
            setErrMsg(res, "服务器响应异常,请稍后重试");
        }
        return res;
    }

    /**
     * APP用户信息
     * @return
     */
    @RequestMapping(value = "/user-info-app")
    @ResponseBody
    public Response userInfoApp() {
        PipelineContext context = buildPipelineContent();
        Response res = context.getResponse();
        try {
            Employee employee = getEmployeeromSession(); //从session中获取登陆员工的ID
            RedstarEmployee redstarEmployee = (RedstarEmployee) dispatchDriver.getRedstarEmployeeManager().getBean(employee.getId());//从 xiwa_crm_employee 获取员工信息
            //从 xiwa_redstar_mall_employee 获取商场所属员工信息
            List<RedstarMallEmployee> redstarMallEmployees = dispatchDriver.getRedstarMallEmployeeManager().getBeanListByColumn("employeeId", redstarEmployee.getId());
            RedstarMallEmployee redstarMallEmployee;

            res.addKey("id", redstarEmployee.getId());
            res.addKey("name", redstarEmployee.getXingMing());
            res.addKey("employeeCode", redstarEmployee.getEmployeeCode());
            res.addKey("gender", redstarEmployee.getGender());
            int mallOrganizationId = 0;
            String mallOrganizationName = "";
            if (redstarMallEmployees.size() > 0) {
                redstarMallEmployee = redstarMallEmployees.get(0);
                res.addKey("mallId", redstarMallEmployee.getShoppingMallId());
                res.addKey("mallName", redstarMallEmployee.getShoppingMallName());
                Object[] mallOrg = dispatchDriver.getRedstarShopMallOrganizationManager().getParentId(redstarMallEmployee.getShoppingMallId());

                if (mallOrg.length == 2) {
                    mallOrganizationId = DataUtil.getInt(mallOrg[0], 0);
                    mallOrganizationName = DataUtil.getString(mallOrg[1], "");
                }
            }
            res.addKey("mallOrganizationId", mallOrganizationId);
            res.addKey("mallOrganizationName", mallOrganizationName);


            /**
             * 根据登陆员工的ID，去授权表(xiwa_security_authorized objectIdentified=employee，objectId=登陆员工ID)
             如果查不出就抛出异常
             如果查出取第一个元素
             查询 roles 为条件查询角色表(xiwa_security_role ,id in roles)
             */
            MultiSearchBean searchBean = new MultiSearchBean();
            TextSearch objectIdentified = new TextSearch("objectIdentified");
            objectIdentified.setSearchValue("employee");
            IntSearch objectId = new IntSearch("objectId");
            objectId.setSearchValue(String.valueOf(employee.getId()));
            searchBean.addSearchBean(objectIdentified);
            searchBean.addSearchBean(objectId);

            List<SecurityAuthorized> authorizeds = securityAuthorizedManager.searchIdentify(searchBean);
            if (authorizeds.size() == 0) {
                setErrMsg(res, "未查到授权信息");
                res.setCode(500);
                return res;
            }
            SecurityAuthorized securityAuthorized = authorizeds.get(0);
            int count = securityAuthorizedManager.getAuthorizeCount(securityAuthorized.getRoles(), "role_hx_25f");
            int attendanceStatus = 0;
            int restaurantStatus = 0;
            if (count > 0) {
                attendanceStatus = 1;
                restaurantStatus = 1;
            }
            res.addKey("attendanceStatus", attendanceStatus);
            res.addKey("restaurantStatus", restaurantStatus);
            /**
             * 根据登陆员工ID，去小区表 xiwa_redstar_community 查询 ownerId = 当前员工ID or createEmployeeId = 当前员工ID ，createDate = 昨日0点-昨日24点，返回条目数到字段 yesterdayCommunityCount
             根据登陆员工ID，去小区表 xiwa_redstar_community 查询 ownerId = 当前员工ID or createEmployeeId = 当前员工ID ，createDate = 本月1日0点-本月最后一日24点，返回条目数到字段 currMonthCommunityCount
             根据登陆员工ID，去小区表 xiwa_redstar_community 查询 ownerId = 当前员工ID or createEmployeeId = 当前员工ID ，返回条目数到字段 totalCommunityCount
             */
            DateTime today = DateTime.now();
            DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:SS");
            today = DateTime.parse(today.toString("yyyy-MM-dd") + " 00:00:00", format);
            DateTime yesterday = today.minusDays(1);
            String date = today.getYear() + "-" + today.getMonthOfYear() + "-01 00:00:00";
            DateTime nowMonth = DateTime.parse(date, format);
            DateTime nextMonth = nowMonth.plusMonths(1);
            int yesterdayCommunityCount = dispatchDriver.getRedstarCommunityManager().getCountByOwnerIdAndDate(employee.getId(), yesterday.toString("yyyy-MM-dd HH:mm:SS"), today.toString("yyyy-MM-dd HH:mm:SS"));
            int currMonthCommunityCount = dispatchDriver.getRedstarCommunityManager().getCountByOwnerIdAndDate(employee.getId(), nowMonth.toString("yyyy-MM-dd HH:mm:SS"), nextMonth.toString("yyyy-MM-dd HH:mm:SS"));
            int totalCommunityCount = dispatchDriver.getRedstarCommunityManager().getCountByOwnerIdAndDate(employee.getId(), null, null);
            res.addKey("yesterdayCommunityCount", yesterdayCommunityCount);
            res.addKey("currMonthCommunityCount", currMonthCommunityCount);
            res.addKey("totalCommunityCount", totalCommunityCount);
            setSuccessMsg(res,"成功");
        } catch (ManagerException e) {
            e.printStackTrace();
            setErrMsg(res,"获取用户信息异常");
            res.setCode(500);
        }
        return res;
    }

}

