package Fragments.Settings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.szampchat.R;

import Config.env;
import Data.DTO.ChannelType;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class ChannelsFragment extends Fragment {

    ChannelListener channelListener;

    public interface ChannelListener {
        void addChannel();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransitionInflater inflater = TransitionInflater.from(requireContext());
        setEnterTransition(inflater.inflateTransition(R.transition.slide_right));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_channels_settings, container, false);

        Button addVoiceChannel = view.findViewById(R.id.addVoiceChannelButton);
        Button addTextChannel = view.findViewById(R.id.addTextChannelButton);
        addVoiceChannel.setOnClickListener(v -> {
//            TODO formularz, walidacja
            Log.d("ChannelsFragment - addChannel", ChannelType.VOICE_CHANNEL.name());
//            channelListener.addChannel();
        });
        addTextChannel.setOnClickListener(v -> {
//            TODO formularz, walidacja
            Log.d("ChannelsFragment - addChannel", ChannelType.TEXT_CHANNEL.name());
        });

        return view;
    }
}