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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

public class StudentSignupActivity extends AbstractRunTimePermission {

    String[] Department , Section , Study_Year ;
    ArrayAdapter SectionAdapter , DepartmentAdapter , StudyYearAdapter;
    Spinner spinner_studentSection , spinner_studentDepartment ,spinner_studentStudyYear ;

    private Activity activity;
    String ba1 = "";
    RelativeLayout relativeCircle;
    CustomEditText ed_studentName, ed_studentEmail, ed_studentPassword, ed_FacultyName , ed_studentPhone;
    CustomButton signupBtn;
    SharedPref sharedPref;
    LinearLayout skiptoHomeBtn;
    TextView plusTxt;
    CircularImageView profileImage;
    String picturePath;
    int ACCESS_STORAGE_PERMISSIONS_REQUEST = 1;
    String userName, email, password, phone , faculty ,
            Department_Spinner , Section_Spinner , StudyYear_Spinner;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_signup);

        init();
        FillSectionSpinner();
        FillStudentDepartmentSpinner();
        FillStudentStudyYearSpinner();

        skiptoHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentSignupActivity.this, StudentNavigationDrawer.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        StudentSignup();

    }

//-----------------------------------------------------------------------------------------------------------------------------------------------------
    void StudentSignup()
    {
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = ed_studentName.getText().toString();
                email = ed_studentEmail.getText().toString();
                password = ed_studentPassword.getText().toString();
                faculty = ed_FacultyName.getText().toString();
                phone = ed_studentPhone.getText().toString();
                String check = "0";

                boolean validated = true;
                if (userName.equalsIgnoreCase("")) {
                    validated = false;
                    Toast.makeText(StudentSignupActivity.this, R.string.please_enter_right_user_name, Toast.LENGTH_SHORT).show();
                }
                if (email.equalsIgnoreCase("")) {
                    validated = false;
                    Toast.makeText(StudentSignupActivity.this, R.string.please_enter_right_email, Toast.LENGTH_SHORT).show();
                }
                if (password.equalsIgnoreCase("")) {
                    validated = false;
                    Toast.makeText(StudentSignupActivity.this, R.string.please_enter_right_password, Toast.LENGTH_SHORT).show();
                }
                if (phone.equalsIgnoreCase("")) {
                    validated = false;
                    Toast.makeText(StudentSignupActivity.this, R.string.please_enter_phone, Toast.LENGTH_SHORT).show();
                }
                if (validated) {

                    pDialog = new ProgressDialog(StudentSignupActivity.this);
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
                    Call<Responses.SignupResponse> call=registerInterface.insertUser(new SentBody.UserOperationBody(userName,email,password,ba1,phone,faculty , StudyYear_Spinner,Department_Spinner,Section_Spinner , check));
                    call.enqueue(new Callback<Responses.SignupResponse>() {
                        @Override
                        public void onResponse(Call<Responses.SignupResponse> call, Response<Responses.SignupResponse> response) {
                            try {
                                if (response.body().getUserModels()!=null){

                                    SharedPref sharedPref=SharedPref.getInstance(getApplicationContext());
                                    Models.UserModel userModel = response.body().getUserModels().get(0);
                                    sharedPref.putString("student_id",userModel.getUser_ID());
                                    sharedPref.putString("student_name",userModel.getUser_Name());
                                    sharedPref.setStudentImage(userModel.getUser_Image());
                                    sharedPref.putString("student_email",userModel.getUser_Email());
                                    sharedPref.putString("student_phone",userModel.getUser_Phone());
                                    sharedPref.putString("student_faculty",userModel.getUser_Faculty());
                                    sharedPref.putString("student_password",userModel.getUser_Password());
                                    sharedPref.putString("student_section",userModel.getUser_Section());
                                    sharedPref.putString("student_department",userModel.getDepartment());
                                    sharedPref.putString("student_study_year",userModel.getStudy_Year());
                                    sharedPref.putString("checking",userModel.getChecking());

//                                    String token = FirebaseInstanceId.getInstance().getToken();
//                                    if(token != null){
//                                        Token.sendTokenToServer(getBaseContext(), token);
//                                    }
                                    Intent intent=new Intent(StudentSignupActivity.this,StudentNavigationDrawer.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                }
                                if (response.body().getSuccess()==0){
                                    Toast.makeText(StudentSignupActivity.this, " "+response.body().getMessage(), Toast.LENGTH_LONG).show();
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
                                Toast.makeText(StudentSignupActivity.this, R.string.check_your_internet, Toast.LENGTH_SHORT).show();
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

//----------------------------------------------------------------------------------------------------------------------------------------------------

@Override
public void onPermissionGranted(int requestCode) {

    getImage();
}

//---------------------------------------------------------------------------------------------------------------------------------------------------
    public void getImage() {
        final Dialog dialog = new Dialog(StudentSignupActivity.this);
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
            picturePath =  FilePath.getPath(StudentSignupActivity.this,selectedImage.toString());

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
//----------------------------------------------------------------------------------------------------------------------------------------------------
    void FillStudentStudyYearSpinner()
    {
        Study_Year = getResources().getStringArray(R.array.Study_Year);
        StudyYearAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, Study_Year);
        StudyYearAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spinner_studentStudyYear.setAdapter(StudyYearAdapter);

        spinner_studentStudyYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                StudyYear_Spinner = Study_Year[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
//----------------------------------------------------------------------------------------------------------------------------------------------------
    void FillStudentDepartmentSpinner()
    {
        Department = getResources().getStringArray(R.array.Department);
        DepartmentAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, Department);
        DepartmentAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spinner_studentDepartment.setAdapter(DepartmentAdapter);

        spinner_studentDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Department_Spinner = Department[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

//----------------------------------------------------------------------------------------------------------------------------------------------------
    void FillSectionSpinner()
    {
        Section = getResources().getStringArray(R.array.Section);
        SectionAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, Section);
        SectionAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spinner_studentSection.setAdapter(SectionAdapter);

        spinner_studentSection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               Section_Spinner = Section[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

//----------------------------------------------------------------------------------------------------------------------------------------------------
    void init()
    {
        spinner_studentSection = (Spinner) findViewById(R.id.spinner_studentSection);
        spinner_studentDepartment = (Spinner) findViewById(R.id.spinner_studentDepartment);
        spinner_studentStudyYear = (Spinner) findViewById(R.id.spinner_studentStudyYear);

        signupBtn = (CustomButton) findViewById(R.id.signup_login);
        skiptoHomeBtn = (LinearLayout) findViewById(R.id.signup_skipTohome);
        relativeCircle = (RelativeLayout) findViewById(R.id.relative_circle);
        profileImage = (CircularImageView) findViewById(R.id.signup_upload_photo);
        plusTxt = (TextView) findViewById(R.id.signup_plus);

        ed_studentName = (CustomEditText) findViewById(R.id.ed_studentName);
        ed_studentEmail = (CustomEditText) findViewById(R.id.ed_studentEmail);
        ed_studentPassword = (CustomEditText) findViewById(R.id.ed_studentPassword);
        ed_FacultyName = (CustomEditText)findViewById(R.id.ed_FacultyName);
        ed_studentPhone = (CustomEditText) findViewById(R.id.ed_studentPhone);
        sharedPref =  SharedPref.getInstance(getApplicationContext());

    }
}
