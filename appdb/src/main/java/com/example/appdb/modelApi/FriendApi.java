package com.example.appdb.modelApi;

import com.example.appdb.InitDatabase;
import com.example.appdb.model.dao.FriendDao;
import com.example.appdb.model.entity.Friend;
import com.example.appdb.model.entity.User;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FriendApi{
    private static String TAG="FriendApi";
    private static FriendDao friendDao;


    //执行线程池 数据库的访问需要在子线程执行
    private static ExecutorService mThreadPool = Executors.newSingleThreadExecutor();
    //初始化FriendDao
    private static void initFriendDao(){
        if(friendDao == null)
            friendDao = InitDatabase.getAppDB().getFriendDao();
    }
    public interface ExecuteCallback {
        void onExecute(Boolean result);
    }

    public interface FriendInfoCallback {
        void onFriendInfo(Boolean result, Friend f);
    }
    public interface FriendListCallback {
        void onFriendList(Boolean result, List<Friend> friendList);
    }
    public interface FriendIDListCallback {
        void onFriendDeviceIDList(Boolean result, List<String> friendIDList);
    }

    //编号：101；说明：向表中新增一项好友信息,传入字段
    public static void insertFriendToList(final String f_ID, final String f_NickName, final String f_HeadPortrait
            , final int f_Sex, final String f_IP, final int f_Status, final String f_Remark, final int f_DeleteStatus,
                                          final ExecuteCallback callback){
        initFriendDao();
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                Friend f=new Friend();
                f.setFID(f_ID);
                User u=new User();
                u.setUHeadPortrait(f_HeadPortrait);
                u.setUNickName(f_NickName);
                u.setUIP(f_IP);
                u.setUSex(f_Sex);
                u.setUStatus(f_Status);
                u.setURemark(f_Remark);
                f.setFUser(u);
                f.setFDeleteStatus(f_DeleteStatus);
                long result=friendDao.insertFriendToList(f);
                if(result>=0) {
                    callback.onExecute(true);
                }else{
                    callback.onExecute(false);
                }
            }
        });
    }
    //编号：102；说明：从表中删除一项好友
    public static void deleteFriendFromList(final String f_ID,final ExecuteCallback callback){
        initFriendDao();
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                Friend f=friendDao.getFriendInfo(f_ID);
                if(f!=null){
                    int result=friendDao.deleteFriendFromList(f);
                    if(result!=0) {
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
    //编号：103；说明：更新好友信息（昵称、头像、性别）
    public static void updateFriendInfo(final String f_ID, final String f_NickName, final String f_HeadPortrait,
                                        final int f_Sex,final ExecuteCallback callback){
        initFriendDao();
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                Friend f=friendDao.getFriendInfo(f_ID);
                if(f!=null){
                    User u=f.getFUser();
                    u.setUNickName(f_NickName);
                    u.setUHeadPortrait(f_HeadPortrait);
                    u.setUSex(f_Sex);
                    f.setFUser(u);
                    int result=friendDao.updateFriendInfo(f);
                    if(result!=0) {
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
    //编号：104；说明：更新好友备注
    public static void updateFriendRemark(final String f_ID,final String f_Remark,
                                          final ExecuteCallback callback){
        initFriendDao();
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
               Friend f=friendDao.getFriendInfo(f_ID);
               if(f!=null){
                   f.getFUser().setURemark(f_Remark);
                   int result=friendDao.updateFriendInfo(f);
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
    //编号：105；说明：更新好友在线1或离线0状态
    public static void updateFriendStatus(final String f_ID,final int f_Status,
                                          final ExecuteCallback callback){
        initFriendDao();
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                Friend f=friendDao.getFriendInfo(f_ID);
                if(f!=null){
                    f.getFUser().setUStatus(f_Status);
                    int result=friendDao.updateFriendInfo(f);
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
    //编号：106；说明：更新好友IP地址
    public static void updateFriendIP(final String f_ID,final String f_IP,
                                      final ExecuteCallback callback ){
        initFriendDao();
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                Friend f=friendDao.getFriendInfo(f_ID);
                if(f!=null){
                    f.getFUser().setUIP(f_IP);
                    int result=friendDao.updateFriendInfo(f);
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
    //编号：107；说明：更新好友是否被删除状态，被删除1，未被删除0
    public static void updateFriendDeleteStatus(final String f_ID,final int f_DeleteStatus,
                                                final ExecuteCallback callback){
        initFriendDao();
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                Friend f=friendDao.getFriendInfo(f_ID);
                if(f!=null){
                    f.setFDeleteStatus(f_DeleteStatus);
                    int result=friendDao.updateFriendInfo(f);
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
    //编号：108；说明：获得好友信息（返回Friend对象）
    public static void getFriendInfo(final String f_ID, final FriendInfoCallback callback) {
        initFriendDao();
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                Friend f=friendDao.getFriendInfo(f_ID);
                if(f!=null){
                    callback.onFriendInfo(true,f);
                }else{
                    callback.onFriendInfo(false,null);
                }

            }
        });
    }

    //编号：109；说明：获得好友信息表（返回List<Friend>对象）
    public static void getFriendList(final FriendListCallback callback){
        initFriendDao();
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                List<Friend> list=friendDao.getFriendList();
                if(list!=null){
                    callback.onFriendList(true,list);
                }else{
                    callback.onFriendList(false,null);
                }

            }
        });
    }

    //编号：110；说明：获得好友设备ID表（返回List<String>对象）
    public static void getFriendDeviceIDList(final FriendIDListCallback callback){
       initFriendDao();
       mThreadPool.execute(new Runnable() {
           @Override
           public void run() {
               List<String> list=friendDao.getFriendDeviceIDList();
               if(list!=null){
                   callback.onFriendDeviceIDList(true,list);
               }else{
                   callback.onFriendDeviceIDList(false,null);
               }
           }
       });
    }
    //编号：108；说明：获得好友信息（返回Friend对象）
    public static void getFriendInfoByIP(final String f_IP, final FriendInfoCallback callback) {
        initFriendDao();
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                Friend f=friendDao.getFriendInfoByIP(f_IP);
                if(f!=null){
                    callback.onFriendInfo(true,f);
                }else{
                    callback.onFriendInfo(false,null);
                }

            }
        });
    }

}
