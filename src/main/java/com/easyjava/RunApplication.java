package com.easyjava;


import com.easyjava.bean.TableInfo;
import com.easyjava.builder.BuildBase;
import com.easyjava.builder.BuildPo;
import com.easyjava.builder.BuildQuery;
import com.easyjava.builder.BuildTable;
import com.easyjava.utils.RunUtils;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class RunApplication {
    public static void main(String[] args) {
        RunUtils.run();
    }
}
