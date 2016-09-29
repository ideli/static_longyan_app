package com.chinaredstar.longyan.web.controller;


import com.chinaredstar.commonBiz.bean.constant.CommonBizConstant;
import com.chinaredstar.longyan.util.Encryption;
import com.redstar.sms.api.AppPushService;
import com.xiwa.base.bean.Response;
import com.xiwa.base.pipeline.PipelineContext;
import com.xiwa.base.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 社区授权
 * Created by wangj on 2015/7/21.
 */
@Controller
@RequestMapping(value = "/test")
public class TestController extends BaseController implements CommonBizConstant {

    @Autowired
    private AppPushService appPushService;

    @RequestMapping(value = "/index")
    public
    @ResponseBody
    Response index() {
        PipelineContext pipelineContext = buildPipelineContent();
        return pipelineContext.getResponse();
    }

    @RequestMapping(value = "/password")
    public
    @ResponseBody
    Response password() {
        PipelineContext pipelineContext = buildPipelineContent();
        String password = pipelineContext.getRequest().getString("password");
        if (StringUtil.isValid(password)) {
            password = Encryption.Encrypt(password);
            pipelineContext.getResponse().addKey("password", password);
        }
        return pipelineContext.getResponse();
    }


    @RequestMapping(value = "/push")
    @ResponseBody
    public Response pushMessageToClient() {
        PipelineContext pipelineContext = buildPipelineContent();
        String empCode = pipelineContext.getRequest().getString("empCode");
        String content = pipelineContext.getRequest().getString("content");
        if (StringUtil.isValid(empCode)) {
            pipelineContext.getResponse().getDataMap().putAll(appPushService.sendPush(content, content, "LY", empCode, null, 0, null));
        }
        return pipelineContext.getResponse();
    }

//    @Autowired
//    private DispatchDriver dispatchDriver;
//
//    @Autowired
//    private ReadCsv readCsv;
//    @Autowired
//    private StorageConfig storageConfig;
//    @Autowired
//    private ReadExcel readExcel;
//
//  /*  @Autowired
//    private ThreadPoolTaskExecutor taskExecutor;*/
//
//    @RequestMapping(value = "/import")
//    public
//    @ResponseBody
//    Response test() {
//        PipelineContext pipelineContext = buildPipelineContent();
//        String csvFilePath = pipelineContext.getRequest().getString("csvFilePath");
//        int importCount = 0;
//
//        try {
//            if (StringUtil.isValid(csvFilePath)) {
//                String[] fileType = csvFilePath.split("\\.");
//                List<List<String>> lists = null;
//                if (StringUtil.equals(fileType[1], "csv")) {
//                    lists = readCsv.readCSVFile(storageConfig.getStorage_config_store_path() + "/" + csvFilePath);
//                } else if (StringUtil.equals(fileType[1], "xls")) {
//                    lists = readExcel.execute(storageConfig.getStorage_config_store_path() + "/" + csvFilePath, 42);
//                } else if (StringUtil.equals(fileType[1], "xlsx")) {
//                    throw new ManagerException("请导入03版本的excel");
//                } else {
//                    throw new ManagerException("请导入excel或者csv文件");
//                }
//                logger.info("[MerchantImportController][productStockImport] lists.size()=" + lists.size());
//                boolean isFirst = false;
//                for (int j = 0; j < lists.size(); j++) {
//                    List<String> lineList = lists.get(j);
//                    logger.info("[MerchantImportController][productStockImport] lineList.size()=" + lineList.size());
//                    logger.info("[MerchantImportController][productStockImport] lineList=" + JSONArray.fromObject(lineList).toString());
//                    if (CollectionUtil.isValid(lineList) && !isFirst) {
//                        isFirst = true;//去掉第一行标题
//                        continue;
//                    }
//                    //记录数据有效
//                    // 导入逻辑,根据keyAddress查找有没有存在的室，如果没有就直接跳过，如果有，先去费用表查询有没有该条记录，如果有就覆盖（包括将未支付状态变成已支付状态），没有就添加，然后把最新的缴费周期更新到room表     如果excel中 月份没有日期的 就直接跳过  走下一个月
//                    if (CollectionUtil.isValid(lineList)) {
////                        //导入逻辑
//                        System.out.println(JSONArray.fromObject(lineList).toString());
//                        String employeeCode = lineList.get(0);
//                        String employeeRecord = lineList.get(1);
//                        String employeeXingMing = lineList.get(2);
//                        String ruzhiDate = lineList.get(3);
//                        String hrStatus = lineList.get(4);
//                        String gender = lineList.get(5);
//                        if (StringUtil.isValid(gender) && gender.equalsIgnoreCase("m")) {
//                            gender = "male";
//                        } else {
//                            gender = "female";
//                        }
//                        String businessUnitCode = lineList.get(6);
//                        String businessUnitName = lineList.get(7);
//                        String mallCode = lineList.get(8);
//                        String mallName = lineList.get(9);
//                        String departmentCode = lineList.get(10);
//                        String departmentName = lineList.get(11);
//
//                        int mallId = 0;
//                        int departmentId = 0;
//                        int employeeId = 0;
//                        if (StringUtil.isValid(mallCode)) {
//                            //查找商场
//                            RedstarShoppingMall mall = dispatchDriver.getRedstarShoppingMallManager().getShoppingMallByCode(mallCode);
//                            if (mall == null) {
//                                //没有找到商场，创建一个
//                                mall = new RedstarShoppingMall();
//                                mall.setName(mallName);
//                                mall.setMallCode(mallCode);
//                                mallId = dispatchDriver.getRedstarShoppingMallManager().addBean(mall);
//                            } else {
//                                //更新商场信息
//                                mallId = mall.getId();
//                                mall.setName(mallName);
//                                dispatchDriver.getRedstarShoppingMallManager().updateBean(mall);
//                            }
//                        }
//
//                        if (StringUtil.isValid(departmentCode)) {
//                            //查找部门
//                            RedstarDepartment department = dispatchDriver.getRedstarDepartmentManager().getDepartmentByCode(departmentCode);
//                            if (department == null) {
//                                //没有找到部门，创建一个
//                                department = new RedstarDepartment();
//                                department.setShowData(true);
//                                department.setCreateDate(new Date(System.currentTimeMillis()));
//                                department.setDepartmentCode(departmentCode);
//                                department.setName(departmentName);
//                                department.setBelongedId(10944);
//                                department.setPinYin("");
//                                department.setParentId(-1);
//                                departmentId = dispatchDriver.getRedstarDepartmentManager().addBean(department);
//                            } else {
//                                //更新原有部门信息
//                                departmentId = department.getId();
//                                department.setName(departmentName);
//                                dispatchDriver.getRedstarDepartmentManager().updateBean(department);
//                            }
//                        }
//
//                        if (StringUtil.isValid(employeeCode)) {
//                            //查找员工
//                            RedstarEmployee employee = dispatchDriver.getRedstarEmployeeManager().getEmployeeByCode(employeeCode);
//                            if (employee == null) {
//                                //insert
//                                employee = new RedstarEmployee();
//                                employee.setCreateDate(new Date(System.currentTimeMillis()));
//                                employee.setXingMing(employeeXingMing);
//                                employee.setEmployeeRecord(DataUtil.getInt(employeeRecord, 0));
//                                employee.setShowData(true);
//                                employee.setBelongedId(10944);
//                                employee.setHrStatus(hrStatus);
//                                employee.setGender(gender);
//                                employee.setBusinessUnitCode(businessUnitCode);
//                                employee.setBusinessUnitName(businessUnitName);
//                                employee.setDepartmentCode(departmentCode);
//                                employee.setDepartmentId(departmentId);
//                                employee.setSource("import");
//                                if (StringUtil.isValid(ruzhiDate)) {
//                                    employee.setEntryDate(StrToDate(ruzhiDate));
//                                }
//                                employee.setEmployeeCode(employeeCode);
//                                employee.setInputCommunityAmountRank(0);
//                                employee.setInputMemberAmountRank(0);
//
//                                employeeId = dispatchDriver.getRedstarEmployeeManager().addBean(employee);
//                            } else {
//                                //update
//                                employeeId = employee.getId();
//                                employee.setXingMing(employeeXingMing);
//                                employee.setEmployeeRecord(DataUtil.getInt(employeeRecord, 0));
//                                employee.setHrStatus(hrStatus);
//                                employee.setGender(gender);
//                                employee.setBusinessUnitCode(businessUnitCode);
//                                employee.setBusinessUnitName(businessUnitName);
//                                employee.setDepartmentCode(departmentCode);
//                                employee.setDepartmentId(departmentId);
//                                if (StringUtil.isValid(ruzhiDate)) {
//                                    employee.setEntryDate(StrToDate(ruzhiDate));
//                                }
//                                employee.setEmployeeCode(employeeCode);
//                                employee.setSource("import");
//                                dispatchDriver.getRedstarEmployeeManager().updateBean(employee);
//                            }
//                        }
//
//                        if (mallId > 0 && employeeId > 0) {
//                            //员工和商场的关系
//                            RedstarMallEmployee mallEmployee = dispatchDriver.getRedstarMallEmployeeManager().getRedstarMallEmployee(employeeId, mallId);
//                            if (mallEmployee == null) {
//                                //insert
//                                mallEmployee = new RedstarMallEmployee();
//                                mallEmployee.setEmployeeId(employeeId);
//                                mallEmployee.setEmployeeXingMing(employeeXingMing);
//                                mallEmployee.setShoppingMallId(mallId);
//                                mallEmployee.setShoppingMallName(mallName);
//                                dispatchDriver.getRedstarMallEmployeeManager().addBean(mallEmployee);
//                            } else {
//                                //update
//                                mallEmployee.setEmployeeId(employeeId);
//                                mallEmployee.setEmployeeXingMing(employeeXingMing);
//                                mallEmployee.setShoppingMallId(mallId);
//                                mallEmployee.setShoppingMallName(mallName);
//                                dispatchDriver.getRedstarMallEmployeeManager().updateBean(mallEmployee);
//                            }
//                        }
//                    }//lineList有效,没有直接跳过
//                }
//
//                //导入记录数量
//                pipelineContext.getResponse().addKey("importCount", importCount);
//                //导入成功标记
//                pipelineContext.getResponse().addKey("import", true);
//            } else {
//                pipelineContext.getResponse().setOk(false);
//            }
//        } catch (ManagerException e) {
//            e.printStackTrace();
//            logger.error(e);
//            logger.error(e.getMessage());
//            logger.error(e.toString());
//            pipelineContext.getResponse().setOk(false);
//            pipelineContext.getResponse().setMessage(e.getMessage());
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error(e);
//            logger.error(e.getMessage());
//            logger.error(e.toString());
//            pipelineContext.getResponse().setOk(false);
//            pipelineContext.getResponse().setMessage(e.getMessage());
//        }
//        logger.info("[MerchantImportController][productStockImport] response=" + JSONObject.fromObject(pipelineContext.getResponse().getDataMap()).toString());
//        return pipelineContext.getResponse();
//    }
//
//
//    @RequestMapping(value = "/areaInput")
//    public
//    @ResponseBody
//    Response areaInput() {
//        PipelineContext pipelineContext = buildPipelineContent();
//
//        try {
//            int count = 0;
//            List<RedstarCommunity> list = dispatchDriver.getRedstarCommunityManager().getBeanList();
//            for (RedstarCommunity item : list) {
//                if (StringUtil.isInvalid(item.getAreaCode()) && StringUtil.isValid(item.getArea()) && StringUtil.isValid(item.getCityCode())) {
//                    //没有填写省市区的
//                    MultiSearchBean multiSearchBean = new MultiSearchBean();
//                    TextSearch cityCodeSearch = new TextSearch("cityCode");
//                    cityCodeSearch.setSearchValue(item.getCityCode());
//
//                    TextSearch areaSearch = new TextSearch("area", true);
//                    areaSearch.setSearchValue(item.getArea());
//
//                    multiSearchBean.addSearchBean(cityCodeSearch);
//                    multiSearchBean.addSearchBean(areaSearch);
//
//                    List<DispatchLocation> locationList = dispatchDriver.getDispatchLocationManager().searchIdentify(multiSearchBean);
//                    if (CollectionUtil.isValid(locationList)) {
//                        DispatchLocation location = locationList.get(0);
//                        //update
//                        item.setProvince(location.getProvince());
//                        item.setProvinceCode(location.getProvinceCode());
//                        item.setCity(location.getCity());
//                        item.setArea(location.getArea());
//                        item.setAreaCode(location.getAreaCode());
//                        dispatchDriver.getRedstarCommunityManager().updateBean(item);
//                        count++;
//                    }
//                }
//            }
//            pipelineContext.getResponse().addKey("count", count);
//            pipelineContext.getResponse().addKey("size", list.size());
//        } catch (ManagerException e) {
//            e.printStackTrace();
//            logger.error(e);
//            logger.error(e.getMessage());
//            logger.error(e.toString());
//            pipelineContext.getResponse().setOk(false);
//            pipelineContext.getResponse().setMessage(e.getMessage());
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error(e);
//            logger.error(e.getMessage());
//            logger.error(e.toString());
//            pipelineContext.getResponse().setOk(false);
//            pipelineContext.getResponse().setMessage(e.getMessage());
//        }
//        logger.info("[MerchantImportController][productStockImport] response=" + JSONObject.fromObject(pipelineContext.getResponse().getDataMap()).toString());
//        return pipelineContext.getResponse();
//    }

    /**
     * 字符串转换成日期
     *
     * @param str
     * @return date
     */
    public static Date StrToDate(String str) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

/*    @RequestMapping("/thread")
    @ResponseBody
    public  Integer  myTestThread(){

        //执行异步任务
        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                for (int index =0;index<10;index++){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+"--->"+index);
                }
            }
        });


        return  200;
    }*/

}
