package com.example.appdb;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.room.Room;

import com.example.appdb.model.database.AppDatabase;

public class InitDatabase {
    private static AppDatabase mAppDb;
    private static  Application mInstance;
    private static Context mContext;

    public static void initDatabase(Context context){
        mContext=context;
        //数据库初始化
        if(mAppDb == null){
            Log.d("initDatebase","测试是否创建了数据库");
            mAppDb = Room.databaseBuilder(mContext, AppDatabase.class,"gochatdb").build();}
    }
    public static AppDatabase getAppDB(){
        return mAppDb;
    }
    public static Context getContext(){return mContext;}
}
