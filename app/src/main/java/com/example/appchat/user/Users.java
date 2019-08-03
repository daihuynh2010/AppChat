package com.example.appchat.user;

public class Users {
    public int id;
    public String displayName;
    public String email;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void User(int _id, String _displayName, String _email){
        this.setId(_id);
        this.setDisplayName(_displayName);
        this.setEmail(_email);
    }
}
