package com.chinaredstar.longyan.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.chinaredstar.longyan.api.CommunityService;
import com.chinaredstar.longyan.api.vo.Community;
import com.chinaredstar.longyan.api.vo.Response;
import com.chinaredstar.longyan.common.EntityUtil;
import com.chinaredstar.longyan.common.InputRateAndOccupanyRateUtil;
import com.chinaredstar.longyan.common.PageResponseUtil;
import com.chinaredstar.longyan.model.RedstarCommunity;

import com.chinaredstar.longyan.model.RedstarEmployee;
import com.chinaredstar.longyan.model.RedstarMember;
import com.chinaredstar.longyan.repository.RedstarCommunityRepository;
import com.chinaredstar.longyan.repository.RedstarEmployeeRepository;
import com.chinaredstar.longyan.repository.RedstarMemberRepository;
import com.chinaredstar.longyan.repository.specifation.CommunitySpecifications;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Created by lenovo on 2016/7/29.
 */
@Service
public class CommunityServiceImpl extends BasicService implements CommunityService {
    protected static org.slf4j.Logger logger = LoggerFactory.getLogger(CommunityServiceImpl.class);

    @Autowired
    private RedstarCommunityRepository redstarCommunityRepository;

    @Autowired
    private RedstarEmployeeRepository redstarEmployeeRepository;

    @Autowired
    private RedstarMemberRepository redstarMemberRepository;


    /**
     * 根据省市区，小区名称等条件来获取小区列表
     *
     * @return 小区列表
     */
    @Override
    @Transactional(readOnly = true)
    public Response communityList(Community community) {

        //1.参数验证
        //page和pageSize 验证
        int page = community.getPage();
        if (page <= 0) {
            page = 1;
        }
        int rows = community.getPageSzie();
        if (rows == 0) {
            rows = 20;
        }

        //provinceCode 验证
        if (StringUtils.isBlank(community.getProvinceCode())) {
            errorResponse(-1001, "provinceCode不能为空");
            return response;
        }

        //2.排序规则
        boolean isAsc = community.isAsc();//将传来的参数转化为BOOLEAN类型,isAsc的初始化值为false
        Sort.Direction sort = Sort.Direction.DESC;
        if (isAsc == true)
            sort = Sort.Direction.ASC;

        //3.设orderBy默认值 以及判断排序字段是否存在于该表中
        String orderBy = community.getOrderBy();
        //判断排序字段是否为空
        if (StringUtils.isNotEmpty(orderBy)) {
            //3.1.排序
            //查找该类的所有字段
            String fieldResult = EntityUtil.getString(orderBy, RedstarCommunity.class);
            //4.判断该排序字段不存在于此类
            if (fieldResult.indexOf(orderBy) < 0) {
                orderBy = "id";
                community.setOrderBy(orderBy);
                community.setIsAsc(false);
            }
        } else {
            orderBy = "id";
            //isAsc=false;
            community.setOrderBy(orderBy);
            community.setIsAsc(false);
        }

        try {
            //page-1为首页，rows=pagesize  ,  Sort.Direction.DESC 排序规则 ，ID排序字段
            //5.查询结果
            PageRequest pageRequest = new PageRequest(page, rows, sort, "id");   //通过条件得到分页规则

            Specification<RedstarCommunity> communitySpecification = Specifications.where(CommunitySpecifications.findByProvinceCode(community.getProvinceCode()))
                    .and(CommunitySpecifications.findByName(community.getCommunityName())).and(CommunitySpecifications.findByCityCode(community.getCityCode()))
                    .and(CommunitySpecifications.findByAreaCode(community.getAreaCode()));
            Page<RedstarCommunity> pageData = redstarCommunityRepository.findAll(communitySpecification,pageRequest);


            //6.判断当前页是否超出总页数
                successResponse("查询成功");
                response.addKey("pageData", pageData);

        } catch (Exception exception) {
            exception.printStackTrace();
            logger.error(exception.getMessage());
            errorResponse(EXCEPTION_CODE, "获取小区列表异常");

        }

        return response;
    }


    /**
     * 根据某个商场的辐射半径获取小区列表
     *
     * @return小区列表
     */
    @Override
    @Transactional(readOnly = true)
    public Response communityMallCommunityList(String mallId, String orderBy, String isAsc, int page, int pageSize) {
        //1.判断为空
        if (StringUtils.isBlank(mallId)) {
            errorResponse(-1001, "mallId不能为空");
            return response;
        }

        //2.排序字段是否存在
        if (StringUtils.isNotEmpty(orderBy)) {
            //1.查找该类 的所有字段
            String fieldResult = EntityUtil.getString(orderBy, RedstarCommunity.class);
            //orderBy不存在与该类里面
            if (fieldResult.indexOf(orderBy) < 0) {
                //排序字段为空时，默认以Id的降序排序，并从表中查询数据
                orderBy = "id";
                isAsc = "DESC";
            }
        } else {
            //排序字段为空时，默认以Id的降序排序，并从表中查询数据
            orderBy = "id";
            isAsc = "DESC";
        }

        //给page和pageSize默认值

        if (pageSize == 0) {
            pageSize = 20;
        }
        if (page <= 0) {
            page = 1;
        }


        //3.设置排序规则
        Sort.Direction sort = Sort.Direction.DESC;
        if (isAsc.equals(true))
            sort = Sort.Direction.ASC;

        int mallIdInt = 0;
        try {
            //处理转换异常(String => int)
            mallIdInt = Integer.parseInt(mallId);
            //page-1为首页，rows=pagesize  ,  Sort.Direction.DESC 排序规则 ，ID排序字段
            PageRequest pageRequest = new PageRequest(page, pageSize, sort, "id");   //通过条件得到分页规则
            //4.查询结果
            Page<RedstarCommunity> pageData = redstarCommunityRepository.findByOwnerMallId(mallIdInt, pageRequest);

                successResponse("查询成功");
                response.addKey("pageData", pageData);

        } catch (Exception exception) {
            exception.printStackTrace();
            logger.error(exception.getMessage());
            errorResponse(EXCEPTION_CODE, "获取小区列表异常");
            return response;

        }

        return response;
    }

