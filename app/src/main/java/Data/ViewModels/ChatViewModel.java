package Data.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import Data.Models.MessageModel;
import Data.Relations.ChatWithMessages;
import Data.Repositories.CommunityRepository;

public class ChatViewModel extends AndroidViewModel {

    private final CommunityRepository repository;

    public ChatViewModel(@NonNull Application application) {
        super(application);
        this.repository = new CommunityRepository(application);
    }
    public LiveData<List<ChatWithMessages>> getMessages(long chatId){
        return repository.getMessages(chatId);
    }
    public void addMessage(MessageModel message){
        repository.addMessage(message);
    }
}
