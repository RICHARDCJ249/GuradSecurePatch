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
    private OkHttpClient client = new OkHttpClient();
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


    public void GetDataOfType(okhttp3.Callback callback, int type, String... arg) {
        getWLLData(callback);
    }


}

