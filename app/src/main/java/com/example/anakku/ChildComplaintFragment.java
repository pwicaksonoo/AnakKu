package com.example.anakku;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.anakku.settings.SharedPref;
import com.example.anakku.viewmodels.ChildViewModel;

public class ChildComplaintFragment extends Fragment {

    private Button keluhanDemamButton, keluhanDiareButton, keluhanBatukButton, keluhanGatalButton;

    private ChildViewModel childViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        childViewModel = new ViewModelProvider(requireActivity()).get(ChildViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_child_complaint, container, false);

        keluhanDemamButton = view.findViewById(R.id.buttonKeluhanDemam);
        keluhanDiareButton = view.findViewById(R.id.buttonkeluhanDiare);
        keluhanBatukButton = view.findViewById(R.id.buttonkeluhanBatuk);
        keluhanGatalButton = view.findViewById(R.id.buttonkeluhanGatal);

        keluhanDemamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPref.write(SharedPref.ACTIVE_CHILD, "MHK1CK8KaEY4KIrHtciC");
                childViewModel.switchChild();
                Navigation.findNavController(getView()).navigate(R.id.action_childComplaintFragment_to_keluhanDemamFragment);
            }
        });

        keluhanDiareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPref.write(SharedPref.ACTIVE_CHILD, "SPQjO8IsX5gkhwIb9R5K");
                childViewModel.switchChild();
                Navigation.findNavController(getView()).navigate(R.id.action_childComplaintFragment_to_keluhanDiareFragment);
            }
        });

        keluhanBatukButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPref.write(SharedPref.ACTIVE_CHILD, "f58B8hscikf2a60RNbyJ");
                childViewModel.switchChild();
                Navigation.findNavController(getView()).navigate(R.id.action_childComplaintFragment_to_keluhanBatukFragment);
            }
        });

        keluhanGatalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPref.write(SharedPref.ACTIVE_CHILD, "NULL");
                childViewModel.switchChild();
                Navigation.findNavController(getView()).navigate(R.id.action_childComplaintFragment_to_keluhanGatalFragment);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}