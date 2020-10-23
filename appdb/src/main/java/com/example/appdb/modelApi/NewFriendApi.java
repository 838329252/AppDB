package com.example.appdb.modelApi;


import com.example.appdb.InitDatabase;
import com.example.appdb.model.dao.NewFriendDao;
import com.example.appdb.model.entity.NewFriend;
import com.example.appdb.model.entity.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NewFriendApi {
    private static String TAG="NewFriendApi";
    private static NewFriendDao newFriendDao;

    //执行线程池 数据库的访问需要在子线程执行
    private static ExecutorService mThreadPool = Executors.newSingleThreadExecutor();
    //初始化NewFriendDao
    private static void initNewFriendDao(){
        if(newFriendDao == null)
            newFriendDao = InitDatabase.getAppDB().getNewFriendDao();
    }

    public interface ExecuteCallback {
        void onExecute(Boolean result);
    }

    public interface NewFriendInfoCallback {
        void onNewFriendInfo(Boolean result, NewFriend newFriend);
    }

    //编号：301；说明：向表中新增一项新朋友信息
    public static void insertNewFriendToList(final String nf_ID,final String nf_NickName,final String nf_HeadPortrait
            ,final int nf_Sex,final String nf_IP,final int nf_Status,final String nf_Remark
            ,final ExecuteCallback callback){
        initNewFriendDao();
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                NewFriend nf=new NewFriend();
                nf.setNfID(nf_ID);
                User u=new User();
                u.setUHeadPortrait(nf_HeadPortrait);
                u.setUNickName(nf_NickName);
                u.setUIP(nf_IP);
                u.setUSex(nf_Sex);
                u.setUStatus(nf_Status);
                u.setURemark(nf_Remark);
                nf.setNfUser(u);
                long result=newFriendDao.insertNewFriendToList(nf);
                if(result>=0){
                    callback.onExecute(true);
                }else{
                    callback.onExecute(false);
                }

            }
        });
    }
    //编号：302；说明：从表中删除一项新朋友信息
    public static void deleteNewFriendFromList(final String nf_ID,final ExecuteCallback callback){
        initNewFriendDao();
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                NewFriend newFriend=newFriendDao.getNewFriendInfo(nf_ID);
                if(newFriend!=null){
                    int result=newFriendDao.deleteNewFriendFromList(newFriend);
                    if(result!=0){
                        callback.onExecute(true);
                    }else{
                        callback.onExecute(false);
                    }
                }else{
                    callback.onExecute(false);
                }

            }
        });
    }
    //编号：303；说明：更新新添加的朋友的备注
    public static void updateNewFriendRemark(final String nf_ID,final String nf_Remark,final ExecuteCallback callback){
        initNewFriendDao();
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                NewFriend nf=newFriendDao.getNewFriendInfo(nf_ID);
                if(nf!=null){
                    nf.getNfUser().setURemark(nf_Remark);
                    int result=newFriendDao.updateNewFriendInfo(nf);
                    if(result!=0){
                        callback.onExecute(true);
                    }else{
                        callback.onExecute(false);
                    }
                }else{
                    callback.onExecute(false);
                }

            }
        });
    }
    //编号：304；说明：更新新添加的朋友的信息（昵称、头像、性别）
    public static void updateNewFriendInfo(final String nf_ID,final String nf_NickName,
                                           final String nf_HeadPortrait, final int nf_Sex,
                                           final ExecuteCallback callback){
        initNewFriendDao();
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                NewFriend nf=newFriendDao.getNewFriendInfo(nf_ID);
                if(nf!=null){
                    nf.getNfUser().setUNickName(nf_NickName);
                    nf.getNfUser().setUHeadPortrait(nf_HeadPortrait);
                    nf.getNfUser().setUSex(nf_Sex);
                    int result=newFriendDao.updateNewFriendInfo(nf);
                    if(result!=0){
                        callback.onExecute(true);
                    }else{
                        callback.onExecute(false);
                    }
                }else{
                    callback.onExecute(false);
                }

            }
        });
    }
    //编号：305；说明：获得新添加的朋友的信息（返回NewFriend对象）
    public static void getNewFriendInfo(final String nf_ID, final NewFriendInfoCallback callback ){
        initNewFriendDao();
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                NewFriend nf=newFriendDao.getNewFriendInfo(nf_ID);
                if(nf!=null){
                    callback.onNewFriendInfo(true,nf);
                }else{
                    callback.onNewFriendInfo(false,null);
                }
            }
        });
    }


}
