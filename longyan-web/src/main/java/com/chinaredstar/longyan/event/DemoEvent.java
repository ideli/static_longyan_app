package com.chinaredstar.longyan.event;

import com.xiwa.base.pipeline.PipelineContext;
import com.xiwa.base.pipeline.PipelineException;
import com.xiwa.zeus.bean.DBField;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 自定义事件 demo
 * <p/>
 * Created by zhangxuechao on 3/18/15.
 */
@Component("demoEvent")
public class DemoEvent extends BaseServerEvent {
    @Override
    public void execute(PipelineContext context) throws PipelineException {
        JSONObject request = JSONObject.fromObject(context.getRequest().getDataMap());

        List<DBField> saveFiels = (List<DBField>) context.getRequest().getDataValue("saveFields");

        logger.info(request);
        context.getResponse().addKey("response_key", "response_value");
        context.getResponse().setOk(false);
        context.getResponse().setMessage("test");

    }
}
