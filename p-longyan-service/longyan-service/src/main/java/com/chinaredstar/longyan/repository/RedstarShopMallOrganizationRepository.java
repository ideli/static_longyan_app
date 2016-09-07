package com.chinaredstar.longyan.repository;

import com.chinaredstar.longyan.model.RedstarShoppingMallOrganization;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by leiyun 2016/8/1.
 */
public interface RedstarShopMallOrganizationRepository extends PagingAndSortingRepository<RedstarShoppingMallOrganization, Integer>, JpaSpecificationExecutor<RedstarShoppingMallOrganization> {


    List<RedstarShoppingMallOrganization> findByParentId(int parentId);

}
