package Fragments.Settings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.szampchat.R;

import Config.env;
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

        Button addChannel = view.findViewById(R.id.addChannelButton);
        addChannel.setOnClickListener(v -> {
//            TODO formularz, walidacja
            channelListener.addChannel();
        });

        return view;
    }
}