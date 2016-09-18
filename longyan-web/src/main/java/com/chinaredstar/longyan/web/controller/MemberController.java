package com.chinaredstar.longyan.web.controller;


import com.chinaredstar.commonBiz.bean.*;
import com.chinaredstar.longyan.bean.StorageConfig;
import com.chinaredstar.longyan.bean.bo.UnitRoomObject;
import com.chinaredstar.longyan.util.DayDataSearchUtil;
import com.chinaredstar.longyan.util.PageUtil;
import com.chinaredstar.commonBiz.bean.constant.CommonBizConstant;
import com.chinaredstar.commonBiz.manager.DispatchDriver;
import com.chinaredstar.commonBiz.manager.RedstarCommonManager;
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
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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


@RequestMapping("/member")
@Controller
public class MemberController extends BaseController implements CommonBizConstant {

    @Autowired
    private DispatchDriver dispatchDriver;
    @Autowired
    private StorageConfig storageConfig;
    @Autowired
    private RedstarCommonManager redstarCommonManager;

/*    //查询住户列表
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

            Integer offSet = (page-1)*pageSize;

            Employee employee = getEmployeeromSession();
            IntSearch ownerIdSearch = new IntSearch("ownerId");
            ownerIdSearch.setSearchValue(String.valueOf(employee.getId()));
            PaginationDescribe<RedStarMember> result = dispatchDriver.getRedStarMemberManager().searchBeanPage(page, pageSize, ownerIdSearch, "updateDate", Boolean.FALSE);
            List<RedStarMember> memberList  =   result.getCurrentRecords();

            Set<Object> set = new HashSet<Object>();
            List<RedStarMember> dataList  = new ArrayList<RedStarMember>();
            if(!CollectionUtils.isEmpty(memberList)){
                for (RedStarMember redStarMember:memberList){
                    set.add(redStarMember.getCommunityId());
                }
                List<RedstarCommunity> communityList =  dispatchDriver.getRedstarCommunityManager().getDataList(set,null,null,0,pageSize);
                for (RedStarMember redStarMember:memberList){
                    for (RedstarCommunity redstarCommunity:communityList){
                        if (redStarMember.getCommunityId()==redstarCommunity.getId()){
                            redStarMember.setCommunityAddress(redstarCommunity.getAddress());
                            dataList.add(redStarMember);
                            break;
                        }
                    }
                }
            }
            ((SimplePaginationDescribe) result).setCurrentRecords(dataList);
            res.setCode(HTTP_SUCCESS_CODE);
            res.addKey("result", result);
        } catch (Exception e) {
            setErrMsg(res, "没有数据");
            logger.error(e.getMessage());
        }
        return res;
    }*/

    //查询住户列表
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

            if (page <= 0) {
                page = Page_Default;
            }

            if (pageSize <= 0) {
                pageSize = PageSize_Default;
            }

            Employee employee = getEmployeeromSession();
            if (employee == null) {
                throw new RuntimeException("LoginTimeOut");
            }

            //查询参数
            List<Object> paramList = new ArrayList<Object>();
            paramList.add(employee.getId());

            String sql = "SELECT m.*, c.address communityAddress FROM xiwa_redstar_member m JOIN xiwa_redstar_community c ON m.communityId = c.id WHERE m.ownerId=? ORDER BY c.updateDate DESC";
            String countSql = "SELECT count(*) FROM xiwa_redstar_member WHERE ownerId=?";

            Map<String, Object> dataMap = redstarCommonManager.queryBySql(sql, countSql, paramList, ExtRedstarMember.class, page, pageSize);
            Integer dataCount = Integer.parseInt(dataMap.get("dataCount").toString());
            List dataList = (List) dataMap.get("dataList");

