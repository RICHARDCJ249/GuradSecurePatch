package com.iflytek.tab1.errorbook;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iflytek.tab1.bean.Weather;
import com.iflytek.tab1.bean.WeatherAdapter;
import com.iflytek.tab1.errorbook.R;
import com.iflytek.tab1.errorbook.utill.GetData;
import com.dd.CircularProgressButton;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;



public class mineFragment extends Fragment {
    private View mView;
    private RelativeLayout mBackgroungImg;
    private TextView mWeatherCity;
    private ListView mWeatherList;
    private WeatherAdapter mWeatherApadetr;
    private TextView reFreshWeather;
    private TextView mBigTempature;
    private Weather mWeatherInfo;
    private TextView mShiDu;
    private TextView mFengXiang;
    private TextView mAqi;
    private CircularProgressButton mCircularProgressButton;
    int i = 1;




    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_mine, container, false);
        init(mView);
        initData(1);
        mWeatherList.setAdapter(mWeatherApadetr);
        return mView;
    }

    /**
     * 用于初始化界面
     * @param mView
     * @return
     */
    private void init(View mView){
        mBackgroungImg = (RelativeLayout)mView.findViewById(R.id.mineBackgrount);
        mWeatherCity = (TextView)mView.findViewById(R.id.WeatherCity);
        mWeatherList = (ListView)mView.findViewById(R.id.WeatherList);
        reFreshWeather = (TextView)mView.findViewById(R.id.refreshWeather);
        mBigTempature = (TextView)mView.findViewById(R.id.bigTempature);
        mShiDu = (TextView)mView.findViewById(R.id.shidu);
        mFengXiang = (TextView)mView.findViewById(R.id.fengxiang);
        mAqi = (TextView)mView.findViewById(R.id.aqi);
        mCircularProgressButton = (CircularProgressButton)mView.findViewById(R.id.btnAsyncCalendar);
        reFreshWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initData(2);

            }
        });
        mCircularProgressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (i == 1){
                    new SyncCalendar(mCircularProgressButton,MyApplication.getContext()).execute();
                    i += 1;
                }
            }
        });
    }



    /**
     * 填充数据
     * @param type
     */
    public void initData(final int type){
        try {
            if (type == 2){
                throw new Exception();
            }
            mWeatherCity.setText(getContext().getSharedPreferences("appConfig",Context.MODE_PRIVATE).getString("LocatinCity","合肥"));
            String mWeatherInfo_shared = getContext().getSharedPreferences("weather",Context.MODE_PRIVATE).getString("WeatherInfo","-1");
            int mWeatherDay = getContext().getSharedPreferences("weather",Context.MODE_PRIVATE).getInt("WeatherDay",0);
            if (mWeatherDay == 0 || mWeatherInfo_shared.equals("-1") || (Calendar.getInstance().get(Calendar.DAY_OF_YEAR) - mWeatherDay) >= 1){
                throw new Exception();
            }
            mWeatherInfo = new Gson().fromJson(mWeatherInfo_shared, Weather.class);
            if (mWeatherInfo.getError_code() != 0){
                Toast.makeText(getContext(),mWeatherInfo.getReason(),Toast.LENGTH_SHORT);
            }else {
                mBigTempature.setText(mWeatherInfo.getWeatherResult().getRealtime().getTemperature());
                mAqi.setText(mWeatherInfo.getWeatherResult().getRealtime().getAqi());
                mShiDu.setText(mWeatherInfo.getWeatherResult().getRealtime().getHumidity()+"%");
                mFengXiang.setText(mWeatherInfo.getWeatherResult().getRealtime().getDirect()+" "+mWeatherInfo.getWeatherResult().getRealtime().getPower());
                mWeatherApadetr = new WeatherAdapter(mWeatherInfo.getWeatherResult().getFuture());
                mWeatherList.setAdapter(mWeatherApadetr);
            }
        }catch(Exception e){
            GetData mGD = new GetData();
            mGD.GetDataOfType(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mWeatherCity.setText(getContext().getSharedPreferences("appConfig",Context.MODE_PRIVATE).getString("LocatinCity","合肥"));
                            Toast.makeText(getContext(),"获取数据失败",Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String body = response.body().string();
                    mWeatherInfo = new Gson().fromJson(body, Weather.class);
                    if (mWeatherInfo.getError_code() != 0){
                        Toast.makeText(getContext(),mWeatherInfo.getReason(),Toast.LENGTH_SHORT).show();
                    }else {
                        mWeatherApadetr = new WeatherAdapter(mWeatherInfo.getWeatherResult().getFuture());
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mAqi.setText(mWeatherInfo.getWeatherResult().getRealtime().getAqi());
                                mShiDu.setText(mWeatherInfo.getWeatherResult().getRealtime().getHumidity()+"%");
                                mFengXiang.setText(mWeatherInfo.getWeatherResult().getRealtime().getDirect()+" "+mWeatherInfo.getWeatherResult().getRealtime().getPower());
                                mBigTempature.setText(mWeatherInfo.getWeatherResult().getRealtime().getTemperature());
                                if (type == 1){
                                    mWeatherList.setAdapter(mWeatherApadetr);
                                }else {
                                    mWeatherApadetr.setmFuture(mWeatherInfo.getWeatherResult().getFuture());
                                    mWeatherCity.setText(getContext().getSharedPreferences("appConfig",Context.MODE_PRIVATE).getString("LocatinCity","合肥"));
                                    Toast.makeText(getContext(),"刷新成功",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        getContext().getSharedPreferences("weather",Context.MODE_PRIVATE).edit().putString("WeatherInfo",body).putInt("WeatherDay",Calendar.getInstance().get(Calendar.DAY_OF_YEAR)).commit();

                    }
                }
            },mGD.WEATHER,MyApplication.getContext().getSharedPreferences("appConfig",Context.MODE_PRIVATE).getString("LocatinCity","合肥"));

        }
    }
}
