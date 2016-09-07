package com.chinaredstar.longyan.repository;

import com.chinaredstar.longyan.model.RedstarStore;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RedstarStoreRepository extends PagingAndSortingRepository<RedstarStore, Integer>, JpaSpecificationExecutor<RedstarStore> {
    List<RedstarStore> findByQrCode(String qrCode);

}
