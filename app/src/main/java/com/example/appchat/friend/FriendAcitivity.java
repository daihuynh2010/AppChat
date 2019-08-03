package com.example.appchat.friend;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appchat.R;
import com.example.appchat.base.BaseActivity;
import com.example.appchat.firebase.DataCallback;
import com.example.appchat.room.RoomActivity;
import com.example.appchat.user.Users;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnItemClick;

public class FriendAcitivity extends BaseActivity {
    @BindView(R.id.lvFriend) ListView lvFriend;

    FriendViewModel friendViewModel;
    List<Users> listFriend = new ArrayList<Users>();
    public static String IDRoom = "RoomID";
    public static String PatnerName = "PatnerName";
    int myID=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        friendViewModel = FriendViewModel.of(this);
        friendViewModel.GetData("",new DataCallback<List<Users>>() {
            @Override
            public void onData(List<Users> value) {
                listFriend = value;
                FriendAdapter friendAdapter = new FriendAdapter(getApplication(), R.layout.item_friend, listFriend);
                lvFriend .setAdapter(friendAdapter);
            }

            @Override
            public void onError(String errorMsg) {
                Toast.makeText(getApplication() , errorMsg,
                        Toast.LENGTH_LONG)
                        .show();
            }
        });

        friendViewModel.GetMyInfo(new DataCallback<Users>() {
            @Override
            public void onData(Users value) {
                myID = value.id;
            }

            @Override
            public void onError(String errorMsg) {

            }
        });

        lvFriend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int parnerID =  listFriend.get(i).getId();
                String Name= listFriend.get(i).getDisplayName();
                String room = BuildRoom(myID, parnerID);
                Intent intent = new Intent(getApplication(), RoomActivity.class);
                intent.putExtra(IDRoom, room);
                intent.putExtra(PatnerName, Name);
                startActivity(intent);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.list_friend;
    }

    private String BuildRoom(int myID, int partnerID){
        if(myID > partnerID)
            return partnerID + "-" + myID;
        return myID + "-" + partnerID;
    }
}
