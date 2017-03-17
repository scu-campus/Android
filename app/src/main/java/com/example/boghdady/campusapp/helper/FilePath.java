package com.example.boghdady.campusapp.helper;

import android.content.Context;
import android.net.Uri;

import java.io.File;

/**
 * Created by Omar on 2/26/2017.
 */

public class FilePath {


    public static String getPath(Context context, String uri){
        String path = null;
        int flage = 1;
        File file = new File(uri);
        if(file != null && file.exists()) {
            path = file.getPath();
        }
        if (file == null || !(file.exists())) {
            flage = 2;
            try {
                file = new File(FileMetaData.getFileMetaData(context, Uri.parse(uri)).path);
                path = FileMetaData.getFileMetaData(context, Uri.parse(uri)).path;
            } catch (Exception e) {
                file = null;
            }
        }


        if (file == null || !(file.exists())) {
            flage = 3;
            try {
                file = new File(MyFile.getPath(context, Uri.parse(uri)));
                path = MyFile.getPath(context, Uri.parse(uri));
            }catch (Exception e){
                file = null;
            }
        }

        return  path;
    }
}
