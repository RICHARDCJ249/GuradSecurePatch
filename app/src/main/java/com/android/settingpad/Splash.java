package com.android.settingpad;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.android.settingpad.utill.ApkUtill;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import static com.android.settingpad.MyApplication.getContext;
import static com.android.settingpad.MyApplication.getMdm;

public class Splash extends AppCompatActivity {
    private String[] permissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_CALENDAR,
            Manifest.permission.WRITE_CALENDAR,
    };

        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_splash);
            initApp();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(Splash.this, MainActivity.class));
                    finish();
                }
            }, 3000);
    }
        public boolean initApp() {
            boolean isGranted = true;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {    // 检查该权限是否已经获取    //
                for (String i : permissions)   // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                {
                    if (ContextCompat.checkSelfPermission(getApplicationContext(), i) != PackageManager.PERMISSION_GRANTED) {
                        isGranted = isGranted && false;
                    }
                }
                if (!isGranted){
                    ActivityCompat.requestPermissions(this, permissions, 321);
                }

                if (!Settings.System.canWrite(getContext())) {
                    Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                    intent.setData(Uri.parse("package:" + getContext().getPackageName()));
                    intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                    getContext().startActivity(intent);
                }

            }
            if (getContext().getSharedPreferences("appConfig", Context.MODE_PRIVATE).getBoolean("isFirstBoot", true)){
                List<String> list1 = new ArrayList<>();
                list1.add("com.android.settingPad");
                list1.addAll(MyApplication.getMdm().appWhiteListRead());
                MyApplication.getMdm().appWhiteListWrite(list1);
                getContext().getSharedPreferences("appConfig", Context.MODE_PRIVATE).edit().putBoolean("isFirstBoot", true).commit();
            }
            return true;
        }
    }

