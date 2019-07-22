package com.android.settingpad;

import android.app.Application;
import android.app.mia.MiaMdmPolicyManager;
import android.content.Context;

import com.android.bean.GetMDMInstance;
import com.android.mia.IMDM;

import org.litepal.LitePal;

public class MyApplication extends Application {
    public enum USER_TYPE {COMMON, VIP, ADMIN}

    ;

    private static Context context;
    private static IMDM mdm;
    public static boolean HAVE_BEEN_LOGIN = false;
    public static USER_TYPE user_type;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        mdm = GetMDMInstance.init();
        LitePal.initialize(this);
    }
    /**
     * 获取全局上下文*/
    public static Context getContext() {
        return context;
    }

    public static IMDM getMdm() {
        return mdm;
    }
}


