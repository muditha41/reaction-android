package com.tech41.app.Remote;




import android.content.Context;
import android.content.SharedPreferences;

import com.tech41.app.Model.Invitation;
import com.tech41.app.Model.RegisterModel;
import com.tech41.app.Model.ResponseError;
import com.tech41.app.Model.TblFriends;
import com.tech41.app.Model.TblRequests;
import com.tech41.app.Model.TblUser;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

   // http://localhost:54926/api/authenticate/login
    @POST("api/authenticate/register")
    Observable<String> registerUser(@Body RegisterModel user );

  @POST("/api/authenticate/login")
  Observable<String> loginUser( @Body TblUser user);

  @GET("/Account/abf8fe05-cbba-4f00-8da7-471d7a479960/friends")
 Call<List<TblFriends>> getfriends();

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("/Account/{uid}/friends")
    Call<List<TblFriends>> getfriendslist(@Header("Authorization")String authToken, @Path("uid") String uid);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("/Account/invite")
    Call<ResponseError> sendInvitation(@Header("Authorization")String authToken, @Body Invitation invitation);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("/Account/{uid}/friendrequests")
    Call<List<TblRequests>> getRequestsList(@Header("Authorization")String authToken, @Path("uid") String uid);



}
