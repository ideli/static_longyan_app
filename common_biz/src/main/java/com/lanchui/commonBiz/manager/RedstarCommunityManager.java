package com.lanchui.commonBiz.manager;

import com.lanchui.commonBiz.bean.ExtEmployee;
import com.lanchui.commonBiz.bean.RedstarCommunity;
import com.xiwa.base.bean.SimplePaginationDescribe;
import com.xiwa.base.manager.BasicManager;
import com.xiwa.base.manager.ManagerException;

import java.util.Collection;
import java.util.List;

/**
 * Created by wangj on 2015/6/3.
 */
public interface RedstarCommunityManager extends BasicManager {

    List getDistanceDataList(Double longitude, Double latitude, Integer distance) throws ManagerException;

    List<RedstarCommunity> getDataList(Collection<Object> idList, String orderColumn, Boolean desc, Integer page, Integer pageSize);

    SimplePaginationDescribe<RedstarCommunity> getRedstarCommunity(String mallId, int page, int pageSize, String orderColumn, String isAscStr) throws ManagerException;

    int getCommunityCount(String mallId) throws ManagerException;


    List getDataList(String sql, List<Object> paramList, Integer page, Integer pageSize) throws ManagerException;

    int getCountByOwnerIdAndDate(int ownerId,String startDate, String endDate);

}
