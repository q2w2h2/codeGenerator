package com.easyjava.builder;

import com.easyjava.bean.Constants;
import com.easyjava.utils.DateUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Date;

/**
 *
 */
public class BuildComment {
    public static void buildClassComment(BufferedWriter bw, String comment) throws Exception {
        bw.write("/**");
        bw.newLine();
        bw.write(" * @Description: " + comment);
        bw.newLine();
        bw.write(" * @author: " + Constants.AUTHOR);
        bw.newLine();
        bw.write(" * @date: " + DateUtils.format(new Date(), DateUtils._YYYY_MM_DD));
        bw.newLine();
        bw.write(" */");
        bw.newLine();
    }

    public static void buildFieldComment(BufferedWriter bw, String comment) throws IOException {
        if (comment == null) {
            comment = "";
        }
        bw.write("\t/**");
        bw.newLine();
        bw.write("\t * " + comment);
        bw.newLine();
        bw.write("\t */");
        bw.newLine();
    }

    public static void buildMethodComment(BufferedWriter bw, String comment) throws IOException{
        if (comment == null) {
            comment = "";
        }
        bw.write("\t/**");
        bw.newLine();
        bw.write("\t * " + comment);
        bw.newLine();
        bw.write("\t */");
        bw.newLine();
    }
}
