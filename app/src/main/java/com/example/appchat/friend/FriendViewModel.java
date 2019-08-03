package com.example.appchat.friend;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.example.appchat.Constant;
import com.example.appchat.firebase.DataCallback;
import com.example.appchat.firebase.Database;
import com.example.appchat.user.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FriendViewModel extends ViewModel implements Database<Users> {
    public static FriendViewModel of(FragmentActivity fragmentActivity) {
        return ViewModelProviders.of(fragmentActivity).get(FriendViewModel.class);
    }
    DatabaseReference database =  FirebaseDatabase.getInstance().getReference();

    @Override
    public void writeData(Users users, String node, final DataCallback callback) {

    }

    @Override
    public void GetData( String node, final DataCallback callback) {
        final List<Users> listUsers = new ArrayList<Users>();
        database.child(Constant.NodeUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    Users user = childSnapshot.getValue(Users.class);
                    if(!childSnapshot.getKey().equals(FirebaseAuth.getInstance().getCurrentUser().getUid() ))
                        listUsers.add(user);
                }
                callback.onData(listUsers);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                callback.onError(error.getMessage());
            }
        });
    }

    public void GetMyInfo(final DataCallback callback) {
        database.child(Constant.NodeUser).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Users users = dataSnapshot.getValue(Users.class);
                callback.onData(users);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                callback.onError(error.getMessage());
            }
        });
    }
}
