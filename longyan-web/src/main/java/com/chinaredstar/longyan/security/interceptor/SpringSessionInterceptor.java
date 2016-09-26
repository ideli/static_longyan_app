package com.chinaredstar.longyan.security.interceptor;

import com.chinaredstar.commonBiz.manager.DispatchDriver;
import com.chinaredstar.nvwaBiz.bean.NvwaEmployee;
import com.chinaredstar.nvwaBiz.manager.NvwaDriver;
import com.chinaredstar.uc.dubbo.core.api.IEmployeeService;
import com.chinaredstar.uc.dubbo.core.api.vo.PsSyncEmployeesVo;
import com.chinaredstar.uc.dubbo.core.api.vo.ServiceResult2;
import com.xiwa.base.bean.Constants;
import com.xiwa.base.bean.ext.SimpleResponse;
import com.xiwa.base.cmd.SessionTimeoutException;
import com.xiwa.base.util.StringUtil;
import com.xiwa.zeus.util.SessionTool;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by niu on 2016/5/5.
 */

/**
 * 登陆持久化拦截器
 */
public class SpringSessionInterceptor implements HandlerInterceptor {

    @Autowired
    private IEmployeeService iEmployeeService;

    @Autowired
    private NvwaDriver nvwaDriver;

    @Autowired
    private DispatchDriver dispatchDriver;

    public static void writeJSON(HttpServletResponse response, String jsonString) throws IOException {
        try {
            response.setContentType("application/json");
            response.getOutputStream().write(jsonString.getBytes("UTF-8"));
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (IOException e) {

        }
    }

    public static void writeJSON(HttpServletResponse response, Object o) throws IOException {
        writeJSON(response, JSONObject.fromObject(o).toString());
    }

    public static boolean isMatch(ServletRequest request, List<String> patternList) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String requestURI = httpServletRequest.getRequestURI();
        for (String pattern : patternList) {
            if (Pattern.compile(pattern).matcher(requestURI).matches()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        /**
         * preHandle方法是进行处理器拦截用的，顾名思义，该方法将在Controller处理之前进行调用，SpringMVC中的Interceptor拦截器是链式的，可以同时存在
         * 多个Interceptor，然后SpringMVC会根据声明的前后顺序一个接一个的执行，而且所有的Interceptor中的preHandle方法都会在
         * Controller方法调用之前调用。SpringMVC的这种Interceptor链式结构也是可以进行中断的，这种中断方式是令preHandle的返
         * 回值为false，当preHandle的返回值为false的时候整个请求就结束了。
         */
        try {

            String strSessionId = httpServletRequest.getHeader("x-auth-token");

            // sessionId 不为空
            if (StringUtil.isValid(strSessionId)) {
                HttpSession redisSession = httpServletRequest.getSession(false);

                if (redisSession != null) {
                    String strOpenId = String.valueOf(redisSession.getAttribute("openId"));

                    //有OpenId
                    if (StringUtil.isValid(strOpenId)) {
                        // 员工信息取得时间
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String strSystemDateTime = df.format(System.currentTimeMillis());

                        String strNvwaEmployee = String.valueOf(redisSession.getAttribute(SessionTool.SESSION_EMPLOYEE));
                        if (StringUtil.isValid(strNvwaEmployee)) { // session里存在员工信息（第二次登陆）
                            // session 里存储的员工信息取得时间
                            Date sessionDateTime = df.parse(strSystemDateTime);
                            // 计算3小时后的时间
                            sessionDateTime.setTime(sessionDateTime.getTime() + 180 * 60 * 1000);

                            // 每隔3小时去员工中心校验一次员工信息
                            if (sessionDateTime.compareTo(df.parse(strSystemDateTime)) < 0) {
                                //调用用户中心接口获取员工信息
                                ServiceResult2 ucEmployeesService = iEmployeeService.getBasicInfoByEmplid(strOpenId);
                                PsSyncEmployeesVo ucEmployeesInfo = (PsSyncEmployeesVo) ucEmployeesService.getData();

                                if ("I".equals(ucEmployeesInfo.getHrStatus())) {
                                    SimpleResponse response = new SimpleResponse();
                                    response.setOk(false);
                                    response.setMessage("无效用户");
                                    response.setCode(Constants.Error_Code_No_Session);
                                    JSONObject.fromObject(response).toString();
                                    writeJSON(httpServletResponse, response);
                                    return false;
                                }
                            }
                            return true;
                        } else { //session里未带入员工信息（初回登陆）
                            //根据openId查询员工信息
                            NvwaEmployee redstarEmployee = nvwaDriver.getNvwaEmployeeManager().getEmployeeById(strOpenId);
                            redisSession.setAttribute(SessionTool.SESSION_EMPLOYEE, JSONObject.fromObject(redstarEmployee).toString());
                            return true;
                        }
                    }
                }
            }

            //未登陆过
            throw new SessionTimeoutException("error_session_timeout");
        } catch (SessionTimeoutException e) {
            e.printStackTrace();
            SimpleResponse response = new SimpleResponse();
            response.setOk(false);
            response.setMessage("请重新登录");
            response.setCode(Constants.Error_Code_No_Session);
            JSONObject.fromObject(response).toString();
            writeJSON(httpServletResponse, response);
            return false;
        }
    }


    /**
     * 这个方法只会在当前这个Interceptor的preHandle方法返回值为true的时候才会执行。postHandle是进行处理器拦截用的，它的执行时间是在处理器进行处理之
     * 后，也就是在Controller的方法调用之后执行，但是它会在DispatcherServlet进行视图的渲染之前执行，也就是说在这个方法中你可以对ModelAndView进行操
     * 作。这个方法的链式结构跟正常访问的方向是相反的，也就是说先声明的Interceptor拦截器该方法反而会后调用，这跟Struts2里面的拦截器的执行过程有点像，
     * 只是Struts2里面的intercept方法中要手动的调用ActionInvocation的invoke方法，Struts2中调用ActionInvocation的invoke方法就是调用下一个Interceptor
     * 或者是调用action，然后要在Interceptor之前调用的内容都写在调用invoke之前，要在Interceptor之后调用的内容都写在调用invoke方法之后。
     */
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 该方法也是需要当前对应的Interceptor的preHandle方法的返回值为true时才会执行。该方法将在整个请求完成之后，也就是DispatcherServlet渲染了视图执行，
     * 这个方法的主要作用是用于清理资源的，当然这个方法也只能在当前这个Interceptor的preHandle方法的返回值为true时才会执行。
     */
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
