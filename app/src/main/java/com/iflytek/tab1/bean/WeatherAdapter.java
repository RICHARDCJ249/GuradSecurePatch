package com.iflytek.tab1.bean;

import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.iflytek.tab1.errorbook.MyApplication;
import com.iflytek.tab1.errorbook.R;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeatherAdapter extends BaseAdapter {

    private List<Future> mFuture;

    public WeatherAdapter(List<Future> mFuture){
        this.mFuture = mFuture;
    }

    @Override
    public int getCount() {
        return mFuture.size();
    }

    @Override
    public Object getItem(int i) {
        return mFuture.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.weatherlist, null);
            holder = new ViewHolder();
            holder.mTempature = (TextView) view.findViewById(R.id.Tempature);
            holder.mWeatherDay = (TextView) view.findViewById(R.id.WeatherDay);
            holder.mWeatherIcon = (ImageView) view.findViewById(R.id.WeatherIcon);
            holder.mWeatherType = (TextView) view.findViewById(R.id.WeatherType);
            view.setTag(holder);
        } else {
            holder = (ViewHolder)view.getTag();
        }
        holder.mWeatherDay.setText(mFuture.get(i).getDate());
        holder.mWeatherType.setText(mFuture.get(i).getWeather());
        holder.mTempature.setText(mFuture.get(i).getTemperature());
        if(Calendar.getInstance().get(Calendar.HOUR_OF_DAY)>17){
            holder.mWeatherIcon.setImageDrawable(ContextCompat.getDrawable(MyApplication.getContext(),getWeatherMap().get(mFuture.get(i).getWid().getDay())));
        }else {
            holder.mWeatherIcon.setImageDrawable(ContextCompat.getDrawable(MyApplication.getContext(),getWeatherMap().get(mFuture.get(i).getWid().getNight())));
        }

        return view;
    }

    public Map<String,Integer> getWeatherMap(){
        Map<String,Integer> a = new HashMap();
        a.put("00",R.drawable.tianqi_12);
        a.put("01",R.drawable.tianqi_13);
        a.put("02",R.drawable.tianqi_14);
        a.put("03",R.drawable.tianqi_19);
        a.put("04",R.drawable.tianqi_17);
        a.put("05",R.drawable.tianqi_17);
        a.put("06",R.drawable.tianqi_20);
        a.put("07",R.drawable.tianqi_16);
        a.put("08",R.drawable.tianqi_19);
        a.put("09",R.drawable.tianqi_18);
        a.put("10",R.drawable.tianqi_18);
        a.put("11",R.drawable.tianqi_18);
        a.put("12",R.drawable.tianqi_18);
        a.put("13",R.drawable.tianqi_8);
        a.put("14",R.drawable.tianqi_20);
        a.put("15",R.drawable.tianqi_1);
        a.put("16",R.drawable.tianqi_2);
        a.put("17",R.drawable.tianqi_2);
        a.put("18",R.drawable.tianqi_15);
        a.put("19",R.drawable.tianqi_7);
        a.put("20",R.drawable.tianqi_4);
        a.put("21",R.drawable.tianqi_19);
        a.put("22",R.drawable.tianqi_18);
        a.put("23",R.drawable.tianqi_18);
        a.put("24",R.drawable.tianqi_18);
        a.put("25",R.drawable.tianqi_18);
        a.put("26",R.drawable.tianqi_1);
        a.put("27",R.drawable.tianqi_2);
        a.put("28",R.drawable.tianqi_2);
        a.put("29",R.drawable.tianqi_4);
        a.put("30",R.drawable.tianqi_4);
        a.put("31",R.drawable.tianqi_4);
        a.put("32",R.drawable.tianqi_4);
        a.put("53",R.drawable.tianqi_2);
        return a;
        }

    public void setmFuture(List<Future> mFuture){
        this.mFuture = mFuture;
        notifyDataSetChanged();
    }
    public static class ViewHolder{
        public ImageView mWeatherIcon;
        public TextView mWeatherType;
        public TextView mWeatherDay;
        public TextView mTempature;
    }
}
