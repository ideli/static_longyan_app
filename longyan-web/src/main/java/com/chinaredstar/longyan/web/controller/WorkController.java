package com.chinaredstar.longyan.web.controller;

import com.chinaredstar.commonBiz.bean.work.*;
import com.chinaredstar.commonBiz.manager.*;
import com.chinaredstar.longyan.exception.BusinessException;
import com.chinaredstar.longyan.exception.constant.CommonExceptionType;
import com.chinaredstar.longyan.util.DayDataSearchUtil;
import com.chinaredstar.longyan.util.DistanceUtil;
import com.chinaredstar.longyan.util.UserUtil;

import com.chinaredstar.commonBiz.bean.constant.CommonBizConstant;
import com.xiwa.base.bean.PaginationDescribe;
import com.xiwa.base.bean.Request;
import com.xiwa.base.bean.Response;
import com.xiwa.base.bean.search.ext.IntSearch;
import com.xiwa.base.bean.search.ext.MultiSearchBean;
import com.xiwa.base.bean.search.ext.TextSearch;
import com.xiwa.base.manager.ManagerException;
import com.xiwa.base.pipeline.PipelineContext;
import com.xiwa.base.util.DataUtil;
import com.xiwa.base.util.StringUtil;
import com.xiwa.zeus.trinity.bean.Employee;
import net.sf.json.JSONObject;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by niu on 2016/7/7.
 */


@Controller
@RequestMapping(value = "/work")
public class WorkController extends BaseController implements CommonBizConstant {

    @Autowired
    private DispatchDriver dispatchDriver;

    @Autowired
    private RedstarCommonManager redstarCommonManager;
    @Autowired
    private RedstarRestaurantDepartmentManager redstarRestaurantDepartmentManager;

    @Autowired
    private RedstarRestaurantMenuManager redstarRestaurantMenuManager;

    @Autowired
    private RedstarRestaurantOrderManager redstarRestaurantOrderManager;

    @Autowired
    private RedstarAttendanceRecordManager redstarAttendanceRecordManager;

    @Autowired
    private RedstarRestaurantInfoManager redstarRestaurantInfoManager;

    @Autowired
    private RedstarAttendanceLogManager redstarAttendanceLogManager;


