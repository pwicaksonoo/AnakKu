package com.example.anakku.repositories;

import android.app.Application;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;

import com.example.anakku.models.Child;
import com.example.anakku.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChildRepository {
    private Application application;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    private MutableLiveData<FirebaseUser> userMutableLiveData;
    private MutableLiveData<User> user;
    private MutableLiveData<Child> child;
    private MutableLiveData<ArrayList<Child>> childs;

    public ChildRepository(Application application) {
        this.application = application;

        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userMutableLiveData = new MutableLiveData<>();
        child = new MutableLiveData<>();
        childs = new MutableLiveData<>();
        populateChild();
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void registerChild(String nama, String jenisKelamin, Date tanggalLahir) {
        Child newChild = new Child(firebaseAuth.getCurrentUser().getUid(), nama, jenisKelamin, tanggalLahir);
        db.collection("childs").add(newChild).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                child.postValue(newChild);
                Toast.makeText(application, "Child Registration Success", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(application, "Child Registration Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void populateChild() {
        db.collection("childs").
                whereEqualTo("uid", firebaseAuth.getCurrentUser().getUid())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            ArrayList<Child> newChilds = new ArrayList<>();
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                newChilds.add(document.toObject(Child.class));
                            }
                            childs.setValue(newChilds);
                            if(!newChilds.isEmpty()) child.postValue(newChilds.get(0));
                            else child.postValue(null);
                        }
                    }
                });
    }

    public MutableLiveData<Child> getChild() { return child;}

    public MutableLiveData<ArrayList<Child>> getChilds() {
        return childs;
    }
}
