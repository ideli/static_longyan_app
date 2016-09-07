package com.chinaredstar.longyan.repository.specifation;

import com.chinaredstar.longyan.model.RedstarCommunity;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;

/**
 * Created by leiyun 2016/8/3.
 */
public class CommunitySpecifications {

    public static Specification<RedstarCommunity> findByProvinceCode(String provinceCode) {

        return (root, criteriaQuery, criteriaBuilder) -> !StringUtils.isEmpty(provinceCode) ? criteriaBuilder.equal(root.get("provinceCode"), provinceCode) : null;
    }

    public static Specification<RedstarCommunity> findByName(String communityName) {

        return (root, criteriaQuery, criteriaBuilder) -> !StringUtils.isEmpty(communityName) ? criteriaBuilder.like(root.get("name"), "%" + communityName + "%") : null;
    }

    public static Specification<RedstarCommunity> findByCityCode(String cityCode) {

        return (root, criteriaQuery, criteriaBuilder) -> !StringUtils.isEmpty(cityCode) ? criteriaBuilder.equal(root.get("cityCode"), cityCode) : null;
    }

    public static Specification<RedstarCommunity> findByAreaCode(String areaCode) {

        return (root, criteriaQuery, criteriaBuilder) -> !StringUtils.isEmpty(areaCode) ? criteriaBuilder.equal(root.get("areaCode"), areaCode) : null;
    }

}
