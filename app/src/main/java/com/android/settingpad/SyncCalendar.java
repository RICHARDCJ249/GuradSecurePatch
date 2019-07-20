package com.android.settingpad;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.bean.Days;
import com.android.bean.Month;
import com.android.bean.WanLianLi;
import com.android.settingpad.utill.CalendarUtil;
import com.android.settingpad.utill.GetData;
import com.dd.CircularProgressButton;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SyncCalendar extends AsyncTask<Integer,Integer,String> {

    private CircularProgressButton mCircularProgressButton;
    private Context mContext;
    int mJieQiId;
    int mLongLiId;
    int mFestivalId;
    private OkHttpClient client = new OkHttpClient();

    public SyncCalendar(CircularProgressButton mCircularProgressButton,Context mContext) {
        this.mCircularProgressButton = mCircularProgressButton;
        this.mContext =mContext;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        if (s != null){
            Toast.makeText(mContext,s,Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(mContext,"执行成功",Toast.LENGTH_SHORT).show();
            mCircularProgressButton.setProgress(100);
        }

    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        if (values[0]>0){
            Log.i("CalendarSync", "onProgressUpdate: 进度"+values[0]);
            mCircularProgressButton.setProgress(values[0]);
        }else {
            if (values[0] == -1){
                Toast.makeText(mContext,"网络连接失败",Toast.LENGTH_SHORT).show();
                mCircularProgressButton.setProgress(-1);
                cancel(true);
            }else if (values[0] == -2){
                Toast.makeText(mContext,"网络回复错误",Toast.LENGTH_SHORT).show();
                mCircularProgressButton.setProgress(-1);
                cancel(true);
            }else if (values[0] == -3){
                Toast.makeText(mContext,"日历账户创建错误",Toast.LENGTH_SHORT).show();
                mCircularProgressButton.setProgress(-1);
                cancel(true);
            }
        }
    }



    @Override
    protected String doInBackground(Integer...values) {
        try {
            final CalendarUtil mCU = new CalendarUtil();
            mLongLiId = mCU.checkCalendarAccount(mContext,"LONG_LI");
            if (mLongLiId == -1){
                mLongLiId = mCU.addCalendarAccount(mContext,"LONG_LI","LONG_LI","LOCAL","农历", Color.BLUE);
                if (mLongLiId == -1){
                    publishProgress(-3);
                }
            }
            mJieQiId = mCU.checkCalendarAccount(mContext,"JIE_QI");
            if (mJieQiId == -1){
                mJieQiId = mCU.addCalendarAccount(mContext,"JIE_QI","JIE_QI","LOCAL","节气",Color.GREEN);
                if (mJieQiId == -1){
                    publishProgress(-3);
                }
            }
            mFestivalId = mCU.checkCalendarAccount(mContext,"FESTIVAL");
            if (mFestivalId == -1){
                mFestivalId = mCU.addCalendarAccount(mContext,"FESTIVAL","FESTIVAL","LOACL","节日",Color.rgb(255,0,255));
                if (mFestivalId == -1){
                    publishProgress(-3);
                }
            }
            Request request = new Request.Builder().url("http://www.mxnzp.com/api/holiday/list/year/" + Calendar.getInstance().get(Calendar.YEAR)).build();
            Call call = client.newCall(request);
            Response mResponse = call.execute();
            if (!mResponse.isSuccessful()){
                publishProgress(-1);
            }else {
                String s = mResponse.body().string();
                Calendar mCalendar = Calendar.getInstance();
                WanLianLi mWLL = null;
                int process = -4;
                try {
                    mWLL = new Gson().fromJson(s,WanLianLi.class);
                    for (Month mMonth : mWLL.getData()){
                        for (Days mDays : mMonth.getDays()){
                            mCalendar.set(Calendar.DAY_OF_YEAR,mDays.getDayOfYear());
                            if (mDays.getType() == 2){
                                mCU.addCalendarEvent(mContext,mDays.getTypeDes(),"",mCalendar.getTimeInMillis(),mFestivalId);
                            }
                            if (mDays.getSolarTerms().length() == 2){
                                mCU.addCalendarEvent(mContext,mDays.getSolarTerms(),"",mCalendar.getTimeInMillis(),mJieQiId);
                            }
                            mCU.addCalendarEvent(mContext,mDays.getLunarCalendar(),"",mCalendar.getTimeInMillis(),mLongLiId);
                            Log.i("CalendarSync", "doInBackground: 已添加"+mDays.getDate() + mDays.getLunarCalendar());
                        }
                        Log.i("CalendarSync", "doInBackground: 已添加"+mMonth.getMonth());
                        process += 8;
                        publishProgress(process);
                    }
                }catch (Exception e){
                    publishProgress(-2);
                }
            }
        }catch (Exception e){
            return e.getMessage();
        }
        return null;
    }
}
