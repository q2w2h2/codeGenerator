package com.easyjava.builder;

import com.easyjava.bean.Constants;
import com.easyjava.bean.FieldInfo;
import com.easyjava.bean.TableInfo;
import com.easyjava.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;
import java.util.Map;

public class BuildService {
    public static Logger logger = LoggerFactory.getLogger(BuildService.class);

    public static void execute(TableInfo tableInfo) {
        //创建文件夹
        File folder = new File(Constants.PATH_SERVICE);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        String className = tableInfo.getBeanName() + "Service";
        File poFile = new File(folder, className + ".java");

        OutputStream out = null;
        OutputStreamWriter outw = null;
        BufferedWriter bw = null;
        try {
            out = new FileOutputStream(poFile);
            outw = new OutputStreamWriter(out, "utf-8");
            bw = new BufferedWriter(outw);

            bw.write("package " + Constants.PACKAGE_SERVICE + ";");
            bw.newLine();
            bw.newLine();
            bw.write("import " + Constants.PACKAGE_PO + "." + tableInfo.getBeanName() + ";");
            bw.newLine();
            bw.write("import " + Constants.PACKAGE_QUERY + "." + tableInfo.getBeanParamName() + ";");
            bw.newLine();
            bw.write("import " + Constants.PACKAGE_VO + ".PaginationResultVO;");
            bw.newLine();
            bw.newLine();
            bw.write("import java.util.List;");
            bw.newLine();

            BuildComment.buildClassComment(bw, tableInfo.getComment() + "业务接口");
            bw.write("public interface " + className + " {");
            bw.newLine();

            BuildComment.buildMethodComment(bw, "根据条件查询列表");
            bw.write("\tList<" + tableInfo.getBeanName() + "> findListByParam(" + tableInfo.getBeanParamName() + " query);");
            bw.newLine();
            bw.newLine();

            BuildComment.buildMethodComment(bw, "根据条件查询数量");
            bw.write("\tLong findCountByParam(" + tableInfo.getBeanParamName() + " query);");
            bw.newLine();
            bw.newLine();

            BuildComment.buildMethodComment(bw, "分页查询");
            bw.write("\tPaginationResultVO<" + tableInfo.getBeanName() + "> findListByPage(" + tableInfo.getBeanParamName() + " query);");
            bw.newLine();
            bw.newLine();

            BuildComment.buildMethodComment(bw, "新增");
            bw.write("\tLong add(" + tableInfo.getBeanName() + " bean);");
            bw.newLine();
            bw.newLine();

            BuildComment.buildMethodComment(bw, "批量新增");
            bw.write("\tLong addBatch(List<" + tableInfo.getBeanName() + "> listBean);");
            bw.newLine();
            bw.newLine();

            BuildComment.buildMethodComment(bw, "批量新增或修改");
            bw.write("\tLong addOrUpdateBatch(List<" + tableInfo.getBeanName() + "> listBean);");
            bw.newLine();
            bw.newLine();

            for (Map.Entry<String, List<FieldInfo>> entry : tableInfo.getKeyIndexMap().entrySet()) {
                List<FieldInfo> keyFieldInfoList = entry.getValue();
                int index = 0;
                StringBuilder methodName = new StringBuilder();
                StringBuilder methodParam = new StringBuilder();
                for (FieldInfo fieldInfo : keyFieldInfoList) {
                    index++;
                    methodName.append(StringUtils.upperFirstLetter(fieldInfo.getPropertyName()));
                    methodParam.append(fieldInfo.getJavaType() + " " + fieldInfo.getPropertyName());
                    if (index < keyFieldInfoList.size()) {
                        methodName.append("And");
                        methodParam.append(", ");
                    }
                }

                BuildComment.buildFieldComment(bw, "根据" + methodName + "查询");
                bw.write("\t" + tableInfo.getBeanName() + " getBy" + methodName + "(" + methodParam + ");");
                bw.newLine();
                bw.newLine();

                BuildComment.buildFieldComment(bw, "根据" + methodName + "更新");
                bw.write("\tLong updateBy" + methodName + "(" + tableInfo.getBeanName() + " bean, " + methodParam + ");");
                bw.newLine();
                bw.newLine();

                BuildComment.buildFieldComment(bw, "根据" + methodName + "删除");
                bw.write("\tLong deleteBy" + methodName + "(" + methodParam + ");");
                bw.newLine();
                bw.newLine();
            }


            bw.write("}");
            bw.flush();

        } catch (Exception e) {
            logger.info("创建service文件失败", e);
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
