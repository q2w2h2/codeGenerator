package com.easyjava.builder;

import com.easyjava.bean.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class BuildBase {
    public static Logger logger = LoggerFactory.getLogger(BuildBase.class);
    public static String PACKAGE_UTILS = "package " + Constants.PACKAGE_UTILS + ";";
    public static String PACKAGE_ENUMS = "package " + Constants.PACKAGE_ENUMS + ";";
    public static String PACKAGE_MAPPERS = "package " + Constants.PACKAGE_MAPPERS + ";";
    public static String PACKAGE_QUERY = "package " + Constants.PACKAGE_QUERY + ";";
    public static String PACKAGE_VO = "package " + Constants.PACKAGE_VO + ";";

    public static void execute() {
        List<String> headerInfoList = new ArrayList<String>();

        //生成date枚举
        headerInfoList.add(PACKAGE_ENUMS);
        build(headerInfoList, "DateTimePatternEnum", Constants.PATH_ENUMS);
        headerInfoList.clear();

        //生成date工具
        headerInfoList.add(PACKAGE_UTILS);
        build(headerInfoList, "DateUtils", Constants.PATH_UTILS);
        headerInfoList.clear();

        //生成baseMapper
        headerInfoList.add(PACKAGE_MAPPERS);
        build(headerInfoList, "BaseMapper", Constants.PATH_MAPPERS);
        headerInfoList.clear();

        //生成pageSize枚举
        headerInfoList.add(PACKAGE_ENUMS);
        build(headerInfoList, "PageSize", Constants.PATH_ENUMS);
        headerInfoList.clear();

        //生成simplePage
        headerInfoList.add(PACKAGE_QUERY);
        //这个类还需要导包一下PageSize
        headerInfoList.add("import " + Constants.PACKAGE_ENUMS + ".PageSize" + ";");
        build(headerInfoList, "SimplePage", Constants.PATH_QUERY);
        headerInfoList.clear();

        //生成baseQuery
        headerInfoList.add(PACKAGE_QUERY);
        build(headerInfoList, "BaseQuery", Constants.PATH_QUERY);
        headerInfoList.clear();

        //生成paginationResultVO
        headerInfoList.add(PACKAGE_VO);
        build(headerInfoList, "PaginationResultVO", Constants.PATH_VO);
        headerInfoList.clear();

    }

    /**
     * build方法,传入参数[String列表] ：表示头部要先加入的内容(package、import等) [String类型的文件名称] [String类型的输出路径]
     */
    public static void build(List<String> headerInfoList, String fileName, String outPutPath) throws RuntimeException {
        File folder = new File(outPutPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        //java文件名包括.java后缀
        File javaFile = new File(outPutPath, fileName + ".java");

        OutputStream out = null;
        OutputStreamWriter outw = null;
        BufferedWriter bw = null;

        InputStream in = null;
        InputStreamReader inr = null;
        BufferedReader br = null;
        try {
            out = new FileOutputStream(javaFile);
            outw = new OutputStreamWriter(out, "utf-8");
            bw = new BufferedWriter(outw);

            //这里使用另一种getClassLoader写法获取不到值,百度了半天没有解决
            String templatePath = "E:/code/workspace-easyjava/easyjava/src/main/resources/template";
            templatePath = templatePath + "/" + fileName + ".txt";
            //String templatePath = BuildBase.class.getClassLoader().getResource("template/" + fileName + ".txt").getPath();
            in = new FileInputStream(templatePath);
            inr = new InputStreamReader(in, "utf-8");
            br = new BufferedReader(inr);

            for (String head : headerInfoList) {
                bw.write(head);
                bw.newLine();
                bw.newLine();
            }

            String line;
            while ((line = br.readLine()) != null) {
                bw.write(line);
                bw.newLine();
            }


        } catch (Exception e) {
            logger.error("生成基础类：{},失败:", fileName, e);
        } finally {
            if (br != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (inr != null) {
                try {
                    outw.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (in != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

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
