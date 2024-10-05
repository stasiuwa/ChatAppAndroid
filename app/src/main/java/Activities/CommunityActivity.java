package Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.navigation.NavigationBarView;
import com.szampchat.R;

import java.util.ArrayList;

import Adapters.ChannelAdapter;
import Adapters.ChatAdapter;
import Adapters.MessageAdapter;
import Data.Models.ChannelModel;
import Data.Models.ChatModel;
import Data.Models.MessageModel;
import Data.Relations.CommunityWithChannels;
import Data.Relations.CommunityWithChats;
import Data.ViewModels.CommunityViewModel;
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
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        this.getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, new CommunityWelcomeFragment())
                .commit();

//        Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);

        settingsButton = findViewById(R.id.communitySettingsButton);
        settingsButton.setOnClickListener(v -> {
//            this.getSupportFragmentManager().popBackStack("uniqueFrag", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            this.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, new SettingsFragment())
                    .addToBackStack("uniqueFrag")
                    .commit();
//            Hide settings button after displaying SettingsFragment
            settingsButton.setVisibility(View.INVISIBLE);
        });
//        Load Community id and name from intent
        Intent intent = getIntent();
        if (intent.hasExtra("CommunityID")) {
            communityID = intent.getLongExtra("CommunityID", 1);
        }
        if (intent.hasExtra("communityName")){
            getSupportActionBar().setTitle(intent.getStringExtra("communityName"));
        }
        Bundle bundle = new Bundle();
        bundle.putLong("communityID", communityID);

//        Load channels from community to adapter
//        communityViewModel.getChannels(communityID).observe(this, channelModels -> {
//            for (CommunityWithChannels community : channelModels) {
//                channelAdapter.setChannelsList(new ArrayList<>(community.channels));
//            }
//        });
//        Load Chats from community to adapter
//        communityViewModel.getChats(communityID).observe(this, chatModels -> {
//            for (CommunityWithChats community : chatModels) {
//                chatAdapter.setChatsList(new ArrayList<>(community.chats));
//            }
//        });

//        Button displaying welcome page of community (fragment)
        Button homeButton = findViewById(R.id.communityHomeButton);
        homeButton.setOnClickListener(v -> {
            this.getSupportFragmentManager().popBackStack("uniqueFrag", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            this.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, new CommunityWelcomeFragment())
                    .commit();
        });

//        Handle bottom navbar events
//        TODO na wejściu jest pusto i jedna opcja jest zaznaczona, trzeba dorzucic jakiś default fragment do wyświetlania na start
//        TODO ogarnac transition bo jak klikasz w lewo a slide w prawo to troche gryzie w oczy, dac slide in i out
        NavigationBarView navbar = findViewById(R.id.bottom_navbar);
        navbar.setOnItemSelectedListener( item -> {
            if (item.getItemId() == R.id.navbar_menu_chats) {
                this.getSupportFragmentManager().popBackStack("uniqueFrag", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                ChatsFragment chatsFragment = new ChatsFragment();
                chatsFragment.setArguments(bundle);
                this.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, chatsFragment)
                        .addToBackStack("uniqueFrag")
                        .commit();
                return true;
            }
            else if (item.getItemId() == R.id.navbar_menu_channels) {
                this.getSupportFragmentManager().popBackStack("uniqueFrag", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                ChannelsFragment channelsFragment = new ChannelsFragment();
                channelsFragment.setArguments(bundle);
                this.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, channelsFragment)
                        .addToBackStack("uniqueFrag")
                        .commit();
                return true;
            }
            else if (item.getItemId() == R.id.navbar_menu_users) {
                this.getSupportFragmentManager().popBackStack("uniqueFrag", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                UsersFragment usersFragment = new UsersFragment();
                usersFragment.setArguments(bundle);
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

    @Override
    public void onItemClickListener(ChannelModel channel) {
        this.getSupportFragmentManager().popBackStack("uniqueSubFrag", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        this.getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, new VoiceChatFragment())
                .addToBackStack("uniqueSubFrag")
                .commit();
    }

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