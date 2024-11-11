package Fragments.Auth;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


import com.szampchat.R;

import DataAccess.ViewModels.UserViewModel;

public class LoginFragment extends Fragment {

    UserViewModel userViewModel;
    LoginListener loginListener;

//    Functions required by this fragment to implement
    public interface LoginListener {
        void verifyLogin();
        void switchToRegister();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            loginListener = (LoginListener) context;
        }catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement LoginListener interface");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransitionInflater inflater = TransitionInflater.from(requireContext());
        setExitTransition(inflater.inflateTransition(R.transition.slide_left));
        setEnterTransition(inflater.inflateTransition(R.transition.slide_right));

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

//        Setup login form view elements
//        EditText login = view.findViewById(R.id.loginTextField);
//        EditText password = view.findViewById(R.id.passwordTextField);

//        Button registerButton = (Button) view.findViewById(R.id.registerButton);
//        registerButton.setOnClickListener( v -> {
//            loginListener.switchToRegister();
//        });
        Button loginButton = (Button) view.findViewById(R.id.loginButton);
        loginButton.setOnClickListener( v -> {
            loginListener.verifyLogin();
        });
        return view;
    }
}