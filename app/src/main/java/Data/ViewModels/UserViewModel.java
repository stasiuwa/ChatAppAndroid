package Data.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import Data.Models.UserModel;
import Data.Repositories.UserRepository;

public class UserViewModel extends AndroidViewModel {
    private final UserRepository userRepository;
    private final LiveData<List<UserModel>> allUsers;

    public UserViewModel(@NonNull Application application) {
        super(application);
        this.userRepository = new UserRepository(application);
        this.allUsers = userRepository.getAllUsers();
    }
    public void addUser(UserModel user) {
        userRepository.addUser(user);
    }
    public LiveData<List<UserModel>> getAllUsers() {
        return allUsers;
    }
    public boolean readLoginData(String username, String password) {
        return userRepository.readLoginData(username,password);
    }
}
