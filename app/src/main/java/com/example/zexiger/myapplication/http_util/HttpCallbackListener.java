package com.example.zexiger.myapplication.http_util;

public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
