package com.easyjava;


import com.easyjava.builder.BuildTable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RunApplication {
    public static void main(String[] args) {
        BuildTable.getTables();
    }
}
