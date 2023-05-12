package com.easyjava.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class PropertiesUtils {
    private static final Properties props = new Properties();
    private static final Map<String, String> PROPER_MAP = new ConcurrentHashMap<String, String>();

    static {
        InputStream in = null;
        try {
            in = PropertiesUtils.class.getClassLoader().getResourceAsStream("application.properties");
            props.load(in);

            for (Object o : props.keySet()) {
                String key = (String) o;
                PROPER_MAP.put(key, props.getProperty(key));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String getString(String key) {
        return props.getProperty(key);
    }

}
