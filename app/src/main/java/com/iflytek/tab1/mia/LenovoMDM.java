package com.iflytek.tab1.mia;
import android.app.mia.MiaMdmPolicyManager;
import android.util.Log;
import com.iflytek.tab1.errorbook.MyApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class LenovoMDM implements IMDM {
    private MiaMdmPolicyManager mMiaMdmPolicyManager = new MiaMdmPolicyManager(MyApplication.getContext());

    @Override
    public void controlWiFiProxy(boolean b) {
        mMiaMdmPolicyManager.setWifiProxy(b);
    }


    @Override
    public void controlBluetooth(boolean b) {
        int n = 1;
        if (!b) {
            n = 2;
        }
        shellExec(n);
        Log.i("control", "controlBluetooth: " + n);

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
    public void deletAppWhiteList(String s) {
        boolean a = false;
        List<String> s1 = mMiaMdmPolicyManager.appWhiteListRead();
        for (String s2 : s1) {
            if (s2.equals(s)) {
                a = true;
            }
        }
        if (a) {
            s1.remove(s);
            mMiaMdmPolicyManager.appWhiteListWrite(s1);
        } else {
            return;
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

    public void shellExec(Integer s) {
        Runtime mRuntime = Runtime.getRuntime();
        String filename = "file:///android_asset/command.sh";
        try {
            mRuntime.exec(new String[]{"chmod", "+x", filename});
            mRuntime.exec(new String[]{filename, s.toString()});
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
