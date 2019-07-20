/**
  * Copyright 2019 bejson.com 
  */
package com.android.bean;
import java.util.List;

/**
 * Auto-generated: 2019-07-09 13:3:52
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class WeatherResult {

    private String city;
    private Realtime realtime;
    private List<Future> future;
    public void setCity(String city) {
         this.city = city;
     }
     public String getCity() {
         return city;
     }

    public void setRealtime(Realtime realtime) {
         this.realtime = realtime;
     }
     public Realtime getRealtime() {
         return realtime;
     }

    public void setFuture(List<Future> future) {
         this.future = future;
     }
     public List<Future> getFuture() {
         return future;
     }

}