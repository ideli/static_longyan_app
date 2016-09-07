package com.greatbee.bean.constant;

/**
 * Created by usagizhang on 15-2-28.
 */
public interface LanchuiConstant {
    public final static String Waiting_RE_Distribute = "Waiting_RE_Distribute";
    public final static String Waiting_Worker_Confirm = "Waiting_Worker_Confirm";
    public final static String Waiting_Worker_On_Board = "Waiting_Worker_On_Board";
    public final static String Waiting_Worker_Finish = "Waiting_Worker_Finish";
    public final static String Waiting_Visit = "Waiting_Visit";


    //等待派单
    public final static String Waiting_Distribute = "Waiting_Distribute";
    //初始化数字
    public final static int Init_Num = 0;
    //等待结算
    public final static String Waiting_Settlement = "Waiting_Settlement";
    //结算中
    public final static String In_Settlement = "In_Settlement";

    //没有权限报错
    public final static String No_Permission = "没有权限查看该列表";
    //抢单中
    public final static String Challenging = "Challenging";
    //商铺
    public final static String Merchant = "Merchant";
    //社区
    public final static String Community = "Community";
    //订单来源    用户
    public final static String User = "User";
    //预约中
    public final static String Appointment = "Appointment";
    //登陆Employee
    public final static String SESSION_EMPLOYEE = "session_employee";
    //商户的sessionKey
    public final static String Merchant_Session = "merchant_session";
    //session的datamap
    public final static String SESSION_DATA_MAP = "session_data_map";
    //登陆信息 cookie的name值
    public static final String COOKIE_LOGIN_TOKEN_NAME = "loginToken";

    //Token的目标标记
    public static final String Community_PC = "Community-PC";

    //返回错误码 没有登录
    public final static int ERROR_CODE_NO_SESSION = -520;

    //跳转到首页的code
    public final static int CODE_GOTO_INDEX = 200;

    public static final int LC_BELONG_ID = 10944;

    //社区的sessionKey
    public final static String Community_Session = "community_session";
    //社区的sessionResource
    public final static String Community_Resource_Session = "community_resource_session";
    //社区登陆用户
    public final static String Community_Auth_Session = "community_auth_session";

    public static final String Request_FilePath = "RequestFilePath";
    public static final String Request_Store_Name = "RequestStoreName";
    public static final String Request_FileLength = "RequestFileLength";

    public final static int HTTP_SUCCESS_CODE = 200;
    public final static int HTTP_ERROR_CODE = 1003;
    public final static int FORM_ERROR_CODE = -1001;

    public final static int LOG_BELONG_ID = 10944;
    public final static String  Default_Root_Id  = "-1";

    public final static String Emp_Identify_Str="employee";

    public final static Integer Page_Default = 1;
    public final static Integer PageSize_Default = 20;

    public final  static  String taskLogStart = "start";

    public final  static  String taskLogEnd = "success";
    public final  static  String taskError = "error";

    public final static   String  Default_Date_Format="yyyy-MM-dd";
    /*商场类型*/
    public final  static  String Default_MallType = "real";



    /*********报表相关constant***********/

    //删除标识
    public final  static  String Report_Reduce_Community = "community";
    public final  static  String Report_Reduce_Member = "member";

    public final  static  String Report_Type_Day = "day";
    public final  static  String Report_Type_Month = "month";

    public final  static  String Query_Column_Year = "year";
    public final  static  String Query_Column_Month = "month";
    public final  static  String Query_Column_Day= "day";

    public final  static  String Query_Column_EmployeeId= "employeeId";
    public final  static  String Query_Column_CommunityId= "communityId";
    public final  static  String Query_Column_ShoppingMallId= "shoppingMallId";
    public final  static  String Query_Column_OrganizationId= "organizationId";
    public final  static  String Query_Column_MallType= "mallType";
    /*********报表相关constant***********/


    public final  static  String Default_ShowData="1";

    public final static  String Request_Header_Token ="_token";
    public final static  String Request_Header_OpenId ="_openId";



}
