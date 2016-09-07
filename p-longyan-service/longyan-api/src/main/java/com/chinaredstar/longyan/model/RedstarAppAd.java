package com.chinaredstar.longyan.model;


import java.io.Serializable;
import java.util.Date;


public class RedstarAppAd implements Serializable {
    private static final long serialVersionUID = 3526745825926940220L;

    private Integer id;

    private Date createDate;

    private Integer createEmployeeId;

    private String createXingMing;

    private String android720p;

    private String adnroid1280p;

    private String ios47;

    private String ios55;

    private String ios40;

    private String ios35;

    private boolean activity;

   
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column xiwa_redstar_app_ad.id
     *
     * @param id the value for xiwa_redstar_app_ad.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column xiwa_redstar_app_ad.createDate
     *
     * @return the value of xiwa_redstar_app_ad.createDate
     *
     * @mbggenerated
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column xiwa_redstar_app_ad.createDate
     *
     * @param createDate the value for xiwa_redstar_app_ad.createDate
     *
     * @mbggenerated
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column xiwa_redstar_app_ad.createEmployeeId
     *
     * @return the value of xiwa_redstar_app_ad.createEmployeeId
     *
     * @mbggenerated
     */
    public Integer getCreateEmployeeId() {
        return createEmployeeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column xiwa_redstar_app_ad.createEmployeeId
     *
     * @param createEmployeeId the value for xiwa_redstar_app_ad.createEmployeeId
     *
     * @mbggenerated
     */
    public void setCreateEmployeeId(Integer createEmployeeId) {
        this.createEmployeeId = createEmployeeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column xiwa_redstar_app_ad.createXingMing
     *
     * @return the value of xiwa_redstar_app_ad.createXingMing
     *
     * @mbggenerated
     */
    public String getCreateXingMing() {
        return createXingMing;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column xiwa_redstar_app_ad.createXingMing
     *
     * @param createXingMing the value for xiwa_redstar_app_ad.createXingMing
     *
     * @mbggenerated
     */
    public void setCreateXingMing(String createXingMing) {
        this.createXingMing = createXingMing == null ? null : createXingMing.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column xiwa_redstar_app_ad.android720p
     *
     * @return the value of xiwa_redstar_app_ad.android720p
     *
     * @mbggenerated
     */
    public String getAndroid720p() {
        return android720p;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column xiwa_redstar_app_ad.android720p
     *
     * @param android720p the value for xiwa_redstar_app_ad.android720p
     *
     * @mbggenerated
     */
    public void setAndroid720p(String android720p) {
        this.android720p = android720p == null ? null : android720p.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column xiwa_redstar_app_ad.adnroid1280p
     *
     * @return the value of xiwa_redstar_app_ad.adnroid1280p
     *
     * @mbggenerated
     */
    public String getAdnroid1280p() {
        return adnroid1280p;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column xiwa_redstar_app_ad.adnroid1280p
     *
     * @param adnroid1280p the value for xiwa_redstar_app_ad.adnroid1280p
     *
     * @mbggenerated
     */
    public void setAdnroid1280p(String adnroid1280p) {
        this.adnroid1280p = adnroid1280p == null ? null : adnroid1280p.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column xiwa_redstar_app_ad.ios47
     *
     * @return the value of xiwa_redstar_app_ad.ios47
     *
     * @mbggenerated
     */
    public String getIos47() {
        return ios47;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column xiwa_redstar_app_ad.ios47
     *
     * @param ios47 the value for xiwa_redstar_app_ad.ios47
     *
     * @mbggenerated
     */
    public void setIos47(String ios47) {
        this.ios47 = ios47 == null ? null : ios47.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column xiwa_redstar_app_ad.ios55
     *
     * @return the value of xiwa_redstar_app_ad.ios55
     *
     * @mbggenerated
     */
    public String getIos55() {
        return ios55;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column xiwa_redstar_app_ad.ios55
     *
     * @param ios55 the value for xiwa_redstar_app_ad.ios55
     *
     * @mbggenerated
     */
    public void setIos55(String ios55) {
        this.ios55 = ios55 == null ? null : ios55.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column xiwa_redstar_app_ad.ios40
     *
     * @return the value of xiwa_redstar_app_ad.ios40
     *
     * @mbggenerated
     */
    public String getIos40() {
        return ios40;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column xiwa_redstar_app_ad.ios40
     *
     * @param ios40 the value for xiwa_redstar_app_ad.ios40
     *
     * @mbggenerated
     */
    public void setIos40(String ios40) {
        this.ios40 = ios40 == null ? null : ios40.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column xiwa_redstar_app_ad.ios35
     *
     * @return the value of xiwa_redstar_app_ad.ios35
     *
     * @mbggenerated
     */
    public String getIos35() {
        return ios35;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column xiwa_redstar_app_ad.ios35
     *
     * @param ios35 the value for xiwa_redstar_app_ad.ios35
     *
     * @mbggenerated
     */
    public void setIos35(String ios35) {
        this.ios35 = ios35 == null ? null : ios35.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column xiwa_redstar_app_ad.activity
     *
     * @return the value of xiwa_redstar_app_ad.activity
     *
     * @mbggenerated
     */
    public boolean getActivity() {
        return activity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column xiwa_redstar_app_ad.activity
     *
     * @param activity the value for xiwa_redstar_app_ad.activity
     *
     * @mbggenerated
     */
    public void setActivity(boolean activity) {
        this.activity = activity;
    }
}