package com.tech41.app;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.JsonObject;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.tech41.app.JWT.JWTUtils;
import com.tech41.app.JWT.TokenManager;
import com.tech41.app.Model.TblUser;
import com.tech41.app.Remote.Api;
import com.tech41.app.Remote.RetrofitClient;

import org.json.JSONArray;
import org.json.JSONObject;

import dmax.dialog.SpotsDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {

    Api api;

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    EditText Username, Password;
    Button button;
    TextView signup_text;
    public static String usernameSave;
    public static String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signup_text = findViewById(R.id.signup_text);
        signup_text.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        api = RetrofitClient.getInstance().create(Api.class);

        Username = (EditText) findViewById(R.id.username);
        Password = (EditText) findViewById(R.id.password);
        button=(Button) findViewById(R.id.btn_login);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog dialog = new SpotsDialog.Builder()
                        .setContext(LoginActivity.this)
                        .build();
                dialog.show();
                usernameSave = Username.getText().toString();
              // TblUser user = new TblUser("kavinda", "Kavinda@1234");
                TblUser user = new TblUser(Username.getText().toString(), Password.getText().toString());

                compositeDisposable.add(api.loginUser(user)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<String>() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void accept(String s) throws Exception {

                          //  Toast.makeText(LoginActivity.this, s, Toast.LENGTH_SHORT).show();
                                JSONObject obj = new JSONObject(s);
                                token = obj.getString("token");
                                JWTUtils.decordeJWT(token);


                        startActivity(new Intent(LoginActivity.this, MainActivity.class));

                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                dialog.dismiss();
                                Toast.makeText(LoginActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }));
            }

        });

    }
    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }
}