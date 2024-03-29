package com.example.anakku.viewmodels;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.anakku.models.Child;
import com.example.anakku.repositories.AuthRepository;
import com.example.anakku.repositories.ChildRepository;
import com.google.firebase.auth.FirebaseUser;

import java.util.Date;
import java.util.List;

public class ChildViewModel extends AndroidViewModel {
    private AuthRepository authRepository;
    private ChildRepository childRepository;
    private MutableLiveData<FirebaseUser> userMutableLiveData;
    private MutableLiveData<Child> child;
    private MutableLiveData<List<Child>> childs;

    public ChildViewModel(@NonNull Application application) {
        super(application);

        authRepository = new AuthRepository(application);
        childRepository = new ChildRepository(application);
        userMutableLiveData = authRepository.getFirebaseUserMutableLiveData();
        child = childRepository.getChild();
        childs = childRepository.getChildListMutableLiveData();
    }

    public void logOut() {
        authRepository.logOut();
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public MutableLiveData<Boolean> registerChild(String nama, String jenisKelamin, Date tanggalLahir) {
        return childRepository.registerChild(nama, jenisKelamin, tanggalLahir);
    }

    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return userMutableLiveData;
    }

    public MutableLiveData<Child> getChild() {
        return child;
    }
    public void switchChild() {
        child = childRepository.getChild();
    }

}
