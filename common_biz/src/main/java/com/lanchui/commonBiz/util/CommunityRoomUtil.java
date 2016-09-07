package com.lanchui.commonBiz.util;

import com.lanchui.commonBiz.bean.RedstarCommunity;
import com.lanchui.commonBiz.bean.DispatchCommunityRoom;
import com.lanchui.commonBiz.bean.DispatchUser;
import com.lanchui.commonBiz.bean.DispatchUserRoom;
import com.lanchui.commonBiz.bean.constant.CommonBizConstant;
import com.lanchui.commonBiz.manager.DispatchDriver;
import com.xiwa.base.bean.search.ext.BooleanSearch;
import com.xiwa.base.bean.search.ext.IntSearch;
import com.xiwa.base.bean.search.ext.MultiSearchBean;
import com.xiwa.base.manager.ManagerException;
import com.xiwa.base.pipeline.PipelineContext;
import com.xiwa.base.util.CollectionUtil;
import com.xiwa.base.util.DataUtil;
import com.xiwa.base.util.StringUtil;

import java.util.List;

/**
 * Created by usagizhang on 15/12/11.
 */
public class CommunityRoomUtil implements CommonBizConstant {
    public static DispatchCommunityRoom getLoginRoom(DispatchUser user, RedstarCommunity community, DispatchDriver dispatchDriver, PipelineContext pipelineContext) throws ManagerException {
        MultiSearchBean searchBean = new MultiSearchBean();
        IntSearch userIdSearch = new IntSearch("userId");
        userIdSearch.setSearchValue(DataUtil.getString(user.getId(), "0"));
        searchBean.addSearchBean(userIdSearch);
        IntSearch communityIdSearch = new IntSearch("communityId");
        communityIdSearch.setSearchValue(DataUtil.getString(community.getId(), "0"));
        searchBean.addSearchBean(communityIdSearch);
        BooleanSearch markSearch = new BooleanSearch("loginMark");
        markSearch.setValue("true");
        searchBean.addSearchBean(markSearch);
        //查询当前社区 业主的房屋信息
        List<DispatchUserRoom> userRoom = dispatchDriver.getDispatchUserRoomManager().searchIdentify(searchBean, "createDate", false);
        if (CollectionUtil.isInvalid(userRoom)) {
            pipelineContext.getResponse().setCode(ERROR_CODE_NO_COMMUNITY_SESSION);
            throw new ManagerException("请您先添加房屋信息！");
        }
        DispatchUserRoom loginRoom = userRoom.get(0);
        //查询房屋信息

        DispatchCommunityRoom room = (DispatchCommunityRoom) dispatchDriver.getDispatchCommunityRoomManager().getBean(loginRoom.getRoomId());
        return room;
    }

    public static String getRoomAddress(RedstarCommunity community, DispatchCommunityRoom room) {
        String address = "";
        if (StringUtil.isValid(community.getAddress())) {
            address = address + community.getAddress();
        }
        if (StringUtil.isValid(room.getGardenName())) {
            address = address + room.getGardenName();
        }
        if (StringUtil.isValid(room.getBuildingName())) {
            address = address + room.getBuildingName()+"栋";
        }
        if (StringUtil.isValid(room.getUnitName())) {
            address = address + room.getUnitName()+"单元";
        }
        if (StringUtil.isValid(room.getRoom())) {
            address = address + room.getRoom()+"室";
        }
        return address;
    }
}
