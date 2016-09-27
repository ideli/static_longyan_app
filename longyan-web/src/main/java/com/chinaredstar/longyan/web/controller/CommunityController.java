package com.chinaredstar.longyan.web.controller;

import com.chinaredstar.commonBiz.bean.*;
import com.chinaredstar.commonBiz.bean.constant.CommonBizConstant;
import com.chinaredstar.commonBiz.manager.DispatchDriver;
import com.chinaredstar.commonBiz.manager.RedstarCommonManager;
import com.chinaredstar.commonBiz.util.DoubleUtil;
import com.chinaredstar.longyan.exception.BusinessException;
import com.chinaredstar.longyan.exception.FormException;
import com.chinaredstar.longyan.exception.constant.CommonExceptionType;
import com.chinaredstar.longyan.exception.constant.CommunityExceptionType;
import com.chinaredstar.longyan.util.CommunityFormUtil;
import com.chinaredstar.longyan.util.RateUtil;
import com.chinaredstar.longyan.util.SetCommunityMallUtil;
import com.chinaredstar.nvwaBiz.bean.NvwaEmployee;
import com.chinaredstar.nvwaBiz.bean.NvwaSecurityOperationLog;
import com.chinaredstar.nvwaBiz.manager.NvwaDriver;
import com.chinaredstar.nvwaBiz.util.EmployeeUtil;
import com.xiwa.base.bean.PaginationDescribe;
import com.xiwa.base.bean.Request;
import com.xiwa.base.bean.Response;
import com.xiwa.base.bean.SimplePaginationDescribe;
import com.xiwa.base.bean.search.ext.IntSearch;
import com.xiwa.base.bean.search.ext.MultiSearchBean;
import com.xiwa.base.pipeline.PipelineContext;
import com.xiwa.base.util.DataUtil;
import com.xiwa.base.util.StringUtil;
import com.xiwa.zeus.trinity.bean.Employee;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


/**
 * Created by StevenDong on 2016/9/21
 */
@RequestMapping("/community")
@Controller
public class CommunityController extends BaseController implements CommonBizConstant {

    @Autowired
    private DispatchDriver dispatchDriver;
    @Autowired
    private NvwaDriver nvwaDriver;
    @Autowired
    private RedstarCommonManager redstarCommonManager;

    // 小区认领状态
    private final int reclaimStatus_OK = 1;
    // 数据源名
    private final String source_LY = "LY";
    // 数据拥有者
    private final int dataBelong_LY = 2;


    //查询我的小区列表
    @RequestMapping(value = "/myList/{type}", method = RequestMethod.POST)
    @ResponseBody
    public Response myList(@PathVariable("type") String strType) {
        PipelineContext pipelineContext = this.buildPipelineContent();
        Response res = pipelineContext.getResponse();

        try {
            // 查询参数设定
            // 登陆EmployeeID获得
            NvwaEmployee loginEmployee = this.getEmployeeromSession();
            if (loginEmployee.getId() == 0) {
                setErrMsg(res, "用户ID参数缺失");
                return res;
            }
            int intEmployeeId = loginEmployee.getId();

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

            // 负责的小区
            if ("inChargeCommunity".equals(strType)) {
                IntSearch ownerIdSearch = new IntSearch("ownerId");
                ownerIdSearch.setSearchValue(String.valueOf(intEmployeeId));
                PaginationDescribe<RedstarCommunity> inChargeCommunityResult =
                        (PaginationDescribe<RedstarCommunity>) dispatchDriver.getRedstarCommunityManager().searchBeanPage(page, pageSize, ownerIdSearch, "updateDate", Boolean.FALSE);
                List<RedstarCommunity> redstarCommunityList = inChargeCommunityResult.getCurrentRecords();
                ((SimplePaginationDescribe) inChargeCommunityResult).setCurrentRecords(redstarCommunityList);

                res.addKey("result", inChargeCommunityResult);
            } else if ("updateCommunity".equals(strType)) {  // 完善的小区（非商场员工只调用这个接口）
                IntSearch updateIdSearch = new IntSearch("updateEmployeeId");
                updateIdSearch.setSearchValue(String.valueOf(intEmployeeId));

                PaginationDescribe<RedstarCommunityUpdateLog> updateCommunityResult =
                        (PaginationDescribe<RedstarCommunityUpdateLog>) dispatchDriver.getRedstarCommunityUpdateLogManager().searchBeanPage(page, pageSize, updateIdSearch, "updateDate", Boolean.FALSE);
                List<RedstarCommunityUpdateLog> redstarCommunityUpdateLogList = updateCommunityResult.getCurrentRecords();
                ((SimplePaginationDescribe) updateCommunityResult).setCurrentRecords(redstarCommunityUpdateLogList);

                res.addKey("result", updateCommunityResult);
            }
            setSuccessMsg(res);
        } catch (Exception e) {
            setUnknowException(e, res);
        }
        return res;
    }

