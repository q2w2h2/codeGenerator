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
    public static String AUTHOR;
    public static Boolean IGNORE_TABLE_PREFIX;
    public static String SUFFIX_BEAN_QUERY;
    public static String SUFFIX_BEAN_QUERY_FUZZY;
    public static String SUFFIX_BEAN_QUERY_TIME_START;
    public static String SUFFIX_BEAN_QUERY_TIME_END;
    public static String SUFFIX_MAPPER;
    public static String PATH_JAVA = "java";
    public static String PATH_RESOURCES = "resources";
    public static String PACKAGE_BASE;
    public static String PACKAGE_PO;
    public static String PACKAGE_QUERY;
    public static String PACKAGE_UTILS;
    public static String PACKAGE_ENUMS;
    public static String PACKAGE_MAPPERS;
    public static String PACKAGE_SERVICE;
    public static String PACKAGE_SERVICE_IMPL;
    public static String PATH_BASE;
    public static String PATH_PO;
    public static String PATH_QUERY;
    public static String PATH_UTILS;
    public static String PATH_ENUMS;
    public static String PATH_MAPPERS;
    public static String PATH_MAPPERS_XML;
    public static String PATH_SERVICE;
    public static String PATH_SERVICE_IMPL;
    public static String IGNORE_BEAN_TOJSON_FILED;
    public static String IGNORE_BEAN_TOJSON_EXPRESSION;
    public static String IGNORE_BEAN_TOJSON_CLASS;
    public static String BEAN_DATE_FORMAT_EXPRESSION;
    public static String BEAN_DATE_FORMAT_CLASS;
    public static String BEAN_DATE_UNFORMAT_EXPRESSION;
    public static String BEAN_DATE_UNFORMAT_CLASS;


    static {
        //从properties配置类获取常量的值
        AUTHOR = PropertiesUtils.getString("author");
        IGNORE_TABLE_PREFIX = Boolean.valueOf(PropertiesUtils.getString("ignore.table.prefix"));
        SUFFIX_BEAN_QUERY = PropertiesUtils.getString("suffix.bean.query");
        SUFFIX_BEAN_QUERY_FUZZY = PropertiesUtils.getString("suffix.bean.query.fuzzy");
        SUFFIX_BEAN_QUERY_TIME_START = PropertiesUtils.getString("suffix.bean.query.time.start");
        SUFFIX_BEAN_QUERY_TIME_END = PropertiesUtils.getString("suffix.bean.query.time.end");
        SUFFIX_MAPPER = PropertiesUtils.getString("suffix.mapper");
        //包名
        PACKAGE_BASE = PropertiesUtils.getString("package.base");
        PACKAGE_PO = PACKAGE_BASE + "." + PropertiesUtils.getString("package.po");
        PACKAGE_QUERY = PACKAGE_BASE + "." + PropertiesUtils.getString("package.query");
        PACKAGE_UTILS = PACKAGE_BASE + "." + PropertiesUtils.getString("package.utils");
        PACKAGE_ENUMS = PACKAGE_BASE + "." + PropertiesUtils.getString("package.enums");
        PACKAGE_MAPPERS = PACKAGE_BASE + "." + PropertiesUtils.getString("package.mappers");
        PACKAGE_SERVICE = PACKAGE_BASE + "." + PropertiesUtils.getString("package.service");
        PACKAGE_SERVICE_IMPL = PACKAGE_BASE + "." + PropertiesUtils.getString("package.service.impl");
        //路径名
        PATH_BASE = PropertiesUtils.getString("path.base");
        PATH_PO = PATH_BASE + "/" + PATH_JAVA + "/" + PACKAGE_PO.replace('.', '/');
        PATH_QUERY = PATH_BASE + "/" + PATH_JAVA + "/" + PACKAGE_QUERY.replace('.', '/');
        PATH_UTILS = PATH_BASE + "/" + PATH_JAVA + "/" + PACKAGE_UTILS.replace('.', '/');
        PATH_ENUMS = PATH_BASE + "/" + PATH_JAVA + "/" + PACKAGE_ENUMS.replace('.', '/');
        PATH_MAPPERS = PATH_BASE + "/" + PATH_JAVA + "/" + PACKAGE_MAPPERS.replace('.', '/');
        PATH_MAPPERS_XML = PropertiesUtils.getString("path.base") + "/" + PATH_RESOURCES + "/" + PACKAGE_MAPPERS.replace('.', '/');
        PATH_SERVICE = PATH_BASE + "/" + PATH_JAVA + "/" + PACKAGE_SERVICE.replace('.', '/');
        PATH_SERVICE_IMPL = PATH_BASE + "/" + PATH_JAVA + "/" + PACKAGE_SERVICE_IMPL.replace('.', '/');
        //需要忽略的属性
        IGNORE_BEAN_TOJSON_FILED = PropertiesUtils.getString("ignore.bean.tojson.filed");
        IGNORE_BEAN_TOJSON_EXPRESSION = PropertiesUtils.getString("ignore.bean.tojson.expression");
        IGNORE_BEAN_TOJSON_CLASS = PropertiesUtils.getString("ignore.bean.tojson.class");
        //日期格式序列化
        BEAN_DATE_FORMAT_EXPRESSION = PropertiesUtils.getString("bean.date.format.expression");
        BEAN_DATE_FORMAT_CLASS = PropertiesUtils.getString("bean.date.format.class");
        //日期格式反序列化
        BEAN_DATE_UNFORMAT_EXPRESSION = PropertiesUtils.getString("bean.date.unformat.expression");
        BEAN_DATE_UNFORMAT_CLASS = PropertiesUtils.getString("bean.date.unformat.class");

    }

    public static void main(String[] args) {
        System.out.println(PATH_MAPPERS_XML);
    }

}
