package com.tech41.app;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.appcompat.app.AlertDialog;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.appcompat.widget.Toolbar;
        import androidx.fragment.app.Fragment;
        import androidx.fragment.app.FragmentManager;
        import androidx.fragment.app.FragmentPagerAdapter;
        import androidx.viewpager.widget.ViewPager;

        import android.content.Context;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.graphics.Color;
        import android.graphics.drawable.ColorDrawable;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.LinearLayout;
        import android.widget.TextView;

        import com.google.android.material.tabs.TabLayout;
        import com.tech41.app.Fragments.NotificationFragment;
        import com.tech41.app.Fragments.RequestsFragment;
        import com.tech41.app.Fragments.FriendsFragment;
        import com.tech41.app.Model.Invitation;
        import com.tech41.app.Model.ResponseError;
        import com.tech41.app.Remote.Api;
        import com.tech41.app.Remote.RetrofitClient;

        import org.json.JSONObject;

        import java.util.ArrayList;
        import java.util.Timer;
        import java.util.TimerTask;

        import de.hdodenhof.circleimageview.CircleImageView;
        import dmax.dialog.SpotsDialog;
        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;

        import static com.tech41.app.LoginActivity.usernameSave;

public class MainActivity extends AppCompatActivity {

    CircleImageView profile_image;
    TextView username;
    SharedPreferences preferences;
    private static final String TAG = "MainActivity";
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");


        profile_image = findViewById(R.id.profile_image);
        username = findViewById(R.id.username);

        username.setText(usernameSave.toString());
        if (profile_image.equals(null)) {
            profile_image.setImageResource(R.mipmap.ic_launcher_round);
        } else {
            profile_image.setImageResource(R.mipmap.ic_launcher_round);
        }


        //add new friend button

        Button addfriendButton = findViewById(R.id.btn_addFriends);
        addfriendButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                addFriendDilaog();
            }
        });


        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.view_pager);

        ViewPagerAdpter viewPagerAdpter = new ViewPagerAdpter(getSupportFragmentManager());

        viewPagerAdpter.addFragment(new FriendsFragment(), "My contacts");
        viewPagerAdpter.addFragment(new NotificationFragment(), "Notification");
        viewPagerAdpter.addFragment(new RequestsFragment(), "Requests");

        viewPager.setAdapter(viewPagerAdpter);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                // FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
                return true;
        }
        return false;
    }

    class ViewPagerAdpter extends FragmentPagerAdapter{

        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;

        ViewPagerAdpter(FragmentManager fm){
            super(fm);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void addFragment (Fragment fragment, String title){
            fragments.add(fragment);
            titles.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }

    void addFriendDilaog(){
        //save data
        preferences = getSharedPreferences("JWTTOKEN", Context.MODE_PRIVATE);
        String token = preferences.getString("keyname","");
        String id = preferences.getString("id","");

        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.addfriend_dialog,null);

        LinearLayout layout_fill_form = view.findViewById(R.id.layout_fill_form);
        LinearLayout layout_success = view.findViewById(R.id.layout_success);
        Button btn_Invite = view.findViewById(R.id.btn_request_send);
        EditText frnd_email = view.findViewById(R.id.frnd_email);
        TextView msg_txt = view.findViewById(R.id.msg_txt);


        // friends invite
        btn_Invite.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                final android.app.AlertDialog dialog = new SpotsDialog.Builder()
                        .setContext(MainActivity.this)
                        .build();

              String  frndEmail = frnd_email.getText().toString();
              Invitation invitation = new Invitation(id, frnd_email.getText().toString());
                Api api = RetrofitClient.getInstance().create(Api.class);

                Call<ResponseError> call = api.sendInvitation("Bearer "+token,invitation);
                call.enqueue(new Callback<ResponseError>() {
                    @Override
                    public void onResponse(Call<ResponseError> call, Response<ResponseError> response) {
                        if (response.isSuccessful()) {
                            layout_success.setVisibility(view.VISIBLE);
                            layout_fill_form.setVisibility(view.GONE);

                            timer = new Timer();
                            timer.schedule(new TimerTask() {
                                public void run() {
                                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }, 2000);
                        }
                        // error code
                        else
                            try {
                            JSONObject obj = new JSONObject(response.errorBody().string());
                             msg_txt.setText(obj.getString("message"));
                             msg_txt.setTextColor(Color.RED);

                        } catch (Exception e) { }
                    }

                    @Override
                    public void onFailure(Call<ResponseError> call, Throwable t) {
                        Log.e("Error",t.getLocalizedMessage());
                    }

                });

            }
        });
// Add friend dialog box
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setView(view)
                .create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();



    }
}