    /**
     * 获取某员工所分配的小区列表
     *
     * @return小区列表
     */
    @Override
    @Transactional(readOnly = true)
    public Response employeeCommunityList(String employeeCode, int page, int pageSize, String orderBy, String isAsc) {

        //1.判断为空
        if (StringUtils.isBlank(employeeCode)) {
            errorResponse(-1001, "employeeCode不能为空");
            return response;
        }

        if (page <= 0) {
            page = 1;
        }
        if (pageSize == 0) {
            pageSize = 20;
        }

        //2.排序字段是否存在
        if (StringUtils.isNotEmpty(orderBy)) {
            //1.查找该类 的所有字段
            String fieldResult = EntityUtil.getString(orderBy, RedstarCommunity.class);
            //orderBy不存在与该类里面
            if (fieldResult.indexOf(orderBy) < 0) {
                //排序字段为空时，默认以Id的降序排序，并从表中查询数据
                orderBy = "id";
                isAsc = "false";
            }
        } else {
            //排序字段为空时，默认以Id的降序排序，并从表中查询数据
            orderBy = "id";
            isAsc = "false";
        }

        Sort.Direction sort = Sort.Direction.DESC;
        if (isAsc.equals(true))
            sort = Sort.Direction.ASC;

     //   int employeeCodeInt = 0;
        try {
            //.处理转换异常(String => int)
           // employeeCodeInt = Integer.parseInt(employeeCode);

            //3.得到员工列表
            RedstarEmployee redstarEmployee = redstarEmployeeRepository.findByEmployeeCode(employeeCode);
            if (redstarEmployee == null) {
                errorResponse(30301,"该员工不存在");
                return response;
            } else {

                //4.获取社区集合
                PageRequest pageRequest = new PageRequest(page - 1, pageSize, sort, "id");   //通过条件得到分页规则
                Page<RedstarCommunity> redstarCommunityPage = redstarCommunityRepository.findByOwnerId(redstarEmployee.getId(), pageRequest);

                //5.计算入住率和录入率
                List<RedstarCommunity> redstarCommunityList = InputRateAndOccupanyRateUtil.computeValue(redstarCommunityPage.getContent());

                    successResponse("查询成功");
                    response.addKey("redstarCommunities", redstarCommunityList);
                    PageResponseUtil.setPage(response, redstarCommunityPage);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
            logger.error(exception.getMessage());
            errorResponse(EXCEPTION_CODE, "查询异常");
            return response;
        }
        return response;
    }


    /**
     * 根据小区来获取对应住宅列表
     *
     * @return住宅列表
     */
    @Override
    @Transactional(readOnly = true)
    public Response memberList(String communityId, int page, int pageSize, String orderBy, String isAsc) {

        //1.判断为空
        if (StringUtils.isBlank(communityId)) {
            errorResponse(-1001, "communityId不能为空");
            return response;
        }

        if (pageSize == 0) {
            pageSize = 20;
        }
        if (page <= 0) {
            page = 1;
        }

        //2.排序字段是否存在
        if (StringUtils.isNotEmpty(orderBy)) {
            //1.查找该类 的所有字段
            String fieldResult = EntityUtil.getString(orderBy, RedstarCommunity.class);
            //orderBy不存在与该类里面
            if (fieldResult.indexOf(orderBy) < 0) {
                //排序字段为空时，默认以Id的降序排序，并从表中查询数据
                orderBy = "id";
                isAsc = "false";
            }
        } else {
            //排序字段为空时，默认以Id的降序排序，并从表中查询数据
            orderBy = "id";
            isAsc = "false";
        }


        Sort.Direction sort = Sort.Direction.DESC;
        if (isAsc.equals(true))
            sort = Sort.Direction.ASC;

        int communityIdInt = 0;
        try {
            //3.处理转换异常(String => int)
            communityIdInt = Integer.parseInt(communityId);
            PageRequest pageRequest = new PageRequest(page - 1, pageSize, sort, "id");   //通过条件得到分页规则
            //4.查询结果
            Page<RedstarMember> redstarMembers = redstarMemberRepository.findByCommunityId(communityIdInt, pageRequest);

                successResponse("查询成功");
                response.addKey("redstarMembers", redstarMembers);

        } catch (Exception exception) {
            exception.printStackTrace();
            logger.error(exception.getMessage());
            errorResponse(EXCEPTION_CODE, "获取对应住宅列表异常");
            return response;
        }

        return response;
    }
}