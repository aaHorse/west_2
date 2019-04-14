package com.example.zexiger.myapplication_1.http_util;

public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
