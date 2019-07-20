package com.android.settingpad;

import android.app.Application;
import android.app.mia.MiaMdmPolicyManager;
import android.content.Context;

import org.litepal.LitePal;

public class MyApplication extends Application {

    private static Context context;
    private static MiaMdmPolicyManager mdm;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        mdm = new MiaMdmPolicyManager(context);
        LitePal.initialize(this);
    }
    /**
     * 获取全局上下文*/
    public static Context getContext() {
        return context;
    }
    public static MiaMdmPolicyManager getMdm() {
        return mdm;
    }
}


