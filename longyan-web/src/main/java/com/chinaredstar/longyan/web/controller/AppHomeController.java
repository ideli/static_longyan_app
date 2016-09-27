package com.chinaredstar.longyan.web.controller;

import com.chinaredstar.commonBiz.bean.RedstarEmployeeDayInput;
import com.chinaredstar.commonBiz.bean.RedstarMessageCenter;
import com.chinaredstar.commonBiz.bean.constant.CommonBizConstant;
import com.chinaredstar.commonBiz.manager.DispatchDriver;
import com.chinaredstar.commonBiz.manager.RedstarCommonManager;
import com.chinaredstar.nvwaBiz.bean.NvwaEmployee;
import com.xiwa.base.bean.Request;
import com.xiwa.base.bean.Response;
import com.xiwa.base.bean.search.ConditionType;
import com.xiwa.base.bean.search.ext.IntSearch;
import com.xiwa.base.bean.search.ext.MultiSearchBean;
import com.xiwa.base.pipeline.PipelineContext;
import com.xiwa.base.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by StevenDong on 2016/9/19
 */
@Controller
@RequestMapping(value = "/appHome")
public class AppHomeController extends BaseController implements CommonBizConstant {
    @Autowired
    private DispatchDriver dispatchDriver;

    @Autowired
    private RedstarCommonManager redstarCommonManager;

    // APP首页显示数据集合
    @RequestMapping(value = "/viewList", method = RequestMethod.POST)
    @ResponseBody
    public Response getViewListData(String longitude, String latitude, String provinceCode, String cityCode, String limitM) {
        PipelineContext pipelineContext = this.buildPipelineContent();
        Response res = pipelineContext.getResponse();
        Request req = pipelineContext.getRequest();

        try {
            // 查询参数设定
            // 登陆EmployeeID获得
            NvwaEmployee loginEmployee = this.getEmployeeromSession();
            if (loginEmployee == null || loginEmployee.getId() == 0) {
                setErrMsg(res, "用户ID参数缺失");
                return res;
            }
            int intEmployeeId = loginEmployee.getId();

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

            // 首页广告banner取得
            res.addKey("adBanner", getAdBanner());
            // 首页消息数取得
            res.addKey("myMessages", getMessageCount(intEmployeeId));
            // 查询我的小区总数
            res.addKey("myCommunities", getDataCountByEmployeeId(intEmployeeId));
            // 查询周边小区总数
            res.addKey("aroundCommunities", getDataCountByLongitudeAndLatitude(longitude, latitude, provinceCode, cityCode, limitM));
            // 查询龙榜排名
            res.addKey("dragonEyeRanking", getDragonEyeRanking(intEmployeeId));
            // 成功与否消息文字设置
            setSuccessMsg(res);
        } catch (Exception e) {
            setUnknowException(e, res);
        }
        return res;
    }

    /**
     * 我的小区数获得函数
     */
    public int getDataCountByEmployeeId(Integer employeeId) throws Exception {

        // 小区表ownerID是该查询员工的记录（员工负责小区数量）
        IntSearch ownerIdSearch = new IntSearch("ownerId");
        ownerIdSearch.setSearchValue(String.valueOf(employeeId));
        List lstOwerIdResults = dispatchDriver.getRedstarCommunityManager().searchIdentify(ownerIdSearch);

        // 小区更新记录表updateId是该查询员工的记录（员工完善的小区数量）
        IntSearch updateIdSearch = new IntSearch("updateEmployeeId");
        updateIdSearch.setSearchValue(String.valueOf(employeeId));
        List lstUpdateIdResults = dispatchDriver.getRedstarCommunityUpdateLogManager().searchIdentify(updateIdSearch);

        return lstOwerIdResults.size() + lstUpdateIdResults.size();
    }

