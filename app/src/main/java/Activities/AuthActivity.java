package Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.szampchat.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Data.Repositories.UserRepository;
import Data.ViewModels.UserViewModel;
import Fragments.Auth.LoginFragment;
import Fragments.Auth.RegisterFragment;

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
    public void verifyLogin(String username, String password) {
//        narazie tak zostaje, bo nie ma co sie z tym meczycz jak potem trzeba bedzie zmienic na serwer
        if (true) {
            Intent intent = new Intent(AuthActivity.this, MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("username", username);
            intent.putExtras(bundle);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Niepoprawne dane logowania", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void switchToRegister() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.authFragmentContainer, new RegisterFragment())
                .addToBackStack(null)
                .setReorderingAllowed(true)
                .commit();
    }
}