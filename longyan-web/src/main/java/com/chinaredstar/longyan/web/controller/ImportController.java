package com.chinaredstar.longyan.web.controller;

import com.chinaredstar.commonBiz.bean.*;
import com.chinaredstar.longyan.bean.StorageConfig;
import com.chinaredstar.longyan.util.RateUtil;
import com.chinaredstar.longyan.util.ReadExcelUtil;
import com.chinaredstar.commonBiz.bean.constant.CommonBizConstant;
import com.chinaredstar.commonBiz.manager.DispatchDriver;
import com.chinaredstar.commonBiz.manager.RedstarCommonManager;
import com.chinaredstar.nvwaBiz.bean.NvwaEmployee;
import com.chinaredstar.nvwaBiz.bean.NvwaSecurityOperationLog;
import com.chinaredstar.nvwaBiz.manager.NvwaDriver;
import com.xiwa.base.bean.Request;
import com.xiwa.base.bean.Response;
import com.xiwa.base.bean.search.ext.IntSearch;
import com.xiwa.base.bean.search.ext.MultiSearchBean;
import com.xiwa.base.bean.search.ext.TextSearch;
import com.xiwa.base.manager.ManagerException;
import com.xiwa.base.pipeline.PipelineContext;
import com.xiwa.base.util.CollectionUtil;
import com.xiwa.base.util.DataUtil;
import com.xiwa.base.util.StringUtil;
import com.xiwa.zeus.trinity.bean.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Created by lenovo on 2016/5/18.
 */
@Controller
@RequestMapping("/import")
public class ImportController extends BaseController implements CommonBizConstant {

    @Autowired
    DispatchDriver dispatchDriver;
    @Autowired
    NvwaDriver nvwaDriver;
    @Autowired
    RedstarCommonManager redstarCommonManager;

    @Autowired
    private StorageConfig storageConfig;

