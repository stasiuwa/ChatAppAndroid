package Fragments.Community;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.szampchat.R;

import Data.Models.ChannelModel;

public class VoiceChatFragment extends Fragment {

    ChannelModel voiceChat;

    public VoiceChatFragment(ChannelModel voiceChat) {
        this.voiceChat = voiceChat;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_voice_chat, container, false);

        TextView test1 = view.findViewById(R.id.test1);
        test1.setText(voiceChat.getChannelName());

        return view;
    }
}