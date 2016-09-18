package com.chinaredstar.longyan.event;

import com.chinaredstar.commonBiz.bean.RedstarCommunity;
import com.chinaredstar.commonBiz.manager.DispatchDriver;
import com.xiwa.base.pipeline.PipelineContext;
import com.xiwa.base.pipeline.PipelineException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import java.util.Date;
import java.util.Map;

/**分配小区
 */
@Component("dispatchCommunityEvent")
public class DispatchCommunityEvent extends BaseServerEvent {

    @Autowired
    private DispatchDriver dispatchDriver;

    @Override
    public void execute(PipelineContext context) throws PipelineException{

        Map<String,Object> dataMap = context.getRequest().getDataMap();

        if(!CollectionUtils.isEmpty(dataMap)){

            Object dataId = dataMap.get("operationId");

            if(dataId!=null){
                    try {
                        RedstarCommunity community = (RedstarCommunity) dispatchDriver.getRedstarCommunityManager().getBean(Integer.parseInt(dataId.toString()));
                        if(community!=null && community.getCreateDate()==null){
                            community.setCreateDate(new Date());
                            dispatchDriver.getRedstarCommunityManager().updateBean(community);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        throw new RuntimeException();
                    }
                }
            }
        }
}
