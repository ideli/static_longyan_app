package com.chinaredstar.longyan.repository.specifation;

import com.chinaredstar.longyan.model.RedstarDepartment;
import org.springframework.data.jpa.domain.Specification;


/**
 * Created by mdc on 2016/8/2.
 */
public class ContactSpecifications {
    public static Specification<RedstarDepartment> filterByDepartmentCode(String departmentCode) {
        return (root, query, cb) -> departmentCode != null ? cb.equal(root.get("departmentCode"), departmentCode) : null;
    }
}
