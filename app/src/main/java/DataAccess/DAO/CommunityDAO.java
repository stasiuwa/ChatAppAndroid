package DataAccess.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import Data.DTO.CommunityDTO;
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
    @Insert
    void addCommunity(CommunityModel community);

    @Update
    void updateCommunity(CommunityModel community);

    @Transaction
    @Query("SELECT * FROM communities")
    LiveData<List<CommunityModel>> getAllCommunities();

    @Transaction
    @Query("SELECT * FROM communities WHERE communityID = :id")
    LiveData<CommunityModel> getCommunityById(long id);

//    @Query("SELECT COUNT() FROM communities WHERE communityID = :id")
//    int communityExists(long id);

    @Delete
    void deleteCommunity(CommunityModel community);
}
