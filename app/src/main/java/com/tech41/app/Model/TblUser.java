package com.tech41.app.Model;

import android.provider.ContactsContract;

public class TblUser {


    private String Username;
    private String Password;



    public TblUser() {
    }

    public TblUser(String username, String password ) {
        Username = username;
        Password = password;

    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }


}

