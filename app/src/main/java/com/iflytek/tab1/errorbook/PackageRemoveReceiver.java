package com.iflytek.tab1.errorbook;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.iflytek.tab1.bean.AppInfo;

import org.litepal.LitePal;

public class PackageRemoveReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")) {
            LitePal.deleteAll(AppInfo.class, "apppackagename = ?", intent.getData().getSchemeSpecificPart());
        } else if (intent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {
            MyApplication.getMdm().deletAppWhiteList(intent.getData().getSchemeSpecificPart());
        } else if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            for (AppInfo a : LitePal.where("needtohidden=?", "1").find(AppInfo.class)) {
                MyApplication.getMdm().controlApp(true, a.getAppPackageName());
            }
            MyApplication.getMdm().controlBluetooth(false);
        }
    }
}
