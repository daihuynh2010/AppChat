package com.example.appchat.register;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.lifecycle.Observer;

import com.example.appchat.Constant;
import com.example.appchat.MainActivity;
import com.example.appchat.R;
import com.example.appchat.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;


public class RegisterActivity extends BaseActivity {
    private static final String TAG= "FireBase Register";

    @BindView(R.id.email) EditText edEmail;
    @BindView(R.id.password) EditText edPass;
    @BindView(R.id.displayName) EditText edDisplayName;

    RegisterViewModel registerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        registerViewModel = RegisterViewModel.of(RegisterActivity.this);

        registerViewModel.get().observe(this, new Observer<RegisterModel>() {
            @Override
            public void onChanged(RegisterModel registerModel) {
                onRegisterModelChane(registerModel);
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.register;
    }

    @OnClick(R.id.btnRegister)
    public void handleRegister() {
        String email = edEmail.getText().toString();
        String pass = edPass.getText().toString();
        String displayName = edDisplayName.getText().toString();

        registerViewModel.Regis(email,pass, displayName);
    }

    private void onRegisterModelChane(RegisterModel registerModel) {
        if (registerModel == null) {
            return;
        }

        Toast.makeText(RegisterActivity.this, registerModel.getMessage(),
                Toast.LENGTH_SHORT).show();
        if (registerModel.getStatus() == Constant.STATUS_SUCCESS){
            Intent intent = new Intent(getApplication(), MainActivity.class);
            startActivity(intent);
        }
    }
}
