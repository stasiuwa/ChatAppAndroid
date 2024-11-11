package Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.szampchat.R;

import Auth.KeycloakService;
import Config.Environment;
import DataAccess.ViewModels.UserViewModel;
import Fragments.Auth.LoginFragment;
import Fragments.Auth.RegisterFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class AuthActivity extends AppCompatActivity implements LoginFragment.LoginListener {

    UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_auth);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Uri data = getIntent().getData();
        if (data != null && data.toString().startsWith("com.app:/oauth2redirect")) {
            String authcode = data.getQueryParameter("code");
            if (authcode != null){
                Log.d("CHUj", "chuj");
            }
        }
    }

    @Override
    public void verifyLogin() {
//       TODO narazie tak zostaje, bo nie ma co sie z tym meczycz jak potem trzeba bedzie zmienic na serwer

        String keycloakURL = Environment.keycloakUrl +
                "/realms/szampchat/protocol/openid-connect/auth" +
                "?client_id=mobile" +
                "&response_type=code" +
                "&redirect_uri=com.szampchat:/oauth2redirect";
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(keycloakURL));
        startActivity(browserIntent);
    }

    /**
     * Replace fragment container in AuthActivity with Register From Framgent
     */
    @Override
    public void switchToRegister() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.authFragmentContainer, new RegisterFragment())
                .addToBackStack(null)
                .setReorderingAllowed(true)
                .commit();
    }
}