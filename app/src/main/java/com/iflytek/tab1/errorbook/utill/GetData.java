package com.iflytek.tab1.errorbook.utill;

import java.net.URLEncoder;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import com.iflytek.tab1.bean.*;

/**
 * @author Mr.chen
 * 获取网络数据
 */
public class GetData {

    private String WanLianLiAddress = "http://www.mxnzp.com/api/holiday/list/year/";
    private String WeatherAddress = "http://apis.juhe.cn/simpleWeather/query?key=d7deba32bdc132e653e1535318ad7c79&city=";
    private String CityCodeAddress = "http://apis.juhe.cn/simpleWeather/cityList?key=d7deba32bdc132e653e1535318ad7c79";
    private OkHttpClient client = new OkHttpClient();
    public int WAN_LIAN_LI = 1;
    public int WEATHER = 2;
    public int CITY_CODE = 3;
    private Calendar calendar = Calendar.getInstance();

    public static GetData getInstance() {
        return new GetData();
    }


    /**
     * 获取万年历数据
     *
     * @return WanLianLi 对象
     */
    private void getWLLData(okhttp3.Callback callback) {
        Request request = new Request.Builder().url(WanLianLiAddress + calendar.get(Calendar.YEAR)).build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    /**
     * 获取天气数据
     *
     * @param CityCode 城市代码
     * @return Weather数据
     */
    private void getWeatherData(int CityCode, okhttp3.Callback callback) {
        Request request = new Request.Builder().url(WeatherAddress + CityCode).build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    /**
     * 获取天气数据
     *
     * @param CityCode 城市代码
     * @return Weather数据
     */
    private void getWeatherData(String CityCode, okhttp3.Callback callback) {
        String str = null;
        try {
            str = new String(CityCode.getBytes(), "UTF-8");
            str = URLEncoder.encode(str, "UTF-8");
        } catch (Exception localException) {
        }

        Request request = new Request.Builder().url(WeatherAddress + str).build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    /**
     * 获取城市代码
     *
     * @return
     */
    private void getCityCode(okhttp3.Callback callback) {
        Request request = new Request.Builder().url(CityCodeAddress).build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public void GetDataOfType(okhttp3.Callback callback, int type, String... arg) {
        switch (type) {
            case 1:
                getWLLData(callback);
                break;
            case 2:
                getWeatherData(arg[0], callback);
                break;
        }
    }


}

