package com.example.myapplication.LoginStuff;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.SharedPrefManager;
import com.example.myapplication.Welcomescreen;
import com.example.myapplication.homescreen;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

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
        if (email.isEmpty()) {
            editTextUserName.setError("Username required");
            editTextUserName.requestFocus();
            check = false;
        } if (password.isEmpty()) {
            editTextPassword.setError("Password required");
            editTextPassword.requestFocus();
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

            progressBar.setVisibility(View.VISIBLE);
            buttonText.setText("Please Wait");

            /** Pass email and password entered by the user.Call LoginResponse that you can get from RetrofitClient  */                                      ///
            Call<LoginResponse> call = RetrofitClient.getInstance().getApi().userLogin(userName, password);

            /** To learn about public interface Call <T>
             *  https://square.github.io/retrofit/2.x/retrofit/retrofit2/Call.html
             */
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.isSuccessful()) {
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
                        } else {
                            progressBar.setVisibility(View.GONE);
                            buttonText.setText("Login");
                            Toast.makeText(Login.this, loginResponse.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    buttonText.setText("Login");
                    Toast.makeText(Login.this, "\t\t\t\t\tUnable to Login.\nFailed to Connect to the Server", Toast.LENGTH_LONG).show();
                }
            });
        }
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
}
