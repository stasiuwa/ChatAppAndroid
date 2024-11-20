package Fragments.Auth;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.szampchat.R;
public class RegisterFragment extends Fragment {

    RegisterListener registerListener;

    public interface RegisterListener {
        void registerUser(String username, String email, String password, String passwordCheck);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            registerListener = (RegisterListener) context;
        }catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement RegisterListener interface");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransitionInflater inflater = TransitionInflater.from(requireContext());
        setExitTransition(inflater.inflateTransition(R.transition.slide_left));
        setEnterTransition(inflater.inflateTransition(R.transition.slide_right));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        TextInputEditText username = view.findViewById(R.id.usernameTextField);
        TextInputEditText email = view.findViewById(R.id.emailTextField);
        TextInputEditText password = view.findViewById(R.id.passwordTextField);
        TextInputEditText passwordCheck = view.findViewById(R.id.passwordCheckTextField);

        Button registerButton = view.findViewById(R.id.createAccountButton);
        registerButton.setOnClickListener(v -> {
            String
                    usernameText = username.getText().toString(),
                    emailText = email.getText().toString(),
                    passwordText = password.getText().toString(),
                    passwordCheckText = passwordCheck.getText().toString();

            boolean validate = true;
            if (usernameText.matches("")) {
                username.setError("Podaj nazwe użytkownika!");
                validate = false;
            };
            if (emailText.matches("")) {
                email.setError("Podaj adres email!");
                validate = false;
            }
            if (passwordText.matches("")) {
                password.setError("Podaj hasło!");
                validate = false;
            }
            if (passwordCheckText.matches("")) {
                passwordCheck.setError("Powtórz hasło!");
                validate = false;
            }
            if (validate){
                if (passwordText.matches(passwordCheckText)){
//                    Pass params to AuthActivity function
                    registerListener.registerUser(usernameText,emailText,passwordText,passwordCheckText);
                } else {
                    Toast.makeText(getContext(), "Hasła się nie zgadzają!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
}