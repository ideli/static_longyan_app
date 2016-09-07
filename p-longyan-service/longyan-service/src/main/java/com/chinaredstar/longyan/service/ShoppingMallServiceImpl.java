package com.chinaredstar.longyan.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.chinaredstar.longyan.api.ShoppingMallService;
import com.chinaredstar.longyan.api.vo.Response;
import com.chinaredstar.longyan.model.RedstarShoppingMall;
import com.chinaredstar.longyan.model.RedstarShoppingMallOrganization;
import com.chinaredstar.longyan.repository.RedstarShopMallOrganizationRepository;
import com.chinaredstar.longyan.repository.RedstarShoppingMallRepositoy;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by leiyun on 2016/7/29.
 */
@Service
public class ShoppingMallServiceImpl extends BasicService implements ShoppingMallService {

    protected static Logger logger = LoggerFactory.getLogger(ShoppingMallServiceImpl.class);

    @Autowired
    private RedstarShopMallOrganizationRepository redstarShopMallOrganizationRepository;

    @Autowired
    private RedstarShoppingMallRepositoy redstarShoppingMallRepositoy;

    /**
     * 商场组织列表
     *
     * @return 商场组织列表
     */
    @Override
    @Transactional(readOnly = true)
    public Response shopingMallOrgList(String parentId) {

        //1.判断是否为空
        if (StringUtils.isBlank(parentId)) {
            errorResponse(-1001, "parentId不能为空");
            return response;
        }

        int parentIdInt = 0;
        try {
            //2.处理转换异常(String => int)
            parentIdInt = Integer.parseInt(parentId);

            List<RedstarShoppingMallOrganization> shoppingMallOrganizations = redstarShopMallOrganizationRepository.findByParentId(parentIdInt);
            successResponse("查询成功");
            response.addKey("shoppingMallOrganizations", shoppingMallOrganizations);

        } catch (Exception exception) {

            exception.printStackTrace();
            logger.error(exception.getMessage());
            errorResponse(EXCEPTION_CODE, "获取商场组织列表异常");
            return response;
        }
        return response;
    }

    /**
     * 根据商场组织获取商场列表
     *
     * @return 商场列表
     */
    @Override
    @Transactional(readOnly = true)
    public Response shopingMallListByOrganization(String organizationId) {

        //1.判断为空
        if (StringUtils.isBlank(organizationId)) {
            errorResponse(-1001,"organizationId不能为空");
            return response;
        }

        //2.处理转换异常(String => int)
        int organizationIdInt = 0;
        try {
            organizationIdInt = Integer.parseInt(organizationId);

            List<RedstarShoppingMall> shoppingMallList = redstarShoppingMallRepositoy.findByOrganizationId(organizationIdInt);
            successResponse("查询成功");
            response.addKey("shoppingMallList", shoppingMallList);

        } catch (Exception exception) {

            exception.printStackTrace();
            logger.error(exception.getMessage());
            errorResponse(EXCEPTION_CODE, "获取商场列表异常");
            return response;

        }

        return response;
    }


    /**
     * 根据省份获取商场列表
     *
     * @return 商场列表
     */
    @Override
    @Transactional(readOnly = true)
    public Response shopingMallListByProvince(String provinceCode) {

        //1判断为空
        if (StringUtils.isBlank(provinceCode)) {
            errorResponse(-1001,"provinceCode不能为空");
            return response;

        }
        try{
            //2.从表查询数据
            List<RedstarShoppingMall> shoppingMalls = redstarShoppingMallRepositoy.findByprovinceCode(provinceCode);
            successResponse("查询成功");
            response.addKey("shoppingMalls", shoppingMalls);

        }catch (Exception exception){
            exception.printStackTrace();
            logger.error(exception.getMessage());
            errorResponse(EXCEPTION_CODE, "获取商场列表异常");
            return response;

        }
        return response;
    }
}
