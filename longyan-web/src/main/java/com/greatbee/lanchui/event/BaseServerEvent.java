package com.greatbee.lanchui.event;

import com.lanchui.commonBiz.manager.DispatchDriver;
import com.xiwa.base.bean.search.Condition;
import com.xiwa.base.pipeline.Pipeline;
import com.xiwa.base.pipeline.PipelineContext;
import com.xiwa.base.util.CollectionUtil;
import com.xiwa.security.bean.constant.AuthTarget;
import com.xiwa.security.web.common.SecurityTool;
import com.xiwa.zeus.bean.constant.ServerEventConstant;
import com.xiwa.zeus.bean.constant.ZeusConstant;
import com.xiwa.zeus.trinity.bean.Employee;
import com.xiwa.zeus.util.SessionTool;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 3/18/15.
 */
public abstract class BaseServerEvent implements Pipeline, ZeusConstant, ServerEventConstant {
    protected static Logger logger = Logger.getLogger(BaseServerEvent.class);
    @Autowired
    protected DispatchDriver dispatchDriver;

    /**
     * 获取管道里面的ID参数
     *
     * @param context
     * @param defaultValue
     * @return
     */
    protected int getOperationId(PipelineContext context, int defaultValue) {
        return context.getRequest().getInt(OperationId, defaultValue);
    }

    /**
     * 获取管道里面的ID参数
     *
     * @param context *
     * @return
     */
    protected int getOperationId(PipelineContext context) {
        return this.getOperationId(context, -1);
    }

    /**
     * get ModuleName
     *
     * @param context
     * @return
     */
    protected String getModuleName(PipelineContext context) {   //todo 得到模板名字
        return context.getRequest().getString(ModuleName);
    }

    /**
     * 获取表单的数据
     *
     * @param context
     * @return
     *
     */
    protected Map<String, Object> getFormValue(PipelineContext context) {
        Map<String, Object> formValue = new HashMap<String, Object>();
        Map<String, Object> request = context.getRequest().getDataMap();    //TODO 得到通道中的数据
        Iterator it = request.keySet().iterator();
        while (it.hasNext()) {      //todo 迭代数据
            String key = it.next().toString();
            Object value = request.get(key);
            if (key.startsWith(FormPrefix)) {       //todo 判断key是否以FormPrefix前缀开头的
                formValue.put(key.replace(FormPrefix, ""), value);
            }
        }
        return formValue;
    }

    /**
     * 获取提交表单的值
     *
     * @param context
     * @param formName
     * @return
     */
    protected Object getFormValue(PipelineContext context, String formName) {
        Map<String, Object> formValues = this.getFormValue(context);    //todo 过去表单中的数据
        if (formValues != null && formValues.containsKey(formName)) {   // todo 判断表单中的数据不为空，且.containsKey(formName)为true,即formName不为空
            return formValues.get(formName);    //todo （根据表单名）得到表单中各元素的值
        }
        return null;
    }

    /**
     * 获取登陆的员工信息
     *
     * @return
     */
    protected Employee getLoginEmployee() {
        //检查更新员工信息
        if (SecurityTool.isSecurityLogin(AuthTarget.Employee)) {
            Employee employee = ((Employee) SessionTool.getSessionDataObject(SessionTool.SESSION_EMPLOYEE));
            return employee;
        } else {
            return null;
        }
    }

    /**
     * 设置返回的condition
     *
     * @param conditions
     * @param context
     */
    protected void setCustomConditions(List<Condition> conditions, PipelineContext context) {
        if (context != null && CollectionUtil.isValid(conditions)) {
            context.getResponse().addKey(ServerEventConstant.RESPONSE_CUSTOM_CONDITIONS, conditions);
        }
    }

}
