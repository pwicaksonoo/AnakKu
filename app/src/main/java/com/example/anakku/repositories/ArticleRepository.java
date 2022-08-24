package com.example.anakku.repositories;

import android.app.Application;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;

import com.example.anakku.models.Article;
import com.example.anakku.models.Discussion;
import com.example.anakku.settings.SharedPref;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ArticleRepository {
    private Application application;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    private MutableLiveData<Article> articleMutableLiveData;
    private MutableLiveData<List<Discussion>> discussionListMutableLiveData;

    private ListenerRegistration articleListener;
    private ListenerRegistration discussionListener;

    public ArticleRepository(Application application) {
        this.application = application;
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        articleMutableLiveData = new MutableLiveData<>();
        discussionListMutableLiveData = new MutableLiveData<>();

        articleListener = null;
        discussionListener = null;
    }

    public MutableLiveData<Article> getArticleMutableLiveData(String documentId) {
        articleListener = db.collection("articles")
                .document(documentId)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(value != null) {
                            articleMutableLiveData.postValue(value.toObject(Article.class));
                        }
                    }
                });

        return articleMutableLiveData;
    }

    public void stopArtikelListener() {
        if(articleListener != null) {
            articleListener.remove();
            articleListener = null;
        }
    }

    public MutableLiveData<List<Discussion>> getDiscussionListMutableLiveData(String documentId) {
        discussionListener = db.collection("articles")
                .document(documentId)
                .collection("discussions")
                .orderBy("created")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        List<Discussion> discussionList = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : value) {
                            if(doc != null) discussionList.add(doc.toObject(Discussion.class));
                        }
                        discussionListMutableLiveData.postValue(discussionList);

                    }
                });

        return discussionListMutableLiveData;
    }

    public void stopDiscussionListener() {
        if(discussionListener != null) {
            discussionListener.remove();
            discussionListener = null;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void addNewDiscussion(String articleDocumentId, String text, Timestamp created) {
        Discussion newDiscussion = new Discussion(SharedPref.read(SharedPref.ACTIVE_USER, "NULL"), text, created);
        db.collection("articles")
                .document(articleDocumentId)
                .collection("discussions")
                .add(newDiscussion);
    }
}
