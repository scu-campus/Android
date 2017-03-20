package com.example.boghdady.campusapp.helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Dell-pc on 12/6/2015.
 */
public class SharedPref {

    private static SharedPref instance = null;
    private static final String DOCTOR_IMAGE = "DOCTOR_IMAGE";
    private static final String STUDENT_IMAGE= "STUDENT_IMAGE";


    public static SharedPreferences sharedPreferences;
    public static   SharedPreferences.Editor editor;
    private SharedPref(Context context) {
        sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        editor  = sharedPreferences.edit();
    }

    public static SharedPref getInstance(Context context){
        if (instance == null){
            instance = new SharedPref(context);
        }
        return instance;
    }

    public void putString(String key, String value){
        editor.putString(key, value);
        editor.commit();
    }
    public String getString(String key){
       return sharedPreferences.getString(key,"");
    }

    public void putLong(String key, Long value){
        editor.putLong(key, value);
        editor.commit();
    }
    public Long getLong(String key){
        return sharedPreferences.getLong(key,999);
    }
    public void putInt(String key, int value){
        editor.putInt(key, value);
        editor.commit();
    }
    public int getInt(String key){
        return sharedPreferences.getInt(key,0);
    }

    public void setDoctorImage(String img){
        putString(DOCTOR_IMAGE, img);
    }

    public String getDoctorImage(){
        return getString(DOCTOR_IMAGE);
    }

    public void setStudentImage(String img){
        putString(STUDENT_IMAGE, img);
    }

    public String getStudentImage(){
        return getString(STUDENT_IMAGE);
    }
}
