package Fragments.Community;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.szampchat.R;

import Data.Models.Channel;
import DataAccess.ViewModels.ChannelViewModel;
import DataAccess.ViewModels.RoleViewModel;
import DataAccess.ViewModels.UserViewModel;

public class CommunityWelcomeFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransitionInflater inflater = TransitionInflater.from(requireContext());
//        setExitTransition(inflater.inflateTransition(R.transition.slide_up));
//        setEnterTransition(inflater.inflateTransition(R.transition.slide_down));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community_welcome, container, false);

        TextView voiceChannelNumber = view.findViewById(R.id.voiceChannelNumber);
        TextView textChannelNumber = view.findViewById(R.id.textChannelNumber);
        TextView usersNumber = view.findViewById(R.id.usersNumber);
        TextView rolesNumber = view.findViewById(R.id.rolesNumber);

        Bundle communityBundle = getArguments();
        if (communityBundle!=null){
            int usersCount = communityBundle.getInt("usersCount", 0);
            int rolesCount = communityBundle.getInt("rolesCount", 0);
            int voiceChannelsCount = communityBundle.getInt("voiceChannelsCount", 0);
            int textChannelsCount = communityBundle.getInt("textChannelsCount", 0);

            if (usersNumber != null) {
                usersNumber.setText(String.valueOf(usersCount));
            }
            if (rolesNumber != null) {
                rolesNumber.setText(String.valueOf(rolesCount));
            }
            if (voiceChannelNumber != null) {
                voiceChannelNumber.setText(String.valueOf(voiceChannelsCount));
            }
            if (textChannelNumber != null) {
                textChannelNumber.setText(String.valueOf(textChannelsCount));
            }
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        requireActivity().findViewById(R.id.communitySettingsButton).setVisibility(View.VISIBLE);
    }
}