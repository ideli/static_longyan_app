package com.chinaredstar.longyan.web.controller;

import com.chinaredstar.commonBiz.bean.RedstarCommunity;
import com.chinaredstar.commonBiz.bean.RedstarCommunityUpdateLog;
import com.chinaredstar.commonBiz.bean.constant.CommonBizConstant;
import com.chinaredstar.commonBiz.manager.DispatchDriver;
import com.chinaredstar.commonBiz.manager.RedstarCommonManager;
import com.chinaredstar.commonBiz.manager.RedstarCommunityUnitManager;
import com.chinaredstar.nvwaBiz.manager.NvwaDriver;
import com.xiwa.base.bean.PaginationDescribe;
import com.xiwa.base.bean.Response;
import com.xiwa.base.bean.SimplePaginationDescribe;
import com.xiwa.base.bean.search.ext.IntSearch;
import com.xiwa.base.pipeline.PipelineContext;
import com.xiwa.zeus.trinity.bean.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * Created by StevenDong on 2016/9/21
 */
@RequestMapping("/community")
@Controller
public class CommunityController extends BaseController implements CommonBizConstant {

    @Autowired
    private DispatchDriver dispatchDriver;


    //查询小区列表
    @RequestMapping(value = "/list/{type}")
    @ResponseBody
    public Response dataList(@PathVariable("type") String strType) {
        PipelineContext pipelineContext = this.buildPipelineContent();
        Response res = pipelineContext.getResponse();

        try {
            // 查询参数设定
            // 登陆EmployeeID获得
            Employee loginEmployee = this.getEmployeeromSession();
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
            } else if ("updateCommunity".equals(strType)){  // 完善的小区
                // TODO 审核被驳回的小区更新记录要显示吗
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


}
