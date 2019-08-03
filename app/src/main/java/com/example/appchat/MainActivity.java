package com.example.appchat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appchat.friend.FriendAcitivity;
import com.example.appchat.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {
    TextView tvUserName;
    Button btnLogout;
    Button btnFriend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitView();

        if(FirebaseAuth.getInstance().getCurrentUser() == null) {
            // Start sign in/sign up activity
           Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this,
                    "Welcome " + FirebaseAuth.getInstance()
                            .getCurrentUser()
                            .getEmail(),
                    Toast.LENGTH_LONG)
                    .show();

            displayChatMessages();
        }

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btnFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FriendAcitivity.class);
                startActivity(intent);
            }
        });
    }

    private void displayChatMessages() {
        String user = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        tvUserName.setText(user);
    }

    private void InitView(){
        tvUserName = findViewById(R.id.username);
        btnLogout = findViewById(R.id.btnLogout);
        btnFriend = findViewById(R.id.btnfriend);
    }

}