    /**
     * //查询周边小区列表
     *
     * @param strType      allAroundCommunity=全部 occupyCommunity=可抢占 predistributionCommunity=预分配
     * @param longitude    经度
     * @param latitude     纬度
     * @param provinceCode
     * @param cityCode
     * @param limitM       查询小区半径（米）
     * @return
     */
    @RequestMapping(value = "/aroundList/{type}", method = RequestMethod.POST)
    @ResponseBody
    public Response aroundList(@PathVariable("type") String strType, String longitude, String latitude,
                               String provinceCode, String cityCode, String limitM) {
        PipelineContext pipelineContext = this.buildPipelineContent();
        Response res = pipelineContext.getResponse();

        try {
            int intOwnerMallId = 0;
            // 查询参数设定
            // 登陆EmployeeID的商场ID获得（非员工没有商场ID）
            // TODO 登陆逻辑修改
//            Employee loginEmployee = this.getEmployeeromSession();
            NvwaEmployee loginEmployee = new NvwaEmployee();
            loginEmployee.setDepartmentId(1642);
            //查询员工所属商场
            RedstarShoppingMall shoppingMall = EmployeeUtil.getMall(loginEmployee, dispatchDriver, nvwaDriver);
            if (shoppingMall != null) {
                intOwnerMallId = shoppingMall.getId();
            }

            // 所在经纬度,省市code判断
            if (StringUtil.isInvalid(latitude) || StringUtil.isInvalid(longitude)
                    || StringUtil.isInvalid(provinceCode) || StringUtil.isInvalid(cityCode)) {
                setErrMsg(res, "经纬度参数缺失");
                return res;
            }

            // 查询小区范围如果缺失，设定默认查询范围为5公里
            if (StringUtil.isInvalid(limitM)) {
                limitM = "5000";
            }

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

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String strSysytemDateTime = df.format(System.currentTimeMillis());

            // 预分配小区(所属商场下无管理者的小区)如果员工不是商场员工则该列表不返回数据
            if ("predistributionCommunity".equals(strType) && intOwnerMallId > 0) {

                int intLimtM = Integer.parseInt(limitM);
                StringBuffer sb = new StringBuffer();

                sb.append("Select c.id, c.name,c.address,c.ownerMallName,c.reclaimStatus,c.reclaimCompleteDate, ");
                sb.append(" round(6378.138 * 2 * asin(  ");
                sb.append(" sqrt( pow(sin((c.latitude * pi() / 180 - ?*pi() / 180) / 2),2) + cos(c.latitude * pi() / 180) ");
                sb.append(" * cos(?*pi() / 180) * pow(  ");
                sb.append(" sin((c.longitude * pi() / 180 - ?*pi()/180) / 2),2))) * 1000) AS distance   ");
                sb.append(" FROM xiwa_redstar_community c where c.longitude>0 and c.latitude>0 and c.ownerMallId=? AND c.reclaimStatus = 0 AND c.reclaimCompleteDate > ? HAVING distance < ? ORDER BY distance ");

                String querySQL = sb.toString();

                List<Object> paramsList = new ArrayList<Object>();
                paramsList.add(Double.parseDouble(latitude));
                paramsList.add(Double.parseDouble(latitude));
                paramsList.add(Double.parseDouble(longitude));
                paramsList.add(intOwnerMallId);
                paramsList.add(strSysytemDateTime);
                paramsList.add(intLimtM);

                //搜索结果（以员工GPS位置为圆心半径intLimtM内所有该员工所属商场下小区列表）
                List lsAroundCommunity = redstarCommonManager.excuteBySql(querySQL, paramsList);
                List<HashMap> lsAroundCommunitys = new ArrayList<HashMap>();

                // 查询结果list转化成输出对象
                for (int i = 0; i < lsAroundCommunity.size(); i++) {
                    Object[] objAd = (Object[]) lsAroundCommunity.get(i);
                    HashMap hmADComObj = new HashMap();
                    hmADComObj.put("communityId", objAd[0]);
                    hmADComObj.put("name", objAd[1]);
                    hmADComObj.put("address", objAd[2]);
                    hmADComObj.put("ownerMallName", objAd[3]);
                    hmADComObj.put("reclaimStatus", objAd[4]);
                    hmADComObj.put("reclaimCompleteDate", objAd[5]);
                    hmADComObj.put("distance", objAd[6]);
                    hmADComObj.put("status", 1);
                    lsAroundCommunitys.add(hmADComObj);
                }
                res.addKey("result", lsAroundCommunitys);
                res.addKey("currentPage", page);
            } else if ("occupyCommunity".equals(strType) && intOwnerMallId > 0) {  // 可抢的小区,如果员工不是商场员工则该列表不返回数据

                int intLimtM = Integer.parseInt(limitM);
                StringBuffer sb = new StringBuffer();

                sb.append("Select c.id, c.name,c.address,c.ownerMallName, ");
                sb.append(" round(6378.138 * 2 * asin(  ");
                sb.append(" sqrt( pow(sin((c.latitude * pi() / 180 - ?*pi() / 180) / 2),2) + cos(c.latitude * pi() / 180) ");
                sb.append(" * cos(?*pi() / 180) * pow(  ");
                sb.append(" sin((c.longitude * pi() / 180 - ?*pi()/180) / 2),2))) * 1000) AS distance   ");
                sb.append(" FROM xiwa_redstar_community c WHERE c.longitude>0 AND c.latitude>0 AND c.reclaimStatus = 0 AND c.reclaimCompleteDate < ? HAVING distance < ? ORDER BY distance ");

                String querySQL = sb.toString();
                List<Object> paramsList = new ArrayList<Object>();
                paramsList.add(Double.parseDouble(latitude));
                paramsList.add(Double.parseDouble(latitude));
                paramsList.add(Double.parseDouble(longitude));
                paramsList.add(strSysytemDateTime);
                paramsList.add(intLimtM);

                //搜索结果（以员工GPS位置为圆心半径intLimtM内所有未分配小区列表）
                List lsAroundCommunity = redstarCommonManager.excuteBySql(querySQL, paramsList);
                List<HashMap> lsAroundCommunitys = new ArrayList<HashMap>();

                // 查询结果list转化成输出对象
                for (int i = 0; i < lsAroundCommunity.size(); i++) {
                    Object[] objAd = (Object[]) lsAroundCommunity.get(i);
                    HashMap hmADComObj = new HashMap();
                    hmADComObj.put("communityId", objAd[0]);
                    hmADComObj.put("name", objAd[1]);
                    hmADComObj.put("address", objAd[2]);
                    hmADComObj.put("ownerMallName", objAd[3]);
                    hmADComObj.put("distance", objAd[4]);
                    hmADComObj.put("status", 2);
                    lsAroundCommunitys.add(hmADComObj);
                }

                res.addKey("result", lsAroundCommunitys);
                res.addKey("currentPage", page);
            } else if ("allAroundCommunity".equals(strType)) {  // 该城市内当前位置周边所有的小区（非商场员工调用接口）

                int intLimtM = Integer.parseInt(limitM);
                StringBuffer sb = new StringBuffer();

                sb.append("Select c.id, c.name,c.address,c.ownerMallId,c.ownerMallName,c.ownerId,c.reclaimStatus,c.reclaimCompleteDate, ");
                sb.append(" round(6378.138 * 2 * asin(  ");
                sb.append(" sqrt( pow(sin((c.latitude * pi() / 180 - ?*pi() / 180) / 2),2) + cos(c.latitude * pi() / 180) ");
                sb.append(" * cos(?*pi() / 180) * pow(  ");
                sb.append(" sin((c.longitude * pi() / 180 - ?*pi()/180) / 2),2))) * 1000) AS distance   ");
                sb.append(" FROM xiwa_redstar_community c WHERE c.longitude>0 AND c.latitude>0 and c.provinceCode = ? and c.cityCode = ? HAVING distance < ? ORDER BY distance ");

                String querySQL = sb.toString();

                List<Object> paramsList = new ArrayList<Object>();
                paramsList.add(Double.parseDouble(latitude));
                paramsList.add(Double.parseDouble(latitude));
                paramsList.add(Double.parseDouble(longitude));
                paramsList.add(provinceCode);
                paramsList.add(cityCode);
                paramsList.add(intLimtM);

                //搜索结果（以员工GPS位置为圆心半径intLimtM内所有小区列表）
                List lsAllAroundCommunity = redstarCommonManager.excuteBySql(querySQL, paramsList);
                List<HashMap> lsAllAroundCommunitys = new ArrayList<HashMap>();

                // 查询结果list转化成输出对象
                for (int i = 0; i < lsAllAroundCommunity.size(); i++) {

                    Object[] objAllCommunity = (Object[]) lsAllAroundCommunity.get(i);
                    HashMap hmAllComObj = new HashMap();
                    int communityOwnerMallId = DataUtil.getInt(objAllCommunity[3], 0);
                    int communityOwnerId = DataUtil.getInt(objAllCommunity[5], 0);
                    int reclaimStatus = DataUtil.getInt(objAllCommunity[6], 0);
                    Date reclaimCompleteDate = DataUtil.getDate(DataUtil.getDate(objAllCommunity[7]), new Date(System.currentTimeMillis() + 1000000));
                    hmAllComObj.put("communityId", objAllCommunity[0]);
                    hmAllComObj.put("name", objAllCommunity[1]);
                    hmAllComObj.put("address", objAllCommunity[2]);
                    hmAllComObj.put("ownerMallName", objAllCommunity[4]);
                    hmAllComObj.put("distance", objAllCommunity[8]);
                    if (intOwnerMallId < 1) {
                        //如果不是商场员工,只能编辑
                        hmAllComObj.put("status", 0);
                    } else if (reclaimStatus == 0 && reclaimCompleteDate.getTime() < System.currentTimeMillis()) {
                        //没有认领但是已经过了认领期限了
                        hmAllComObj.put("status", 2);
                    } else if (intOwnerMallId == communityOwnerMallId && communityOwnerId < 1) {
                        //所属商场但是没有管理者,可以认领
                        hmAllComObj.put("status", 1);
                    } else {
                        //其他状态,只能编辑
                        hmAllComObj.put("status", 0);
                    }
                    lsAllAroundCommunitys.add(hmAllComObj);
                }

                res.addKey("result", lsAllAroundCommunitys);
                res.addKey("currentPage", page);
            }

            setSuccessMsg(res);
        } catch (Exception e) {
            setUnknowException(e, res);
        }
        return res;
    }

