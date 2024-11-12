package Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.szampchat.R;

import Auth.KeycloakService;
import Auth.Token;
import Config.Environment;
import DataAccess.ViewModels.UserViewModel;
import Fragments.Auth.LoginFragment;
import Fragments.Auth.RegisterFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class AuthActivity extends AppCompatActivity
        implements LoginFragment.LoginListener, RegisterFragment.RegisterListener {

    UserViewModel userViewModel;
    KeycloakService keycloakService;
    Token token;

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
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Environment.keycloakUrl)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        keycloakService = retrofit.create(KeycloakService.class);
    }

    /**
     * Function calling api for user authentication and receive token which is sent to MainActivity with Intent
     * @param username username from login form
     * @param password password from login form
     */

    @Override
    public void verifyLogin(String username, String password) {
        token = new Token();

        Call<Token> tokenCall = keycloakService.getAccessToken(
                Environment.keycloakClientId,
                "password",
                username,
                password
        );
        tokenCall.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (response.isSuccessful() && response.body() != null) {
                    token.setAccessToken(response.body().getAccessToken());
                    token.setExpiresIn(response.body().getExpiresIn());
                    token.setRefreshExpiresIn(response.body().getRefreshExpiresIn());
                    token.setRefreshToken(response.body().getRefreshToken());
                    token.setTokenType(response.body().getTokenType());

                    Log.d("AuthActivity", "Token: " + token.getAccessToken());
                    Intent intent = new Intent(AuthActivity.this, MainActivity.class);
                    saveToken(getApplicationContext(), token.getAccessToken());
                    startActivity(intent);
                } else {
                    Log.e("AuthActivity", "Błąd logowania: " + response.code() + " " + response.message());
                }
            }
            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Log.e("AuthActivity", "Błąd podczas wywoływania usługi: " + t.getMessage());
            }
        });
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

    @Override
    public void registerUser(String username, String email, String password, String passwordCheck) {
//        dodac POST na /api/users z username
    }

//    TODO przerobic aby nie przechowywac klucz wartosć tylko obiekt typu Token
    /**
     * Saving token to SharedPreferences
     * @param context
     * @param token
     */
    public void saveToken(Context context, String token){
        SharedPreferences sharedPreferences = context.getSharedPreferences("app_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);
        editor.apply();
    }
}