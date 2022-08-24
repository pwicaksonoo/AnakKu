package com.example.anakku.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.anakku.models.Immunization;
import com.example.anakku.repositories.ChildRepository;

public class ImmunizationViewModel extends AndroidViewModel {
    private ChildRepository childRepository;

    private MutableLiveData<Immunization> immunizationMutableLiveData;

    public ImmunizationViewModel(@NonNull Application application) {
        super(application);

        childRepository = new ChildRepository(application);
        immunizationMutableLiveData = childRepository.getImmunizationMutableLiveData();
    }

    public MutableLiveData<Immunization> getImmunizationMutableLiveData() {
        return immunizationMutableLiveData;
    }

    public void changeImmunizationStatus(Immunization immunization) {
        childRepository.updateImmunizationData(immunization);
    }

    public void stopImmunizationListener() {
        childRepository.stopImmunizationListener();
    }
}
