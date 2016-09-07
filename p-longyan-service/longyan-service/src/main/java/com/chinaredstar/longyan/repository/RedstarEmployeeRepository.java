package com.chinaredstar.longyan.repository;


import com.chinaredstar.longyan.model.RedstarEmployee;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by leiyun 2016/7/31.
 */
@Repository
public interface RedstarEmployeeRepository extends PagingAndSortingRepository<RedstarEmployee, Integer>, JpaSpecificationExecutor<RedstarEmployee> {

    RedstarEmployee findByEmployeeCode(String employeeCode);

    List<RedstarEmployee> findByUserNameLike(String name);


}
