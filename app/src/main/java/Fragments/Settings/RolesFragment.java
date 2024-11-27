package Fragments.Settings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.szampchat.R;

import Adapters.RoleAdapter;
import DataAccess.ViewModels.RoleViewModel;

public class RolesFragment extends Fragment {
    long communityId;
    RoleAdapter roleAdapter;
    RoleViewModel roleViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransitionInflater inflater = TransitionInflater.from(requireContext());
        setEnterTransition(inflater.inflateTransition(R.transition.slide_right));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_roles, container, false);

        try {
            communityId = getArguments().getLong("communityID");
        } catch (NullPointerException e) {
            throw new NullPointerException("communityID from fragment's arguments is null");
        }

        roleViewModel = new ViewModelProvider(requireActivity()).get(RoleViewModel.class);
        roleAdapter = new RoleAdapter(requireActivity());

        roleViewModel.getRolesForCommunity(communityId).observe(getViewLifecycleOwner(), roles -> {
            if (roles != null){
                roleAdapter.setRoleList(roles);
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.roleRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(roleAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(roleAdapter.getItemTouchHelperCallback());
        itemTouchHelper.attachToRecyclerView(recyclerView);

        return view;
    }
}