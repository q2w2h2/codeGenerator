package com.easyjava.bean;


import com.easyjava.utils.PropertiesUtils;

public class Constants {
    public static Boolean IGNORE_TABLE_PREFIX;

    static {
        String s =  PropertiesUtils.getString("ignore.table.prefix");
        IGNORE_TABLE_PREFIX = Boolean.valueOf(s);
    }
}
