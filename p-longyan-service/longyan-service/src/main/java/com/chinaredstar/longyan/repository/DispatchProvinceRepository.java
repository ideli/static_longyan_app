package com.chinaredstar.longyan.repository;

import com.chinaredstar.longyan.model.DispatchProvince;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by mdc on 2016/7/19.
 */
@Repository
public interface DispatchProvinceRepository extends PagingAndSortingRepository<DispatchProvince, Integer>, JpaSpecificationExecutor<DispatchProvince> {

   List<DispatchProvince> findAll();



}
