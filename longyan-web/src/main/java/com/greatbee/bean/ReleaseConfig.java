package com.greatbee.bean;

import java.util.List;

/**
 * 存储配置
 * Created by wangj on 15-10-09.
 */
public class ReleaseConfig {
    private String mysql_path;
    private String backup_path;
    private String release_download_path;
    private List<String> shellCMD;

    public String getMysql_path() {
        return mysql_path;
    }

    public void setMysql_path(String mysql_path) {
        this.mysql_path = mysql_path;
    }

    public String getBackup_path() {
        return backup_path;
    }

    public void setBackup_path(String backup_path) {
        this.backup_path = backup_path;
    }

    public String getRelease_download_path() {
        return release_download_path;
    }

    public void setRelease_download_path(String release_download_path) {
        this.release_download_path = release_download_path;
    }

    public List<String> getShellCMD() {
        return shellCMD;
    }

    public void setShellCMD(List<String> shellCMD) {
        this.shellCMD = shellCMD;
    }
}
