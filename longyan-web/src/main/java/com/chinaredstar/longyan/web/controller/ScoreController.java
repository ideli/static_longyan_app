package com.chinaredstar.longyan.web.controller;


import com.chinaredstar.commonBiz.bean.ExtRedstarEmployeeMonth;
import com.chinaredstar.longyan.exception.BusinessException;
import com.chinaredstar.nvwaBiz.bean.NvwaEmployee;
import com.chinaredstar.commonBiz.bean.RedstarEmployeeMonth;
import com.chinaredstar.commonBiz.bean.RedstarScoreRule;
import com.chinaredstar.commonBiz.bean.constant.CommonBizConstant;
import com.chinaredstar.commonBiz.manager.DispatchDriver;
import com.chinaredstar.commonBiz.manager.RedstarCommonManager;
import com.chinaredstar.commonBiz.manager.RedstarEmployeeMonthManager;
import com.chinaredstar.commonBiz.manager.RedstarScoreRuleManager;
import com.chinaredstar.nvwaBiz.manager.NvwaDriver;
import com.xiwa.base.bean.PaginationDescribe;
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
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Created by niu on 2016/7/11.
 */
@RestController
@RequestMapping(value = "/score")
public class ScoreController extends BaseController implements CommonBizConstant {

    @Autowired
    private RedstarCommonManager redstarCommonManager;

    @Autowired
    private DispatchDriver dispatchDriver;
    @Autowired
    NvwaDriver nvwaDriver;

    @Autowired
    private RedstarScoreRuleManager redstarScoreRuleManager;

    @Autowired
    private RedstarEmployeeMonthManager redstarEmployeeMonthManager;


    @RequestMapping(value = "/rank", method = RequestMethod.POST)
    public Response rank() {
        PipelineContext context = buildPipelineContent();
        Response res = context.getResponse();
        try {
            Employee employee = getEmployeeromSession();
            Calendar calendar = Calendar.getInstance();
            Integer year = calendar.get(Calendar.YEAR);
            Integer month = calendar.get(Calendar.MONTH) + 1;
            List<Object> paramsList = new ArrayList<Object>();
            paramsList.add(year);
            paramsList.add(month);


            //List<ExtRedstarEmployeeMonth> result = redstarCommonManager.excuteSql(sb.toString(),paramsList,ExtRedstarEmployeeMonth.class);
            List<ExtRedstarEmployeeMonth> result = redstarEmployeeMonthManager.getRankDataList(paramsList, ExtRedstarEmployeeMonth.class);
            res.addKey("rank", result);

            paramsList.add(employee.getId());
            List<ExtRedstarEmployeeMonth> currentList = redstarEmployeeMonthManager.getCurrentUserDataList(paramsList,ExtRedstarEmployeeMonth.class);
            if (!CollectionUtils.isEmpty(currentList)){
                res.addKey("currentUser", currentList.get(0));
            }

            setSuccessMsg(res);
        } catch (Exception e) {
            setUnknowException(e,res);
        }
        return res;
    }


    @RequestMapping(value = "/rank-history", method = RequestMethod.POST)
    public Response rankHistory() {
        PipelineContext context = buildPipelineContent();
        Response res = context.getResponse();
        try {
            List<ExtRedstarEmployeeMonth> currentList = redstarEmployeeMonthManager.getHistoryList(null,ExtRedstarEmployeeMonth.class);
            res.addKey(Http_Default_Data, currentList);
            setSuccessMsg(res);
        } catch (Exception e) {
            setUnknowException(e,res);
        }
        return res;
    }

    @RequestMapping(value = "/my-score-history")
    public Response myScoreHistory() {
        PipelineContext context = buildPipelineContent();
        Response res = context.getResponse();
        try {
            Employee loginEmployee = getEmployeeromSession();
            if (loginEmployee == null) {
                response.setOk(false);
                response.setMessage("您的登录已超时，请重新登录");
                return response;
            }

            Integer page = context.getRequest().getInt(Param_Page);
            Integer pageSize = context.getRequest().getInt(Param_PageSize);

            if (page <= 0) {
                page = Page_Default;
            }
            if (pageSize <= 0) {
                pageSize = PageSize_Default;
            }

            //=====获取个人信息=======
            NvwaEmployee employee = (NvwaEmployee) nvwaDriver.getNvwaEmployeeManager().getBean(loginEmployee.getId());

            //=====获取个人当月获得积分=====
            Date date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            Integer year = calendar.get(Calendar.YEAR);
            Integer month = calendar.get(Calendar.MONTH) + 1;

            MultiSearchBean multiSearchBeanMonth = new MultiSearchBean();
            IntSearch employeeIdSearch = new IntSearch("employeeId");
            employeeIdSearch.setSearchValue(StringUtil.getString(loginEmployee.getId()));
            multiSearchBeanMonth.addSearchBean(employeeIdSearch);
            IntSearch yearSearch = new IntSearch("year");
            yearSearch.setSearchValue(StringUtil.getString(year));
            multiSearchBeanMonth.addSearchBean(yearSearch);
            IntSearch monthSearch = new IntSearch("month");
            monthSearch.setSearchValue(StringUtil.getString(month));
            multiSearchBeanMonth.addSearchBean(monthSearch);
            List<RedstarEmployeeMonth> employeeMonthList = dispatchDriver.getRedstarEmployeeMonthManager().searchIdentify(multiSearchBeanMonth);
            RedstarEmployeeMonth employeeMonth = new RedstarEmployeeMonth();
            if (CollectionUtil.isValid(employeeMonthList)) {
                employeeMonth = employeeMonthList.get(0);
            }


            //=====获取积分历史记录=======

            MultiSearchBean multiSearchBean = new MultiSearchBean();
            IntSearch userIdSearch = new IntSearch("userId");
            userIdSearch.setSearchValue(StringUtil.getString(loginEmployee.getId()));
            multiSearchBean.addSearchBean(userIdSearch);

            TextSearch userObjectSearch = new TextSearch("userObject");
            userObjectSearch.setSearchValue("employee");
            multiSearchBean.addSearchBean(userObjectSearch);

            PaginationDescribe paginationDescribe = dispatchDriver.getRedstarScoreLogManager().searchBeanPage(page, pageSize, multiSearchBean, "createDate", false);
            res.addKey("page_data", paginationDescribe);
            res.addKey("score", employee.getScore());
            res.addKey("score_month", employeeMonth.getScoreAmount());

            setSuccessMsg(res);
        }catch (BusinessException e){
            e.printStackTrace();
            res.setCode(DataUtil.getInt(e.getErrorCode(), 0));
            res.setMessage(e.getMessage());
        }catch (Exception e) {
           setUnknowException(e,res);
        }
        return res;
    }


    /**
     * 获取系统积分规则
     */
    @RequestMapping(value = "/score-rule-info" ,method = RequestMethod.POST)
    @ResponseBody
    public Response scoreRuleInfo() {
        PipelineContext context = buildPipelineContent();
        Response res = context.getResponse();
        try {
            List<RedstarScoreRule> list = redstarScoreRuleManager.getBeanList();
            res.addKey("rules",list);
            setSuccessMsg(res, "查询成功");
        } catch (ManagerException e) {
            setUnknowException(e,res);
        }
        return res;
    }
}
