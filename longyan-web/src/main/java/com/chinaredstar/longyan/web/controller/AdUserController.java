package com.chinaredstar.longyan.web.controller;

import com.chinaredstar.commonBiz.bean.*;
import com.chinaredstar.commonBiz.manager.*;
import com.chinaredstar.lang.fastdfs.api.IFastdfsService;
import com.chinaredstar.longyan.bean.constant.LanchuiConstant;
import com.chinaredstar.longyan.exception.BusinessException;
import com.chinaredstar.longyan.util.HttpClientUtil;
import com.chinaredstar.longyan.util.ReportUtil;
import com.chinaredstar.longyan.util.SpringUtil;
import com.chinaredstar.longyan.util.UploadFileUtil;
import com.chinaredstar.commonBiz.bean.constant.CommonBizConstant;
import com.chinaredstar.commonBiz.util.CookieUtil;
import com.chinaredstar.nvwaBiz.bean.NvwaEmployee;
import com.chinaredstar.nvwaBiz.bean.NvwaRole;
import com.chinaredstar.nvwaBiz.manager.NvwaDriver;
import com.chinaredstar.nvwaBiz.manager.NvwaSecurityAuthorizedManager;
import com.chinaredstar.nvwaBiz.util.EmployeeUtil;
import com.xiwa.base.bean.Identified;
import com.xiwa.base.bean.Request;
import com.xiwa.base.bean.Response;
import com.xiwa.base.bean.search.ext.IntSearch;
import com.xiwa.base.bean.search.ext.TextSearch;
import com.xiwa.base.manager.ManagerException;
import com.xiwa.base.pipeline.PipelineContext;
import com.xiwa.base.util.CollectionUtil;
import com.xiwa.base.util.DataUtil;
import com.xiwa.base.util.StringUtil;
import com.xiwa.zeus.trinity.bean.Employee;
import net.sf.json.JSONObject;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by niu on 2016/8/8.
 */
@Controller
@RequestMapping(value = "/user")
public class AdUserController extends BaseController implements CommonBizConstant {

    @Autowired
    private DispatchDriver dispatchDriver;

//    @Autowired
//    private RedstarShoppingMallManager redstarShoppingMallManager;

    @Autowired
    private RedstarMallEmployeeManager redstarMallEmployeeManager;

    @Autowired
    private NvwaDriver nvwaDriver;

//    @Autowired
//    private RedstarCommonManager redstarCommonManager;


    @Autowired
    private IFastdfsService iFastdfsService;

    @Autowired
    private HashMap systemConfig;


    /********************************发送找回密码验证码*******************************/
//    @RequestMapping(value = "/employee-register-send-otp", method = RequestMethod.POST)
//    @ResponseBody
//    public Response employeeRegisterSendOtp(String phone) {
//        Response res = this.buildPipelineContent().getResponse();
//        try {
//            //校验电话号码
//            Pattern pattern = Pattern.compile(Reg_Phone);
//            Matcher matcher = pattern.matcher(phone);
//            if (StringUtil.isInvalid(phone) || !matcher.matches()) {
//                throw new ManagerException("手机号码格式错误,请重新输入");
//            }
//            HashMap systemConfig = (HashMap) SpringUtil.getContext().getBean("systemConfig");
//            String appId = systemConfig.get("appId").toString();
//            String appSecret = systemConfig.get("appSecret").toString();
//            String userCenterUrl = systemConfig.get("userCenterUrl").toString();
//
//            /*******检查用户名是否存在********/
//            Map<String, String> checkParams = new HashMap<String, String>();
//            checkParams.put("appId", appId);
//            checkParams.put("appSecret", appSecret);
//            checkParams.put("username", phone);
//
//            JSONObject mobileJson = HttpClientUtil.httpGet(new StringBuffer(userCenterUrl).append("/employee/check/username?").append("appId=").append(appId).append("&appSecret=").append(appSecret).append("&username=").append(phone).toString());
//
//            int errorCode = mobileJson.getInt(UserCenter_Res_ErrorCode);
//            if (errorCode != 0) {
//                setErrMsg(res,mobileJson.getString(UserCenter_Res_ErrorMsg));
//                return res;
//            }
//
//            /*******用户名存在时再发送验证码********/
//            String msgType = "10003";//短信验证码模板代号
//            Map<String, String> params = new HashMap<String, String>();
//            params.put("mobile", phone);
//            params.put("appId", appId);
//            params.put("appSecret", appSecret);
//            params.put("smsTemplateId", msgType);
//
//            JSONObject smsJson = HttpClientUtil.post(new StringBuffer(userCenterUrl).append("/sms/send").toString(), params);
//            if (smsJson.getInt(UserCenter_Res_ErrorCode) != 0) {
//                setErrMsg(res, "验证码发送失败!");
//                return res;
//            }
//
//            res.setOk(true);
//            res.setMessage("验证码发送成功");
//            res.setCode(HTTP_SUCCESS_CODE);
//        } catch (ManagerException e) {
//            setErrMsg(res, "验证码发送失败!");
//            return res;
//        }
//        return res;
//    }
    /********************************发送找回密码验证码*******************************/


