package com.example.zexiger.myapplication.base;

import android.app.Application;
import android.content.Context;

import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager;

import org.litepal.LitePal;

public class MyApplication extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        //
        LitePal.initialize(context);
        QMUISwipeBackActivityManager.init(this);
    }
    public static Context getContext(){
        return context;
    }
}
