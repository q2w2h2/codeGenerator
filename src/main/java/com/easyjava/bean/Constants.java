package com.easyjava.bean;


import com.easyjava.utils.PropertiesUtils;

public class Constants {
    //定义数据库类型转java类型对应关系
    public final static String[] SQL_DATE_TIME_TYPE = new String[]{"datetime", "timestamp"};
    public final static String[] SQL_DATE_TYPE = new String[]{"date"};
    public final static String[] SQL_DECIMAL_TYPE = new String[]{"decimal", "double", "float"};
    public final static String[] SQL_STRING_TYPE = new String[]{"char", "varchar", "text", "mediumtext", "longtext"};
    public final static String[] SQL_INTEGER_TYPE = new String[]{"int", "tinyint"};
    public final static String[] SQL_LONG_TYPE = new String[]{"bigint"};
    //定义全局配置常量
    public static Boolean IGNORE_TABLE_PREFIX;
    public static String SUFFIX_BEAN_PARAM;
    public static String PATH_JAVA = "java";
    public static String PATH_RESOURCES = "resources";
    public static String PATH_BASE;
    public static String PATH_PO;
    public static String PACKAGE_BASE;
    public static String PACKAGE_PO;
    public static String PACKAGE_PARAM;


    static {
        //从properties配置类获取常量的值
        IGNORE_TABLE_PREFIX = Boolean.valueOf(PropertiesUtils.getString("ignore.table.prefix"));
        SUFFIX_BEAN_PARAM = PropertiesUtils.getString("suffix.bean.param");
        PATH_BASE = PropertiesUtils.getString("path.base");
        PACKAGE_BASE = PropertiesUtils.getString("package.base").replace('.','/');
        PACKAGE_PO = PropertiesUtils.getString("package.po").replace('.','/');
        PACKAGE_PARAM = PropertiesUtils.getString("package.param").replace('.','/');
        PATH_PO = PATH_BASE + "/" + PATH_JAVA + "/" + PACKAGE_BASE + "/" + PACKAGE_PO;
        PACKAGE_PO = PACKAGE_BASE.replace('/','.') +"."+ PACKAGE_PO.replace('/','.');



    }

    public static void main(String[] args) {
        System.out.println(PATH_PO);
    }

}
