package com.tech41.app;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.appcompat.app.AlertDialog;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.appcompat.widget.Toolbar;
        import androidx.fragment.app.Fragment;
        import androidx.fragment.app.FragmentManager;
        import androidx.fragment.app.FragmentPagerAdapter;
        import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
        import androidx.viewpager.widget.ViewPager;
        import android.content.Context;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.graphics.Color;
        import android.graphics.drawable.ColorDrawable;
        import android.os.Bundle;
        import android.preference.PreferenceManager;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.LinearLayout;
        import android.widget.TextView;
        import android.widget.Toast;
        import com.google.android.material.tabs.TabLayout;
        import com.tech41.app.Fragments.NotificationFragment;
        import com.tech41.app.Fragments.RequestsFragment;
        import com.tech41.app.Fragments.FriendsFragment;
        import com.tech41.app.JWT.JWTUtils;
        import com.tech41.app.JWT.TokenManager;
        import com.tech41.app.Model.Invitation;
        import com.tech41.app.Model.ResponseError;
        import com.tech41.app.Model.TblFriends;
        import com.tech41.app.Model.user;
        import com.tech41.app.Remote.Api;
        import com.tech41.app.Remote.RetrofitClient;
        import org.json.JSONObject;
        import java.util.ArrayList;
        import java.util.List;
        import java.util.Timer;
        import java.util.TimerTask;
        import java.util.jar.Manifest;

        import de.hdodenhof.circleimageview.CircleImageView;
        import dmax.dialog.SpotsDialog;
        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;

        import static com.tech41.app.MainActivity.token;


public class MainActivity extends AppCompatActivity {

    CircleImageView profile_image;
    TextView tab_title,notificationNumber;
    SharedPreferences preferences;
    private static final String TAG = "MainActivity";
    Timer timer;
    NotificationCounter notificationCounter;
    Context context;
    public static String uId;
    public static String token;
    MyAccountActivity myAccountActivity;
    JWTUtils jwtUtils;
    TokenManager tokenManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences = getSharedPreferences("JWTTOKEN", Context.MODE_PRIVATE);
         token = preferences.getString("keyname","");
         uId = preferences.getString("id","");

        tab_title =(TextView)findViewById(R.id.tab_title);
        profile_image = findViewById(R.id.profile_image);
        notificationCounter = new NotificationCounter(findViewById(R.id.bell));
        notificationNumber=(TextView)findViewById(R.id.notificationNumber);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

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

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
              //  Toast.makeText(getApplicationContext(),"onTabSelected",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                String ac = null;
                int pos = viewPager.getCurrentItem();
                Fragment activeFragment = viewPagerAdpter.getItem(pos);
                if(pos==0){
                    tab_title.setText("My contacts");
                }else if(pos==1){
                    tab_title.setText("Notification");
                }else
                    tab_title.setText("Requests");
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Toast.makeText(getApplicationContext(),"onTabReselected",Toast.LENGTH_LONG).show();
            }
        });

    }


    public void moveToProfile(View view) {

        Intent  intent = new Intent(MainActivity.this,TestActivity.class);
        startActivity(intent);
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
        public void addView (View view){

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
                            }, 1000);
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