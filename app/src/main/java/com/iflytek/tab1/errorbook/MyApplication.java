package com.iflytek.tab1.errorbook;

import android.app.Application;
import android.content.Context;

import com.iflytek.tab1.errorbook.utill.GetMDMInstance;
import com.iflytek.tab1.mia.IMDM;

import org.litepal.LitePal;

import interfaces.heweather.com.interfacesmodule.view.HeConfig;
import interfaces.heweather.com.interfacesmodule.view.HeWeather;


public class MyApplication extends Application {
    public enum USER_TYPE {COMMON, VIP, ADMIN}

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
        HeConfig.init("HE1911171132101979","ad8273acce5d4579b404ee4e7c4c2a7d");


    }

    /**
     * 获取全局上下文
     */
    public static Context getContext() {
        return context;
    }

    public static IMDM getMdm() {
        return mdm;
    }
}


