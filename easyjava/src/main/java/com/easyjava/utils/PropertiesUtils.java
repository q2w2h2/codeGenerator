package com.easyjava.utils;

import com.easyjava.bean.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class PropertiesUtils {
    private static Properties props = new Properties();
    private static Map<String,String> PROPER_MAP = new ConcurrentHashMap<String, String>();

    static {
        InputStream in = null;
        try {
            in = PropertiesUtils.class.getClassLoader().getResourceAsStream("application.properties");
            props.load(in);

            Iterator<Object> iterator = props.keySet().iterator();
            while (iterator.hasNext()){
                String key = (String) iterator.next();
                PROPER_MAP.put(key, props.getProperty(key));
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String getString(String key){
        return props.getProperty(key);
    }

    public static void main(String[] args) {
        System.out.println(getString("ignore.table.prefix"));
        System.out.println(Constants.IGNORE_TABLE_PREFIX);
        String str = getString("ignore.table.prefix");
        Boolean b = Boolean.valueOf(str);
        System.out.println(b);
        System.out.println(str);
    }

 }
