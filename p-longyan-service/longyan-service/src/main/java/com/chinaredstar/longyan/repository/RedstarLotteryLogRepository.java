package com.chinaredstar.longyan.repository;

import com.chinaredstar.longyan.model.RedstarLotteryLog;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by mdc on 2016/8/5.
 */
@Repository
public interface RedstarLotteryLogRepository extends PagingAndSortingRepository<RedstarLotteryLog, Integer>, JpaSpecificationExecutor<RedstarLotteryLog> {
}
