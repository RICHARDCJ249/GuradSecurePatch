package com.iflytek.tab1.errorbook;

import android.app.Application;
import android.content.Context;

import com.iflytek.tab1.errorbook.utill.GetMDMInstance;
import com.iflytek.tab1.mia.IMDM;





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


