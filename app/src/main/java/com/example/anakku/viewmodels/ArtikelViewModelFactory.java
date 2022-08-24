package com.example.anakku.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ArtikelViewModelFactory implements ViewModelProvider.Factory {
    private Application application;
    private String documentId;

    public ArtikelViewModelFactory(Application application, String documentId) {
        this.application = application;
        this.documentId = documentId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ArtikelViewModel(application, documentId);
    }
}
