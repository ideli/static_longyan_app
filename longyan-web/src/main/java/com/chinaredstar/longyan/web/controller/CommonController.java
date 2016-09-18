package com.chinaredstar.longyan.web.controller;


import com.chinaredstar.commonBiz.bean.*;
import com.chinaredstar.longyan.bean.AppConfig;
import com.chinaredstar.longyan.bean.bo.AreaObject;
import com.chinaredstar.longyan.bean.bo.CityObject;
import com.chinaredstar.longyan.bean.bo.ProvinceObject;
import com.chinaredstar.commonBiz.bean.constant.CommonBizConstant;
import com.chinaredstar.commonBiz.manager.DispatchDriver;
import com.chinaredstar.commonBiz.manager.RedstarCommonManager;
import com.xiwa.base.bean.Identified;
import com.xiwa.base.bean.Response;
import com.xiwa.base.bean.ext.SimpleResponse;
import com.xiwa.base.bean.search.ext.IntSearch;
import com.xiwa.base.bean.search.ext.MultiSearchBean;
import com.xiwa.base.bean.search.ext.TextSearch;
import com.xiwa.base.manager.ManagerException;
import com.xiwa.base.pipeline.PipelineContext;
import com.xiwa.base.util.CollectionUtil;
import com.xiwa.base.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 通用接口
 * Created by wangj on 2015/7/21.
 */
@Controller
@RequestMapping(value = "/common")
public class CommonController extends BaseController implements CommonBizConstant {

    @Autowired
    private DispatchDriver dispatchDriver;

    @Autowired
    private RedstarCommonManager redstarCommonManager;

    @Autowired
    private AppConfig appConfig;

    @RequestMapping(value = "/get-app-config")
    public
    @ResponseBody
    Response appConfig() {
        PipelineContext pipelineContext = buildPipelineContent();
        pipelineContext.getResponse().addKey("config", appConfig);

        return pipelineContext.getResponse();
    }

    private List<ProvinceObject> provinceObjectList = new ArrayList<ProvinceObject>();

