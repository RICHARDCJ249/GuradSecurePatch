package com.iflytek.tab1.errorbook;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.iflytek.tab1.errorbook.utill.ApkUtillPre;


public class PackageRemoveReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")) {
            new ApkUtillPre(context).delApkName(intent.getData().getSchemeSpecificPart());
        } else if (intent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {
            MyApplication.getMdm().deletAppWhiteList(intent.getData().getSchemeSpecificPart());
        } else if (intent.getAction().equals("android.intent.action.ACTION_SHUTDOWN")) {
            for (String a : new ApkUtillPre(context).getAllHideApK()) {
                MyApplication.getMdm().controlApp(true, a);
            }
        } else if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            MyApplication.getMdm().controlBluetooth(false);
        }
    }
}
