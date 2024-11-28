package Fragments.Settings;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.szampchat.R;

import java.util.ArrayList;
import java.util.List;

public class TechFragment extends Fragment {
    long communityId;
    RolesListener rolesListener;

    public interface RolesListener{
        void addRole(String name, long permission, List<Long> members);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            rolesListener = (RolesListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(e.getMessage() + " must implements RolesFragment.RolesListener");
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransitionInflater inflater = TransitionInflater.from(requireContext());
        setEnterTransition(inflater.inflateTransition(R.transition.slide_right));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tech, container, false);

        try {
            communityId = getArguments().getLong("communityID");
        } catch (NullPointerException e) {
            throw new NullPointerException("communityID from fragment's arguments is null");
        }

        TextInputEditText roleNameInput = view.findViewById(R.id.createRoleNameInput);
        TextInputLayout roleNameLayout = view.findViewById(R.id.createRoleNameLayout);
        SwitchMaterial setting1Switch = view.findViewById(R.id.roleSetting1Switch);
        SwitchMaterial setting2Switch = view.findViewById(R.id.techSetting2Switch);
        SwitchMaterial setting3Switch = view.findViewById(R.id.techSetting3Switch);
        SwitchMaterial setting4Switch = view.findViewById(R.id.techSetting4Switch);
        SwitchMaterial setting5Switch = view.findViewById(R.id.techSetting5Switch);
        SwitchMaterial setting6Switch = view.findViewById(R.id.techSetting6Switch);
        SwitchMaterial setting7Switch = view.findViewById(R.id.techSetting7Switch);
        SwitchMaterial setting8Switch = view.findViewById(R.id.techSetting8Switch);

        TextView techSetting1Input = view.findViewById(R.id.techSetting1Input);
        techSetting1Input.setText("Administrator:");

        TextView techSetting2Input = view.findViewById(R.id.techSetting2Input);
        techSetting2Input.setText("Tworzenie/Modyfikacja ról:");

        TextView techSetting3Input = view.findViewById(R.id.techSetting3Input);
        techSetting3Input.setText("Tworzenie zaproszeń:");

        TextView techSetting4Input = view.findViewById(R.id.techSetting4Input);
        techSetting4Input.setText("Tworzenie kanałów:");

        TextView techSetting5Input = view.findViewById(R.id.techSetting5Input);
        techSetting5Input.setText("Modyfikacja kanałów:");

        TextView techSetting6Input = view.findViewById(R.id.techSetting6Input);
        techSetting6Input.setText("Wysyłanie wiadomości w kanałach tekstowych:");

        TextView techSetting7Input = view.findViewById(R.id.techSetting7Input);
        techSetting7Input.setText("Usuwanie wiadomości w kanałach tekstowych:");

        TextView techSetting8Input = view.findViewById(R.id.techSetting8Input);
        techSetting8Input.setText("Dodawanie reakcji do wiadomości:");

        Button saveButton = view.findViewById(R.id.saveButton);

        saveButton.setOnClickListener(v -> {
            String roleName = roleNameInput.getText().toString();
            if (roleName.matches("")){
                roleNameLayout.setError("Podaj nazwe roli!");
            } else {
                long permissionOverwrites = 0;

                if (setting1Switch.isChecked()) permissionOverwrites |= 1L << 0;
                if (setting2Switch.isChecked()) permissionOverwrites |= 1L << 1;
                if (setting3Switch.isChecked()) permissionOverwrites |= 1L << 2;
                if (setting4Switch.isChecked()) permissionOverwrites |= 1L << 3;
                if (setting5Switch.isChecked()) permissionOverwrites |= 1L << 4;
                if (setting6Switch.isChecked()) permissionOverwrites |= 1L << 5;
                if (setting7Switch.isChecked()) permissionOverwrites |= 1L << 6;
                if (setting8Switch.isChecked()) permissionOverwrites |= 1L << 7;

                List<Long> members = new ArrayList<>();

                rolesListener.addRole(roleName, permissionOverwrites, members);

                Bundle fragmentArgs = new Bundle();
                fragmentArgs.putLong("communityId", communityId);
                RolesFragment rolesFragment = new RolesFragment();
                rolesFragment.setArguments(fragmentArgs);

                requireActivity().getSupportFragmentManager().popBackStack("uniqueSettingsFrag", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, rolesFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }
}