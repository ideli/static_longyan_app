package com.chinaredstar.longyan.event;

import com.xiwa.base.bean.search.Comparison;
import com.xiwa.base.bean.search.Condition;
import com.xiwa.base.bean.search.ConditionDescribe;
import com.xiwa.base.pipeline.PipelineContext;
import com.xiwa.base.pipeline.PipelineException;
import com.xiwa.zeus.constant.DataType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by usagizhang on 15/4/24.
 */
@Component("demoBuildingCustomConditionEvent")
public class DemoBuildingCustomConditionEvent extends BaseServerEvent { //todo 演示构建自定义条件的事件
    @Override
    public void execute(PipelineContext context) throws PipelineException {
        List<Condition> conditions = new ArrayList<Condition>();
        //building condition
        Condition cod = new Condition();    // todo 创建新的条件
        cod.setConditionColumn("provinceCode"); //todo 设置条件的值
        cod.setConditionDescribes(new ConditionDescribe[]{new ConditionDescribe(Comparison.EQ, "120000", DataType.STRING)}); //todo 对条件的描述
        conditions.add(cod);  //todo 添加到条件集合中
        this.setCustomConditions(conditions, context);  //todo 调用父类BaseServerEvent中 设置返回的condition的方法

    }
}
