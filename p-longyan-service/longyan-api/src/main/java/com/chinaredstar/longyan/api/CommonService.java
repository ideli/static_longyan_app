package com.chinaredstar.longyan.api;

import com.chinaredstar.longyan.api.vo.Response;

/**
 * Created by mdc on 2016/7/28.
 */
public interface CommonService {
    //省列表
    Response provinceList();
    //市列表
    Response cityList(String provinceCode);
    //区列表
    Response areaList(String cityCode);
}
