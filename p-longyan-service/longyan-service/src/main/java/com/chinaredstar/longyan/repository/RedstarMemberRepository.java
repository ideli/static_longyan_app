package com.chinaredstar.longyan.repository;

import com.chinaredstar.longyan.model.RedstarMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


/**
 * Created by leiyun 2016/7/31.
 */
@Repository
public interface RedstarMemberRepository extends PagingAndSortingRepository<RedstarMember, Integer>, JpaSpecificationExecutor< RedstarMember> {

    Page<RedstarMember> findByCommunityId(int communityId,Pageable pageable);



}
