package com.chinaredstar.longyan.web.controller;

import com.chinaredstar.commonBiz.bean.*;
import com.chinaredstar.longyan.bean.bo.RecommendCommunityExtObject;
import com.chinaredstar.longyan.exception.BusinessException;
import com.chinaredstar.longyan.exception.FormException;
import com.chinaredstar.longyan.exception.constant.CommonExceptionType;
import com.chinaredstar.longyan.exception.constant.CommunityExceptionType;
import com.chinaredstar.longyan.util.CommunityFormUtil;
import com.chinaredstar.longyan.util.DayDataSearchUtil;
import com.chinaredstar.longyan.util.RateUtil;
import com.chinaredstar.longyan.util.SetCommunityMallUtil;
import com.chinaredstar.commonBiz.bean.constant.CommonBizConstant;
import com.chinaredstar.commonBiz.manager.DispatchDriver;
import com.chinaredstar.commonBiz.manager.RedstarCommonManager;
import com.chinaredstar.commonBiz.manager.RedstarCommunityUnitManager;
import com.chinaredstar.commonBiz.util.DoubleUtil;
import com.xiwa.base.bean.PaginationDescribe;
import com.xiwa.base.bean.Request;
import com.xiwa.base.bean.Response;
import com.xiwa.base.bean.SimplePaginationDescribe;
import com.xiwa.base.bean.search.ext.IntSearch;
import com.xiwa.base.bean.search.ext.MultiSearchBean;
import com.xiwa.base.bean.search.ext.TextSearch;
import com.xiwa.base.manager.ManagerException;
import com.xiwa.base.pipeline.PipelineContext;
import com.xiwa.base.util.DataUtil;
import com.xiwa.base.util.StringUtil;
import com.xiwa.zeus.trinity.bean.Employee;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by wangj on 2015/7/21.
 */
@RequestMapping("/community")
@Controller
public class CommunityController extends BaseController implements CommonBizConstant {

    @Autowired
    private DispatchDriver dispatchDriver;
//    @Autowired
//    protected SecurityDriver securityDriver;

    @Autowired
    private RedstarCommonManager redstarCommonManager;

    @Autowired
    private RedstarCommunityUnitManager redstarCommunityUnitManager;


    //查询小区列表
    @RequestMapping(value = "/list")
    @ResponseBody
    public Response dataList() {
        PipelineContext pipelineContext = this.buildPipelineContent();
        Response res = pipelineContext.getResponse();
        try {
            //页数
            Integer page = pipelineContext.getRequest().getInt("page");
            //每页记录数
            Integer pageSize = pipelineContext.getRequest().getInt("pageSize");

            if (page == 0) {
                page = Page_Default;
            }
            if (pageSize == 0) {
                pageSize = PageSize_Default;
            }

            Employee loginEmployee = getEmployeeromSession();
            //IntSearch ownerIdSearch = new IntSearch("ownerId");
            IntSearch ownerIdSearch = new IntSearch("createEmployeeId");
            ownerIdSearch.setSearchValue(String.valueOf(loginEmployee.getId()));
            PaginationDescribe<RedstarCommunity> result = (PaginationDescribe<RedstarCommunity>) dispatchDriver.getRedstarCommunityManager().searchBeanPage(page, pageSize, ownerIdSearch, "updateDate", Boolean.FALSE);

            List<RedstarCommunity> redstarCommunityList = result.getCurrentRecords();

            List<RedstarCommunity> dataList = new LinkedList<RedstarCommunity>();

            //录入率和入住率
            for (RedstarCommunity r : redstarCommunityList) {
                double occupanyRate;
                double inputRate;
                if (r.getAlreadyCheckAmount() != null && r.getRoomMount() != null && r.getRoomMount() > 0) {
                    occupanyRate = DoubleUtil.div(r.getAlreadyCheckAmount(), r.getRoomMount(), 2);
                    r.setOccupanyRate(RateUtil.getDoubleValue(occupanyRate * 100));
                } else {
                    r.setOccupanyRate(0.0);
                }
                if (r.getAlreadyInputAmount() != null && r.getRoomMount() != null && r.getRoomMount() > 0) {
                    inputRate = DoubleUtil.div(r.getAlreadyInputAmount(), r.getRoomMount(), 2);
                    r.setInputRate(RateUtil.getDoubleValue(inputRate * 100));
                } else {
                    r.setInputRate(0.0);
                }
                dataList.add(r);
            }
            ((SimplePaginationDescribe) result).setCurrentRecords(dataList);
            setSuccessMsg(res);
            res.addKey("result", result);
        } catch (Exception e) {
            setUnknowException(e, res);
        }
        return res;
    }

    //查询小区
    @RequestMapping(value = "/{id}")
    @ResponseBody
    public Response dataItem(@PathVariable("id") Integer id) {
        PipelineContext pipelineContext = this.buildPipelineContent();
        Response res = pipelineContext.getResponse();
        try {
            RedstarCommunity community = (RedstarCommunity) dispatchDriver.getRedstarCommunityManager().getBean(id);
            double occupanyRate;
            double inputRate;
            if (community.getAlreadyCheckAmount() != null && community.getRoomMount() != null && community.getRoomMount() > 0) {
                occupanyRate = DoubleUtil.div(community.getAlreadyCheckAmount(), community.getRoomMount(), 2);
                community.setOccupanyRate(RateUtil.getDoubleValue(occupanyRate * 100));
            } else {
                community.setOccupanyRate(0.0);
            }
            if (community.getAlreadyInputAmount() != null && community.getRoomMount() != null && community.getRoomMount() > 0) {
                inputRate = DoubleUtil.div(community.getAlreadyInputAmount(), community.getRoomMount(), 2);
                community.setInputRate(RateUtil.getDoubleValue(inputRate * 100));
            } else {
                community.setInputRate(0.0);
            }
            res.setCode(HTTP_SUCCESS_CODE);
            res.addKey("community", community);
        } catch (Exception e) {
            setErrMsg(res, "没有数据");
        }
        return res;
    }

