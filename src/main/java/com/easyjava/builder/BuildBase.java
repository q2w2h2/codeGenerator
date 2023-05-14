package com.easyjava.builder;

import com.easyjava.bean.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;


public class BuildBase {
    public static Logger logger = LoggerFactory.getLogger(BuildBase.class);
    public static String HEAD_DATEUTILS = "package " + Constants.PACKAGE_UTILS + ";";
    public static String HEAD_DATETIMEPATTERNENUM = "package " + Constants.PACKAGE_ENUMS + ";";

    public static void execute() {
        build(HEAD_DATETIMEPATTERNENUM,"DateTimePatternEnum",Constants.PATH_ENUMS);
        build( HEAD_DATEUTILS, "DateUtils", Constants.PATH_UTILS);
    }

    public static void build(String head, String fileName, String outPutPath) throws RuntimeException {
        File folder = new File(outPutPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }

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


            String templatePath = "E:/code/workspace-easyjava/easyjava/src/main/resources/template";
            templatePath = templatePath + "/" +fileName +".txt";

            in = new FileInputStream(templatePath);
            inr = new InputStreamReader(in, "utf-8");
            br = new BufferedReader(inr);

            bw.write(head);
            bw.newLine();
            bw.newLine();

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
