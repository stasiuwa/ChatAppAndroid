package Fragments.Community;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.szampchat.R;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

import Adapters.MessageAdapter;
import Data.Models.ChatModel;
import Data.Models.MessageModel;
import Data.Relations.ChatWithMessages;
import Data.ViewModels.ChatViewModel;

public class TextChatFragment extends Fragment {

    ChatModel textChat;
    ChatViewModel chatViewModel;
    MessageAdapter adapter;

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

//        Displaying messages
        adapter = new MessageAdapter(getActivity());
        chatViewModel = new ViewModelProvider(this).get(ChatViewModel.class);
        chatViewModel.getMessages(textChat.chatID).observe(getViewLifecycleOwner(), chatModels -> {
            for (ChatWithMessages chatWithMessages1 : chatModels){
                adapter.setMessagesList(new ArrayList<>(chatWithMessages1.messages));
            }
        });
        RecyclerView recyclerView = view.findViewById(R.id.chatsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

//        Sending messages
        EditText messageText = view.findViewById(R.id.messageInput);
        Button sendMessageButton = view.findViewById(R.id.messageSendButton);
        sendMessageButton.setOnClickListener(v -> {
            chatViewModel.addMessage(
                    new MessageModel(
                            textChat.getChatID(),
                            messageText.getText().toString(),
                            String.valueOf(Date.from(Instant.now())),
                            "userTEST"
                    ));
        });

//        Chat name in top side of the view
        TextView test1 = view.findViewById(R.id.textChatName);
        test1.setText(textChat.getChatName());

        return view;
    }
}