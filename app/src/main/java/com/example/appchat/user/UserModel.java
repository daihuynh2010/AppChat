package com.example.appchat.user;

import androidx.annotation.NonNull;

import com.example.appchat.Constant;
import com.example.appchat.firebase.DataCallback;
import com.example.appchat.firebase.Database;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserModel implements Database<Users> {
    DatabaseReference database =  FirebaseDatabase.getInstance().getReference();

    @Override
    public void writeData(Users users, String node, final DataCallback callback) {
        String UID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if (UID == null) {
            return;
        }
        database.child(Constant.NodeUser)
                .child(UID)
                .setValue(users)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        callback.onData(null);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onError(e.getMessage());
                    }
                });
    }

    @Override
    public void GetData(String node, DataCallback callback) {

    }


}
