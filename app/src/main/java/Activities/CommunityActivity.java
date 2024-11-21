package Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationBarView;
import com.szampchat.R;

import Adapters.ChannelAdapter;
import Adapters.ChatAdapter;
import Adapters.MessageAdapter;
import Data.Models.ChannelModel;
import Data.Models.ChatModel;
import Data.Models.MessageModel;
import Fragments.Community.ChannelsFragment;
import Fragments.Community.ChatsFragment;
import Fragments.Community.CommunityWelcomeFragment;
import Fragments.Community.TextChatFragment;
import Fragments.Community.UsersFragment;
import Fragments.Community.VoiceChatFragment;
import Fragments.Settings.SettingsFragment;

public class CommunityActivity extends AppCompatActivity implements
        ChannelAdapter.OnItemClickListener,
        ChatAdapter.OnItemClickListener,
        MessageAdapter.OnItemClickListener
{
    long communityID;
    Button settingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_community);
//        Setup startup fragment in container
        this.getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, new CommunityWelcomeFragment())
                .commit();

        Button homeButton = findViewById(R.id.communityHomeButton);
        settingsButton = findViewById(R.id.communitySettingsButton);
        settingsButton.setOnClickListener(v -> {
            this.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, new SettingsFragment())
                    .addToBackStack("uniqueFrag")
                    .commit();
//            Hide settings button after displaying SettingsFragment
            settingsButton.setVisibility(View.INVISIBLE);
        });

//        Bundle storing community data
        Bundle communityBundle = new Bundle();

//        Load Community id and name from intent
        Intent intent = getIntent();
        if (intent.hasExtra("communityID")) {
            communityID = intent.getLongExtra("communityID", 1);
            communityBundle.putLong("communityID", communityID);
        }
        if (intent.hasExtra("communityName")){
            String communityName = intent.getStringExtra("communityName");
            homeButton.setText(communityName);
            communityBundle.putString("communityName", communityName);
        }

        homeButton.setOnClickListener(v -> {
            CommunityWelcomeFragment communityWelcomeFragment = new CommunityWelcomeFragment();
            communityWelcomeFragment.setArguments(communityBundle);
            this.getSupportFragmentManager().popBackStack("uniqueFrag", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            this.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, communityWelcomeFragment)
                    .commit();
        });

//        Handle bottom navbar events
        NavigationBarView navbar = findViewById(R.id.bottom_navbar);
        navbar.setOnItemSelectedListener( item -> {
            if (item.getItemId() == R.id.navbar_menu_chats) {
                this.getSupportFragmentManager().popBackStack("uniqueFrag", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                ChatsFragment chatsFragment = new ChatsFragment();
                chatsFragment.setArguments(communityBundle);
                this.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, chatsFragment)
                        .addToBackStack("uniqueFrag")
                        .commit();
                return true;
            }
            else if (item.getItemId() == R.id.navbar_menu_channels) {
                this.getSupportFragmentManager().popBackStack("uniqueFrag", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                ChannelsFragment channelsFragment = new ChannelsFragment();
                channelsFragment.setArguments(communityBundle);
                this.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, channelsFragment)
                        .addToBackStack("uniqueFrag")
                        .commit();
                return true;
            }
            else if (item.getItemId() == R.id.navbar_menu_users) {
                this.getSupportFragmentManager().popBackStack("uniqueFrag", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                UsersFragment usersFragment = new UsersFragment();
                usersFragment.setArguments(communityBundle);
                this.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, usersFragment)
                        .addToBackStack("uniqueFrag")
                        .commit();
                return true;
            }
            else return false;
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    /**
     * Allows user to join a specific voice channel of available channels in RecyclerView from ChannelsFragment
     * @param channel - specific channel selected from a list
     */
    @Override
    public void onItemClickListener(ChannelModel channel) {
        this.getSupportFragmentManager().popBackStack("uniqueSubFrag", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        Bundle channelBundle = new Bundle();
        channelBundle.putLong("channelID", channel.getChannelID());
        channelBundle.putString("channelName", channel.getChannelName());
        VoiceChatFragment voiceChatFragment = new VoiceChatFragment();
        voiceChatFragment.setArguments(channelBundle);
        this.getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, voiceChatFragment)
                .addToBackStack("uniqueSubFrag")
                .commit();
    }

    /**
     * Allows user to join a specific text chat of available chats in RecyclerView from ChatsFragment
     * @param chat - specific chat selected from a list
     */
    @Override
    public void onItemClickListener(ChatModel chat) {
        this.getSupportFragmentManager().popBackStack("uniqueSubFrag", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        Bundle chatBundle = new Bundle();
        chatBundle.putLong("chatID", chat.getChatID());
        chatBundle.putString("chatName", chat.getChatName());
        TextChatFragment textChatFragment = new TextChatFragment();
        textChatFragment.setArguments(chatBundle);
        this.getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, textChatFragment)
                .addToBackStack("uniqueSubFrag")
                .commit();
    }

    @Override
    public void onItemClickListener(MessageModel message) {

    }
}