    //小区数据导入
    @RequestMapping("/community")
    @ResponseBody
    public Response importCommunity() {
        PipelineContext context = this.buildPipelineContent();
        Request req = context.getRequest();
        Response res = context.getResponse();

        NvwaEmployee employee = null;
//        NvwaEmployee currentEmployee = null;
        try {
            employee = getEmployeeromSession();
//            currentEmployee = (NvwaEmployee) nvwaDriver.getNvwaEmployeeManager().getBean(employee.getId());
        } catch (ManagerException e) {
            e.printStackTrace();
        }


        if (null == employee) {
            setErrMsg(res, "当前不存在登录用户");
            return res;
        }

        Integer tipIndex = 1;
        Integer successCount = 0;
        List<List<String>> dataList = new ArrayList<List<String>>();

        List<String> errorList = new LinkedList<String>();
        try {
            String filePath = req.getString("csvFilePath");
            if (StringUtil.isInvalid(filePath)) {
                setErrMsg(res, "文件路径参数缺失");
                return res;
            }

            String[] fileType = filePath.split("\\.");

/*            if (StringUtil.equals(fileType[1], "csv")) {
                dataList = readCsv.readCSVFile(storageConfig.getStorage_config_store_path() + "/" + filePath);
            } else if (StringUtil.equals(fileType[1], "xls")) {
                dataList = readExcel.execute(storageConfig.getStorage_config_store_path() + "/" + filePath, 15);
            } else if (StringUtil.equals(fileType[1], "xlsx")) {
                throw new ManagerException("请导入03版本的excel");
            } else {
                throw new ManagerException("请导入excel或者csv文件");
            }*/

            if (!StringUtil.equals(fileType[1], "xlsx")) {
                setErrMsg(res, "请导入Excel文件");
                return res;
            }

            dataList = ReadExcelUtil.readCommunityExcel(storageConfig.getStorage_config_store_path() + "/" + filePath);


            if (CollectionUtils.isEmpty(dataList)) {
                setErrMsg(res, "数据为空");
                return res;
            }


            if (dataList.size() == 1) {
                setErrMsg(res, "数据为空,请严格按照模板填写数据");
                return res;
            }

            List<List<String>> _thisDataList = new LinkedList<List<String>>();

            List<String> myList;
            for (int index = 0; index < dataList.size(); index++) {
                myList = dataList.get(index);
                if (!CollectionUtils.isEmpty(myList)) {
                    if (StringUtil.isInvalid(myList.get(0)) || StringUtil.isInvalid(myList.get(1)) || StringUtil.isInvalid(myList.get(2))) {
                        break;
                    } else {
                        _thisDataList.add(myList);
                    }
                } else {
                    break;
                }
            }

            if (_thisDataList.size() < 2) {
                setErrMsg(res, "上传数据不能小于1条");
                return res;
            }

            if (_thisDataList.size() > 101) {
                setErrMsg(res, "上传数据不能超过100条");
                return res;
            }

            RedstarCommunity redstarCommunity;
            MultiSearchBean multiSearchBean;

            TextSearch provinceSearch;
            TextSearch citySearch;
            TextSearch areaSearch;
            List<String> excelList;

            TextSearch provinceCodeSearch;
            TextSearch cityCodeSearch;
            TextSearch areaCodeSearch;
            TextSearch nameSearch;
            TextSearch userCodeSearch;
            NvwaSecurityOperationLog securityOperationLog;
            //List<RedstarCommunity> valDataList = new LinkedList<RedstarCommunity>();

            for (int index = 1; index < _thisDataList.size(); index++) {

                tipIndex += 1;
                excelList = dataList.get(index);

/*                if (!CollectionUtils.isEmpty(excelList) && index == 0) {
                    continue;
                }*/
                //tipIndex = index;

                //读取小区数据
                if (!CollectionUtils.isEmpty(excelList)) {

                    String provinceName = excelList.get(0);

                    if (StringUtil.isInvalid(provinceName) || provinceName.length() > 20) {
                        errorList.add("第" + tipIndex + "行 省数据有误");
                        continue;
                    }

                    String cityName = excelList.get(1);
                    if (StringUtil.isInvalid(cityName) || cityName.length() > 20) {
                        errorList.add("第" + tipIndex + "行 市数据有误");
                        continue;
                    }

                    String areaName = excelList.get(2);
                    if (StringUtil.isInvalid(areaName) || areaName.length() > 20) {
                        errorList.add("第" + tipIndex + "行 区数据有误");
                        continue;
                    }


                    redstarCommunity = new RedstarCommunity();
                    String name = excelList.get(3);
                    if (StringUtil.isInvalid(name) || name.trim().length() == 0 || name.length() > 20) {
                        errorList.add("第" + tipIndex + "行 小区名称有误,不能为空且不能超过20字");
                        continue;
                    }
                    redstarCommunity.setName(name.trim());


                    multiSearchBean = new MultiSearchBean();
                    provinceSearch = new TextSearch("province", true);
                    provinceSearch.setSearchValue(provinceName);
                    multiSearchBean.addSearchBean(provinceSearch);

                    citySearch = new TextSearch("city", true);
                    citySearch.setSearchValue(cityName);
                    multiSearchBean.addSearchBean(citySearch);

                    areaSearch = new TextSearch("area", true);
                    areaSearch.setSearchValue(areaName);
                    multiSearchBean.addSearchBean(areaSearch);
                    //验证城市是否存在
                    List<DispatchLocation> cityList = dispatchDriver.getDispatchLocationManager().searchIdentify(multiSearchBean);
                    DispatchLocation dispatchLocation;
                    if (CollectionUtils.isEmpty(cityList)) {
                        errorList.add("第" + tipIndex + "行 省市区不存在");
                        continue;
                    } else {
                        multiSearchBean = new MultiSearchBean();
                        dispatchLocation = cityList.get(0);
                        redstarCommunity.setProvince(dispatchLocation.getProvince());
                        redstarCommunity.setCity(dispatchLocation.getCity());
                        redstarCommunity.setArea(dispatchLocation.getArea());

                        redstarCommunity.setProvinceCode(dispatchLocation.getProvinceCode());
                        redstarCommunity.setCityCode(dispatchLocation.getCityCode());
                        redstarCommunity.setAreaCode(dispatchLocation.getAreaCode());

                        provinceCodeSearch = new TextSearch("provinceCode");
                        provinceCodeSearch.setSearchValue(dispatchLocation.getProvinceCode());

                        cityCodeSearch = new TextSearch("cityCode");
                        cityCodeSearch.setSearchValue(dispatchLocation.getCityCode());

                        areaCodeSearch = new TextSearch("areaCode");
                        areaCodeSearch.setSearchValue(dispatchLocation.getAreaCode());

                        nameSearch = new TextSearch("name");
                        nameSearch.setSearchValue(name.trim());

                        multiSearchBean.addSearchBean(provinceCodeSearch);
                        multiSearchBean.addSearchBean(cityCodeSearch);
                        multiSearchBean.addSearchBean(areaCodeSearch);
                        multiSearchBean.addSearchBean(nameSearch);
                    }
                    //根据省市区和小区名称查询小区
                    List<RedstarCommunity> redstarCommunityList = dispatchDriver.getRedstarCommunityManager().searchIdentify(multiSearchBean);

                    Boolean updateFlag = false;
                    //该小区存在时
                    if (!CollectionUtils.isEmpty(redstarCommunityList)) {
                        RedstarCommunity tempData = redstarCommunityList.get(0);
                        if (tempData.getOwnerId() != null && tempData.getOwnerId() > 0) {
                            errorList.add("第" + tipIndex + "行 小区已存在");
                            continue;
                        } else {
                            tempData.setProvince(dispatchLocation.getProvince());
                            tempData.setCity(dispatchLocation.getCity());
                            tempData.setArea(dispatchLocation.getArea());
                            tempData.setProvinceCode(dispatchLocation.getProvinceCode());
                            tempData.setCityCode(dispatchLocation.getCityCode());
                            tempData.setAreaCode(dispatchLocation.getAreaCode());
                            tempData.setName(name);
                            redstarCommunity = tempData;
                            updateFlag = true;
                        }
                    }


                    //小区地址
                    String address = excelList.get(4);
                    if (StringUtil.isInvalid(address) || address.length() > 100) {
                        errorList.add("第" + tipIndex + "行 地址有误,不能为空且不能超过100字");
                        continue;
                    }
                    redstarCommunity.setAddress(address.trim());

                    //经纬度
                    String locationStr = excelList.get(5);
                    if (StringUtil.isInvalid(locationStr)) {
                        errorList.add("第" + tipIndex + "行 经纬度未填写");
                        continue;
                    }
                    String[] dataArr = locationStr.split(",");
                    if (dataArr.length != 2) {
                        errorList.add("第" + tipIndex + "行 经纬度填写有误,必须以英文,分割");
                        continue;
                    }

                    redstarCommunity.setLongitude(Double.valueOf(dataArr[0]));
                    redstarCommunity.setLatitude(Double.valueOf(dataArr[1]));


                    String areaMonut = excelList.get(6);
                    if (StringUtil.isInvalid(areaMonut) || areaMonut.length() > 10) {
                        errorList.add("第" + tipIndex + "行 占地面积有误");
                        continue;
                    }
                    redstarCommunity.setAreaMonut(RateUtil.getIntegerValue(Double.parseDouble(areaMonut)));


                    String roomMount = excelList.get(7);
                    if (StringUtil.isInvalid(roomMount) || roomMount.length() > 7) {
                        errorList.add("第" + tipIndex + "行 总户数有误");
                        continue;
                    }
                    //redstarCommunity.setRoomMount(Integer.parseInt(roomMount.replace(".0", "")));
                    redstarCommunity.setRoomMount(RateUtil.getIntegerValue(Double.parseDouble(roomMount)));

                    String buildingAmount = excelList.get(8);
                    if (StringUtil.isInvalid(buildingAmount) || buildingAmount.replace(".0", "").length() > 5) {
                        errorList.add("第" + tipIndex + "行 总幢数有误");
                        continue;
                    }
                    redstarCommunity.setBuildingAmount(RateUtil.getIntegerValue(Double.parseDouble(buildingAmount)));

                    //入住率
                    String occupanyRate = excelList.get(9);
                    if (StringUtil.isInvalid(occupanyRate) || Integer.parseInt(occupanyRate.replace(".0", "")) > 100) {
                        errorList.add("第" + tipIndex + "行 入住率有误");
                        continue;
                    }

/*                    double _thisValue =Integer.parseInt(roomMount.replace(".0", ""))*Integer.parseInt(occupanyRate.replace(".0", ""))/100;*/

                    double _thisValue = RateUtil.getIntegerValue(Double.parseDouble(roomMount) * RateUtil.getIntegerValue(Double.parseDouble(occupanyRate))) / 100;

                    //已入住户数
                    redstarCommunity.setAlreadyCheckAmount(RateUtil.getIntegerValue(_thisValue));

                    String priceSection = excelList.get(10);

                    if (StringUtil.isInvalid(priceSection) || priceSection.replace(".0", "").length() > 10) {
                        errorList.add("第" + tipIndex + "行 房价有误");
                        continue;
                    }
                    redstarCommunity.setPriceSection(RateUtil.getIntegerValue(Double.parseDouble(priceSection)));

                    String buildingDate = excelList.get(11);
                    System.out.print("------" + buildingDate);
                    if (StringUtil.isInvalid(buildingDate) || buildingDate.replace(".0", "").trim().length() > 4) {
                        errorList.add("第" + tipIndex + "行 建筑年代有误");
                        continue;
                    }
                    redstarCommunity.setBuildingDate(buildingDate.replace(".0", "").trim());

                    String developers = excelList.get(12);


                    if (StringUtil.isInvalid(developers) || developers.length() > 20) {
                        errorList.add("第" + tipIndex + "行 开发商有误");
                        continue;
                    }
                    redstarCommunity.setDevelopers(developers);


                    //姓名
                    String userName = excelList.get(13);
                    if (StringUtil.isInvalid(userName) || userName.length() > 20) {
                        errorList.add("第" + tipIndex + "行 小区负责人有误");
                        continue;
                    }

                    //用户编码
                    String userCode = excelList.get(14);
                    if (StringUtil.isInvalid(userCode) || userCode.length() > 20) {
                        errorList.add("第" + tipIndex + "行 员工编号有误");
                        continue;
                    }


                    userCodeSearch = new TextSearch("employeeCode");
                    userCodeSearch.setSearchValue(userCode.replace(".0", "").trim());

                    List<NvwaEmployee> employeeList = nvwaDriver.getNvwaEmployeeManager().searchIdentify(userCodeSearch);
                    //验证用户是否存在
                    if (CollectionUtils.isEmpty(employeeList)) {
                        errorList.add("第" + tipIndex + "行 员工编号不存在");
                        continue;
                    }

                    NvwaEmployee redstarEmployee = employeeList.get(0);
                    redstarCommunity.setOwnerXingMing(redstarEmployee.getXingMing());
                    redstarCommunity.setOwnerId(redstarEmployee.getId());

                    String hotLine = excelList.get(15);
                    if (StringUtil.isValid(hotLine) && hotLine.trim().length() < 20) {
                        redstarCommunity.setHotline(hotLine.trim());
                    }


                    redstarCommunity.setCreateDate(new Date());
                    redstarCommunity.setUpdateDate(new Date());
                    //当前上传人的信息
                    redstarCommunity.setCreateEmployeeId(employee.getId());
                    redstarCommunity.setCreateXingMing(employee.getXingMing());
                    redstarCommunity.setUpdateEmployeeId(employee.getId());
                    redstarCommunity.setUpdateEmployeeXingMing(employee.getXingMing());
                    redstarCommunity.setSource("employee");
                    //valDataList.add(redstarCommunity);

                    Integer dataId;
                    if (updateFlag) {
                        dataId = redstarCommunity.getId();
                        dispatchDriver.getRedstarCommunityManager().updateBean(redstarCommunity);
                    } else {
                        dataId = dispatchDriver.getRedstarCommunityManager().addBean(redstarCommunity);
                    }
                    successCount += 1;
                    //操作日志
                    securityOperationLog = new NvwaSecurityOperationLog();
                    securityOperationLog.setOperatorId(employee.getId());
                    securityOperationLog.setOperator(employee.getXingMing());
                    securityOperationLog.setOperateResource(Community_Operate_Resource);
                    securityOperationLog.setCreateDate(new Date());
                    securityOperationLog.setBelongedId(LOG_BELONG_ID);
                    securityOperationLog.setOperationTypeField(ADD_OPERATION);
                    securityOperationLog.setOperateResourceId(String.valueOf(dataId));
                    securityOperationLog.setContent("添加小区");
                    nvwaDriver.getNvwaSecurityOperationLogManager().addBean(securityOperationLog);
                }
            }

            res.addKey("successCount", successCount);
            res.addKey("failCount", dataList.size() - successCount);
            res.addKey("errorList", errorList);
            res.setCode(HTTP_SUCCESS_CODE);
            res.setMessage("数据导入成功");
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            res.addKey("successCount", successCount);
            res.addKey("failCount", dataList.size() - successCount);
            res.addKey("errorList", errorList);
            setErrMsg(res, "第" + tipIndex + "行数据异常,请检查数据");
        }
        return res;
    }

