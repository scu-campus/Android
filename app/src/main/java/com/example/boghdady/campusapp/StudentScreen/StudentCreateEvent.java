package com.example.boghdady.campusapp.StudentScreen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.example.boghdady.campusapp.R;

public class StudentCreateEvent extends AppCompatActivity {

    FrameLayout backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_creat_event);


        // handling back arrow

//        getSupportActionBar().hide();
        backBtn= (FrameLayout) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
}
