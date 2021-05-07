package com.example.myapplication.LoginStuff;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.myapplication.R;
import com.example.myapplication.SharedPrefManager;
import com.example.myapplication.homescreen;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private TextInputLayout editTextUserName, editTextPassword;
    private TextView buttonText;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //intialize click listener on login screen
        findViewById(R.id.button_log_in).setOnClickListener(this);
        findViewById(R.id.login_close).setOnClickListener(this);

        //initialize to get the data entered in username and password box
        editTextUserName = findViewById(R.id.login_username);
        editTextPassword = findViewById(R.id.login_password);
        progressBar = findViewById(R.id.progressBarLoginButton);
        buttonText = findViewById(R.id.textViewLoginButton);
    }

    //shows welcome screen on arrow back (X) clicked
    private void showWelcomeScreen() {
        finish();
        overridePendingTransition(R.anim.no_animation, R.anim.bottom_down);
    }

    /**
     * checks user enter input
     * @return 'true' if field is not empty, 'false' if empty
     */
    private Boolean checkInput(String email, String password) {
        boolean check = false;
        editTextUserName.setError(null);
        editTextPassword.setError(null);

        if(email.isEmpty() && password.isEmpty()){
           showCustomAlertDialog("Invalid Input", "Username and password is Required");
        }
        else if (email.isEmpty()) {
            showCustomAlertDialog("Invalid Input", "Username is Required");
            check = false;
        }
        else if (password.isEmpty()) {
            showCustomAlertDialog("Invalid Input", "Password is Required");
            check = false;
        } else if (!email.isEmpty() && !password.isEmpty()){
            check = true;
        }
        return check;
    }

    private void userLogin() {
        String userName = editTextUserName.getEditText().getText().toString().trim();
        String password = editTextPassword.getEditText().getText().toString().trim();

        if (!checkInput(userName, password)) {
            return;
        } else {

            new MyTask().execute(userName, password);

        }
    }

    private void showCustomAlertDialog(String title, String message) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_custom_alert);
        TextView alertTitle = dialog.findViewById(R.id.alertTitle);
        TextView alertMessage = dialog.findViewById(R.id.alertMessage);
        ImageView imageView = dialog.findViewById(R.id.alertErrorImage);

        alertTitle.setText(title);
        alertMessage.setText(message);
        imageView.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_error));

        dialog.setCanceledOnTouchOutside(true);

        dialog.show();

        //time to dismiss the dialog
        final Timer t = new Timer();
        t.schedule(new TimerTask() {
            public void run() {
                dialog.dismiss();
                t.cancel();
            }
        }, 1500);
    }

    //listens to all click on login screen and calls appropriate function
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_log_in:
                userLogin();
                break;
            case R.id.login_close:
                showWelcomeScreen();
                break;
        }
    }

    class MyTask extends AsyncTask{

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            buttonText.setText("Please Wait");
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            /** Pass email and password entered by the user.Call LoginResponse that you can get from RetrofitClient  */

            String userName = (String) objects[0];
            String password = (String) objects[1];

            Call<LoginResponse> call = RetrofitClient.getInstance().getApi().userLogin(userName, password);

            /** To learn about public interface Call <T>
             *  https://square.github.io/retrofit/2.x/retrofit/retrofit2/Call.html
             */
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if(response.isSuccessful()) {
                        // body will return a loginResponse
                        LoginResponse loginResponse = response.body();
                        if (loginResponse.getStatus().equals("success")) {
                            progressBar.setVisibility(View.GONE);
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
                    }
                    else{
                        //handle the error response if the user credentials doesn't match
                        APIError message = new Gson().fromJson(response.errorBody().charStream(), APIError.class);
                        showCustomAlertDialog("Login Error", message.getMessage());

                        progressBar.setVisibility(View.GONE);
                        buttonText.setText("Login");
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    buttonText.setText("Login");
                    showCustomAlertDialog("Server Error","Unable to Login\n Failed to connect to the server");
                }
            });

            return null;
        }
    }
}
