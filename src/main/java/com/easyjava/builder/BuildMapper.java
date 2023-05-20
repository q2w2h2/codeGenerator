package com.easyjava.builder;

import com.easyjava.bean.Constants;
import com.easyjava.bean.FieldInfo;
import com.easyjava.bean.TableInfo;
import com.easyjava.utils.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;
import java.util.Map;

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

            bw.write("import org.springframework.data.repository.query.Param;");
            bw.newLine();
            bw.newLine();

            //添加类注释
            BuildComment.buildClassComment(bw, tableInfo.getComment() + "Mapper对象");
            bw.write("public interface " + fileName + "<T, P> extends BaseMapper {");
            bw.newLine();

            Map<String, List<FieldInfo>> keyIndexMap = tableInfo.getKeyIndexMap();

            for (Map.Entry<String, List<FieldInfo>> entry : keyIndexMap.entrySet()) {
                List<FieldInfo> keyFieldInfoList = entry.getValue();
                int index = 0;
                StringBuilder methodName = new StringBuilder();
                StringBuilder methodParam = new StringBuilder();
                for (FieldInfo fieldInfo : keyFieldInfoList) {
                    index++;
                    methodName.append(StringUtils.upperFirstLetter(fieldInfo.getPropertyName()));
                    methodParam.append("@Param(\"" + fieldInfo.getPropertyName() + "\") " + fieldInfo.getJavaType() + " " + fieldInfo.getPropertyName());
                    if (index < keyFieldInfoList.size()) {
                        methodName.append("And");
                        methodParam.append(", ");
                    }
                }

                BuildComment.buildFieldComment(bw, "根据" + methodName + "查询");
                bw.write("\tT selectBy" + methodName + "("+methodParam+");");
                bw.newLine();
                bw.newLine();

                BuildComment.buildFieldComment(bw, "根据" + methodName + "更新");
                bw.write("\tLong updateBy" + methodName + "(@Param(\"bean\") T t, "+methodParam+");");
                bw.newLine();
                bw.newLine();

                BuildComment.buildFieldComment(bw, "根据" + methodName + "删除");
                bw.write("\tLong deleteBy" + methodName + "("+methodParam+");");
                bw.newLine();
                bw.newLine();
            }
            bw.newLine();
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
