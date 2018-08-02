package com.api.model;

import com.base.util.DateUtils;

import java.util.Calendar;
import java.util.Date;

/**
 *计算2个日期之间相差的  相差多少年月日
 * @author 陈清玉
 */
public class DayCompare {
    private Long year;
    private Long month;
    private Long day;
    private Long hours;
    private Long minutes;

    public DayCompare(){
    }

    public DayCompare(Long year, Long month, Long day, Long hours, Long minutes) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hours = hours;
        this.minutes = minutes;
    }

    public Long getYear() {
        return year;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    public Long getMonth() {
        return month;
    }

    public void setMonth(Long month) {
        this.month = month;
    }

    public Long getDay() {
        return day;
    }

    public void setDay(Long day) {
        this.day = day;
    }

    public Long getHours() {
        return hours;
    }

    public void setHours(Long hours) {
        this.hours = hours;
    }

    public Long getMinutes() {
        return minutes;
    }

    public void setMinutes(Long minutes) {
        this.minutes = minutes;
    }

    /**
     * 计算2个日期之间相差的  相差多少年月日
     * 比如：2017-02-02 到  2011-03-02 相差 6年，1个月，0天
     * @param fromDate
     * @param toDate
     * @return
     */
    public static DayCompare dayComparePrecise(Date fromDate, Date toDate){
        //这样得到的差值是微秒级别
        long diff = fromDate.getTime() - toDate.getTime();
        long days = diff / (1000 * 60 * 60 * 24);
        long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
        long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);
        System.out.println(""+days+"天"+hours+"小时"+minutes+"分");
        return new DayCompare(days / 365 ,days / 30 ,days,hours,minutes);
    }

    public static void main(String[] args) {
        Date fromDate = DateUtils.strConvertToDate("2018-05-02",DateUtils.STYLE_2);
        Date toDate = DateUtils.strConvertToDate("2019-05-03",DateUtils.STYLE_2);
        DayCompare dayCompare = dayComparePrecise(toDate, fromDate);
        System.out.println(dayCompare.year+"年"+dayCompare.month+"月"+dayCompare.day+"天"+dayCompare.hours+"小时"+dayCompare.minutes+"分");
    }

}
