package com.chinaredstar.longyan.web.controller;

import com.chinaredstar.commonBiz.bean.RedstarCommunityUpdateLog;
import com.chinaredstar.commonBiz.bean.constant.CommonBizConstant;
import com.chinaredstar.commonBiz.manager.DispatchDriver;
import com.chinaredstar.nvwaBiz.bean.NvwaEmployee;
import com.xiwa.base.bean.PaginationDescribe;
import com.xiwa.base.bean.Request;
import com.xiwa.base.bean.Response;
import com.xiwa.base.bean.search.ext.IntSearch;
import com.xiwa.base.bean.search.ext.MultiSearchBean;
import com.xiwa.base.pipeline.PipelineContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Created by StevenDong on 2016/9/27
 */
@Controller
@RequestMapping(value = "/audit")
public class AuditController extends BaseController implements CommonBizConstant {

    @Autowired
    private DispatchDriver dispatchDriver;

    // 审核通过
    private String strAuditOK = "2";
    // 审核失败
    private String strAuditNG = "3";
    // 审核失败
    private String strAuditNeedAction = "1";

    // 查询我的审核列表
    @RequestMapping(value = "/viewAuditList/{type}", method = RequestMethod.POST)
    @ResponseBody
    public Response getViewAuditList(@PathVariable("type") String strType) {
        PipelineContext pipelineContext = this.buildPipelineContent();
        Response res = pipelineContext.getResponse();

        try {
            // 查询参数设定
            // 登陆EmployeeID获得
            NvwaEmployee loginEmployee = this.getEmployeeromSession();
            if (loginEmployee == null || loginEmployee.getId() == 0) {
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

            IntSearch ownerIdSearch = new IntSearch("ownerId");
            ownerIdSearch.setSearchValue(String.valueOf(intEmployeeId));

            IntSearch reviewStatusSearch = null;
            if ("OK".equals(strType)) {
                reviewStatusSearch = new IntSearch("reviewStatus");
                reviewStatusSearch.setSearchValue(strAuditOK);
            } else if ("NG".equals(strType)) {
                reviewStatusSearch = new IntSearch("reviewStatus");
                reviewStatusSearch.setSearchValue(strAuditNG);
            } else if ("NEEDACTION".equals(strType)) {
                reviewStatusSearch = new IntSearch("reviewStatus");
                reviewStatusSearch.setSearchValue(strAuditNeedAction);
            }

            MultiSearchBean multiSearchBean = new MultiSearchBean();
            multiSearchBean.addSearchBean(ownerIdSearch);
            multiSearchBean.addSearchBean(reviewStatusSearch);

            PaginationDescribe<RedstarCommunityUpdateLog> result = (PaginationDescribe<RedstarCommunityUpdateLog>) dispatchDriver.getRedstarCommunityUpdateLogManager().searchBeanPage(page, pageSize, multiSearchBean, "updateDate", Boolean.FALSE);
            res.addKey("result", result);

            // 成功与否消息文字设置
            setSuccessMsg(res);
        } catch (Exception e) {
            setUnknowException(e, res);
        }
        return res;
    }


}
