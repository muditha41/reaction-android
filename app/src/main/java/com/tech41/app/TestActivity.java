package com.tech41.app;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.tech41.app.JWT.TokenManager;
import com.tech41.app.Model.ImageUpdate;
import com.tech41.app.Model.ResponseError;
import com.tech41.app.Model.TblFriends;
import com.tech41.app.Model.userStatusUpdate;
import com.tech41.app.Remote.Api;
import com.tech41.app.Remote.ImageManager;
import com.tech41.app.Remote.RetrofitClient;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONObject;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import static com.tech41.app.MainActivity.token;
import static com.tech41.app.MainActivity.uId;
import static java.sql.DriverManager.println;

public class TestActivity extends AppCompatActivity {

 CircleImageView userProfileImage;
private static final int GalleryPick = 1;
CropImageView cropImageView;
ImageView quick_start_cropped_image;
    private TblFriends userFriend;
    TokenManager tokenManager;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        CircleImageView userProfileImage = findViewById(R.id.profile_image);
         cropImageView = findViewById(R.id.cropImageView);
        quick_start_cropped_image = findViewById(R.id.quick_start_cropped_image);
        Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {

               SavetoGallery();

            }
        });

userProfileImage.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,GalleryPick);
    }
});
        ActivityCompat.requestPermissions(TestActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        ActivityCompat.requestPermissions(TestActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==GalleryPick && resultCode==RESULT_OK && data!=null){
            Uri ImageUri = data.getData();

            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                quick_start_cropped_image.setImageURI(result.getUri());

                Toast.makeText(
                        this, "Cropping successful, Sample: " + result.getSampleSize(), Toast.LENGTH_LONG)
                        .show();

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }

    }
    public void SavetoGallery(){

        BitmapDrawable bitmapDrawable = (BitmapDrawable) quick_start_cropped_image.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
      //   convert img to bitmap
         //   Bitmap bm = ImageManager.getBitmap(resultUri.toString());
        byte[] bytes = ImageManager.getBytesFromBitmap(bitmap,100);
        byte[] encoded = Base64.encode(bytes,Base64.DEFAULT);
        String imgString =new String(encoded);

       // byte[] decoded = Base64.decode(encodedstr,Base64.DEFAULT);

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