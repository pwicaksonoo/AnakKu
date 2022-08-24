package com.example.anakku.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.anakku.models.Article;
import com.example.anakku.repositories.ArticleRepository;

public class ArtikelViewModel extends AndroidViewModel{
    private ArticleRepository articleRepository;
    private MutableLiveData<Article> articleMutableLiveData;

    public ArtikelViewModel(@NonNull Application application, String documentId) {
        super(application);

        articleRepository = new ArticleRepository(application);
        articleMutableLiveData = articleRepository.getArticleMutableLiveData(documentId);
    }

    public MutableLiveData<Article> getArticleMutableLiveData() {
        return articleMutableLiveData;
    }

    public void stopArtikelListener() {
        articleRepository.stopArtikelListener();
    }
}
