package com.chinaredstar.longyan.web.controller;

import com.chinaredstar.commonBiz.bean.RedstarEmployeeDayInput;
import com.chinaredstar.commonBiz.bean.constant.CommonBizConstant;
import com.chinaredstar.commonBiz.manager.DispatchDriver;
import com.chinaredstar.commonBiz.manager.RedstarCommonManager;
import com.xiwa.base.bean.Request;
import com.xiwa.base.bean.Response;
import com.xiwa.base.bean.search.ext.IntSearch;
import com.xiwa.base.pipeline.PipelineContext;
import com.xiwa.base.util.StringUtil;
import com.xiwa.zeus.trinity.bean.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by StevenDong on 2016/9/19 0019.
 */
@Controller
@RequestMapping(value = "/appHome", method = RequestMethod.POST)
public class AppHomeController extends BaseController implements CommonBizConstant {
    @Autowired
    private DispatchDriver dispatchDriver;

    @Autowired
    private RedstarCommonManager redstarCommonManager;

    // APP首页显示数据集合
    @RequestMapping(value = "/viewList")
    @ResponseBody
    public Response getViewListData() {
        PipelineContext pipelineContext = this.buildPipelineContent();
        Response res = pipelineContext.getResponse();
        Request req = pipelineContext.getRequest();

        try {
            // 查询参数设定
            // 登陆EmployeeID获得
            Employee loginEmployee = this.getEmployeeromSession();
            if (loginEmployee.getId() == 0) {
                setErrMsg(res, "参数缺失");
                return res;
            }
            int intEmployeeId = loginEmployee.getId();

            // 所在经纬度,省市code获得
            String longitude = req.getString("longitude");
            String latitude = req.getString("latitude");
            String provinceCode = req.getString("provinceCode");
            String cityCode = req.getString("cityCode");

            if (StringUtil.isInvalid(latitude) || StringUtil.isInvalid(longitude)
                    || StringUtil.isInvalid(provinceCode) || StringUtil.isInvalid(cityCode)) {
                setErrMsg(res, "经纬度参数缺失");
                return res;
            }

            // 首页广告banner取得
            res.addKey("adBanner", getAdBanner());
            // 首页消息数取得
            res.addKey("myMessages", getMessageCount(intEmployeeId));
            // 查询我的小区总数
            res.addKey("myCommunities", getDataCountByEmployeeId(intEmployeeId));
            // 查询周边小区总数
            res.addKey("aroundCommunities", getDataCountByLongitudeAndLatitude(latitude, longitude, provinceCode, cityCode));
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

        // 小区ownerID是该查询员工的记录（员工负责小区数量）
        IntSearch ownerIdSearch = new IntSearch("ownerId");
        ownerIdSearch.setSearchValue(String.valueOf(employeeId));

        List lstResults = dispatchDriver.getRedstarCommunityManager().searchIdentify(ownerIdSearch);
        return lstResults.size();
    }

    /**
     * 周边小区数获得函数
     */
    public int getDataCountByLongitudeAndLatitude(String longitude, String latitude, String provinceCode, String cityCode) throws Exception {

        final int intLimtKm = 5000;
        StringBuffer sb = new StringBuffer();

        sb.append("Select c.id,c.city,c.province, c.name,c.longitude, c.latitude, ");
        sb.append(" round(6378.138 * 2 * asin(  ");
        sb.append(" sqrt( pow(sin((c.latitude * pi() / 180 - ?*pi() / 180) / 2),2) + cos(c.latitude * pi() / 180) ");
        sb.append(" * cos(?*pi() / 180) * pow(  ");
        sb.append(" sin((c.longitude * pi() / 180 - ?*pi()/180) / 2),2))) * 1000) AS distance   ");
        sb.append(" FROM xiwa_redstar_community c where c.longitude>0 and c.latitude>0 and c.provinceCode = ? and c.cityCode = ? HAVING distance < ?");

        String querySQL = sb.toString();

        List<Object> paramsList = new ArrayList<Object>();
        paramsList.add(Double.parseDouble(longitude));
        paramsList.add(Double.parseDouble(longitude));
        paramsList.add(Double.parseDouble(latitude));
        paramsList.add(provinceCode);
        paramsList.add(cityCode);
        paramsList.add(intLimtKm);

        //搜索结果（以员工GPS位置为圆心半径5KM内所有小区数量）
        List lsAroundCommunity = redstarCommonManager.excuteBySql(querySQL, paramsList);
        return lsAroundCommunity.size();
    }

    /**
     * 龙榜排名获得函数
     */
    public int getDragonEyeRanking(Integer employeeId) throws Exception {

        IntSearch employeeIdSearch = new IntSearch("employeeId");
        employeeIdSearch.setSearchValue(String.valueOf(employeeId));
        List<RedstarEmployeeDayInput> memberRanking = dispatchDriver.getRedstarEmployeeDayInputManager().searchIdentify(employeeIdSearch, "scoreRank", false);

        // 最新排名只可能有一个，所以降序查找出数据后取首位
        return memberRanking.get(0).getScoreRank();
    }

    /**
     * 首页广告banner数据获得函数
     */
    public List getAdBanner() throws Exception {

        // 系统时间在广告开始结束时间内的，activity为激活的广告按sortNum顺取出
        StringBuffer sb = new StringBuffer();
        sb.append("Select android720p,adnroid1280p,ios47,ios55,ios40,ios35,url,title,sortNum ");
        sb.append("FROM xiwa_redstar_app_ad ");
        sb.append("WHERE activity='1' AND beginDatetime <= ? AND endDatetime > ? ");
        sb.append("ORDER BY sortNum DESC");
        String querySQL = sb.toString();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strSysytemDateTime = df.format(System.currentTimeMillis());
        List<Object> paramsList = new ArrayList<Object>();
        paramsList.add(strSysytemDateTime);
        paramsList.add(strSysytemDateTime);

        List lsAD = redstarCommonManager.excuteBySql(querySQL, paramsList);

        return lsAD;
    }

    /**
     * 推送消息获得函数
     */
    public int getMessageCount(Integer employeeId) throws Exception {
        // TODO
        return 0;
    }
}