    //添加小区
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Response dataCreate() {
        PipelineContext pipelineContext = this.buildPipelineContent();
        Request request = pipelineContext.getRequest();
        Response res = pipelineContext.getResponse();
        RedstarCommunity community = new RedstarCommunity();
        try {
            Employee employee = getEmployeeromSession();

            //设置省市区和小区名称
            CommunityFormUtil.setLoctionAndName(request, dispatchDriver, community);
            //详细地址
            CommunityFormUtil.setAddress(request, community);
            //小区别称
            CommunityFormUtil.setShortName(request, community);
            //总户数
            CommunityFormUtil.setRoomMount(request, community);
            //总栋数
            CommunityFormUtil.setBuildingAmount(request, community);
            //入住率
            CommunityFormUtil.setAlreadyCheckAmount(request, community);
            //房屋均价
            CommunityFormUtil.setPriceSection(request, community);
            //建筑类型
            CommunityFormUtil.setConstructionTypes(request, community);
            //交房装修
            CommunityFormUtil.setRenovations(request, community);
            //交房时间
            CommunityFormUtil.setDeliveryTime(request, community);
            //开发商信息
            CommunityFormUtil.setDevelopers(request, community, redstarCommonManager);
            //物业公司
            CommunityFormUtil.setPropertyName(request, community, redstarCommonManager);
            //物业电话
            CommunityFormUtil.setHotline(request, community);


            SecurityOperationLog securityOperationLog = new SecurityOperationLog();

            if (employee != null) {
                community.setCreateEmployeeId(employee.getId());
                community.setCreateXingMing(employee.getXingMing());
                securityOperationLog.setOperatorId(employee.getId());
                securityOperationLog.setOperator(employee.getXingMing());
            }

            community.setCreateDate(new Date());
            community.setUpdateDate(new Date());
            community.setSource("employee");


            //根据省市代码查找
//            MultiSearchBean multiSearchBean = new MultiSearchBean();
//            multiSearchBean.addSearchBean(provinceSearch);
//            multiSearchBean.addSearchBean(cityCodeSearch);
//            community = SetCommunityMallUtil.setCommunityMall(redstarCommonManager, employee, community, multiSearchBean);


            /***
             * 2016.6.20
             */
            Integer dataId = dispatchDriver.getRedstarCommunityManager().addBean(community);
            res.addKey("id", dataId);
            //添加日志
            securityOperationLog.setOperateResource(Community_Operate_Resource);
            securityOperationLog.setCreateDate(new Date());
            securityOperationLog.setBelongedId(LOG_BELONG_ID);
            securityOperationLog.setOperationTypeField(ADD_OPERATION);
            securityOperationLog.setOperateResourceId(String.valueOf(dataId));
            securityOperationLog.setContent("添加小区");
            dispatchDriver.getSecurityOperationLogManager().addBean(securityOperationLog);
            //添加积分


            res.setCode(HTTP_SUCCESS_CODE);
            res.setMessage("操作成功");
        } catch (FormException e) {
            res.setCode(FORM_ERROR_CODE);
            res.setOk(Boolean.FALSE);
            res.setMessage(e.getMessage());
        } catch (BusinessException e) {
            res.setCode(DataUtil.getInt(e.getErrorCode(), 0));//设置异常CODE
            res.setOk(Boolean.FALSE);
            res.setMessage(e.getMessage());
        } catch (Exception e) {
            setUnknowException(e, res);
        }
        return res;
    }


    //更新小区信息
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Response dataUpdate() {
        PipelineContext pipelineContext = this.buildPipelineContent();
        Request request = pipelineContext.getRequest();
        Response res = pipelineContext.getResponse();

        try {
            Employee employee = getEmployeeromSession();
            Integer dataId = request.getInt("id");
            if (dataId == null) {
                throw new FormException("小区id没有填写");
            }
            RedstarCommunity community = (RedstarCommunity) dispatchDriver.getRedstarCommunityManager().getBean(dataId);
            if (community == null) {
                throw new FormException("没有找到小区");
            }
            //详细地址
            CommunityFormUtil.setAddress(request, community);
            //小区别称
            CommunityFormUtil.setShortName(request, community);
            //总户数
            CommunityFormUtil.setRoomMount(request, community);
            //总栋数
            CommunityFormUtil.setBuildingAmount(request, community);
            //入住率
            CommunityFormUtil.setAlreadyCheckAmount(request, community);
            //房屋均价
            CommunityFormUtil.setPriceSection(request, community);
            //建筑类型
            CommunityFormUtil.setConstructionTypes(request, community);
            //交房装修
            CommunityFormUtil.setRenovations(request, community);
            //交房时间
            CommunityFormUtil.setDeliveryTime(request, community);
            //开发商信息
            CommunityFormUtil.setDevelopers(request, community, redstarCommonManager);
            //物业公司
            CommunityFormUtil.setPropertyName(request, community, redstarCommonManager);
            //物业电话
            CommunityFormUtil.setHotline(request, community);

            SecurityOperationLog securityOperationLog = new SecurityOperationLog();
            if (employee != null) {
                securityOperationLog.setOperatorId(employee.getId());
                securityOperationLog.setOperator(employee.getXingMing());
                securityOperationLog.setOperateResource(Community_Operate_Resource);
                securityOperationLog.setCreateDate(new Date());
                securityOperationLog.setBelongedId(LOG_BELONG_ID);

                securityOperationLog.setOperateResourceId(String.valueOf(dataId));

                if (!(DataUtil.getInt(community.getCreateEmployeeId(), 0) > 0)) {
                    //如果没有CreateEmployeeId，则设置当前用户为CreateEmployeeId
/*                    community.setOwnerId(employee.getId());
                    community.setOwnerXingMing(employee.getXingMing());*/
                    community.setCreateEmployeeId(employee.getId());
                    community.setCreateXingMing(employee.getXingMing());
                    //设置小区的商场id name
                    community = SetCommunityMallUtil.setCommunityMall(redstarCommonManager, employee, community, null);
                    //重新设置下createDate的时间
                    community.setCreateDate(new Date());
                    securityOperationLog.setOperationTypeField(ADD_OPERATION);
                    securityOperationLog.setContent("添加小区");
                    dispatchDriver.getSecurityOperationLogManager().addBean(securityOperationLog);
                    //TODO:添加小区积分规则

                } else {
                    securityOperationLog.setOperationTypeField(UPDATE_OPERATION);
                    securityOperationLog.setContent("更新小区");
                    dispatchDriver.getSecurityOperationLogManager().addBean(securityOperationLog);
                }
                community.setUpdateEmployeeId(employee.getId());
                community.setUpdateEmployeeXingMing(employee.getXingMing());
            }
            community.setUpdateDate(new Date());
            dispatchDriver.getRedstarCommunityManager().updateBean(community);
            res.setCode(HTTP_SUCCESS_CODE);
            res.setMessage("操作成功");
        } catch (FormException e) {
            //表单校验不通过
            res.setCode(FORM_ERROR_CODE);
            res.setOk(Boolean.FALSE);
            res.setMessage(e.getMessage());
        } catch (Exception e) {
            setUnknowException(e, res);
        }
        return res;
    }


