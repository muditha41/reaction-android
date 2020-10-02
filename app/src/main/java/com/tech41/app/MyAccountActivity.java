package com.tech41.app;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tech41.app.JWT.TokenManager;
import com.tech41.app.Model.ImageUpdate;
import com.tech41.app.Model.ResponseError;
import com.tech41.app.Model.TblFriends;
import com.tech41.app.Model.TblNotifications;
import com.tech41.app.Model.user;
import com.tech41.app.Remote.Api;
import com.tech41.app.Remote.ImageManager;
import com.tech41.app.Remote.RetrofitClient;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONObject;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.tech41.app.MainActivity.token;
import static com.tech41.app.MainActivity.uId;

public class MyAccountActivity extends AppCompatActivity {

    TokenManager tokenManager;
    SharedPreferences preferences;
    private user userdata;
    private static final int GalleryPick = 1;
    CircleImageView profile_image;
    Uri uri;
    private static final int ACCESS_FILE=43;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        getData();
        CircleImageView profile_image = (CircleImageView)findViewById(R.id.profile_image);
        ActivityCompat.requestPermissions(MyAccountActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        ActivityCompat.requestPermissions(MyAccountActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
    }

    public void updateImage(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent,"ACCESS"),ACCESS_FILE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        CircleImageView profile_image = (CircleImageView)findViewById(R.id.profile_image);
        if(requestCode == ACCESS_FILE && resultCode ==Activity.RESULT_OK && data !=null &&data.getData()!=null){
            Uri FILE_URI=data.getData();
            startCrop(FILE_URI);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                profile_image.setImageURI(resultUri);
                Toast.makeText(MyAccountActivity.this,"Image Crop Successfuly!",Toast.LENGTH_SHORT).show();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(MyAccountActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
    }

        private void startCrop(Uri FILE_URI) {
            CropImage.activity(FILE_URI)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setCropShape(CropImageView.CropShape.OVAL)
                    .setActivityTitle("Profile Image")
                    .setFixAspectRatio(true)
                    .setCropMenuCropButtonTitle("Done")
                    .start(this); }

    private void getData() {

        Api api = RetrofitClient.getInstance().create(Api.class);
        Call<user> call = api.getUserDetails("Bearer "+token, uId);
        call.enqueue(new Callback<user>() {
            @Override
            public void onResponse(Call<user> call, Response<user> response) {

                if (response.isSuccessful())
                {
                    user userdata = response.body();
                    setData(userdata);

                }
                else   Log.d("error","Your contact list is empty. Invite yor firends");

            }
            @Override
            public void onFailure(Call<user> call, Throwable t) {

                Log.e("failure",t.getLocalizedMessage());
            }
        });
    }

    public void setData(user userdata){

        EditText input_about = (EditText)findViewById(R.id.input_about);
        EditText input_name = (EditText)findViewById(R.id.input_name);
        EditText input_lives_drop = (EditText)findViewById(R.id.input_lives_drop);
        EditText input_workplace = (EditText)findViewById(R.id.input_workplace);
        EditText input_relationship_drop = (EditText)findViewById(R.id.input_relationship_drop);
        CircleImageView profile_image = (CircleImageView)findViewById(R.id.profile_image);


        if(userdata.getId()!=null){

           input_about.setText(userdata.getDescription());
            input_name.setText(userdata.getFullName());
            input_lives_drop.setText(userdata.getLocation());
            input_workplace.setText(userdata.getWorkPlace());
            input_relationship_drop.setText(userdata.getRelationshipStatus());

            //image decorde
            String imgString = userdata.getImage();
            if(imgString!=null){
                byte[] decoded = Base64.decode(imgString,Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(decoded , 0, decoded .length);
                profile_image.setImageBitmap(bitmap);

            }else {
                profile_image.setImageResource(R.mipmap.ic_launcher_round);
            }
       }
    }


    public void Logout(View view) {
        this.tokenManager = new TokenManager(MyAccountActivity.this);
        tokenManager.removeSession();
        moveToLogin();
    }
    private void moveToLogin() {
        Intent intent = new Intent(MyAccountActivity.this, StartActivity.class);
        startActivity(intent);
    }

    public void moveToMainActivity(View view) {
        Intent intent = new Intent(MyAccountActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void updateDbProfileImage() {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) profile_image.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();

        byte[] bytes = ImageManager.getBytesFromBitmap(bitmap,100);
        byte[] encoded = Base64.encode(bytes,Base64.DEFAULT);
        String imgString =new String(encoded);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences = getSharedPreferences("JWTTOKEN", Context.MODE_PRIVATE);
        token = preferences.getString("keyname","");
        uId = preferences.getString("id","");
        ImageUpdate imageUpdate = new ImageUpdate(uId,imgString);
        Api api = RetrofitClient.getInstance().create(Api.class);
        Call<ResponseError> call = api.updateimage("Bearer "+token,imageUpdate);
        call.enqueue(new Callback<ResponseError>() {
            @Override
            public void onResponse(Call<ResponseError> call, Response<ResponseError> response) {
                if (response.isSuccessful()) {
                    Log.d("result","image upload success");
                }
                else
                    try {
                        JSONObject obj = new JSONObject(response.errorBody().string());
                        String e = (obj.getString("message"));
                    } catch (Exception e) { }
            }

            @Override
            public void onFailure(Call<ResponseError> call, Throwable t) {
                Log.e("Error",t.getLocalizedMessage());
            }
        });
    }

}
