package com.chinaredstar.longyan.repository;

import com.chinaredstar.longyan.model.RedstarLotteryLocation;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by leiyun 2016/8/5.
 */
@Repository
public interface RedstarLotteryLocationRepository  extends PagingAndSortingRepository<RedstarLotteryLocation, Integer>, JpaSpecificationExecutor<RedstarLotteryLocation> {

}
