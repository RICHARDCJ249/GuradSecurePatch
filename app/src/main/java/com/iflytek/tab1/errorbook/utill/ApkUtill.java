package com.iflytek.tab1.errorbook.utill;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.List;


/**
 * 应用工具类
 */
public class ApkUtill {
    private Context mContext;
    private PackageManager pm;

    public ApkUtill(Context mContext){
        this.mContext = mContext;
        pm = mContext.getPackageManager();
    }

    /**
     * 用于获取所有第三方应用
     * @return List<AppInfo>
     */
    public List<String> getThirtAppInfo() {
        List<PackageInfo> pF1 = pm.getInstalledPackages(0);
        List<String> aF = new ArrayList<String>();
        for (PackageInfo pf : pF1){
            if ((pf.applicationInfo.flags & pf.applicationInfo.FLAG_SYSTEM) == 0){
                aF.add(pf.applicationInfo.packageName);
            }
        }
        return aF;
    }

    /**
     * 用于获取所有系统应用
     * @return List<AppInfo>
     */
    private List<String> getSystemAppInfo() {
        List<PackageInfo> pF1 = pm.getInstalledPackages(0);
        List<String> aF = new ArrayList<String>();
        for (PackageInfo pf : pF1){
            if ((pf.applicationInfo.flags & pf.applicationInfo.FLAG_SYSTEM) != 0){
                aF.add(pf.applicationInfo.packageName);
            }
        }
        return aF;
    }


    public String getAppPackageName(String s){
        return pm.getPackageArchiveInfo(s, PackageManager.GET_ACTIVITIES).packageName;
    }

}