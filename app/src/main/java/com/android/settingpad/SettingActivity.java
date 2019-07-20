package com.android.settingpad;

import android.content.Intent;
import android.preference.PreferenceFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.support.v7.widget.Toolbar;


public class SettingActivity extends AppCompatActivity {
    public Toolbar tl;
    public settingFragment mSettingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        tl = (Toolbar)findViewById(R.id.SettingToolBar);
        setSupportActionBar(tl);
        tl.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Intent intent = getIntent();
        switch (intent.getIntExtra("Type",-1)){
            case R.id.system_setting:
                if (mSettingFragment == null){
                    mSettingFragment = new settingFragment();
                    changeFragment(mSettingFragment);
                }else{
                    changeFragment(mSettingFragment);
                }
                break;


        }
    }


    /**
     * 加载替换Fragment
     * @param fragment 需要替换的实例对象
     */
    private void changeFragment(Fragment fragment){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_content,fragment);
        transaction.commit();
    }


}
