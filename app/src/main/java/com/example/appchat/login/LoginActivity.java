package com.example.appchat.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.lifecycle.Observer;

import com.example.appchat.Constant;
import com.example.appchat.MainActivity;
import com.example.appchat.R;
import com.example.appchat.base.BaseActivity;
import com.example.appchat.register.RegisterActivity;
import com.example.appchat.register.RegisterModel;
import com.example.appchat.register.RegisterViewModel;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    private static final String TAG= "FireBase Login";

    @BindView(R.id.login_email) EditText edEmail;
    @BindView(R.id.login_password) EditText edPass;

    private RegisterViewModel registerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        registerViewModel = RegisterViewModel.of(LoginActivity.this);
        registerViewModel.get().observe(this, new Observer<RegisterModel>() {
            @Override
            public void onChanged(RegisterModel registerModel) {
                onRegisterModelChane(registerModel);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.login;
    }

    @OnClick(R.id.btnLogin)
    public void handleLogin() {
        String email = edEmail.getText().toString();
        String pass = edPass.getText().toString();
        registerViewModel.login(email,pass);
    }

    @OnClick(R.id.btnSignUp)
    public void handleRegister() {
        Intent intent = new Intent(getApplication(), RegisterActivity.class);
        startActivity(intent);
    }

    private void onRegisterModelChane(RegisterModel registerModel) {
        if (registerModel == null) {
            return;
        }

        Toast.makeText(LoginActivity.this, registerModel.getMessage(),
                Toast.LENGTH_SHORT).show();
        if (registerModel.getStatus() == Constant.STATUS_SUCCESS){
            Intent intent = new Intent(getApplication(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
