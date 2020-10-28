package com.example.appdb.modelApi;

import com.example.appdb.InitDatabase;
import com.example.appdb.model.dao.FriendDao;
import com.example.appdb.model.dao.MessageDao;
import com.example.appdb.model.entity.Message;
import com.example.appdb.model.entity.User;
import com.example.appdb.model.pojo.ChatListInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MessageApi {
    private static String TAG="MessageApi";
    private static MessageDao messageDao;
    private static FriendDao friendDao;

    //执行线程池 数据库的访问需要在子线程执行
    private static ExecutorService mThreadPool = Executors.newSingleThreadExecutor();
    //初始化MessageDao
    private static void initMessageDao(){
        if(messageDao == null)
            messageDao = InitDatabase.getAppDB().getMessageDao();
    }
    //初始化FriendDao
    private static void initFriendDao(){
        if(friendDao == null)
            friendDao = InitDatabase.getAppDB().getFriendDao();
    }

    public interface ExecuteCallback {
        void onExecute(Boolean result);
    }
    public interface FriendMessageListCallback{
        void onFriendMessageList(Boolean result, List<Message> messageList);
    }
    public interface LatestMessageInfoCallback{
        void onFriendLatestMessageInfo(Boolean result, Message message);
    }
    public interface UnreadMessageNumCallback{
        void onUnreadMessageNum(Boolean result, int num);
    }
    public interface ChatListCallback{
        void onChatList(Boolean result, List<ChatListInfo> chatList);
    }


    //编号：401；说明：向表中新增一项文本聊天内容（用字段传入）
    public static void insertOneTextMessageToList(final String m_FriendID,final int m_SendOrReceive,
        final long m_Time, final int m_MessageType, final String m_Text, final int m_ReadStatus,
        final int m_SendStatus,final ExecuteCallback callback){
        initMessageDao();
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                Message m=new Message();
                m.setMFriendID(m_FriendID);
                m.setMSendOrReceive(m_SendOrReceive);
                m.setMTime(m_Time);
                m.setMMessageType(m_MessageType);
                m.setMText(m_Text);
                m.setMReadStatus(m_ReadStatus);
                m.setMSendStatus(m_SendStatus);
                long result=messageDao.insertOneMessageToList(m);
                if(result>=0){
                    callback.onExecute(true);
                }else{
                    callback.onExecute(false);
                }

            }
        });
    }

    //编号：402；说明：向表中新增一项图片聊天内容（用字段传入）
    public static void insertOnePictureMessageToList(final String m_FriendID,final int m_SendOrReceive,
        final long m_Time, final int m_MessageType, final String m_Picture,final String m_PictureThumbnail,
        final int m_ReadStatus, final int m_SendStatus,final ExecuteCallback callback){
        initMessageDao();
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                Message m=new Message();
                m.setMFriendID(m_FriendID);
                m.setMSendOrReceive(m_SendOrReceive);
                m.setMTime(m_Time);
                m.setMMessageType(m_MessageType);
                m.setMPicture(m_Picture);
                m.setMPictureThumbnail(m_PictureThumbnail);
                m.setMReadStatus(m_ReadStatus);
                m.setMSendStatus(m_SendStatus);
                long result=messageDao.insertOneMessageToList(m);
                if(result>=0){
                    callback.onExecute(true);
                }else{
                    callback.onExecute(false);
                }

            }
        });
    }
    //编号：403；说明：向表中新增一项聊天内容（用Message对象传入）
    public static void insertOneMessageToList(final Message message,final ExecuteCallback callback){
        initMessageDao();
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                long result=messageDao.insertOneMessageToList(message);
                if(result>=0){
                    callback.onExecute(true);
                }else{
                    callback.onExecute(false);
                }
            }
        });
    }

    //编号：404；说明：从表中删除一项聊天内容
    public static void deleteOneMessageFromList(final int m_ID,final ExecuteCallback callback){
        initMessageDao();
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                Message m=messageDao.getMessageInfo(m_ID);
                if(m!=null){
                    int result=messageDao.deleteOneMessageFromList(m);
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

    //编号：405；说明：删除与某个聊天对象的全部聊天信息
    public static void deleteMessagesFromList(final String m_FriendID,final ExecuteCallback callback){
        initMessageDao();
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                List<Message> messages=messageDao.getFriendMessages(m_FriendID);
                if(messages!=null){
                    int result=messageDao.deleteMessagesFromList(messages);
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

    //编号：406；说明：将与某个聊天对象产生的未读消息变更为已读状态
    public static void updateReadStatus(final String m_FriendID,final ExecuteCallback callback){
        initMessageDao();
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                List<Message> messages=messageDao.getUnreadMessageList(m_FriendID);
                List<Message> messages1=new ArrayList<>();
                if(messages!=null){
                    for(Message m:messages){
                        m.setMReadStatus(1);
                        messages1.add(m);
                    }
                    int result=messageDao.updateMessages(messages1);
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
    //编号412;说明：基于消息发送时间找到该消息后，更新其发送成功或失败标志位
    public static void updateSendStatus(final long m_Time,final int m_SendStatus,final ExecuteCallback callback){
        initMessageDao();
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                Message m=messageDao.getMessageInfoByTime(m_Time);
                if(m!=null){
                    m.setMSendStatus(m_SendStatus);
                    int result=messageDao.updateMessageInfo(m);
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

    //编号：407；说明：获得聊天信息表（返回List<ChatListInfo>对象，即用于聊天列表页的信息）
    public static void getChatList(final ChatListCallback callback){
        initMessageDao();
        initFriendDao();
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                List<Message> messages=messageDao.getChatList();
                List<ChatListInfo> list=new ArrayList<>();
                if(messages!=null){
                    for(Message m:messages){
                        User u=friendDao.getFriendInfo(m.getMFriendID()).getFUser();
                        ChatListInfo chatListInfo=new ChatListInfo();
                        chatListInfo.setUser(u);
                        chatListInfo.setMessage(m);
                        list.add(chatListInfo);
                    }
                    callback.onChatList(true,list);
                }else{
                    callback.onChatList(false,null);
                }
            }
        });
    }

    //编号：408；说明：获得与某个聊天对象的num条聊天信息，可基于fromNum的值设定获取[fromNum+1,fromNum+num]区间的消息。
    // 例如：fromNum=0，num=50,得到1-50条消息；fromNum=50,num=50,得到51-100条消息，以此类推
    public static void getFriendMessageList(final String m_FriendID, final int num,
                                            final int fromNum, final FriendMessageListCallback callback){
        initMessageDao();
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                List<Message> messages=messageDao.getFriendMessageList(m_FriendID,num,fromNum);
                if(messages!=null){
                    callback.onFriendMessageList(true,messages);
                }else{
                    callback.onFriendMessageList(false,null);
                }
            }
        });
    }

    //编号：409；说明：获得与某个聊天对象的最近一条消息信息
    public static void getFriendLatestMessageInfo(final String m_FriendID,final LatestMessageInfoCallback callback){
        initMessageDao();
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                Message m=messageDao.getFriendLatestMessageInfo(m_FriendID);
                if(m!=null){
                    callback.onFriendLatestMessageInfo(true,m);
                }else {
                    callback.onFriendLatestMessageInfo(false,null);
                }
            }
        });
    }

    //编号：410；说明：获取与某个聊天对象的未读消息数
    public static void getUnreadMessageNum(final String m_FriendID,final UnreadMessageNumCallback callback){
        initMessageDao();
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                int unreadNum=messageDao.getUnreadMessageNum(m_FriendID);
                if(unreadNum>=0){
                    callback.onUnreadMessageNum(true,unreadNum);
                }else{
                    callback.onUnreadMessageNum(false,-1);
                }

            }
        });
    }

    //编号：411；说明：获得总未读消息数
    public static void getTotalUnreadMessageNum(final UnreadMessageNumCallback callback){
        initMessageDao();
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                int totalUnreadNum=messageDao.getTotalUnreadMessageNum();
                if(totalUnreadNum>=0){
                    callback.onUnreadMessageNum(true,totalUnreadNum);
                }else{
                    callback.onUnreadMessageNum(false,-1);
                }

            }
        });
    }
}