    /**********************重置密码*******************************/
//    @RequestMapping(value = "/reset-password", method = RequestMethod.POST)
//    @ResponseBody
//    public Response resetPassWord() {
//        Request request = this.buildPipelineContent().getRequest();
//        Response res = this.buildPipelineContent().getResponse();
//        try {
//            String smsCode = request.getString("code");
//            String mobile = request.getString("phone");
//            String newPassword = request.getString("password");
//            if (StringUtil.isInvalid(mobile)) {
//                setErrMsg(res, "手机号不能为空");
//                return res;
//            }
//            if (StringUtil.isInvalid(newPassword)) {
//                setErrMsg(res, "密码不能为空");
//                return res;
//            }
//            if (StringUtil.isInvalid(smsCode)) {
//                setErrMsg(res, "验证码不能为空");
//                return res;
//            }
//            HashMap systemConfig = (HashMap) SpringUtil.getContext().getBean("systemConfig");
//
//            String appId = systemConfig.get("appId").toString();
//            String appSecret = systemConfig.get("appSecret").toString();
//            String userCenterUrl = systemConfig.get("userCenterUrl").toString();
//
//            Map<String, String> params = new HashMap<String, String>();
//            params.put("appId",appId);
//            params.put("appSecret",appSecret);
//            params.put("mobile", mobile);
//            params.put("smsCode", smsCode);
//            params.put("password",newPassword);
//
//            JSONObject jsonObject = HttpClientUtil.post(new StringBuffer(userCenterUrl).append("/employee/password/retrieve").toString(), params);
//            if (jsonObject.getInt(UserCenter_Res_ErrorCode) != 0) {
//                setErrMsg(res, jsonObject.getString(UserCenter_Res_ErrorMsg));
//                return res;
//            }
//
//            res.setOk(Boolean.TRUE);
//            res.setMessage("密码设置成功");
//            res.setCode(HTTP_SUCCESS_CODE);
//        } catch (Exception e) {
//            setErrMsg(res, "密码设置失败");
//        }
//        return res;
//    }
    /**********************重置密码*******************************/


//    //根据原密码修改密码
//    @RequestMapping(value = "/change-login-password", method = RequestMethod.POST)
//    @ResponseBody
//    public Response changePassWord(HttpServletRequest httpServletRequest) {
//        Request request = this.buildPipelineContent().getRequest();
//        Response res = this.buildPipelineContent().getResponse();
//        try {
//
//            String oldPassword = request.getString("originPassword");
//            String newPassword = request.getString("newPassword");
//            if (StringUtil.isInvalid(newPassword) || StringUtil.isInvalid(oldPassword)) {
//                setErrMsg(res, "密码不能为空");
//                return res;
//            }
//
//            //获取openid accessToken
//            String token = CookieUtil.getCookieValue("_token", httpServletRequest);
//
//            if(StringUtil.isInvalid(token)){
//                token=httpServletRequest.getHeader(LanchuiConstant.Request_Header_Token);
//            }
//
//            String openId = CookieUtil.getCookieValue("_openId", httpServletRequest);
//            if (StringUtil.isInvalid(openId)){
//                TextSearch textSearch = new TextSearch("token");
//                textSearch.setSearchValue(token);
//                List<RedstarSession> sessionList = redstarCommonManager.getDataList(RedstarSession.class,textSearch);
//                openId = sessionList.get(0).getOpenId();
//            }
//
//
//            //从cookie取不到token或openId
//            if (StringUtil.isInvalid(token) || StringUtil.isInvalid(openId)) {
//                setErrMsg(res, "登录超时!");
//                return res;
//            }
//
//
//
//            //得到配置参数
//            HashMap systemConfig = (HashMap)SpringUtil.getContext().getBean("systemConfig");
//
//            String appId = systemConfig.get("appId").toString();
//            String appSecret = systemConfig.get("appSecret").toString();
//
//
//            Map<String, String> params = new HashMap<String, String>();
//            params.put("oldPassword",oldPassword);
//            params.put("newPassword",newPassword);
//            params.put("emplid", openId);
//
//            params.put("appId", appId);
//            params.put("appSecret", appSecret);
//
//            String userCenterUrl = systemConfig.get("userCenterUrl").toString();
//            JSONObject jsonObject = HttpClientUtil.post(userCenterUrl + "/employee/password/modify", params);
//            if (jsonObject.getInt(UserCenter_Res_ErrorCode) !=0) {
//                setErrMsg(res, jsonObject.getString(UserCenter_Res_ErrorMsg));
//                return res;
//            }
//            res.setOk(true);
//            res.setMessage("密码修改成功");
//            res.setCode(HTTP_SUCCESS_CODE);
//        } catch (Exception e) {
//            setErrMsg(res, "密码修改失败");
//        }
//        return res;
//    }