    //获取记录
    @RequestMapping(value = "/attendance/get-records", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public Object getRecords() throws ManagerException {
        PipelineContext context = buildPipelineContent();
        Response response = context.getResponse();
        Request request = context.getRequest();
        Employee employee = getEmployeeromSession();
        if (employee == null) {
            throw new BusinessException(CommonExceptionType.NoSession);
        }

        int year = request.getInt("year");
        int month = request.getInt("month");

        // System.out.print("KKK"+yearStr.length()+"^^^^");

        if (year == 0) {
            throw new BusinessException(CommonExceptionType.ParameterError);
        }
        if (month == 0) {
            throw new BusinessException(CommonExceptionType.ParameterError);
        }

        MultiSearchBean multiSearchBean = new MultiSearchBean();

        IntSearch yearSearch = new IntSearch("year");
        yearSearch.setSearchValue(String.valueOf(year));
        multiSearchBean.addSearchBean(yearSearch);

        IntSearch monthSearch = new IntSearch("month");
        monthSearch.setSearchValue(String.valueOf(month));
        multiSearchBean.addSearchBean(monthSearch);

        int employeeId = employee.getId();
        IntSearch employeeIdSearch = new IntSearch("employeeId");
        employeeIdSearch.setSearchValue(String.valueOf(employeeId));
        multiSearchBean.addSearchBean(employeeIdSearch);

        try {
            int page = 1;
            int pageSize = 20;
            String orderColumn = "createDate";
            Boolean isAsc = false;

            PaginationDescribe<RedstarAttendanceRecord> redstarAttendanceRecords = dispatchDriver.getRedstarAttendanceRecordManager().searchBeanPage(page, pageSize, multiSearchBean, orderColumn, isAsc);
            response.addKey("redstarAttendanceRecords", redstarAttendanceRecords);
            setSuccessMsg(response);
        } catch (BusinessException e) {
            response.setCode(DataUtil.getInt(e.getErrorCode(), 0));//设置异常CODE
            response.setOk(Boolean.FALSE);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            response.setCode(DataUtil.getInt(CommonExceptionType.Unknow.getCode(), 0));
            setExceptionMsg(response);
            return response;
        }
        return response;
    }

    //打卡
    @RequestMapping(value = "/attendance/add-attendance", method = RequestMethod.POST)
    @ResponseBody
    public Object addRecords(String latitude, String longitude, RedstarAttendanceRecord thisRecord) {
        PipelineContext context = buildPipelineContent();
        Response res = context.getResponse();
        Request req = context.getRequest();

        if (StringUtil.isInvalid(latitude) || StringUtil.isInvalid(longitude)) {
            setErrMsg(res, "经纬度参数缺失");
            return res;
        }

        if (StringUtil.isInvalid(thisRecord.getProvince())) {
            setErrMsg(res, "省参数缺失");
            return res;
        }
        if (StringUtil.isInvalid(thisRecord.getCity())) {
            setErrMsg(res, "市参数缺失");
            return res;
        }
        if (StringUtil.isInvalid(thisRecord.getArea())) {
            setErrMsg(res, "区参数缺失");
            return res;
        }
        if (StringUtil.isInvalid(thisRecord.getAddress())) {
            setErrMsg(res, "地址参数缺失");
            return res;
        }

        try {

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());

            Employee employee = getEmployeeromSession();

            JSONObject userObj = UserUtil.getUserInfo(req.getHttpServletRequest(), redstarCommonManager);

            if (userObj != null && userObj.getInt("errorCode") == 0) {

                String deptId = userObj.getJSONObject("data").get("deptid").toString();

                TextSearch deptIdSearch = new TextSearch("departmentCode");
                deptIdSearch.setSearchValue(deptId);

                List<RedstarAttendanceDepartment> deptList = redstarCommonManager.getDataList(RedstarAttendanceDepartment.class, deptIdSearch);
                if (CollectionUtils.isEmpty(deptList)) {
                    setErrMsg(res, "员工部门不存在");
                    return res;
                }

                RedstarAttendanceDepartment dept = deptList.get(0);
                Integer checkpointId = dept.getCheckpointId();
                RedstarAttendanceCheckpoint redstarAttendanceCheckpoint = (RedstarAttendanceCheckpoint) redstarCommonManager.getDataById(checkpointId, RedstarAttendanceCheckpoint.class);
                if (redstarAttendanceCheckpoint == null) {
                    setErrMsg(res, "考勤点信息不存在");
                    return res;
                }

                double distance = DistanceUtil.getDistance(Double.parseDouble(latitude), Double.parseDouble(longitude), redstarAttendanceCheckpoint.getLatitude(), redstarAttendanceCheckpoint.getLongitude());

                if (distance > redstarAttendanceCheckpoint.getDistance()) {
                    setErrMsg(res, "不符合考勤点的距离要求");
                    return res;
                }




                Integer day = calendar.get(Calendar.DATE);
                Integer month = calendar.get(Calendar.MONTH) + 1;
                Integer year = calendar.get(Calendar.YEAR);

                //记录打卡
                RedstarAttendanceLog redstarAttendanceLog = new RedstarAttendanceLog();
                redstarAttendanceLog.setCreateDate(calendar.getTime());
                redstarAttendanceLog.setDay(day);
                redstarAttendanceLog.setMonth(month);
                redstarAttendanceLog.setYear(year);
                redstarAttendanceLog.setEmployeeId(employee.getId());
                redstarAttendanceLog.setEmployeeXingMing(employee.getXingMing());
                redstarAttendanceLog.setLongitude(Double.parseDouble(longitude));
                redstarAttendanceLog.setLatitude(Double.parseDouble(latitude));
                redstarAttendanceLog.setProvince(thisRecord.getProvince());
                redstarAttendanceLog.setCity(thisRecord.getCity());
                redstarAttendanceLog.setArea(thisRecord.getArea());
                redstarAttendanceLog.setAddress(thisRecord.getAddress());
                redstarCommonManager.addData(redstarAttendanceLog);


                MultiSearchBean multiSearchBean = DayDataSearchUtil.getMultiSearchBean(calendar);
                IntSearch userIdSearch = new IntSearch("employeeId");
                userIdSearch.setSearchValue(String.valueOf(employee.getId()));

                multiSearchBean.addSearchBean(userIdSearch);

                List<RedstarAttendanceLog> logList = redstarCommonManager.getDataList(RedstarAttendanceLog.class, multiSearchBean);

                if (!CollectionUtils.isEmpty(logList)) {
                    //记录签到时间
                    if (logList.size() == 1) {
                        RedstarAttendanceRecord redstarAttendanceRecord = new RedstarAttendanceRecord();
                        redstarAttendanceRecord.setYear(year);
                        redstarAttendanceRecord.setMonth(month);
                        redstarAttendanceRecord.setDay(day);
                        redstarAttendanceRecord.setEmployeeId(employee.getId());
                        redstarAttendanceRecord.setEmployeeXingMing(employee.getXingMing());
                        redstarAttendanceRecord.setCreateDate(calendar.getTime());
                        redstarAttendanceRecord.setStatus(Work_Default_Status);
                        redstarAttendanceRecord.setProvince(thisRecord.getProvince());
                        redstarAttendanceRecord.setCity(thisRecord.getCity());
                        redstarAttendanceRecord.setArea(thisRecord.getArea());
                        redstarAttendanceRecord.setAddress(thisRecord.getAddress());

                        redstarAttendanceRecord.setCheckinTime(new Date());
                        redstarCommonManager.addData(redstarAttendanceRecord);
                    } else {
                        //记录签退时间
                        List<RedstarAttendanceRecord> recordList = redstarCommonManager.getDataList(RedstarAttendanceRecord.class, multiSearchBean);
                        RedstarAttendanceRecord record = recordList.get(0);
                        record.setCheckoutTime(calendar.getTime());
                        redstarCommonManager.updateData(record);
                    }
                    res.addKey("data", calendar.getTime());
                    setSuccessMsg(res, "打卡成功");
                    return res;
                }
                setErrMsg(res, "打卡异常");
                return res;
            } else {
                setErrMsg(res, "用户信息获取失败");
                return res;
            }
        } catch (Exception e) {
            e.printStackTrace();
            //setExceptionMsg(res);
            setErrMsg(res, "打卡异常");
        }
        return res;
    }


