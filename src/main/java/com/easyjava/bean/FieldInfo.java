package com.easyjava.bean;

public class FieldInfo {
    private String fieldName;
    private String propertyName;
    //sql类型
    private String sqlType;
    //java类型
    private String javaType;
    //备注
    private String comment;
    //是否自增长
    private Boolean isAutoIncrement;
    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getSqlType() {
        return sqlType;
    }

    public void setSqlType(String sqlType) {
        this.sqlType = sqlType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean getAutoIncrement() {
        return isAutoIncrement;
    }

    public void setAutoIncrement(Boolean autoIncrement) {
        isAutoIncrement = autoIncrement;
    }


    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    public String getJavaType() {
        return javaType;
    }
}
