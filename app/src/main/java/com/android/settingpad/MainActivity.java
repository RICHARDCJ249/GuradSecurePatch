package com.android.settingpad;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.android.bean.AppInfo;
import com.android.settingpad.utill.ApkUtill;
import com.leon.lfilepickerlibrary.LFilePicker;
import com.leon.lfilepickerlibrary.utils.Constant;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static com.android.settingpad.MyApplication.getContext;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,NavigationView.OnNavigationItemSelectedListener{
    private RelativeLayout hiddenapp;
    private RelativeLayout mine;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private Toolbar tl;
    private DrawerLayout mDrawerLayout;
    private Intent mIntent = new Intent(getContext(),SettingActivity.class);
    private mineFragment mmineFragment;
    private HiddenAppFragment mHiddenAppFragment;
    private NavigationView mNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setStatusBar();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setSupportActionBar(tl);
        setOnClickListener();
        hiddenapp.setSelected(true);
        mine.setSelected(false);
        changeFragment(mHiddenAppFragment);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.SelectApk:
                new LFilePicker().withActivity(MainActivity.this).withRequestCode(1000).withFileFilter(new String[]{".apk"}).withIconStyle(Constant.ICON_STYLE_YELLOW).withTitle("选择安装包").withMutilyMode(false).start();
                break;
            case R.id.HiddenAllApp:
                new Thread(){
                    @Override
                    public void run() {
                        List<AppInfo> ai;
                        try {
                            ai = LitePal.where("needtohidden = ?","1").find(AppInfo.class);
                            for (final AppInfo ap : ai){
                                MyApplication.getMdm().controlApp(ap.getAppPackageName(),true);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getContext(),"已隐藏"+ap.getAppName(),Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }catch (Exception e){
                            Toast.makeText(getContext(),"没有需要隐藏的应用",Toast.LENGTH_SHORT).show();
                        }

                    }
                }.run();
                break;
        }
        return true;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.hiddenapp_layout:
                mine.setSelected(false);
                hiddenapp.setSelected(true);
                changeFragment(mHiddenAppFragment);
                break;
            case R.id.mine_layout:
                mine.setSelected(true);
                hiddenapp.setSelected(false);
                changeFragment(mmineFragment);
                break;
                default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1000) {
                try {
                    List<String> list = data.getStringArrayListExtra("paths");
                    List<String> list1 = new ArrayList<String>();
                    list1.add(new ApkUtill(getContext()).getAppPackageName(list.get(0)));
                    list1.addAll(MyApplication.getMdm().appWhiteListRead());
                    MyApplication.getMdm().appWhiteListWrite(list1);
                    MyApplication.getMdm().silentInstall(list.get(0));
                    Toast.makeText(getContext(),"安装中",Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(getContext(),"安装失败",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /**
     * 加载替换Fragment
     * @param fragment 需要替换的实例对象
     */
    private void changeFragment(Fragment fragment){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.fl_fragment_content,fragment);
        transaction.commit();
    }

    /**
     * 设置沉浸式状态栏
     */
    public void setStatusBar(){
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
    }

    /**
     * 实例化控件
     */
    private void initViews(){
        hiddenapp = (RelativeLayout) findViewById(R.id.hiddenapp_layout);
        mine = (RelativeLayout) findViewById(R.id.mine_layout);
        tl = (Toolbar)findViewById(R.id.MainToolBar);
        mHiddenAppFragment = new HiddenAppFragment();
        mmineFragment = new mineFragment();
        mDrawerLayout = (DrawerLayout)findViewById(R.id.grawerlayout);
        mNavigationView = (NavigationView)findViewById(R.id.view_nav);
    }

    /**
     * 设置各控件监听器
     */
    private void setOnClickListener(){
        tl.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
        hiddenapp.setOnClickListener(this);
        mine.setOnClickListener(this);
        mNavigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.select_city:
                showInput();
                break;
            case R.id.menu_user:
                mIntent.putExtra("Type",R.id.menu_user);
                startActivity(mIntent);
                break;
            case R.id.system_setting:
                mIntent.putExtra("Type",R.id.system_setting);
                startActivity(mIntent);
                break;
            case R.id.about:
                mIntent.putExtra("Type",R.id.about);
                startActivity(mIntent);
                break;
        }
        return true;
    }

    /**
     * 监听侧边栏是否打开，拦截Back事件
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    /**
     * 一个输入框的 dialog
     */
    private void showInput() {
        final EditText editText = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle("设置城市").setView(editText)
                .setPositiveButton("设置城市信息", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "设置城市" + editText.getText().toString(), Toast.LENGTH_LONG).show();
                        getContext().getSharedPreferences("appConfig",MODE_PRIVATE).edit().putString("LocatinCity",editText.getText().toString()).commit();
                    }
                });
        builder.create().show();
    }
}