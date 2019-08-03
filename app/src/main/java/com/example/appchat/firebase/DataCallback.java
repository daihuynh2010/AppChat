package com.example.appchat.firebase;

public interface DataCallback<T> {
    void onData(T value);
    void onError(String errorMsg);
}
