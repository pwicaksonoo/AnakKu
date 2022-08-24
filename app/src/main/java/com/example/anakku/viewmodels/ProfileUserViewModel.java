package com.example.anakku.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.anakku.models.User;
import com.example.anakku.repositories.AuthRepository;
import com.example.anakku.settings.SharedPref;
import com.google.firebase.auth.FirebaseUser;

public class ProfileUserViewModel extends AndroidViewModel {
    private AuthRepository authRepository;

    private MutableLiveData<FirebaseUser> firebaseUserMutableLiveData;
    private MutableLiveData<User> userMutableLiveData;

    public ProfileUserViewModel(@NonNull Application application) {
        super(application);
        authRepository = new AuthRepository(application);
        firebaseUserMutableLiveData = authRepository.getFirebaseUserMutableLiveData();
        userMutableLiveData = authRepository.getUserMutableLiveData();
    }

    public MutableLiveData<FirebaseUser> getFirebaseUserMutableLiveData() {
        return firebaseUserMutableLiveData;
    }

    public MutableLiveData<User> getUserMutableLiveData() {
        return userMutableLiveData;
    }

    public void logOut() {
        authRepository.logOut();
        SharedPref.write(SharedPref.ACTIVE_USER, "NULL");
        SharedPref.write(SharedPref.ACTIVE_CHILD, "NULL");
    }

    public void stopProfileUserListener() {
        authRepository.stopUserListener();
    }
}
