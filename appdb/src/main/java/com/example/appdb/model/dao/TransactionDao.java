package com.example.appdb.model.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.appdb.model.entity.Friend;
import com.example.appdb.model.entity.FriendRequest;
import com.example.appdb.model.entity.NewFriend;

@Dao
public abstract class TransactionDao {
    @Insert
    public abstract void insertFriendToList(Friend friend);
    @Delete
    public abstract void deleteFriendRequestFromList(FriendRequest friendRequest);
    @Delete
    public abstract void deleteNewFriendFromList(NewFriend newFriend);
    @Update
    public abstract void updateFriendInfo(Friend friend);
    @Transaction
    public void iAgreeRequest(Friend friend, FriendRequest friendRequest){
        insertFriendToList(friend);
        deleteFriendRequestFromList(friendRequest);
    }
    @Transaction
    public void newFriendAcceptMe(Friend friend,NewFriend newFriend){
        insertFriendToList(friend);
        deleteNewFriendFromList(newFriend);
    }
    @Transaction
    public void friendDeletingMeAcceptMe(Friend friend,NewFriend newFriend){
        updateFriendInfo(friend);
        deleteNewFriendFromList(newFriend);
    }
}
