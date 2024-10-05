package Activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.szampchat.R;

import java.util.Collections;

import Adapters.CommunityAdapter;
import Data.Models.CommunityModel;
import Data.ViewModels.CommunityViewModel;
import Fragments.MainFragment;
import Fragments.Settings.SettingsFragment;

public class MainActivity extends AppCompatActivity implements
        CommunityAdapter.OnItemClickListener,
        MainFragment.MainFragmentListener
{
    CommunityViewModel mCommunitiesViewModel;

    Button settingsButton;

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

//        Button displaying Settings section (fragment)
        settingsButton = findViewById(R.id.mainSettingsButton);
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