package com.chinaredstar.longyan.repository;

import com.chinaredstar.longyan.model.RedstarShoppingMall;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by leiyun 2016/8/1.
 */
@Repository
public interface RedstarShoppingMallRepositoy extends PagingAndSortingRepository<RedstarShoppingMall, Integer>, JpaSpecificationExecutor< RedstarShoppingMall> {

    List<RedstarShoppingMall> findByOrganizationId(int organizationId);

    List<RedstarShoppingMall> findByprovinceCode(String provinceCode);

}
