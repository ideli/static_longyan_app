package com.chinaredstar.longyan.repository;

import com.chinaredstar.longyan.model.RedstarDepartment;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by mdc on 2016/8/2.
 */
@Repository
public interface RedstarDepartmentRepository  extends PagingAndSortingRepository<RedstarDepartment, Integer>, JpaSpecificationExecutor<RedstarDepartment> {

    //根据departmentCode得到部门信息 list
    RedstarDepartment findByDepartmentCode(String departmentCode);

    //根据departmentParentCode得到部门信息 list
    List<RedstarDepartment> findByDepartmentParentCode(String departmentParentCode);

    //根据父部门ID得到子部门信息
    List<RedstarDepartment> findByParentId(int parentId);




}
