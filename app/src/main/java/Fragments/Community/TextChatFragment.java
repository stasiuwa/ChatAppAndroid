package Fragments.Community;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import Data.ViewModels.TextChatViewModel;

public class TextChatFragment extends Fragment{

    TextChatViewModel messagesViewModel;
    MessageAdapter messageAdapter;
    String chatName;
    long chatID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_text_chat, container, false);
//        Receive chat data from bundle
        Bundle receivedBundle = getArguments();
        if (receivedBundle != null){
            if (receivedBundle.containsKey("chatID")){
                chatID = receivedBundle.getLong("chatID");
            }
            if (receivedBundle.containsKey("chatName")){
                chatName = receivedBundle.getString("chatName");
            } else {
                chatName = "Chat name not found!";
            }
        }

//        Displaying messages
        messagesViewModel = new ViewModelProvider(this).get(TextChatViewModel.class);
        messageAdapter = new MessageAdapter(requireActivity());

        messagesViewModel.getMessages(chatID).observe(getViewLifecycleOwner(), chatModels -> {
            for (ChatWithMessages chatWithMessages1 : chatModels){
                messageAdapter.setMessagesList(new ArrayList<>(chatWithMessages1.messages));
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.messagesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(messageAdapter);

//        Sending messagess
        EditText messageText = view.findViewById(R.id.inputText);
        Button sendMessageButton = view.findViewById(R.id.messageSendButton);
        sendMessageButton.setOnClickListener(v -> {
            messagesViewModel.addMessage(
                    new MessageModel(
                            getArguments().getLong("chatID"),
                            messageText.getText().toString(),
                            String.valueOf(Date.from(Instant.now())),
//                            TODO poprawic logike do pobrania nazwy uzytkownika zalogowanego do aplikacji
                            "TEST"
                    ));
            messageAdapter.notifyDataSetChanged();
        });

//        Chat name in top side of the view
        TextView test1 = view.findViewById(R.id.textChatName);
        test1.setText(getArguments().getString("chatName"));

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        requireActivity().findViewById(R.id.communitySettingsButton).setVisibility(View.VISIBLE);
    }
}