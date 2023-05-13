package com.easyjava.builder;

import com.easyjava.bean.Constants;
import com.easyjava.bean.FieldInfo;
import com.easyjava.bean.TableInfo;
import com.easyjava.utils.DateUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class BuildPo {
    public static Logger logger = LoggerFactory.getLogger(BuildPo.class);

    public static void execute(TableInfo tableInfo) {
        //创建文件夹
        File folder = new File(Constants.PATH_PO);
        if (!folder.exists()) {
            folder.mkdirs();
            System.out.println(folder);
        }

        File poFile = new File(folder, tableInfo.getBeanName() + ".java");

        OutputStream out = null;
        OutputStreamWriter outw = null;
        BufferedWriter bw = null;
        try {
            out = new FileOutputStream(poFile);
            outw = new OutputStreamWriter(out, "utf-8");
            bw = new BufferedWriter(outw);

            bw.write("package " + Constants.PACKAGE_PO + ";");
            bw.newLine();
            bw.newLine();
            bw.write("import java.io.Serializable;");
            bw.newLine();
            if (tableInfo.getHaveDateTime()) {
                bw.write("import java.math.BigDecimal;");
                bw.newLine();
            }
            if (tableInfo.getHaveDateTime() || tableInfo.getHaveDate()) {
                bw.write("import java.util.Date;");
                bw.newLine();
                bw.write(Constants.BEAN_DATE_FORMAT_CLASS);
                bw.newLine();
                bw.write(Constants.BEAN_DATE_UNFORMAT_CLASS);
                bw.newLine();
            }
            bw.newLine();
            bw.newLine();
            BuildComment.buildClassComment(bw, tableInfo.getComment());
            bw.write("public class " + tableInfo.getBeanName() + " implements Serializable {");
            bw.newLine();

            for (FieldInfo field : tableInfo.getFieldList()) {
                BuildComment.buildFieldComment(bw, field.getComment());
                if (ArrayUtils.contains(Constants.SQL_DATE_TIME_TYPE, field.getSqlType())) {
                    bw.write("\t" + String.format(Constants.BEAN_DATE_FORMAT_EXPRESSION,DateUtils.YYYY_MM_DD_HH_MM_SS));
                    bw.newLine();
                    bw.write("\t" + String.format(Constants.BEAN_DATE_UNFORMAT_EXPRESSION,DateUtils.YYYY_MM_DD));
                    bw.newLine();
                }
                if (ArrayUtils.contains(Constants.SQL_DATE_TYPE, field.getSqlType())) {
                    bw.write("\t" + String.format(Constants.BEAN_DATE_FORMAT_EXPRESSION,DateUtils.YYYY_MM_DD));
                    bw.newLine();
                    bw.write("\t" + String.format(Constants.BEAN_DATE_UNFORMAT_EXPRESSION,DateUtils.YYYY_MM_DD));
                    bw.newLine();
                }
                bw.write("\tprivate " + field.getJavaType() + " " + field.getPropertyName() + ";");
                bw.newLine();
            }

            bw.write("}");
            bw.flush();


        } catch (Exception e) {
            logger.info("创建po文件失败", e);
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
