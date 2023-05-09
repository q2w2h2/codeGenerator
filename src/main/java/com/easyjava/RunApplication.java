package com.easyjava;


import com.easyjava.bean.TableInfo;
import com.easyjava.builder.BuildTable;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class RunApplication {
    public static void main(String[] args) {
        List<TableInfo> tableInfoList =  BuildTable.getTables();
        System.out.println(tableInfoList);
    }
}
