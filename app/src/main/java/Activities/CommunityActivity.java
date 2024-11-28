package Activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.szampchat.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Adapters.ChannelAdapter;
import Adapters.MessageAdapter;
import Adapters.RoleAdapter;
import Adapters.UserAdapter;
import Config.env;
import Data.DTO.ChannelDTO;
import Data.DTO.ChannelResponseDTO;
import Data.DTO.ChannelType;
import Data.DTO.FullCommunityDTO;
import Data.DTO.MemberDTO;
import Data.DTO.RoleResponseDTO;
import Data.Models.Role;
import Data.Models.Token;
import Data.Models.Channel;
import Data.Models.Message;
import Data.Models.User;
import DataAccess.ViewModels.ChannelViewModel;
import DataAccess.ViewModels.RoleViewModel;
import DataAccess.ViewModels.UserViewModel;
import Fragments.Community.ChannelsFragment;
import Fragments.Community.CommunityWelcomeFragment;
import Fragments.Community.TextChatFragment;
import Fragments.Community.UsersFragment;
import Fragments.Community.VoiceChatFragment;
import Fragments.Settings.ChannelsSettingsFragment;
import Fragments.Settings.RolesFragment;
import Fragments.Settings.SettingsFragment;
import Fragments.Settings.TechFragment;
import Services.ChannelService;
import Services.CommunityService;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

