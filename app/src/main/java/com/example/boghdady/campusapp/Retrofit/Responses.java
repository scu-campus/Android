package com.example.boghdady.campusapp.Retrofit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by boghdady on 23/02/17.
 */

public class Responses {

    public static class GetUserDetailsResponse{
        ArrayList<Models.UserModel> Users;
        int success;

        public ArrayList<Models.UserModel> getUsers() {
            return Users;
        }

        public void setUsers(ArrayList<Models.UserModel> users) {
            Users = users;
        }

        public int getSuccess() {
            return success;
        }

        public void setSuccess(int success) {
            this.success = success;
        }
    }

    public static class DefaultResponse{
        int success;
        String message;

        public int getSuccess() {
            return success;
        }

        public void setSuccess(int success) {
            this.success = success;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

//---------------------------------------------------------------------------------------------------------

    public  static class  SignupResponse{
        List<Models.UserModel> Users;
        int success;
        String message;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public List<Models.UserModel> getUserModels() {
            return Users;
        }

        public void setUserModels(List<Models.UserModel> userModels) {
            this.Users = userModels;
        }

        public int getSuccess() {
            return success;
        }

        public void setSuccess(int success) {
            this.success = success;
        }
    }
}
