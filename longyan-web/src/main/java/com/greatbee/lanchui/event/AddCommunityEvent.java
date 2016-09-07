package com.greatbee.lanchui.event;

import com.xiwa.base.pipeline.PipelineContext;
import com.xiwa.base.pipeline.PipelineException;
import com.xiwa.base.util.StringUtil;
import com.xiwa.zeus.bean.DBField;
import com.xiwa.zeus.trinity.bean.Employee;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 自定义事件 demo
 * <p/>
 * Created by zhangxuechao on 6/17/16.
*/
@Component("addCommunityEvent")
public class AddCommunityEvent extends BaseServerEvent {
    @Override
    public void execute(PipelineContext context) throws PipelineException {
        JSONObject request = JSONObject.fromObject(context.getRequest().getDataMap());  //todo 转换为json格式
        Employee loginEmployee=this.getLoginEmployee();                                 //todo 获取当前登录员工信息
        List<DBField> saveFiels = (List<DBField>) context.getRequest().getDataValue("saveFields");
        for (DBField field : saveFiels) {
            if(field.getFieldName().equalsIgnoreCase("ownerId")){           //TODO String字符串对比
                //负责人ID
                field.setFieldValue(StringUtil.getString(loginEmployee.getId()));
            }
            if(field.getFieldName().equalsIgnoreCase("ownerXingMing")){
                //负责人姓名
                field.setFieldValue(StringUtil.getString(loginEmployee.getXingMing()));
            }

            if(field.getFieldName().equalsIgnoreCase("updateDate")){
                //负责人姓名
                field.setFieldValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            }

            if(field.getFieldName().equalsIgnoreCase("updateEmployeeXingMing")){
                //负责人姓名
                field.setFieldValue(StringUtil.getString(loginEmployee.getXingMing()));
            }
        }

    }
}
