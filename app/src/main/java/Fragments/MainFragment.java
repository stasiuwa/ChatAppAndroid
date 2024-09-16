package Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.szampchat.R;

import Adapters.CommunityAdapter;

public class MainFragment extends Fragment {

    CommunityAdapter adapter;
    MainFragmentListener mainFragmentListener;

    public MainFragment(CommunityAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mainFragmentListener = (MainFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(e.getMessage() + " must implements MainFragment.MainFragmentListener");
        }
    }
    public interface MainFragmentListener {
        void callCreateCommunityDialog();
        void callAddCommunityDialog();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransitionInflater inflater = TransitionInflater.from(requireContext());
        setExitTransition(inflater.inflateTransition(R.transition.slide_left));
        setEnterTransition(inflater.inflateTransition(R.transition.slide_right));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.communitiesGrid);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setAdapter(adapter);

        Button createCommunityButton = view.findViewById(R.id.createCommunityButton);
        createCommunityButton.setOnClickListener(v -> {
            mainFragmentListener.callCreateCommunityDialog();
        });

        return view;
    }
}