    /**
     * **********************分割线**************************
     */
    /*

    /**
     * 搜索社区列表
     *
     * @return
     */
    @RequestMapping(value = "/search-by-name")
    public
    @ResponseBody
    Response searchByName() {
        PipelineContext pipelineContext = this.buildPipelineContent();
        Response response = pipelineContext.getResponse();

        try {
            String name = pipelineContext.getRequest().getString("name");
            String city = pipelineContext.getRequest().getString("city");
/*            String provinceCode = pipelineContext.getRequest().getString("provinceCode");
            String cityCode = pipelineContext.getRequest().getString("cityCode");
            String areaCode = pipelineContext.getRequest().getString("areaCode");*/

            MultiSearchBean multiSearchBean = new MultiSearchBean();

            TextSearch nameSearch = new TextSearch("name", true);
            nameSearch.setSearchValue(name);
            multiSearchBean.addSearchBean(nameSearch);

            TextSearch citySearch = new TextSearch("city");
            citySearch.setSearchValue(city);
            multiSearchBean.addSearchBean(citySearch);

/*
            TextSearch provinceCodeSearch = new TextSearch("provinceCode");
            provinceCodeSearch.setSearchValue(provinceCode);
            multiSearchBean.addSearchBean(provinceCodeSearch);
            TextSearch cityCodeSearch = new TextSearch("cityCode");
            cityCodeSearch.setSearchValue(cityCode);
            multiSearchBean.addSearchBean(cityCodeSearch);
            TextSearch areaCodeSearch = new TextSearch("areaCode");
            areaCodeSearch.setSearchValue(areaCode);
            multiSearchBean.addSearchBean(areaCodeSearch);*/

            PaginationDescribe paginationDescribe = dispatchDriver.getRedstarCommunityManager().searchBeanPage(1, 5, multiSearchBean, "ownerId", Boolean.FALSE);
            response.addKey("result", paginationDescribe.getCurrentRecords());

        } catch (ManagerException e) {
            setUnknowException(e, response);
        }
        return response;
    }


    //开发商搜索接口
    @RequestMapping(value = "/developers")
    @ResponseBody
    public Response getDevelopers() {
        PipelineContext context = buildPipelineContent();
        Response res = context.getResponse();
        Request req = context.getRequest();
        try {
            String name = req.getString("name");
            if (StringUtil.isInvalid(name)) {
                setErrMsg(res, "参数缺失");
                return res;
            }
            TextSearch textSearch = new TextSearch("name", true);
            textSearch.setSearchValue(name);

            res.addKey("result", redstarCommonManager.getDataList(RedstarDevelopers.class, textSearch));
            res.setCode(HTTP_SUCCESS_CODE);
            res.setMessage("查询成功");
        } catch (Exception e) {
            setUnknowException(e, res);
        }
        return res;
    }


