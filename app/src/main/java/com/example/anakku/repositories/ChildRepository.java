package com.example.anakku.repositories;

import android.app.Application;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;

import com.example.anakku.models.ActivityAnak;
import com.example.anakku.models.Child;
import com.example.anakku.models.Immunization;
import com.example.anakku.models.User;
import com.example.anakku.settings.SharedPref;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Timestamp;
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
    private MutableLiveData<List<Child>> childListMutableLiveData;
    private ListenerRegistration childListener;
    private ListenerRegistration childsListener;

    private MutableLiveData<List<ActivityAnak>> activityAnakListMutableLiveData;
    private ListenerRegistration activityAnakListener;

    private MutableLiveData<Immunization> immunizationMutableLiveData;
    private ListenerRegistration immunizationListener;

    public ChildRepository(Application application) {
        this.application = application;

        this.firebaseAuth = FirebaseAuth.getInstance();
        this.db = FirebaseFirestore.getInstance();
        this.userMutableLiveData = new MutableLiveData<>();
        this.child = new MutableLiveData<>();
        this.childListMutableLiveData = new MutableLiveData<>();
//        populateChild();

        this.activityAnakListMutableLiveData = new MutableLiveData<>();
        this.immunizationMutableLiveData = new MutableLiveData<>();

        this.childListener = null;
        this.childsListener = null;
        this.activityAnakListener = null;
        this.immunizationListener = null;
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public MutableLiveData<Boolean> registerChild(String nama, String jenisKelamin, Date tanggalLahir) {
        MutableLiveData<Boolean> isSuccess = new MutableLiveData<>();

        Child newChild = new Child(firebaseAuth.getCurrentUser().getUid(), nama, jenisKelamin, tanggalLahir);
        db.collection("childs").add(newChild).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                child.postValue(newChild);
                Toast.makeText(application, "Child Registration Success", Toast.LENGTH_SHORT).show();
                SharedPref.write(SharedPref.ACTIVE_CHILD, documentReference.getId());
                isSuccess.postValue(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(application, "Child Registration Failed", Toast.LENGTH_SHORT).show();
                isSuccess.postValue(false);
            }
        });

        return isSuccess;
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
                            childListMutableLiveData.setValue(newChilds);
                            if(!newChilds.isEmpty()) {
                                child.postValue(newChilds.get(0));
                                SharedPref.write(SharedPref.ACTIVE_CHILD, newChilds.get(0).getDocumentId());
                            }
                            else child.postValue(null);
                        }
                    }
                });
    }

    public MutableLiveData<Child> getChild() {
        String activeChild = SharedPref.read(SharedPref.ACTIVE_CHILD, "NULL");

        if(childListener != null) {
            childListener.remove();
            childListener = null;
        }

        childListener = db.collection("childs")
                .whereEqualTo("uid", firebaseAuth.getCurrentUser().getUid())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if(queryDocumentSnapshots.size() == 0) {
                            child.postValue(null);
                            SharedPref.write(SharedPref.ACTIVE_CHILD, "NULL");
                        } else {
                            boolean notFound = true;

                            for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                                if (doc != null && (activeChild.equals("NULL") || doc.getId().equals(activeChild))) {
                                    child.postValue(doc.toObject(Child.class));
                                    SharedPref.write(SharedPref.ACTIVE_CHILD, doc.getId());
                                    notFound = false;
                                    break;
                                }
                            }

                            if(notFound) {
                                for(QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                                    if(doc != null) {
                                        child.postValue(doc.toObject(Child.class));
                                        SharedPref.write(SharedPref.ACTIVE_CHILD, doc.getId());
                                        break;
                                    }
                                }
                            }
                        }
                    }
                });

        return child;
    }

    public void stopChildListener() {
        if(childListener != null) {
            childListener.remove();
            childListener = null;
        }
    }

    public MutableLiveData<List<Child>> getChildListMutableLiveData() {
        String uid = firebaseAuth.getCurrentUser().getUid();

        childsListener = db.collection("childs")
                .whereEqualTo("uid", uid)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        List<Child> childList = new ArrayList<>();
                        for(QueryDocumentSnapshot doc : value) {
                            if(doc != null) {
                                childList.add(doc.toObject(Child.class));
                            }
                        }

                        childListMutableLiveData.postValue(childList);
                    }
                });

        return childListMutableLiveData;
    }

    public void stopChildsListener() {
        if(childsListener != null) {
            childsListener.remove();
            childsListener = null;
        }
    }

    public MutableLiveData<List<ActivityAnak>> getActivityAnakListMutableLiveData() {
        activityAnakListener =  db.collection("childs").document(SharedPref.read(SharedPref.ACTIVE_CHILD, "NULL"))
                .collection("activities").orderBy("created").addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        List<ActivityAnak> activityAnakList = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            if (doc != null) {
                                activityAnakList.add(doc.toObject(ActivityAnak.class));
                            }
                        }

                        for (ActivityAnak aa : activityAnakList) {
                            Log.d("Activity", "aktifitas " + aa.getName());
                        }
                        activityAnakListMutableLiveData.postValue(activityAnakList);
                    }
                });
        return activityAnakListMutableLiveData;
    }

    public MutableLiveData<List<ActivityAnak>> getActivityAnakListMutableLiveData(String orderBy, Integer limit) {
        activityAnakListener =  db.collection("childs")
                .document(SharedPref.read(SharedPref.ACTIVE_CHILD, "NULL"))
                .collection("activities")
                .orderBy(orderBy, Query.Direction.DESCENDING)
                .limit(limit)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        List<ActivityAnak> activityAnakList = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            if (doc != null) {
                                activityAnakList.add(doc.toObject(ActivityAnak.class));
                            }
                        }
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

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void addNewActivity(String name, Timestamp created) {
        ActivityAnak newActivity = new ActivityAnak(name, created);
        db.collection("childs")
                .document(SharedPref.read(SharedPref.ACTIVE_CHILD, "NULL"))
                .collection("activities")
                .add(newActivity);
    }

    public void updateUserDocumentIdSharedPref() {
        db.collection("users")
                .whereEqualTo("uid", firebaseAuth.getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            String userDocumentId = task.getResult().getDocuments().get(0).getId();
                            SharedPref.write(SharedPref.ACTIVE_USER, userDocumentId);
                        }
                    }
                });
    }

    public MutableLiveData<Immunization> getImmunizationMutableLiveData() {
        immunizationListener = db.collection("immunizations")
                .whereEqualTo("childDocumentId", SharedPref.read(SharedPref.ACTIVE_CHILD, "NULL"))
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        for(QueryDocumentSnapshot doc : value) {
                            if(doc != null) {
                                Immunization immunization = doc.toObject(Immunization.class);
                                immunizationMutableLiveData.postValue(immunization);
                                break;
                            }
                        }
                    }
                });

        return immunizationMutableLiveData;
    }

    public void stopImmunizationListener()
    {
        if(immunizationListener != null) {
            immunizationListener.remove();
            immunizationListener = null;
        }
    }

    public void updateImmunizationData(Immunization immunization) {
        db.collection("immunizations")
                .document(immunization.getDocumentId())
                .set(immunization);
    }
}
