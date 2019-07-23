package com.iflytek.tab1.errorbook.utill;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.List;

import com.iflytek.tab1.bean.AppInfo;

import org.litepal.LitePal;


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
    private List<AppInfo> getThirtAppInfo(){
        List<PackageInfo> pF1 = pm.getInstalledPackages(0);
        List<AppInfo> aF1 = new ArrayList<>();
        for (PackageInfo pf : pF1){
            if ((pf.applicationInfo.flags & pf.applicationInfo.FLAG_SYSTEM) == 0){
                AppInfo aF = new AppInfo();
                aF.setAppName(pf.applicationInfo.loadLabel(pm).toString());
                aF.setAppPackageName(pf.packageName);
                aF.setAppVersion(pf.versionName);
                aF.setIsthirtapp(aF.THIRT_APP);
                aF1.add(aF);
            }
        }
        return aF1;
    }

    /**
     * 用于获取所有系统应用
     * @return List<AppInfo>
     */
    private List<AppInfo> getSystemAppInfo(){
        List<PackageInfo> pF1 = pm.getInstalledPackages(0);
        List<AppInfo> aF1 = new ArrayList<>();
        for (PackageInfo pf : pF1){
            if ((pf.applicationInfo.flags & pf.applicationInfo.FLAG_SYSTEM) != 0){
                AppInfo aF = new AppInfo();
                aF.setAppName(pf.applicationInfo.loadLabel(pm).toString());
                aF.setAppPackageName(pf.packageName);
                aF.setAppVersion(pf.versionName);
                aF.setIsthirtapp(aF.SYSTEM_APP);
                aF1.add(aF);
            }
        }
        return aF1;
    }

    /**
     * 用于获取所有第三方应用
     * @return
     */
    public List<AppInfo> getAllThirtAppInfo(){
        List<AppInfo> af = getThirtAppInfo();
        try {
            List<AppInfo> af1 = LitePal.where("isthirtapp = ?","0").find(AppInfo.class);
            af.removeAll(af1);
            af.addAll(af1);
            return af;
        }catch (Exception e){
            return af;
        }
    }

    /**
     * 用于获取所有系统应用
     * @return
     */
    public List<AppInfo> getAllSystemAppInfo(){
        List<AppInfo> af = getThirtAppInfo();
        try {
            List<AppInfo> af1 = LitePal.where("isthirtapp = ?","1").find(AppInfo.class);
            af.removeAll(af1);
            af.addAll(af1);
            return af;
        }catch (Exception e){
            return af;
        }
    }

    public String getAppPackageName(String s){
        return pm.getPackageArchiveInfo(s, PackageManager.GET_ACTIVITIES).packageName;
    }

}