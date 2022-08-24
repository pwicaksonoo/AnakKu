package com.example.anakku;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.anakku.viewmodels.ProfileUserViewModel;

import java.text.SimpleDateFormat;

public class ProfileUserFragment extends Fragment {
    private ProfileUserViewModel profileUserViewModel;

    private RelativeLayout loadingPanel;
    private TextView namaTextView;
    private TextView tanggalLahirTextView;
    private TextView emailTextView;
    private Button changeEmailButton;
    private Button changePasswordButton;
    private Button logoutButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profileUserViewModel = new ViewModelProvider(this).get(ProfileUserViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_user, container, false);

        loadingPanel = view.findViewById(R.id.loadingPanel);
        loadingPanel.setVisibility(View.VISIBLE);

        namaTextView = view.findViewById(R.id.textViewNama);
        tanggalLahirTextView = view.findViewById(R.id.textViewTanggalLahir);
        emailTextView = view.findViewById(R.id.textViewEmail);
        changeEmailButton = view.findViewById(R.id.buttonChangeEmail);
        changePasswordButton = view.findViewById(R.id.buttonChangePassword);
        logoutButton = view.findViewById(R.id.buttonLogout);

        profileUserViewModel.getFirebaseUserMutableLiveData().observe(getViewLifecycleOwner(), firebaseUser -> {
            if(firebaseUser != null) {
//                Log.d("profile", firebaseUser.getEmail());

                loadingPanel.setVisibility(View.GONE);
                emailTextView.setText(firebaseUser.getEmail());
            }
        });

        profileUserViewModel.getUserMutableLiveData().observe(getViewLifecycleOwner(), user -> {
            if(user != null) {
//                Log.d("profile", user.getNama());

                loadingPanel.setVisibility(View.GONE);
                namaTextView.setText(user.getNama());

                if(user.getTanggalLahir() != null) {
                    SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
                    tanggalLahirTextView.setText(formatter.format(user.getTanggalLahir()));
                }
            }
        });

        changeEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getView()).navigate(R.id.action_profileUserFragment_to_changeEmailFragment);
            }
        });

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getView()).navigate(R.id.action_profileUserFragment_to_changePassFragment);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profileUserViewModel.logOut();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStop() {
        super.onStop();
        profileUserViewModel.stopProfileUserListener();
    }
}
