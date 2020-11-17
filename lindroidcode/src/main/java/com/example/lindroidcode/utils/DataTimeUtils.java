package com.example.lindroidcode.utils;

import java.util.Calendar;
import java.util.Date;

public class DataTimeUtils {
    public static int getHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }
}
