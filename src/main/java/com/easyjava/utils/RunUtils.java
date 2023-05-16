package com.easyjava.utils;

import com.easyjava.bean.TableInfo;
import com.easyjava.builder.*;

import java.util.List;

public class RunUtils {
    public static void run(){
        List<TableInfo> tableInfoList = BuildTable.getTables();
        //构建基本类
        BuildBase.execute();
        for (TableInfo tableInfo : tableInfoList) {
            BuildPo.execute(tableInfo);
            BuildQuery.execute(tableInfo);
            BuildMapper.execute(tableInfo);
        }
    }
}
