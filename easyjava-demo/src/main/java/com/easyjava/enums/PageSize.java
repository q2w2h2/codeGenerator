package com.easyjava.enums;

public enum PageSize {
    SIZE1(1), SIZE2(2), SIZE5(5), SIZE10(10), SIZE15(15), SIZE20(20), SIZE30(30), SIZE40(40), SIZE50(50), SIZE100(100);
    int size;


    PageSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return this.size;
    }
}
