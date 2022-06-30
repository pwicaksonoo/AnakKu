package com.example.anakku.viewmodels;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.anakku.models.User;
import com.example.anakku.repositories.AuthRepository;
import com.google.firebase.auth.FirebaseUser;

public class RegisterViewModel extends AndroidViewModel {
    private AuthRepository authRepository;
    private MutableLiveData<FirebaseUser> userMutableLiveData;
    private MutableLiveData<User> user;

    public RegisterViewModel(@NonNull Application application) {
        super(application);

        authRepository = new AuthRepository(application);
        userMutableLiveData = authRepository.getUserMutableLiveData();
        user = authRepository.getUser();
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void register(String nama, String email, String password) {
        authRepository.register(nama, email, password);
    }

    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return userMutableLiveData;
    }

    public MutableLiveData<User> getUser() { return user; }
}
