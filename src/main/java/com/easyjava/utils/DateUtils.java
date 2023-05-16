package com.easyjava.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static final String YYYY_MM_DD = "YYYY-MM-dd";
    public static final String YYYY_MM_DD_HH_MM_SS = "YYYY-MM-dd HH:mm:ss";
    public static final String _YYYY_MM_DD = "YYYY/MM/dd";
    public static String format(Date date, String patten) {
        return new SimpleDateFormat(patten).format(date);
    }

    public static String parse(String data, String patten) {
        try {
            new SimpleDateFormat(patten).parse(data);
        }catch (ParseException e){
            e.printStackTrace();
        }
        return null;
    }
}
