package Fragments.Community;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.szampchat.R;

import Adapters.ChannelAdapter;
import Data.Relations.CommunityWithChannels;
import DataAccess.ViewModels.CommunityViewModel;

public class ChannelsFragment extends Fragment {

    CommunityViewModel communityViewModel;
    ChannelAdapter channelAdapter;
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
        View view = inflater.inflate(R.layout.fragment_channels, container, false);

        try {
            communityID = getArguments().getLong("communityID");
        } catch (NullPointerException e) {
            throw new NullPointerException("communityID from fragment's arguments is null");
        }

        channelAdapter = new ChannelAdapter(requireActivity());
        communityViewModel = new ViewModelProvider(requireActivity()).get(CommunityViewModel.class);
        communityViewModel.getChannels(communityID).observe(getViewLifecycleOwner(), communityWithChannels -> {
            if (!communityWithChannels.isEmpty()){
                CommunityWithChannels temp = communityWithChannels.get(0);
                if (temp != null) {
                    channelAdapter.setChannelsList(temp.channels);
                }
            }
        });

//        Setup RecyclerView to show channels from specific community
        RecyclerView recyclerView = view.findViewById(R.id.channelsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(channelAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        requireActivity().findViewById(R.id.communitySettingsButton).setVisibility(View.VISIBLE);
    }
}