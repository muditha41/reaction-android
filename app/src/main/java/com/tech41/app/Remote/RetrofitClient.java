package com.tech41.app.Remote;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClient {

 private static Retrofit instance;

 public static Retrofit getInstance() {

     if (instance == null)
         instance = new Retrofit.Builder()
                 // .baseUrl("http://10.0.2.2:5000")
                 .baseUrl("http://192.168.8.102:54926/")
                 .addConverterFactory(ScalarsConverterFactory.create())
                 .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                 .addConverterFactory(GsonConverterFactory.create())
                 .build();
         return instance;
 }

}
