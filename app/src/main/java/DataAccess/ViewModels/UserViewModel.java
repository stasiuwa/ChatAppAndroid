package DataAccess.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import DataAccess.Repositories.CommunityRepository;
import Data.Models.UserModel;

public class UserViewModel extends AndroidViewModel {
    private final CommunityRepository repository;
    private final LiveData<List<UserModel>> allUsers;

    public UserViewModel(@NonNull Application application) {
        super(application);
        this.repository = new CommunityRepository(application);
        this.allUsers = repository.getAllUsers();
    }
    public void addUser(UserModel user) {
        repository.addUser(user);
    }
    public LiveData<List<UserModel>> getAllUsers() {
        return allUsers;
    }
    public boolean readLoginData(String username) {
        return repository.readLoginData(username);
    }
}
