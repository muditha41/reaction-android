package com.tech41.app.Remote;




import com.tech41.app.Model.RegisterModel;
import com.tech41.app.Model.TblFriends;
import com.tech41.app.Model.TblUser;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
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

   // @GET("/Account/{uid}/friends")
   // Call<List<TblFriends>> getfriends(@Path("uid") String uid);

   // @GET("/Account/{uid}/friends")
   // Call<List<TblFriends>> getfriends(@Header("Authorization")String token, @Path("uid") String uid);

    //@Header("Authorization") String token, - code for headers
    //, @Query("id") String id
    //abf8fe05-cbba-4f00-8da7-471d7a479960

}
