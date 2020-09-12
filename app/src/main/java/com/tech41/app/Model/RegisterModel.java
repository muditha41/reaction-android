package com.tech41.app.Model;

public class RegisterModel {


    public String Username ;
    public String Password;

    public String Email ;





    public RegisterModel(String username, String email, String password) {
        Username = username;
        Password = password;
        Email = email;

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

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }


}
