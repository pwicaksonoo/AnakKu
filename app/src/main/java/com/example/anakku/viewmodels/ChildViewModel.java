package com.example.anakku.viewmodels;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.anakku.models.Child;
import com.example.anakku.models.User;
import com.example.anakku.repositories.AuthRepository;
import com.example.anakku.repositories.ChildRepository;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Date;

public class ChildViewModel extends AndroidViewModel {
    private AuthRepository authRepository;
    private ChildRepository childRepository;
    private MutableLiveData<FirebaseUser> userMutableLiveData;
    private MutableLiveData<User> user;
    private MutableLiveData<Child> child;
    private MutableLiveData<ArrayList<Child>> childs;

    public ChildViewModel(@NonNull Application application) {
        super(application);

        authRepository = new AuthRepository(application);
        childRepository = new ChildRepository(application);
        userMutableLiveData = authRepository.getUserMutableLiveData();
        user = authRepository.getUser();
        child = childRepository.getChild();
        childs = childRepository.getChilds();
    }

    public void logOut() {
        authRepository.logOut();
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void registerChild(String nama, String jenisKelamin, Date tanggalLahir) {
        childRepository.registerChild(nama, jenisKelamin, tanggalLahir);
    }

    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return userMutableLiveData;
    }

    public MutableLiveData<Child> getChild() {
        return child;
    }

    public MutableLiveData<ArrayList<Child>> getChilds() {
        return childs;
    }
}
