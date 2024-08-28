package Data.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface ChatDAO {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void addChat();
//    @Query("SELECT * FROM chats")
//    LiveData<List<>> getAllChats;
//
}
