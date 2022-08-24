package com.example.anakku;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.anakku.viewmodels.ChangeEmailViewModel;

public class ChangeEmailFragment extends Fragment {
    private ChangeEmailViewModel changeEmailViewModel;
    private EditText newEmailEditText;
    private EditText passwordEditText;
    private Button changeEmailButton;
    private RelativeLayout loadingPanel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        changeEmailViewModel = new ViewModelProvider(this).get(ChangeEmailViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_email, container, false);

        loadingPanel = view.findViewById(R.id.loadingPanel);
        newEmailEditText = view.findViewById(R.id.editTextNewEmail);
        passwordEditText = view.findViewById(R.id.editTextPassword);
        changeEmailButton = view.findViewById(R.id.buttonChangeEmail);
        changeEmailButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onClick(View view) {
                String newEmail = newEmailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                if(newEmail.length() > 0 && password.length() > 0) {
                    loadingPanel.setVisibility(View.VISIBLE);
                    changeEmailViewModel.changeEmail(newEmail, password).observe(getViewLifecycleOwner(), isSuccess -> {
                        if(isSuccess) {
                            getActivity().onBackPressed();
                        }
                        loadingPanel.setVisibility(View.GONE);
                    });

                    try {
                        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    } catch (Exception e) {

                    }
                    newEmailEditText.clearFocus();
                    passwordEditText.clearFocus();
                }
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
    }
}
