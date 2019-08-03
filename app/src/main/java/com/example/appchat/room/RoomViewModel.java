package com.example.appchat.room;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.example.appchat.Constant;
import com.example.appchat.firebase.ChildEvent;
import com.example.appchat.firebase.DataCallback;
import com.example.appchat.firebase.Database;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RoomViewModel extends ViewModel implements Database<MessageModel> {
    public static RoomViewModel of(FragmentActivity fragmentActivity) {
        return ViewModelProviders.of(fragmentActivity).get(RoomViewModel.class);
    }
    DatabaseReference database =  FirebaseDatabase.getInstance().getReference();


    @Override
    public void writeData(MessageModel messageModel, String node, final DataCallback callback) {
        database.child(Constant.NodeMessage)
                .child(node)
                .push()
                .setValue(messageModel)
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
    public void GetData( String node, final DataCallback callback) {
        database.child(Constant.NodeMessage).child(node).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<MessageModel> listMessage = new ArrayList<MessageModel>();
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    MessageModel mess = childSnapshot.getValue(MessageModel.class);
                    listMessage.add(mess);
                }
                callback.onData(listMessage);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                callback.onError(error.getMessage());
            }
        });
    }

    public void GetNewData( String node, long StartAt, final DataCallback callback) {
        database.child(Constant.NodeMessage).child(node).orderByChild("timestamp").addChildEventListener(new ChildEvent(){
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                MessageModel mess = dataSnapshot.getValue(MessageModel.class);
                callback.onData(mess);
            }
        });
    }
}
