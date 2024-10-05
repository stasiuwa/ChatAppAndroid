package Fragments.Community;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.szampchat.R;

import java.util.ArrayList;
import java.util.List;

import Adapters.ChatAdapter;
import Adapters.MessageAdapter;
import Data.Models.ChatModel;
import Data.Relations.CommunityWithChannels;
import Data.Relations.CommunityWithChats;
import Data.ViewModels.CommunityViewModel;

public class ChatsFragment extends Fragment{

    CommunityViewModel communityViewModel;
    ChatAdapter chatAdapter;
    long communityID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        TransitionInflater inflater = TransitionInflater.from(requireContext());
//        setExitTransition(inflater.inflateTransition(R.transition.slide_up));
//        setEnterTransition(inflater.inflateTransition(R.transition.slide_down));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chats, container, false);

        try {
            communityID = getArguments().getLong("communityID");
        } catch (NullPointerException e) {
            throw new NullPointerException("communityID from fragment arguments is null");
        }
        Log.d("ID", String.valueOf(communityID));

        chatAdapter = new ChatAdapter(requireActivity());
        communityViewModel = new ViewModelProvider(requireActivity()).get(CommunityViewModel.class);
        communityViewModel.getChats(communityID).observe(requireActivity(), chatModels -> {
            if (!chatModels.isEmpty()) {
                CommunityWithChats temp = chatModels.get(0);
                if (temp != null) {
                    chatAdapter.setChatsList(temp.chats);
                }
            }
        });
//        Setup RecyclerView to show chats from specific community
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.chatsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(chatAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        requireActivity().findViewById(R.id.communitySettingsButton).setVisibility(View.VISIBLE);
    }
}