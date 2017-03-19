package com.example.boghdady.campusapp.Registeration;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.boghdady.campusapp.NavigationDrawer.DoctorNavigationDrawer;
import com.example.boghdady.campusapp.NavigationDrawer.StudentNavigationDrawer;
import com.example.boghdady.campusapp.R;
import com.example.boghdady.campusapp.Retrofit.Interfaces;
import com.example.boghdady.campusapp.Retrofit.Models;
import com.example.boghdady.campusapp.Retrofit.Responses;
import com.example.boghdady.campusapp.Retrofit.SentBody;
import com.example.boghdady.campusapp.helper.AbstractRunTimePermission;
import com.example.boghdady.campusapp.helper.Constants;
import com.example.boghdady.campusapp.helper.CustomButton;
import com.example.boghdady.campusapp.helper.CustomEditText;
import com.example.boghdady.campusapp.helper.FilePath;
import com.example.boghdady.campusapp.helper.SharedPref;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DoctorSignupActivity extends AbstractRunTimePermission {

    private Activity activity;
    String ba1 = "";
    RelativeLayout relativeCircle;
    CustomEditText ed_DoctortName, ed_DoctorEmail, ed_DoctorPassword, ed_FacultyName , ed_DoctorPhone;
    CustomButton signupBtn;
    SharedPref sharedPref;
    LinearLayout skiptoHomeBtn;
    TextView plusTxt;
    CircularImageView profileImage;
    String picturePath;
    int ACCESS_STORAGE_PERMISSIONS_REQUEST = 1;
    String userName, email, password, phone , faculty;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_signup);

        init();
        skiptoHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoctorSignupActivity.this, DoctorNavigationDrawer.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        DoctorSignup();
    }

    public DoctorSignupActivity(){

    }
//------------------------------------------------------------------------------------------------------------------------------------------------
    void DoctorSignup(){
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = ed_DoctortName.getText().toString();
                email = ed_DoctorEmail.getText().toString();
                password = ed_DoctorPassword.getText().toString();
                faculty = ed_FacultyName.getText().toString();
                phone = ed_DoctorPhone.getText().toString();
                String check = "1";

                boolean validated = true;
                if (userName.equalsIgnoreCase("")) {
                    validated = false;
                    Toast.makeText(DoctorSignupActivity.this, R.string.please_enter_right_user_name, Toast.LENGTH_SHORT).show();
                }
                if (email.equalsIgnoreCase("")) {
                    validated = false;
                    Toast.makeText(DoctorSignupActivity.this, R.string.please_enter_right_email, Toast.LENGTH_SHORT).show();
                }
                if (password.equalsIgnoreCase("")) {
                    validated = false;
                    Toast.makeText(DoctorSignupActivity.this, R.string.please_enter_right_password, Toast.LENGTH_SHORT).show();
                }
                if (phone.equalsIgnoreCase("")) {
                    validated = false;
                    Toast.makeText(DoctorSignupActivity.this, R.string.please_enter_phone, Toast.LENGTH_SHORT).show();
                }
                if (validated) {

                    pDialog = new ProgressDialog(DoctorSignupActivity.this);
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
                    Interfaces.User_RegisterAPI registerInterface=retrofit.create(Interfaces.User_RegisterAPI.class);
                    Call<Responses.SignupResponse> call=registerInterface.insertUser(new SentBody.UserOperationBody(userName,email,password,ba1,phone,faculty , check));
                    call.enqueue(new Callback<Responses.SignupResponse>() {
                        @Override
                        public void onResponse(Call<Responses.SignupResponse> call, Response<Responses.SignupResponse> response) {
                            try {
                                if (response.body().getUserModels()!=null){

                                    SharedPref sharedPref=new SharedPref(getApplicationContext());
                                    Models.UserModel userModel = response.body().getUserModels().get(0);
                                    sharedPref.putString("doctor_id",userModel.getUser_ID());
                                    sharedPref.putString("doctor_name",userModel.getUser_Name());
                                    sharedPref.putString("doctor_image",userModel.getUser_Image());
                                    sharedPref.putString("doctor_email",userModel.getUser_Email());
                                    sharedPref.putString("doctor_phone",userModel.getUser_Phone());
                                    sharedPref.putString("doctor_faculty",userModel.getUser_Faculty());
                                    sharedPref.putString("doctor_password",userModel.getUser_Password());
                                    sharedPref.putString("checking",userModel.getChecking());

//                                    String token = FirebaseInstanceId.getInstance().getToken();
//                                    if(token != null){
//                                        Token.sendTokenToServer(getBaseContext(), token);
//                                    }
                                    Intent intent=new Intent(DoctorSignupActivity.this,DoctorNavigationDrawer.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                }
                                if (response.body().getSuccess()==0){
                                    Toast.makeText(DoctorSignupActivity.this, " "+response.body().getMessage(), Toast.LENGTH_LONG).show();
                                    pDialog.dismiss();
                                }
                                pDialog.dismiss();
                            }catch (Exception e){
                                e.getMessage();
                                pDialog.dismiss();
                            }
                        }
                        @Override
                        public void onFailure(Call<Responses.SignupResponse> call, Throwable t) {
                            try {
                                Toast.makeText(DoctorSignupActivity.this, R.string.check_your_internet, Toast.LENGTH_SHORT).show();
                                pDialog.dismiss();
                            }catch (Exception e){
                                pDialog.dismiss();
                            }
                        }
                    });
                }
            }
        });

        // get user image
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23) {
                    requestAppPermission(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.MANAGE_DOCUMENTS}, R.string.perrmission, ACCESS_STORAGE_PERMISSIONS_REQUEST);
                } else {
                    getImage();
                }
            }
        });
            }
