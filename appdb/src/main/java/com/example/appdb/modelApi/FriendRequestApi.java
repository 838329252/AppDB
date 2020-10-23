package com.example.appdb.modelApi;


import com.example.appdb.InitDatabase;
import com.example.appdb.model.dao.FriendRequestDao;
import com.example.appdb.model.entity.FriendRequest;
import com.example.appdb.model.entity.User;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FriendRequestApi {
    private static String TAG="FriendRequestApi";
    private static FriendRequestDao friendRequestDao;

    //执行线程池 数据库的访问需要在子线程执行
    private static ExecutorService mThreadPool = Executors.newSingleThreadExecutor();
    //初始化FriendRequestDao
    private static void initFriendRequestDao(){
        if(friendRequestDao == null)
            friendRequestDao = InitDatabase.getAppDB().getFriendRequestDao();
    }

    public interface ExecuteCallback {
        void onExecute(Boolean result);
    }
    public interface FriendRequestInfoCallback {
        void onFriendRequestInfo(Boolean result, FriendRequest fr);
    }
    public interface FriendRequestListCallback {
        void onFriendRequestList(Boolean result, List<FriendRequest> friendRequestList);
    }
    public interface FriendRequestNumCallback{
        void onFriendRequestNum(Boolean result, int requestNum);
    }

    //编号：201；说明：向表中新增一项好友申请信息
    public static void insertFriendRequestToList(final String fr_ID,final String fr_NickName,final String fr_HeadPortrait
            ,final int fr_Sex,final String fr_IP,final int fr_Status,final String fr_Remark,final String fr_RequestContent
    ,final ExecuteCallback callback){
        initFriendRequestDao();
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                FriendRequest fr=new FriendRequest();
                fr.setFrID(fr_ID);
                User u=new User();
                u.setUHeadPortrait(fr_HeadPortrait);
                u.setUNickName(fr_NickName);
                u.setUIP(fr_IP);
                u.setUSex(fr_Sex);
                u.setUStatus(fr_Status);
                u.setURemark(fr_Remark);
                fr.setFrUser(u);
                fr.setFrRequestContent(fr_RequestContent);
                long result=friendRequestDao.insertFriendRequestToList(fr);
                if(result>=0){
                    callback.onExecute(true);
                }else{
                    callback.onExecute(false);
                }
            }
        });
    }

    //编号：202；说明：从表中删除一项好友申请信息
    public static void deleteFriendRequestFromList(final String fr_ID,final ExecuteCallback callback){
        initFriendRequestDao();
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                FriendRequest fr=friendRequestDao.getFriendRequestInfo(fr_ID);
                if(fr!=null){
                    int result=friendRequestDao.deleteFriendRequestFromList(fr);
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

    //编号：203；说明：更新好友申请信息（昵称、头像、性别、申请内容）
    public static void updateFriendRequestInfo(final String fr_ID, final String fr_NickName,
                                               final String fr_HeadPortrait, final int fr_Sex,
                                               final String fr_RequestContent,
                                               final ExecuteCallback callback){
        initFriendRequestDao();
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                FriendRequest fr=friendRequestDao.getFriendRequestInfo(fr_ID);
                if(fr!=null){
                    fr.setFrRequestContent(fr_RequestContent);
                    User u=fr.getFrUser();
                    u.setUNickName(fr_NickName);
                    u.setUHeadPortrait(fr_HeadPortrait);
                    u.setUSex(fr_Sex);
                    fr.setFrUser(u);
                    int result=friendRequestDao.updateFriendRequestInfo(fr);
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

    //编号：204；说明：更新好友申请对象的备注
    public static void updateFriendRequestRemark(final String fr_ID, final String fr_Remark,
                                                 final ExecuteCallback callback){
        initFriendRequestDao();
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                FriendRequest fr=friendRequestDao.getFriendRequestInfo(fr_ID);
                if(fr!=null){
                    fr.getFrUser().setURemark(fr_Remark);
                    int result=friendRequestDao.updateFriendRequestInfo(fr);
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

    //编号：205；说明：更新好友申请内容
    public static void updateFriendRequestContent(final String fr_ID, final String fr_RequestContent,
                                                  final ExecuteCallback callback){
        initFriendRequestDao();
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                FriendRequest fr=friendRequestDao.getFriendRequestInfo(fr_ID);
                if(fr!=null){
                    fr.setFrRequestContent(fr_RequestContent);
                    int result=friendRequestDao.updateFriendRequestInfo(fr);
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

    //编号：206；说明：获得好友申请表（返回List<FriendRequest>对象）
    public static void getFriendRequestList(final FriendRequestListCallback callback){
        initFriendRequestDao();
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                List<FriendRequest> list=friendRequestDao.getFriendRequestList();
                if(list!=null){
                    callback.onFriendRequestList(true,list);
                }else{
                    callback.onFriendRequestList(false,null);
                }
            }
        });

    }

    //编号：207；说明：获得好友申请信息（返回FriendRequest对象）
    public static void getFriendRequestInfo(final String fr_ID, final FriendRequestInfoCallback callback){
        initFriendRequestDao();
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                FriendRequest fr=friendRequestDao.getFriendRequestInfo(fr_ID);
                if(fr!=null){
                    callback.onFriendRequestInfo(true,fr);
                }else{
                    callback.onFriendRequestInfo(false,null);
                }

            }
        });
    }

    //编号：208；说明：获得好友申请数量
    public static void getFriendRequestNum(final FriendRequestNumCallback callback){
        initFriendRequestDao();
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                int num=friendRequestDao.getFriendRequestNum();
                if(num>=0){
                    callback.onFriendRequestNum(true,num);
                }else{
                    callback.onFriendRequestNum(false,-1);
                }
            }
        });
    }
}
