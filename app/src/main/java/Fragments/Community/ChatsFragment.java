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

import com.szampchat.R;

import Adapters.ChatAdapter;
import Data.Relations.CommunityWithChats;
import DataAccess.ViewModels.CommunityViewModel;

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
            throw new NullPointerException("communityID from fragment's arguments is null");
        }
        Log.d("ID", String.valueOf(communityID));

        chatAdapter = new ChatAdapter(requireActivity());
        communityViewModel = new ViewModelProvider(requireActivity()).get(CommunityViewModel.class);
        communityViewModel.getChats(communityID).observe(getViewLifecycleOwner(), communityWithChats -> {
            if (!communityWithChats.isEmpty()) {
                CommunityWithChats temp = communityWithChats.get(0);
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