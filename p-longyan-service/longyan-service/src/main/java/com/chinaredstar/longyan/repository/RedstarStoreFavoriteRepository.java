package com.chinaredstar.longyan.repository;

import com.chinaredstar.longyan.model.RedstarStoreFavorite;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by mdc on 2016/7/29.
 */
@Repository
public interface RedstarStoreFavoriteRepository extends PagingAndSortingRepository<RedstarStoreFavorite, Integer>, JpaSpecificationExecutor<RedstarStoreFavorite> {
    List<RedstarStoreFavorite> findByUserIdAndStoreId(String userId, int storeId);
}
