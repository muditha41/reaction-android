package com.tech41.app.JWT;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

public class TokenManager {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    public static String userId;

    Context context;
    int privatemode = 0;
    private static final String PREF_NAME="JWTTOKEN";
  //  private static final String islogin="ISLOGIN";
    private static final String KeyName="keyname";
    private static final String  Name="name";
    private static final String  Id="id";
  public static final String  Uid="id";

    public TokenManager(Context context)
    {
        this.context=context;
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor=preferences.edit();
    }
public void createLoginSession(String keyName,String payload) throws JSONException {
       JSONObject obj = new JSONObject(payload);
       String id = obj.getString("id");
       userId = obj.getString("id"); // user id save public temp
       String name = obj.getString("name");

        editor.putString(KeyName,keyName);
        editor.putString(Name,name);
        editor.putString(Id,id);
        editor.commit();
}

public String getSession(){
        return preferences.getString(KeyName,"0");
}

public void removeSession(){ editor.putString(KeyName,null).commit();}

}
