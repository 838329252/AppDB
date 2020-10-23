package com.example.appdb.model.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.appdb.model.dao.FriendDao;
import com.example.appdb.model.dao.FriendRequestDao;
import com.example.appdb.model.dao.MessageDao;
import com.example.appdb.model.dao.NewFriendDao;
import com.example.appdb.model.dao.TransactionDao;
import com.example.appdb.model.entity.Friend;
import com.example.appdb.model.entity.FriendRequest;
import com.example.appdb.model.entity.Message;
import com.example.appdb.model.entity.NewFriend;
import com.example.appdb.model.entity.User;

@Database(version = 1,entities = {User.class, Friend.class, NewFriend.class,
                                  FriendRequest.class, Message.class},exportSchema = true)
public abstract class AppDatabase extends RoomDatabase {
    public abstract FriendDao getFriendDao();
    public abstract FriendRequestDao getFriendRequestDao();
    public abstract NewFriendDao getNewFriendDao();
    public abstract MessageDao getMessageDao();
    public abstract TransactionDao getTransactionDao();
}
