package com.chinaredstar.longyan.bean;

/**
 * Created by usagizhang on 16-5-5.
 */
public class AppConfig {
    private String longyanVersion;
    private String mainDomain;

    public String getLongyanVersion() {
        return longyanVersion;
    }

    public void setLongyanVersion(String longyanVersion) {
        this.longyanVersion = longyanVersion;
    }

    public String getMainDomain() {
        return mainDomain;
    }

    public void setMainDomain(String mainDomain) {
        this.mainDomain = mainDomain;
    }
}
