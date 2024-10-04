package Data.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import Data.Models.ChannelModel;
import Data.Models.ChatModel;
import Data.Models.CommunityModel;
import Data.Models.MessageModel;
import Data.Models.RoleModel;
import Data.Models.UserModel;
import Data.Relations.ChatWithMessages;
import Data.Relations.CommunityWithChats;
import Data.Relations.CommunityWithChannels;

@Dao
public interface CommunityDAO {
//    Community
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void addCommunity(CommunityModel community);
    @Transaction
    @Query("SELECT * FROM communities")
    LiveData<List<CommunityModel>> getAllCommunities();

    @Transaction
    @Query("SELECT * FROM communities WHERE communityID = :id")
    LiveData<CommunityModel> getCommunity(long id);

    @Delete
    void deleteCommunity(CommunityModel community);

//    Chats
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void addChat(ChatModel chat);
    @Delete
    void deleteChat(ChatModel chat);

//    Messages
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addMessage(MessageModel message);
    @Update(entity = MessageModel.class)
    void editMessage(MessageModel message);

//    Channels
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void addChannel(ChannelModel channel);
    @Delete
    void deleteChannel(ChannelModel channel);

//    User
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void addUser(UserModel user);
    @Query("SELECT * FROM users")
    LiveData<List<UserModel>> getAllUsers();
    @Query("SELECT * FROM users WHERE Username LIKE :username")
    boolean readLoginData(String username);

//    Role
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void addRole(RoleModel role);
    @Query("SELECT * FROM roles WHERE roleCommunityID LIKE :communityId")
    LiveData<List<RoleModel>> getAllRoles(long communityId);

//    Relations
    @Transaction
    @Query("SELECT * FROM communities")
    LiveData<List<CommunityWithChats>> getAllChats();

    @Transaction
    @Query("SELECT * FROM communities")
    LiveData<List<CommunityWithChannels>> getAllChannels();


    @Transaction
    @Query("SELECT * FROM communities WHERE communityID = :communityId")
    LiveData<List<CommunityWithChats>> getChats(long communityId);

    @Transaction
    @Query("SELECT * FROM communities WHERE communityID = :communityId")
    LiveData<List<CommunityWithChannels>> getChannels(long communityId);

    @Transaction
    @Query("SELECT * FROM chats WHERE chatID = :chatId")
    LiveData<List<ChatWithMessages>> getMessages(long chatId);

    @Transaction
    @Query("SELECT * FROM roles WHERE roleUserID = :roleUserId")
    LiveData<RoleModel> getRole(long roleUserId);

}
