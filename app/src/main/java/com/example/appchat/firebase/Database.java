package com.example.appchat.firebase;

public interface Database<T> {
    void writeData(T t, String node, DataCallback callback);
    void GetData(String node, DataCallback callback);
}
