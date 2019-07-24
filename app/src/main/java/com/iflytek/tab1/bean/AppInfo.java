package com.iflytek.tab1.bean;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

public class AppInfo extends LitePalSupport {
    public int THIRT_APP = 0;
    public int SYSTEM_APP = 1;
    private String appName;
    private String appPackageName;
    private String appVersion;

    @Column(defaultValue = "0")
    private boolean needToHidden;

    private int isthirtapp;

    public int getIsthirtapp() {
        return isthirtapp;
    }

    public void setIsthirtapp(int isthirtapp) {
        this.isthirtapp = isthirtapp;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppPackageName() {
        return appPackageName;
    }

    public void setAppPackageName(String appPackageName) {
        this.appPackageName = appPackageName;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public boolean isNeedToHidden() {
        return needToHidden;
    }

    public void setNeedToHidden(boolean needToHidden) {
        this.needToHidden = needToHidden;
    }

    @Override
    public boolean equals(@android.support.annotation.Nullable Object obj) {
        AppInfo ap = (AppInfo) obj;
        if (ap.getAppPackageName().equals(this.appPackageName)) {
            return true;
        } else {
            return false;
        }
    }
}