    @RequestMapping(value = "/developers/{id}")
    @ResponseBody
    public Response getDevelopersById(@PathVariable("id") String id) {
        PipelineContext context = buildPipelineContent();
        Response res = context.getResponse();
        try {
            if (StringUtil.isInvalid(id)) {
                setErrMsg(res, "参数缺失");
                return res;
            }
            res.addKey("result", redstarCommonManager.getDataById(Integer.parseInt(id), RedstarDevelopers.class));
            res.setCode(HTTP_SUCCESS_CODE);
            res.setMessage("查询成功");
        } catch (Exception e) {
            e.printStackTrace();
            setErrMsg(res, "服务器响应异常");
        }
        return res;
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

    //查询小区列表
    @RequestMapping(value = "/list_by_employee")
    @ResponseBody
    public Response getDataListByEmployeeId() {
        PipelineContext pipelineContext = this.buildPipelineContent();
        Response res = pipelineContext.getResponse();
        Request req = pipelineContext.getRequest();
        try {
            Integer employeeId = req.getInt("employeeId");
            if (employeeId == 0) {
                setErrMsg(res, "参数缺失");
                return res;
            }
            //页数
            Integer page = req.getInt("page");
            //每页记录数
            Integer pageSize = req.getInt("pageSize");

            if (page == 0) {
                page = Page_Default;
            }
            if (pageSize == 0) {
                pageSize = PageSize_Default;
            }

            //IntSearch ownerIdSearch = new IntSearch("ownerId");
            IntSearch ownerIdSearch = new IntSearch("createEmployeeId");
            ownerIdSearch.setSearchValue(String.valueOf(employeeId));

            PaginationDescribe<RedstarCommunity> result = (PaginationDescribe<RedstarCommunity>) dispatchDriver.getRedstarCommunityManager().searchBeanPage(page, pageSize, ownerIdSearch, "updateDate", Boolean.FALSE);

            List<RedstarCommunity> redstarCommunityList = result.getCurrentRecords();

            List<RedstarCommunity> dataList = new ArrayList<RedstarCommunity>();

            //录入率和入住率
            for (RedstarCommunity r : redstarCommunityList) {
                double occupanyRate;
                double inputRate;
                if (r.getAlreadyCheckAmount() != null && r.getRoomMount() != null && r.getRoomMount() > 0) {
                    occupanyRate = DoubleUtil.div(r.getAlreadyCheckAmount(), r.getRoomMount(), 2);
                    r.setOccupanyRate(RateUtil.getDoubleValue(occupanyRate * 100));
                } else {
                    r.setOccupanyRate(0.0);
                }
                if (r.getAlreadyInputAmount() != null && r.getRoomMount() != null && r.getRoomMount() > 0) {
                    inputRate = DoubleUtil.div(r.getAlreadyInputAmount(), r.getRoomMount(), 2);
                    r.setInputRate(RateUtil.getDoubleValue(inputRate * 100));
                } else {
                    r.setInputRate(0.0);
                }
                dataList.add(r);
            }
            ((SimplePaginationDescribe) result).setCurrentRecords(dataList);
            setSuccessMsg(res);
            res.addKey("result", result);
        } catch (Exception e) {
            setUnknowException(e, res);
        }
        return res;
    }


    //创建小区列表
    @RequestMapping(value = "/create-list")
    @ResponseBody
    public Response createList() {
        PipelineContext pipelineContext = this.buildPipelineContent();
        Response res = pipelineContext.getResponse();
        Request req = pipelineContext.getRequest();
        try {
            //页数
            Integer page = req.getInt("page");
            //每页记录数
            Integer pageSize = req.getInt("pageSize");

            if (page == 0) {
                page = Page_Default;
            }
            if (pageSize == 0) {
                pageSize = PageSize_Default;
            }

            //当前登录用户
            Employee employee = getEmployeeromSession();

            IntSearch thisSearch = new IntSearch("createEmployeeId");
            thisSearch.setSearchValue(String.valueOf(employee.getId()));

            PaginationDescribe<RedstarCommunity> result = (PaginationDescribe<RedstarCommunity>) dispatchDriver.getRedstarCommunityManager().searchBeanPage(page, pageSize, thisSearch, "createDate", Boolean.FALSE);

            List<RedstarCommunity> redstarCommunityList = result.getCurrentRecords();

            List<RedstarCommunity> dataList = new ArrayList<RedstarCommunity>();

            //录入率和入住率
            for (RedstarCommunity r : redstarCommunityList) {
                double occupanyRate;
                double inputRate;
                if (r.getAlreadyCheckAmount() != null && r.getRoomMount() != null && r.getRoomMount() > 0) {
                    occupanyRate = DoubleUtil.div(r.getAlreadyCheckAmount(), r.getRoomMount(), 2);
                    r.setOccupanyRate(RateUtil.getDoubleValue(occupanyRate * 100));
                } else {
                    r.setOccupanyRate(0.0);
                }
                if (r.getAlreadyInputAmount() != null && r.getRoomMount() != null && r.getRoomMount() > 0) {
                    inputRate = DoubleUtil.div(r.getAlreadyInputAmount(), r.getRoomMount(), 2);
                    r.setInputRate(RateUtil.getDoubleValue(inputRate * 100));
                } else {
                    r.setInputRate(0.0);
                }
                dataList.add(r);
            }
            ((SimplePaginationDescribe) result).setCurrentRecords(dataList);
            res.setMessage("查询成功");
            res.setCode(HTTP_SUCCESS_CODE);
            res.addKey("result", result);
        } catch (Exception e) {
            e.printStackTrace();
            setErrMsg(res, "服务器响应异常");
        }
        return res;
    }


    //删除
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Response delete() {
        PipelineContext pipelineContext = this.buildPipelineContent();
        Response res = pipelineContext.getResponse();
        Request req = pipelineContext.getRequest();
        try {
            //小区id
            int id = req.getInt("id");
            if (id <= 0) {
                setErrMsg(res, "参数缺失");
                return res;
            }
            //当前登录用户
            Employee employee = getEmployeeromSession();

            RedstarCommunity community = (RedstarCommunity) dispatchDriver.getRedstarCommunityManager().getBean(id);

            if (community == null) {
                setErrMsg(res, "不存在的小区,不允许删除");
                return res;
            }

            if (employee.getId() != community.getCreateEmployeeId()) {
                setErrMsg(res, "非小区创建人,不允许删除");
                return res;
            }

            IntSearch communityIdSearch = new IntSearch(Query_Column_CommunityId);
            communityIdSearch.setSearchValue(String.valueOf(id));

            List<RedStarMember> memberList = redstarCommonManager.getDataList(RedStarMember.class, communityIdSearch);
            if (!CollectionUtils.isEmpty(memberList)) {
                setErrMsg(res, "小区下存在住户,不允许删除");
                return res;
            }

            Integer ownerId = community.getOwnerId();
            Date createDate = community.getCreateDate();

            community.setOwnerMallId(0);
            community.setOwnerMallName(null);
            community.setOwnerId(0);
            community.setOwnerXingMing(null);

            community.setCreateEmployeeId(0);
            community.setCreateXingMing(null);
            community.setCreateDate(null);

            community.setUpdateEmployeeId(0);
            community.setCreateXingMing(null);
            community.setUpdateDate(null);
            dispatchDriver.getRedstarCommunityManager().updateBean(community);

            DateFormat dateFormat = new SimpleDateFormat(Default_Date_Format);

            //非当天数据
            if (!dateFormat.format(new Date()).equals(dateFormat.format(createDate))) {
                //存在ownerId
                if (ownerId != null && ownerId > 0) {

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(createDate);

                    //员工id
                    IntSearch employeeIdSearch = new IntSearch(Query_Column_EmployeeId);
                    employeeIdSearch.setSearchValue(String.valueOf(ownerId));

                    //员工日报
                    MultiSearchBean multiSearchBean = DayDataSearchUtil.getMultiSearchBean(calendar, Report_Type_Day);
                    multiSearchBean.addSearchBean(employeeIdSearch);
                    DayDataSearchUtil.reduceEmployeeDayInput(redstarCommonManager, multiSearchBean, Report_Reduce_Community);

                    //员工月报
                    MultiSearchBean monthMultiSearchBean = DayDataSearchUtil.getMultiSearchBean(calendar, Report_Type_Month);
                    monthMultiSearchBean.addSearchBean(employeeIdSearch);
                    DayDataSearchUtil.reduceEmployeeMonthInput(redstarCommonManager, monthMultiSearchBean, Report_Reduce_Community);


                    //员工所属商场
                    List<RedstarMallEmployee> meList = redstarCommonManager.getDataList(RedstarMallEmployee.class, employeeIdSearch);

                    if (!CollectionUtils.isEmpty(meList)) {

                        RedstarMallEmployee me = meList.get(0);

                        RedstarShoppingMall redstarShoppingMall = (RedstarShoppingMall) dispatchDriver.getRedstarShoppingMallManager().getBean(me.getShoppingMallId());

                        if (Default_MallType.equals(redstarShoppingMall.getMallType())) {

                            //商场id
                            IntSearch mallIdSearch = new IntSearch(Query_Column_ShoppingMallId);
                            mallIdSearch.setSearchValue(String.valueOf(me.getShoppingMallId()));

                            //商场日报
                            MultiSearchBean mallMultiSearchBean = DayDataSearchUtil.getMultiSearchBean(calendar, Report_Type_Day);
                            mallMultiSearchBean.addSearchBean(mallIdSearch);
                            DayDataSearchUtil.reduceMallDayInput(redstarCommonManager, mallMultiSearchBean, Report_Reduce_Community);

                            //商场月报
                            MultiSearchBean mallMonthMultiSearchBean = DayDataSearchUtil.getMultiSearchBean(calendar, Report_Type_Month);
                            mallMonthMultiSearchBean.addSearchBean(mallIdSearch);
                            DayDataSearchUtil.reduceMallMonthInput(redstarCommonManager, mallMonthMultiSearchBean, Report_Reduce_Community);

                            //组织id
                            Integer organizationId = redstarShoppingMall.getOrganizationId();
                            //组织日报月报
                            if (organizationId != null && organizationId > 0) {
                                DayDataSearchUtil.reduceOrgInfo(organizationId, redstarCommonManager, dispatchDriver, calendar, Report_Reduce_Community);
                            }
                        }

                    }
                    //减去根节点数据
                    DayDataSearchUtil.reduceOrgInfo(Integer.parseInt(Default_Root_Id), redstarCommonManager, dispatchDriver, calendar, Report_Reduce_Community);
                }

            }

            //记录日志
            SecurityOperationLog securityOperationLog = new SecurityOperationLog();
            securityOperationLog.setOperatorId(employee.getId());
            securityOperationLog.setOperator(employee.getXingMing());
            securityOperationLog.setOperateResource(Community_Operate_Resource);
            securityOperationLog.setCreateDate(new Date());
            securityOperationLog.setBelongedId(LOG_BELONG_ID);
            securityOperationLog.setOperationTypeField(DELETE_OPERATION);
            //删除小区的id
            securityOperationLog.setOperateResourceId(String.valueOf(id));
            securityOperationLog.setContent("删除小区");
            dispatchDriver.getSecurityOperationLogManager().addBean(securityOperationLog);

            res.setMessage("操作成功");
            res.setCode(HTTP_SUCCESS_CODE);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            setErrMsg(res, "服务器响应异常");
        }
        return res;
    }

    @RequestMapping(value = "/recommend-list", method = RequestMethod.POST)
    @ResponseBody
    public Object recommendList() {

        PipelineContext pipelineContext = this.buildPipelineContent();
        Response res = pipelineContext.getResponse();
        Request req = pipelineContext.getRequest();


        try {
            Employee loginEmployee = this.getEmployeeromSession();
            Integer page = req.getInt(Param_Page);
            Integer pageSize = req.getInt(Param_PageSize);
            if (page <= 0) {
                page = Page_Default;
            }
            if (pageSize <= 0) {
                pageSize = PageSize_Default;
            }

            String longitude = req.getString("longitude");
            String latitude = req.getString("latitude");

            //longitude = "121.391968";
            //latitude="31.243099";

            if (StringUtil.isInvalid(latitude) || StringUtil.isInvalid(longitude)) {
                setErrMsg(res, "经纬度参数缺失");
                return res;
            }


            StringBuffer sb = new StringBuffer();

            sb.append("c.*, ");
            sb.append(" round(6378.138 * 2 * asin(  ");
            sb.append(" sqrt( pow(sin((c.latitude * pi() / 180 - ?*pi() / 180) / 2),2) + cos(c.latitude * pi() / 180) * cos(?*pi() / 180) * pow(  ");

            sb.append(" sin((c.longitude * pi() / 180 - ?*pi()/180) / 2),2))) * 1000) AS distance   ");


            String colunmSQL = sb.toString();
            String conditionSQL = "FROM xiwa_redstar_community c where c.longitude>0 and c.latitude>0 and c.createEmployeeId=0 or c.createEmployeeId=" + loginEmployee.getId();
            String orderBySql = "distance";


            List<Object> paramsList = new ArrayList<Object>();
            paramsList.add(Double.parseDouble(latitude));
            paramsList.add(Double.parseDouble(latitude));
            paramsList.add(Double.parseDouble(longitude));
            //搜索分页结果
            SimplePaginationDescribe paginationDescribe = redstarCommonManager.getIdentifiedPageList(RecommendCommunityObject.class, colunmSQL, conditionSQL, orderBySql, paramsList, page, pageSize);
            //状态转换
            List<RecommendCommunityObject> searchResult = paginationDescribe.getCurrentRecords();
            List<RecommendCommunityExtObject> extList = new ArrayList<RecommendCommunityExtObject>();
            for (RecommendCommunityObject item : searchResult) {
                RecommendCommunityExtObject extItem = new RecommendCommunityExtObject();
                extItem.setName(item.getName());
                extItem.setAddress(item.getAddress());
                extItem.setAlreadyInputAmount(item.getAlreadyInputAmount());
                extItem.setArea(item.getArea());
                extItem.setAreaCode(item.getAreaCode());
                extItem.setCity(item.getCity());
                extItem.setCityCode(item.getCityCode());
                extItem.setCreateEmployeeId(item.getCreateEmployeeId());
                extItem.setCreateXingMing(item.getCreateXingMing());
                extItem.setDistance(item.getDistance());
                extItem.setId(item.getId());
                extItem.setLatitude(item.getLatitude());
                extItem.setLongitude(item.getLongitude());
                extItem.setRoomMount(item.getRoomMount());
                extItem.setProvince(item.getProvince());
                extItem.setProvinceCode(item.getProvinceCode());

                if (item.getCreateEmployeeId() == 0) {
                    //没有创建者,可以认领
                    extItem.setStatus("toCreate");
                } else {
                    //当前员工创建,转到待完善
                    extItem.setStatus("toComplete");
                }

                extList.add(extItem);
            }
            paginationDescribe.setCurrentRecords(extList);

            res.addKey(Http_Default_Data, paginationDescribe);

            setSuccessMsg(res);
            return res;
        } catch (Exception e) {
            setExceptionMsg(res);
            logger.error(e.getMessage());
        }
        return res;

    }


    /**
     * 返回某个小区的栋单元列表
     * <p/>
     * 参数 communityId
     * 表 xiwa_redstar_community_building
     *
     * @return response
     */
    @RequestMapping(value = "/building/list", method = RequestMethod.POST)
    @ResponseBody
    public Response buildingList() {
        PipelineContext pipelineContext = buildPipelineContent();
        Response response = pipelineContext.getResponse();
        int communityId = pipelineContext.getRequest().getInt("communityId");
        //communityId 输入为空，为0，格式不符时 报错
        if (communityId == 0) {
            response.setCode(FORM_ERROR_CODE);
            response.setOk(false);
            response.setMessage("communityId输入有误");
            return response;
        }

        try {
            List<RedstarCommunityBuilding> redstarCommunityBuildings = dispatchDriver.getRedstarCommunityBuildingManager().getBeanListByColumn("communityId", communityId);
            response.addKey("redstarCommunityBuildings", redstarCommunityBuildings);

        } catch (Exception e) {
            e.printStackTrace();
            response.setCode(40901);
            response.setOk(false);
            response.setMessage(e.getMessage());
            return response;
        }
        return response;
    }


    /**
     * 添加栋
     * <p/>
     * 再某个小区下面添加一个栋单元
     * <p/>
     * 栋数据表：xiwa_redstar_community_building
     *
     * @return response
     */
    @RequestMapping(value = "/building/add", method = RequestMethod.POST)
    @ResponseBody
    public Response buildingAdd() {
        PipelineContext pipelineContext = this.buildPipelineContent();
        Response response = pipelineContext.getResponse();

        //小区ID 和 栋的姓名
        int communityId = pipelineContext.getRequest().getInt("communityId");
        String buildingName = pipelineContext.getRequest().getString("buildingName");

        int roomAmount = pipelineContext.getRequest().getInt("roomAmount");//住宅数
        int unitAmount = pipelineContext.getRequest().getInt("unitAmount");//单元数
        int floorAmount = pipelineContext.getRequest().getInt("floorAmount");//楼层数

        if(roomAmount<=0){
            setErrCodeAndMsg(response,-1001,"住宅数不能为空");
            return response;
        }
        if(unitAmount<=0){
            setErrCodeAndMsg(response,-1001,"单元数不能为空");
            return response;
        }
        if(floorAmount<=0){
            setErrCodeAndMsg(response,-1001,"楼层数不能为空");
            return response;
        }
        if (communityId == 0) {
            response.setCode(FORM_ERROR_CODE);
            response.setOk(false);
            response.setMessage("communityId不能为空");
            return response;
        }

        if (StringUtils.isBlank(buildingName)) {
            response.setOk(false);
            response.setMessage("buildingName不能为空");
            response.setCode(FORM_ERROR_CODE);
            return response;
        }

        RedstarCommunityBuilding redstarCommunityBuilding = new RedstarCommunityBuilding();

        redstarCommunityBuilding.setCommunityId(communityId);
        redstarCommunityBuilding.setBuildingName(buildingName);
        redstarCommunityBuilding.setRoomAmount(roomAmount);
        redstarCommunityBuilding.setUnitAmount(unitAmount);
        redstarCommunityBuilding.setFloorAmount(floorAmount);

        try {
            //Employee employee = null;    // 获取session中的登陆用户的employeeCode
            Employee employee = getEmployeeromSession();

            //用户ID,姓名，创建时间
            redstarCommunityBuilding.setCreateEmployeeId(employee.getId());
            redstarCommunityBuilding.setCreateXingMing(employee.getXingMing());
            redstarCommunityBuilding.setCreateDate(new Date());

            //添加进栋列表
            int buildingId = dispatchDriver.getRedstarCommunityBuildingManager().addBean(redstarCommunityBuilding);
            response.addKey("buildingId", buildingId);
            response.setMessage("添加成功");
            response.setOk(true);
            response.setCode(HTTP_SUCCESS_CODE);
        } catch (Exception exception) {
            exception.printStackTrace();
            response.setCode(40101);
            response.setOk(false);
            response.setMessage(exception.getMessage());
            return response;

        }

        return response;
    }


    //编辑栋信息
    @RequestMapping(value = "/building/update", method = RequestMethod.POST)
    @ResponseBody
    public Response buildingUpdate()  {
        PipelineContext pipelineContext = this.buildPipelineContent();
        Response response = pipelineContext.getResponse();

        Integer dataId = pipelineContext.getRequest().getInt("id");

        if(dataId<=0){
            setErrCodeAndMsg(response,-1001,"数据id缺失");
            return response;
        }

        //小区ID 和 栋的姓名

        String buildingName = pipelineContext.getRequest().getString("buildingName");
        int roomAmount = pipelineContext.getRequest().getInt("roomAmount");//住宅数
        int unitAmount = pipelineContext.getRequest().getInt("unitAmount");//单元数
        int floorAmount = pipelineContext.getRequest().getInt("floorAmount");//楼层数

        if(roomAmount<=0){
            setErrCodeAndMsg(response,-1001,"住宅数不能为空");
            return response;
        }
        if(unitAmount<=0){
            setErrCodeAndMsg(response,-1001,"单元数不能为空");
            return response;
        }
        if(floorAmount<=0){
            setErrCodeAndMsg(response,-1001,"楼层数不能为空");
            return response;
        }

        if (StringUtils.isBlank(buildingName)) {
            setErrCodeAndMsg(response,-1001,"buildingName不能为空");
            return response;
        }

        try {

            RedstarCommunityBuilding redstarCommunityBuilding = (RedstarCommunityBuilding) dispatchDriver.getRedstarCommunityBuildingManager().getBean(dataId);

            if (redstarCommunityBuilding==null){
                setErrCodeAndMsg(response,-1001,"当前数据不存在");
                return response;
            }

            redstarCommunityBuilding.setBuildingName(buildingName);
            redstarCommunityBuilding.setRoomAmount(roomAmount);
            redstarCommunityBuilding.setUnitAmount(unitAmount);
            redstarCommunityBuilding.setFloorAmount(floorAmount);

            //添加进栋列表
            dispatchDriver.getRedstarCommunityBuildingManager().updateBean(redstarCommunityBuilding);
            response.setMessage("更新成功");
            response.setOk(true);
            response.setCode(HTTP_SUCCESS_CODE);
        } catch (Exception exception) {
            exception.printStackTrace();
            response.setCode(40101);
            response.setOk(false);
            response.setMessage(exception.getMessage());
            return response;
        }

        return response;
    }



    /**
     * 根据栋单元ID删除栋单元数据
     * <p/>
     * 只能删除自己创建的栋/单元
     * 删除栋单元前查询该栋下面有无住宅，有则不允许删除
     * 是否有住宅查询表: xiwa_redstar_member
     *
     * @return response
     */
    @RequestMapping(value = "/building/delete", method = RequestMethod.POST)
    @ResponseBody
    public Response buildingDelete() {
        PipelineContext pipelineContext = buildPipelineContent();
        Response response = pipelineContext.getResponse();
        String idStr = pipelineContext.getRequest().getString("ids");
        int communityId = pipelineContext.getRequest().getInt("communityId");

        try {
            //1.判断 栋ID 、 communityId 是否为空
            if (StringUtils.isBlank(idStr)) {
                throw new BusinessException(CommonExceptionType.ParameterError);
            }
            if (communityId == 0) {
                throw new BusinessException(CommonExceptionType.ParameterError);
            }

            // 获取session中的登陆用户的employeeCode
            Employee employee = getEmployeeromSession();
            if (employee == null) {
                throw new BusinessException(CommonExceptionType.NoSession);
            }

            //2.截取，获得string 数组
            String[] deleteArray = idStr.split(",");
            String strIds = "";      //*****
            //.获取ids数组
            if (deleteArray.length < 1) {
                throw new BusinessException(CommonExceptionType.ParameterError);
            }
            for (int i = 0; i < deleteArray.length; i++) {
                //获取集合的值 下标从0开始
                int id = DataUtil.getInt(deleteArray[i], 0);    //如果数组中有非数字时，这个方法直接将其排除掉了

                //判断是否符合删除条件，如果不符合，continue
                if (id <= 0) {  //ID为非整形数 和 0
                    continue;
                }

                try {
                    //封装查询参数
                    MultiSearchBean multiSearchBean = new MultiSearchBean();
                    IntSearch idIntSearch = new IntSearch("id");
                    idIntSearch.setSearchValue(String.valueOf(id));
                    multiSearchBean.addSearchBean(idIntSearch);
                    IntSearch communityIdIntSearch = new IntSearch("communityId");
                    communityIdIntSearch.setSearchValue(String.valueOf(communityId));
                    multiSearchBean.addSearchBean(communityIdIntSearch);

                    int createEmployeeId = employee.getId();

                    IntSearch createEmployeeIdSearch = new IntSearch("createEmployeeId");
                    createEmployeeIdSearch.setSearchValue(String.valueOf(createEmployeeId));
                    multiSearchBean.addSearchBean(createEmployeeIdSearch);

                    //3.根据communityID 和 创建人ID 查询是否存在该栋 xiwa_redstar_community_building
                    List<RedstarCommunityBuilding> redstarCommunityBuildings = dispatchDriver.getRedstarCommunityBuildingManager().searchIdentify(multiSearchBean);

                    //判断如果存在该栋
                    if (redstarCommunityBuildings.size() > 0) {    //存在该栋
                        //3.1. 存在该栋   根据buildingID查找 单元表 xiwa_redstar_community_unit
                        for (RedstarCommunityBuilding redstarCommunityBuilding : redstarCommunityBuildings) {
                            int buildingId = redstarCommunityBuilding.getId();
                            IntSearch buildingIdSearch = new IntSearch("buildingId");
                            buildingIdSearch.setSearchValue(String.valueOf(buildingId));

                            //根据buildingID查找 该栋下是否存在unit
                            List<RedstarCommunityUnit> redstarCommunityUnits = redstarCommonManager.getDataList(RedstarCommunityUnit.class, buildingIdSearch);
                            if (redstarCommunityUnits.size() > 0) { //该栋下有单元存在
                                response.setOk(false);
                                response.setCode(DataUtil.getInt(CommunityExceptionType.DeleteInUse.getCode(), 0));
                                response.setMessage(CommunityExceptionType.DeleteInUse.getMessage());
                                continue;
                            }
                            if (id != 0) {
                                //下标i循环到最后一个时 得到栋存在的字符串
                                if (i + 1 == deleteArray.length) {
                                    strIds += id;
                                } else {
                                    strIds += id + ",";
                                }
                            }
                        }

                    } else {
                        continue;

                    }

                } catch (Exception exception) {
                    exception.printStackTrace();
                    response.setCode(401101);
                    response.setMessage(exception.getMessage());
                    response.setOk(false);
                    return response;
                }

            }


            if (StringUtil.isValid(strIds)) {
                int ids[] = new int[strIds.split(",").length];
                for (int i = 0; i < strIds.split(",").length; i++) {
                    ids[i] = Integer.parseInt(strIds.split(",")[i]);
                }
                if (ids.length == 0) {
                    throw new BusinessException(CommonExceptionType.DeleteNoExist);
                }
                //删除
                dispatchDriver.getRedstarCommunityBuildingManager().removeBeans(ids);
                setSuccessMsg(response, "删除成功");
            } else {
                throw new BusinessException(CommonExceptionType.ParameterError);
            }

        } catch (BusinessException e) {
            setBusinessException(e, response);
        } catch (Exception e) {
            setUnknowException(e, response);
        }
        return response;
    }


    /**
     * ****************分割线**********************************
     */

//    @RequestMapping(value = "/building/copy")
//    @ResponseBody
//    public Object buildingCopy(String ids) {
//
//        PipelineContext pipelineContext = this.buildPipelineContent();
//        Response res = pipelineContext.getResponse();
//        try {
//
//            Employee employee = getEmployeeromSession();
//
//            if (StringUtil.isInvalid(ids)) {
//                throw new BusinessException(CommonExceptionType.ParameterError);
//            }
//
//            String[] strArr = ids.split(",");
//
//            List<Object> idList = new ArrayList<Object>();
//
//            for (String str : strArr) {
//                idList.add(Integer.parseInt(str));
//            }
//
//            //栋信息
//            List<RedstarCommunityBuilding> buildingList = redstarCommonManager.getDataList(idList, RedstarCommunityBuilding.class, null, null, null);
//
//            if (!CollectionUtils.isEmpty(buildingList)) {
//                //单元信息
//                for (RedstarCommunityBuilding b : buildingList) {
//
//                    RedstarCommunityBuilding thisBuilding = new RedstarCommunityBuilding();
//
//                    thisBuilding.setBuildingName(b.getBuildingName() + "副本");
//                    thisBuilding.setCommunityId(b.getCommunityId());
//                    thisBuilding.setCommunityName(b.getCommunityName());
//                    thisBuilding.setCreateDate(new Date());
//                    thisBuilding.setCreateEmployeeId(employee.getId());
//                    thisBuilding.setCreateXingMing(employee.getXingMing());
//
//                    //复制栋信息
//                    Integer buildingId = dispatchDriver.getDispatchCommunityBuildingManager().addBean(thisBuilding);
//
//                    IntSearch intSearch = new IntSearch("buildingId");
//                    intSearch.setSearchValue(String.valueOf(b.getId()));
//                    //单元列表
//                    List<RedstarCommunityUnit> unitList = redstarCommonManager.getDataList(RedstarCommunityUnit.class, intSearch);
//
//                    if (!CollectionUtils.isEmpty(unitList)) {
//
//                        for (RedstarCommunityUnit unit : unitList) {
//                            //复制单元
//                            RedstarCommunityUnit thisUnit = new RedstarCommunityUnit();
//                            thisUnit.setBuildingId(buildingId);
//                            thisUnit.setBuildingName(unit.getBuildingName() + "副本");
//                            thisUnit.setUnitName(unit.getUnitName());
//                            thisUnit.setCommunityId(unit.getCommunityId());
//                            thisUnit.setCommunityName(unit.getCommunityName());
//                            thisUnit.setCreateXingMing(employee.getXingMing());
//                            thisUnit.setCreateEmployeeId(employee.getId());
//                            thisUnit.setCreateDate(new Date());
//                            Integer unitId = redstarCommunityUnitManager.addBean(thisUnit);
//
//                            //查找单元下的住户
//
//                            IntSearch unitIdSearch = new IntSearch("unitId");
//                            unitIdSearch.setSearchValue(String.valueOf(unit.getId()));
//
//                            List<RedStarMember> memberList = redstarCommonManager.getDataList(RedStarMember.class, unitIdSearch);
//
//                            if (!CollectionUtils.isEmpty(memberList)) {
//                                //复制住户
//                                for (RedStarMember member : memberList) {
//
//                                    RedStarMember redStarMember = new RedStarMember();
//
//                                    //上一步创建的unitId buildingId
//                                    redStarMember.setBuildingId(buildingId);
//                                    redStarMember.setUnitId(unitId);
//
//                                    redStarMember.setCommunityId(member.getCommunityId());
//                                    redStarMember.setCommunityName(member.getCommunityName());
//                                    redStarMember.setBuildingName(b.getBuildingName() + "副本");
//                                    redStarMember.setUnitName(unit.getUnitName());
//                                    redStarMember.setRoom(member.getRoom());
//
//                                    redstarCommonManager.addData(redStarMember);
//                                }
//                            }
//
//                        }
//
//                    }
//                }
//            }
//
//            setSuccessMsg(res, "信息复制成功");
//            return res;
//        } catch (BusinessException e) {
//            setBusinessException(e, res);
//        } catch (Exception e) {
//            setUnknowException(e, res);
//        }
//        return res;
//
//    }


}
