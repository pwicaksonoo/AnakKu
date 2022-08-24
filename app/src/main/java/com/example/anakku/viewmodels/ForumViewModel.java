package com.example.anakku.viewmodels;

import android.app.Application;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.anakku.models.Article;
import com.example.anakku.models.Discussion;
import com.example.anakku.models.User;
import com.example.anakku.repositories.ArticleRepository;
import com.example.anakku.utils.PairLiveData;

import java.sql.Timestamp;
import java.util.List;

public class ForumViewModel extends AndroidViewModel {
    private ArticleRepository articleRepository;
    private MutableLiveData<Article> articleMutableLiveData;
    private MutableLiveData<List<Discussion>> discussionListMutableLiveData;

    private String articleDocumentId;

    public ForumViewModel(@NonNull Application application, String documentId) {
        super(application);

        articleRepository = new ArticleRepository(application);
        articleDocumentId = documentId;
        articleMutableLiveData = articleRepository.getArticleMutableLiveData(documentId);
        discussionListMutableLiveData = articleRepository.getDiscussionListMutableLiveData(articleDocumentId);
    }

    public MutableLiveData<Article> getArticleMutableLiveData() {
        return articleMutableLiveData;
    }

    public MutableLiveData<List<Discussion>> getDiscussionListMutableLiveData() {
        return discussionListMutableLiveData;
    }

    public void stopDiscussionListener() {
        articleRepository.stopDiscussionListener();
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void addNewDiscussion(String text, Timestamp created) {
        articleRepository.addNewDiscussion(articleDocumentId, text, created);
    }
}
