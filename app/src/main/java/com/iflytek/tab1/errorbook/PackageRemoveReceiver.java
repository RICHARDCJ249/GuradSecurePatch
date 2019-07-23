package com.iflytek.tab1.errorbook;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.iflytek.tab1.bean.AppInfo;

import org.litepal.LitePal;

public class PackageRemoveReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        LitePal.deleteAll(AppInfo.class,"apppackagename = ?",intent.getData().getSchemeSpecificPart());
    }
}
