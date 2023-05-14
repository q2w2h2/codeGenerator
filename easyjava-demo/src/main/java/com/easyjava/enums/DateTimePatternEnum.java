package com.easyjava.enums;

public enum DateTimePatternEnum {
    YYYY_MM_DD_HH_MM_SS("yyyy-mm-dd HH:mm:ss"), YYYY_MM_DD("yyyy-mm-dd");

    private String pattern;

    DateTimePatternEnum(String pattern) { this.pattern = pattern; }

    public String getPattern() { return pattern; }
}
