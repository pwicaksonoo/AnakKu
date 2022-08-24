package com.example.anakku;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.anakku.viewmodels.ChangePassViewModel;

public class ChangePassFragment extends Fragment {
    private ChangePassViewModel changePassViewModel;

    private EditText currentPasswordEditText;
    private EditText newPasswordEditText;
    private EditText confirmPasswordEditText;
    private Button changePasswordButton;
    private RelativeLayout loadingPanel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        changePassViewModel = new ViewModelProvider(this).get(ChangePassViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_pass, container, false);

        loadingPanel = view.findViewById(R.id.loadingPanel);
        currentPasswordEditText = view.findViewById(R.id.editTextCurrentPassword);
        newPasswordEditText = view.findViewById(R.id.editTextNewPassword);
        confirmPasswordEditText = view.findViewById(R.id.editTextConfirmPassword);
        changePasswordButton = view.findViewById(R.id.buttonChangePassword);

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentPass = currentPasswordEditText.getText().toString();
                String newPass = newPasswordEditText.getText().toString();
                String confirmPass = confirmPasswordEditText.getText().toString();

                if(currentPass.length() > 0 && newPass.length() > 0 && confirmPass.length() > 0) {
                    if(newPass.equals(confirmPass)) {
                        loadingPanel.setVisibility(View.VISIBLE);

                        changePassViewModel.changePassword(currentPass, newPass).observe(getViewLifecycleOwner(), isSuccess -> {
                            if(isSuccess) {
                                getActivity().onBackPressed();
                            }
                            loadingPanel.setVisibility(View.GONE);
                        });
                    } else Toast.makeText(getContext(), "Kata sandi dan konfirmasi harus sama", Toast.LENGTH_SHORT);
                }

                try {
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                } catch (Exception e) {

                }
                currentPasswordEditText.clearFocus();
                newPasswordEditText.clearFocus();
                confirmPasswordEditText.clearFocus();
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
