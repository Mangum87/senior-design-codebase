package com.example.myapplication.LoginStuff;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.SharedPrefManager;
import com.example.myapplication.SplashScreen;
import com.example.myapplication.Welcomescreen;
import com.example.myapplication.amazonS3.pullBucketData;
import com.example.myapplication.homescreen;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity implements View.OnClickListener{
    private EditText editTextUserName;
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //intialize click listener on login screen
        findViewById(R.id.button_log_in).setOnClickListener(this);
        findViewById(R.id.login_arrow_back).setOnClickListener(this);

        //initialize to get the data entered in username and password box
        editTextUserName = findViewById(R.id.login_username);
        editTextPassword = findViewById(R.id.login_password);
    }

    //shows welcome screen on arrow back (<) clicked
    private void showWelcomeScreen(){
        Intent intent = new Intent(Login.this, Welcomescreen.class);
        startActivity(intent);
    }

    private void userLogin() {
        String email = editTextUserName.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        //Validation of Username can't be empty
        if (email.isEmpty()) {
            editTextUserName.setError("Username is required");
            editTextUserName.requestFocus();
            return;
        }
        /*
        Validation of password can't be empty
         */
        if (password.isEmpty()) {
            editTextPassword.setError("Password required");
            editTextPassword.requestFocus();
            return;
        }

        ////////////////////////////////////////////////////////////////////////////////
        //// Pass email and password entered by the user.Call LoginResponse that    ////
        //// you can get from RetrofitClient                                        ///
        ///////////////////////////////////////////////////////////////////////////////
        Call<LoginResponse> call = RetrofitClient.getInstance().getApi().userLogin(email, password);

        //To learn about public interface Call <T>
        //https://square.github.io/retrofit/2.x/retrofit/retrofit2/Call.html
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                   if(response.isSuccessful()) {
                       // body will return a loginResponse
                       LoginResponse loginResponse = response.body();
                       if (loginResponse.getStatus().equals("success")) {

                           //If authentication is successfull then save User and save LoginResponse
                           SharedPrefManager.getInstance(Login.this)
                                   .saveUser(loginResponse.getUser());
                           SharedPrefManager.getInstance(Login.this).saveLoginResponse(loginResponse);

                           //If the Login is Successfull then take the user to the homescreen.
                           Intent intent = new Intent(Login.this, homescreen.class);

                           //When we close and comeback we don't want user to see the login page.
                           //So, we need to set the flag.
                           intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                           startActivity(intent);
                       }
                       else {
                           Toast.makeText(Login.this, loginResponse.getMessage(), Toast.LENGTH_LONG).show();
                       }
                   }
                   else {
                       Toast.makeText(Login.this, "User does not exist.", Toast.LENGTH_LONG).show();
                   }
            }

            //if unable to connect to the server shows failure message to the screen
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(Login.this, "\t\t\t\t\tUnable to Login.\nFailed to Connect to the Server", Toast.LENGTH_LONG).show();
            }
        });
    }

    //listens to all click on login screen and calls appropriate function
        @Override
        public void onClick (View v){
            switch (v.getId()) {
                case R.id.button_log_in:
                    userLogin();
                    break;
                case R.id.login_arrow_back:
                    showWelcomeScreen();
                    break;
            }
        }
    }
