package com.iflytek.tab1.bean;

import com.iflytek.tab1.mia.IMDM;
import com.iflytek.tab1.mia.LenovoMDM;

public class GetMDMInstance {
    public static IMDM init() {
        switch (android.os.Build.MANUFACTURER) {
            case "Lenovo":
                return new LenovoMDM();
        }
        return new LenovoMDM();

    }
}
