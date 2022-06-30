package com.example.anakku.viewmodels;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.anakku.repositories.AuthRepository;
import com.google.firebase.auth.FirebaseUser;

public class LoginViewModel extends AndroidViewModel {
    private AuthRepository authRepository;
    private MutableLiveData<FirebaseUser> userMutableLiveData;

    public LoginViewModel(@NonNull Application application) {
        super(application);

        authRepository = new AuthRepository(application);
        userMutableLiveData = authRepository.getUserMutableLiveData();
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void login(String email, String password) {
        authRepository.login(email, password);
    }

    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return userMutableLiveData;
    }
}
