package Fragments.Community;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.szampchat.R;

public class VoiceChatFragment extends Fragment {

    long channelID;
    String channelName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_voice_chat, container, false);

        Bundle receivedBundle = getArguments();
        if (receivedBundle != null){
            if (receivedBundle.containsKey("channelID")){
                channelID = receivedBundle.getLong("channelID");
            }
            if (receivedBundle.containsKey("channelName")){
                channelName = receivedBundle.getString("channelName");
            } else {
                channelName = "Channel name not found!";
            }
        }

        TextView test1 = view.findViewById(R.id.test1);
        test1.setText(channelName);

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        requireActivity().findViewById(R.id.communitySettingsButton).setVisibility(View.VISIBLE);
    }
}