package com.iflytek.tab1.errorbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import interfaces.heweather.com.interfacesmodule.bean.search.Search;
import interfaces.heweather.com.interfacesmodule.view.HeWeather;

public class WeatherActivity extends AppCompatActivity {
    String CityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        WebView webView = (WebView)findViewById(R.id.webview);
        Bundle bundle=getIntent().getExtras();
        int type=bundle.getInt("type");
        if (type == 0){
            String city = bundle.getString("city");
            HeWeather.getSearch(this, city,1,new HeWeather.OnResultSearchBeansListener() {
                @Override
                public void onError(Throwable throwable) {

                }

                @Override
                public void onSuccess(Search search) {
                    CityId = search.getBasic().get(0).getCid();
                }
            });
        }else {
            CityId = bundle.getString("city");
        }
        webView.loadUrl("https://widget-page.heweather.net/h5/index.html?bg=1&md=0123456&key=eab035ec3fa9420e9032b60916f4b233"+"&lc="+CityId);
        WebSettings webSettings=webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(false);
        webSettings.setDisplayZoomControls(false);
    }
}