//--------------------------------------------------------------------------------------------------------------------------------------------------
    @Override
    public void onPermissionGranted(int requestCode) {
        getImage();
    }
//---------------------------------------------------------------------------------------------------------------------------------------------------
    // constructor
    public DoctorSignupActivity(Activity activity) {
        this.activity = activity;
    }
//---------------------------------------------------------------------------------------------------------------------------------------------------

    void init(){
        signupBtn = (CustomButton) findViewById(R.id.signup_login);
        skiptoHomeBtn = (LinearLayout) findViewById(R.id.signup_skipTohome);
        relativeCircle = (RelativeLayout) findViewById(R.id.relative_circle);
        profileImage = (CircularImageView) findViewById(R.id.signup_upload_photo);
        plusTxt = (TextView) findViewById(R.id.signup_plus);

        ed_DoctortName = (CustomEditText) findViewById(R.id.ed_DoctortName);
        ed_DoctorEmail = (CustomEditText) findViewById(R.id.ed_DoctorEmail);
        ed_DoctorPassword = (CustomEditText) findViewById(R.id.ed_DoctorPassword);
        ed_FacultyName = (CustomEditText)findViewById(R.id.ed_FacultyName);
        ed_DoctorPhone = (CustomEditText) findViewById(R.id.ed_DoctorPhone);
        sharedPref = new SharedPref(getApplicationContext());
    }
//---------------------------------------------------------------------------------------------------------------------------------------------------
public void getImage() {
    final Dialog dialog = new Dialog(DoctorSignupActivity.this);
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
    dialog.setContentView(R.layout.dialog);
    LinearLayout cameraLayout = (LinearLayout) dialog.findViewById(R.id.linearLayout);
    LinearLayout galleryLaout = (LinearLayout) dialog.findViewById(R.id.gal_layout);
    TextView cancel = (TextView) dialog.findViewById(R.id.dialog_cancel_txt);
    cameraLayout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 2);
            dialog.dismiss();
        }
    });
    galleryLaout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, getString(R.string.select_picture)), 1);
            dialog.dismiss();
        }
    });
    cancel.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dialog.dismiss();
        }
    });
    dialog.setCancelable(true);
    dialog.show();
}
//---------------------------------------------------------------------------------------------------------------------------------------------------

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK && data != null && requestCode == 1) {
            Uri selectedImage = data.getData();

            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            picturePath =  FilePath.getPath(DoctorSignupActivity.this,selectedImage.toString());

            File sd = Environment.getExternalStorageDirectory();
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap sent = BitmapFactory.decodeFile(picturePath,bmOptions);
            sent = Bitmap.createScaledBitmap(sent,200,200,true);


//            Bitmap bm = BitmapFactory.decodeFile(picturePath);
//            Bitmap sent = Bitmap.createScaledBitmap(bm, 500, 500, true);

            plusTxt.setVisibility(View.INVISIBLE);
            // Set the Image in ImageView after decoding the String
            profileImage.setImageBitmap(sent);
            uploading(sent);
        } else if (resultCode == RESULT_OK && data != null && requestCode == 2) {
            Bundle bu = data.getExtras();
            Bitmap bit = (Bitmap) bu.get("data");
            Bitmap sent = Bitmap.createScaledBitmap(bit, 500, 500, true);
            plusTxt.setVisibility(View.INVISIBLE);


            profileImage.setImageBitmap(sent);
            uploading(sent);
        }
    }
//--------------------------------------------------------------------------------------------------------------------------------------------------

    public void uploading(Bitmap bitmap) {
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bao);
        byte[] ba = bao.toByteArray();
        ba1 = Base64.encodeToString(ba, 0);
        sharedPref.putString("profile_img_base64", ba1);

    }
}
