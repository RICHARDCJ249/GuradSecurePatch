package com.iflytek.tab1.errorbook.utill;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iflytek.tab1.errorbook.MyApplication;
import com.iflytek.tab1.errorbook.R;

import java.util.List;

public class AppInfoAdapter extends RecyclerView.Adapter<AppInfoAdapter.AppInfoViewHolder> {

    List<String> mAppPackageName;
    Context mContext;
    private LayoutInflater mInflater;
    private ApkUtillPre mApkUtillPre;
    private PackageManager mPm;

    public AppInfoAdapter(Context mContext, List<String> mAppPackageName) {
        this.mContext = mContext;
        this.mAppPackageName = mAppPackageName;
        mInflater = LayoutInflater.from(this.mContext);
        this.mApkUtillPre = new ApkUtillPre(this.mContext);
        this.mPm = this.mContext.getPackageManager();
    }

    public void setmAppInfo(List<String> mAppPackageName) {
        this.mAppPackageName = mAppPackageName;
    }

    @Override
    public void onBindViewHolder(final AppInfoViewHolder holder, final int position) {
        try {
            Log.i("errorbook", mAppPackageName.get(position));
            holder.appName.setText(mPm.getApplicationLabel(mPm.getApplicationInfo(mAppPackageName.get(position), PackageManager.GET_META_DATA)).toString());
            Log.i("errorbook", "1");
            holder.mDrawableAppImg = mContext.getPackageManager().getPackageInfo(mAppPackageName.get(position), 0).applicationInfo.loadIcon(mContext.getPackageManager());
            Log.i("errorbook", "2");
            holder.appImg.setImageDrawable(holder.mDrawableAppImg);
            Log.i("errorbook", "3");
        } catch (Exception e) {
            Toast.makeText(mContext, "未找到应用名", Toast.LENGTH_LONG).show();
        }

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mApkUtillPre.getApkStatus(mAppPackageName.get(position))) {
                    MyApplication.getMdm().controlApp(false, mAppPackageName.get(position));
                }
                try {
                    mContext.startActivity(mPm.getLaunchIntentForPackage(mAppPackageName.get(position)));
                    Toast.makeText(MyApplication.getContext(), "正在打开" + mPm.getApplicationLabel(mPm.getApplicationInfo(mAppPackageName.get(position), PackageManager.GET_META_DATA)).toString(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(MyApplication.getContext(), "打开失败", Toast.LENGTH_LONG).show();
                }
            }
        });

        holder.item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("选择一个操作");
                String[] chooseItem = {"打开", "隐藏(取消隐藏)", "删除"};
                builder.setItems(chooseItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0: {
                                if (mApkUtillPre.getApkStatus(mAppPackageName.get(position))) {
                                    MyApplication.getMdm().controlApp(false, mAppPackageName.get(position));
                                }
                                try {
                                    mContext.startActivity(mContext.getPackageManager().getLaunchIntentForPackage(mAppPackageName.get(position)));
                                    Toast.makeText(MyApplication.getContext(), "正在打开" + mPm.getApplicationLabel(mPm.getApplicationInfo(mAppPackageName.get(position), PackageManager.GET_META_DATA)).toString(), Toast.LENGTH_SHORT).show();
                                } catch (Exception e) {
                                    Toast.makeText(MyApplication.getContext(), "打开失败", Toast.LENGTH_LONG).show();
                                }
                                break;
                            }
                            case 2: {
                                Uri uri = Uri.parse("package:" + mAppPackageName.get(position));
                                Intent intent = new Intent(Intent.ACTION_DELETE, uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                mContext.startActivity(intent);
                                break;
                            }
                            case 1: {
                                if (mApkUtillPre.getIfChooseToHide(mAppPackageName.get(position))) {
                                    MyApplication.getMdm().controlApp(false, mAppPackageName.get(position));
                                    Log.i("errorbook", "onClick: 显示");
                                    mApkUtillPre.delApkName(mAppPackageName.get(position));
                                    LocalBroadcastManager.getInstance(MyApplication.getContext()).sendBroadcast(new Intent("updateAppList"));
                                } else {
                                    MyApplication.getMdm().controlApp(true, mAppPackageName.get(position));
                                    Log.i("errorbook", "onClick: 隐藏");
                                    mApkUtillPre.addApkName(mAppPackageName.get(position));
                                    LocalBroadcastManager.getInstance(MyApplication.getContext()).sendBroadcast(new Intent("updateAppList"));
                                }
                                break;

                            }
                        }
                    }

                });
                builder.show();
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
        try {
            return mAppPackageName.size();
        } catch (Exception e) {
            return 0;
        }
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