    /**
     * 周边小区数获得函数
     */
    public int getDataCountByLongitudeAndLatitude(String longitude, String latitude, String provinceCode,
                                                  String cityCode, String limitM) throws Exception {

        int intLimtM = Integer.parseInt(limitM);
        StringBuffer sb = new StringBuffer();

        sb.append("Select c.id,c.city,c.province, c.name,c.longitude, c.latitude, ");
        sb.append(" round(6378.138 * 2 * asin(  ");
        sb.append(" sqrt( pow(sin((c.latitude * pi() / 180 - ?*pi() / 180) / 2),2) + cos(c.latitude * pi() / 180) ");
        sb.append(" * cos(?*pi() / 180) * pow(  ");
        sb.append(" sin((c.longitude * pi() / 180 - ?*pi()/180) / 2),2))) * 1000) AS distance   ");
        sb.append(" FROM xiwa_redstar_community c where c.longitude>0 and c.latitude>0 and c.provinceCode = ? and c.cityCode = ? HAVING distance < ?");

        String querySQL = sb.toString();

        List<Object> paramsList = new ArrayList<Object>();
        paramsList.add(Double.parseDouble(latitude));
        paramsList.add(Double.parseDouble(latitude));
        paramsList.add(Double.parseDouble(longitude));
        paramsList.add(provinceCode);
        paramsList.add(cityCode);
        paramsList.add(intLimtM);

        //搜索结果（以员工GPS位置为圆心半径intLimtM内所有小区数量）
        List lsAroundCommunity = redstarCommonManager.excuteBySql(querySQL, paramsList);
        return lsAroundCommunity.size();
    }

    /**
     * 龙榜排名获得函数
     */
    public int getDragonEyeRanking(Integer employeeId) throws Exception {

        IntSearch employeeIdSearch = new IntSearch("employeeId");
        employeeIdSearch.setSearchValue(String.valueOf(employeeId));
        List<RedstarEmployeeDayInput> memberRanking = dispatchDriver.getRedstarEmployeeDayInputManager().
                searchIdentify(employeeIdSearch, "scoreRank", Boolean.FALSE);

        // 最新排名只可能有一个，所以降序查找出数据后取首位
        return memberRanking.get(0).getScoreRank();
    }

    /**
     * 首页广告banner数据获得函数
     */
    public List getAdBanner() throws Exception {

        // 系统时间在广告开始结束时间内的，activity为激活的广告按sortNum顺取出
        StringBuffer sb = new StringBuffer();
        sb.append("Select android1280p,ios55,url,title,sortNum ");
        sb.append("FROM xiwa_redstar_app_ad ");
        sb.append("WHERE activity='1' AND beginDatetime <= ? AND endDatetime > ? AND android1280p <> '' AND ios55 <> ''");
        sb.append("ORDER BY sortNum DESC");
        String querySQL = sb.toString();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strSysytemDateTime = df.format(System.currentTimeMillis());
        List<Object> paramsList = new ArrayList<Object>();
        paramsList.add(strSysytemDateTime);
        paramsList.add(strSysytemDateTime);

        List lsADs = redstarCommonManager.excuteBySql(querySQL, paramsList);
        List<HashMap> lsADObjs = new ArrayList<HashMap>();

        // 查询结果list转化成输出对象
        for (int i = 0; i < lsADs.size(); i++) {
            Object[] objAd = (Object[]) lsADs.get(i);
            HashMap hmADObj = new HashMap();
            hmADObj.put("android1280p", objAd[0]);
            hmADObj.put("ios55", objAd[1]);
            hmADObj.put("url", objAd[2]);
            hmADObj.put("title", objAd[3]);
            hmADObj.put("sortNum", objAd[4]);

            lsADObjs.add(hmADObj);
        }
        return lsADObjs;
    }

    /**
     * 推送消息获得函数
     */
    public int getMessageCount(Integer employeeId) throws Exception {

        MultiSearchBean multiSearchBean = new MultiSearchBean();
        multiSearchBean.setCondition(ConditionType.AND);

        IntSearch recipientIdSearch = new IntSearch("recipientID");
        recipientIdSearch.setSearchValue(String.valueOf(employeeId));
        multiSearchBean.addSearchBean(recipientIdSearch);

        IntSearch readFlgSearch = new IntSearch("readFlg");
        readFlgSearch.setSearchValue("0");
        multiSearchBean.addSearchBean(readFlgSearch);

        // 消息接收者ID为该员工并且未读的消息按创建时间倒序
        List<RedstarMessageCenter> message = dispatchDriver.getRedstarMessageCenterManager().
                searchIdentify(multiSearchBean, "createDate", Boolean.FALSE);

        // 新消息数返回
        return message.size();
    }
}
