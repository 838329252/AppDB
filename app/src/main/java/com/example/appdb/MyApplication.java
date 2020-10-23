package com.example.appdb;

import android.app.Application;
import android.content.Context;
import android.util.Log;


public class MyApplication extends Application {
    private String TAG="MyApplication";
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        //数据库初始化
        InitDatabase.initDatabase(context);
    }
}
