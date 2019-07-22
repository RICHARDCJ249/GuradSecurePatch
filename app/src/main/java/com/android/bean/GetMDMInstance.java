package com.android.bean;

import android.content.Context;

import com.android.mia.IMDM;
import com.android.mia.LenovoMDM;
import com.android.settingpad.MyApplication;

public class GetMDMInstance {
    public static IMDM init() {
        switch (android.os.Build.MANUFACTURER) {
            case "Lenovo":
                return new LenovoMDM();
        }
        return new LenovoMDM();

    }
}
