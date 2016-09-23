package com.chinaredstar.longyan.web.oldcontroller;

import com.chinaredstar.commonBiz.manager.*;
import com.chinaredstar.commonBiz.bean.constant.CommonBizConstant;
import com.chinaredstar.longyan.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lenovo on 2016/4/26.
 */
//@RequestMapping(value = "/report")
//@Controller
public class ReportController extends BaseController implements CommonBizConstant {
//
//    @Autowired
//    private DispatchDriver dispatchDriver;
//
//    @Autowired
//    private RedstarMallEmployeeManager redstarMallEmployeeManager;
//
//    @Autowired
//    private RedstarMallMonthManager redstarMallMonthManager;
//
//    @Autowired
//    private RedstarShopMallOrganizationManager redstarShopMallOrganizationManager;
//
//    @Autowired
//    private RedstarCommonManager redstarCommonManager;
//
//    @Autowired
//    private RedstarOrganizationMonthManager redstarOrganizationMonthManager;
//
////    //员工录入情况报表
////    @RequestMapping(value = "/employee-input-report")
////    @ResponseBody
////    public Response employeeInputInfo() {
////
////        PipelineContext pipelineContext = this.buildPipelineContent();
////        Response res = pipelineContext.getResponse();
////        try {
////
////            int employeeId = pipelineContext.getRequest().getInt("employeeId", 0);
////            if (employeeId < 1) {
////                employeeId = getEmployeeromSession().getId();
////            }
////
////            //获取当前用户
////            RedstarEmployee employee = (RedstarEmployee) dispatchDriver.getRedstarEmployeeManager().getBean(employeeId);
////
////
////            List<RedstarEmployeeMonth> resultList = new LinkedList<RedstarEmployeeMonth>();
////
////            for (int i = 0; i < 5; i++) {
////                Calendar calendar = Calendar.getInstance();
////                if (i > 0) {
////                    calendar.add(Calendar.MONTH, -1 * i);
////                } else {
////                    calendar.setTime(new Date());
////                }
////
////                //当前年月
////                int year = calendar.get(Calendar.YEAR);
////                int month = calendar.get(Calendar.MONTH) + 1;
////                MultiSearchBean multiSearchBean = new MultiSearchBean();
////                IntSearch yearSearch = new IntSearch("year");
////                yearSearch.setSearchValue(String.valueOf(year));
////                multiSearchBean.addSearchBean(yearSearch);
////                IntSearch monthSearch = new IntSearch("month");
////                monthSearch.setSearchValue(String.valueOf(month));
////                multiSearchBean.addSearchBean(monthSearch);
////                IntSearch employeeSearch = new IntSearch("employeeId");
////                employeeSearch.setSearchValue(String.valueOf(employee.getId()));
////                multiSearchBean.addSearchBean(employeeSearch);
////
////                //当前月份录入的数量
////                List<RedstarEmployeeMonth> dataList = dispatchDriver.getRedstarEmployeeMonthManager().searchIdentify(multiSearchBean);
////
////                if (CollectionUtil.isValid(dataList)) {
////                    resultList.add(dataList.get(0));
////                }
////            }
////
////            res.addKey("employee_id", employee.getId());
////            res.addKey("employee_name", employee.getXingMing());
////            //社区录入总数
////            res.addKey("employee_community_input_count", employee.getInputCommunityAmount());
////            //住户录入总数
////            res.addKey("employee_member_input_count", employee.getInputMemberAmount());
////            //录入社区总户数
////            res.addKey("employee_input_community_room_amount", employee.getInputCommunityRoomAmount());
////            //最近5各月住户和小区录入情况
////            res.addKey("monthDataList", resultList);
////
////            IntSearch userIdSearch = new IntSearch("employeeId");
////            userIdSearch.setSearchValue(String.valueOf(employee.getId()));
////            res.addKey("dayDataList",getDayDataList(redstarCommonManager.getDayReportDataList(RedstarEmployeeDayInput.class,userIdSearch,DayReportDays)));
////
////            res.setCode(HTTP_SUCCESS_CODE);
////            res.setMessage("查询成功");
////        } catch (Exception e) {
////            setUnknowException(e,res);
////        }
////        return res;
////    }
////
////
////    //商场录入进度排名
////    @RequestMapping(value = "/shopping-mall-input-rank-report")
////    @ResponseBody
////    public Response shoppingMallInputInfo() {
////
////        PipelineContext pipelineContext = this.buildPipelineContent();
////        Response res = pipelineContext.getResponse();
////        Request req = pipelineContext.getRequest();
////        try {
////            Integer page = req.getInt("page");
////            Integer pageSize = req.getInt("pageSize");
////
////            if (page == 0) {
////                page = 1;
////            }
////            if (pageSize == 0) {
////                pageSize = 10;
////            }
////
////            Employee employee = getEmployeeromSession();
////            IntSearch intSearch = new IntSearch("employeeId");
////            intSearch.setSearchValue(String.valueOf(employee.getId()));
////            List<RedstarMallEmployee> mallList = redstarMallEmployeeManager.searchIdentify(intSearch);
////
////            ExtMallData currentMall;
////            if (!CollectionUtils.isEmpty(mallList)) {
////                RedstarMallEmployee me = mallList.get(0);
////                //员工所属商场
////                RedstarShoppingMall sm = (RedstarShoppingMall) dispatchDriver.getRedstarShoppingMallManager().getBean(me.getShoppingMallId());
////                currentMall = new ExtMallData();
////                currentMall.setInputMemberAmountRank(sm.getInputMemberAmountRank());
////                currentMall.setId(sm.getId());
////                currentMall.setName(sm.getName());
////                currentMall.setInputMemberAmount(sm.getInputMemberAmount());
////                currentMall.setInputCommunityAmount(sm.getInputCommunityAmount());
////                currentMall.setEmployeeCount(sm.getEmployeeCount());
////                res.addKey("employee_mall", currentMall);
////            }
////
////            ExtMallData extMallData = null;
////            PaginationDescribe<RedstarShoppingMall> dataList = dispatchDriver.getDispatchEmployeeManager().getByPages(page, pageSize, RedstarShoppingMall.class, "inputMemberAmount", Boolean.FALSE);
////            List<ExtMallData> resultList = new LinkedList<ExtMallData>();
////            for (RedstarShoppingMall m : dataList.getCurrentRecords()) {
////                extMallData = new ExtMallData();
////                extMallData.setInputMemberAmountRank(m.getInputMemberAmountRank());
////                extMallData.setId(m.getId());
////                extMallData.setName(m.getName());
////                extMallData.setInputMemberAmount(m.getInputMemberAmount());
////                extMallData.setInputCommunityAmount(m.getInputCommunityAmount());
////                extMallData.setEmployeeCount(m.getEmployeeCount());
////                resultList.add(extMallData);
////            }
////
////            ((SimplePaginationDescribe) dataList).setCurrentRecords(resultList);
////
////            res.setCode(HTTP_SUCCESS_CODE);
////            res.addKey("result", dataList);
////            res.setMessage("查询成功");
////
////        } catch (Exception e) {
////            setUnknowException(e,res);
////        }
////        return res;
////    }
////
////
////    //员工录入进度排名
////    @RequestMapping(value = "/employee-input-rank-report")
////    @ResponseBody
////    public Response employeeInputRank() {
////
////        PipelineContext pipelineContext = this.buildPipelineContent();
////        Response res = pipelineContext.getResponse();
////        Request req = pipelineContext.getRequest();
////        try {
////
////            Integer page = req.getInt("page");
////            Integer pageSize = req.getInt("pageSize");
////
////            if (page == 0) {
////                page = 1;
////            }
////            if (pageSize == 0) {
////                pageSize = 10;
////            }
////
////            //PaginationDescribe<RedstarEmployee> dataList = (PaginationDescribe<RedstarEmployee>) dispatchDriver.getDispatchEmployeeManager().getByPages(page, pageSize, RedstarEmployee.class, "inputMemberAmountRank", Boolean.FALSE);
////            MultiSearchBean multiSearchBean = new MultiSearchBean();
////            IntSearch beLongsIdSearch = new IntSearch("belongedId");
////            beLongsIdSearch.setSearchValue(String.valueOf(LC_BELONG_ID));
////            multiSearchBean.addSearchBean(beLongsIdSearch);
////
////            PaginationDescribe<RedstarEmployee> dataList = (PaginationDescribe<RedstarEmployee>) dispatchDriver.getRedstarEmployeeManager().searchBeanPage(page, pageSize, multiSearchBean, "inputMemberAmountRank", Boolean.TRUE);
////            ExtEmpData redstarEmployee = null;
////            List<ExtEmpData> resultList = new LinkedList<ExtEmpData>();
////            for (RedstarEmployee r : dataList.getCurrentRecords()) {
////                redstarEmployee = new ExtEmpData();
////                redstarEmployee.setId(r.getId());
////                redstarEmployee.setXingMing(r.getXingMing());
////                redstarEmployee.setInputCommunityAmount(r.getInputCommunityAmount());
////                redstarEmployee.setInputMemberAmount(r.getInputMemberAmount());
////                redstarEmployee.setInputMemberAmountRank(r.getInputMemberAmountRank());
////                resultList.add(redstarEmployee);
////            }
////
////            ((SimplePaginationDescribe) dataList).setCurrentRecords(resultList);
////            res.setCode(HTTP_SUCCESS_CODE);
////            res.addKey("result", dataList);
////            res.setMessage("查询成功");
////        } catch (Exception e) {
////            setUnknowException(e,res);
////        }
////        return res;
////    }
////
////
////    //商场录入情况报表
////    @RequestMapping(value = "/shopping-mall-input-report")
////    @ResponseBody
////    public Response shopInputReport() {
////
////        PipelineContext pipelineContext = this.buildPipelineContent();
////        Response res = pipelineContext.getResponse();
////        Request req = pipelineContext.getRequest();
////        try {
////
////            Integer shopId = req.getInt("mallId");
////
////
////            if (shopId == null || shopId == 0) {
////                setErrMsg(res, "mallId必须填写");
////                return res;
////            }
////
////            RedstarShoppingMall mall = (RedstarShoppingMall) dispatchDriver.getRedstarShoppingMallManager().getBean(shopId);
////
////            if (mall == null) {
////                setErrMsg(res, "当前商场不存在");
////                return res;
////            }
////
////            MultiSearchBean multiSearchBean;
////
////            IntSearch idSearch = new IntSearch("shoppingMallId");
////            idSearch.setSearchValue(String.valueOf(shopId));
////
////            Calendar calendar;
////            //当前年月
////            int year;
////            int month;
////            IntSearch yearSearch;
////            IntSearch monthSearch;
////            List<RedstarMallMonth> monthDataList = new LinkedList<RedstarMallMonth>();
////
////            /****************/
////            for (int index = 0; index < 5; index++) {
////                multiSearchBean = new MultiSearchBean();
////                calendar = Calendar.getInstance();
////                calendar.add(Calendar.MONTH, 0 - index);
////                year = calendar.get(Calendar.YEAR);
////                month = calendar.get(Calendar.MONTH) + 1;
////                yearSearch = new IntSearch("year");
////                yearSearch.setSearchValue(String.valueOf(year));
////                multiSearchBean.addSearchBean(yearSearch);
////                monthSearch = new IntSearch("month");
////                monthSearch.setSearchValue(String.valueOf(month));
////                multiSearchBean.addSearchBean(monthSearch);
////                multiSearchBean.addSearchBean(idSearch);
////                List<RedstarMallMonth> monthList = redstarMallMonthManager.searchIdentify(multiSearchBean);
////                if (!CollectionUtils.isEmpty(monthList)) {
////                    monthDataList.add(monthList.get(0));
////                }
////            }
////
////            /****************/
////
////            ExtMallData mallData = new ExtMallData();
////            mallData.setId(mall.getId());
////            mallData.setName(mall.getName());
////            mallData.setInputCommunityAmount(mall.getInputCommunityAmount());
////            mallData.setInputMemberAmount(mall.getInputMemberAmount());
////            mallData.setCommunityMemberAmount(mall.getInputCommunityRoomAmount());
////            mallData.setEmployeeCount(mall.getEmployeeCount());
////
////            List<Integer> idList = new LinkedList<Integer>();
////            //商场员工
////            List<RedstarMallEmployee> empList = redstarMallEmployeeManager.searchIdentify(idSearch);
////            for (RedstarMallEmployee r : empList) {
////                idList.add(r.getEmployeeId());
////            }
////
////            List<RedstarEmployee> dataList = dispatchDriver.getRedstarEmployeeManager().getBeanList(idList, "id", "inputMemberAmount", Boolean.TRUE);
////
////
////            res.addKey("mallInfo", mallData);
////            res.addKey("monthDataList", monthDataList);
////
////            res.addKey("dayDataList",getDayDataList(redstarCommonManager.getDayReportDataList(RedstarShoppingMallDayInput.class,idSearch,DayReportDays)));
//////            res.addKey("employeeCount", resultList);
////            res.setCode(HTTP_SUCCESS_CODE);
////            res.setMessage("查询成功");
////        } catch (Exception e) {
////            setUnknowException(e,res);
////        }
////        return res;
////    }
////
////
////    //区录入情况
////    @RequestMapping(value = "/area-input-report")
////    @ResponseBody
////    public Response areaInputInfo() {
////
////        PipelineContext pipelineContext = this.buildPipelineContent();
////        Response res = pipelineContext.getResponse();
////        Request req = pipelineContext.getRequest();
////        try {
////            Integer organizationId = req.getInt("organizationId");
////            if (organizationId == null || organizationId == 0) {
////                setErrMsg(res, "组织id必须填写");
////                return res;
////            }
////
////            IntSearch idSearch = new IntSearch("parentId");
////            idSearch.setSearchValue(String.valueOf(organizationId));
////
////
////            //所有的组织
////            List<RedstarShopMallOrganization> redstarShopMallOrganizationList = redstarShopMallOrganizationManager.searchIdentify(idSearch);
////
////            //组织id集合
////            List<Integer> idList = new LinkedList<Integer>();
////            for (RedstarShopMallOrganization r : redstarShopMallOrganizationList) {
////                idList.add(r.getId());
////            }
////
////            if(CollectionUtil.isInvalid(idList)){
////                //如果没有idlist表示当前查询的组织ID是中小区,下边没有挂接组织架构,则只查询当前组织的
////                idList.add(organizationId);
////            }
////
////            //查询组织所有商场的数据
////            List<RedstarShoppingMall> mallList = dispatchDriver.getRedstarShoppingMallManager().getBeanList(idList, "organizationId", "inputMemberAmount", Boolean.TRUE,getMapParams("mallType",Default_MallType));
////            //查询组织数据
////            RedstarShopMallOrganization currentData = (RedstarShopMallOrganization) redstarShopMallOrganizationManager.getBean(organizationId);
////            //查询组织月报
////            IntSearch organizationSearch = new IntSearch("organizationId");
////            organizationSearch.setSearchValue(StringUtil.getString(organizationId));
////
////            Calendar calendar;
////            Integer year;
////            Integer month;
////            MultiSearchBean multiSearchBean;
////
////            IntSearch yearSearch;
////
////            IntSearch monthSearch;
////
////            List<RedstarOrganizationMonth> monthList = new LinkedList<RedstarOrganizationMonth>();
////
////
////            for (int index = 0; index < 5; index++) {
////                multiSearchBean = new MultiSearchBean();
////
////                calendar = Calendar.getInstance();
////
////                calendar.add(Calendar.MONTH, 0 - index);
////
////                year = calendar.get(Calendar.YEAR);
////                month = calendar.get(Calendar.MONTH) + 1;
////
////                yearSearch = new IntSearch("year");
////                yearSearch.setSearchValue(String.valueOf(year));
////
////                monthSearch = new IntSearch("month");
////                monthSearch.setSearchValue(String.valueOf(month));
////
////                multiSearchBean.addSearchBean(organizationSearch);
////                multiSearchBean.addSearchBean(yearSearch);
////                multiSearchBean.addSearchBean(monthSearch);
////
////                List<RedstarOrganizationMonth> dataList = redstarOrganizationMonthManager.searchIdentify(multiSearchBean);
////                if (!CollectionUtils.isEmpty(dataList)) {
////                    RedstarOrganizationMonth resultData = dataList.get(0);
////                    monthList.add(resultData);
////                }
////            }
////            res.addKey("monthDataList", monthList);
////
////            res.addKey("currentData", currentData);
//////            res.addKey("monthDataList", resultList);
//////            res.addKey("mallDataList", extMallDataList);
////            res.addKey("mallCount", mallList.size());
////            res.addKey("dayDataList",getDayDataList(redstarCommonManager.getDayReportDataList(RedstarOrganizationDayInput.class,organizationSearch,DayReportDays)));
////            res.setCode(HTTP_SUCCESS_CODE);
////            res.setMessage("查询成功");
////        } catch (Exception e) {
////            setUnknowException(e,res);
////        }
////        return res;
////    }
////
////
////    //集团录入情况报表
////    @RequestMapping(value = "/all-input-report")
////    @ResponseBody
////    public Response allInputReport() {
////
////        PipelineContext pipelineContext = this.buildPipelineContent();
////        Response res = pipelineContext.getResponse();
////        try {
////
////            /**
////             * 查询出xiwa_redstar_shopping_mall_organization，parentId=1的所有组织
////             查询 xiwa_redstar_report_organization_input_month，organizationId=1的报表并返回最近5个月的数据
////             查询xiwa_redstar_shopping_mall_organization ，organizationId=1 ，返回住户录入数，社区录入数，社区总户数
////             根据A查询出来的结果，返回住户录入数，社区录入数，社区总户数，并且根据住户录入数进行倒序
////             */
////
////            //月数据
////            IntSearch organizationSearch = new IntSearch("organizationId");
////            organizationSearch.setSearchValue(Default_Root_Id);
////
////            Calendar calendar;
////            Integer year;
////            Integer month;
////            MultiSearchBean multiSearchBean;
////
////            IntSearch yearSearch;
////
////            IntSearch monthSearch;
////
////            List<RedstarOrganizationMonth> monthList = new LinkedList<RedstarOrganizationMonth>();
////
////
////            for (int index = 0; index < 5; index++) {
////                multiSearchBean = new MultiSearchBean();
////
////                calendar = Calendar.getInstance();
////
////                calendar.add(Calendar.MONTH, 0 - index);
////
////                year = calendar.get(Calendar.YEAR);
////                month = calendar.get(Calendar.MONTH) + 1;
////
////                yearSearch = new IntSearch("year");
////                yearSearch.setSearchValue(String.valueOf(year));
////
////                monthSearch = new IntSearch("month");
////                monthSearch.setSearchValue(String.valueOf(month));
////
////                multiSearchBean.addSearchBean(organizationSearch);
////                multiSearchBean.addSearchBean(yearSearch);
////                multiSearchBean.addSearchBean(monthSearch);
////
////                List<RedstarOrganizationMonth> dataList = redstarOrganizationMonthManager.searchIdentify(multiSearchBean);
////                if (!CollectionUtils.isEmpty(dataList)) {
////                    RedstarOrganizationMonth resultData = dataList.get(0);
////                    monthList.add(resultData);
////                }
////            }
////            res.addKey("monthDataList", monthList);
////            //总数据
////            RedstarShopMallOrganization redstarShopMallOrganization = (RedstarShopMallOrganization) redstarShopMallOrganizationManager.getBean(Integer.parseInt(Default_Root_Id));
////            res.addKey("totalData", redstarShopMallOrganization);
////
////
////            //所有组织列表
////            IntSearch parentIdSearch = new IntSearch("parentId");
////            parentIdSearch.setSearchValue(Default_Root_Id);
////            List<RedstarShopMallOrganization> redstarShopMallOrganizationList = redstarShopMallOrganizationManager.searchIdentify(parentIdSearch, "inputCommunityAmount", Boolean.FALSE);
////
////
////            res.addKey("organizationCount", redstarShopMallOrganizationList.size());
////
////            IntSearch idSearch = new IntSearch("organizationId");
////            idSearch.setSearchValue(Default_Root_Id);
////
////            //List<RedstarReportCountrywideDaily> dayDataList = getDayDataList(redstarCommonManager.getDayReportDataList(RedstarReportCountrywideDaily.class, null, DayReportDays));
////            List<RedstarReportCountrywideDaily> dayDataList = getDayCountryDataList(redstarCommonManager.getDayReportDataList(RedstarReportCountrywideDaily.class, null, DayReportDays));
////
////            List<RedstarOrganizationDayInput> resultList = new ArrayList<RedstarOrganizationDayInput>();
////            RedstarOrganizationDayInput thisData;
////
////            if(!CollectionUtils.isEmpty(dayDataList)){
////                for(RedstarReportCountrywideDaily item:dayDataList){
////                    thisData = new RedstarOrganizationDayInput();
////                    thisData.setId(item.getId());
////                    thisData.setYear(item.getYear());
////                    thisData.setMonth(item.getMonth());
////                    thisData.setDay(item.getDay());
////                    thisData.setOrganizationId(Integer.parseInt(Default_Root_Id));
////                    thisData.setOrganizationName("全国");
////                    thisData.setInputMemberAmount(item.getMemberInputAmount());
////                    thisData.setInputCommunityAmount(item.getCommunityInputAmount());
////                    resultList.add(thisData);
////                }
////            }
////            res.addKey("dayDataList",resultList);
////            res.setCode(HTTP_SUCCESS_CODE);
////            res.setMessage("查询成功");
////        } catch (Exception e) {
////            setUnknowException(e,res);
////        }
////        return res;
////    }
////
////    //商场的员工列表
////    @RequestMapping(value = "/shopping-mall-employee/list")
////    @ResponseBody
////    public Response shoppingMallEmployeeList() {
////
////        PipelineContext context = this.buildPipelineContent();
////        Response res = context.getResponse();
////        Request req = context.getRequest();
////        try {
////
////            Integer shoppingId = req.getInt("mallId");
////            if (shoppingId == 0) {
////                setErrMsg(res, "参数缺失");
////                return res;
////            }
////
////            Integer page = req.getInt("page");
////            Integer pageSize = req.getInt("pageSize");
////
////            if (page == 0) {
////                page = Page_Default;
////            }
////            if (pageSize == 0) {
////                pageSize = PageSize_Default;
////            }
////            RedstarShoppingMall mall = (RedstarShoppingMall) dispatchDriver.getRedstarShoppingMallManager().getBean(shoppingId);
////
////            if (mall == null) {
////                setErrMsg(res, "当前商场不存在");
////                return res;
////            }
////            IntSearch idSearch = new IntSearch("shoppingMallId");
////            idSearch.setSearchValue(String.valueOf(shoppingId));
////
////            List<Integer> idList = new LinkedList<Integer>();
////            //商场员工
////            List<RedstarMallEmployee> empList = redstarMallEmployeeManager.searchIdentify(idSearch);
////            for (RedstarMallEmployee r : empList) {
////                idList.add(r.getEmployeeId());
////            }
////            Map<String, Object> dataMap = dispatchDriver.getRedstarEmployeeManager().getDataListByIds(page, pageSize, idList, "id", "inputMemberAmount", Boolean.TRUE);
////
////
////            if (dataMap != null) {
////                res.addKey("result", dataMap.get("data"));
////
////                Integer total =
////                        Integer.valueOf(String.valueOf(dataMap.get("total")));
////
////                Integer totalPages = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
////
////                res.addKey("totalPages", totalPages);
////                res.addKey("currentPage", page);
////            }
////
////
////            res.setCode(HTTP_SUCCESS_CODE);
////            res.setMessage("查询成功");
////        } catch (Exception e) {
////            setUnknowException(e,res);
////        }
////        return res;
////    }
////
////
////    //大区所属商场列表
////    @RequestMapping(value = "/area-shopping-mall/list")
////    @ResponseBody
////    public Response areaShoppingMallList() {
////        PipelineContext context = this.buildPipelineContent();
////        Response res = context.getResponse();
////        Request req = context.getRequest();
////        try {
////            Integer organizationId = req.getInt("organizationId");
////            if (organizationId == 0) {
////                setErrMsg(res, "参数缺失");
////                return res;
////            }
////
////            RedstarShopMallOrganization org = (RedstarShopMallOrganization) dispatchDriver.getRedstarShopMallOrganizationManager().getBean(organizationId);
////
////            if (org == null) {
////                setErrMsg(res, "查询组织不存在");
////                return res;
////            }
////            //查询这个组织下的商场
////            List<Integer> idList = new ArrayList<Integer>();
////            idList.add(organizationId);
////
////            Map<Integer, String> orgInfo = new HashMap<Integer, String>();
////            orgInfo.put(org.getId(), org.getName());
////
////            orgInfo = setOrgInfo(orgInfo, org);
////
////            //id集合
////            if(orgInfo.size()>0){
////                for (Integer key : orgInfo.keySet()) {
////                    idList.add(key);
////                }
////            }
////
////            //查询所有商场
////            List<RedstarShoppingMall> mallList = dispatchDriver.getRedstarShoppingMallManager().getBeanList(idList, "organizationId", "inputMemberAmount", Boolean.TRUE,getMapParams("mallType",Default_MallType));
////            //查询商场对应数据
////            List<ExtShoppingMall> resultList = new ArrayList<ExtShoppingMall>();//所有数据
////            ExtShoppingMall shoppingMall;
////
////            if(!CollectionUtils.isEmpty(mallList)){
////                for (RedstarShoppingMall mall : mallList) {
////                    shoppingMall = new ExtShoppingMall();
////                    shoppingMall.setId(mall.getId());
////                    shoppingMall.setName(mall.getName());
////                    shoppingMall.setEmployeeCount(mall.getEmployeeCount());
////
////                    shoppingMall.setInputCommunityAmount(mall.getInputCommunityAmount());
////                    shoppingMall.setInputMemberAmount(mall.getInputMemberAmount());
////
////                    shoppingMall.setCommunityMemberAmount(mall.getInputCommunityRoomAmount());
////                    shoppingMall.setInputMemberAmountRank(mall.getInputMemberAmountRank());
////                    shoppingMall.setOrganizationId(mall.getOrganizationId());
////                    shoppingMall.setOrganizationName(orgInfo.get(mall.getOrganizationId()));
////
////                    resultList.add(shoppingMall);
////                }
////            }
////
////            setSuccessMsg(res);
////            res.addKey("result", resultList);
////        } catch (Exception e) {
////            setUnknowException(e,res);
////        }
////        return res;
////    }
////
////
////    private Map setOrgInfo(Map params, RedstarShopMallOrganization organization) throws ManagerException {
////        if (organization != null) {
////            IntSearch parentIdSearch = new IntSearch("parentId");
////            parentIdSearch.setSearchValue(String.valueOf(organization.getId()));
////            List<RedstarShopMallOrganization> mallOrganizationList = dispatchDriver.getRedstarShopMallOrganizationManager().searchIdentify(parentIdSearch);
////            if (!CollectionUtils.isEmpty(mallOrganizationList)) {
////                for (RedstarShopMallOrganization data : mallOrganizationList) {
////                    params.put(data.getId(), data.getName());
////                    setOrgInfo(params, data);
////                }
////            }
////        }
////        return params;
////    }
////
////
////    //集团所属大区列表
////    @RequestMapping(value = "/root-area/list")
////    @ResponseBody
////    public Response rootAreaList() {
////        PipelineContext context = this.buildPipelineContent();
////        Response res = context.getResponse();
////        try {
////
////            setSuccessMsg(res);
////            res.addKey("result", dispatchDriver.getRedstarShopMallOrganizationManager().getChildList(RedstarShopMallOrganization.class,"inputMemberAmount",Boolean.TRUE,"root"));
////            res.addKey("sub_area",dispatchDriver.getRedstarShopMallOrganizationManager().getChildList(RedstarShopMallOrganization.class,"inputMemberAmount",Boolean.TRUE,"child"));
////        } catch (Exception e) {
////            setUnknowException(e,res);
////        }
////        return res;
////    }
////
////
////    //省数据
////    @RequestMapping(value = "/province-report")
////    @ResponseBody
////    public Response provinceDataList() {
////
////        PipelineContext context = this.buildPipelineContent();
////        Response res = context.getResponse();
////        try {
////
////            List<RedstarReportProvinceInput> dataList = redstarCommonManager.getDataList(RedstarReportProvinceInput.class, null);
////            res.addKey("result",dataList);
////            List<RedstarReportProvinceInput> hasMallList = new ArrayList<RedstarReportProvinceInput>();
////            List<RedstarReportProvinceInput> hasNoMallList = new ArrayList<RedstarReportProvinceInput>();
////            MultiSearchBean multiSearchBean;
////            TextSearch textSearch;
////            TextSearch typeSearch;
////            Integer mallCount;
////            for (RedstarReportProvinceInput r:dataList){
////                textSearch = new TextSearch("provinceCode");
////                String provinceCode = r.getProvinceCode();
////                if (StringUtil.isInvalid(provinceCode)){
////                    continue;
////                }
////                textSearch.setSearchValue(provinceCode);
////
////                multiSearchBean = new MultiSearchBean();
////
////                typeSearch = new TextSearch("mallType");
////                typeSearch.setSearchValue(Default_MallType);
////
////                multiSearchBean.addSearchBean(textSearch);
////                multiSearchBean.addSearchBean(typeSearch);
////
////                mallCount = dispatchDriver.getSecurityOperationLogManager().getAllCount(RedstarShoppingMall.class,multiSearchBean);
////
////                r.setMallCount(mallCount);
////                if(mallCount==0){
////                    hasNoMallList.add(r);
////                }else{
////                    hasMallList.add(r);
////                }
////            }
////            res.addKey("hasMallList",hasMallList);
////            res.addKey("hasNoMallList",hasNoMallList);
////            res.setCode(HTTP_SUCCESS_CODE);
////            res.setMessage("查询成功");
////        } catch (Exception e) {
////            setUnknowException(e,res);
////        }
////        return res;
////    }
////
////    //近十天的数据
////    @RequestMapping("/dashboard/all-data-by-daily")
////    @ResponseBody
////    public Response allDataByDaily() {
////        PipelineContext context = this.buildPipelineContent();
////        Response res = context.getResponse();
////        Request req = context.getRequest();
////        String sortColumn = req.getString("sortColumn");
////        String isAsc = req.getString("isAsc");
////        Calendar calendar = Calendar.getInstance();
////        calendar.setTime(new Date());
////        try {
////            calendar.add(Calendar.DAY_OF_MONTH,-9);
////            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
////            String thisDate  = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
////            Date date = dateFormat.parse(thisDate);
////            Boolean sort=false;
////            if(StringUtil.isValid(sortColumn)&&StringUtil.isValid(isAsc)){
////                if("desc".equals(isAsc)){
////                    sort=true;
////                }else{
////                    sort=false;
////                }
////            }
////            List  _thisList = redstarCommonManager.getDayList(sortColumn,sort,date,10);
////
////            res.addKey("result", _thisList);
////            res.setCode(HTTP_SUCCESS_CODE);
////            res.setMessage("查询成功");
////        } catch (Exception e) {
////            setUnknowException(e,res);
////        }
////        return res;
////    }
////
////    //昨天 今天 历史数据总和
////    @RequestMapping("/dashboard/all-data-by-sum")
////    @ResponseBody
////    public Response allDataBySum() {
////        PipelineContext context = this.buildPipelineContent();
////        Response res = context.getResponse();
////        try {
////           // redstarCommonManager.excuteBySql()
////            ExtCountryData countryData = ReportUtil.getAllDataBySum(redstarCommonManager, res);
////            countryData.setActiveUserCount(redstarCommonManager.getHistoryUserCount());
////            res.addKey("history", countryData);
////            res.setCode(HTTP_SUCCESS_CODE);
////            res.setMessage("查询成功");
////        } catch (Exception e) {
////            setUnknowException(e,res);
////        }
////        return res;
////    }
////
////    //PC顶部数据
////    @RequestMapping("/dashboard/pc/top-data")
////    @ResponseBody
////    public Response dashboardTopData() {
////        PipelineContext context = this.buildPipelineContent();
////        Response res = context.getResponse();
////        try {
////            TextSearch textSearch = new TextSearch("mallType");
////            textSearch.setSearchValue(Default_MallType);
////            List<RedstarShoppingMall> mallList = dispatchDriver.getRedstarShoppingMallManager().searchIdentify(textSearch);
////            //获取集团总数据
////            RedstarShopMallOrganization topOrganization = (RedstarShopMallOrganization) dispatchDriver.getRedstarShopMallOrganizationManager().getBean(-1);
////
////
////            res.addKey("employeeCount", topOrganization.getEmployeeCount());
////            res.addKey("mallCount", mallList.size());
////            res.addKey("communityCount", topOrganization.getInputCommunityAmount());
////            res.addKey("memberCount", topOrganization.getInputMemberAmount());
////            res.setCode(HTTP_SUCCESS_CODE);
////            res.setMessage("查询成功");
////        } catch (Exception e) {
////            setUnknowException(e,res);
////        }
////        return res;
////    }
////
////    //
////    public Response tallDataByDaily() {
////
////        String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
////        MultiSearchBean multiSearchBean = new MultiSearchBean();
////
////        IntSearch belongedId = new IntSearch("belongedId");
////        belongedId.setSearchValue(String.valueOf(LOG_BELONG_ID));
////        multiSearchBean.addSearchBean(belongedId);
////
////        TextSearch textSearch = new TextSearch("operateResource");
////        textSearch.setSearchValue(Community_Operate_Resource);
////        multiSearchBean.addSearchBean(textSearch);
////
////        TextSearch textSearch1 = new TextSearch("operationTypeField");
////        textSearch1.setSearchValue(ADD_OPERATION);
////        multiSearchBean.addSearchBean(textSearch1);
////
/////*        Integer count =  dispatchDriver.getSecurityOperationLogManager().getCount(SecurityOperationLog.class,"2016-05-05",multiSearchBean);*/
////
////        return null;
////    }
////
////    private Map<String,Object>  getMapParams(String key,Object value){
////        Map<String,Object> params = new HashMap<String, Object>();
////        params.put(key,value);
////        return  params;
////    }
////
////    private List<DayInputInterface> getDayDataList(List<DayInputInterface> dataList){
////        Calendar calendar =Calendar.getInstance();
////        calendar.setTime(new Date());
////
////        String year = String.valueOf(calendar.get(Calendar.YEAR));
////        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
////        String day = String.valueOf(calendar.get(Calendar.DATE));
////        String today=year+month+day;
////
////        List<DayInputInterface> resultList = new ArrayList<DayInputInterface>();
////        if(!CollectionUtils.isEmpty(dataList)){
////            for (DayInputInterface data:dataList){
////                if(today.equals(data.getDate())){
////                    continue;
////                }else{
////                    resultList.add(data);
////                }
////
////                if (resultList.size()==DayReportDays-1){
////                    break;
////                }
////            }
////        }
////        return  resultList;
////    }
////
////
////    private List<RedstarReportCountrywideDaily> getDayCountryDataList(List<RedstarReportCountrywideDaily> dataList){
////        Calendar calendar =Calendar.getInstance();
////        calendar.setTime(new Date());
////
////        String year = String.valueOf(calendar.get(Calendar.YEAR));
////        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
////        String day = String.valueOf(calendar.get(Calendar.DATE));
////        String today=year+month+day;
////
////        List<RedstarReportCountrywideDaily> resultList = new ArrayList<RedstarReportCountrywideDaily>();
////        if(!CollectionUtils.isEmpty(dataList)){
////            for (RedstarReportCountrywideDaily data:dataList){
////                if(today.equals(data.getDate())){
////                    continue;
////                }else{
////                    resultList.add(data);
////                }
////
////                if (resultList.size()==DayReportDays-1){
////                    break;
////                }
////            }
////        }
////        return  resultList;
////    }

}
