package com.example.anakku.repositories;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.example.anakku.ActivityAnakFragment;
import com.example.anakku.models.ActivityAnak;
import com.example.anakku.settings.SharedPref;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ActivityAnakRepository {
    MutableLiveData<List<ActivityAnak>> activityAnakListMutableLiveData;
    FirebaseFirestore mFirestore;
    MutableLiveData<ActivityAnak> activityAnakMutableLiveData;

    private ListenerRegistration activityAnakListener;

    public ActivityAnakRepository() {
        this.activityAnakListMutableLiveData = new MutableLiveData<>();
        mFirestore = FirebaseFirestore.getInstance();
        activityAnakMutableLiveData = new MutableLiveData<>();

        activityAnakListener = null;
    }

    public MutableLiveData<List<ActivityAnak>> getActivityAnakListMutableLiveData() {
//        Log.i("TAG", "getActivityAnakListMutableLiveData: ");
        activityAnakListener =  mFirestore.collection("childs").document(SharedPref.read(SharedPref.ACTIVE_CHILD, "NULL"))
                .collection("activities").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                List<ActivityAnak> activityAnakList = new ArrayList<>();
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    if (doc != null) {
                        activityAnakList.add(doc.toObject(ActivityAnak.class));
                    }
                }

//                for (ActivityAnak aa : activityAnakList) {
//                    Log.d("Activity", "aktifitas " + aa.getName());
//                }
                activityAnakListMutableLiveData.postValue(activityAnakList);
            }
        });
        return activityAnakListMutableLiveData;
    }

    public void stopActivityAnakListener() {
        if(activityAnakListener != null) {
            activityAnakListener.remove();
            activityAnakListener = null;
        }
    }
}
