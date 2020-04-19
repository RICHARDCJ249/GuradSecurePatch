package com.iflytek.tab1.errorbook;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.CircularProgressButton;
import com.iflytek.tab1.errorbook.R;
import com.iflytek.tab1.errorbook.utill.ApkUtill;
import com.iflytek.tab1.errorbook.utill.ApkUtillPre;
import com.iflytek.tab1.errorbook.utill.AppInfoAdapter;
import com.leon.lfilepickerlibrary.LFilePicker;
import com.leon.lfilepickerlibrary.utils.Constant;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.iflytek.tab1.errorbook.MyApplication.getContext;
import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    private Toolbar tl;
    private DrawerLayout mDrawerLayout;
    private Intent mIntent = new Intent(MyApplication.getContext(), SettingActivity.class);
    private NavigationView mNavigationView;
    private Receiver mReceiver;
    private IntentFilter mIntentFilter;
    private RecyclerView mRecyclerViewHidden;
    private RecyclerView mRecyclerViewNoHidden;
    private AppInfoAdapter mHiddenAdapter;
    private AppInfoAdapter mNoHiddenAdapter;
    private SwipeRefreshLayout Sfl;
    private List<String> noHiddenApp;
    private List<String> HiddenApp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        setStatusBar();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (new ApkUtillPre(MyApplication.getContext()).getAllHideApK() == null) {
            HiddenApp = new ArrayList<String>();
        } else {
            HiddenApp = Arrays.asList(new ApkUtillPre(MyApplication.getContext()).getAllHideApK());
        }

        noHiddenApp = new ApkUtill(MyApplication.getContext()).getThirtAppInfo();
        noHiddenApp.removeAll(HiddenApp);
        initViews();
        setSupportActionBar(tl);
        setOnClickListener();
        registerReceiver(mReceiver, mIntentFilter);
        LocalBroadcastManager.getInstance(MyApplication.getContext()).registerReceiver(mReceiver, new IntentFilter("updateAppList"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.SelectApk:
                new LFilePicker().withActivity(MainActivity.this).withRequestCode(1000).withFileFilter(new String[]{".apk"}).withIconStyle(Constant.ICON_STYLE_YELLOW).withTitle("选择安装包").withMutilyMode(false).start();
                break;
            case R.id.HiddenAllApp:
                new Thread() {
                    @Override
                    public void run() {
                        String[] ai;
                        try {
                            ai = new ApkUtillPre(MyApplication.getContext()).getAllHideApK();
                            for (String ap : ai) {
                                MyApplication.getMdm().controlApp(true, ap);
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MyApplication.getContext(), "已隐藏", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } catch (Exception e) {
                            Toast.makeText(MyApplication.getContext(), "没有需要隐藏的应用", Toast.LENGTH_SHORT).show();
                        }

                    }
                }.run();
                break;
        }
        return true;
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1000) {
                try {
                    List<String> list = data.getStringArrayListExtra("paths");
                    MyApplication.getMdm().writeAppWhiteList(new ApkUtill(MyApplication.getContext()).getAppPackageName(list.get(0)));
                    MyApplication.getMdm().slientInstall(list.get(0));
                    Toast.makeText(MyApplication.getContext(), "安装中", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(MyApplication.getContext(), "安装失败", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /**
     * 加载替换Fragment
     *
     * @param fragment 需要替换的实例对象
     */
//    private void changeFragment(Fragment fragment) {
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        transaction.replace(R.id.fl_fragment_content, fragment);
//        transaction.commit();
//    }

    /**
     * 设置沉浸式状态栏
     */    public void setStatusBar() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
    }

    /**
     * 实例化控件
     */
    private void initViews() {
        tl = (Toolbar) findViewById(R.id.MainToolBar);
        tl.setTitle("");
        mDrawerLayout = (DrawerLayout) findViewById(R.id.grawerlayout);
        mNavigationView = (NavigationView) findViewById(R.id.view_nav);
        mReceiver = new Receiver();
        mRecyclerViewHidden = (RecyclerView)findViewById(R.id.HiddenListOfApp);
        mRecyclerViewNoHidden = (RecyclerView)findViewById(R.id.NoHiddenListOfApp);
        Sfl = (SwipeRefreshLayout)findViewById(R.id.reRefreshOfHidden);
        mRecyclerViewHidden.setLayoutManager(new GridLayoutManager(MyApplication.getContext(),5));
        mRecyclerViewNoHidden.setLayoutManager(new GridLayoutManager(MyApplication.getContext(),5));
        mHiddenAdapter = new AppInfoAdapter(this, HiddenApp);
        mNoHiddenAdapter = new AppInfoAdapter(this, noHiddenApp);
        mIntentFilter = new IntentFilter();

    }

    /**
     * 设置各控件监听器
     */
    private void setOnClickListener() {
        mRecyclerViewHidden.setAdapter(mHiddenAdapter);
        mRecyclerViewNoHidden.setAdapter(mNoHiddenAdapter);
        tl.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
        mNavigationView.setNavigationItemSelectedListener(this);
        mIntentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        mIntentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        mIntentFilter.addDataScheme("package");
        Sfl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (new ApkUtillPre(MyApplication.getContext()).getAllHideApK() == null) {
                    HiddenApp = new ArrayList<String>();
                } else {
                    HiddenApp = Arrays.asList(new ApkUtillPre(MyApplication.getContext()).getAllHideApK());
                }
                noHiddenApp = new ApkUtill(MyApplication.getContext()).getThirtAppInfo();
                noHiddenApp.removeAll(HiddenApp);
                mHiddenAdapter.setmAppInfo(HiddenApp);
                mHiddenAdapter.notifyDataSetChanged();
                mNoHiddenAdapter.setmAppInfo(noHiddenApp);
                mNoHiddenAdapter.notifyDataSetChanged();
                Sfl.setRefreshing(false);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.select_city:
                showInput();
                break;
            case R.id.system_setting:
                mIntent.putExtra("Type", R.id.system_setting);
                startActivity(mIntent);
                break;
            case R.id.about:
                mIntent.putExtra("Type", R.id.about);
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
        if (keyCode == KeyEvent.KEYCODE_BACK && mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
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
                        MyApplication.getContext().getSharedPreferences("appConfig", MODE_PRIVATE).edit().putString("LocatinCity", editText.getText().toString()).commit();
                    }
                });
        builder.create().show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
        LocalBroadcastManager.getInstance(MyApplication.getContext()).unregisterReceiver(mReceiver);
    }

    private class Receiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.intent.action.PACKAGE_ADDED") || intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")) {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                HiddenApp = Arrays.asList(new ApkUtillPre(MyApplication.getContext()).getAllHideApK());
                noHiddenApp = new ApkUtill(MyApplication.getContext()).getThirtAppInfo();
                noHiddenApp.removeAll(HiddenApp);
                mHiddenAdapter.setmAppInfo(HiddenApp);
                mHiddenAdapter.notifyDataSetChanged();
                mNoHiddenAdapter.setmAppInfo(noHiddenApp);
                mNoHiddenAdapter.notifyDataSetChanged();
            } else {
                HiddenApp = Arrays.asList(new ApkUtillPre(MyApplication.getContext()).getAllHideApK());
                noHiddenApp = new ApkUtill(MyApplication.getContext()).getThirtAppInfo();
                noHiddenApp.removeAll(HiddenApp);
                mHiddenAdapter.setmAppInfo(HiddenApp);
                mHiddenAdapter.notifyDataSetChanged();
                mNoHiddenAdapter.setmAppInfo(noHiddenApp);
                mNoHiddenAdapter.notifyDataSetChanged();
            }
        }
    }

}
