/**
  * Copyright 2019 bejson.com 
  */
package com.android.bean;

/**
 * Auto-generated: 2019-07-09 13:3:52
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Weather {

    private String reason;
    private WeatherResult result;
    private int error_code;
    public void setReason(String reason) {
         this.reason = reason;
     }
     public String getReason() {
         return reason;
     }

    public void setWeatherResult(WeatherResult result) {
         this.result = result;
     }
     public WeatherResult getWeatherResult() {
         return result;
     }

    public void setError_code(int error_code) {
         this.error_code = error_code;
     }
     public int getError_code() {
         return error_code;
     }

}