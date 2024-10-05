package Fragments.Settings;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.szampchat.R;

public class SettingsFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransitionInflater inflater = TransitionInflater.from(requireContext());
        setExitTransition(inflater.inflateTransition(R.transition.slide_left));
        setEnterTransition(inflater.inflateTransition(R.transition.slide_right));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

//        Setup onClickListener to replace current fragment with ProfileFragment
        LinearLayout profileSettings = view.findViewById(R.id.settingsUserProfile);
        profileSettings.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, new ProfileFragment())
                    .addToBackStack(null)
                    .commit();
        });

//        Setup onClickListener to replace current fragment with TechFragment
        LinearLayout appSettings = view.findViewById(R.id.settingsApplication);
        appSettings.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, new TechFragment())
                    .addToBackStack(null)
                    .commit();
        });
        return view;
    }
}