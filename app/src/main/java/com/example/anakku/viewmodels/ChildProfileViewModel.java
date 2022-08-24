package com.example.anakku.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.anakku.models.Child;
import com.example.anakku.repositories.ChildRepository;

public class ChildProfileViewModel extends AndroidViewModel {
    private MutableLiveData<Child> child;
    
    private ChildRepository childRepository;
    
    public ChildProfileViewModel(@NonNull Application application) {
        super(application);
        childRepository = new ChildRepository(application);
        child = childRepository.getChild();
    }

    public MutableLiveData<Child> getChild() { return child; }

    public void stopChildProfileListener() {
        childRepository.stopChildListener();
    }
}
