package com.tech41.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.tech41.app.Model.RegisterModel;
import com.tech41.app.Remote.Api;
import com.tech41.app.Remote.RetrofitClient;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import dmax.dialog.SpotsDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RegisterActivity extends AppCompatActivity {

    Api api;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    EditText Username,Email, Password,com_password;
    Button button;
   private TextView error_txt;
    Timer timer;

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_register);

        api = RetrofitClient.getInstance().create(Api.class);
        Username = (EditText) findViewById(R.id.username);
        Password = (EditText) findViewById(R.id.password);
        Email = (EditText) findViewById(R.id.email) ;
        com_password = (EditText)findViewById(R.id.com_password) ;
        button=(Button) findViewById(R.id.btn_register);
        error_txt=(TextView)findViewById(R.id.error_txt);

        com_password.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String strPass1 = Password.getText().toString();
                String strPass2 = com_password.getText().toString();
                if (strPass1.equals(strPass2)) {
                   error_txt.setText(R.string.settings_pwd_equal);
                   error_txt.setVisibility(View.GONE);
                } else {
                    error_txt.setVisibility(View.VISIBLE);
                    error_txt.setText(R.string.settings_pwd_not_equal);
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog dialog = new SpotsDialog.Builder()
                        .setContext(RegisterActivity.this)
                        .build();
                dialog.show();
                RegisterModel user = new RegisterModel(Username.getText().toString(),Email.getText().toString(), Password.getText().toString()

                );

                compositeDisposable.add(api.registerUser(user)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {
                                if (s.equals("Success")) {
                                    finish();
                                }
                              Toast.makeText(RegisterActivity.this, "User created successfully!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();

                                timer = new Timer();
                                timer.schedule(new TimerTask() {
                                    public void run(){
                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        finish(); }
                                },1000);

                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                dialog.dismiss();
                               Toast.makeText(RegisterActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }));
            }
        });

        }
}