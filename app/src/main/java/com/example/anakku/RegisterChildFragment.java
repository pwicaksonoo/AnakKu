package com.example.anakku;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.anakku.viewmodels.RegisterChildViewModel;

public class RegisterChildFragment extends Fragment {

    private RegisterChildViewModel registerChildViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        registerChildViewModel = new ViewModelProvider(this).get(RegisterChildViewModel.class);

        OnBackPressedCallback backPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                registerChildViewModel.logOut();
                Navigation.findNavController(getView()).navigate(R.id.action_registerChildFragment_to_loginFragment);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, backPressedCallback);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_child, container, false);

        return view;
    }
}