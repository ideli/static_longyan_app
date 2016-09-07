package com.chinaredstar.longyan.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
@Entity
@Table(name = "xiwa_redstar_mall_community")
public class RedstarMallCommunity implements Serializable {
    private static final long serialVersionUID = 4684205870790319302L;
    @Id
    @GeneratedValue(generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(columnDefinition = "BIGINT")
    private Integer id;

    private Integer shoppingMallId;

    private String shoppingMallName;

    private Integer communityId;

    private String communityName;

    private BigDecimal distance;

    private Integer memberAmount;

    private Integer memberInputAmount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getShoppingMallId() {
        return shoppingMallId;
    }

    public void setShoppingMallId(Integer shoppingMallId) {
        this.shoppingMallId = shoppingMallId;
    }

    public String getShoppingMallName() {
        return shoppingMallName;
    }

    public void setShoppingMallName(String shoppingMallName) {
        this.shoppingMallName = shoppingMallName == null ? null : shoppingMallName.trim();
    }

    public Integer getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Integer communityId) {
        this.communityId = communityId;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName == null ? null : communityName.trim();
    }

    public BigDecimal getDistance() {
        return distance;
    }

    public void setDistance(BigDecimal distance) {
        this.distance = distance;
    }

    public Integer getMemberAmount() {
        return memberAmount;
    }

    public void setMemberAmount(Integer memberAmount) {
        this.memberAmount = memberAmount;
    }

    public Integer getMemberInputAmount() {
        return memberInputAmount;
    }

    public void setMemberInputAmount(Integer memberInputAmount) {
        this.memberInputAmount = memberInputAmount;
    }
}