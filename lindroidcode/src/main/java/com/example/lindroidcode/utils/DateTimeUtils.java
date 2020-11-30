package com.example.lindroidcode.utils;

import java.util.Calendar;
import java.util.Date;

public class DateTimeUtils {
    public static int getHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }
    public static String formattedTime(long second) {
        //改成四舍五入的方式更精确
        String hs, ms, ss, formatTime;
//        second = second / 1000;
        second = Math.round(second / 1000f);
        long h, m, s;
//        h = second / 3600;
        h = Math.round(second / 3600f);
//        m = (second % 3600) / 60;
        m = Math.round((second % 3600) / 60);
        s = (second % 3600) % 60;
        if (h < 10) {
            hs = "0" + h;
        } else {
            hs = "" + h;
        }

        if (m < 10) {
            ms = "0" + m;
        } else {
            ms = "" + m;
        }

        if (s < 10) {
            ss = "0" + s;
        } else {
            ss = "" + s;
        }
        formatTime = hs + ":" + ms + ":" + ss;

        return formatTime;
    }
}