    /**
     * APP用户信息
     *
     * @return
     */
    @RequestMapping(value = "/user-info-app")
    @ResponseBody
    public Response userInfoApp() {
        PipelineContext context = buildPipelineContent();
        Response res = context.getResponse();
        try {
            NvwaEmployee employee = getEmployeeromSession(); //从session中获取登陆员工的ID
//            NvwaEmployee redstarEmployee = (NvwaEmployee) nvwaDriver.getNvwaEmployeeManager().getBean(employee.getId());//从 xiwa_crm_employee 获取员工信息
            //从 xiwa_redstar_mall_employee 获取商场所属员工信息
            List<RedstarMallEmployee> redstarMallEmployees = dispatchDriver.getRedstarMallEmployeeManager().getBeanListByColumn("employeeId", employee.getId());
            RedstarMallEmployee redstarMallEmployee;

            res.addKey("id", employee.getId());
            res.addKey("name", employee.getXingMing());
            res.addKey("employeeCode", employee.getEmployeeCode());
            res.addKey("gender", employee.getGender());
            int mallOrganizationId = 0;
            String mallOrganizationName = "";
            //if (redstarMallEmployees.size() > 0) {
            if (!CollectionUtils.isEmpty(redstarMallEmployees)) {
                redstarMallEmployee = redstarMallEmployees.get(0);
                res.addKey("mallId", redstarMallEmployee.getShoppingMallId());
                res.addKey("mallName", redstarMallEmployee.getShoppingMallName());
                Object[] mallOrg = dispatchDriver.getRedstarShopMallOrganizationManager().getParentId(redstarMallEmployee.getShoppingMallId());

                if (mallOrg != null && mallOrg.length == 2) {
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
//            MultiSearchBean searchBean = new MultiSearchBean();
//            TextSearch objectIdentified = new TextSearch("objectIdentified");
//            objectIdentified.setSearchValue("employee");
//            IntSearch objectId = new IntSearch("objectId");
//            objectId.setSearchValue(String.valueOf(employee.getId()));
//            searchBean.addSearchBean(objectIdentified);
//            searchBean.addSearchBean(objectId);
//
//            List<SecurityAuthorized> authorizeds = securityAuthorizedManager.searchIdentify(searchBean);
//            //if (authorizeds.size() == 0) {
//            if (CollectionUtils.isEmpty(authorizeds)) {
//                setErrMsg(res, "未查到授权信息");
//                res.setCode(500);
//                return res;
//            }
//            SecurityAuthorized securityAuthorized = authorizeds.get(0);
//            int count = securityAuthorizedManager.getAuthorizeCount(securityAuthorized.getRoles(), "role_hx_25f");
//            int attendanceStatus = 0;
//            int restaurantStatus = 0;
//            if (count > 0) {
//            }
            res.addKey("attendanceStatus", 0);
            res.addKey("restaurantStatus", 0);
            res.addKey("lotteryStatus", 1);
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
            setSuccessMsg(res, "成功");
        } catch (Exception e) {
            e.printStackTrace();
            setErrMsg(res, "获取用户信息异常");
            res.setCode(500);
            logger.error("user--info--app--------->" + e.getMessage());
            logger.info("user--info--app--------->" + e.getMessage());
        }
        return res;
    }

    //查询用户信息
    @RequestMapping(value = "/user-info")
    @ResponseBody
    public Response getUserInfo(HttpServletRequest request) {

        Response res = this.buildPipelineContent().getResponse();
        try {
            //Employee employee = (Employee) session.getAttribute(SESSION_EMPLOYEE);
            NvwaEmployee employee = getEmployeeromSession();
            if (employee == null) {
                setErrMsg(res, "登录超时");
                return res;
            }

//            NvwaEmployee employeeObject = (NvwaEmployee) nvwaDriver.getNvwaEmployeeManager().getBean(employee.getId());

//            IntSearch dataIdSearch = new IntSearch("employeeId");
//            dataIdSearch.setSearchValue(String.valueOf(employee.getId()));

//            List<RedstarMallEmployee> dataList = redstarMallEmployeeManager.searchIdentify(dataIdSearch);
//            if (CollectionUtil.isValid(dataList)) {
//                RedstarMallEmployee redstarMallEmployee = dataList.get(0);
//                List<RedstarShoppingMall> shoppingMalls = redstarShoppingMallManager.getBeanListByColumn(Identified.BEAN_NAME, redstarMallEmployee.getShoppingMallId());
//                //员工所属商场信息
//                res.addKey("shoppingMalls", shoppingMalls);
//            }


//            List<NvwaRole> redstarRole = nvwaDriver.getNvwaEmployeeManager().getRoleByEmployeeId(employee.getId());
//            if (CollectionUtil.isValid(redstarRole)) {
//                employee.setRole(redstarRole.get(0).getName());
//            }
//            ExtCountryData countryData = ReportUtil.getAllDataBySum(redstarCommonManager, res);
            RedstarShoppingMall mall = EmployeeUtil.getMall(employee, dispatchDriver, nvwaDriver);
            res.addKey("userInfo", employee);
            res.addKey("mall", mall);
//            res.addKey("roleInfo", redstarRole);
//            res.addKey("dashboard", countryData);
        } catch (Exception e) {
            e.printStackTrace();
            setErrMsg(res, "系统异常");
        }
        return res;
    }


    @RequestMapping(value = "setting-avatar", method = RequestMethod.POST)
    @ResponseBody
    public Response settingAvatar(@RequestParam("file") MultipartFile file) {
        PipelineContext context = buildPipelineContent();
        Response res = context.getResponse();
        try {

            if (file == null) {
                setErrMsg(res, "上传图片为空");
                return res;
            }

            String fileUrl = iFastdfsService.upload(file.getBytes(), UploadFileUtil.getImgSuffix(file).replace(".", ""));

            NvwaEmployee employee = getEmployeeromSession();

//            NvwaEmployee redstarEmployee = (NvwaEmployee) nvwaDriver.getNvwaEmployeeManager().getBean(employee.getId());

            employee.setPhotos(fileUrl);
            employee.setHead(fileUrl);
            redstarMallEmployeeManager.updateBean(employee);

            res.addKey("headUrl", fileUrl);
            res.setCode(HTTP_SUCCESS_CODE);
            res.setMessage("上传成功");
        } catch (Exception e) {
            e.printStackTrace();
            setErrMsg(res, "服务器响应异常,请稍后重试");
        }
        return res;
    }


    @RequestMapping(value = "avatar", method = RequestMethod.POST)
    @ResponseBody
    public Response avatar() {
        PipelineContext context = buildPipelineContent();
        Response res = context.getResponse();
        try {

            int employeeId = context.getRequest().getInt("id", 0);

            NvwaEmployee employee = getEmployeeromSession();
            //获取员工信息
            NvwaEmployee redstarEmployee = (NvwaEmployee) nvwaDriver.getNvwaEmployeeManager().getEmployeeById(employeeId);
            if (redstarEmployee == null) {
                throw new BusinessException("找不到员工");
            }
            String employeeCode = redstarEmployee.getEmployeeCode();
            //调用ps系统的头像接口
            String psServiceUrl = StringUtil.getString(systemConfig.get("psServiceUrl"));
            String psResponse = HttpClientUtil.sendPostBody(psServiceUrl + "/EmployeePhoto", "{\"EmployeeId\":\"" + employeeCode + "\",\"IP\":\"127.0.0.1\"}");
            if (StringUtil.isInvalid(psResponse)) {
                throw new BusinessException("PS接口调用失败");
            }
            JSONObject jsonObject = JSONObject.fromObject(psResponse);

            res.addKey("employeeCode", employeeCode);
            if (jsonObject.containsKey("photo")) {
                res.addKey("photo", jsonObject.getString("photo"));
            } else {
                throw new BusinessException("无图片信息");
            }
        } catch (Exception e) {
            e.printStackTrace();
            setErrMsg(res, "服务器响应异常,请稍后重试");
        }
        return res;
    }

}
