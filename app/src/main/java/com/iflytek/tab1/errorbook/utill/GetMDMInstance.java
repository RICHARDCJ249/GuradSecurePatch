package com.iflytek.tab1.errorbook.utill;

import com.iflytek.tab1.mia.AndroidMDM;
import com.iflytek.tab1.mia.IMDM;
import com.iflytek.tab1.mia.LenovoMDM;

public class GetMDMInstance {
    public static IMDM init() {
        switch (android.os.Build.MANUFACTURER) {
            case "Lenovo":
                return new LenovoMDM();
        }
        return new AndroidMDM();

    }
}
