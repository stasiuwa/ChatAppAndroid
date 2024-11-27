package DataAccess.ViewModels;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import Data.Models.User;
import DataAccess.Repositories.UserRepository;
import lombok.Getter;

public class UserViewModel extends AndroidViewModel {
    private final UserRepository userRepository;
    @Getter
    private final LiveData<List<User>> allUsers;

    public UserViewModel(Application application) {
        super(application);
        this.userRepository = new UserRepository(application);
        this.allUsers = userRepository.getAllUsers();
    }
    public void addUser(User user) {
        userRepository.addUser(user);
    }
    public LiveData<User> getUserById(long userId){
        return userRepository.getUserById(userId);
    }
    public void updateUser(User user){
        userRepository.updateUser(user);
    }
    public void deleteUser(User user){
        userRepository.deleteUser(user);
    }
}
