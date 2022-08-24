package com.example.anakku.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.anakku.models.Child;
import com.example.anakku.repositories.ChildRepository;

import java.util.List;

public class PickChildViewModel extends AndroidViewModel {
    private ChildRepository childRepository;

    private MutableLiveData<List<Child>> childListMutableLiveData;

    public PickChildViewModel(@NonNull Application application) {
        super(application);

        childRepository = new ChildRepository(application);
        childListMutableLiveData = childRepository.getChildListMutableLiveData();
    }

    public MutableLiveData<List<Child>> getChildListMutableLiveData() {
        return childListMutableLiveData;
    }

    public void stopChildsListener() {
        childRepository.stopChildsListener();
    }
}
