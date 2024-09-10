package Fragments.Auth;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.szampchat.R;

import Activities.MainActivity;

public class LoginFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransitionInflater inflater = TransitionInflater.from(requireContext());
        setExitTransition(inflater.inflateTransition(R.transition.slide_left));
        setEnterTransition(inflater.inflateTransition(R.transition.slide_right));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        Button registerButton = (Button) view.findViewById(R.id.registerButton);
        registerButton.setOnClickListener( v -> {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.authFragmentContainer, new RegisterFragment())
                    .addToBackStack(null)
                    .setReorderingAllowed(true)
                    .commit();
        });
        Button loginButton = (Button) view.findViewById(R.id.loginButton);
        loginButton.setOnClickListener( v -> {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        });
        return view;
    }
}