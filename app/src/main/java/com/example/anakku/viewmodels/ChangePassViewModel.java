package com.example.anakku.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.anakku.repositories.AuthRepository;

public class ChangePassViewModel extends AndroidViewModel {
    private AuthRepository authRepository;

    public ChangePassViewModel(@NonNull Application application) {
        super(application);

        authRepository = new AuthRepository(application);
    }

    public MutableLiveData<Boolean> changePassword(String currentPass, String newPass) {
        return authRepository.changePassword(currentPass, newPass);
    }
}
