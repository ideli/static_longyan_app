package com.chinaredstar.longyan.repository.specifation;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.chinaredstar.longyan.model.RedstarEmployee;
import org.springframework.data.jpa.domain.Specification;

/**
 * Created by leiyun 2016/8/3.
 */
public class EmployeeSpecifications {

    //根据parentId 得到员工list
    public static Specification<RedstarEmployee> filterByDepartmentCode(String departmentCode) {
        return (root, criteriaQuery, criteriaBuilder) -> !StringUtils.isEmpty(departmentCode) ? criteriaBuilder.equal(root.get("departmentCode"), departmentCode) : null;
    }

    //模糊查询
    public static Specification<RedstarEmployee> findByUserNameLike(String name) {
        return (root, criteriaQuery, criteriaBuilder) -> !StringUtils.isEmpty(name) ? criteriaBuilder.like(root.get("xingMing"), "%" + name + "%") : null;
    }

    public static Specification<RedstarEmployee> findByHrStatus(String hrStatus){
        return (root, criteriaQuery, criteriaBuilder) -> !StringUtils.isEmpty(hrStatus) ? criteriaBuilder.equal(root.get("hrStatus"),hrStatus):null;
    }

}
