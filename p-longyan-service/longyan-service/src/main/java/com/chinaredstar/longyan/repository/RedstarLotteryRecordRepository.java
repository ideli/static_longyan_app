package com.chinaredstar.longyan.repository;

import com.chinaredstar.longyan.model.RedstarLotteryRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by leiyun 2016/8/5.
 */
@Repository
public interface RedstarLotteryRecordRepository extends PagingAndSortingRepository<RedstarLotteryRecord, Integer>, JpaSpecificationExecutor<RedstarLotteryRecord> {

    //获取中奖列表
    Page<RedstarLotteryRecord> findAll();

    //根据employeeCode获取个人奖品列表
    List<RedstarLotteryRecord> findByemployeeCodeAndUsed(String employeeCode,boolean used);

}
