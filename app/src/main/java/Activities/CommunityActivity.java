package Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.navigation.NavigationBarView;
import com.szampchat.R;

import java.util.ArrayList;
import java.util.Arrays;

import Adapters.ChannelAdapter;
import Adapters.ChatAdapter;
import Config.env;
import Data.DTO.ChannelDTO;
import Data.DTO.ChannelResponseDTO;
import Data.DTO.ChannelType;
import Data.DTO.FullCommunityDTO;
import Data.DTO.Token;
import Data.Models.Channel;
import Data.Models.Chat;
import DataAccess.ViewModels.ChannelViewModel;
import Fragments.Community.ChannelsFragment;
import Fragments.Community.CommunityWelcomeFragment;
import Fragments.Community.TextChatFragment;
import Fragments.Community.UsersFragment;
import Fragments.Community.VoiceChatFragment;
import Fragments.Settings.ChannelsSettingsFragment;
import Fragments.Settings.SettingsFragment;
import Services.ChannelService;
import Services.CommunityService;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class CommunityActivity extends AppCompatActivity implements
        ChannelAdapter.OnItemClickListener,
        ChatAdapter.OnItemClickListener,

        ChannelsSettingsFragment.ChannelsListener
{
    long communityID;
    ChannelViewModel channelViewModel;

    NavigationBarView navbar;
    Button settingsButton, homeButton;

    Token token = new Token();

    Retrofit retrofit;
    CommunityService communityService;
    ChannelService channelService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_community);
//        Setup startup fragment in container
        this.getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, new CommunityWelcomeFragment())
                .commit();

        homeButton = findViewById(R.id.communityHomeButton);
        settingsButton = findViewById(R.id.communitySettingsButton);
        navbar = findViewById(R.id.bottom_navbar);
        channelViewModel = new ViewModelProvider(this).get(ChannelViewModel.class);

//        Bundle storing community data
        Bundle communityBundle = new Bundle();
//        Load token from SharedPreferences
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("app_prefs", MODE_PRIVATE);
        token.setAccessToken(sharedPreferences.getString("token", "TOKEN NOT FOUND"));

//        Load Community id and name from intent
        Intent intent = getIntent();
        if (intent.hasExtra("communityID")) {
            communityID = intent.getLongExtra("communityID", 1);
            communityBundle.putLong("communityID", communityID);
        } else Log.d("CommunityActivity", "communityID wasn't passed to activity!");
        if (intent.hasExtra("communityName")){
            String communityName = intent.getStringExtra("communityName");
            homeButton.setText(communityName);
            communityBundle.putString("communityName", communityName);
        } else Log.d("CommunityActivity", "communityName wasn't passed to activity!");

//        Calling API for full info about Community - channels, roles, members.
        retrofit = new Retrofit.Builder()
                .baseUrl(env.api)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        communityService = retrofit.create(CommunityService.class);
        channelService = retrofit.create(ChannelService.class);

        getCommunityFullInfo();
        setupNavbar(communityBundle);

//        Button that changes present fragment to SettingsFragment and hides
//        Pass to SettingsFragment info to display extended options to set (Createing roles, channels etc)
        Bundle settingsArgs = new Bundle();
        SettingsFragment settingsFragment = new SettingsFragment();
        settingsArgs.putBoolean("EXTENDED", true);
        settingsArgs.putLong("communityId", communityID);
        settingsFragment.setArguments(settingsArgs);
        settingsButton.setOnClickListener(v -> {
            this.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, settingsFragment)
                    .addToBackStack("uniqueFrag")
                    .commit();
//            Hide settings button after displaying SettingsFragment
            settingsButton.setVisibility(View.INVISIBLE);
        });

//        Button that returns to main page of Community, changes fragment to CommunityWelcomeFragment
        homeButton.setOnClickListener(v -> {
            CommunityWelcomeFragment communityWelcomeFragment = new CommunityWelcomeFragment();
            communityWelcomeFragment.setArguments(communityBundle);
            this.getSupportFragmentManager().popBackStack("uniqueFrag", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            this.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, communityWelcomeFragment)
                    .commit();
        });
