package com.easyjava.builder;

import com.easyjava.bean.Constants;
import com.easyjava.bean.FieldInfo;
import com.easyjava.bean.TableInfo;
import com.easyjava.utils.JsonUtils;
import com.easyjava.utils.PropertiesUtils;
import com.easyjava.utils.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class BuildTable {

    private static final Logger logger = LoggerFactory.getLogger(BuildTable.class);
    private static final String SQL_SHOW_TABLE_STATUS = "show table status";
    private static final String SQL_SHOW_TABLE_FIELDS = "show full fields from %s";
    private static final String SQL_SHOW_TABLE_INDEX = "show index from %s";
    private static Connection conn = null;

    /*
      连接数据库
     */
    static {
        String driverName = PropertiesUtils.getString("db.driver.name");
        String url = PropertiesUtils.getString("db.url");
        String user = PropertiesUtils.getString("db.user");
        String password = PropertiesUtils.getString("db.password");
        try {
            Class.forName(driverName);
            conn = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            logger.error("数据库连接失败", e);
        }
    }

    /**
     * 获取表
     */
    public static List<TableInfo> getTables() {
        PreparedStatement ps = null;
        ResultSet tableResult = null;

        List<TableInfo> tableInfoList = new ArrayList<TableInfo>();

        try {
            //数据库执行show table status 可以获取表的信息 包括表的注释
            ps = conn.prepareStatement(SQL_SHOW_TABLE_STATUS);
            tableResult = ps.executeQuery();
            //遍历多张表并保存为Json形式
            while (tableResult.next()) {
                String tableName = tableResult.getString("name");
                String comment = tableResult.getString("comment");
                String beanName = tableName;
                //是否忽略表前缀
                if (Constants.IGNORE_TABLE_PREFIX) {
                    beanName = tableName.substring(beanName.indexOf("_") + 1);
                }
                beanName = processField(beanName, true);

                TableInfo tableInfo = new TableInfo();
                tableInfo.setTableName(tableName);
                tableInfo.setBeanName(beanName);
                tableInfo.setComment(comment);
                tableInfo.setBeanParamName(beanName + Constants.SUFFIX_BEAN_QUERY);

                //读每张表的字段信息
                readFieldInfo(tableInfo);
                //读每张表的索引信息
                getKeyIndexInfo(tableInfo);
                tableInfoList.add(tableInfo);
                logger.info("tableInfo:{}", JsonUtils.convertObj2Json(tableInfo));

            }
        } catch (Exception e) {
            logger.error("读取表失败", e);
        } finally {
            if (tableResult != null) {
                try {
                    tableResult.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return tableInfoList;
    }

    /**
     * 读取字段信息,信息保存到table中无返回值
     */
    public static void readFieldInfo(TableInfo tableInfo) {
        PreparedStatement ps = null;
        ResultSet fieldResult = null;
        //用来判断这整个表中是否含有时间、日期、小数
        boolean havaDateTime = false;
        boolean havaDate = false;
        boolean havaDecimal = false;
        //字段列表和拓展字段列表
        List<FieldInfo> fieldInfoList = new ArrayList<FieldInfo>();
        List<FieldInfo> tableInfoExtendList = new ArrayList<FieldInfo>();

        try {
            //数据库执行show full fields from 表名 可以获取字段的信息 包括字段的注释
            ps = conn.prepareStatement(String.format(SQL_SHOW_TABLE_FIELDS, tableInfo.getTableName()));
            fieldResult = ps.executeQuery();
            while (fieldResult.next()) {
                String field = fieldResult.getString("field");
                String type = fieldResult.getString("type");
                String extra = fieldResult.getString("extra");
                String comment = fieldResult.getString("comment");
                //处理数据库中传来带括号的类型
                if (type.indexOf("(") > 0) {
                    type = type.substring(0, type.indexOf("("));
                }
                //属性名首字母不大写
                String propertyName = processField(field, false);

                FieldInfo fieldInfo = new FieldInfo();
                fieldInfoList.add(fieldInfo);

                fieldInfo.setFieldName(field);
                fieldInfo.setComment(comment);
                fieldInfo.setSqlType(type);
                fieldInfo.setPropertyName(propertyName);
                fieldInfo.setAutoIncrement("auto_increment".equalsIgnoreCase(extra));
                fieldInfo.setJavaType(processJavaType(type));

                //判断字段是否为时间、日期、小数类型
                if (ArrayUtils.contains(Constants.SQL_DATE_TIME_TYPE, type)) {
                    havaDateTime = true;
                }
                if (ArrayUtils.contains(Constants.SQL_DATE_TYPE, type)) {
                    havaDate = true;
                }
                if (ArrayUtils.contains(Constants.SQL_DECIMAL_TYPE, type)) {
                    havaDecimal = true;
                }
                //为时间、日期类型字段添加拓展字段
                if (ArrayUtils.contains(Constants.SQL_DATE_TIME_TYPE, type) || ArrayUtils.contains(Constants.SQL_DATE_TYPE, type)) {
                    String propertyNameStart, propertyNameEnd;
                    propertyNameStart = fieldInfo.getPropertyName() + Constants.SUFFIX_BEAN_QUERY_TIME_START;
                    propertyNameEnd = fieldInfo.getPropertyName() + Constants.SUFFIX_BEAN_QUERY_TIME_END;

                    //起始时间
                    FieldInfo timeStart = new FieldInfo();
                    timeStart.setJavaType("String");
                    timeStart.setPropertyName(propertyNameStart);
                    timeStart.setFieldName(field);
                    timeStart.setSqlType(type);
                    tableInfoExtendList.add(timeStart);

                    //终止时间
                    FieldInfo timeEnd = new FieldInfo();
                    timeEnd.setJavaType("String");
                    timeEnd.setPropertyName(propertyNameEnd);
                    timeEnd.setFieldName(field);
                    timeEnd.setSqlType(type);
                    tableInfoExtendList.add(timeEnd);
                }

                //为String类型字段添加拓展字段
                if (ArrayUtils.contains(Constants.SQL_STRING_TYPE, type)) {
                    String fuzzyName;
                    fuzzyName = fieldInfo.getPropertyName() + Constants.SUFFIX_BEAN_QUERY_FUZZY;

                    //模糊搜索
                    FieldInfo fuzzy = new FieldInfo();
                    fuzzy.setJavaType(fieldInfo.getJavaType());
                    fuzzy.setPropertyName(fuzzyName);
                    fuzzy.setFieldName(field);
                    fuzzy.setSqlType(type);
                    tableInfoExtendList.add(fuzzy);
                }

                tableInfo.setHaveDate(havaDate);
                tableInfo.setHaveDateTime(havaDateTime);
                tableInfo.setHaveBigDecimal(havaDecimal);
            }
            tableInfo.setHaveDate(havaDate);
            tableInfo.setHaveDateTime(havaDateTime);
            tableInfo.setHaveBigDecimal(havaDecimal);
            tableInfo.setFieldList(fieldInfoList);
            tableInfo.setFieldExtendList(tableInfoExtendList);
        } catch (Exception e) {
            logger.error("读取表失败", e);
        } finally {
            if (fieldResult != null) {
                try {
                    fieldResult.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * 获取索引信息,信息保存到table中无返回值
     */
    public static void getKeyIndexInfo(TableInfo tableInfo) {
        PreparedStatement ps = null;
        ResultSet fieldResult = null;


        try {
            //数据库执行show index from 表名 可以获取表的索引 包括联合索引,唯一索引,主键
            ps = conn.prepareStatement(String.format(SQL_SHOW_TABLE_INDEX, tableInfo.getTableName()));
            fieldResult = ps.executeQuery();
            while (fieldResult.next()) {
                String keyName = fieldResult.getString("key_name");
                int nonUnique = Integer.parseInt(fieldResult.getString("non_unique"));
                String columnName = fieldResult.getString("column_name");
                //nonUnique == 1 就不是唯一索引
                if (nonUnique == 1) continue;
                List<FieldInfo> keyFieldList = tableInfo.getKeyIndexMap().get(keyName);
                if (null == keyFieldList) {
                    keyFieldList = new ArrayList<FieldInfo>();
                    tableInfo.getKeyIndexMap().put(keyName, keyFieldList);
                }
                for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                    if (fieldInfo.getFieldName().equals(columnName)) {
                        keyFieldList.add(fieldInfo);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("读取索引失败", e);
        } finally {
            if (fieldResult != null) {
                try {
                    fieldResult.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


    /**
     * 将字段中的'_'下划线处理,并根据需要 自行决定首字母是否大写
     */
    private static String processField(String field, Boolean isUpperFirstLetter) {
        StringBuilder sb = new StringBuilder();
        String[] fields = field.split("_");

        sb.append(isUpperFirstLetter ? StringUtils.upperFirstLetter(fields[0]) : StringUtils.lowerFirstLetter(fields[0]));
        for (int i = 1, len = fields.length; i < len; i++) {
            sb.append(StringUtils.upperFirstLetter(fields[i]));
        }

        return sb.toString();
    }

    /**
     * 将数据库字段的类型转换成Java类型 方便后续生成数据库表字段
     */
    private static String processJavaType(String type) {
        if (ArrayUtils.contains(Constants.SQL_INTEGER_TYPE, type)) {
            return "Integer";
        } else if (ArrayUtils.contains(Constants.SQL_LONG_TYPE, type)) {
            return "Long";
        } else if (ArrayUtils.contains(Constants.SQL_STRING_TYPE, type)) {
            return "String";
        } else if (ArrayUtils.contains(Constants.SQL_DATE_TIME_TYPE, type) || ArrayUtils.contains(Constants.SQL_DATE_TYPE, type)) {
            return "Date";
        } else if (ArrayUtils.contains(Constants.SQL_DECIMAL_TYPE, type)) {
            return "BigDecimal";
        } else {
            throw new RuntimeException("无法识别的类型" + type);
        }
    }
}
