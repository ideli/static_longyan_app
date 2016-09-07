package com.chinaredstar.longyan.api;

import com.chinaredstar.longyan.api.vo.Community;
import com.chinaredstar.longyan.api.vo.Response;

/**
 * Created by mdc on 2016/7/28.
 */
public interface CommunityService {
    
    //搜索小区列表
    Response communityList(Community community);

    //根据某个商场的辐射半径获取小区列表
    Response communityMallCommunityList(String mallId,String orderBy,String isAsc,int page,int pageSize);

    //获取某员工所分配的小区列表
    Response employeeCommunityList(String employeeCode,int page,int pageSize,String orderBy,String isAsc);

    //根据小区来获取对应住宅列表
    Response memberList(String communityId,int page,int pageSize,String orderBy,String isAsc);
}
