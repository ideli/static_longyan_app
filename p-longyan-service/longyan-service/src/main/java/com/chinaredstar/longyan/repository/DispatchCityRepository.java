package com.chinaredstar.longyan.repository;

import com.chinaredstar.longyan.model.DispatchCity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lenovo on 2016/7/29.
 */
@Repository
public interface DispatchCityRepository extends PagingAndSortingRepository<DispatchCity, Integer>, JpaSpecificationExecutor<DispatchCity> {
    List<DispatchCity> findByProvinceCode(String provinceCode);

}

