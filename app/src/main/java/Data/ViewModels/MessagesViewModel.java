package Data.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import Data.Models.MessageModel;
import Data.Relations.ChatWithMessages;
import Data.Repositories.CommunityRepository;

public class MessagesViewModel extends AndroidViewModel {

    private final CommunityRepository repository;

    public MessagesViewModel(@NonNull Application application) {
        super(application);
        this.repository = new CommunityRepository(application);
    }

    public void addMessage(MessageModel message){
        repository.addMessage(message);
    }

    public LiveData<List<ChatWithMessages>> getMessages(long chatId){
        return repository.getMessages(chatId);
    }
}
