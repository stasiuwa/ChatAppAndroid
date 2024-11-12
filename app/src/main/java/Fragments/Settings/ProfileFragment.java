package Fragments.Settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.szampchat.R;

public class ProfileFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransitionInflater inflater = TransitionInflater.from(requireContext());
        setEnterTransition(inflater.inflateTransition(R.transition.slide_right));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("app_prefs", Context.MODE_PRIVATE);

//        TODO pobrac całe info o userze razem z email i dodac opcje zmiany informacji

        ImageView profilePicture = view.findViewById(R.id.profilePicture);
        TextView profileUsername = view.findViewById(R.id.profileUsername);
        TextView profileDescription = view.findViewById(R.id.profileAbout);

//        sharedPreferences.getString("imageUrl", "Image Not Found");
        profileUsername.setText(sharedPreferences.getString("username", "User Not Found :("));
        profileDescription.setText(sharedPreferences.getString("description", "No description from this user :("));

        return view;
    }
}