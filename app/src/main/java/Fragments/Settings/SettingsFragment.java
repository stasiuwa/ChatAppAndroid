package Fragments.Settings;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.szampchat.R;

import Activities.SettingsActivity;

public class SettingsFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        LinearLayout profileSettings = view.findViewById(R.id.settingsUserProfile);
        profileSettings.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.settingsFragmentContainer, new ProfileFragment())
                    .addToBackStack(null)
                    .commit();
            ((SettingsActivity) getActivity()).getSupportActionBar().setTitle("PROFIL");
        });
        LinearLayout appSettings = view.findViewById(R.id.settingsApplication);
        appSettings.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.settingsFragmentContainer, new TechFragment())
                    .addToBackStack(null)
                    .commit();
            ((SettingsActivity) getActivity()).getSupportActionBar().setTitle("USTAWIENIA APKI");
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((SettingsActivity) getActivity()).getSupportActionBar().setTitle("USTAWIENIA");
    }
}