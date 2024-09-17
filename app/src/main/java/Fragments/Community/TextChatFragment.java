package Fragments.Community;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.szampchat.R;

import Data.Models.ChatModel;
import Data.Models.MessageModel;
import Data.Relations.ChatWithMessages;
import Data.ViewModels.ChatViewModel;

public class TextChatFragment extends Fragment {

    ChatModel textChat;
    ChatViewModel chatViewModel;

    public TextChatFragment(ChatModel chat) {
        this.textChat = chat;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_text_chat, container, false);

        chatViewModel = new ViewModelProvider(this).get(ChatViewModel.class);
        chatViewModel.getMessages(textChat.chatID).observe(getViewLifecycleOwner(), chatModels -> {
            for (ChatWithMessages chatWithMessages1 : chatModels){
//                TODO tutaj wczytac do adaptera liste i przekazac ja do recyclerview albo jakos inaczej to wyswietlic nw xd
//                TODO dopisac MessageAdapter
            }
        });

        TextView test1 = view.findViewById(R.id.textChatName);
        test1.setText(textChat.getChatName());

        return view;
    }
}