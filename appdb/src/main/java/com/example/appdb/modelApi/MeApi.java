package com.example.appdb.modelApi;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.appdb.InitDatabase;
import com.example.appdb.model.pojo.Me;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MeApi {
    private static Context mContext;
    private static SharedPreferences.Editor editor;
    private static SharedPreferences sp;
    //执行线程池 数据库的访问需要在子线程执行
    private static ExecutorService mThreadPool = Executors.newSingleThreadExecutor();
    private static void initContext(){
        if(mContext==null){
            mContext= InitDatabase.getContext();
        }
    }
    private static void initSharedPreferences(){
        initContext();
        if(sp==null){
            sp=mContext.getSharedPreferences("gochatsp",Context.MODE_PRIVATE);
            editor = sp.edit();
        }
    }
    public interface ExecuteCallback {
        void onExecute(Boolean result);
    }
    public interface MeInfoCallback{
        void onMeInfo(Boolean result, Me me);
    }

    //编号：501；说明：存储个人信息
    public static void insertMeInfo(final String id, final String nickName, final String headPortrait,
                                    final int sex, final String ip, final int status,
                                    final ExecuteCallback callback){
        initSharedPreferences();
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                editor.putString("id",id);
                editor.putString("nickname",nickName);
                editor.putString("headPortrait",headPortrait);
                editor.putInt("sex",sex);
                editor.putString("ip",ip);
                editor.putInt("status",status);
                Boolean result=editor.commit();
                callback.onExecute(result);
            }
        });

    }

    //编号：502；说明：更新个人信息（昵称、头像、性别）
    public static void updateMeInfo(final String nickName, final String headPortrait, final int sex,
                                    final ExecuteCallback callback){
        initSharedPreferences();
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                editor.putString("nickname",nickName);
                editor.putString("headPortrait",headPortrait);
                editor.putInt("sex",sex);
                Boolean result=editor.commit();
                callback.onExecute(result);
            }
        });

    }

    //编号：503；说明：更新个人头像
    public static void updateMeHeadPortrait(final String headPortrait, final ExecuteCallback callback){
        initSharedPreferences();
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                editor.putString("headPortrait",headPortrait);
                Boolean result=editor.commit();
                callback.onExecute(result);
            }
        });
    }

    //编号：504；说明：更新个人昵称
    public static void updateMeNickName(final String nickName, final ExecuteCallback callback){
        initSharedPreferences();
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                editor.putString("nickname",nickName);
                Boolean result=editor.commit();
                callback.onExecute(result);
            }
        });

    }

    //编号：505；说明：更新个人性别
    public static void updateMeSex(final int sex, final ExecuteCallback callback){
        initSharedPreferences();
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                editor.putInt("sex",sex);
                Boolean result=editor.commit();
                callback.onExecute(result);
            }
        });
    }

    //编号：506；说明：更新个人在线（1）或离线（0）状态
    public static void updateMeStatus(final int status, final ExecuteCallback callback){
        initSharedPreferences();
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                editor.putInt("status",status);
                Boolean result=editor.commit();
                callback.onExecute(result);
            }
        });

    }

    //编号：507；说明：更新个人IP地址
    public static void updateMeIP(final String ip, final ExecuteCallback callback){
        initSharedPreferences();
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                editor.putString("ip",ip);
                Boolean result=editor.commit();
                callback.onExecute(result);
            }
        });
    }

    //编号：508；说明：获取个人信息（昵称、头像、性别）
    public static void getMeInfo(final MeInfoCallback callback){
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                Me me=new Me();
                SharedPreferences sp=mContext.getSharedPreferences("gochatsp",Context.MODE_PRIVATE);
                if(sp!=null){
                    me.setNickname(sp.getString("nickname","null"));
                    me.setHeadPortrait(sp.getString("headPortrait","null"));
                    me.setSex(sp.getInt("sex",2));
                    callback.onMeInfo(true,me);
                }else{
                    callback.onMeInfo(false,null);
                }
            }
        });
    }

}