    /**
     * 获取今天菜单 & 加班餐点餐状态
     *
     * @return
     */
    @RequestMapping(value = "/restaurant/get-menu", method = RequestMethod.POST)
    @ResponseBody
    public Response getMenu(HttpServletRequest request) {
        PipelineContext context = buildPipelineContent();
        Response res = context.getResponse();
        Request req = context.getRequest();

        try {
            Employee employee = getEmployeeromSession();    // 获取session中的登陆用户的employeeCode
            if (employee == null) {
                throw new RuntimeException("LoginTimeOut");
            }
            JSONObject json = UserUtil.getUserInfo(request, redstarCommonManager);
            String deptId = "";
            if (json != null && json.getInt("errorCode") == 0) {
                deptId = json.getJSONObject("data").get("deptid").toString();
            }
            MultiSearchBean searchBean = new MultiSearchBean();
            IntSearch intSearch = new IntSearch("departmentId");
            intSearch.setSearchValue(deptId);//Integer.parseInt(json.get("deptid").toString()) TODO
            searchBean.addSearchBean(intSearch);
            List<RedstarRestaurantDepartment> redstarRestaurantDepartments = redstarRestaurantDepartmentManager.searchIdentify(searchBean);
            if (redstarRestaurantDepartments.size() == 0) {
                setErrMsg(res, "请开通点餐权限");//   如果没读取到，抛出异常：请开通点餐权限
                res.setCode(90401);
                return res;
            }
            //   根据餐厅ID读取xiwa_redstar_restaurant_menu 拉出菜单   返回当天菜单信息（午餐和加班餐分开2个数组返回）
            RedstarRestaurantInfo redstarRestaurantInfo = (RedstarRestaurantInfo) redstarRestaurantInfoManager.getBean(redstarRestaurantDepartments.get(0).getRestaurantId());
            List<RedstarRestaurantMenu> redstarRestaurantMenus = redstarRestaurantMenuManager.findByRestaurantIdAndDate(redstarRestaurantDepartments.get(0).getRestaurantId());
            List<RedstarRestaurantMenu> dinners = new ArrayList<RedstarRestaurantMenu>();
            List<RedstarRestaurantMenu> lunch = new ArrayList<RedstarRestaurantMenu>();
            for (RedstarRestaurantMenu redstarRestaurantMenu : redstarRestaurantMenus) {
                if ("dinner".equals(redstarRestaurantMenu.getType())) {
                    dinners.add(redstarRestaurantMenu);
                } else {
                    lunch.add(redstarRestaurantMenu);
                }
            }
            res.addKey("lunch", lunch);
            res.addKey("dinner", dinners);
            res.addKey("restaurant", redstarRestaurantInfo);
//            res.addKey("dinnerMenu", dinnerMenu);
            //       根据餐厅ID和员工ID去xiwa_redstar_restaurant_order读取当天是否有点餐 返回点餐状态
            boolean isOrder = redstarRestaurantOrderManager.findByRestaurantIdAndEmployeeIdAndDay(redstarRestaurantDepartments.get(0).getRestaurantId(), employee.getId(), RedstarRestaurantOrder.NORMAL_ENUM, redstarCommonManager);
            res.addKey("isOrder", isOrder);
        } catch (ManagerException e) {
            e.printStackTrace();
        }
        setSuccessMsg(res);
        return res;
    }