    //住户数据导入
    @RequestMapping("/member")
    @ResponseBody
    public Response importMember() throws ManagerException {
        PipelineContext context = this.buildPipelineContent();
        Request req = context.getRequest();
        Response res = context.getResponse();

        NvwaEmployee employee = getEmployeeromSession();
//        NvwaEmployee currentEmployee = (NvwaEmployee) nvwaDriver.getNvwaEmployeeManager().getBean(employee.getId());

        if (null == employee) {
            setErrMsg(res, "当前不存在登录用户");
            return res;
        }

        Integer tipIndex = 1;
        Integer successCount = 0;
        RedstarCommunity community = null;
        List<List<String>> dataList = new ArrayList<List<String>>();

        List<String> errorList = new LinkedList<String>();
        try {

            long t1 = System.currentTimeMillis();

            String filePath = req.getString("csvFilePath");
            if (StringUtil.isInvalid(filePath)) {
                setErrMsg(res, "文件路径参数缺失");
                return res;
            }

            String[] fileType = filePath.split("\\.");

            if (!StringUtil.equals(fileType[1], "xlsx")) {
                setErrMsg(res, "请导入Excel文件");
                return res;
            }

            dataList = ReadExcelUtil.readMemberExcel(storageConfig.getStorage_config_store_path() + "/" + filePath);

            if (CollectionUtils.isEmpty(dataList)) {
                errorList.add("数据为空");
                setErrMsg(res, "数据为空");
                return res;
            }

            if (dataList.size() == 1) {
                errorList.add("数据为空,请严格按照模板填写数据");
                setErrMsg(res, "数据为空,请严格按照模板填写数据");
                return res;
            }


            List<List<String>> _thisDataList = new LinkedList<List<String>>();
            List<String> myList;
            for (int index = 0; index < dataList.size(); index++) {
                myList = dataList.get(index);
                if (!CollectionUtils.isEmpty(myList)) {
                    if (StringUtil.isInvalid(myList.get(0)) || StringUtil.isInvalid(myList.get(1)) || StringUtil.isInvalid(myList.get(2))) {
                        break;
                    } else {
                        _thisDataList.add(myList);
                    }
                } else {
                    break;
                }
            }

            if (_thisDataList.size() < 2) {
                setErrMsg(res, "上传数据不能小于1条");
                return res;
            }

            RedStarMember redStarMember;
            MultiSearchBean multiSearchBean;

            TextSearch provinceSearch;
            TextSearch citySearch;
            TextSearch areaSearch;
            List<String> excelList;

            TextSearch provinceCodeSearch;
            TextSearch cityCodeSearch;
            TextSearch areaCodeSearch;
            TextSearch nameSearch;

            TextSearch buildingSearch;
            TextSearch roomSearch;
            TextSearch unitSearch;

            NvwaSecurityOperationLog securityOperationLog;

            //住宅缓存
            Map<String, Integer> memberCacheMap = null;


            //装修类型字典
            List<RedstarRenovationStatus> reList = redstarCommonManager.getDataList(RedstarRenovationStatus.class);
            //房屋类型字典
            List<RedstarRoomType> typeList = redstarCommonManager.getDataList(RedstarRoomType.class);


            for (int index = 1; index < _thisDataList.size(); index++) {

                tipIndex += 1;
                excelList = dataList.get(index);
/*                if (!CollectionUtils.isEmpty(excelList) && index == 0) {
                    continue;
                }*/

                //读取小区数据
                if (!CollectionUtils.isEmpty(excelList)) {

                    String provinceName = excelList.get(0);

                    if (StringUtil.isInvalid(provinceName) || provinceName.length() > 20) {
                        errorList.add("第" + tipIndex + "行 所属省名称有误");
                        continue;
                    }

                    String cityName = excelList.get(1);
                    if (StringUtil.isInvalid(cityName) || cityName.length() > 20) {
                        errorList.add("第" + tipIndex + "行 所属市名称没有填写");
                        continue;
                    }

                    String areaName = excelList.get(2);
                    if (StringUtil.isInvalid(areaName) || areaName.length() > 20) {
                        errorList.add("第" + tipIndex + "行 所属区名称没有填写");
                        continue;
                    }

                    redStarMember = new RedStarMember();
                    String name = excelList.get(3);
                    if (StringUtil.isInvalid(name) || name.trim().length() == 0 || name.length() > 20) {
                        errorList.add("第" + tipIndex + "行 小区名称填写有误");
                        continue;
                    }


                    String building = excelList.get(4);
                    if (StringUtil.isInvalid(building) || building.length() > 10) {
                        errorList.add("第" + tipIndex + "行 幢/栋填写有误");
                        continue;
                    }
                    //building = String.valueOf(RateUtil.getIntegerValue(Double.parseDouble(building)));
                    redStarMember.setBuilding(building.replace(".0", "").trim());
                    buildingSearch = new TextSearch("building");
                    buildingSearch.setSearchValue(building.replace(".0", "").trim());


                    String unit = excelList.get(5);
                    unitSearch = new TextSearch("unit");
                    if (StringUtil.isValid(unit)) {
                        if (unit.length() > 10) {
                            errorList.add("第" + tipIndex + "行 单元填写有误");
                            continue;
                        }
                        unitSearch.setSearchValue(unit.replace(".0", "").trim());
                        redStarMember.setUnit(unit.replace(".0", "").trim());
                    }


                    String roomNo = excelList.get(6);
                    if (StringUtil.isInvalid(roomNo) || roomNo.trim().length() > 10) {
                        errorList.add("第" + tipIndex + " 行 房号 填写有误");
                        continue;
                    }
                    String tempRoomNo = roomNo.trim().replace("室", "").replace(".0", "");
                    roomSearch = new TextSearch("room");
                    roomSearch.setSearchValue(tempRoomNo);
                    redStarMember.setRoom(tempRoomNo);


                    String renovationStatus = excelList.get(7);
                    if (StringUtil.isInvalid(renovationStatus)) {
                        errorList.add("第" + tipIndex + " 行 装修情况 填写有误");
                        continue;
                    }

                    RedstarRenovationStatus renovationStatusSelectData = null;

                    for (RedstarRenovationStatus redstarRenovationStatus : reList) {
                        if (redstarRenovationStatus != null && redstarRenovationStatus.getName().trim().equalsIgnoreCase(renovationStatus.trim())) {
                            renovationStatusSelectData = redstarRenovationStatus;
                        }
                    }

                    if (renovationStatusSelectData == null) {
                        errorList.add("第" + tipIndex + " 行 装修类型 不存在");
                        continue;
                    } else {
                        redStarMember.setRenovationStatus(renovationStatusSelectData.getName());
                        redStarMember.setRenovationStatusId(renovationStatusSelectData.getId());
                    }


                    //类型
                    String roomType = excelList.get(8);
                    if (StringUtil.isValid(roomType)) {

                        RedstarRoomType roomTypeSelectData = null;
                        for (RedstarRoomType redstarRoomType : typeList) {
                            if (redstarRoomType != null && redstarRoomType.getName().trim().equalsIgnoreCase(roomType.trim())) {
                                roomTypeSelectData = redstarRoomType;
                            }
                        }

                        if (roomTypeSelectData == null) {
                            errorList.add("第" + tipIndex + "行 住宅类型不存在");
                            continue;
                        } else {
                            redStarMember.setRoomType(roomTypeSelectData.getName());
                            redStarMember.setRoomTypeId(roomTypeSelectData.getId());
                        }
                    }

                    //室
                    String roomCount = excelList.get(9);
                    if (StringUtil.isValid(roomCount) && roomCount.trim().length() != 0) {
                        redStarMember.setBedroomAmount(Integer.parseInt(roomCount.trim().replace("室", "")));
                    }
                    //厅
                    String hallCount = excelList.get(10);
                    if (StringUtil.isValid(hallCount) && hallCount.trim().length() != 0) {
                        redStarMember.setHallAmount(Integer.parseInt(hallCount.trim().replace("厅", "")));
                    }

                    String areaAmount = excelList.get(11);
                    if (StringUtil.isValid(areaAmount) || areaAmount.trim().length() != 0) {
                        Double tempArea = Double.parseDouble(areaAmount.trim());
                        if (tempArea > 0 && tempArea < 99999) {
                            redStarMember.setHousingAreaAmount(tempArea);
                        }
                    }

                    if (index == 1) {
                        multiSearchBean = new MultiSearchBean();
                        provinceSearch = new TextSearch("province", true);
                        provinceSearch.setSearchValue(provinceName.trim());
                        multiSearchBean.addSearchBean(provinceSearch);

                        citySearch = new TextSearch("city", true);
                        citySearch.setSearchValue(cityName.trim());
                        multiSearchBean.addSearchBean(citySearch);

                        areaSearch = new TextSearch("area", true);
                        areaSearch.setSearchValue(areaName.trim());
                        multiSearchBean.addSearchBean(areaSearch);
                        //验证城市是否存在
                        List<DispatchLocation> cityList = dispatchDriver.getDispatchLocationManager().searchIdentify(multiSearchBean);
                        if (CollectionUtils.isEmpty(cityList)) {
                            errorList.add("第" + tipIndex + "行 省市区不存在 请确认数据是否正确");
                            continue;
                        } else {
                            multiSearchBean = new MultiSearchBean();
                            DispatchLocation dispatchLocation = cityList.get(0);
                            redStarMember.setProvince(dispatchLocation.getProvince());
                            redStarMember.setCity(dispatchLocation.getCity());
                            redStarMember.setArea(dispatchLocation.getArea());

                            redStarMember.setProvinceCode(dispatchLocation.getProvinceCode());
                            redStarMember.setCityCode(dispatchLocation.getCityCode());
                            redStarMember.setAreaCode(dispatchLocation.getAreaCode());

                            provinceCodeSearch = new TextSearch("provinceCode");
                            provinceCodeSearch.setSearchValue(dispatchLocation.getProvinceCode());

                            cityCodeSearch = new TextSearch("cityCode");
                            cityCodeSearch.setSearchValue(dispatchLocation.getCityCode());

                            areaCodeSearch = new TextSearch("areaCode");
                            areaCodeSearch.setSearchValue(dispatchLocation.getAreaCode());

                            nameSearch = new TextSearch("name");
                            nameSearch.setSearchValue(name.trim());

                            multiSearchBean.addSearchBean(provinceCodeSearch);
                            multiSearchBean.addSearchBean(cityCodeSearch);
                            multiSearchBean.addSearchBean(areaCodeSearch);
                            multiSearchBean.addSearchBean(nameSearch);
                        }

                        //根据省市区和小区名称查询小区
                        List<RedstarCommunity> redstarCommunityList = dispatchDriver.getRedstarCommunityManager().searchIdentify(multiSearchBean);
                        //该小区存在时
                        if (CollectionUtils.isEmpty(redstarCommunityList)) {
//                            errorList.add("第" + index + "行 小区不存在");
                            errorList.add(name.trim() + " 小区不存在,请创建小区");
                            break;
                        } else {
                            community = redstarCommunityList.get(0);
                        }
                    } else {
                        if (!community.getName().trim().equalsIgnoreCase(name.trim())) {
                            //如果小区名称不匹配
                            errorList.add("第" + tipIndex + "行 一个表格只允许上传一个小区的住宅信息");
                            continue;
                        }
                        redStarMember.setProvince(community.getProvince());
                        redStarMember.setProvinceCode(community.getProvinceCode());
                        redStarMember.setCity(community.getCity());
                        redStarMember.setCityCode(community.getCityCode());
                        redStarMember.setArea(community.getArea());
                        redStarMember.setAreaCode(community.getAreaCode());

                    }

                    if (community == null) {
                        errorList.add(name.trim() + " 小区不存在,请创建小区");
                        break;
                    } else if (!(DataUtil.getInt(community.getOwnerId(), 0) > 0)) {
                        errorList.add(name.trim() + " 小区不存在,请创建小区");
                        break;
                    }
                    redStarMember.setCommunityId(community.getId());
                    redStarMember.setCommunityName(community.getName());

                    multiSearchBean = new MultiSearchBean();
                    IntSearch idSearch = new IntSearch("communityId");
                    idSearch.setSearchValue(String.valueOf(redStarMember.getCommunityId()));
                    multiSearchBean.addSearchBean(idSearch);

                    multiSearchBean.addSearchBean(buildingSearch);
                    multiSearchBean.addSearchBean(roomSearch);
                    if (StringUtil.isValid(unitSearch.getSearchValue())) {
                        multiSearchBean.addSearchBean(unitSearch);
                    }

                    if (memberCacheMap == null) {
                        List<RedStarMember> memberCacheList = dispatchDriver.getRedStarMemberManager().searchIdentify(idSearch);
                        memberCacheMap = new HashMap<String, Integer>();
                        if (CollectionUtil.isValid(memberCacheList)) {
                            for (RedStarMember memberCacheItem : memberCacheList) {
                                //不管当前数据是否有单元存在 将小区内的住户都放到缓存
                                //if (StringUtil.isValid(redStarMember.getUnit())) {
                                //住户列表单元不为空的
                                if (StringUtil.isValid(memberCacheItem.getUnit())) {
                                    //有单元
                                    memberCacheMap.put(memberCacheItem.getBuilding().trim() + "-" + memberCacheItem.getUnit().trim() + "-" + memberCacheItem.getRoom().trim(), memberCacheItem.getId());
                                } else {
                                    //没单元
                                    memberCacheMap.put(memberCacheItem.getBuilding().trim() + "-" + memberCacheItem.getRoom().trim(), memberCacheItem.getId());
                                }
                                //}
                            }
                        }
                    }

                    if (StringUtil.isValid(redStarMember.getUnit())) {
                        //有单元
                        if (memberCacheMap.containsKey(redStarMember.getBuilding().trim() + "-" + redStarMember.getUnit().trim() + "-" + redStarMember.getRoom().trim())) {
                            errorList.add("第" + tipIndex + "行 住宅已存在");
                            continue;
                        }
                    } else {
                        //没单元
                        if (memberCacheMap.containsKey(redStarMember.getBuilding().trim() + "-" + redStarMember.getRoom().trim())) {
                            errorList.add("第" + tipIndex + "行 住宅已存在");
                            continue;
                        }
                    }


                    //信息所属当前用户
                    redStarMember.setOwnerId(employee.getId());
                    redStarMember.setOwnerXingMing(employee.getXingMing());

                    //上传人信息
                    redStarMember.setCreateEmployeeId(employee.getId());
                    redStarMember.setCreateXingming(employee.getXingMing());
                    redStarMember.setUpdateEmployeeId(employee.getId());
                    redStarMember.setUpdateEmployeeXingMing(employee.getXingMing());
                    redStarMember.setSource("employee");
                    //该记录是否存在
                    //装修情况 Y  住宅类型 房型 面积 N  //存在则查询 否则直接插入

                    //创建更新时间
                    redStarMember.setCreateDate(new Date());
                    redStarMember.setUpdateDate(new Date());

                    Integer dataId = dispatchDriver.getRedStarMemberManager().addBean(redStarMember);
                    successCount += 1;

                    if (StringUtil.isValid(redStarMember.getUnit())) {
                        //有单元
                        memberCacheMap.put(redStarMember.getBuilding().trim() + "-" + redStarMember.getUnit().trim() + "-" + redStarMember.getRoom().trim(), dataId);
                    } else {
                        //没单元
                        memberCacheMap.put(redStarMember.getBuilding().trim() + "-" + redStarMember.getRoom().trim(), dataId);
                    }


                    //操作日志
                    securityOperationLog = new NvwaSecurityOperationLog();
                    securityOperationLog.setOperatorId(employee.getId());
                    securityOperationLog.setOperator(employee.getXingMing());
                    securityOperationLog.setOperateResource(Member_Operate_Resource);
                    securityOperationLog.setCreateDate(new Date());
                    securityOperationLog.setBelongedId(LOG_BELONG_ID);
                    securityOperationLog.setOperationTypeField(ADD_OPERATION);
                    securityOperationLog.setOperateResourceId(String.valueOf(dataId));
                    securityOperationLog.setContent("添加住户");
                    nvwaDriver.getNvwaSecurityOperationLogManager().addBean(securityOperationLog);
                }

            }


            if (community != null) {
                //更新小区总户数
                IntSearch idSearch = new IntSearch("communityId");
                idSearch.setSearchValue(String.valueOf(community.getId()));
                //List<RedStarMember> memberList = dispatchDriver.getRedStarMemberManager().searchIdentify(idSearch);
                //根据小区id查询录入户数总数
                Integer currentMemberCount = nvwaDriver.getNvwaSecurityOperationLogManager().getAllCount(RedStarMember.class, idSearch);
                community.setAlreadyInputAmount(currentMemberCount);
                //已录入户数大于总户数时更新总户数
                if (currentMemberCount > (community.getRoomMount() == null ? 0 : community.getRoomMount())) {
                    community.setRoomMount(currentMemberCount);
                }

                //更新当前小区
                dispatchDriver.getRedstarCommunityManager().updateBean(community);
            }


            long t2 = System.currentTimeMillis();
            long t3 = t2 - t1;

            res.addKey("successCount", successCount);
            res.addKey("failCount", dataList.size() - successCount);
            res.addKey("errorList", errorList);
            res.addKey("executeTime", t3);
            res.setCode(HTTP_SUCCESS_CODE);
            res.setMessage("数据导入成功");
        } catch (Exception e) {
            e.printStackTrace();
            res.addKey("successCount", successCount);
            res.addKey("failCount", dataList.size() - successCount);
            res.addKey("errorList", errorList);
            setErrMsg(res, "第" + tipIndex + "行数据异常,请检查数据");
        }
        return res;
    }

}
