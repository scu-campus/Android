package com.example.boghdady.campusapp.Retrofit;

/**
 * Created by boghdady on 23/02/17.
 */

public class Models {

    public static class UserModel {
        String User_ID, User_Email, User_Password, User_Image, User_Name, User_Phone, User_Faculty, Study_Year,
                Department, User_Section, Checking;

        public String getUser_ID() {
            return User_ID;
        }

        public void setUser_ID(String user_ID) {
            User_ID = user_ID;
        }

        public String getUser_Email() {
            return User_Email;
        }

        public void setUser_Email(String user_Email) {
            User_Email = user_Email;
        }

        public String getUser_Password() {
            return User_Password;
        }

        public void setUser_Password(String user_Password) {
            User_Password = user_Password;
        }

        public String getUser_Image() {
            return User_Image;
        }

        public void setUser_Image(String user_Image) {
            User_Image = user_Image;
        }

        public String getUser_Name() {
            return User_Name;
        }

        public void setUser_Name(String user_Name) {
            User_Name = user_Name;
        }

        public String getUser_Phone() {
            return User_Phone;
        }

        public void setUser_Phone(String user_Phone) {
            User_Phone = user_Phone;
        }

        public String getUser_Faculty() {
            return User_Faculty;
        }

        public void setUser_Faculty(String user_Faculty) {
            User_Faculty = user_Faculty;
        }

        public String getStudy_Year() {
            return Study_Year;
        }

        public void setStudy_Year(String study_Year) {
            Study_Year = study_Year;
        }

        public String getDepartment() {
            return Department;
        }

        public void setDepartment(String department) {
            Department = department;
        }

        public String getUser_Section() {
            return User_Section;
        }

        public void setUser_Section(String user_Section) {
            User_Section = user_Section;
        }

        public String getChecking() {
            return Checking;
        }

        public void setChecking(String checking) {
            Checking = checking;
        }
    }


    public static class LocationModel{
        int Location_ID;
        String Faculty_Name;
        Double Latitude , Longitude;

        public void setLocation_ID(int location_ID) {
            Location_ID = location_ID;
        }

        public void setFaculty_Name(String faculty_Name) {
            Faculty_Name = faculty_Name;
        }

        public void setLatitude(Double latitude) {
            Latitude = latitude;
        }

        public void setLongitude(Double longitude) {
            Longitude = longitude;
        }

        public int getLocation_ID() {
            return Location_ID;
        }

        public String getFaculty_Name() {
            return Faculty_Name;
        }

        public Double getLatitude() {
            return Latitude;
        }

        public Double getLongitude() {
            return Longitude;
        }
    }
}
