package com.iflytek.tab1.errorbook.utill;

import android.os.Build;
import android.widget.Toast;

import com.iflytek.tab1.errorbook.MyApplication;
import com.iflytek.tab1.mia.AndroidMDM;
import com.iflytek.tab1.mia.IMDM;
import com.iflytek.tab1.mia.LenovoMDM;

public class GetMDMInstance {
    public static IMDM init() {
        switch (android.os.Build.MANUFACTURER) {
            case "LENOVO":
                return new LenovoMDM();
        }
        return new AndroidMDM();
    }
}