    //按名字查询小区信息
    // TODO
    @RequestMapping(value = "/{communityName}")
    @ResponseBody
    public Response dataItem(@PathVariable("communityName") String communityName) {
        PipelineContext pipelineContext = this.buildPipelineContent();
        Response res = pipelineContext.getResponse();

        StringBuffer sb = new StringBuffer();

        sb.append("Select c.id, c.name,c.address,c.ownerMallId,c.ownerMallName,c.ownerId,c.reclaimStatus,c.reclaimCompleteDate, ");
        String querySQL = sb.toString();


        return null;
    }

    //更新小区信息
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Response dataUpdate() {
        PipelineContext pipelineContext = this.buildPipelineContent();
        Request request = pipelineContext.getRequest();
        Response res = pipelineContext.getResponse();

        try {
            NvwaEmployee employee = getEmployeeromSession();
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

            NvwaSecurityOperationLog securityOperationLog = new NvwaSecurityOperationLog();
            if (employee != null) {
                securityOperationLog.setOperatorId(employee.getId());
                securityOperationLog.setOperator(employee.getXingMing());
                securityOperationLog.setOperateResource(Community_Operate_Resource);
                securityOperationLog.setCreateDate(new Date());
                securityOperationLog.setBelongedId(LOG_BELONG_ID);

                securityOperationLog.setOperateResourceId(String.valueOf(dataId));

                if (!(DataUtil.getInt(community.getCreateEmployeeId(), 0) > 0)) {
                    //如果没有CreateEmployeeId，则设置当前用户为CreateEmployeeId
                    community.setCreateEmployeeId(employee.getId());
                    community.setCreateXingMing(employee.getXingMing());
                    //设置小区的商场id name
                    community = SetCommunityMallUtil.setCommunityMall(redstarCommonManager, employee, community, null);
                    //重新设置下createDate的时间
                    community.setCreateDate(new Date());
                    securityOperationLog.setOperationTypeField(ADD_OPERATION);
                    securityOperationLog.setContent("添加小区");
                    nvwaDriver.getNvwaSecurityOperationLogManager().addBean(securityOperationLog);
                } else {
                    securityOperationLog.setOperationTypeField(UPDATE_OPERATION);
                    securityOperationLog.setContent("更新小区");
                    nvwaDriver.getNvwaSecurityOperationLogManager().addBean(securityOperationLog);
                }
                community.setUpdateEmployeeId(employee.getId());
                community.setUpdateEmployeeXingMing(employee.getXingMing());
            }
            community.setUpdateDate(new Date());

            dispatchDriver.getRedstarCommunityUpdateLogManager().updateBean(community);
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

    //添加小区
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Response dataCreate() {
        PipelineContext pipelineContext = this.buildPipelineContent();
        Request request = pipelineContext.getRequest();
        Response res = pipelineContext.getResponse();
        RedstarCommunity community = new RedstarCommunity();
        try {
            int intOwnerMallId = 0;
            NvwaEmployee employee = getEmployeeromSession();
            // TODO 测试数据删除需要
            employee.setDepartmentId(1642);
            //查询员工所属商场
            RedstarShoppingMall shoppingMall = EmployeeUtil.getMall(employee, dispatchDriver, nvwaDriver);
            if (shoppingMall != null) {
                intOwnerMallId = shoppingMall.getId();
            }

            //设置省市区和小区名称
            CommunityFormUtil.setLoctionAndName(request, dispatchDriver, community);
            //小区别称
            CommunityFormUtil.setShortName(request, community);
            //详细地址
            CommunityFormUtil.setAddress(request, community);
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


            NvwaSecurityOperationLog securityOperationLog = new NvwaSecurityOperationLog();

            if (employee != null) {
                community.setCreateEmployeeId(employee.getId());
                community.setCreateXingMing(employee.getXingMing());
                securityOperationLog.setOperatorId(employee.getId());
                securityOperationLog.setOperator(employee.getXingMing());
                community.setDataBelong(dataBelong_LY);
                community.setSource(source_LY);
                if (intOwnerMallId > 0) {
                    // 员工创建完即为owner,非员工则不更新
                    community.setOwnerId(employee.getId());
                    community.setReclaimStatus(reclaimStatus_OK);
                }
            }

            community.setCreateDate(new Date());
            community.setUpdateDate(new Date());

            Integer dataId = dispatchDriver.getRedstarCommunityManager().addBean(community);
            res.addKey("id", dataId);

            //添加日志
            securityOperationLog.setOperateResource(Community_Operate_Resource);
            securityOperationLog.setCreateDate(new Date());
            securityOperationLog.setBelongedId(LOG_BELONG_ID);
            securityOperationLog.setOperationTypeField(ADD_OPERATION);
            securityOperationLog.setOperateResourceId(String.valueOf(dataId));
            securityOperationLog.setContent("添加小区");
            nvwaDriver.getNvwaSecurityOperationLogManager().addBean(securityOperationLog);

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


    //查询小区详细信息
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

        if (roomAmount <= 0) {
            setErrCodeAndMsg(response, -1001, "住宅数不能为空");
            return response;
        }

        if (floorAmount <= 0) {
            setErrCodeAndMsg(response, -1001, "楼层数不能为空");
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
            NvwaEmployee employee = getEmployeeromSession();

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
    public Response buildingUpdate() {
        PipelineContext pipelineContext = this.buildPipelineContent();
        Response response = pipelineContext.getResponse();

        Integer dataId = pipelineContext.getRequest().getInt("id");

        if (dataId <= 0) {
            setErrCodeAndMsg(response, -1001, "数据id缺失");
            return response;
        }

        //小区ID 和 栋的姓名

        String buildingName = pipelineContext.getRequest().getString("buildingName");
        int roomAmount = pipelineContext.getRequest().getInt("roomAmount");//住宅数
        int unitAmount = pipelineContext.getRequest().getInt("unitAmount");//单元数
        int floorAmount = pipelineContext.getRequest().getInt("floorAmount");//楼层数

        if (roomAmount <= 0) {
            setErrCodeAndMsg(response, -1001, "住宅数不能为空");
            return response;
        }
        if (unitAmount <= 0) {
            setErrCodeAndMsg(response, -1001, "单元数不能为空");
            return response;
        }
        if (floorAmount <= 0) {
            setErrCodeAndMsg(response, -1001, "楼层数不能为空");
            return response;
        }

        if (StringUtils.isBlank(buildingName)) {
            setErrCodeAndMsg(response, -1001, "buildingName不能为空");
            return response;
        }

        try {

            RedstarCommunityBuilding redstarCommunityBuilding = (RedstarCommunityBuilding) dispatchDriver.getRedstarCommunityBuildingManager().getBean(dataId);

            if (redstarCommunityBuilding == null) {
                setErrCodeAndMsg(response, -1001, "当前数据不存在");
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
            NvwaEmployee employee = getEmployeeromSession();
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

//                    IntSearch createEmployeeIdSearch = new IntSearch("createEmployeeId");
//                    createEmployeeIdSearch.setSearchValue(String.valueOf(createEmployeeId));
//                    multiSearchBean.addSearchBean(createEmployeeIdSearch);

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


}
