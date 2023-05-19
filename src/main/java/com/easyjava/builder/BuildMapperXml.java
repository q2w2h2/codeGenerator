package com.easyjava.builder;

import com.easyjava.bean.Constants;
import com.easyjava.bean.FieldInfo;
import com.easyjava.bean.TableInfo;
import com.easyjava.utils.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuildMapperXml {
    public static final String BASE_COLUMN_LIST = "base_column_list";
    public static final String BASE_QUERY_CONDITION = "base_query_condition";
    public static final String BASE_QUERY_CONDITION_EXTEND = "base_query_condition_extend";
    public static final String QUERY_CONDITION = "query_condition";

    public static void execute(TableInfo tableInfo) {
        //可以定义一些常用的复用
        String PO_BEAN_NAME = Constants.PACKAGE_PO + "." + tableInfo.getBeanName();
        //创建文件夹
        File folder = new File(Constants.PATH_MAPPERS_XML);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String fileName = tableInfo.getBeanName() + Constants.SUFFIX_MAPPER;

        File mapperFile = new File(folder, fileName + ".xml");

        OutputStream out = null;
        OutputStreamWriter outw = null;
        BufferedWriter bw = null;
        try {
            out = new FileOutputStream(mapperFile);
            outw = new OutputStreamWriter(out, "utf-8");
            bw = new BufferedWriter(outw);

            //头信息
            bw.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
            bw.newLine();
            bw.write("<!DOCTYPE mapper");
            bw.newLine();
            bw.write("\t\tPUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\"");
            bw.newLine();
            bw.write("\t\t\"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">");
            bw.newLine();
            bw.write("<mapper namespace=\"" + Constants.PACKAGE_MAPPERS + "." + fileName + "\">");
            bw.newLine();
            bw.newLine();

            //索引
            bw.write("\t<!-- 实体映射 -->");
            bw.newLine();
            bw.write("\t<resultMap id=\"base_result_map\" type=\"" + PO_BEAN_NAME + "\">");
            bw.newLine();
            bw.newLine();

            FieldInfo idField = null;
            Map<String, List<FieldInfo>> keyIndexMap = tableInfo.getKeyIndexMap();
            for (Map.Entry<String, List<FieldInfo>> entry : keyIndexMap.entrySet()) {
                if ("PRIMARY".equals(entry.getKey())) {
                    List<FieldInfo> fieldInfoList = entry.getValue();
                    if (fieldInfoList.size() == 1) {
                        idField = fieldInfoList.get(0);
                        break;
                    }
                }
            }

            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                bw.write("\t\t<!-- " + fieldInfo.getComment() + " -->");
                bw.newLine();
                String key = "";
                if (idField != null && fieldInfo.getPropertyName().equals(idField.getPropertyName())) {
                    key = "id";
                } else {
                    key = "result";
                }
                bw.write("\t\t<" + key + " column=\"" + fieldInfo.getFieldName() + "\" property=\"" + fieldInfo.getPropertyName() + "\"/>");
                bw.newLine();
            }
            bw.write("\t</resultMap>");
            bw.newLine();
            bw.newLine();

            //通用查询列
            bw.write("\t<!-- 通用查询列 -->");
            bw.newLine();
            bw.write("\t<sql id=\"" + BASE_COLUMN_LIST + "\">");
            bw.newLine();
            StringBuilder columnBuilder = new StringBuilder();
            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                columnBuilder.append(fieldInfo.getFieldName()).append(",");
            }
            String columnBuilderStr = columnBuilder.substring(0, columnBuilder.lastIndexOf(","));
            bw.write("\t\t" + columnBuilderStr);
            bw.newLine();
            bw.write("\t</sql>");
            bw.newLine();
            bw.newLine();

            //基础查询条件
            bw.write("\t<!-- 基础查询条件 -->");
            bw.newLine();
            bw.write("\t<sql id=\"" + BASE_QUERY_CONDITION + "\">");
            bw.newLine();
            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                String stringQuery = "";
                if (ArrayUtils.contains(Constants.SQL_STRING_TYPE, fieldInfo.getSqlType())) {
                    stringQuery = " and query." + fieldInfo.getPropertyName() + " != ''";
                }
                bw.write("\t\t<if test=\"query." + fieldInfo.getPropertyName() + " != null" + stringQuery + "\">");
                bw.newLine();
                bw.write("\t\t\tand id = #{query." + fieldInfo.getPropertyName() + "}");
                bw.newLine();
                bw.write("\t\t</if>");
                bw.newLine();
            }
            bw.write("\t</sql>");
            bw.newLine();
            bw.newLine();

            //拓展的查询条件
            bw.write("\t<!-- 拓展的查询条件 -->");
            bw.newLine();
            bw.write("\t<sql id=\"" + BASE_QUERY_CONDITION_EXTEND + "\">");
            bw.newLine();
            for (FieldInfo fieldInfo : tableInfo.getFieldExtendList()) {
                String andWhere = "";
                if (ArrayUtils.contains(Constants.SQL_STRING_TYPE, fieldInfo.getSqlType())) {
                    andWhere = " and " + fieldInfo.getFieldName() + " like count('%', #{query." + fieldInfo.getPropertyName() + "}, '%')";
                } else if (ArrayUtils.contains(Constants.SQL_DATE_TYPE, fieldInfo.getSqlType()) || ArrayUtils.contains(Constants.SQL_DATE_TIME_TYPE, fieldInfo.getSqlType())) {
                    if (fieldInfo.getPropertyName().endsWith(Constants.SUFFIX_BEAN_QUERY_TIME_START)) {
                        andWhere = "<![CDATA[ and " + fieldInfo.getFieldName() + " >= str_to_date(#{query." + fieldInfo.getPropertyName() + "}, '%Y-%m-%d') ]]>";
                    } else if (fieldInfo.getPropertyName().endsWith(Constants.SUFFIX_BEAN_QUERY_TIME_END)) {
                        andWhere = "<![CDATA[ and " + fieldInfo.getFieldName() + " < date_sub(str_to_date(#{query." + fieldInfo.getPropertyName() + "},'%Y-%m-%d'),interval - 1 day) ]]>";
                    }
                }
                bw.write("\t\t<if test=\"query." + fieldInfo.getPropertyName() + " != null and query." + fieldInfo.getPropertyName() + " !=''\">");
                bw.newLine();
                bw.write("\t\t\t" + andWhere);
                bw.newLine();
                bw.write("\t\t</if>");
                bw.newLine();
            }
            bw.write("\t</sql>");
            bw.newLine();
            bw.newLine();

            //通用查询条件
            bw.write("\t<!-- 通用查询条件 -->");
            bw.newLine();
            bw.write("\t<sql id=\"" + QUERY_CONDITION + "\">");
            bw.newLine();
            bw.write("\t\t<where>");
            bw.newLine();
            bw.write("\t\t\t<include refid=\"" + BASE_QUERY_CONDITION + "\"/>");
            bw.newLine();
            bw.write("\t\t\t<include refid=\"" + BASE_QUERY_CONDITION_EXTEND + "\"/>");
            bw.newLine();
            bw.write("\t\t</where>");
            bw.newLine();
            bw.write("\t</sql>");
            bw.newLine();
            bw.newLine();

            //查询列表
            bw.write("\t<!-- 查询列表 -->");
            bw.newLine();
            bw.write("\t<select id=\"selectList\" resultMap=\"base_result_map\">");
            bw.newLine();
            bw.write("\t\tSELECT <include refid=\"" + BASE_COLUMN_LIST + "\"/> FROM " + tableInfo.getTableName() + " <include refid=\"" + QUERY_CONDITION + "\"/>");
            bw.newLine();
            bw.write("\t\t<if test=\"query.orderBy!=null\">order by ${query.orderBy}</if>");
            bw.newLine();
            bw.write("\t\t<if test=\"query.simplePage!=null\">limit #{query.simplePage.start},#{query.simplePage.end}</if>");
            bw.newLine();
            bw.write("\t</select>");
            bw.newLine();
            bw.newLine();

            //查询数量
            bw.write("\t<!-- 查询数量 -->");
            bw.newLine();
            bw.write("\t<select id=\"selectCount\" resultMap=\"java.lang.Long\">");
            bw.newLine();
            bw.write("\t\tSELECT count(1) FROM " + tableInfo.getTableName() + " <include refid=\"" + QUERY_CONDITION + "\"/>");
            bw.newLine();
            bw.write("\t</select>");
            bw.newLine();
            bw.newLine();

            //插入单条记录
            bw.write("\t<!-- 添加 插入 （匹配有值的字段） -->");
            bw.newLine();
            bw.write("\t<insert id=\"insect\" parameterType=\"" + PO_BEAN_NAME + "\">");
            bw.newLine();

            FieldInfo autoIncrementField = null;
            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                if (fieldInfo.getAutoIncrement() != null && fieldInfo.getAutoIncrement()) {
                    autoIncrementField = fieldInfo;
                    break;
                }
            }

            if (autoIncrementField != null) {
                bw.write("\t\t<selectKey keyProperty=\"bean." + autoIncrementField.getFieldName() + "\" resultType=\"" + autoIncrementField.getJavaType() + "\" order=\"AFTER\">");
                bw.newLine();
                bw.write("\t\t\tSELECT LAST_INSERT_ID()");
                bw.newLine();
                bw.write("\t\t</selectKey>");
                bw.newLine();
            }
            bw.write("\t\tINSERT INTO " + tableInfo.getTableName());
            bw.newLine();
            bw.write("\t\t<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
            bw.newLine();
            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                bw.write("\t\t\t<if test=\"bean." + fieldInfo.getPropertyName() + " != null\">");
                bw.newLine();
                bw.write("\t\t\t\t" + fieldInfo.getFieldName() + ",");
                bw.newLine();
                bw.write("\t\t\t</if>");
                bw.newLine();
            }
            bw.write("\t\t</trim>");
            bw.newLine();

            bw.write("\t\t<trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\">");
            bw.newLine();
            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                bw.write("\t\t\t<if test=\"bean." + fieldInfo.getPropertyName() + " != null\">");
                bw.newLine();
                bw.write("\t\t\t\t#{bean." + fieldInfo.getPropertyName() + "},");
                bw.newLine();
                bw.write("\t\t\t</if>");
                bw.newLine();
            }
            bw.write("\t\t</trim>");
            bw.newLine();

            bw.write("\t</insert>");
            bw.newLine();
            bw.newLine();

            //插入或更新单条记录
            bw.write("\t<!-- 添加或修改 插入或更新 （匹配有值的字段） -->");
            bw.newLine();
            bw.write("\t<insert id=\"insectOrUpdate\" parameterType=\"" + PO_BEAN_NAME + "\">");
            bw.newLine();
            bw.write("\t\tINSERT INTO " + tableInfo.getTableName());
            bw.newLine();
            bw.write("\t\t<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
            bw.newLine();
            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                bw.write("\t\t\t<if test=\"bean." + fieldInfo.getPropertyName() + " != null\">");
                bw.newLine();
                bw.write("\t\t\t\t" + fieldInfo.getFieldName() + ",");
                bw.newLine();
                bw.write("\t\t\t</if>");
                bw.newLine();
            }

            bw.write("\t\t</trim>");
            bw.newLine();

            bw.write("\t\t<trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\">");
            bw.newLine();
            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                bw.write("\t\t\t<if test=\"bean." + fieldInfo.getPropertyName() + " != null\">");
                bw.newLine();
                bw.write("\t\t\t\t#{bean." + fieldInfo.getPropertyName() + "},");
                bw.newLine();
                bw.write("\t\t\t</if>");
                bw.newLine();
            }
            bw.write("\t\t</trim>");
            bw.newLine();
            bw.write("\t\ton DUPLICATE key update");
            bw.newLine();
            bw.write("\t\t<trim prefix=\"\" suffix=\"\" suffixOverrides=\",\">");
            bw.newLine();

            Map<String, String> keyTempMap = new HashMap<String, String>();
            for (Map.Entry<String, List<FieldInfo>> entry : keyIndexMap.entrySet()) {
                List<FieldInfo> fieldInfoList = entry.getValue();
                for (FieldInfo item : fieldInfoList) {
                    keyTempMap.put(item.getFieldName(), item.getFieldName());
                }
            }

            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                if (keyTempMap.get(fieldInfo.getFieldName()) != null) {
                    continue;
                }
                bw.write("\t\t\t<if test=\"bean." + fieldInfo.getPropertyName() + " != null\">");
                bw.newLine();
                bw.write("\t\t\t\t" + fieldInfo.getFieldName() + " = VALUES(" + fieldInfo.getFieldName() + "),");
                bw.newLine();
                bw.write("\t\t\t</if>");
                bw.newLine();
            }
            bw.write("\t\t</trim>");
            bw.newLine();
            bw.write("\t</insert>");
            bw.newLine();
            bw.newLine();

            //批量插入
            bw.write("\t<!-- 添加 批量插入 -->");
            bw.newLine();
            bw.write("\t<insert id=\"insertBatch\" parameterType=\"" + PO_BEAN_NAME + "\">");
            bw.newLine();
            StringBuffer insertFiledBuffer = new StringBuffer();
            StringBuffer insertPropertyBuffer = new StringBuffer();
            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                if (fieldInfo.getAutoIncrement()) {
                    continue;
                }
                insertFiledBuffer.append(fieldInfo.getPropertyName()).append(",");
                insertPropertyBuffer.append("#{item." + fieldInfo.getPropertyName() + "}").append(",");
            }
            String insertFieldBufferStr = insertFiledBuffer.substring(0, insertFiledBuffer.lastIndexOf(","));
            String insertPropertyBufferStr = insertPropertyBuffer.substring(0, insertPropertyBuffer.lastIndexOf(","));
            bw.write("\t\tINSERT INTO " + tableInfo.getTableName() + "(" + insertFieldBufferStr + ")values");
            bw.newLine();
            bw.write("\t\t<foreach collection=\"list\" item=\"item\" separator=\",\">");
            bw.newLine();
            bw.write("\t\t\t(" + insertPropertyBufferStr + ")");
            bw.newLine();
            bw.write("\t\t</foreach>");
            bw.newLine();
            bw.write("\t</insert>");
            bw.newLine();
            bw.newLine();

            //批量插入或更新
            bw.write("\t<!-- 添加或修改 批量插入或更新 -->");
            bw.newLine();
            bw.write("\t<insert id=\"insertOrUpdateBatch\" parameterType=\"" + PO_BEAN_NAME + "\">");
            bw.newLine();
            StringBuffer insertOrUpdateFiledBuffer = new StringBuffer();
            StringBuffer insertOrUpdatePropertyBuffer = new StringBuffer();
            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                if (fieldInfo.getAutoIncrement()) {
                    continue;
                }
                insertOrUpdatePropertyBuffer.append("#{item." + fieldInfo.getPropertyName() + "}").append(",");
                insertOrUpdateFiledBuffer.append(fieldInfo.getPropertyName()).append(",");
            }
            String insertOrUpdateFieldBufferStr = insertOrUpdateFiledBuffer.substring(0, insertOrUpdateFiledBuffer.lastIndexOf(","));
            String insertOrUpdatePropertyBufferStr = insertOrUpdatePropertyBuffer.substring(0, insertOrUpdatePropertyBuffer.lastIndexOf(","));
            bw.write("\t\tINSERT INTO " + tableInfo.getTableName() + "(" + insertOrUpdateFieldBufferStr + ")values");
            bw.newLine();
            bw.write("\t\t<foreach collection=\"list\" item=\"item\" separator=\",\">");
            bw.newLine();
            bw.write("\t\t\t(" + insertOrUpdatePropertyBufferStr + ")");
            bw.newLine();
            bw.write("\t\t</foreach>");
            bw.newLine();
            bw.write("\t\ton DUPLICATE key update");
            StringBuffer insertBatchUpdateBuffer = new StringBuffer();
            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                insertBatchUpdateBuffer.append(fieldInfo.getFieldName() + " = VALUES(" + fieldInfo.getFieldName() + "),");
            }
            String insertBatchUpdateBufferStr = insertBatchUpdateBuffer.substring(0, insertBatchUpdateBuffer.lastIndexOf(","));
            bw.write(" " + insertBatchUpdateBufferStr);
            bw.newLine();
            bw.write("\t</insert>");
            bw.newLine();
            bw.newLine();

            //根据主键查询更新删除
            for (Map.Entry<String, List<FieldInfo>> entry : keyIndexMap.entrySet()) {
                List<FieldInfo> keyFieldInfoList = entry.getValue();

                int index = 0;
                StringBuilder methodName = new StringBuilder();
                StringBuffer paramNames = new StringBuffer();
                for (FieldInfo fieldInfo : keyFieldInfoList) {
                    index++;
                    methodName.append(StringUtils.upperFirstLetter(fieldInfo.getPropertyName()));
                    paramNames.append(fieldInfo.getFieldName() + "=#{" + fieldInfo.getPropertyName() + "}");
                    if (index < keyFieldInfoList.size()) {
                        methodName.append("And");
                        paramNames.append(" and ");
                    }
                }
                //根据主键查询
                bw.write("\t<!-- 根据" + methodName + "查询 -->");
                bw.newLine();
                bw.write("\t<select id=\"selectBy" + methodName + "\" resultMap=\"base_result_map\">");
                bw.newLine();
                bw.write("\t\tselect <include refid=\"" + BASE_COLUMN_LIST + "\"/> from " + tableInfo.getTableName() + " where " + paramNames);
                bw.newLine();
                bw.write("\t</select>");
                bw.newLine();
                bw.newLine();

                //根据主键更新
                bw.write("\t<!-- 根据" + methodName + "更新 -->");
                bw.newLine();
                bw.write("\t<update id=\"updateBy" + methodName + "\" parameterType=\"" + PO_BEAN_NAME + "\">");
                bw.newLine();
                bw.write("\t\tupdate " + tableInfo.getTableName());
                bw.newLine();
                bw.write("\t\t<set>");
                bw.newLine();
                for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                    bw.write("\t\t\t<if test=\"bean." + fieldInfo.getPropertyName() + "!=null\">");
                    bw.newLine();
                    bw.write("\t\t\t\t" + fieldInfo.getFieldName() + " = #{bean." + fieldInfo.getPropertyName() + "}");
                    bw.newLine();

                    bw.write("\t\t\t</if>");
                    bw.newLine();
                }
                bw.write("\t\t</set>");
                bw.newLine();
                bw.write("\t\twhere " + paramNames);
                bw.newLine();
                bw.write("\t</update>");
                bw.newLine();
                bw.newLine();

                //根据主键删除
                bw.write("\t<!-- 根据" + methodName + "删除 -->");
                bw.newLine();
                bw.write("\t<delete id=\"deleteBy" + methodName + "\">");
                bw.newLine();
                bw.write("\t\tdelete from " + tableInfo.getTableName() + " where " + paramNames);
                bw.newLine();
                bw.write("\t</delete>");
                bw.newLine();
                bw.newLine();

            }

            //-----------------
            bw.write("</mapper>");
            bw.flush();

        } catch (Exception e) {
            logger.info("创建mapper XML文件失败", e);
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (outw != null) {
                try {
                    outw.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    public static Logger logger = LoggerFactory.getLogger(BuildMapperXml.class);
}
