package com.example.appdb.modelApi;

import com.example.appdb.InitDatabase;
import com.example.appdb.model.dao.FriendDao;
import com.example.appdb.model.dao.FriendRequestDao;
import com.example.appdb.model.dao.NewFriendDao;
import com.example.appdb.model.dao.TransactionDao;
import com.example.appdb.model.entity.Friend;
import com.example.appdb.model.entity.FriendRequest;
import com.example.appdb.model.entity.NewFriend;
import com.example.appdb.model.entity.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TransactionApi {
    private static String TAG="TransactionApi";
    private static TransactionDao transactionDao;
    private static FriendRequestDao friendRequestDao;
    private static NewFriendDao newFriendDao;
    //执行线程池 数据库的访问需要在子线程执行
    private static ExecutorService mThreadPool = Executors.newSingleThreadExecutor();
    //初始化Dao
    private static void initTransactionDao(){
        if(transactionDao == null)
            transactionDao = InitDatabase.getAppDB().getTransactionDao();
    }
    private static void initFriendRequestDao(){
        if(friendRequestDao == null)
            friendRequestDao = InitDatabase.getAppDB().getFriendRequestDao();
    }
    private static void initNewFriendDao(){
        if(newFriendDao == null)
            newFriendDao = InitDatabase.getAppDB().getNewFriendDao();
    }

    public interface ExecuteCallback {
        void onExecute(Boolean result);
    }
    //编号：601；说明：我同意了对方的好友申请
    public static void iAgreeRequest(final String f_ID,final String f_NickName,final String f_HeadPortrait,
                                     final int f_Sex,final String f_IP,final int f_Status,final int f_DeleteStatus,
                                     final ExecuteCallback callback){
        initTransactionDao();
        initFriendRequestDao();
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                FriendRequest fr=friendRequestDao.getFriendRequestInfo(f_ID);
                if(fr!=null){
                    Friend f=new Friend();
                    f.setFID(f_ID);
                    User u=new User();
                    u.setUHeadPortrait(f_HeadPortrait);
                    u.setUNickName(f_NickName);
                    u.setUIP(f_IP);
                    u.setUSex(f_Sex);
                    u.setUStatus(f_Status);
                    u.setURemark(fr.getFrUser().getURemark());
                    f.setFUser(u);
                    f.setFDeleteStatus(f_DeleteStatus);
                    try {
                        transactionDao.iAgreeRequest(f,fr);
                        callback.onExecute(true);
                    }catch (Exception e){
                        e.printStackTrace();
                        callback.onExecute(false);
                    }
                }else{
                    callback.onExecute(false);
                }
            }
        });
    }

    //编号：602；说明：新朋友同意了我的好友申请
    public static void newFriendAcceptMe(final String f_ID,final String f_NickName,final String f_HeadPortrait,
                                         final int f_Sex,final String f_IP,final int f_Status,final int f_DeleteStatus,
                                         final ExecuteCallback callback){
        initTransactionDao();
        initNewFriendDao();
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                NewFriend nf=newFriendDao.getNewFriendInfo(f_ID);
                if(nf!=null){
                    Friend f=new Friend();
                    f.setFID(f_ID);
                    User u=new User();
                    u.setUHeadPortrait(f_HeadPortrait);
                    u.setUNickName(f_NickName);
                    u.setUIP(f_IP);
                    u.setUSex(f_Sex);
                    u.setUStatus(f_Status);
                    u.setURemark(nf.getNfUser().getURemark());
                    f.setFUser(u);
                    f.setFDeleteStatus(f_DeleteStatus);
                    try{
                        transactionDao.newFriendAcceptMe(f,nf);
                        callback.onExecute(true);
                    }catch (Exception e){
                        callback.onExecute(false);
                    }
                }else{
                    callback.onExecute(false);
                }
            }
        });
    }

    //编号603 说明：A之前删除了我，后来我又加A，现在A同意了我的请求
    public static void friendDeletingMeAcceptMe(final String f_ID,final String f_NickName,final String f_HeadPortrait,
                                                final int f_Sex,final String f_IP,final int f_Status,
                                                final ExecuteCallback callback){

        initTransactionDao();
        initNewFriendDao();
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                NewFriend nf=newFriendDao.getNewFriendInfo(f_ID);
                if(nf!=null){
                    Friend f=new Friend();
                    f.setFID(f_ID);
                    User u=new User();
                    u.setUHeadPortrait(f_HeadPortrait);
                    u.setUNickName(f_NickName);
                    u.setUIP(f_IP);
                    u.setUSex(f_Sex);
                    u.setUStatus(f_Status);
                    u.setURemark(nf.getNfUser().getURemark());
                    f.setFUser(u);
                    f.setFDeleteStatus(0);
                    try{
                        transactionDao.friendDeletingMeAcceptMe(f,nf);
                        callback.onExecute(true);
                    }catch (Exception e){
                        callback.onExecute(false);
                    }
                }else{
                    callback.onExecute(false);
                }
            }
        });
    }
}
