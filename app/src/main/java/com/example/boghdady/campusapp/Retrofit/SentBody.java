package com.example.boghdady.campusapp.Retrofit;

/**
 * Created by boghdady on 23/02/17.
 */

public class SentBody {

    public static class UserOperationBody{
        String User_ID,User_Name,User_Email,User_Password,User_Image, User_Phone,
        User_Faculty , Study_Year , Department , User_Section , Checking;

        // constructor for student signup
        public UserOperationBody(String user_Name, String user_Email, String user_Password, String user_Image, String user_Phone , String user_Faculty ,String study_Year , String department , String user_Section , String checking) {
            User_Name = user_Name;
            User_Email = user_Email;
            User_Password = user_Password;
            User_Image = user_Image;
            User_Phone = user_Phone;
            User_Faculty = user_Faculty;
            Study_Year = study_Year;
            Department = department;
            User_Section = user_Section;
            Checking = checking;
        }

        // constructor for doctor signup
        public UserOperationBody(String user_Name, String user_Email, String user_Password, String user_Image, String user_Phone , String user_Faculty , String checking) {
            User_Name = user_Name;
            User_Email = user_Email;
            User_Password = user_Password;
            User_Image = user_Image;
            User_Phone = user_Phone;
            User_Faculty = user_Faculty;
            Checking = checking;
        }

        // constructor for login
        public UserOperationBody(String user_Email, String user_Password) {
            User_Email = user_Email;
            User_Password = user_Password;
        }
    }
}
