package com.example.boghdady.campusapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.boghdady.campusapp.NavigationDrawer.StudentNavigationDrawer;
import com.example.boghdady.campusapp.Registeration.DoctorSignupActivity;
import com.example.boghdady.campusapp.Registeration.StudentSignupActivity;

public class MainActivity extends AppCompatActivity {

   Button btnDocLoging , btnStudentLogin ;
   LinearLayout SkipLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        btnDocLoging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this , DoctorSignupActivity.class);
                startActivity(i);
            }
        });

        btnStudentLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this , StudentSignupActivity.class);
                startActivity(i);
            }
        });

        SkipLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this , StudentNavigationDrawer.class);
                startActivity(i);
            }
        });

    }

    void init()
    {
        btnDocLoging = (Button)findViewById(R.id.btn_Doctor_Login);
        btnStudentLogin = (Button)findViewById(R.id.btn_Student_Login);
        SkipLogin = (LinearLayout)findViewById(R.id.login_skiptoHome);

    }


}
