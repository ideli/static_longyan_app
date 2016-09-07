package com.chinaredstar.longyan.repository;

import com.chinaredstar.longyan.model.DispatchLocation;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lenovo on 2016/7/29.
 */
@Repository
public interface DispatchLocationRepository extends PagingAndSortingRepository<DispatchLocation, Integer>, JpaSpecificationExecutor<DispatchLocation> {
    List<DispatchLocation> findByCityCode(String cityCode);

}
