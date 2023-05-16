package com.easyjava.builder;

import com.easyjava.bean.Constants;
import com.easyjava.bean.FieldInfo;
import com.easyjava.bean.TableInfo;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class BuildMapper {
    public static Logger logger = LoggerFactory.getLogger(BuildMapper.class);

    public static void execute(TableInfo tableInfo) {
        //创建文件夹
        File folder = new File(Constants.PATH_MAPPERS);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String fileName = tableInfo.getBeanName() + Constants.SUFFIX_MAPPER;

        File mapperFile = new File(folder, fileName + ".java");

        OutputStream out = null;
        OutputStreamWriter outw = null;
        BufferedWriter bw = null;
        try {
            out = new FileOutputStream(mapperFile);
            outw = new OutputStreamWriter(out, "utf-8");
            bw = new BufferedWriter(outw);

            bw.write("package " + Constants.PACKAGE_MAPPERS + ";");
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

            //遍历字段生成类的属性
            for (FieldInfo field : tableInfo.getFieldList()) {
                BuildComment.buildFieldComment(bw, field.getComment());
                bw.write("\tprivate " + field.getJavaType() + " " + field.getPropertyName() + ";");
                bw.newLine();
            }
            bw.write("}");
            bw.flush();

        } catch (Exception e) {
            logger.info("创建mapper文件失败", e);
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
