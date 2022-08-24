package com.example.anakku.repositories;

import android.app.Application;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;

import com.example.anakku.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;

public class AuthRepository {
    private Application application;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    private MutableLiveData<FirebaseUser> firebaseUserMutableLiveData;
    private MutableLiveData<User> userMutableLiveData;

    private ListenerRegistration userListener;

    public AuthRepository(Application application) {
        this.application = application;

        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        firebaseUserMutableLiveData = new MutableLiveData<>();
        userMutableLiveData = new MutableLiveData<>();

        userListener = null;
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void register(String nama, String email, String password, String jenisKelamin, Date tanggalLahir, Integer jumlahAnak) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(application.getMainExecutor(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            firebaseUserMutableLiveData.postValue(firebaseAuth.getCurrentUser());

                            User tempUser = new User(firebaseAuth.getCurrentUser().getUid(), nama, jenisKelamin, tanggalLahir, jumlahAnak);
                            db.collection("users").add(tempUser).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
//                                    Log.d("firestore", "onSuccess: " + documentReference.getId());
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
//                                    Log.d("firestore", "onFailure: ");
                                }
                            });

                        } else {
                            Toast.makeText(application, "Registeration Faild" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void login(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(application.getMainExecutor(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            firebaseUserMutableLiveData.postValue(firebaseAuth.getCurrentUser());
                        } else {
                            Toast.makeText(application, "Login Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void logOut() {
        firebaseAuth.signOut();
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public MutableLiveData<Boolean> changeEmail(String newEmail, String password) {
        MutableLiveData<Boolean> isSuccess = new MutableLiveData<>();

        AuthCredential credential = EmailAuthProvider.getCredential(firebaseAuth.getCurrentUser().getEmail(), password);

        firebaseAuth.getCurrentUser().reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            firebaseAuth.getCurrentUser().updateEmail(newEmail)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()) {
                                                firebaseUserMutableLiveData.postValue(firebaseAuth.getCurrentUser());
                                                Toast.makeText(application, "Email Successfully Updated", Toast.LENGTH_SHORT).show();
                                                isSuccess.postValue(true);
                                            } else {
                                                Toast.makeText(application, "Email Update Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                isSuccess.postValue(false);
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(application, "Email Update Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            isSuccess.postValue(false);
                        }
                    }
                });

        return isSuccess;
    }

    public MutableLiveData<Boolean> changePassword(String currentPass, String newPass) {
        MutableLiveData<Boolean> isSuccess = new MutableLiveData<>();

        AuthCredential credential = EmailAuthProvider.getCredential(firebaseAuth.getCurrentUser().getEmail(), currentPass);

        firebaseAuth.getCurrentUser().reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            firebaseAuth.getCurrentUser().updatePassword(newPass)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()) {
                                                firebaseUserMutableLiveData.postValue(firebaseAuth.getCurrentUser());
                                                Toast.makeText(application, "Password Successfully Updated", Toast.LENGTH_SHORT).show();
                                                isSuccess.postValue(true);
                                            } else {
                                                Toast.makeText(application, "Password Update Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                isSuccess.postValue(false);
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(application, "Password Update Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            isSuccess.postValue(false);
                        }
                    }
                });


        return isSuccess;
    }

    public MutableLiveData<FirebaseUser> getFirebaseUserMutableLiveData() {
        if(firebaseAuth.getCurrentUser() != null) firebaseUserMutableLiveData.postValue(firebaseAuth.getCurrentUser());
        return firebaseUserMutableLiveData;
    }

    public MutableLiveData<User> getUserMutableLiveData() {
        if(firebaseAuth.getCurrentUser() != null) {
            userListener = db.collection("users")
                    .whereEqualTo("uid", firebaseAuth.getCurrentUser().getUid())
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            for(QueryDocumentSnapshot doc : value) {
                                if(doc != null) {
                                    userMutableLiveData.postValue(doc.toObject(User.class));
                                    break;
                                }
                            }
                        }
                    });
        }

        return userMutableLiveData;
    }

    public void stopUserListener() {
        if(userListener != null) {
            userListener.remove();
            userListener = null;
        }
    }
}