    /**
     * 读取城市区(县)列表
     * U-Common-0006
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/location/area")
    public
    @ResponseBody
    Response getLocationList() {
        PipelineContext pipelineContext = buildPipelineContent();
        logger.info("[CommonController][getLocationList][request] pipelineContext=" + JSONObject.fromObject(pipelineContext.getRequest().getDataMap()).toString());
        try {
            String cityCode = pipelineContext.getRequest().getString("cityCode");
            logger.info("[CommonController][getLocationList][request] cityCode=" + cityCode);
            List<DispatchLocation> locationList = dispatchDriver.getDispatchLocationManager().getBeanListByColumn("cityCode", cityCode, Identified.BEAN_NAME, true);
            logger.info("[CommonController][getLocationList][response] locationList=" + JSONArray.fromObject(locationList).toString());
            pipelineContext.getResponse().addKey("result", locationList);
        } catch (ManagerException e) {
            _error(e, pipelineContext);
        }
        logger.info("[CommonController][getLocationList][response] pipelineContext=" + JSONObject.fromObject(pipelineContext.getResponse().getDataMap()).toString());
        return pipelineContext.getResponse();
    }

    @RequestMapping(value = "/get-locations")
    public
    @ResponseBody
    Response test() {
        PipelineContext pipelineContext = buildPipelineContent();

        try {

            if(CollectionUtil.isInvalid(provinceObjectList)){
                List<DispatchProvince> dispatchProvinceList = dispatchDriver.getDispatchProvinceManager().getBeanList();
                List<DispatchCity> dispatchCityList = dispatchDriver.getDispatchCityManager().getBeanList();
                List<DispatchLocation> dispatchLocationList = dispatchDriver.getDispatchLocationManager().getBeanList();
                //组装结构体
                List<ProvinceObject> _provinceObjectList = new ArrayList<ProvinceObject>();
                for (DispatchProvince dispatchProvince : dispatchProvinceList) {
                    ProvinceObject provinceObject = new ProvinceObject();
                    provinceObject.setProvinceName(dispatchProvince.getProvince());
                    provinceObject.setProvinceCode(dispatchProvince.getProvinceCode());
                    List<CityObject> cityObjectList = new ArrayList<CityObject>();
                    for (DispatchCity dispatchCity : dispatchCityList) {
                        if (StringUtil.isValid(dispatchCity.getProvinceCode()) && dispatchCity.getProvinceCode().equalsIgnoreCase(provinceObject.getProvinceCode())) {
                            CityObject cityObject = new CityObject();
                            cityObject.setCityName(dispatchCity.getCity());
                            cityObject.setCityCode(dispatchCity.getCityCode());
                            List<AreaObject> areaObjectList = new ArrayList<AreaObject>();

                            for (DispatchLocation dispatchLocation : dispatchLocationList) {
                                if (StringUtil.isValid(dispatchLocation.getCityCode()) && dispatchLocation.getCityCode().equalsIgnoreCase(cityObject.getCityCode())) {
                                    AreaObject areaObject = new AreaObject();
                                    areaObject.setAreaName(dispatchLocation.getArea());
                                    areaObject.setAreaCode(dispatchLocation.getAreaCode());
                                    areaObjectList.add(areaObject);
                                }
                            }
                            cityObject.setAreaList(areaObjectList);
                            cityObjectList.add(cityObject);
                        }
                    }
                    provinceObject.setCityList(cityObjectList);
                    _provinceObjectList.add(provinceObject);
                }
                provinceObjectList=_provinceObjectList;
            }



            pipelineContext.getResponse().addKey("provinceList", provinceObjectList);
        } catch (ManagerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pipelineContext.getResponse();
    }


    //获取房屋类型数据
    @RequestMapping(value = "/roomType")
    @ResponseBody
    public Response getRoomType() {
        PipelineContext context = buildPipelineContent();
        Response res = context.getResponse();
        try {
            res.setCode(HTTP_SUCCESS_CODE);
            res.setMessage("查询成功");
            res.addKey("result", redstarCommonManager.getDataList(RedstarRoomType.class, null,new String[]{"sortNum"},new Boolean[]{Boolean.FALSE}));
        } catch (Exception e) {
            e.printStackTrace();
            setErrMsg(res, "服务器响应异常");
        }
        return res;
    }


    //获取房屋户型数据
    @RequestMapping(value = "/roomLayout")
    @ResponseBody
    public Response getRoomLayout() {
        PipelineContext context = buildPipelineContent();
        Response res = context.getResponse();
        try {
            res.addKey("result", redstarCommonManager.getDataList(RedstarRoomLayout.class, null));
            res.setCode(HTTP_SUCCESS_CODE);
            res.setMessage("查询成功");
        } catch (Exception e) {
            e.printStackTrace();
            setErrMsg(res, "服务器响应异常");
        }
        return res;
    }

    //获取住宅面积类型数据
    @RequestMapping(value = "/roomArea")
    @ResponseBody
    public Response getRoomArea() {
        PipelineContext context = buildPipelineContent();
        Response res = context.getResponse();
        try {
            res.addKey("result", redstarCommonManager.getDataList(RedstarRoomArea.class, null));
            res.setCode(HTTP_SUCCESS_CODE);
            res.setMessage("查询成功");
        } catch (Exception e) {
            e.printStackTrace();
            setErrMsg(res, "服务器响应异常");
        }
        return res;
    }

    //获取住宅装修情况
    @RequestMapping(value = "/renovationStatus")
    @ResponseBody
    public Response getRenovationStatus() {
        PipelineContext context = buildPipelineContent();
        Response res = context.getResponse();
        try {
            res.addKey("result", redstarCommonManager.getDataList(RedstarRenovationStatus.class, null));
            res.setCode(HTTP_SUCCESS_CODE);
            res.setMessage("查询成功");
        } catch (Exception e) {
            e.printStackTrace();
            setErrMsg(res, "服务器响应异常");
        }
        return res;
    }

    //app版本更新
    @RequestMapping(value = "/get-app-version",method = RequestMethod.POST)
    @ResponseBody
    public Response getAppVersion(String platformType) {
        PipelineContext context = buildPipelineContent();
        Response res = context.getResponse();
        try {
            if ((!Ios.equals(platformType))&&(!Android.equals(platformType))){
                setErrMsg(res,"platformType错误");
                return  res;
            }

            res.addKey("platformType",platformType);

            IntSearch activitySearch = new IntSearch("activity");
            activitySearch.setSearchValue("1");

            TextSearch codeSearch = new TextSearch("appCode");
            codeSearch.setSearchValue("LY");

            MultiSearchBean multiSearchBean = new MultiSearchBean();

            TextSearch typeSearch = new TextSearch("type");
            typeSearch.setSearchValue(platformType);

            multiSearchBean.addSearchBean(typeSearch);
            multiSearchBean.addSearchBean(activitySearch);
            multiSearchBean.addSearchBean(codeSearch);

            String[] orderColumn = new String[]{"id"};
            Boolean[] orders = new Boolean[]{Boolean.TRUE};

            List<RedstarVersion>  platformTypeList =  redstarCommonManager.getDataList(RedstarVersion.class, multiSearchBean, orderColumn, orders);
            if(!CollectionUtils.isEmpty(platformTypeList)){
                res.addKey("appVersion",platformTypeList.get(0).getVersion());
                res.addKey("appDownloadUrl",platformTypeList.get(0).getDownloadUrl());
            }

            MultiSearchBean staticSearchBean = new MultiSearchBean();
            typeSearch.setSearchValue("static");
            staticSearchBean.addSearchBean(typeSearch);
            staticSearchBean.addSearchBean(activitySearch);
            staticSearchBean.addSearchBean(codeSearch);
            List<RedstarVersion>  staticList =  redstarCommonManager.getDataList(RedstarVersion.class, staticSearchBean, orderColumn,orders);
            if(!CollectionUtils.isEmpty(staticList)){
                res.addKey("staticVersion",staticList.get(0).getVersion());
                res.addKey("staticDownloadUrl",staticList.get(0).getDownloadUrl());
            }

            res.setCode(HTTP_SUCCESS_CODE);
            res.setMessage("查询成功");
        } catch (Exception e) {
            e.printStackTrace();
            setErrMsg(res, "服务器响应异常");
        }
        return res;
    }

    //获取服务器时间
    @RequestMapping(value = "get-server-time",method = RequestMethod.POST)
    @ResponseBody
    public  Object  getServerTime(){
        SimpleResponse simpleResponse = new SimpleResponse();
        Date date = new Date();
        simpleResponse.addKey("currentDate",date);
        simpleResponse.addKey("currentTimeStamp",date.getTime());
        setSuccessMsg(simpleResponse);
        return simpleResponse;
    }



    /**
     * 只读取最新已激活的一条记录
     *
     * @return
     */
    @RequestMapping(value = "/get-loading-ad",method = RequestMethod.POST)
    @ResponseBody
    public Response getLoadingAd(){
        PipelineContext pipelineContext = buildPipelineContent();
        Response response = pipelineContext.getResponse();

        //激活为 1
        int activity =1;
        boolean isAsc=false;


        String orderColumn = "createDate";
        //查询最近 一条激活记录
        //激活  时间排序

        try{
            List<RedstarAppAd> redstarAppAds = dispatchDriver.getRedstarAppAdManager().getBeanListByColumn("activity",activity,orderColumn,isAsc);
            response.addKey("redstarAppAd",redstarAppAds.get(0));

        }catch (Exception exception){
            exception.printStackTrace();
            response.setOk(false);
            response.setMessage(exception.getMessage());
            response.setCode(10901);
            return response;
        }
        return response;
    }






}