            res.setCode(HTTP_SUCCESS_CODE);
            res.addKey("result", PageUtil.getSimplePagination(page, pageSize, dataCount, dataList));
        } catch (Exception e) {
            e.printStackTrace();
            setErrMsg(res, "没有数据");
            logger.error(e.getMessage());
        }
        return res;
    }

    //查询住户
    @RequestMapping(value = "/{id}")
    @ResponseBody
    public Response dataItem(@PathVariable("id") Integer id) {
        PipelineContext pipelineContext = this.buildPipelineContent();
        Response res = pipelineContext.getResponse();
        try {
            RedStarMember member = (RedStarMember) dispatchDriver.getRedStarMemberManager().getBean(id);
            RedstarCommunity redstarCommunity = (RedstarCommunity) dispatchDriver.getRedstarCommunityManager().getBean(member.getCommunityId());
            member.setCommunityAddress(redstarCommunity.getAddress());
            res.addKey("roomMount", redstarCommunity.getRoomMount());
            res.addKey("alreadyInputAmount", redstarCommunity.getAlreadyInputAmount());
            res.addKey("member", member);
        } catch (Exception e) {
            setErrMsg(res, "没有数据");
            logger.error(e.getMessage());
        }
        return res;
    }

    //添加住户
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Response dataCreate() {
        PipelineContext pipelineContext = this.buildPipelineContent();
        Request request = pipelineContext.getRequest();
        Response res = pipelineContext.getResponse();
        RedStarMember member = new RedStarMember();
        try {
            String provinceCode = request.getString("provinceCode");
            if (StringUtil.isValid(provinceCode)) {
                member.setProvinceCode(provinceCode);
            }
            //映射出省名称
            DispatchProvince province = dispatchDriver.getDispatchProvinceManager().getProvince(provinceCode);
            if (province == null) {
                setErrMsg(res, "省不存在");
                return res;
            }
            member.setProvince(province.getProvince());

            String cityCode = request.getString("cityCode");
            if (StringUtil.isValid(cityCode)) {
                member.setCityCode(cityCode);
            }
            //映射出市名称
            DispatchCity city = dispatchDriver.getDispatchCityManager().getCity(cityCode);
            if (city == null) {
                setErrMsg(res, "市不存在");
                return res;
            }
            member.setCity(city.getCity());

            String areaCode = request.getString("areaCode");
            if (StringUtil.isValid(areaCode)) {
                member.setAreaCode(areaCode);
            }
            //映射出区名称
            DispatchLocation location = dispatchDriver.getDispatchLocationManager().getLoction(areaCode);
            if (location == null) {
                setErrMsg(res, "区不存在");
                return res;
            }
            member.setArea(location.getArea());


            String building = request.getString("building");
            if (StringUtil.isInvalid(building)) {
                setErrMsg(res, "苑/幢/栋没有填写");
                return res;
            }
            member.setBuilding(building);


            TextSearch unitSearch = null;
            String unit = request.getString("unit");
            if (StringUtil.isValid(unit)) {
                member.setUnit(unit);
                unitSearch = new TextSearch("unit");
                unitSearch.setSearchValue(unit);
            }



          /*  //住户单元
            String unit = request.getString("unit");
            if (StringUtil.isInvalid(unit)) {
                setErrMsg(res, "单元没有填写");
                return res;
            }
            member.setUnit(unit);*/

            //住户室
            String room = request.getString("room");
            if (StringUtil.isInvalid(room)) {
                setErrMsg(res, "室没有填写");
                return res;
            }
            member.setRoom(room.trim());

            String communityId = request.getString("communityId");
            if (StringUtil.isInvalid(communityId)) {
                setErrMsg(res, "小区名称没有填写");
                return res;
            }

            RedstarCommunity community = (RedstarCommunity) dispatchDriver.getRedstarCommunityManager().getBean(Integer.parseInt(communityId));

            if (null == community) {
                setErrMsg(res, "数据异常");
                return res;
            }


            MultiSearchBean multiSearchBean = new MultiSearchBean();

            IntSearch idSearch = new IntSearch("communityId");
            idSearch.setSearchValue(String.valueOf(community.getId()));
            multiSearchBean.addSearchBean(idSearch);

            TextSearch provinceCodeSearch = new TextSearch("provinceCode");
            provinceCodeSearch.setSearchValue(province.getProvinceCode());
            multiSearchBean.addSearchBean(provinceCodeSearch);

            TextSearch cityCodeSearch = new TextSearch("cityCode");
            cityCodeSearch.setSearchValue(city.getCityCode());
            multiSearchBean.addSearchBean(cityCodeSearch);

            TextSearch areaCodeSearch = new TextSearch("areaCode");
            areaCodeSearch.setSearchValue(location.getAreaCode());
            multiSearchBean.addSearchBean(areaCodeSearch);

            TextSearch buildingSearch = new TextSearch("building");
            buildingSearch.setSearchValue(building);
            multiSearchBean.addSearchBean(buildingSearch);


            TextSearch roomSearch = new TextSearch("room");
            roomSearch.setSearchValue(room.trim());
            multiSearchBean.addSearchBean(roomSearch);

            if (null != unitSearch) {
                multiSearchBean.addSearchBean(unitSearch);
            }

            if (!CollectionUtils.isEmpty(dispatchDriver.getRedStarMemberManager().searchIdentify(multiSearchBean))) {
                setErrMsg(res, "该住宅已存在，请输入其他住宅");
                return res;
            }


            member.setCommunityName(community.getName());
            member.setCommunityId(Integer.parseInt(communityId));
            //装修情况
            Integer renovationStatusId = request.getInt("renovationStatusId");
            if (renovationStatusId != 0) {
                RedstarRenovationStatus redstarRenovationStatus = (RedstarRenovationStatus) redstarCommonManager.getDataById(renovationStatusId, RedstarRenovationStatus.class);
                if (redstarRenovationStatus == null) {
                    setErrMsg(res, "数据异常");
                    return res;
                }
                member.setRenovationStatusId(redstarRenovationStatus.getId());
                member.setRenovationStatus(redstarRenovationStatus.getName());
            } else {
                setErrMsg(res, "请填写装修情况");
                return res;
            }


            Integer roomTypeId = request.getInt("roomTypeId");
            if (roomTypeId != 0) {
                RedstarRoomType redstarRoomType = (RedstarRoomType) redstarCommonManager.getDataById(roomTypeId, RedstarRoomType.class);
                if (redstarRoomType != null) {
                    member.setRoomType(redstarRoomType.getName());
                    member.setRoomTypeId(redstarRoomType.getId());
                }
            }

            Integer hallAmount = request.getInt("hallAmount");
            member.setHallAmount(hallAmount);

            Integer bedroomAmount = request.getInt("bedroomAmount");
            member.setBedroomAmount(bedroomAmount);

            Double housingAreaAmount = request.getDouble("housingAreaAmount");
            if (housingAreaAmount > 99999) {
                setErrMsg(res, "面积填写有误");
                return res;
            } else {
                member.setHousingAreaAmount(housingAreaAmount);
            }

/*            Integer roomLayoutId = request.getInt("roomLayoutId");
            if (roomLayoutId != 0) {
                RedstarRoomLayout redstarRoomLayout = (RedstarRoomLayout) redstarCommonManager.getDataById(roomLayoutId, RedstarRoomLayout.class);
                if (redstarRoomLayout != null) {
                    member.setRoomLayout(redstarRoomLayout.getName());
                    member.setRoomLayoutId(redstarRoomLayout.getId());
                }
            }*/


/*            Integer areaAmountId = request.getInt("areaAmountId");
            if (areaAmountId != 0) {
                RedstarRoomArea redstarRoomArea = (RedstarRoomArea) redstarCommonManager.getDataById(areaAmountId, RedstarRoomArea.class);
                if (areaAmountId != null) {
                    member.setAreaAmount(redstarRoomArea.getName());
                    member.setAreaAmountId(redstarRoomArea.getId());
                }
            }*/


            Employee employee = getEmployeeromSession();
            SecurityOperationLog securityOperationLog = new SecurityOperationLog();
            if (employee != null) {
                member.setOwnerId(employee.getId());
                member.setOwnerXingMing(employee.getXingMing());
                member.setCreateEmployeeId(employee.getId());
                member.setCreateXingming(employee.getXingMing());
                //操作日志
                securityOperationLog.setOperatorId(employee.getId());
                securityOperationLog.setOperator(employee.getXingMing());
            }

            member.setCreateDate(new Date());
            member.setUpdateDate(new Date());
            member.setSource("employee");
            Integer dataId = dispatchDriver.getRedStarMemberManager().addBean(member);
            securityOperationLog.setOperateResource(Member_Operate_Resource);
            securityOperationLog.setCreateDate(new Date());
            securityOperationLog.setBelongedId(LOG_BELONG_ID);
            securityOperationLog.setOperationTypeField(ADD_OPERATION);
            securityOperationLog.setOperateResourceId(String.valueOf(dataId));
            securityOperationLog.setContent("添加住户");

            //更新录入数量

            community.setAlreadyInputAmount(community.getAlreadyInputAmount() == null ? 1 : community.getAlreadyInputAmount() + 1);
            dispatchDriver.getRedstarCommunityManager().updateBean(community);
            dispatchDriver.getSecurityOperationLogManager().addBean(securityOperationLog);
            res.addKey("id", dataId);
            res.setMessage("操作成功");
            res.setCode(HTTP_SUCCESS_CODE);
        } catch (Exception e) {
            setErrMsg(res, "数据添加失败");
            logger.error(e.getMessage());
        }
        return res;
    }


    //更新住户
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Response dataUpdate() {
        PipelineContext pipelineContext = this.buildPipelineContent();
        Request request = pipelineContext.getRequest();
        Response res = pipelineContext.getResponse();

        try {
            Integer dataId = request.getInt("id", 0);
            if (dataId < 1) {
                setErrMsg(res, "住户id没有填写");
                return res;
            }
            RedStarMember member = (RedStarMember) dispatchDriver.getRedStarMemberManager().getBean(dataId);
            if (member == null) {
                setErrMsg(res, "没有找到住户");
                return res;
            }

            //更新社区信息
//            String communityId = request.getString("communityId");
//            if (StringUtil.isInvalid(communityId)) {
//                setErrMsg(res, "小区名称没有填写");
//                return res;
//            }
//            RedstarCommunity community = (RedstarCommunity) dispatchDriver.getRedstarCommunityManager().getBean(DataUtil.getInt(communityId, 0));
//            member.setCommunityName(community.getName());
//            member.setCommunityId(DataUtil.getInt(communityId, 0));

            //苑 栋 单元 室 信息更新
            String building = request.getString("building");
            if (StringUtil.isValid(building)) {
                member.setBuilding(building);
            }
            //住户单元
            String unit = request.getString("unit");
            if (StringUtil.isValid(unit)) {
                member.setUnit(unit);
            }
            //住户室
            String room = request.getString("room");
            if (StringUtil.isValid(room)) {
                member.setRoom(room);
            }

            //装修情况
            Integer renovationStatusId = request.getInt("renovationStatusId");
            if (renovationStatusId != 0) {
                RedstarRenovationStatus redstarRenovationStatus = (RedstarRenovationStatus) redstarCommonManager.getDataById(renovationStatusId, RedstarRenovationStatus.class);
                if (redstarRenovationStatus != null) {
                    member.setRenovationStatusId(redstarRenovationStatus.getId());
                    member.setRenovationStatus(redstarRenovationStatus.getName());
                }
            }

            Integer roomTypeId = request.getInt("roomTypeId");
            if (roomTypeId != 0) {
                RedstarRoomType redstarRoomType = (RedstarRoomType) redstarCommonManager.getDataById(roomTypeId, RedstarRoomType.class);
                if (redstarRoomType != null) {
                    member.setRoomType(redstarRoomType.getName());
                    member.setRoomTypeId(redstarRoomType.getId());
                }
            }


            Integer hallAmount = request.getInt("hallAmount");
            if (hallAmount == 0) {
                member.setHallAmount(member.getHallAmount());
            } else {
                member.setHallAmount(hallAmount);
            }

            Integer bedroomAmount = request.getInt("bedroomAmount");
            if (bedroomAmount == 0) {
                member.setBedroomAmount(member.getBedroomAmount());
            } else {
                member.setBedroomAmount(bedroomAmount);
            }

            Double housingAreaAmount = request.getDouble("housingAreaAmount");
            if (housingAreaAmount == 0.0) {
                member.setHousingAreaAmount(member.getHousingAreaAmount());
            }
            if (housingAreaAmount > 99999) {
                setErrMsg(res, "面积填写有误");
                return res;
            } else {
                member.setHousingAreaAmount(housingAreaAmount);
            }

/*
            Integer roomLayoutId = request.getInt("roomLayoutId");
            if (roomLayoutId != 0) {
                RedstarRoomLayout redstarRoomLayout = (RedstarRoomLayout) redstarCommonManager.getDataById(roomLayoutId, RedstarRoomLayout.class);
                if (redstarRoomLayout != null) {
                    member.setRoomLayout(redstarRoomLayout.getName());
                    member.setRoomLayoutId(redstarRoomLayout.getId());
                }
            }


            Integer areaAmountId = request.getInt("areaAmountId");
            if (areaAmountId != 0) {
                RedstarRoomArea redstarRoomArea = (RedstarRoomArea) redstarCommonManager.getDataById(areaAmountId, RedstarRoomArea.class);
                if (areaAmountId != null) {
                    member.setAreaAmount(redstarRoomArea.getName());
                    member.setAreaAmountId(redstarRoomArea.getId());
                }
            }
*/


            Employee employee = getEmployeeromSession();
            SecurityOperationLog securityOperationLog = new SecurityOperationLog();
            if (employee != null) {
                member.setUpdateEmployeeId(employee.getId());
                member.setUpdateEmployeeXingMing(employee.getXingMing());

                securityOperationLog.setOperatorId(employee.getId());
                securityOperationLog.setOperator(employee.getXingMing());
            }

            member.setUpdateDate(new Date());
            dispatchDriver.getRedStarMemberManager().updateBean(member);

            securityOperationLog.setOperateResource(Member_Operate_Resource);
            securityOperationLog.setCreateDate(new Date());
            securityOperationLog.setBelongedId(LOG_BELONG_ID);
            securityOperationLog.setOperationTypeField(UPDATE_OPERATION);
            securityOperationLog.setOperateResourceId(String.valueOf(dataId));
            securityOperationLog.setContent("更新住户");
            dispatchDriver.getSecurityOperationLogManager().addBean(securityOperationLog);
            res.setMessage("操作成功");
            res.setCode(HTTP_SUCCESS_CODE);
        } catch (Exception e) {
            setErrMsg(res, "住户更新失败");
            logger.error(e.getMessage());
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
            //住户id
            int id = req.getInt("id");
            if (id <= 0) {
                setErrMsg(res, "参数缺失");
                return res;
            }
            //当前登录用户
            Employee employee = getEmployeeromSession();

            RedStarMember member = (RedStarMember) dispatchDriver.getRedStarMemberManager().getBean(id);

            if (member == null) {
                setErrMsg(res, "不存在的住户,不允许删除");
                return res;
            }

            if (employee.getId() != member.getCreateEmployeeId()) {
                setErrMsg(res, "非住户创建人,不允许删除");
                return res;
            }

            Integer ownerId = member.getOwnerId();
            Date createDate = member.getCreateDate();

            dispatchDriver.getRedStarMemberManager().removeBean(id);

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
                    DayDataSearchUtil.reduceEmployeeDayInput(redstarCommonManager, multiSearchBean, Report_Reduce_Member);

                    //员工月报
                    MultiSearchBean monthMultiSearchBean = DayDataSearchUtil.getMultiSearchBean(calendar, Report_Type_Month);
                    monthMultiSearchBean.addSearchBean(employeeIdSearch);
                    DayDataSearchUtil.reduceEmployeeMonthInput(redstarCommonManager, monthMultiSearchBean, Report_Reduce_Member);


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
                            DayDataSearchUtil.reduceMallDayInput(redstarCommonManager, mallMultiSearchBean, Report_Reduce_Member);

                            //商场月报
                            MultiSearchBean mallMonthMultiSearchBean = DayDataSearchUtil.getMultiSearchBean(calendar, Report_Type_Month);
                            mallMonthMultiSearchBean.addSearchBean(mallIdSearch);
                            DayDataSearchUtil.reduceMallMonthInput(redstarCommonManager, mallMonthMultiSearchBean, Report_Reduce_Member);

                            //组织id
                            Integer organizationId = redstarShoppingMall.getOrganizationId();
                            //组织日报月报
                            if (organizationId != null && organizationId > 0) {
                                DayDataSearchUtil.reduceOrgInfo(organizationId, redstarCommonManager, dispatchDriver, calendar, Report_Reduce_Member);
                            }
                        }
                    }

                    //减去根节点数据
                    DayDataSearchUtil.reduceOrgInfo(Integer.parseInt(Default_Root_Id), redstarCommonManager, dispatchDriver, calendar, Report_Reduce_Member);
                }

            }

            //记录日志
            SecurityOperationLog securityOperationLog = new SecurityOperationLog();
            securityOperationLog.setOperatorId(employee.getId());
            securityOperationLog.setOperator(employee.getXingMing());
            securityOperationLog.setOperateResource(Member_Operate_Resource);
            securityOperationLog.setCreateDate(new Date());
            securityOperationLog.setBelongedId(LOG_BELONG_ID);
            securityOperationLog.setOperationTypeField(DELETE_OPERATION);
            //删除住户的id
            securityOperationLog.setOperateResourceId(String.valueOf(id));
            securityOperationLog.setContent("删除住户");
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

    @RequestMapping(value = "/v2/delete", method = RequestMethod.POST)
    @ResponseBody
    public Object deleteMember(String ids) {
        PipelineContext pipelineContext = this.buildPipelineContent();
        Response res = pipelineContext.getResponse();
        try {

            Employee employee = getEmployeeromSession();
            if (employee == null) {
                throw new RuntimeException("LoginTimeOut");
            }

            if (StringUtil.isInvalid(ids)) {
                setErrMsg(res, "参数缺失");
                return res;
            }

            String[] strArr = ids.split(",");

            List<Object> idList = new ArrayList<Object>();

            for (String str : strArr) {
                idList.add(Integer.parseInt(str));
            }

            List<RedStarMember> memberList = redstarCommonManager.getDataList(idList, RedStarMember.class, null, null, null);

            for (RedStarMember member : memberList) {
                if (employee.getId() != member.getOwnerId()) {
                    setErrMsg(res, "存在非小区创建人数据");
                    return res;
                }
            }

            Set<Integer> unitList = new HashSet<Integer>();

            //全部删除
            for (RedStarMember member : memberList) {
                dispatchDriver.getRedStarMemberManager().removeBean(member.getId());
                if (DataUtil.getInt(member.getUnitId(), 0) > 0) {
                    unitList.add(member.getUnitId());
                }
            }

            //住宅单元不存在室删除单元
            for (Integer unitId : unitList) {

                IntSearch unitSearch = new IntSearch("unitId");
                unitSearch.setSearchValue(String.valueOf(unitId));

                List dataList = redstarCommonManager.getDataList(RedStarMember.class, unitSearch);

                //删除单元
                if (CollectionUtils.isEmpty(dataList)) {
                    List<Object> tempList = new ArrayList<Object>();
                    tempList.add(unitId);
                    redstarCommonManager.excuteSql("delete from xiwa_redstar_community_unit where id=?", tempList);
                }
            }

            setSuccessMsg(res, "操作成功");
            return res;

        } catch (Exception e) {
            e.printStackTrace();
            setExceptionMsg(res);
            logger.error(e.getMessage());
        }
        return res;
    }

    /**
     * 获取住宅列表
     *
     * @return
     */
    @RequestMapping(value = "/v2/list", method = RequestMethod.POST)
    @ResponseBody
    public Response list() {
        PipelineContext pipelineContext = this.buildPipelineContent();
        Response res = pipelineContext.getResponse();
        Request req = pipelineContext.getRequest();
        int id = req.getInt("id", 0);
        if (id == 0) {
            setErrMsg(res, "id不能为空");
            res.setCode(-1001);
            return res;
        }

        try {
            MultiSearchBean searchBean = new MultiSearchBean();
            IntSearch intSearch = new IntSearch("buildingId");
            intSearch.setSearchValue(String.valueOf(id));
            searchBean.addSearchBean(intSearch);
            List<RedStarMember> redstarMembers = redstarCommonManager.getDataList(RedStarMember.class, searchBean);
            res.addKey("members", redstarMembers);
            setSuccessMsg(res, "查询成功");
        } catch (ManagerException e) {
            e.printStackTrace();
        }
        return res;
    }


    @RequestMapping(value = "v2/save")
    @ResponseBody
    public Response addMember() {
        PipelineContext pipelineContext = this.buildPipelineContent();
        Response res = pipelineContext.getResponse();
        Request request = pipelineContext.getRequest();
        int communityId = request.getInt("communityId", 0);
        if (communityId == 0) {
            setErrMsg(res, "communityId不能为空");
            res.setCode(-1001);
            return res;
        }
        int buildingId = request.getInt("buildingId", 0);

        if (buildingId == 0) {
            setErrMsg(res, "buildingId不能为空");
            res.setCode(-1001);
            return res;
        }
        String name = request.getString("name");//栋名
        if (!StringUtil.isValid(name)) {
            setErrMsg(res, "name不能为空");
            res.setCode(-1001);
            return res;
        }
        String rooms = request.getString("rooms");
        if (!StringUtil.isValid(rooms)) {
            setErrMsg(res, "rooms不能为空");
            res.setCode(-1001);
            return res;
        }
        ArrayList<UnitRoomObject> unos = new ArrayList<UnitRoomObject>();
        try {
            JSONArray jsonArray = JSONArray.fromObject(rooms);
            UnitRoomObject un;
            for (int i = 0; i < jsonArray.size(); i++) {
                un = new UnitRoomObject();
                JSONObject obj = (JSONObject) jsonArray.get(i);
                if (!obj.containsKey("unitName")) {
                    setErrMsg(res, "unitName的未找到");
                    res.setCode(-1001);
                    return res;
                }
                if (obj.containsKey("unitId")) {
                    un.unitId = obj.getString("unitId");
                }
                if (obj.containsKey("unitName")) {
                    un.unitName = obj.getString("unitName");
                }
                if (obj.containsKey("rooms")) {
                    JSONArray rs = obj.getJSONArray("rooms");
                    for (int j = 0; j < rs.size(); j++) {
                        un.rooms.add(rs.getString(j));
                    }
                }
                unos.add(un);
            }
        } catch (Exception ex) {
            setErrMsg(res, "rooms必须为json格式");
            res.setCode(-1001);
            return res;
        }

        try {
            Employee employee = getEmployeeromSession();
            if (employee == null) {
                employee = new Employee();
                employee.setId(12166);
                employee.setXingMing("郭红梅");
//                throw new RuntimeException("LoginTimeOut");
            }
            List<RedstarCommunityBuilding> buildings;
            MultiSearchBean building = new MultiSearchBean();
            TextSearch buildingName = new TextSearch("buildingName");
            buildingName.setSearchValue(name);
            building.addSearchBean(buildingName);
            IntSearch communityIdSearch = new IntSearch("communityId");
            communityIdSearch.setSearchValue(String.valueOf(communityId));
            building.addSearchBean(communityIdSearch);

            RedstarCommunity community = (RedstarCommunity) redstarCommonManager.getDataById(communityId, RedstarCommunity.class);
            if (community == null) {
                setErrMsg(res, "communityId未查询到小区信息,请确认");
                res.setCode(500);
                return res;
            }
            if (buildingId == 0) {   //新增
                buildings = redstarCommonManager.getDataList(RedstarCommunityBuilding.class, building);
                if (buildings.size() > 0) {
                    setErrMsg(res, "栋名已存在");
                    res.setCode(-1001);
                    return res;
                }
                RedstarCommunityBuilding addBean = new RedstarCommunityBuilding();


                addBean.setCommunityId(communityId);
                addBean.setCommunityName(community.getName());
                addBean.setCreateDate(new Date());
                addBean.setBuildingName(name);
                addBean.setCreateEmployeeId(employee.getId());
                addBean.setCreateXingMing(employee.getXingMing());
                buildingId = redstarCommonManager.addBean(addBean);//添加栋信息
                ArrayList<RedStarMember> members = new ArrayList<RedStarMember>();
                for (UnitRoomObject uro : unos) {
                    RedstarCommunityUnit unit = new RedstarCommunityUnit();
                    unit.setUnitName(uro.unitName);
                    unit.setBuildingId(buildingId);
                    unit.setBuildingName(name);
                    unit.setCommunityId(communityId);
                    unit.setCommunityName(community.getName());
                    unit.setCreateDate(new Date());
                    unit.setCreateEmployeeId(employee.getId());
                    unit.setCreateXingMing(employee.getXingMing());
                    int unitId = redstarCommonManager.addBean(unit);
                    for (String number : uro.rooms) {
                        RedStarMember member = new RedStarMember();
                        member.setBuildingId(buildingId);
                        member.setBuildingName(name);
                        member.setCommunityId(communityId);
                        member.setCommunityName(community.getName());
                        member.setArea(community.getArea());
                        member.setAreaCode(community.getAreaCode());
                        member.setCommunityAddress(community.getAddress());
                        member.setCity(community.getCity());
                        member.setCityCode(community.getCityCode());
                        member.setCreateDate(new Date());
                        member.setCreateEmployeeId(employee.getId());
                        member.setCreateXingming(employee.getXingMing());
                        member.setOwnerId(employee.getId());
                        member.setOwnerXingMing(employee.getXingMing());
                        member.setUnitId(unitId);
                        member.setRoom(number);
                        member.setProvince(community.getProvince());
                        member.setProvinceCode(community.getProvinceCode());
                        member.setUpdateDate(new Date());
                        member.setUpdateEmployeeId(employee.getId());
                        member.setUpdateEmployeeXingMing(employee.getXingMing());
                        members.add(member);
                    }
                }
                redstarCommonManager.addBeans(members);

            } else { //update
                RedstarCommunityBuilding updateBuildingBean = (RedstarCommunityBuilding) redstarCommonManager.getDataById(buildingId, RedstarCommunityBuilding.class);
                if (updateBuildingBean == null) {
                    setErrMsg(res, "没有查到栋信息");
                    res.setCode(-1001);
                    return res;
                }
                buildings = redstarCommonManager.getDataList(RedstarCommunityBuilding.class, building);
                for (RedstarCommunityBuilding cb : buildings) {
                    if (buildingId != cb.getId()) {
                        setErrMsg(res, "栋名已存在");
                        res.setCode(-1001);
                        return res;
                    }
                }
                updateBuildingBean.setBuildingName(name);
                redstarCommonManager.updateBean(updateBuildingBean);//更新栋信息
                ArrayList<RedStarMember> addMember = new ArrayList<RedStarMember>();
                ArrayList<Object> updateMember = new ArrayList<Object>();
                for (UnitRoomObject uro : unos) {
                    RedstarCommunityUnit unit;
                    MultiSearchBean unitSearch = new MultiSearchBean();
                    TextSearch unitName = new TextSearch("unitName");
                    unitName.setSearchValue(uro.unitName);
                    unitSearch.addSearchBean(unitName);
                    IntSearch buildId = new IntSearch("buildingId");
                    buildId.setSearchValue(String.valueOf(updateBuildingBean.getId()));
                    unitSearch.addSearchBean(buildId);
                    List<RedstarCommunityUnit> validUnits = redstarCommonManager.getDataList(RedstarCommunityUnit.class, unitSearch);
                    if (StringUtil.isValid(uro.unitId)) { //如果有unitId
                        try {
                            unit = (RedstarCommunityUnit) redstarCommonManager.getDataById(Integer.parseInt(uro.unitId), RedstarCommunityUnit.class);
                        } catch (Exception ex) {
                            setErrMsg(res, "单元信息未找到");
                            res.setCode(-1001);
                            return res;
                        }
                        for (RedstarCommunityUnit validUnit : validUnits) {
                            if(unit.getId() != validUnit.getId()){
                                setErrMsg(res, "单元名重复");
                                res.setCode(-1001);
                                return res;
                            }
                        }
                        unit.setUnitName(uro.unitName);
                        redstarCommonManager.updateBean(unit);
                    } else { //如果没有unit则新增
                        if (validUnits.size() > 0) {
                            setErrMsg(res, "单元名重复");
                            res.setCode(-1001);
                            return res;
                        }
                        unit = new RedstarCommunityUnit();
                        unit.setUnitName(uro.unitName);
                        unit.setBuildingId(buildingId);
                        unit.setBuildingName(name);
                        unit.setCommunityId(communityId);
                        unit.setCommunityName(community.getName());
                        unit.setCreateDate(new Date());
                        unit.setCreateEmployeeId(employee.getId());
                        unit.setCreateXingMing(employee.getXingMing());
                        int unitId = redstarCommonManager.addBean(unit);
                        unit.setId(unitId);
                    }


                    for (String number : uro.rooms) {
                        MultiSearchBean memberSearch = new MultiSearchBean();
                        TextSearch numberSearch = new TextSearch("room");
                        numberSearch.setSearchValue(number);
                        memberSearch.addSearchBean(numberSearch);
                        IntSearch unitId = new IntSearch("unitId");
                        unitId.setSearchValue(String.valueOf(unit.getId()));
                        memberSearch.addSearchBean(unitId);
                        List<RedStarMember> validMember = redstarCommonManager.getDataList(RedStarMember.class, memberSearch);
                        if (validMember.size() > 0) {
                            RedStarMember update = validMember.get(0);
                            update.setBuildingName(name);
                            update.setUnitName(uro.unitName);
                            updateMember.add(update);
                        } else {
                            RedStarMember member = new RedStarMember();
                            member.setBuildingId(buildingId);
                            member.setBuildingName(name);
                            member.setCommunityId(communityId);
                            member.setCommunityName(community.getName());
                            member.setArea(community.getArea());
                            member.setAreaCode(community.getAreaCode());
                            member.setCommunityAddress(community.getAddress());
                            member.setCity(community.getCity());
                            member.setCityCode(community.getCityCode());
                            member.setCreateDate(new Date());
                            member.setCreateEmployeeId(employee.getId());
                            member.setCreateXingming(employee.getXingMing());
                            member.setOwnerId(employee.getId());
                            member.setOwnerXingMing(employee.getXingMing());
                            member.setUnitId(unit.getId());
                            member.setRoom(number);
                            member.setProvince(community.getProvince());
                            member.setProvinceCode(community.getProvinceCode());
                            member.setUpdateDate(new Date());
                            member.setUpdateEmployeeId(employee.getId());
                            member.setUpdateEmployeeXingMing(employee.getXingMing());
                            addMember.add(member);
                        }
                    }

                }

                redstarCommonManager.addBeans(addMember);
                redstarCommonManager.batchUpdateIdentified(RedStarMember.class, updateMember);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            setErrMsg(res, "住宅信息异常");
            res.setCode(500);
            return res;
        }
        setSuccessMsg(res, "成功");
        return res;
    }


}
