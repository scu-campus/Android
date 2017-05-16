package com.example.boghdady.campusapp.Retrofit;

/**
 * Created by boghdady on 23/02/17.
 */

public class SentBody {


//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
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

//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public static class InserEventBody {

    String Event_Name, Event_Details, Event_Location_Lng, Event_Location_Lat,
            Event_Date, Event_Time, Event_Link;

    public InserEventBody(String event_Name, String event_Details, String event_Location_Lng, String event_Location_Lat, String event_Date, String event_Time, String event_Link) {
        Event_Name = event_Name;
        Event_Details = event_Details;
        Event_Location_Lng = event_Location_Lng;
        Event_Location_Lat = event_Location_Lat;
        Event_Date = event_Date;
        Event_Time = event_Time;
        Event_Link = event_Link;
    }
}
//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public static class InsertLocationID{
        int ID ;

        public InsertLocationID(int ID) {
            this.ID = ID;
        }
    }
}
