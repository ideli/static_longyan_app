package com.chinaredstar.longyan.web.controller;

import com.chinaredstar.commonBiz.bean.RedstarCommunity;
import com.chinaredstar.commonBiz.bean.RedstarCommunityUpdateLog;
import com.chinaredstar.commonBiz.bean.constant.CommonBizConstant;
import com.chinaredstar.commonBiz.manager.DispatchDriver;
import com.chinaredstar.commonBiz.util.DoubleUtil;
import com.chinaredstar.longyan.exception.FormException;
import com.chinaredstar.longyan.util.RateUtil;
import com.chinaredstar.nvwaBiz.bean.NvwaEmployee;
import com.xiwa.base.bean.PaginationDescribe;
import com.xiwa.base.bean.Request;
import com.xiwa.base.bean.Response;
import com.xiwa.base.bean.search.ext.IntSearch;
import com.xiwa.base.bean.search.ext.MultiSearchBean;
import com.xiwa.base.pipeline.PipelineContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;


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
    @RequestMapping(value = "/viewAuditList", method = RequestMethod.POST)
    @ResponseBody
    public Response getViewAuditList(String type) {
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
            if ("OK".equals(type)) {
                reviewStatusSearch = new IntSearch("reviewStatus");
                reviewStatusSearch.setSearchValue(strAuditOK);
            } else if ("NG".equals(type)) {
                reviewStatusSearch = new IntSearch("reviewStatus");
                reviewStatusSearch.setSearchValue(strAuditNG);
            } else if ("NEEDACTION".equals(type)) {
                reviewStatusSearch = new IntSearch("reviewStatus");
                reviewStatusSearch.setSearchValue(strAuditNeedAction);
            }

            MultiSearchBean multiSearchBean = new MultiSearchBean();
            multiSearchBean.addSearchBean(ownerIdSearch);
            multiSearchBean.addSearchBean(reviewStatusSearch);

            PaginationDescribe<RedstarCommunityUpdateLog> result = (PaginationDescribe<RedstarCommunityUpdateLog>) dispatchDriver.getRedstarCommunityUpdateLogManager().searchBeanPage(page, pageSize, multiSearchBean, "updateDate", Boolean.FALSE);

            List lstTemp = result.getCurrentRecords();
            if ("NEEDACTION".equals(type)) { // 待审核显示创建时间
                for (int i = 0; i < lstTemp.size(); i++) {
                    RedstarCommunityUpdateLog objTemp = (RedstarCommunityUpdateLog) lstTemp.get(i);
                    objTemp.setAuditShowDate(objTemp.getCreateDate());
                }
            } else { // 审核通过与不通过显示更新时间
                for (int i = 0; i < lstTemp.size(); i++) {
                    RedstarCommunityUpdateLog objTemp = (RedstarCommunityUpdateLog) lstTemp.get(i);
                    objTemp.setAuditShowDate(objTemp.getUpdateDate());
                }
            }
            res.addKey("result", result);

            // 成功与否消息文字设置
            setSuccessMsg(res);
        } catch (Exception e) {
            setUnknowException(e, res);
        }
        return res;
    }


    // 查询我的提交列表
    @RequestMapping(value = "/viewUpdateList", method = RequestMethod.POST)
    @ResponseBody
    public Response getViewUpdateList(String type) {
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

            IntSearch updateEmployeeIdSearch = new IntSearch("updateEmployeeId");
            updateEmployeeIdSearch.setSearchValue(String.valueOf(intEmployeeId));

            IntSearch reviewStatusSearch = null;
            if ("OK".equals(type)) {
                reviewStatusSearch = new IntSearch("reviewStatus");
                reviewStatusSearch.setSearchValue(strAuditOK);
            } else if ("NG".equals(type)) {
                reviewStatusSearch = new IntSearch("reviewStatus");
                reviewStatusSearch.setSearchValue(strAuditNG);
            } else if ("NEEDACTION".equals(type)) {
                reviewStatusSearch = new IntSearch("reviewStatus");
                reviewStatusSearch.setSearchValue(strAuditNeedAction);
            }

            MultiSearchBean multiSearchBean = new MultiSearchBean();
            multiSearchBean.addSearchBean(updateEmployeeIdSearch);
            multiSearchBean.addSearchBean(reviewStatusSearch);

            PaginationDescribe<RedstarCommunityUpdateLog> resultCommunityUpdateLog = (PaginationDescribe<RedstarCommunityUpdateLog>) dispatchDriver.getRedstarCommunityUpdateLogManager().searchBeanPage(page, pageSize, multiSearchBean, "updateDate", Boolean.FALSE);

            List lstTemp = resultCommunityUpdateLog.getCurrentRecords();
            if ("NEEDACTION".equals(type)) { // 待审核显示创建时间
                for (int i = 0; i < lstTemp.size(); i++) {
                    RedstarCommunityUpdateLog objTemp = (RedstarCommunityUpdateLog) lstTemp.get(i);
                    objTemp.setAuditShowDate(objTemp.getCreateDate());
                }
            } else { // 审核通过与不通过显示更新时间
                for (int i = 0; i < lstTemp.size(); i++) {
                    RedstarCommunityUpdateLog objTemp = (RedstarCommunityUpdateLog) lstTemp.get(i);
                    objTemp.setAuditShowDate(objTemp.getUpdateDate());
                }
            }

            res.addKey("result", resultCommunityUpdateLog);

            // 成功与否消息文字设置
            setSuccessMsg(res);
        } catch (Exception e) {
            setUnknowException(e, res);
        }
        return res;
    }

    //查询审核小区详细信息
    @RequestMapping(value = "/{id}")
    @ResponseBody
    public Response getDataItem(@PathVariable("id") Integer id) {
        PipelineContext pipelineContext = this.buildPipelineContent();
        Response res = pipelineContext.getResponse();

        try {
            RedstarCommunityUpdateLog communityUpdateLog = (RedstarCommunityUpdateLog) dispatchDriver.getRedstarCommunityUpdateLogManager().getBean(id);
            double occupanyRate;
            double inputRate;
            if (communityUpdateLog.getAlreadyCheckAmount() != null && communityUpdateLog.getRoomMount() != null && communityUpdateLog.getRoomMount() > 0) {
                occupanyRate = DoubleUtil.div(communityUpdateLog.getAlreadyCheckAmount(), communityUpdateLog.getRoomMount(), 2);
                communityUpdateLog.setOccupanyRate(RateUtil.getDoubleValue(occupanyRate * 100));
            } else {
                communityUpdateLog.setOccupanyRate(0.0);
            }
            if (communityUpdateLog.getAlreadyInputAmount() != null && communityUpdateLog.getRoomMount() != null && communityUpdateLog.getRoomMount() > 0) {
                inputRate = DoubleUtil.div(communityUpdateLog.getAlreadyInputAmount(), communityUpdateLog.getRoomMount(), 2);
                communityUpdateLog.setInputRate(RateUtil.getDoubleValue(inputRate * 100));
            } else {
                communityUpdateLog.setInputRate(0.0);
            }
            res.addKey("community", communityUpdateLog);
            setSuccessMsg(res);
        } catch (Exception e) {
            setErrMsg(res, "没有数据");
        }
        return res;
    }

    //依据审核状态变更小区信息
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public Response dataUpdate(String status) {
        PipelineContext pipelineContext = this.buildPipelineContent();
        Request request = pipelineContext.getRequest();
        Response res = pipelineContext.getResponse();

        try {

            Integer dataId = request.getInt("id");
            if (dataId == 0) {
                throw new FormException("小区id没有填写");
            }
            RedstarCommunityUpdateLog communityUpdateLog = (RedstarCommunityUpdateLog) dispatchDriver.getRedstarCommunityUpdateLogManager().getBean(dataId);
            if (communityUpdateLog == null) {
                throw new FormException("没有找到小区");
            }

            if ("OK".equals(status)) { // 小区修正信息审核成功

                // 小区更新履历表数据更新
                communityUpdateLog.setUpdateDate(new Date());
                communityUpdateLog.setReviewStatus(Integer.valueOf(strAuditOK));

                RedstarCommunity community = new RedstarCommunity(communityUpdateLog);
                community.setId(communityUpdateLog.getCommunityId());

                // 2个表更新
                dispatchDriver.getRedstarCommunityUpdateLogManager().updateBean(communityUpdateLog);
                dispatchDriver.getRedstarCommunityManager().updateBean(community);
            } else { // 审核失败
                // 更新者信息添加
                communityUpdateLog.setUpdateDate(new Date());
                //  审核状态变成失败
                communityUpdateLog.setReviewStatus(Integer.valueOf(strAuditNG));

                dispatchDriver.getRedstarCommunityUpdateLogManager().updateBean(communityUpdateLog);
            }
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


}
