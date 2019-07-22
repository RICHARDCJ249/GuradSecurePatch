package com.android.settingpad.utill;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.bean.AppInfo;
import com.android.settingpad.MyApplication;
import com.android.settingpad.R;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class AppInfoAdapter extends RecyclerView.Adapter<AppInfoAdapter.AppInfoViewHolder> {

    List<AppInfo> mAppInfo;
    Context mContext;
    private LayoutInflater mInflater;

    public AppInfoAdapter(Context mContext,List<AppInfo> mAppInfo){
        this.mContext = mContext;
        this.mAppInfo = mAppInfo;
        mInflater = LayoutInflater.from(this.mContext);
    }

    public void setmAppInfo(List<AppInfo> mAppInfo){
        this.mAppInfo = mAppInfo;
    }

    @Override
    public void onBindViewHolder(final AppInfoViewHolder holder, final int position) {
        holder.appName.setText(mAppInfo.get(position).getAppName());
        holder.appPackageName.setText(mAppInfo.get(position).getAppPackageName());
        holder.appVersion.setText(mAppInfo.get(position).getAppVersion());
        try {
            holder.mDrawableAppImg = mContext.getPackageManager().getPackageInfo(mAppInfo.get(position).getAppPackageName(),0).applicationInfo.loadIcon(mContext.getPackageManager());
            holder.appImg.setImageDrawable(holder.mDrawableAppImg);
        }catch (Exception e){
            Toast.makeText(mContext,"未找到应用名",Toast.LENGTH_LONG).show();
        }
        holder.btnHiddenApp.setSelected(!mAppInfo.get(position).isNeedToHidden());
        holder.btnRemoveApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("package:" + mAppInfo.get(position).getAppPackageName());
                Intent intent = new Intent(Intent.ACTION_DELETE, uri);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                mContext.startActivity(intent);
            }
        });
        holder.btnHiddenApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.btnHiddenApp.isSelected()){
                    MyApplication.getMdm().controlApp(true, mAppInfo.get(position).getAppPackageName());
                    mAppInfo.get(position).setNeedToHidden(true);
                    mAppInfo.get(position).save();
                    notifyItemChanged(position);
                }else {
                    MyApplication.getMdm().controlApp(false, mAppInfo.get(position).getAppPackageName());
                    mAppInfo.get(position).setNeedToHidden(false);
                    mAppInfo.get(position).save();
                    notifyItemChanged(position);
                }

            }
        });

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAppInfo.get(position).isNeedToHidden()){
                    MyApplication.getMdm().controlApp(false, mAppInfo.get(position).getAppPackageName());
                }
                try {
                    mContext.startActivity(mContext.getPackageManager().getLaunchIntentForPackage(mAppInfo.get(position).getAppPackageName()));
                    Toast.makeText(MyApplication.getContext(),"正在打开"+mAppInfo.get(position).getAppName(),Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(MyApplication.getContext(),"打开失败",Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    @NonNull
    @Override
    public AppInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AppInfoViewHolder(mInflater.inflate(R.layout.applist, null));
    }

    @Override
    public int getItemCount() {
        return mAppInfo.size();
    }

    public static class AppInfoViewHolder extends RecyclerView.ViewHolder{
        public ImageView appImg;
        public TextView appName;
        public TextView appPackageName;
        public TextView appVersion;
        public ImageView btnHiddenApp;
        public ImageView btnRemoveApp;
        public Drawable mDrawableAppImg;
        public SwipeRefreshLayout SRF;
        public LinearLayout item;
        public AppInfoViewHolder(View v) {
            super(v);
            appImg = (ImageView)v.findViewById(R.id.appImg);
            appName = (TextView)v.findViewById(R.id.appName);
            appPackageName = (TextView)v.findViewById(R.id.appPackageName);
            appVersion = (TextView)v.findViewById(R.id.appVersion);
            btnRemoveApp = (ImageView)v.findViewById(R.id.appDelete);
            btnHiddenApp = (ImageView)v.findViewById(R.id.appState);
            SRF = (SwipeRefreshLayout)v.findViewById(R.id.reRefreshOfHidden);
            item = (LinearLayout)v.findViewById(R.id.itemOfAppList);

        }
    }
}
