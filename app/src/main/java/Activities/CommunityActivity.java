package Activities;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.navigation.NavigationBarView;
import com.szampchat.R;

import Fragments.Community.ChannelsFragment;
import Fragments.Community.ChatsFragment;
import Fragments.Community.CommunityWelcomeFragment;
import Fragments.Community.UsersFragment;
import Fragments.Settings.SettingsFragment;

public class CommunityActivity extends AppCompatActivity  {

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

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            String value = extras.getString("communityName");
            getSupportActionBar().setTitle(value);
        }

        Button homeButton = findViewById(R.id.communityHomeButton);
        homeButton.setOnClickListener(v -> {
            this.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, new CommunityWelcomeFragment())
                    .addToBackStack(null)
                    .commit();
        });

//        Obśługa zdarzeń dolnego paska nawigacji
//        TODO na wejściu jest pusto i jedna opcja jest zaznaczona, trzeba dorzucic jakiś default fragment do wyświetlania na start
//        do tego ewentualnie jakis przycisk domku na toolbarze aby wyswietlic go na wezwanie
//        TODO ogarnac transition bo jak klikasz w lewo a slide w prawo to troche gryzie w oczy, dac slide in i out
        NavigationBarView navbar = findViewById(R.id.bottom_navbar);
        navbar.setOnItemSelectedListener( item -> {
            if (item.getItemId() == R.id.navbar_menu_chats) {
                this.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, new ChatsFragment())
                        .addToBackStack(null)
                        .commit();
                return true;
            }
            else if (item.getItemId() == R.id.navbar_menu_channels) {
                this.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, new ChannelsFragment())
                        .addToBackStack(null)
                        .commit();
                return true;
            }
            else if (item.getItemId() == R.id.navbar_menu_users) {
                this.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, new UsersFragment())
                        .addToBackStack(null)
                        .commit();
                return true;
            }
            else if (item.getItemId() == R.id.navbar_menu_settings) {
                this.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, new SettingsFragment())
                        .addToBackStack(null)
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
}