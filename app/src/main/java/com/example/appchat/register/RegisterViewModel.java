package com.example.appchat.register;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.example.appchat.Constant;
import com.example.appchat.firebase.DataCallback;
import com.example.appchat.user.UserModel;
import com.example.appchat.user.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Random;

public class RegisterViewModel extends ViewModel {
    private MutableLiveData<RegisterModel> register = new MutableLiveData<>();
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    public static RegisterViewModel of(FragmentActivity fragmentActivity) {
        return ViewModelProviders.of(fragmentActivity).get(RegisterViewModel.class);
    }

    public LiveData<RegisterModel> get() {
        return register;
    }

    public void Regis(final String userName, String pass, final String displayName){
        final RegisterModel registerModel = new RegisterModel();

        if(userName == null || pass == null || displayName == null ||
           userName.isEmpty() || pass.isEmpty() || displayName.isEmpty()){

            registerModel.setStatus(Constant.STATUS_ERROR_VALI);
            registerModel.setMessage("Tên, Email, Pasword không được rỗng");
            register.setValue(registerModel);
            return;
        }

        auth.createUserWithEmailAndPassword(userName, pass)
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (! task.isSuccessful()) {
                            registerModel.setStatus(Constant.STATUS_ERROR);
                            registerModel.setMessage("Lỗi: "+task.getException());
                            register.setValue(registerModel);
                            return;
                        }

                        registerUser(registerModel, displayName, userName);
                    }
                })
                .addOnFailureListener( new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        registerModel.setStatus(Constant.STATUS_ERROR);
                        registerModel.setMessage("Error: "+e.getMessage());
                        register.setValue(registerModel);
                    }
                });

    }

    void registerUser(RegisterModel registerModel, String displayName, String UserName){
        Users user = new Users();
        user.User(genUserId(), displayName, UserName);

        UserModel userModel = new UserModel();
        userModel.writeData(user, null, new DataCallback() {
            @Override
            public void onData(Object value) {
                register.setValue(buildRegisterModel(
                        Constant.STATUS_SUCCESS,
                        "Đăng ký thành công"));
            }

            @Override
            public void onError(String errorMsg) {
                register.setValue(buildRegisterModel(
                        Constant.STATUS_ERROR,
                        "Đăng ký that bai"));
            }
        });
    }

    private RegisterModel buildRegisterModel(int code, String message) {
        RegisterModel registerModel = new RegisterModel();
        registerModel.setStatus(code);
        registerModel.setMessage(message);
        return registerModel;
    }

    private int genUserId() {
        return new Random().nextInt(1000000);
    }

    public void login(String userName, String pass){
        final RegisterModel registerModelLogin = new RegisterModel();

        if (userName == null || pass == null ||
            userName.isEmpty() || pass.isEmpty()) {

            registerModelLogin.setStatus(Constant.STATUS_ERROR_VALI);
            registerModelLogin.setMessage("Email, Pasword không được rỗng");
            register.setValue(registerModelLogin);
            return;
        }

        auth.signInWithEmailAndPassword(userName, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                register.setValue(buildRegisterModel(
                                        Constant.STATUS_SUCCESS,
                                        "Đăng nhập thành công"
                                        ));
                                return;
                            }

                            register.setValue(buildRegisterModel(
                                    Constant.STATUS_ERROR,
                                    task.getException().getMessage()
                            ));
                        }
                })
                .addOnFailureListener( new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            register.setValue(buildRegisterModel(
                                    Constant.STATUS_ERROR,
                                    e.getMessage()
                            ));
                        }
                    });
    }
}
