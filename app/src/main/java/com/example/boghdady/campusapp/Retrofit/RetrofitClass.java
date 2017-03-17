package com.example.boghdady.campusapp.Retrofit;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.boghdady.campusapp.MainActivity;
import com.example.boghdady.campusapp.R;
import com.example.boghdady.campusapp.helper.Constants;
import com.example.boghdady.campusapp.helper.SharedPref;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by boghdady on 23/02/17.
 */

public class RetrofitClass {

    private Activity activity;
    ProgressDialog pDialog;

    public void GetUserDetails(String User_Email , String User_Password){


        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        Interfaces.User_Details user_details=retrofit.create(Interfaces.User_Details.class);

        Call<Responses.GetUserDetailsResponse>call=user_details.getUserData(new SentBody.UserOperationBody(User_Email,User_Password));
        call.enqueue(new Callback<Responses.GetUserDetailsResponse>() {
            @Override
            public void onResponse(Call<Responses.GetUserDetailsResponse> call, Response<Responses.GetUserDetailsResponse> response) {
                try {

                    if (response.body().getSuccess()==1){
                        Constants.Users=response.body().getUsers();
                        SharedPref sharedPref=new SharedPref(activity.getApplicationContext());
                        Models.UserModel userModel = Constants.Users.get(0);
                        sharedPref.putString("user_id",userModel.getUser_ID());
                        sharedPref.putString("user_name",userModel.getUser_Name());
                        sharedPref.putString("user_email",userModel.getUser_Email());
                        sharedPref.putString("user_image",userModel.getUser_Image());
                        sharedPref.putString("user_phone",userModel.getUser_Phone());
                        sharedPref.putString("user_password",userModel.getUser_Password());
                        Intent intent=new Intent(activity,MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        activity.startActivity(intent);
                        activity.finish();

                    }else if (response.body().getSuccess()==0){
                        Toast.makeText(activity, "هذا الايميل غير مسجل", Toast.LENGTH_LONG).show();
                   //     pDialog.dismiss();
                    }
                }catch (Exception e){
                    Toast.makeText(activity, R.string.check_your_internet, Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Responses.GetUserDetailsResponse> call, Throwable t) {
                Toast.makeText(activity,R.string.check_your_internet, Toast.LENGTH_LONG).show();
            }
        });

    }
//-------------------------------------------------------------------------------------------------------------




}
