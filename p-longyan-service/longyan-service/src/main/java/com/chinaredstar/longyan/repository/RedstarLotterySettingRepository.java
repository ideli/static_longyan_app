package com.chinaredstar.longyan.repository;

import com.chinaredstar.longyan.model.RedstarLotterySetting;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by mdc on 2016/8/5.
 */
@Repository
public interface RedstarLotterySettingRepository extends PagingAndSortingRepository<RedstarLotterySetting, Integer>, JpaSpecificationExecutor<RedstarLotterySetting> {

    List<RedstarLotterySetting> findByShowData(boolean showData);
}
