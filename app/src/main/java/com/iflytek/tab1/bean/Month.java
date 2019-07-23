package com.iflytek.tab1.bean;
import java.util.List;
/**
 * Copyright Mr.chen
 */


/**
 * Auto-generated: 2019-07-08 18:24:12
 *
 * @author Mr.chen
 */
public class Month {

    private int month;
    private int year;
    private List<Days> days;
    public void setMonth(int month) {
        this.month = month;
    }
    public int getMonth() {
        return month;
    }

    public void setYear(int year) {
        this.year = year;
    }
    public int getYear() {
        return year;
    }

    public void setDays(List<Days> days) {
        this.days = days;
    }
    public List<Days> getDays() {
        return days;
    }

}