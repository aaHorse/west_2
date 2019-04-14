package com.example.zexiger.myapplication_1.db;

import org.litepal.crud.DataSupport;

public class FlagFirst extends DataSupport {
    private String str="处于未登录";

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
}
