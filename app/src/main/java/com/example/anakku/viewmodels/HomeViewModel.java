package com.example.anakku.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.anakku.models.ActivityAnak;
import com.example.anakku.models.Child;
import com.example.anakku.repositories.ChildRepository;
import com.example.anakku.settings.SharedPref;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {
    private MutableLiveData<List<ActivityAnak>> activityAnakMutableLiveData;
    private MutableLiveData<Child> child;

    private ChildRepository childRepository;

    public HomeViewModel(@NonNull Application application) {
        super(application);

        childRepository = new ChildRepository(application);
        child = childRepository.getChild();
        activityAnakMutableLiveData = childRepository.getActivityAnakListMutableLiveData("created", 4);

    }

    public MutableLiveData<Child> getChild() { return child; }
    public void switchChild() {
        child = childRepository.getChild();
    }

    public MutableLiveData<List<ActivityAnak>> getActivityAnakMutableLiveData() {
        if(!SharedPref.read(SharedPref.ACTIVE_CHILD, "NULL").equals("NULL")) {
            childRepository.stopActivityAnakListener();
            activityAnakMutableLiveData = childRepository.getActivityAnakListMutableLiveData("created", 4);
        }
        return activityAnakMutableLiveData;
    }

    public void updateUserDocumentIdSharedPref() {
        childRepository.updateUserDocumentIdSharedPref();
    }

    public void stopHomeListener() {
        childRepository.stopChildListener();
        childRepository.stopActivityAnakListener();
    }
}
