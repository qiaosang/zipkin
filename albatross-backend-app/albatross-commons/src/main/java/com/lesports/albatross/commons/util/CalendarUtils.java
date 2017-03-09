package com.lesports.albatross.commons.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by qinfeng on 16/12/29.
 */
public class CalendarUtils {

    private final static SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 获取过去第几天的日期
     * @param past
     * @return
     */
    public static String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        String result = SIMPLE_DATE_FORMAT.format(today);
        return result;
    }

    public static String getPastDate(String date, int past) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(SIMPLE_DATE_FORMAT.parse(date));
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        String result = SIMPLE_DATE_FORMAT.format(today);
        return result;
    }

    public static String getNextDate(String date, int day) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(SIMPLE_DATE_FORMAT.parse(date));
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + day);
        Date today = calendar.getTime();
        String result = SIMPLE_DATE_FORMAT.format(today);
        return result;
    }

    //获取本周的开始日期
    public static String getBeginDayOfWeek(Date date) throws ParseException {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        cal.add(Calendar.DATE, 2 - dayofweek);
        return SIMPLE_DATE_FORMAT.format(cal.getTime());
    }

    //获取本周的结束日期
    public static String getEndDayOfWeek(Date date) throws ParseException {

        Calendar cal = Calendar.getInstance();
        cal.setTime(SIMPLE_DATE_FORMAT.parse(getBeginDayOfWeek(date)));
        cal.add(Calendar.DAY_OF_WEEK, 6);

        return SIMPLE_DATE_FORMAT.format(cal.getTime());
    }


}
