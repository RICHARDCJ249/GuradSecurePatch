package com.iflytek.tab1.errorbook.utill;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.List;

public class ApkUtillPre {

    Context mContext;
    SharedPreferences mSharedPreference;
    boolean ON = true;
    boolean OFF = false;

    public ApkUtillPre(Context mContext) {
        this.mContext = mContext;
        mSharedPreference = mContext.getSharedPreferences("ApkList", Context.MODE_PRIVATE);
    }

    public String[] getAllHideApK() {
        String allApkName = mSharedPreference.getString("ApkName", "0");
        Log.i("errorbook", "getAllHideApK: " + allApkName);
        if (!(allApkName.equals("") || allApkName.equals("0"))) {
            return allApkName.split(",");
        } else {
            String[] strings = null;
            return strings;
        }
    }

    public void clear() {
        String allApkName = mSharedPreference.getString("ApkName", "0");
        if (!allApkName.equals("0")) {
            String[] ApkList = allApkName.split(",");
            for (String i : ApkList) {
                mSharedPreference.edit().remove(i).commit();
            }
            mSharedPreference.edit().remove("ApkList").commit();
        }
    }

    public void addApkName(String s) {
        String allApkName = mSharedPreference.getString("ApkName", "0");
        if (!allApkName.equals("0")) {
            allApkName += s + ',';
            mSharedPreference.edit().putString("ApkName", allApkName).commit();
            mSharedPreference.edit().putBoolean(s, true).commit();
        } else {
            mSharedPreference.edit().putString("ApkName", s + ",").commit();
            mSharedPreference.edit().putBoolean(s, true).commit();
        }
    }

    public void delApkName(String s) {
        String allApkName = mSharedPreference.getString("ApkName", "0");
        if (!allApkName.equals("0")) {
            allApkName = allApkName.replace(s + ",", "");
            mSharedPreference.edit().remove(s).commit();
            mSharedPreference.edit().putString("ApkName", allApkName).commit();
        }
    }

    public void setApkStatus(String s, boolean type) {
        SharedPreferences.Editor editor = mSharedPreference.edit();
        editor.putBoolean(s, type);
        editor.commit();
    }

    public boolean getApkStatus(String s) {
        return mSharedPreference.getBoolean(s, false);
    }

    public boolean getIfChooseToHide(String s) {
        String allApkName = mSharedPreference.getString("ApkName", "0");
        if (allApkName.equals("0")) {
            return false;
        } else {
            return allApkName.contains(s);
        }
    }

}
