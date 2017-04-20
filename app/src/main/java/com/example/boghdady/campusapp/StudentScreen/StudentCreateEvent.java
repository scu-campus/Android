package com.example.boghdady.campusapp.StudentScreen;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.boghdady.campusapp.R;

public class StudentCreateEvent extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_creat_event);

    }

    public void pick_date(View view)
    {
        DatePickerDialog dialog = new DatePickerDialog(this, this, 2013, 2, 18);
        dialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2)
    {

    }

    public void pick_time(View view)
    {
        TimePickerDialog dialog = new TimePickerDialog(this,this,24,60,true);
        dialog.show();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1)
    {

    }
}
