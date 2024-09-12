package Data.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import Data.Models.ChatModel;

@Dao
public interface ChatDAO {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void addChat(ChatModel chat);
    @Query("SELECT * FROM chats")
    LiveData<List<ChatModel>> getAllChats();
    @Delete
    void deleteChat(ChatModel chat);
//
}
