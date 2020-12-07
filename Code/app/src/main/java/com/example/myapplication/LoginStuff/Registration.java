package com.example.myapplication.LoginStuff;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.R;
import com.example.myapplication.Welcomescreen;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registration extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextFname;
    private EditText editTextLname;
    private EditText editTextPhoneNumber;
    private EditText editTextEmail;
    private EditText editTextAddress;
    private EditText editTextCity;
    private EditText editTextState;
    private EditText editTextZipcode;
    private EditText editTextGender;
    private RadioGroup radioGroup;
    private RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //initialize to get all user info from registration screen
        editTextUsername = findViewById(R.id.registration_username);
        editTextPassword = findViewById(R.id.registration_password);
        editTextFname = findViewById(R.id.registration_fname);
        editTextLname = findViewById(R.id.registration_lname);
        editTextPhoneNumber = findViewById(R.id.registration_phoneNumber);
        editTextEmail = findViewById(R.id.registration_email);
        editTextAddress = findViewById(R.id.registration_address);
        editTextCity = findViewById(R.id.registration_city);
        editTextState = findViewById(R.id.registration_state);
        editTextZipcode = findViewById(R.id.registration_zipcode);
        //radioGroup = findViewById(R.id.radio_gender_group);

        //initialize click listener for register submit button and arrow back (<)
        findViewById(R.id.registration_arrow_back).setOnClickListener(this);
        findViewById(R.id.button_registration_submit).setOnClickListener(this);
    }

    //shows welcome screen on arrow back (<) clicked
    private void showWelcomeScreen() {
        Intent intent = new Intent(Registration.this, Welcomescreen.class);
        startActivity(intent);
    }

    // checks if all data is entered or not and processes it for submission
    private void processRegistration() {
        RegisterUser entered_data = new RegisterUser();
        entered_data = saveRegistration();

        if (entered_data.get_check_entry()) {
            submit_Registration(saveRegistration());
        }
    }


    private RegisterUser saveRegistration() {

        RegisterUser userRequest = new RegisterUser();
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String fname = editTextFname.getText().toString().trim();
        String lname = editTextLname.getText().toString().trim();
        String phoneNumber = editTextPhoneNumber.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String address = editTextAddress.getText().toString().trim();
        String city = editTextCity.getText().toString().trim();
        String state = editTextState.getText().toString().trim();
        String zipcode = editTextZipcode.getText().toString().trim();

        if (username.isEmpty()) {
            editTextUsername.setError("Username is required");
            editTextUsername.requestFocus();
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
        }

        if (fname.isEmpty()) {
            editTextFname.setError("First Name is required");
            editTextFname.requestFocus();

        }
        if (lname.isEmpty()) {
            editTextLname.setError("Last Name is required");
            editTextLname.requestFocus();
        }

        if (phoneNumber.isEmpty()) {
            editTextPhoneNumber.setError("Phone Number is required");
            editTextPhoneNumber.requestFocus();
        }

        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
        }

        if (address.isEmpty()) {
            editTextAddress.setError("Address is required");
            editTextAddress.requestFocus();
        }

        if (city.isEmpty()) {
            editTextCity.setError("City is required");
            editTextCity.requestFocus();
        }

        if (state.isEmpty()) {
            editTextState.setError("State is required");
            editTextState.requestFocus();
        }

        if (zipcode.isEmpty()) {
            editTextZipcode.setError("Zipcode is required");
            editTextZipcode.requestFocus();
        } else {
            userRequest.setUname(username);
            userRequest.setPassword(password);
            userRequest.setFname(fname);
            userRequest.setLname(lname);
            userRequest.setPhoneNumber(phoneNumber);
            userRequest.setEmail(email);
            userRequest.setAddress(address);
            userRequest.setCity(city);
            userRequest.setState(state);
            userRequest.setZipcode(zipcode);
            userRequest.setCheck_entry(true);
        }
        return userRequest;
    }

    private void submit_Registration(RegisterUser userRequest) {

        Call<RegistrationResponse> registerResponse = RetrofitClient.getInstance().getApi().submitRegistration(userRequest);

        registerResponse.enqueue(new Callback<RegistrationResponse>() {
            @Override
            public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {

                if (response.isSuccessful()) {
                    RegistrationResponse registrationResponse = response.body();

                    if (registrationResponse.getStatus().equals("success")) {
                        Toast.makeText(Registration.this, "Successfully Registered", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(Registration.this, com.example.myapplication.LoginStuff.Login.class));
                    } else {
                        Toast.makeText(Registration.this, "User already exists", Toast.LENGTH_LONG).show();
                    }
                }
            }

            //if unable to connect to the server shows failure message to the screen
            @Override
            public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                Toast.makeText(Registration.this, "\t\t\t\t\tUnable to Register.\nFailed to Connect to the Server", Toast.LENGTH_LONG).show();
            }
        });
    }

    //listens to all click on Registration screen and calls appropriate function
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_registration_submit:
                processRegistration();
                break;
            case R.id.registration_arrow_back:
                showWelcomeScreen();
                break;
        }
    }
}




