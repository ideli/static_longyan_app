package com.chinaredstar.commonBiz.bean.constant;

/**
 * Created by bingcheng on 2015/3/23.
 */
public interface CommonBizConstant {

    //短信发送操作命令，MT_REQUEST表示对一个用户发送信息
    public final static String COMMAND_REQUEST = "MT_REQUEST";

    //spid
    public final static String SMS_SPID = "6119";

    //sp密码
    public final static String SMS_SPPWD = "shlc911";

    //服务代码，默认是00
    public final static String SMS_SPSC = "00";

    //源地址，就是短信以这个号码发送
    public final static String SMS_SA = "10690248987796119";

    //服务编码格式  15 代表 gbk
    public final static int SMS_DC = 15;

    public final static String SMS_ENCODEFORM = "GBK";

    //电话号码区号
    public final static String SMS_PRE_PHONE = "86";

    /**
     * http://esms0.etonenet.com/sms/mt 联通线路
     * http://esms1.etonenet.com/sms/mt 移动线路
     * http://esms.etonenet.com/sms/mt 电信线路
     */
    public final static String SMS_MTURL_LT = "http://esms0.etonenet.com/sms/mt";
    public final static String SMS_MTURL_YD = "http://esms1.etonenet.com/sms/mt";
    public final static String SMS_MTURL_DX = "http://esms.etonenet.com/sms/mt";

    public final static String INIT_NULL = null;

    /**
     * 下面是定义的模板别名
     */
    //【微信】派单后通知工人
    public final static String Wx_Notice_Worker_After_Dipatch = "wx_notice_worker_after_dipatch";
    //【微信】确认订单后通知用户
    public final static String Wx_Notice_User_Order_Confirm = "wx_notice_user_order_confirm";
    //【短信】确认订单后通知用户
    public final static String Sms_Notice_User_Order_Confirm = "sms_notice_user_order_confirm";
    //【微信】工人上门签到后通知用户
    public final static String Wx_Notice_User_Signon = "wx_notice_user_signon";
    //【短信】工人上门签到后通知用户
    public final static String Sms_Notice_User_Signon = "sms_notice_user_signon";
    //【微信】工人完工后通知用户
    public final static String Wx_Notice_User_Finish = "wx_notice_user_finish";
    //【短信】工人完工后通知用户
    public final static String Sms_Notice_User_Finish = "sms_notice_user_finish";
    //【短信】派单后通知工人
    public final static String Sms_Notice_Worker_After_Dipatch = "sms_notice_worker_after_dipatch";
    //【短信】派送抢单后通知工人
    public final static String Sms_Notice_Worker_Challeng = "sms_notice_worker_challeng";
    //【微信】派送抢单后通知工人
    public final static String Wx_Notice_Worker_Challeng = "wx_notice_worker_challeng";
    //【短信】工人上门前提醒
    public final static String Sms_Notice_Worker_Get_To_Work = "sms_notice_worker_get_to_work";
    //【微信】工人上门前提醒
    public final static String Wx_Notice_Worker_Get_To_Work = "wx_notice_worker_get_to_work";
    //【短信】用户注册，发送验证码
    public final static String Sms_Send_Validate_Code = "sms_send_validate_code";

    //【短信】开始派单后通知用户
    public final static String Sms_Start_Distribute = "sms_start_distribute";

    //【微信】提醒员工派单
    public final static String Wx_notice_Employee_Distribute = "wx_notice_employee_distribute";

    //【微信】订单状态更新通知BD
    public final static String wx_notice_bd = "wx_notice_bd";

    //【微信】提醒员工回访
    public final static String Wx_Notice_Employee_Visit = "wx_notice_employee_visit";

    //【微信】提醒员工结算
    public final static String Wx_Notice_Employee_Settle = "wx_notice_employee_settle";

    //错误信息 response 中code 的值   出现异常的code   必须4位
    public final static int Error_Code_Exception = 1000;
    //错误信息 response 中code 的值   因为未查询到值得code    比如工人信息没找到
    public final static int Error_Code_Null_Value = 1001;

    //定义模板的类别  sms   wx
    public final static String Message_Type_Sms = "sms";
    public final static String Mesaage_Type_Wx = "wx";
    public final static String Mesaage_Type_Mail = "mail";

    //电话号码校验正则 ^1[3578]\d{9}$
    public final static String Reg_Phone = "^1[3578]\\d{9}$";

    //工人职位
    public final static String Position_Worker = "工人";

    //区域权限
    public final static String Location_Permission = "location";

    //超级权限
    public final static String Super_Permission = "super";

    //无权限
    public final static String None_Permission = "nothing";

    //商户状态
    public final static String Shop_status = "Online";

    //极光推送App注册设备
    public final static String UserApp_Source = "UserApp";

    public final static String RegistrationId = "registrationId";

    public final static String Jpush_Message = "jpush_message";

    //返回错误码 没有登录
    public final static int ERROR_CODE_NO_SESSION = -520;

    //返回错误码 没有房屋信息
    public final static int ERROR_CODE_NO_COMMUNITY_SESSION = -530;

    //返回错误码   没有完善用户信息
    public final static int ERROR_CODE_NO_USERINFO = -521;

    //登陆 并且用户信息完善的用户
    public final static int CODE_USERINFO_DONE = 201;

    //跳转到首页的code
    public final static int CODE_GOTO_INDEX = 200;


    public final static String Community_Operate_Resource= "redstar_community";
    public final static String Member_Operate_Resource= "redstar_member";
    public final static String UPDATE_OPERATION= "update_module";
    public final static String ADD_OPERATION= "add_module";
    public final static String DELETE_OPERATION= "delete_module";


    public final static String Android= "android";
    public final static String Ios= "ios";
    public final static String AppCode= "LY";

    public final static Integer DayReportDays=8;


    public final static String Http_Query_Success_Message="查询成功";
    public final static String Http_Server_Exception_Message="服务器响应异常";
    public final static String Http_Default_Data="data";


    public final static String Work_Default_Status="none";

    public final  static  String Param_Page="page";
    public final  static  String Param_PageSize="pageSize";

    public final  static  String UserCenter_Res_ErrorCode="code";
    public final  static  String UserCenter_Res_ErrorMsg="message";

}
