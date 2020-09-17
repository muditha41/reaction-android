package com.tech41.app.JWT;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class JWTUtils {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String decordeJWT(String EncodeString) throws Exception
    {
        String[] splitstr = EncodeString.split("\\.");
       Log.d("","Header "+ getJSon(splitstr[0]));
       Log.d("","Payload "+getJSon(splitstr[1]));

          java.util.Base64.Decoder decoder = java.util.Base64.getUrlDecoder();
//        String headerJson = new String(decoder.decode(splitstr[0]));
          String payloadJson = new String(decoder.decode(splitstr[1]));
//        String signatureJson = new String(decoder.decode(splitstr[2]));
//
//        System.out.println("JWT Header : " + headerJson);
//        System.out.println("JWT Header : " + payloadJson);
//       System.out.println("JWT Header : " + signatureJson);
//
//        JSONObject obj = new JSONObject(payloadJson);
//        String id = obj.getString("id");

      return payloadJson;
    }
    public static String getJSon(String EncodeString) throws UnsupportedEncodingException
    {

        byte[] decodebyte = Base64.decode(EncodeString,Base64.URL_SAFE);
        return new String(decodebyte, "UTF-8");
    }
}