    /**
     * 点餐 & 取消点餐
     *
     * @param type
     * @return
     */
    @RequestMapping(value = "/restaurant/booking", method = RequestMethod.POST)
    @ResponseBody
    public Response booking(String type, HttpServletRequest request) {
        PipelineContext context = buildPipelineContent();
        Response res = context.getResponse();
        Request req = context.getRequest();
        try {
            Employee employee = getEmployeeromSession();    // 获取session中的登陆用户的employeeCode
            if (employee == null) {
                throw new RuntimeException("LoginTimeOut");
            }
            JSONObject json = UserUtil.getUserInfo(request, redstarCommonManager);
            String deptId = "";
            if (json != null && json.getInt("errorCode") == 0) {
                deptId = json.getJSONObject("data").get("deptid").toString();
            }
            MultiSearchBean searchBean = new MultiSearchBean();
            IntSearch intSearch = new IntSearch("departmentId");
            intSearch.setSearchValue(deptId);//Integer.parseInt(json.get("deptid").toString()) TODO
            searchBean.addSearchBean(intSearch);
            List<RedstarRestaurantDepartment> redstarRestaurantDepartments = redstarRestaurantDepartmentManager.searchIdentify(searchBean);
            if (redstarRestaurantDepartments.size() == 0) {
                setErrMsg(res, "请开通点餐权限");//   如果没读取到，抛出异常：请开通点餐权限
                res.setCode(90502);
                return res;
            }

            RedstarRestaurantInfo redstarRestaurantInfo = (RedstarRestaurantInfo) redstarRestaurantInfoManager.getBean(redstarRestaurantDepartments.get(0).getRestaurantId());
            if (redstarRestaurantInfo == null) {
                setErrMsg(res, "餐厅信息未查到请确认数据");//   如果没读取到，抛出异常：请开通点餐权限
                res.setCode(90503);
                return res;
            }

            if (StringUtil.isValid(redstarRestaurantInfo.getCheckinTime()) && StringUtil.isValid(redstarRestaurantInfo.getCheckoutTime())) {
                DateTime now = DateTime.now();
                DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
                DateTime checkInTime = DateTime.parse(now.toString("yyyy-MM-dd") + " " + redstarRestaurantInfo.getCheckinTime() + ":00", format);
                DateTime checkOutTime = DateTime.parse(now.toString("yyyy-MM-dd") + " " + redstarRestaurantInfo.getCheckoutTime() + ":00", format);

                if (now.isBefore(checkInTime)) {
                    setErrMsg(res, "加班餐预定 "+redstarRestaurantInfo.getCheckinTime() +"-" +redstarRestaurantInfo.getCheckoutTime() +"目前还没开始哟~");
                    res.setCode(90504);
                    return res;
                }
                if (now.isAfter(checkOutTime)){
                    setErrMsg(res, "加班餐预定 "+redstarRestaurantInfo.getCheckinTime() +"-" +redstarRestaurantInfo.getCheckoutTime() +"目前已经结束哟~");
                    res.setCode(90504);
                    return res;
                }

            }

            if ("add".equals(type)) {
                boolean isOrder = redstarRestaurantOrderManager.findByRestaurantIdAndEmployeeIdAndDay(redstarRestaurantDepartments.get(0).getRestaurantId(), employee.getId(), RedstarRestaurantOrder.NORMAL_ENUM, redstarCommonManager);
                if (isOrder) {
                    setErrMsg(res, "已经点过餐了");
                    res.setCode(90503);
                    return res;
                }
                List<RedstarRestaurantOrder> results = redstarRestaurantOrderManager.findByRestaurantIdAndEmployeeIdAndDayInfo(redstarRestaurantDepartments.get(0).getRestaurantId(), employee.getId(), RedstarRestaurantOrder.CANCEL_ENUM);
                if (results.size() == 0) {
                    DateTime now = DateTime.now();
                    RedstarRestaurantOrder order = new RedstarRestaurantOrder();
                    order.setCreateDate(now.toDate());
                    order.setUpdateDate(now.toDate());
                    order.setEmployeeId(employee.getId());
                    order.setEmployeeXingMing(employee.getXingMing());
                    order.setRestaurantId(redstarRestaurantDepartments.get(0).getRestaurantId());
                    order.setRestaurantName(redstarRestaurantDepartments.get(0).getRestaurantName());
                    order.setStatus(RedstarRestaurantOrder.NORMAL_ENUM);
                    order.setYear(now.getYear());
                    order.setMonth(now.getMonthOfYear());
                    order.setDay(now.getDayOfMonth());
                    redstarRestaurantOrderManager.addBean(order);
                } else {
                    RedstarRestaurantOrder order = results.get(0);
                    order.setStatus(RedstarRestaurantOrder.NORMAL_ENUM);
                    order.setUpdateDate(DateTime.now().toDate());
                    redstarRestaurantOrderManager.updateBean(order);
                }
                String msg = employee.getXingMing() + "同学,你已经成功预定加班餐,请在18:30-19:30到食堂用餐";
                setSuccessMsg(res, msg);
            } else if ("cancel".equals(type)) {
                List<RedstarRestaurantOrder> results = redstarRestaurantOrderManager.findByRestaurantIdAndEmployeeIdAndDayInfo(redstarRestaurantDepartments.get(0).getRestaurantId(), employee.getId(), RedstarRestaurantOrder.NORMAL_ENUM);
                if (results.size() <= 0) {
                    setErrMsg(res, "未找到点餐记录无法取消");
                    res.setCode(90504);
                    return res;
                }
                for (RedstarRestaurantOrder removeOrder : results) {
                    removeOrder.setUpdateDate(DateTime.now().toDate());
                    removeOrder.setStatus(RedstarRestaurantOrder.CANCEL_ENUM);
                    redstarRestaurantOrderManager.updateBean(removeOrder);
                }
                setSuccessMsg(res, "取消点餐成功");
            } else {
                setErrMsg(res, "请确认type是否正确");
                res.setCode(90501);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            setErrMsg(res, "点餐接口异常");
            res.setCode(90506);
        }
        return res;
    }


    /**
     * 申诉
     *
     * @return
     */
    @RequestMapping(value = "/attendance/add-remark", method = RequestMethod.POST)
    @ResponseBody
    public Response addRemark() {
        PipelineContext context = buildPipelineContent();
        Response res = context.getResponse();
        Request req = context.getRequest();
        RedstarAttendanceRecord redstarAttendanceRecord;

        int id = req.getInt("id");
        if (id <= 0) {
            setErrMsg(res, "考勤记录ID不能为空");
            res.setCode(-1001);
            return res;
        }
        String remark = req.getString("remark");
        if (!StringUtil.isValid(remark)) {
            setErrMsg(res, "remark不能为空");
            res.setCode(-1001);
            return res;
        }
        String type = req.getString("type");
        if (!StringUtil.isValid(type)) {
            setErrMsg(res, "type不能为空");
            res.setCode(-1001);
            return res;
        } else {
            if (!type.equals(RedstarAttendanceRecord.ALL_TYPE)
                    && !type.equals(RedstarAttendanceRecord.CHECKIN_TYPE)
                    && !type.equals(RedstarAttendanceRecord.CHECKOUT_TYPE)) {
                setErrMsg(res, "type数据不正确,请确认");
                res.setCode(-1001);
                return res;
            }
        }
        try {
            Employee employee = getEmployeeromSession();    // 获取session中的登陆用户的employeeCode
            if (employee == null) {
                throw new RuntimeException("LoginTimeOut");
            }

            redstarAttendanceRecord = (RedstarAttendanceRecord) redstarAttendanceRecordManager.getBean(id);

            if (redstarAttendanceRecord != null) {
                if (StringUtil.isValid(redstarAttendanceRecord.getUserRemark()) || StringUtil.isValid(redstarAttendanceRecord.getUserRemarkType())) {
                    setErrMsg(res, "不允许重复申诉");
                    res.setCode(90302);
                    return res;
                }
                redstarAttendanceRecord.setUserRemark(remark);
                redstarAttendanceRecord.setUserRemarkType(type);
                redstarAttendanceRecordManager.updateBean(redstarAttendanceRecord);
                setSuccessMsg(res, "申诉成功");
            } else {
                setErrMsg(res, "未找到考勤记录");
                res.setCode(90301);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return res;
    }

    @RequestMapping(value = "/attendance/get-today-log", method = RequestMethod.POST)
    @ResponseBody
    public Response getTodayLog(HttpServletRequest request) {
        PipelineContext context = buildPipelineContent();
        Response res = context.getResponse();
        Employee employee = null;    // 获取session中的登陆用户的employeeCode
        try {
            employee = getEmployeeromSession();
            if (employee == null) {
                throw new RuntimeException("LoginTimeOut");
            }
            // 查询 xiwa_redstar_attendance_log 表获取当日当前登录用户的考勤记录并返回
            MultiSearchBean columnMultiSearchBean = new MultiSearchBean();

            IntSearch employeeIdSearch = new IntSearch("employeeId");
            employeeIdSearch.setSearchValue(String.valueOf(employee.getId()));
            columnMultiSearchBean.addSearchBean(employeeIdSearch);

            DateTime now = DateTime.now();
            int year = now.getYear();
            int month = now.getMonthOfYear();
            int day = now.getDayOfMonth();

            IntSearch yearSearch = new IntSearch("year");
            yearSearch.setSearchValue(String.valueOf(year));
            columnMultiSearchBean.addSearchBean(yearSearch);

            IntSearch monthSearch = new IntSearch("month");
            monthSearch.setSearchValue(String.valueOf(month));
            columnMultiSearchBean.addSearchBean(monthSearch);

            IntSearch daySearch = new IntSearch("day");
            daySearch.setSearchValue(String.valueOf(day));
            columnMultiSearchBean.addSearchBean(daySearch);

            List<RedstarAttendanceLog> redstarAttendanceLogs = redstarAttendanceLogManager.searchIdentify(columnMultiSearchBean);

            // 根据部门ID查询 xiwa_redstar_attendance_department 获取考勤点ID
            JSONObject json = UserUtil.getUserInfo(request, redstarCommonManager);
            MultiSearchBean multiSearchBean = new MultiSearchBean();
            TextSearch departmentId = new TextSearch("departmentCode");
            String deptId = "";
            if (json != null && json.getInt("errorCode") == 0) {
                deptId = json.getJSONObject("data").get("deptid").toString();
            }
            departmentId.setSearchValue(deptId);
            multiSearchBean.addSearchBean(departmentId);
            List<RedstarAttendanceDepartment> redstarAttendanceDepartments = redstarCommonManager.getDataList(RedstarAttendanceDepartment.class, multiSearchBean);
            if (redstarAttendanceDepartments.size() == 0) {
                setErrMsg(res, "部门信息未找到");
                res.setCode(500);
                return res;
            }
            RedstarAttendanceDepartment department = redstarAttendanceDepartments.get(0);
            //查询 xiwa_redstar_attendance_checkpoint 获取并返回考勤点信息
            RedstarAttendanceCheckpoint checkpoint = (RedstarAttendanceCheckpoint) redstarCommonManager.getDataById(department.getCheckpointId(), RedstarAttendanceCheckpoint.class);
            DateTime dateTime = DateTime.now();
            String today = dateTime.toString("yyyy-MM-dd") + " " + filterWeek(dateTime);
            checkpoint.setToday(today);
            res.addKey("checkpoint", checkpoint);
            MultiSearchBean searchBean = new MultiSearchBean();
            IntSearch intSearch = new IntSearch("departmentId");
            intSearch.setSearchValue(deptId);//Integer.parseInt(json.get("deptid").toString()) TODO
            searchBean.addSearchBean(intSearch);
            List<RedstarRestaurantDepartment> redstarRestaurantDepartments = redstarRestaurantDepartmentManager.searchIdentify(searchBean);
            String checkInTime = "";
            String checkOutTime = "";
            if (redstarRestaurantDepartments.size() > 0) {
                RedstarRestaurantInfo redstarRestaurantInfo = (RedstarRestaurantInfo) redstarRestaurantInfoManager.getBean(redstarRestaurantDepartments.get(0).getRestaurantId());
                if (redstarRestaurantInfo != null) {
                    checkInTime = redstarRestaurantInfo.getCheckinTime();
                    checkOutTime = redstarRestaurantInfo.getCheckoutTime();
                }
            }
            res.addKey("checkInTime", checkInTime);
            res.addKey("checkOutTime", checkOutTime);
            res.addKey("redstarAttendanceLogs", redstarAttendanceLogs);
//            res.addKey("weekDay", filterWeek(now));
            setSuccessMsg(res, "成功");
        } catch (ManagerException e) {
            e.printStackTrace();
        }

        return res;
    }

    public static String filterWeek(DateTime now) {
        //星期
        switch (now.getDayOfWeek()) {
            case DateTimeConstants.SUNDAY:
                return "星期日";
            case DateTimeConstants.MONDAY:
                return "星期一";
            case DateTimeConstants.TUESDAY:
                return "星期二";
            case DateTimeConstants.WEDNESDAY:
                return "星期三";
            case DateTimeConstants.THURSDAY:
                return "星期四";
            case DateTimeConstants.FRIDAY:
                return "星期五";
            case DateTimeConstants.SATURDAY:
                return "星期六";
            default:
                return "";
        }
    }
}
