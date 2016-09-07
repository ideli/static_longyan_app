package com.greatbee.web.controller;

import com.chinaredstar.longyan.api.CommonService;
import com.chinaredstar.longyan.api.CommunityService;
import com.chinaredstar.longyan.api.vo.Community;
import com.chinaredstar.longyan.api.vo.Response;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by mdc on 2016/8/24.
 */
@RestController
@RequestMapping("/common")
public class CommunityCommonController {

    @Autowired
    private CommonService commonService;

    @Autowired
    private CommunityService communityService;

    @RequestMapping("/province/list")
    public Response provinceList() {
        return commonService.provinceList();
    }

    @RequestMapping("/city/list")
    public Response cityList(String provinceCode) {
        return commonService.cityList(provinceCode);
    }

    @RequestMapping("/area/list")
    public Response areaList(String cityCode) {
        return commonService.areaList(cityCode);
    }


    @RequestMapping("/list")
    public Response communityList(String provinceCode, String cityCode, String areaCode, String communityName, int page, int pageSize,
                                  String orderBy, String isAscStr) {

        Community community = new Community();
        community.setProvinceCode(provinceCode);
        community.setCityCode(cityCode);
        community.setAreaCode(areaCode);
        community.setCommunityName(communityName);
        community.setPage(page);
        community.setPageSzie(pageSize);
        community.setOrderBy(orderBy);

        boolean isAsc = false;
        if (StringUtils.isNotEmpty(isAscStr)) {
            if ("true".equals(isAscStr)) {
                isAsc = true;
            }
        }

        community.setIsAsc(isAsc);
        return communityService.communityList(community);
    }

    @RequestMapping("/mall-community-list")
    public Response communityMallCommunityList(String mallId, String orderBy, String isAsc, int page, int pageSize) {
        return communityService.communityMallCommunityList(mallId, orderBy, isAsc, page, pageSize);
    }

    @RequestMapping("/employee-community-list")
    public Response employeeCommunityList(String employeeCode, int page, int pageSize, String orderBy, String isAsc) {
        return communityService.employeeCommunityList(employeeCode, page, pageSize, orderBy, isAsc);
    }


    @RequestMapping("/member-list")
    public Response memberList(String communityId, int page, int pageSize, String orderBy, String isAsc) {
        return communityService.memberList(communityId, page, pageSize, orderBy, isAsc);
    }


}
