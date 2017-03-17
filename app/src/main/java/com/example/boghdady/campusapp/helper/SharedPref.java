package com.example.boghdady.campusapp.helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Dell-pc on 12/6/2015.
 */
public class SharedPref {


    public static SharedPreferences sharedPreferences;
  public static   SharedPreferences.Editor editor;
    public SharedPref(Context context) {
        sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        editor  = sharedPreferences.edit();
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
}
