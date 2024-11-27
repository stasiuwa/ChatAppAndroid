package DataAccess.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import Data.DTO.MemberDTO;
import Data.Databases.CommunityDB;
import Data.Models.User;
import DataAccess.DAO.UserDAO;
import lombok.Getter;

public class UserRepository {
    private UserDAO dao;
    @Getter
    private LiveData<List<User>> allUsers;

    public UserRepository(Application application){
        CommunityDB communityDB = CommunityDB.getDataBase(application);
        dao = communityDB.userDAO();
        allUsers = dao.getAllUsers();
    }
    public User mapUser(MemberDTO memberDTO){
        return new User(
                memberDTO.getUser().getUserId(),
                memberDTO.getUser().getUsername(),
                memberDTO.getUser().getImageUrl(),
                memberDTO.getUser().getDescription(),
                memberDTO.getRoles()
        );
    }

    public void addUser(User user){
        CommunityDB.databaseWriteExecutor.execute(()->{
            dao.addUser(user);
        });
    }
    public LiveData<User> getUserById(long userId){
        return dao.getUserById(userId);
    }
    public void updateUser(User user){
        CommunityDB.databaseWriteExecutor.execute(()->{
            dao.updateUser(user);
        });
    }
    public void deleteUser(User user){
        CommunityDB.databaseWriteExecutor.execute(()->{
            dao.deleteUser(user);
        });
    }
}
