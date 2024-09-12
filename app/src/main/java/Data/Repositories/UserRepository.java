package Data.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import Data.DAO.UserDAO;
import Data.Databases.UserDB;
import Data.Models.CommunityModel;
import Data.Models.UserModel;

public class UserRepository {
    final private UserDAO mUserDAO;
    final private LiveData<List<UserModel>> allUsers;

    public UserRepository(Application application){
        UserDB mUserDB = UserDB.getDataBase(application);
        mUserDAO = mUserDB.userDAO();
        allUsers = mUserDAO.getAllUsers();
    }
    public void addUser(UserModel user) {
        UserDB.databaseWriteExecutor.execute(() -> {
            mUserDAO.addUser(user);
        });
    }
    public boolean readLoginData(String username, String password) {
        return mUserDAO.readLoginData(username, password);
    }
    public LiveData<List<UserModel>> getAllUsers() {
        return allUsers;
    }
    public void deleteUser(UserModel user) {
        UserDB.databaseWriteExecutor.execute(() -> {
        });
    }

}
