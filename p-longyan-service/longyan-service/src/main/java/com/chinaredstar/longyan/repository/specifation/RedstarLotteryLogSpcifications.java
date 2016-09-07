package com.chinaredstar.longyan.repository.specifation;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.chinaredstar.longyan.model.RedstarLotteryLog;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

/**
 * Created by mdc on 2016/8/5.
 */
public class RedstarLotteryLogSpcifications {
    //根据parentId 得到员工list
    public static Specification<RedstarLotteryLog> filterByEmployeeCode(String employeeCode) {
        return (root, criteriaQuery, criteriaBuilder) -> !StringUtils.isEmpty(employeeCode) ? criteriaBuilder.equal(root.get("employeeCode"), employeeCode) : null;
    }

    public static Specification<RedstarLotteryLog> filterGtCreatedDate(Date createdDate) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("createDate"), createdDate);
    }

    public static Specification<RedstarLotteryLog> filterLtCreatedDate(Date createdDate) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("createDate"), createdDate);
    }

}
