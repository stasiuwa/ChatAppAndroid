package Data.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import Data.Models.UserModel;

@Dao
public interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void addUser(UserModel user);
    @Query("SELECT * FROM users")
    LiveData<List<UserModel>> getAllUsers();
    @Query("SELECT * FROM users WHERE Username LIKE :username AND Password LIKE :password")
    boolean readLoginData(String username, String password);
}