// TODO po dodaniu roli nie pokazuje jej dopóki nie bedzie getFullCommunityInfo
// TODO po dodaniu kanału nie zwieksza ich ilosci na WelcomeFragment
public class CommunityActivity extends AppCompatActivity implements
        ChannelAdapter.OnItemClickListener,
        MessageAdapter.OnItemClickListener,
        UserAdapter.OnItemClickListener,
        RoleAdapter.OnItemClickListener,

        ChannelsSettingsFragment.ChannelsListener,
        TechFragment.RolesListener
{
    long communityID;
    ChannelViewModel channelViewModel;
    RoleViewModel roleViewModel;
    UserViewModel userViewModel;

    Bundle communityBundle;

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
        homeButton = findViewById(R.id.communityHomeButton);
        settingsButton = findViewById(R.id.communitySettingsButton);
        navbar = findViewById(R.id.bottom_navbar);
        channelViewModel = new ViewModelProvider(this).get(ChannelViewModel.class);
        roleViewModel = new ViewModelProvider(this).get(RoleViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

//        Bundle storing community data
        communityBundle = new Bundle();
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
        homeButton.setOnClickListener(v -> setupWelcomeFragment());
    }

    private void setupWelcomeFragment(){
        CommunityWelcomeFragment communityWelcomeFragment = new CommunityWelcomeFragment();
        communityWelcomeFragment.setArguments(communityBundle);
        this.getSupportFragmentManager().popBackStack("uniqueFrag", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        this.getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, communityWelcomeFragment)
                .commit();
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
                    int voiceChannels = 0;
                    Log.d("CommunityActivity - callCommunityInfo",
                            "Społeczność: " + response.body().getCommunity().getCommunityName() +
                                    "\nIlość kanałów: " + response.body().getChannels().size() +
                                    "\nIlość użytkowników: " + response.body().getMembers().size() +
                                    "\nIlość ról: " + response.body().getRoles().size());
                    for (ChannelResponseDTO channelResponseDTO : response.body().getChannels()){
                        channelViewModel.addChannel(channelResponseDTO);
                        if (channelResponseDTO.getChannel().getType() == 1) voiceChannels++;
                    }
                    for (Role role : response.body().getRoles()){
                        role.setCommunityId(communityID);
                        Log.d("ROLE", role.getName() + role.communityId + "-" + role.getRoleId() );
                        roleViewModel.addRole(role);
                    }
                    for (MemberDTO memberDTO : response.body().getMembers()){
                        userViewModel.addUser(memberDTO);
                    }
                    communityBundle.putInt("usersCount", response.body().getMembers().size());
                    communityBundle.putInt("rolesCount", response.body().getRoles().size());
                    communityBundle.putInt("voiceChannelsCount", voiceChannels);
                    communityBundle.putInt("textChannelsCount", response.body().getChannels().size() - voiceChannels);

                    setupWelcomeFragment();

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
     * Allows user to join a specific channel of available channels in RecyclerView from ChannelsFragment
     * @param channel - specific channel selected from a list
     */
    @Override
    public void onItemClickListener(Channel channel) {
        this.getSupportFragmentManager().popBackStack("uniqueSubFrag", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        Bundle channelBundle = new Bundle();
        channelBundle.putLong("channelId", channel.getId());
        channelBundle.putLong("userId", channel.getId());
        channelBundle.putString("channelName", channel.getName());
        if (channel.getType().equals(ChannelType.VOICE_CHANNEL)){
            VoiceChatFragment voiceChatFragment = new VoiceChatFragment();
            voiceChatFragment.setArguments(channelBundle);
            this.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, voiceChatFragment)
                    .addToBackStack("uniqueSubFrag")
                    .commit();
        } else if (channel.getType().equals(ChannelType.TEXT_CHANNEL)) {
            TextChatFragment textChatFragment = new TextChatFragment();
            textChatFragment.setArguments(channelBundle);
            this.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, textChatFragment)
                    .addToBackStack("uniqueSubFrag")
                    .commit();
        } else {
            Log.d("CommunityActivity", "ChannelAdapter.onItemClickListener - niepoprawny typ kanału ChannelType: " + channel.getType());
        }
    }

    /**
     * Handle long click on specific item in RecyclerView to display delete Channel button, only in ChannelsSettingsFragment view
     * @param channel - specific channel selected from a list
     */
    @Override
    public void onItemLongClickListener(Channel channel) {
        Call<Void> callDeleteChannel = channelService.deleteChannel(
                "Bearer " + token.getAccessToken(),
                channel.getId()
        );
        callDeleteChannel.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Usunięto kanał " + channel.getName(), Toast.LENGTH_SHORT).show();
                    channelViewModel.deleteChannel(channel);
                } else {
                    Log.d("DeleteChannel", "Błąd usuwania kanału: " + response.code() + "\t" + response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("DeleteChannel", "Błąd wykonywania usługi: " + Arrays.toString(t.getStackTrace()));
            }
        });
    }

    /**
     * I dont remeber :)
     * @return
     */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    /**
     * TODO
     * @param message
     */
    @Override
    public void onItemClickListener(Message message) {

    }

    /**
     * Display dialog with details about specific user
     * @param user - clicked user from RecyclerView
     */
    @Override
    public void onItemClickListener(User user) {
        final Dialog userProfileDialog = new Dialog(this);
        userProfileDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        userProfileDialog.setContentView(R.layout.user_profile_dialog);

        TextView username = userProfileDialog.findViewById(R.id.userDialogUsername);
        TextView description = userProfileDialog.findViewById(R.id.userDialogDescription);
        TextView roles = userProfileDialog.findViewById(R.id.userDialogRoles);
        ImageView picture = userProfileDialog.findViewById(R.id.userDialogPicture);

        username.setText(user.getUsername());
        description.setText(user.getDescription());
        roles.setText("NIKT");

        userProfileDialog.show();
    }

    /**
     * Handle to show dialog with details about specific role
     * @param role - clicked role from RecyclerView
     */
    @Override
    public void onItemClickListener(Role role) {
        final Dialog userProfileDialog = new Dialog(this);
        userProfileDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        userProfileDialog.setContentView(R.layout.details_dialog);

        TextView title = userProfileDialog.findViewById(R.id.dialogTitle);
        TextView text = userProfileDialog.findViewById(R.id.dialogText);

        title.setText(role.getName());
        text.setText(String.valueOf(role.getPermissionOverwrites()));

        userProfileDialog.show();
    }

    /**
     * Handle LongClick on specific role from RecyclerView - display edit form
     * @param role
     */
    @Override
    public void onItemLongClickListener(Role role) {
//        TODO tutaj dialog z edycja roli
    }

    /**
     * Send POST request to server API to create new role
     * @param name - role name
     * @param permission - role set of permissions
     * @param members - users with this role(for now is always empty)
     */
    @Override
    public void addRole(String name, long permission, List<Long> members) {
        RequestBody body = RequestBody.create(
                MediaType.parse("application/json"),
                "{\n" +
                        "  \"name\": \"" + name + "\",\n" +
                        "  \"permissionOverwrites\": " + permission + ",\n" +
                        "  \"members\": []\n" +
                        "}"
        );

        Call<RoleResponseDTO> callRoleCreate = communityService.createRole(
                "Bearer "+token.getAccessToken(),
                communityID,
                body
        );
        callRoleCreate.enqueue(new Callback<RoleResponseDTO>() {
            @Override
            public void onResponse(Call<RoleResponseDTO> call, Response<RoleResponseDTO> response) {
                if (response.isSuccessful() && response.body() != null){
                    Role role = new Role(
                            response.body().getRole().getRoleId(),
                            response.body().getRole().getName(),
                            communityID,
                            response.body().getRole().getPermissionOverwrites());
                    roleViewModel.addRole(role);
//                    Jakies obejscie, nie aktualizuje adaptera nie wiem czemu
//                    getCommunityFullInfo();
                }
                else {
                    Log.d("CommunityActivity", "addRole() - Błąd dodawania roli " + response.code() + " " + response.message());
                }
            }

            @Override
            public void onFailure(Call<RoleResponseDTO> call, Throwable t) {
                Log.d("CommunityActivity", "addRole() - Błąd wykownywania usługi " + Arrays.toString(t.getStackTrace()));
            }
        });
    }

    /**
     * Send DELETE request to server to remove role from server, on success also from RoleViewModel
     * @param role - specific role to delete
     */
    @Override
    public void onSwipeDeleteRole(Role role) {
        Call<Void> callDeleteRole = communityService.deleteRole(
                "Bearer "+token.getAccessToken(),
                role.getRoleId()
        );
        callDeleteRole.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    roleViewModel.deleteRole(role);
                }
                else Log.d("CommunityActivity", "onSwipeDeleteRole - błąd usuwania roli: " + response.code() + response.message());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("CommunityActivity", "Błąd wykonywania usługi " + Arrays.toString(t.getStackTrace()));
            }
        });
    }


}