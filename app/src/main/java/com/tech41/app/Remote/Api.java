package com.tech41.app.Remote;




import com.tech41.app.Model.ImageUpdate;
import com.tech41.app.Model.Invitation;
import com.tech41.app.Model.RegisterModel;
import com.tech41.app.Model.ResponseError;
import com.tech41.app.Model.TblFriends;
import com.tech41.app.Model.TblNotifications;
import com.tech41.app.Model.TblRequests;
import com.tech41.app.Model.TblUser;
import com.tech41.app.Model.user;
import com.tech41.app.Model.userStatusUpdate;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface Api {

   // http://localhost:54926/api/authenticate/login
    @POST("api/authenticate/register")
    Observable<String> registerUser(@Body RegisterModel user );

    @POST("/api/authenticate/login")
    Observable<String> loginUser( @Body TblUser user);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("/friend/{uid}/friends")
    Call<List<TblFriends>> getfriendslist(@Header("Authorization")String authToken, @Path("uid") String uid);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("/friend/invite")
    Call<ResponseError> sendInvitation(@Header("Authorization")String authToken, @Body Invitation invitation);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("/friend/{uid}/friendrequests")
    Call<List<TblRequests>> getRequestsList(@Header("Authorization")String authToken, @Path("uid") String uid);

   @Headers({ "Content-Type: application/json;charset=UTF-8"})
   @PUT("/friend/acceptinvite")
   Call<ResponseError> acceptInvite(@Header("Authorization")String authToken, @Body Invitation invitation);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @PUT("/status/statuschecked")
    Call<ResponseError> statusChecked(@Header("Authorization")String authToken, @Body userStatusUpdate userStatusUpdate);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @PUT("/status/statusupdate")
    Call<ResponseError> statusUpdate(@Header("Authorization")String authToken, @Body userStatusUpdate userStatusUpdate);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("/Account/{uid}/notification")
    Call<List<TblNotifications>> getNotification(@Header("Authorization")String authToken, @Path("uid") String uid);

   @Headers({ "Content-Type: application/json;charset=UTF-8"})
   @PUT("/Account/{uid}/notificationchecked")
   Call<ResponseError> checkNotifications(@Header("Authorization")String authToken,@Path("uid") String uid);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @PUT("/Account/updateimage")
    Call<ResponseError> updateimage(@Header("Authorization")String authToken,  @Body ImageUpdate imageUpdate);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("/Account/{uid}/profile")
    Call<user> getUserDetails(@Header("Authorization")String authToken, @Path("uid") String uid);


}