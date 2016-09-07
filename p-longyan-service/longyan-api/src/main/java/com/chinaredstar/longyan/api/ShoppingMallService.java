package com.chinaredstar.longyan.api;

import com.chinaredstar.longyan.api.vo.Response;

/**
 * Created by lenovo1 on 2016/7/28.
 */
public interface ShoppingMallService {

    //商场组织列表
    Response shopingMallOrgList(String parentId);

    //根据商场组织获取商场列表
    Response shopingMallListByOrganization(String organizationId);

    //根据省份获取商场列表
    Response shopingMallListByProvince(String provinceCode);

}
