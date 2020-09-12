package com.tech41.app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.tech41.app.Fragments.NotificationFragment;
import com.tech41.app.Fragments.UsersFragment;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.tech41.app.LoginActivity.usernameSave;

public class MainActivity extends AppCompatActivity {

    CircleImageView profile_image;
    TextView username;


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


        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.view_pager);

        ViewPagerAdpter viewPagerAdpter = new ViewPagerAdpter(getSupportFragmentManager());

    //   viewPagerAdpter.addFragment(new ChatFragment(), "My contacts");
       viewPagerAdpter.addFragment(new UsersFragment(), "My contacts");
       viewPagerAdpter.addFragment(new NotificationFragment(), "Notification");

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
}