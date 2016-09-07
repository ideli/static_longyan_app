package com.chinaredstar.longyan.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.chinaredstar.longyan.api.CommonService;
import com.chinaredstar.longyan.api.vo.Response;
import com.chinaredstar.longyan.model.DispatchCity;
import com.chinaredstar.longyan.model.DispatchLocation;
import com.chinaredstar.longyan.model.DispatchProvince;
import com.chinaredstar.longyan.repository.DispatchCityRepository;
import com.chinaredstar.longyan.repository.DispatchLocationRepository;
import com.chinaredstar.longyan.repository.DispatchProvinceRepository;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommonServiceImpl extends BasicService implements CommonService {
    protected static org.slf4j.Logger logger = LoggerFactory.getLogger(CommonServiceImpl.class);

    @Autowired
    private DispatchProvinceRepository dispatchProvinceRepository;

    @Autowired
    private DispatchCityRepository dispatchCityRepository;

    @Autowired
    private DispatchLocationRepository dispatchLocationRepository;

    /**
     * 省列表
     *
     * @return response
     */
    @Override
    @Transactional(readOnly = true)
    public Response provinceList() {
        try {

            List<DispatchProvince> provinceList = dispatchProvinceRepository.findAll();
            successResponse("查询成功");
            response.addKey("provinceList", provinceList);

        } catch (Exception exception) {
            exception.printStackTrace();
            logger.error(exception.getMessage());
            errorResponse(EXCEPTION_CODE, "查询省列表异常");
            return response;

        }
        return response;
    }

    /**
     * 市列表
     *
     * @param provinceCode
     * @return response
     */
    @Override
    @Transactional(readOnly = true)
    public Response cityList(String provinceCode) {
        //1.验证参数
        if (StringUtils.isBlank(provinceCode)) {
            errorResponse(-1001, "provinceCodeb不能为空");
            return response;
        }
        try {
            //2.获取结果
            List<DispatchCity> cityList = dispatchCityRepository.findByProvinceCode(provinceCode);
            successResponse("查询成功");
            response.addKey("cityList", cityList);

        } catch (Exception exception) {
            exception.printStackTrace();
            logger.error(exception.getMessage());
            errorResponse(EXCEPTION_CODE, "查询市列表异常");
            return response;

        }
        return response;
    }

    /**
     * 区列表
     *
     * @param cityCode
     * @return response
     */
    @Override
    @Transactional(readOnly = true)
    public Response areaList(String cityCode) {
        //1.验证参数
        if (StringUtils.isBlank(cityCode)) {
            errorResponse(-1001, "cityCode不能为空");
            return response;
        }
        try {
            //2.获取结果
            List<DispatchLocation> locationList = dispatchLocationRepository.findByCityCode(cityCode);
            successResponse("查询成功");
            response.addKey("locationList", locationList);

        } catch (Exception exception) {
            exception.printStackTrace();
            logger.error(exception.getMessage());
            errorResponse(EXCEPTION_CODE, "查询区列表异常");
            return response;

        }
        return response;
    }

}
