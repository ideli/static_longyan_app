package com.chinaredstar.longyan.event;

import com.xiwa.base.pipeline.PipelineContext;
import com.xiwa.base.pipeline.PipelineException;
import com.xiwa.base.util.StringUtil;
import com.xiwa.zeus.bean.DBField;
import com.xiwa.zeus.trinity.bean.Employee;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 自定义事件 添加住宅
 * <p/>
 * Created by zhangxuechao on 6/17/16.
 */
@Component("addMemberEvent")
public class AddMemberEvent extends BaseServerEvent {
    @Override
    public void execute(PipelineContext context) throws PipelineException {

        //JSONObject request = JSONObject.fromObject(context.getRequest().getDataMap());
        Employee loginEmployee=this.getLoginEmployee();
        List<DBField> saveFiels = (List<DBField>) context.getRequest().getDataValue("saveFields");
        for (DBField field : saveFiels) {
            if(field.getFieldName().equalsIgnoreCase("ownerId")){
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
/*        JSONObject request = JSONObject.fromObject(context.getRequest().getDataMap());

        List<DBField> saveFiels = (List<DBField>) context.getRequest().getDataValue("saveFields");
        logger.info(request);
        context.getResponse().addKey("response_key", "response_value");
        context.getResponse().setOk(false);
        context.getResponse().setMessage("test");*/

    }
}
