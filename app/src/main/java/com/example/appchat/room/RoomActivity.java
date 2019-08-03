package com.example.appchat.room;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appchat.R;
import com.example.appchat.base.BaseActivity;
import com.example.appchat.firebase.DataCallback;
import com.example.appchat.friend.FriendAcitivity;
import com.example.appchat.friend.FriendViewModel;
import com.example.appchat.user.Users;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class RoomActivity extends BaseActivity {

    @BindView(R.id.mess) EditText etMessage;
    @BindView(R.id.btnSend) Button btnSend;
    @BindView(R.id.lisMessage) RecyclerView rvListMessage;

    RoomViewModel roomViewModel;
    FriendViewModel friendViewModel;
    RoomAdapter roomAdapter;

    String room="";
    String name= "";
    List<MessageModel> listMessage = new ArrayList<MessageModel>();
    Users myInfo = new Users();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        room = getIntent().getStringExtra(FriendAcitivity.IDRoom);
        name = getIntent().getStringExtra(FriendAcitivity.PatnerName);
        setTitle(name);

        friendViewModel = FriendViewModel.of(this);
        friendViewModel.GetMyInfo(new DataCallback<Users>() {
            @Override
            public void onData(Users value) {
                myInfo = value;
            }

            @Override
            public void onError(String errorMsg) {

            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplication());
        rvListMessage.setLayoutManager(linearLayoutManager);
        roomAdapter = new RoomAdapter(listMessage, myInfo.getId(),getApplication());
        rvListMessage.setAdapter(roomAdapter);

        roomViewModel = RoomViewModel.of(this);
        roomViewModel.GetData(room, new DataCallback<List<MessageModel>>() {
            @Override
            public void onData(List<MessageModel> value) {
                listMessage = value;
                roomAdapter.setData(listMessage);
                if (value.size() > 0) {
                    rvListMessage.scrollToPosition(roomAdapter.getItemCount() - 1);
                }
                long startAt = value.size() == 0 ? 0 : value.get(value.size()-1).getTime();
                newMessage(roomViewModel,room, startAt);
            }

            @Override
            public void onError(String errorMsg) {
                Toast.makeText(getApplication() , errorMsg,
                        Toast.LENGTH_LONG)
                        .show();
            }
        });
        Toast.makeText(getApplication() , "My room: "+ room,
                Toast.LENGTH_LONG)
                .show();
    }

    @OnClick(R.id.btnSend)
    public void handleSend(){
        String content = etMessage.getText().toString();
        MessageModel messageModel = new MessageModel();
        messageModel.setContent(content);
        messageModel.setOwner(myInfo);
        roomViewModel.writeData(messageModel, room, new DataCallback<List<MessageModel>>() {
            @Override
            public void onData(List<MessageModel> value) {
                etMessage.setText("");
                long startAt = value.size() == 0 ? 0 : value.get(value.size()-1).getTime();
                newMessage(roomViewModel,room, startAt);
            }

            @Override
            public void onError(String errorMsg) {

            }
        });
    }

    public void newMessage(RoomViewModel roomViewModel, String node, long startAt){
        roomViewModel.GetNewData(node, startAt, new DataCallback<MessageModel>() {
            @Override
            public void onData(MessageModel value) {
                roomAdapter.noDataChange(value);
            }

            @Override
            public void onError(String errorMsg) {

            }
        });
    }
    @Override
    protected int getLayoutId() {
        return R.layout.room_activity;
    }
}
