package Activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.szampchat.R;

import java.util.Arrays;

import Adapters.CommunityAdapter;
import Data.DTO.Token;
import Services.UserService;
import Config.Environment;
import Data.Models.CommunityModel;
import Data.DTO.UserDTO;
import DataAccess.ViewModels.CommunityViewModel;
import Fragments.MainFragment;
import Fragments.Settings.SettingsFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class MainActivity extends AppCompatActivity implements
        CommunityAdapter.OnItemClickListener,
        MainFragment.MainFragmentListener
{

    CommunityViewModel mCommunitiesViewModel;
    Button settingsButton;
    Token token = new Token();
    UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //        Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Społeczności");

//        Load token from SharedPreferences
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("app_prefs", MODE_PRIVATE);
        token.setAccessToken(sharedPreferences.getString("token", "TOKEN NOT FOUND"));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Environment.api)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        userService = retrofit.create(UserService.class);
        Call<UserDTO> userInfoCall = userService.getCurrentUser("Bearer "+token.getAccessToken());
        userInfoCall.enqueue(new Callback<UserDTO>() {
            @Override
            public void onResponse(@NonNull Call<UserDTO> call, @NonNull Response<UserDTO> response) {
                if (response.isSuccessful() && response.body() != null){
                    if (response.body().getId() != null && response.body().getUsername() != null)
                    {
                        Log.d("MainActivity UserInfo - id", response.body().getId());
                        Log.d("MainActivity UserInfo - username", response.body().getUsername());

                        String id = response.body().getId();
                        String username = response.body().getUsername();
//                    TODO przerobic na proto DataStore np.
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("userId", id);
                        editor.putString("username", username);
                        getSupportActionBar().setTitle("YO! "+username);
                        if (response.body().getDescription() != null) {
                            String description = response.body().getDescription();
                            Log.d("UserInfo - description:", description);
                            editor.putString("description", description);
                        }
                        if (response.body().getImageUrl() != null){
                            String imageUrl = response.body().getImageUrl();
                            Log.d("UserInfo - imageUrl", imageUrl);
                            editor.putString("imageUrl", imageUrl);
                        }
                        editor.apply();
                    }
                }
                else {
                    Log.d("MainActivity", "Błąd pobierania danych o użytkowniku" + response.message());
                }
            }
            @Override
            public void onFailure(@NonNull Call<UserDTO> call, @NonNull Throwable t) {
                Log.d("MainActivity", "Nieudane połączenie do serwera" + Arrays.toString(t.getStackTrace()));
            }
        });


//        Button displaying Settings section (fragment)
        settingsButton = findViewById(R.id.mainSettingsButton);
        SettingsFragment settingsFragment = new SettingsFragment();
        settingsButton.setOnClickListener(v -> {
            this.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, new SettingsFragment())
                    .addToBackStack(null)
                    .commit();
//            Hide settings button after displaying SettingsFragment
            settingsButton.setVisibility(View.INVISIBLE);
        });

//        Initial setup fragment with communities recyclerview, passed adapter to constructor
        this.getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, new MainFragment())
                .commit();
    }

    /**
     * Showing dialog with join community form.
     */
    @Override
    public void callAddCommunityDialog() {
        final Dialog addCommunityDialog = new Dialog(this);
        addCommunityDialog.setContentView(R.layout.join_community_dialog);
        TextView tittle = addCommunityDialog.findViewById(R.id.dialogTittle);
        EditText joinCode = addCommunityDialog.findViewById(R.id.communityDialogInput);
        Button joinButton = addCommunityDialog.findViewById(R.id.communityDialogButton);
//        TODO zmienic na pobranie spolecznosci z serwera i dołączenie do niej
        joinButton.setOnClickListener(v -> {
            mCommunitiesViewModel.addCommunity(new CommunityModel(joinCode.getText().toString()));
            addCommunityDialog.dismiss();
        });
        tittle.setText("DOŁĄCZ DO SPOŁECZNOŚCI");
        joinCode.setHint("podaj kod dołączenia");
        joinButton.setText("DOŁĄCZ");
        addCommunityDialog.show();
    }

    /**
     * Showing dialog with create community form
     */
    @Override
    public void callCreateCommunityDialog() {
        final Dialog createCommunityDialog = new Dialog(this);
        createCommunityDialog.setContentView(R.layout.join_community_dialog);
        TextView tittle = createCommunityDialog.findViewById(R.id.dialogTittle);
        EditText communityName = createCommunityDialog.findViewById(R.id.communityDialogInput);
        Button createButton = createCommunityDialog.findViewById(R.id.communityDialogButton);
//        TODO wysłać requesta na stworzenie społecznosci na serwer
//        TODO dodac wgranie zdjecia na ikonke spolecznosci
        createButton.setOnClickListener(v -> {
            mCommunitiesViewModel.addCommunity(new CommunityModel(communityName.getText().toString()));
            createCommunityDialog.dismiss();
        });
        tittle.setText("STWÓRZ SPOŁECZNOŚĆ");
        communityName.setHint("podaj nazwę społeczności");
        createButton.setText("STWÓRZ");
        createCommunityDialog.show();
    }
    /**
     * Changing visibility of settings button after resuming main fragment
     */
    @Override
    public void onItemClickListener(CommunityModel community) {
//        Log.d("COMMUNITY-MAIN", "id: " + community.getCommunityID());
        if (community.getCommunityID() == 1) {
            callAddCommunityDialog();
        }
        else {
            Intent intent = new Intent(MainActivity.this, CommunityActivity.class);
            intent.putExtra("communityID", community.getCommunityID());
            intent.putExtra("communityName", community.getCommunityName());
            startActivity(intent);
        }
    }

}