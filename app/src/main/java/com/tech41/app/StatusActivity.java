package com.tech41.app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.tech41.app.Fragments.FriendsFragment;
import com.tech41.app.Model.TblFriends;
import com.tech41.app.Model.TblRequests;
import com.tech41.app.Model.userStatusUpdate;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class StatusActivity extends AppCompatActivity implements SelectorDialog.SelectorDialogListner {

    private Context context;
    SharedPreferences preferences;
    private TblFriends userFriend;

    public StatusActivity(  ) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        getStatusViewData();
    }

    //dialogbox create
    public void openDialog(){
    SelectorDialog selectorDialog = new SelectorDialog(this.userFriend);
    selectorDialog.show(getSupportFragmentManager(),"selectorDialog");
    }

public void getStatusViewData(){

    TextView friend_name = (TextView)findViewById(R.id.friend_name);
    TextView friend_status_id = (TextView)findViewById(R.id.friend_status_id);
    TextView user_status_id = (TextView)findViewById(R.id.user_status_id);
  //  LottieAnimationView friend_status_image = (LottieAnimationView)findViewById(R.id.friend_status_image);
    ImageView friend_status_img =(ImageView)findViewById(R.id.friend_status_img);
    ImageView user_status_img =(ImageView)findViewById(R.id.user_status_img);
    CircleImageView profile_image = (CircleImageView)findViewById(R.id.profile_image);

    user_status_img.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view)
        {
            openDialog();
        }
    });

    Intent intent = getIntent();
    userFriend = (TblFriends) intent.getSerializableExtra("userFriend");
         friend_name.setText(userFriend.getFriend().getUserName()+"'s  status");
         user_status_id.setText("Only "+userFriend.getFriend().getUserName()+" can see your status.");
         String friend_status = userFriend.getUserStatus().getFriendStatus().getName();
         String user_status = userFriend.getUserStatus().getStatus().getName();
         String user_status_img_url = userFriend.getUserStatus().getStatus().getImage();
         String friend_status_img_url = userFriend.getUserStatus().getFriendStatus().getImage();

    //image decorde
    if(userFriend.getFriend().getImage()!=null) {
        String imgString = userFriend.getFriend().getImage();
        byte[] decoded = Base64.decode(imgString, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decoded, 0, decoded.length);
        profile_image.setImageBitmap(bitmap);
    }else {
        profile_image.setImageResource(R.drawable.ic_launcher_round);
    }

    // Friend Status part
       if(friend_status_img_url.equals("Empty")){
        friend_status_img.setImageResource(R.drawable.empty_face);
        friend_status_id.setText(userFriend.getFriend().getUserName()+" hasn't updated yet.");
    }else {
        Resources res = getResources();
        int resourceId = res.getIdentifier(
                friend_status_img_url , "drawable", getPackageName() );
        friend_status_img.setImageResource(resourceId);
        friend_status_id.setText(friend_status);
    }
    // User Status part
        if(user_status_img_url.equals("Empty")){
        user_status_img.setImageResource(R.drawable.empty_face);
        user_status_id.setText(userFriend.getFriend().getUserName()+" hasn't updated yet.");
    } else {
        Resources res = getResources();
        int resourceId = res.getIdentifier(
                user_status_img_url , "drawable", getPackageName() );
        user_status_img.setImageResource(resourceId);
        user_status_id.setText(user_status);
    }
  }

    @Override
    public void applyTexts(String imgId) {
        TextView user_status_id = (TextView)findViewById(R.id.user_status_id);
        ImageView user_status_img =(ImageView)findViewById(R.id.user_status_img);
        Resources res = getResources();
        String packageName = "com.tech41.app";
        int resourceId = res.getIdentifier(
                imgId , "drawable", packageName);
        int img = resourceId;
        user_status_img.setImageResource(resourceId);
        user_status_id.setText(imgId);
    }

    public void moveToMainActivity(View view) {
        Intent intent = new Intent(StatusActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void moveToProfile(View view) {

       Intent intent = new Intent(StatusActivity.this, ProfileActivity.class);
        intent.putExtra("friendProfile", userFriend);
        startActivity(intent);
    }
}