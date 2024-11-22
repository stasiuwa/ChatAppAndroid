package Fragments.Settings;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.szampchat.R;

import Data.DTO.ChannelType;

public class ChannelsFragment extends Fragment {

    ChannelsListener channelsListener;
    private ChannelType channelType;

    public interface ChannelsListener {
        void addChannel(String name, String type);
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            channelsListener = (ChannelsListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(e.getMessage() + " must implements ChannelsFragment.ChannelListener");
        }
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

        final Dialog createChannelDialog = new Dialog(requireContext());
        createChannelDialog.setContentView(R.layout.single_input_dialog);
        createChannelDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);

        TextInputLayout dialogLayout = createChannelDialog.findViewById(R.id.dialogInputLayout);
        TextInputEditText dialogEditText = createChannelDialog.findViewById(R.id.dialogInput);
        Button dialogButton = createChannelDialog.findViewById(R.id.dialogButton);
        dialogButton.setText(R.string.add);

        addVoiceChannel.setOnClickListener(v -> {
            Log.d("ChannelsFragment - addChannel", ChannelType.VOICE_CHANNEL.name());
//            channelListener.addChannel();
            dialogLayout.setHint("Nazwa kanału głosowego");
            dialogEditText.setText("");
            channelType = ChannelType.VOICE_CHANNEL;
            createChannelDialog.show();
        });
        addTextChannel.setOnClickListener(v -> {
            Log.d("ChannelsFragment - addChannel", ChannelType.TEXT_CHANNEL.name());
            dialogLayout.setHint("Nazwa kanału tekstowego");
            dialogEditText.setText("");
            channelType = ChannelType.TEXT_CHANNEL;
            createChannelDialog.show();
        });

        dialogButton.setOnClickListener(v -> {
            String channelName = dialogEditText.getText().toString();
            if (channelName.matches("")) dialogLayout.setError("Podaj nazwę kanału!");
            else {
                channelsListener.addChannel(channelName, channelType.name());
                createChannelDialog.dismiss();
            }
        });

        return view;
    }
}