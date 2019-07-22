package com.android.mia;

import android.app.mia.MiaMdmPolicyManager;

import com.android.settingpad.MyApplication;

import java.io.IOException;
import java.util.List;

public class LenovoMDM implements IMDM {
    private MiaMdmPolicyManager mMiaMdmPolicyManager = new MiaMdmPolicyManager(MyApplication.getContext());

    @Override
    public void controlWiFiProxy(boolean b) {
        mMiaMdmPolicyManager.setWifiProxy(b);
    }


    @Override
    public void controlBluetooth(boolean b) {
        int n = 6;
        if (!b) {
            n = 8;
        }
        try {
            Runtime.getRuntime().exec("cosu 0 0 \"service call bluetooth " + n + " \"");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void controlFireWall(boolean b) {
        if (b) {
            try {
                Runtime.getRuntime().exec("cosu 0 0 \"mv /system/lib64/libWantJoinFireWall /system/lib64/libWantJoinFireWal\"");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                Runtime.getRuntime().exec("cosu 0 0 \"mv /system/lib64/libWantJoinFireWal /system/lib64/libWantJoinFireWall\"");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void controlUSb(boolean b) {
        mMiaMdmPolicyManager.setUsbOnlyCharging(!b);
    }

    @Override
    public void controlTFCard(boolean b) {
        mMiaMdmPolicyManager.setTFcard(b);
    }

    @Override
    public List<String> readAppWhiteList() {
        return mMiaMdmPolicyManager.appWhiteListRead();
    }

    @Override
    public void writeAppWhiteList(String s) {
        boolean a = false;
        List<String> s1 = mMiaMdmPolicyManager.appWhiteListRead();
        for (String s2 : s1) {
            if (s2.equals(s)) {
                a = true;
            }
        }
        if (a) {
            return;
        } else {
            s1.add(s);
            mMiaMdmPolicyManager.appWhiteListWrite(s1);
        }
    }

    @Override
    public List<String> readIPWhiteList() {
        return mMiaMdmPolicyManager.urlWhiteListRead();
    }

    @Override
    public void readIPWhiteList(String s) {
        boolean a = false;
        List<String> s1 = mMiaMdmPolicyManager.urlWhiteListRead();
        for (String s2 : s1) {
            if (s2.equals(s)) {
                a = true;
            }
        }
        if (a) {
            return;
        } else {
            s1.add(s);
            mMiaMdmPolicyManager.urlWhiteListWrite(s1);
        }
    }

    @Override
    public void controlApp(boolean b, String s) {
        mMiaMdmPolicyManager.controlApp(s, b);
    }

    @Override
    public void slientInstall(String s) {
        mMiaMdmPolicyManager.silentInstall(s);
    }

}
