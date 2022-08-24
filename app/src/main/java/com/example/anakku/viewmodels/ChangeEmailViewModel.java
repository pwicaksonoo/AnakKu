package com.example.anakku.viewmodels;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.anakku.repositories.AuthRepository;

public class ChangeEmailViewModel extends AndroidViewModel {
    private AuthRepository authRepository;

    public ChangeEmailViewModel(@NonNull Application application) {
        super(application);

        authRepository = new AuthRepository(application);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public MutableLiveData<Boolean> changeEmail(String newEmail, String password) {
        return authRepository.changeEmail(newEmail, password);
    }
}
