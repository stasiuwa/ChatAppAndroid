package Fragments.Community;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.szampchat.R;

import Adapters.MessageAdapter;

public class TextChatFragment extends Fragment{

    MessageAdapter messageAdapter;
    String chatName;
    long chatID;

    FragmentContainerView fragmentContainerView;
    ConstraintLayout.LayoutParams layoutParams;

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
            if (receivedBundle.containsKey("channelID")){
                chatID = receivedBundle.getLong("channelID");
            }
            if (receivedBundle.containsKey("channelName")){
                chatName = receivedBundle.getString("channelName");
            } else {
                chatName = "Chat name not found!";
            }
        }
        TextView chatName = view.findViewById(R.id.textChatName);
        chatName.setText(getArguments().getString("channelName"));

//        Displaying messages
        messageAdapter = new MessageAdapter(requireActivity());

//        messagesViewModel.getMessages(chatID).observe(getViewLifecycleOwner(), chatModels -> {
//            for (ChatWithMessages chatWithMessages1 : chatModels){
//                messageAdapter.setMessagesList(new ArrayList<>(chatWithMessages1.messages));
//            }
//        });

        RecyclerView recyclerView = view.findViewById(R.id.messagesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(messageAdapter);

//        Sending messagess
        EditText messageText = view.findViewById(R.id.inputText);
        Button sendMessageButton = view.findViewById(R.id.messageSendButton);
//        sendMessageButton.setOnClickListener(v -> {
//            messagesViewModel.addMessage(
//                    new MessageModel(
//                            getArguments().getLong("chatID"),
//                            messageText.getText().toString(),
//                            String.valueOf(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))),
////                            TODO poprawic logike do pobrania nazwy uzytkownika zalogowanego do aplikacji
//                            "user2"
//                    ));
//            messageAdapter.notifyDataSetChanged();
////            Clear messageText value and focus
//            messageText.getText().clear();
//            messageText.clearFocus();
////            Hide system keyboard
//            InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
////            TODO nie działa, poprawic
//            ScrollView scrollView = view.findViewById(R.id.messagesScrollView);
//            scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));
//        });

//        Chat name in top side of the view


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        requireActivity().findViewById(R.id.communitySettingsButton).setVisibility(View.VISIBLE);
//        requireActivity().findViewById(R.id.bottom_navbar).setVisibility(View.GONE);

    }

    @Override
    public void onStop() {
        super.onStop();
//        requireActivity().findViewById(R.id.bottom_navbar).setVisibility(View.VISIBLE);
    }
}