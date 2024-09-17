package Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.navigation.NavigationBarView;
import com.szampchat.R;

import java.util.ArrayList;

import Adapters.ChannelAdapter;
import Adapters.ChatAdapter;
import Data.Models.ChannelModel;
import Data.Models.ChatModel;
import Data.Relations.CommunityWithChannels;
import Data.Relations.CommunityWithChats;
import Data.ViewModels.CommunityViewModel;
import Fragments.Community.ChannelsFragment;
import Fragments.Community.ChatsFragment;
import Fragments.Community.CommunityWelcomeFragment;
import Fragments.Community.UsersFragment;
import Fragments.Settings.SettingsFragment;

public class CommunityActivity extends AppCompatActivity implements ChannelAdapter.OnItemClickListener, ChatAdapter.OnItemClickListener {

    CommunityViewModel communityViewModel;
    long communityID;

    ChannelAdapter channelAdapter;
    ChatAdapter chatAdapter;

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

//        Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        communityViewModel = new ViewModelProvider(this).get(CommunityViewModel.class);

//        Load Community id and name from intent
        Intent intent = getIntent();
        if (intent.hasExtra("CommunityID")) {
            communityID = intent.getLongExtra("CommunityID", 1);
        }
        if (intent.hasExtra("communityName")){
            getSupportActionBar().setTitle(intent.getStringExtra("communityName"));
        }

        chatAdapter = new ChatAdapter(this);
        channelAdapter = new ChannelAdapter(this);

//        Load channels from community to adapter
        communityViewModel.getChannels(communityID).observe(this, channelModels -> {
            for (CommunityWithChannels community : channelModels) {
                channelAdapter.setChannelsList(new ArrayList<>(community.channels));
            }
        });
//        Load Chats from community to adapter
        communityViewModel.getChats(communityID).observe(this, chatModels -> {
            for (CommunityWithChats community : chatModels) {
                chatAdapter.setChatsList(new ArrayList<>(community.chats));
            }
        });

//        Button displaying welcome page of community (fragment)
        Button homeButton = findViewById(R.id.communityHomeButton);
        homeButton.setOnClickListener(v -> {
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
                this.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, new ChatsFragment(chatAdapter))
                        .commit();
                return true;
            }
            else if (item.getItemId() == R.id.navbar_menu_channels) {
                this.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, new ChannelsFragment(channelAdapter))
                        .commit();
                return true;
            }
            else if (item.getItemId() == R.id.navbar_menu_users) {
                this.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, new UsersFragment())
                        .commit();
                return true;
            }
            else if (item.getItemId() == R.id.navbar_menu_settings) {
                this.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, new SettingsFragment())
                        .commit();
                return true;
            } else return false;
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onItemClickListener(ChannelModel channel) {
        Toast.makeText(this, channel.getChannelName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClickListener(ChatModel chat) {
        Toast.makeText(this, chat.getChatName(), Toast.LENGTH_SHORT).show();
    }
}