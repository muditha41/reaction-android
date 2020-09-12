package com.tech41.app.JWT;

import android.content.Context;
import android.content.SharedPreferences;

public class TokenManager {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    Context context;
    int privatemode = 0;
    private static final String PREF_NAME="JWTTOKEN";
    private static final String islogin="ISLOGIN";
    private static final String KeyName="keyname";
    private static final String  Name="name";

    public TokenManager(Context context)
    {
        this.context=context;
        preferences = context.getSharedPreferences(PREF_NAME,privatemode);
        editor=preferences.edit();
    }
public void createLogSession(String keyname, String name)
{
    editor.putString(keyname,keyname);
    editor.putString(Name,name);
    editor.commit();



}
}
