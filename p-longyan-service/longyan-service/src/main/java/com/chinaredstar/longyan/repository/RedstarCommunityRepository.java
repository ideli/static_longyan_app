package com.chinaredstar.longyan.repository;

import com.chinaredstar.longyan.model.RedstarCommunity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;



/**
 * Created by leiyun 2016/7/29.
 */
@Repository
public interface RedstarCommunityRepository extends PagingAndSortingRepository<RedstarCommunity, Integer>, JpaSpecificationExecutor<RedstarCommunity> {

    Page<RedstarCommunity> findByOwnerId(int ownId, Pageable pageable);

    Page<RedstarCommunity> findByOwnerMallId(int ownerMallId, Pageable pageable);




}