//        Handle bottom navbar events
    }

    /**
     * Setup navbar onItemSelectedListener to switch fragments to display and pass them info about community
     * @param communityBundle bundle containing community id and name
     */
    private void setupNavbar(Bundle communityBundle){
        navbar.setOnItemSelectedListener( item -> {
            if (item.getItemId() == R.id.navbar_menu_chats) {
                this.getSupportFragmentManager().popBackStack("uniqueFrag", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                communityBundle.putString("type", ChannelType.TEXT_CHANNEL.name());
                ChannelsFragment channelsFragment = new ChannelsFragment();
                channelsFragment.setArguments(communityBundle);
                this.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, channelsFragment)
                        .addToBackStack("uniqueFrag")
                        .commit();
                return true;
            }
            else if (item.getItemId() == R.id.navbar_menu_channels) {
                this.getSupportFragmentManager().popBackStack("uniqueFrag", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                communityBundle.putString("type", ChannelType.VOICE_CHANNEL.name());
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
                communityBundle.putString("type", ChannelType.TEXT_CHANNEL.name());
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

    /**
     * Fetching all data about current community from Server API
     */
    private void getCommunityFullInfo(){
        Call<FullCommunityDTO> callCommunityInfo = communityService.getFullCommunityInfo(
                "Bearer "+token.getAccessToken(),
                communityID
        );
        callCommunityInfo.enqueue(new Callback<FullCommunityDTO>() {
            @Override
            public void onResponse(Call<FullCommunityDTO> call, Response<FullCommunityDTO> response) {
                if (response.isSuccessful() && response.body()!=null){
                    Log.d("CommunityActivity - callCommunityInfo",
                            "Społeczność: " + response.body().getCommunity().getName() +
                                    "\nIlość kanałów: " + response.body().getChannels().size() +
                                    "\nIlość użytkowników: " + response.body().getMembers().size() +
                                    "\nIlość ról: " + response.body().getRoles().size());
                    for (ChannelResponseDTO channelResponseDTO : response.body().getChannels()){
                        channelViewModel.addChannel(channelResponseDTO);
                    }
//                    TODO zapisac dane do viewmodeli
                    homeButton.setText("DZIAŁA");
                } else {
                    Log.d("CommunityActivity - callCommunityInfo", "Błąd pobierania pełnych danych o społeczności" + response.code() + response.message());
                }
            }

            @Override
            public void onFailure(Call<FullCommunityDTO> call, Throwable t) {
                Log.d("CommunityActivity - callCommunityInfo", "Błąd wykonania usługi: " + Arrays.toString(t.getStackTrace()));
            }
        });
    }

    /**
     * Sending to Server API request to create channel
     * @param name channel name
     * @param type channel type (possible values: TEXT_CHANNEL, VOICE_CHANNEL)
     */
    @Override
    public void addChannel(String name, String type) {
        RequestBody body = RequestBody.create(
                MediaType.parse("application/json"),
                "{\n  \"name\": \"" + name + "\",\n  \"type\": \"" + type + "\"\n}"
        );
        Call<ChannelDTO> callAddChannel = channelService.createChannel(
                "Bearer "+token.getAccessToken(),
                communityID,
                body
        );
        callAddChannel.enqueue(new Callback<ChannelDTO>() {
            @Override
            public void onResponse(Call<ChannelDTO> call, Response<ChannelDTO> response) {
                if (response.isSuccessful() && response.body()!=null){
                    channelViewModel.addChannel(new ChannelResponseDTO(response.body(), (type.equals(ChannelType.VOICE_CHANNEL.name())) ? new ArrayList<>() : null, new ArrayList<>()));
                }
                else Log.d("CommunityActivity - addChannel", "Błąd wykonywania usługi: " + response.code() + " " +response.message());
            }

            @Override
            public void onFailure(Call<ChannelDTO> call, Throwable t) {
                Log.d("CommunityActivity - addChannel", "Błąd wykonywania usługi: " + Arrays.toString(t.getStackTrace()));
            }
        });
    }
    /**
     * Allows user to join a specific voice channel of available channels in RecyclerView from ChannelsFragment
     * @param channel - specific channel selected from a list
     */
    @Override
    public void onItemClickListener(Channel channel) {
        this.getSupportFragmentManager().popBackStack("uniqueSubFrag", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        Bundle channelBundle = new Bundle();
        channelBundle.putLong("channelID", channel.getId());
        channelBundle.putString("channelName", channel.getName());
        VoiceChatFragment voiceChatFragment = new VoiceChatFragment();
        voiceChatFragment.setArguments(channelBundle);
        this.getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, voiceChatFragment)
                .addToBackStack("uniqueSubFrag")
                .commit();
    }

    /**
     * Handle long click on specific item in RecyclerView to display delete Channel button
     * @param channel - specific channel selected from a list
     */
    @Override
    public void onItemLongClickListener(Channel channel) {
        Log.d("TEST", "SPRAWDZAMY DZIALANIE BUTTON PO OnItemLongClick");
    }

    /**
     * Allows user to join a specific text chat of available chats in RecyclerView from ChatsFragment
     * @param chat - specific chat selected from a list
     */
    @Override
    public void onItemClickListener(Chat chat) {
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
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}