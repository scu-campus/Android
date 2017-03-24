package com.example.boghdady.campusapp.NavigationDrawer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.boghdady.campusapp.Create_post;
import com.example.boghdady.campusapp.Notification_list;
import com.example.boghdady.campusapp.R;
import com.example.boghdady.campusapp.StudentScreen.StudentCreateEvent;
import com.example.boghdady.campusapp.Tabedactivity;
import com.example.boghdady.campusapp.helper.Constants;
import com.example.boghdady.campusapp.helper.CustomTextView;
import com.example.boghdady.campusapp.helper.SharedPref;
import com.mikhaellopez.circularimageview.CircularImageView;

public class StudentNavigationDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    CircularImageView profileImage;
    CustomTextView userName;
    SharedPref sharedPref = SharedPref.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_navigation_drawer);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("SCU Campus ");
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.student_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View view = navigationView.getHeaderView(0);

        profileImage = (CircularImageView)view.findViewById(R.id.user_imageView);
        userName = (CustomTextView)view.findViewById(R.id.txtProfileName);

        SetStudentImageAndName();
    }


//---------------------------------------------------------------------------------------------------------------
    void  SetStudentImageAndName(){
        String userImg=sharedPref.getStudentImage();
        if(!userImg.equalsIgnoreCase("") ){
            Glide.with(StudentNavigationDrawer.this)
                    .load(Constants.IMAGES_URL+sharedPref.getStudentImage())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .crossFade()
                    .dontAnimate()
                    .into(profileImage);
            userName.setText(sharedPref.getString("student_name"));
        }else{
            profileImage.setImageDrawable(getResources().getDrawable(R.drawable.profile_place_holder));
        }
    }

//---------------------------------------------------------------------------------------------------------------------------

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.student_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//---------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == R.id.notification_settings) {
            Intent intent=new Intent(this,Notification_list.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
//---------------------------------------------------------------------------------------------------------------------------
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handlالقيمةe navigation view item clicks here.
        int id = item.getItemId();


        if(id == R.id.study_table) {
            Intent in =new Intent(StudentNavigationDrawer.this, Tabedactivity.class);
            startActivity(in);

        } else if(id == R.id.free_places) {

        } else if(id == R.id.creat_events) {
            Intent i = new Intent(StudentNavigationDrawer.this , StudentCreateEvent.class);
            startActivity(i);

        } else if(id == R.id.share) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.student_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
