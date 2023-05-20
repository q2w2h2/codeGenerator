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

        File poQureyFile = new File(folder, fileName + ".java");

        OutputStream out = null;
        OutputStreamWriter outw = null;
        BufferedWriter bw = null;
        try {
            out = new FileOutputStream(poQureyFile);
            outw = new OutputStreamWriter(out, "utf-8");
            bw = new BufferedWriter(outw);



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
            bw.write("public class " + fileName + " extends BaseQuery {");
            bw.newLine();

            //遍历字段生成类的属性
            for (FieldInfo field : tableInfo.getFieldList()) {
                BuildComment.buildFieldComment(bw, field.getComment());
                bw.write("\tprivate " + field.getJavaType() + " " + field.getPropertyName() + ";");
                bw.newLine();
                if (ArrayUtils.contains(Constants.SQL_DATE_TIME_TYPE, field.getSqlType()) || ArrayUtils.contains(Constants.SQL_DATE_TYPE, field.getSqlType())) {
                    bw.write("\tprivate String " + field.getPropertyName() + Constants.SUFFIX_BEAN_QUERY_TIME_START + ";");
                    bw.newLine();
                    bw.write("\tprivate String " + field.getPropertyName() + Constants.SUFFIX_BEAN_QUERY_TIME_END + ";");
                    bw.newLine();
                }

                if (ArrayUtils.contains(Constants.SQL_STRING_TYPE, field.getSqlType())) {
                    bw.write("\tprivate " + field.getJavaType() + " " + field.getPropertyName() + Constants.SUFFIX_BEAN_QUERY_FUZZY + ";");
                    bw.newLine();
                }
            }
            //生成属性get,set方法
            buildGetSet(bw, tableInfo.getFieldList());
            buildGetSet(bw, tableInfo.getFieldExtendList());
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

    public static void buildGetSet(BufferedWriter bw, List<FieldInfo> fieldInfoList) throws Exception {
        for (FieldInfo field : fieldInfoList) {
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
    }
}
