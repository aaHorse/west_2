package com.example.zexiger.myapplication_1.db;

import org.litepal.crud.DataSupport;

public class QQ_messege extends DataSupport {
    String nickname="is_null";
    //腾讯官方文档提供的篮球
    String figureurl_qq_1="http://q.qlogo.cn/qqapp/222222/8C75BBE3DC6B0E9A64BD31449A3C8CB0/40";

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFigureurl_qq_1() {
        return figureurl_qq_1;
    }

    public void setFigureurl_qq_1(String figureurl_qq_1) {
        this.figureurl_qq_1 = figureurl_qq_1;
    }
}
