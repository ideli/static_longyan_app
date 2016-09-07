package com.chinaredstar.longyan.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Entity
@Table(name = "xiwa_redstar_session")
public class RedstarSession implements Serializable {
    private static final long serialVersionUID = 8548890076563723540L;
    @Id
    @GeneratedValue(generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(columnDefinition = "BIGINT")
    private Integer id;

    @Column(columnDefinition = "TINYINT(2)")
    private boolean activated;

    private String source;

    private Date expiredDate;

    private Date createDate;

    private Integer objectId;

    private String loginTarget;

    private String token;

    private String openId;

    private String userToken;

    private String userRefreshToken;

    private Date userTokenExpiredDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean getActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getObjectId() {
        return objectId;
    }

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }

    public String getLoginTarget() {
        return loginTarget;
    }

    public void setLoginTarget(String loginTarget) {
        this.loginTarget = loginTarget == null ? null : loginTarget.trim();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken == null ? null : userToken.trim();
    }

    public String getUserRefreshToken() {
        return userRefreshToken;
    }

    public void setUserRefreshToken(String userRefreshToken) {
        this.userRefreshToken = userRefreshToken == null ? null : userRefreshToken.trim();
    }

    public Date getUserTokenExpiredDate() {
        return userTokenExpiredDate;
    }

    public void setUserTokenExpiredDate(Date userTokenExpiredDate) {
        this.userTokenExpiredDate = userTokenExpiredDate;
    }
}