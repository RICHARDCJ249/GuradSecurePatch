/**
  * Copyright 2019 bejson.com 
  */
package com.iflytek.tab1.bean;
import org.litepal.crud.LitePalSupport;

/**
 * Auto-generated: 2019-07-09 13:3:52
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Future extends LitePalSupport {

    private String date;
    private String temperature;
    private String weather;
    private Wid wid;
    private String direct;
    public void setDate(String date) {
         this.date = date;
     }

    public String getDate() {
         return date;
     }

    public void setTemperature(String temperature) {
         this.temperature = temperature;
     }

    public String getTemperature() {
         return temperature;
     }

    public void setWeather(String weather) {
         this.weather = weather;
     }

    public String getWeather() {
         return weather;
     }

    public void setWid(Wid wid) {
         this.wid = wid;
     }

    public Wid getWid() {
         return wid;
     }

    public void setDirect(String direct) {
         this.direct = direct;
     }

    public String getDirect() {
         return direct;
     }

}