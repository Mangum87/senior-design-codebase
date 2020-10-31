package com.example.myapplication.LoginStuff;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.SharedPrefManager;
import com.example.myapplication.homescreen;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Code used to handle logging in a user.
 */
public class Login extends AppCompatActivity implements View.OnClickListener{

    private TextView Register;
    private Button login;
    private EditText  editTextEmail;
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.register).setOnClickListener(this);
        findViewById(R.id.button_sign_in).setOnClickListener(this);
        editTextEmail= findViewById(R.id.comp_username);
        editTextPassword= findViewById(R.id.customer_pass);
        /*Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,UserRegistration.class));
            }
        });
        */

    }


    /**
     * Try to log user in.
     */
    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        /*
        Validation of Username can't be empty
         */
        if (email.isEmpty()) {
            editTextEmail.setError("Username is required");
            editTextEmail.requestFocus();
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


        /*
         Pass email and password entered by the user.Call LoginResponse that
         you can get from RetrofitClient
         */
        Call<LoginResponse> call = RetrofitClient
                .getInstance().getApi().userLogin(email, password);

        //To learn about public interface Call <T>
        //https://square.github.io/retrofit/2.x/retrofit/retrofit2/Call.html
        // Execute an asynchronous call
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response)
            {
                   if(response.isSuccessful()) {
                       // body will return a loginResponse
                       LoginResponse loginResponse = response.body();
                       if (loginResponse.getStatus().equals("success")) {
                           //Auth_token is toast to check if we received it or noe, you can
                           //change it to some welcome messages.
                           Toast.makeText(Login.this, loginResponse.getAuth_token(), Toast.LENGTH_LONG).show();

                           //If authentication is successfull then save User and save LoginResponse
                           SharedPrefManager.getInstance(Login.this)
                                   .saveUser(loginResponse.getUser());
                           SharedPrefManager.getInstance(Login.this).saveLoginResponse(loginResponse);
                           /*
                           If the Login is Successfull then take the user to the homescreen.
                            */
                           Intent intent = new Intent(Login.this, homescreen.class);
                           /*
                           When we close and comeback we don't want user to see the login page.
                           So, we need to set the flag.
                            */
                           intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                           startActivity(intent);
                       }
                       else {
                           Toast.makeText(Login.this, loginResponse.getMessage(), Toast.LENGTH_LONG).show();
                       }

                   }

                   else
                   {
                       Toast.makeText(Login.this, "User does not exist.", Toast.LENGTH_LONG).show();
                   }


            }



            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
            }
        });
    }
        @Override
        public void onClick (View v){
            switch (v.getId()) {
                case R.id.button_sign_in:
                    userLogin();
                    break;

            }
        }
    }
