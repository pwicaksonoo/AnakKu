package com.example.anakku.viewmodels;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;

import com.example.anakku.models.ActivityAnak;
import com.example.anakku.repositories.ActivityAnakRepository;
import com.example.anakku.repositories.ChildRepository;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Timestamp;
import java.util.List;

public class ActivityAnakViewModel extends AndroidViewModel {
    private MutableLiveData<List<ActivityAnak>> activityAnakListMutableLiveData;
    private ChildRepository childRepository;

    public ActivityAnakViewModel(@NonNull Application application) {
        super(application);

        childRepository = new ChildRepository(application);
        activityAnakListMutableLiveData = childRepository.getActivityAnakListMutableLiveData();
    }

    public MutableLiveData<List<ActivityAnak>> getActivityAnakListMutableLiveData() {
        return activityAnakListMutableLiveData;
    }

    public void stopActivityAnakListener() {
        childRepository.stopActivityAnakListener();
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void addNewActivity(String name, Timestamp created) {
        childRepository.addNewActivity(name, created);
    }
}
