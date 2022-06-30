package com.example.anakku.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.anakku.repositories.AuthRepository;
import com.google.firebase.auth.FirebaseUser;

public class RegisterChildViewModel extends AndroidViewModel {
    private AuthRepository authRepository;
    private MutableLiveData<FirebaseUser> userMutableLiveData;

    public RegisterChildViewModel(@NonNull Application application) {
        super(application);

        authRepository = new AuthRepository(application);
        userMutableLiveData = authRepository.getUserMutableLiveData();
    }

    public void logOut() {
        authRepository.logOut();
    }

    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return userMutableLiveData;
    }
}
