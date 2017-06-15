package com.example.boghdady.campusapp.StudentScreen;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.boghdady.campusapp.R;
import com.example.boghdady.campusapp.Retrofit.Interfaces;
import com.example.boghdady.campusapp.Retrofit.Responses;
import com.example.boghdady.campusapp.Retrofit.SentBody;
import com.example.boghdady.campusapp.helper.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StudentCreateEvent extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    FrameLayout backBtn;
    ProgressDialog pDialog;
    String Event_Name, Event_Details, Event_Location_Lng, Event_Location_Lat,
            Event_Date, Event_Time, Event_Link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_creat_event);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Create Event");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        InsertEvent("Eevnt one","فان داى هالا بالا", "214" , "244424" , "2/2/2939", "3432 AM", "http://index-soft.com/Campus/Insert_Event.php");


    }

    public void pick_date(View view) {
        DatePickerDialog dialog = new DatePickerDialog(this, this, 2013, 2, 18);
        dialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

    }

    public void pick_time(View view) {
        TimePickerDialog dialog = new TimePickerDialog(this, this, 24, 60, true);
        dialog.show();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {

    }

//---------------------------------------------------------------------------------------------------------------------------------------------------------------

    void clearData(){

    }


//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    void InsertEvent(String Event_Name, String Event_Details, String Event_Location_Lng, String Event_Location_Lat
            ,String Event_Date,String Event_Time,String Event_Link) {


        boolean validated = true;
        if (Event_Name.equalsIgnoreCase("")) {
            validated = false;
            Toast.makeText(StudentCreateEvent.this, R.string.please_enter_right_event_name, Toast.LENGTH_SHORT).show();
        }
        if (Event_Details.equalsIgnoreCase("")) {
            validated = false;
            Toast.makeText(StudentCreateEvent.this, R.string.please_enter_right_event_details, Toast.LENGTH_SHORT).show();
        }
        if (Event_Location_Lng.equalsIgnoreCase("")) {
            validated = false;
            Toast.makeText(StudentCreateEvent.this, R.string.please_enter_right_event_location, Toast.LENGTH_SHORT).show();
        }
        if (Event_Location_Lat.equalsIgnoreCase("")) {
            validated = false;
            Toast.makeText(StudentCreateEvent.this, R.string.please_enter_right_event_location, Toast.LENGTH_SHORT).show();
        }
        if (Event_Date.equalsIgnoreCase("")) {
            validated = false;
            Toast.makeText(StudentCreateEvent.this, R.string.please_enter_right_event_date, Toast.LENGTH_SHORT).show();
        }
        if (Event_Time.equalsIgnoreCase("")) {
            validated = false;
            Toast.makeText(StudentCreateEvent.this, R.string.please_enter_right_event_time, Toast.LENGTH_SHORT).show();
        }
        if (validated) {

            pDialog = new ProgressDialog(StudentCreateEvent.this);
            pDialog.setMessage(getString(R.string.please_wait));
            pDialog.setCancelable(false);
            pDialog.show();

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            Interfaces.User_InserEvent inserEvent = retrofit.create(Interfaces.User_InserEvent.class);
            Call<Responses.DefaultResponse> call = inserEvent.insertEvent(new SentBody.InserEventBody(Event_Name, Event_Details, Event_Location_Lng, Event_Location_Lat, Event_Date, Event_Time, Event_Link));
            call.enqueue(new Callback<Responses.DefaultResponse>() {
                @Override
                public void onResponse(Call<Responses.DefaultResponse> call, Response<Responses.DefaultResponse> response) {

                    try {
                        if (response.body().getSuccess() == 1) {
                            Toast.makeText(getApplication(), getString(R.string.event_added_successfully), Toast.LENGTH_LONG).show();
                            pDialog.dismiss();
                            clearData();
                        } else if (response.body().getSuccess() == 0) {
                            Toast.makeText(getApplication(), getString(R.string.wrong), Toast.LENGTH_LONG).show();
                            pDialog.dismiss();
                        }
                    } catch (Exception ex) {
                        Toast.makeText(getApplication(), getString(R.string.check_your_internet), Toast.LENGTH_LONG).show();
                        pDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<Responses.DefaultResponse> call, Throwable t) {
                    Toast.makeText(getApplication(), getString(R.string.check_your_internet), Toast.LENGTH_LONG).show();
                    pDialog.dismiss();
                }

            });
        }

        }
//-------------------------------------------------------------------------------------------------------------------------------------------------------------




}
