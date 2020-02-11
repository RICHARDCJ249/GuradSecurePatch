package com.iflytek.tab1.errorbook.utill;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iflytek.tab1.bean.AppInfo;
import com.iflytek.tab1.errorbook.MyApplication;
import com.iflytek.tab1.errorbook.R;

import java.util.List;

public class AppInfoAdapter extends RecyclerView.Adapter<AppInfoAdapter.AppInfoViewHolder> {

    List<AppInfo> mAppInfo;
    Context mContext;
    private LayoutInflater mInflater;

    public AppInfoAdapter(Context mContext, List<AppInfo> mAppInfo) {
        this.mContext = mContext;
        this.mAppInfo = mAppInfo;
        mInflater = LayoutInflater.from(this.mContext);
    }

    public void setmAppInfo(List<AppInfo> mAppInfo) {
        this.mAppInfo = mAppInfo;
    }

    @Override
    public void onBindViewHolder(final AppInfoViewHolder holder, final int position) {
        holder.appName.setText(mAppInfo.get(position).getAppName());
        try {
            holder.mDrawableAppImg = mContext.getPackageManager().getPackageInfo(mAppInfo.get(position).getAppPackageName(), 0).applicationInfo.loadIcon(mContext.getPackageManager());
            holder.appImg.setImageDrawable(holder.mDrawableAppImg);
        } catch (Exception e) {
            Toast.makeText(mContext, "未找到应用名", Toast.LENGTH_LONG).show();
        }

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAppInfo.get(position).isNeedToHidden()) {
                    MyApplication.getMdm().controlApp(false, mAppInfo.get(position).getAppPackageName());
                }
                try {
                    mContext.startActivity(mContext.getPackageManager().getLaunchIntentForPackage(mAppInfo.get(position).getAppPackageName()));
                    Toast.makeText(MyApplication.getContext(), "正在打开" + mAppInfo.get(position).getAppName(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(MyApplication.getContext(), "打开失败", Toast.LENGTH_LONG).show();
                }
            }
        });

        holder.item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
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

    public static class AppInfoViewHolder extends RecyclerView.ViewHolder {
        public ImageView appImg;
        public TextView appName;
        public Drawable mDrawableAppImg;
        public SwipeRefreshLayout SRF;
        public RelativeLayout item;

        public AppInfoViewHolder(View v) {
            super(v);
            appImg = (ImageView) v.findViewById(R.id.appImg);
            appName = (TextView) v.findViewById(R.id.appName);
            SRF = (SwipeRefreshLayout) v.findViewById(R.id.reRefreshOfHidden);
            item = (RelativeLayout) v.findViewById(R.id.itemOfAppList);

        }
    }
}
