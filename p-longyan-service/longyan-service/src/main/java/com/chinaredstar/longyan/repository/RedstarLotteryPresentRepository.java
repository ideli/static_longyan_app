package com.chinaredstar.longyan.repository;

import com.chinaredstar.longyan.model.RedstarLotteryPresent;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by mdc on 2016/8/7.
 */
public interface RedstarLotteryPresentRepository extends PagingAndSortingRepository<RedstarLotteryPresent, Integer>, JpaSpecificationExecutor<RedstarLotteryPresent> {
    List<RedstarLotteryPresent> findByShowDataAndIsUp(boolean showData,boolean isUp);
}
