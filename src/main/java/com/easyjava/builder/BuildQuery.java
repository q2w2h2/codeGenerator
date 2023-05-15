package com.easyjava.builder;

import com.easyjava.bean.Constants;
import com.easyjava.bean.FieldInfo;
import com.easyjava.bean.TableInfo;
import com.easyjava.utils.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BuildQuery {
    public static Logger logger = LoggerFactory.getLogger(BuildQuery.class);

    public static void execute(TableInfo tableInfo) {
        //创建文件夹
        File folder = new File(Constants.PATH_QUERY);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String fileName = tableInfo.getBeanName() + Constants.SUFFIX_BEAN_QUERY;

        File poFile = new File(folder, fileName + ".java");

        OutputStream out = null;
        OutputStreamWriter outw = null;
        BufferedWriter bw = null;
        try {
            out = new FileOutputStream(poFile);
            outw = new OutputStreamWriter(out, "utf-8");
            bw = new BufferedWriter(outw);

            bw.write("package " + Constants.PACKAGE_QUERY + ";");
            bw.newLine();
            bw.newLine();

            if (tableInfo.getHaveDateTime()) {
                bw.write("import java.math.BigDecimal;");
                bw.newLine();
            }
            if (tableInfo.getHaveDateTime() || tableInfo.getHaveDate()) {
                bw.write("import java.util.Date;");
                bw.newLine();
                bw.newLine();
            }
            //添加类注释
            BuildComment.buildClassComment(bw, tableInfo.getComment() + "查询对象");
            bw.write("public class " + fileName + " {");
            bw.newLine();

            List<FieldInfo> addField = new ArrayList<FieldInfo>();
            //遍历字段生成类的属性
            for (FieldInfo field : tableInfo.getFieldList()) {
                BuildComment.buildFieldComment(bw, field.getComment());
                bw.write("\tprivate " + field.getJavaType() + " " + field.getPropertyName() + ";");
                bw.newLine();
                if (ArrayUtils.contains(Constants.SQL_DATE_TIME_TYPE, field.getSqlType()) || ArrayUtils.contains(Constants.SQL_DATE_TYPE, field.getSqlType())) {
                    String propertyNameStart, propertyNameEnd;
                    propertyNameStart = field.getPropertyName() + Constants.SUFFIX_BEAN_QUERY_TIME_START;
                    propertyNameEnd = field.getPropertyName() + Constants.SUFFIX_BEAN_QUERY_TIME_END;

                    bw.write("\tprivate String " + field.getPropertyName() + Constants.SUFFIX_BEAN_QUERY_TIME_START + ";");
                    bw.newLine();
                    bw.write("\tprivate String " + field.getPropertyName() + Constants.SUFFIX_BEAN_QUERY_TIME_END + ";");
                    bw.newLine();

                    FieldInfo timeStart = new FieldInfo();
                    timeStart.setJavaType("String");
                    timeStart.setPropertyName(propertyNameStart);
                    addField.add(timeStart);

                    FieldInfo timeEnd = new FieldInfo();
                    timeEnd.setJavaType("String");
                    timeEnd.setPropertyName(propertyNameEnd);
                    addField.add(timeEnd);
                }

                if (ArrayUtils.contains(Constants.SQL_STRING_TYPE, field.getSqlType())) {
                    String fuzzyName;
                    fuzzyName = field.getPropertyName() + Constants.SUFFIX_BEAN_QUERY_FUZZY;

                    bw.write("\tprivate " + field.getJavaType() + " " + field.getPropertyName() + Constants.SUFFIX_BEAN_QUERY_FUZZY + ";");
                    bw.newLine();

                    FieldInfo fuzzy = new FieldInfo();
                    fuzzy.setJavaType(field.getJavaType());
                    fuzzy.setPropertyName(fuzzyName);
                    addField.add(fuzzy);
                }
            }
            List<FieldInfo> newTableInfo =tableInfo.getFieldList();
            newTableInfo.addAll(addField);
            //重写属性get,set方法
            for (FieldInfo field : newTableInfo) {
                String name = StringUtils.upperFirstLetter(field.getPropertyName());
                bw.write("\tpublic " + field.getJavaType() + " get" + name + "() {");
                bw.newLine();
                bw.write("\t\treturn this." + field.getPropertyName() + ";");
                bw.newLine();
                bw.write("\t}");
                bw.newLine();
                bw.newLine();
                bw.write("\tpublic " + field.getJavaType() + " set" + name + "(" + field.getJavaType() + " " + field.getPropertyName() + ") {");
                bw.newLine();
                bw.write("\t\treturn this." + field.getPropertyName() + " = " + field.getPropertyName() + ";");
                bw.newLine();
                bw.write("\t}");
                bw.newLine();
                bw.newLine();
            }
            bw.write("}");
            bw.flush();

        } catch (Exception e) {
            logger.info("创建query文件失败", e);
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
}
