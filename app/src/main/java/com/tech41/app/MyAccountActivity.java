package com.tech41.app;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.ListAdapter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.tech41.app.JWT.TokenManager;
import com.tech41.app.Model.ImageUpdate;
import com.tech41.app.Model.ResponseError;
import com.tech41.app.Model.TblFriends;
import com.tech41.app.Model.TblNotifications;
import com.tech41.app.Model.UserUpdate;
import com.tech41.app.Model.user;
import com.tech41.app.Remote.Api;
import com.tech41.app.Remote.ImageManager;
import com.tech41.app.Remote.RetrofitClient;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONObject;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
    Timer timer;



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onStart() {
        super.onStart();
        try {
            checkSession();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void checkSession() throws Exception {
        //check session
        this.tokenManager = new TokenManager(MyAccountActivity.this);
        String KeyName = tokenManager.getSession();

        if(KeyName!=null){
        }else {
            Intent intent = new Intent(MyAccountActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        getData();
        CircleImageView profile_image = (CircleImageView)findViewById(R.id.profile_image);
        ActivityCompat.requestPermissions(MyAccountActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        ActivityCompat.requestPermissions(MyAccountActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);

        // TextInputLayout and Text input
        TextInputLayout aboutInputLayout = (TextInputLayout)findViewById(R.id.aboutInputLayout);
        TextInputLayout nameInputLayout = (TextInputLayout)findViewById(R.id.nameInputLayout);
        TextInputLayout lives_drop_InputLayout = (TextInputLayout)findViewById(R.id.lives_drop_InputLayout);
        TextInputLayout workplace_InputLayout = (TextInputLayout)findViewById(R.id.workplace_InputLayout);
        TextInputLayout relationship_drop_InputLayout = (TextInputLayout)findViewById(R.id.relationship_drop_InputLayout);


        //Edit Text
        EditText input_about = (EditText)findViewById(R.id.input_about);
        EditText input_name = (EditText)findViewById(R.id.input_name);
        AutoCompleteTextView input_lives_drop = (AutoCompleteTextView)findViewById(R.id.input_lives_drop);
        EditText input_workplace = (EditText)findViewById(R.id.input_workplace);
        AutoCompleteTextView input_relationship_dropdown = (AutoCompleteTextView)findViewById(R.id.input_relationship_dropdown);

        String[] items = new String[]{
                "Single",
                "In Relationship",
                "Married",
                "Divorced"
        };

        ArrayAdapter<String>  adapter = new ArrayAdapter<>(MyAccountActivity.this, R.layout.dropdown_item, items);
        input_relationship_dropdown.setAdapter(adapter);

        String[] locationitems = new String[]{
                "Colombo",
                "Kandy",
                "Galle",
                "kegalle",
                "Gampaha"
        };

        ArrayAdapter<String> LocationAdapter = new ArrayAdapter<>(MyAccountActivity.this, R.layout.dropdown_item, locationitems);
        input_lives_drop.setAdapter(LocationAdapter);




        //update details click listners
        aboutInputLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(aboutInputLayout.getEndIconContentDescription().equals("edit")){
                    input_about.setFocusable(true);
                    input_about.setFocusableInTouchMode(true);
                    aboutInputLayout.setEndIconDrawable(R.drawable.baseline_done_outline_24);
                    aboutInputLayout.setEndIconContentDescription("update");
                }else if(aboutInputLayout.getEndIconContentDescription().equals("update")) {
                    input_about.setFocusable(false);
                    input_about.setFocusableInTouchMode(false);
                    aboutInputLayout.setEndIconDrawable(R.drawable.icon_textedit);
                    aboutInputLayout.setEndIconContentDescription("edit");
                   String value =  input_about.getText().toString();
                    updateDbProfileDtails(1,value); }}});


        nameInputLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nameInputLayout.getEndIconContentDescription().equals("edit")){
                    input_name.setFocusable(true );
                    input_name.setFocusableInTouchMode(true);
                    nameInputLayout.setEndIconDrawable(R.drawable.baseline_done_outline_24);
                    nameInputLayout.setEndIconContentDescription("update");
                }else if(nameInputLayout.getEndIconContentDescription().equals("update")) {
                    input_name.setFocusable(false);
                    input_name.setFocusableInTouchMode(false);
                    nameInputLayout.setEndIconDrawable(R.drawable.icon_textedit);
                    nameInputLayout.setEndIconContentDescription("edit");
                    String value =  input_name.getText().toString();
                    updateDbProfileDtails(2,value); }}});


        input_lives_drop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String value =  LocationAdapter.getItem(position).toString();
                updateDbProfileDtails(3,value);
            }
        });

        workplace_InputLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(workplace_InputLayout.getEndIconContentDescription().equals("edit")){
                    input_workplace.setFocusable(true);
                    input_workplace.setFocusableInTouchMode(true);
                    workplace_InputLayout.setEndIconDrawable(R.drawable.baseline_done_outline_24);
                    workplace_InputLayout.setEndIconContentDescription("update");
                }else if(workplace_InputLayout.getEndIconContentDescription().equals("update")) {
                    input_workplace.setFocusable(false);
                    input_workplace.setFocusableInTouchMode(false);
                    workplace_InputLayout.setEndIconDrawable(R.drawable.icon_textedit);
                    workplace_InputLayout.setEndIconContentDescription("edit");
                    String value =  input_workplace.getText().toString();
                    updateDbProfileDtails(4,value); }}});


        input_relationship_dropdown.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String value =  adapter.getItem(position).toString();
                updateDbProfileDtails(5,value); }
        });

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
                updateDbProfileImage();

                Toast.makeText(MyAccountActivity.this,"Profile image uploaded Successfuly!",Toast.LENGTH_SHORT).show();
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
        AutoCompleteTextView input_relationship_dropdown = (AutoCompleteTextView)findViewById(R.id.input_relationship_dropdown);
        CircleImageView profile_image = (CircleImageView)findViewById(R.id.profile_image);


        if(userdata.getId()!=null){

            if(userdata.getDescription()==null){
                input_about.setHint("Describe Yourself");
            }else input_about.setText(userdata.getDescription());

            if(userdata.getFullName()==null){
               input_name.setHint("Update Full Name");
            }else input_name.setText(userdata.getFullName());

            if(userdata.getLocation()==null){
                input_lives_drop.setHintTextColor(getResources().getColor(R.color.fontLightGray));
                input_lives_drop.setHint("Update Location");
            }else {
                input_lives_drop.setHint(userdata.getLocation());
            }

            if(userdata.getWorkPlace()==null){
                input_workplace.setHint("Update Work Place");
            }else  input_workplace.setText(userdata.getWorkPlace());

            if(userdata.getRelationshipStatus()==null){
                input_relationship_dropdown.setHintTextColor(getResources().getColor(R.color.fontLightGray));
                input_relationship_dropdown.setHint("Update Relationship Status");
            }else {
                input_relationship_dropdown.setHint(userdata.getRelationshipStatus());
            }


            if(userdata.getImage()!=null){
                String imgString = userdata.getImage() ;
                byte[] decoded = Base64.decode(imgString,Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(decoded , 0, decoded .length);
                profile_image.setImageBitmap(bitmap);

            }else {
                profile_image.setImageResource(R.drawable.ic_launcher_round);
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
        CircleImageView profile_image = (CircleImageView)findViewById(R.id.profile_image);
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

    private void updateDbProfileDtails(int colum, String value){

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences = getSharedPreferences("JWTTOKEN", Context.MODE_PRIVATE);
        token = preferences.getString("keyname","");
        uId = preferences.getString("id","");

        UserUpdate userUpdate = new UserUpdate(uId,colum,value);
        Api api = RetrofitClient.getInstance().create(Api.class);
        Call<ResponseError> call = api.updateUserDetails("Bearer "+token,userUpdate);
        call.enqueue(new Callback<ResponseError>() {
            @Override
            public void onResponse(Call<ResponseError> call, Response<ResponseError> response) {
                if (response.isSuccessful()) {
                    Log.d("result","Details updated successfully");
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
