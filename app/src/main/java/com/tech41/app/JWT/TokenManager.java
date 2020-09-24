package com.tech41.app.JWT;

import android.content.Context;
import android.content.SharedPreferences;

import com.tech41.app.Model.userStatusUpdate;

import org.json.JSONException;
import org.json.JSONObject;

public class TokenManager {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    Context context;
    int privatemode = 0;
    private static final String PREF_NAME="JWTTOKEN";
  //  private static final String islogin="ISLOGIN";
    private static final String KeyName="keyname";
    private static final String  Name="name";
    private static final String  Id="id";

    public TokenManager(Context context)
    {
        this.context=context;
        preferences = context.getSharedPreferences(PREF_NAME, privatemode);
        editor=preferences.edit();
    }
public void createLoginSession(String keyName,String payload) throws JSONException {
       JSONObject obj = new JSONObject(payload);
       String id = obj.getString("id");
     //   uId = obj.getString("id"); // user id save public temp
       String name = obj.getString("name");

        editor.putString(KeyName,keyName);
        editor.putString(Name,name);
        editor.putString(Id,id);
        editor.commit();
}

public String getSession(){
        return preferences.getString(KeyName,"0");
}

public void removeSession(){ editor.putString(KeyName,"0").commit();}

}
