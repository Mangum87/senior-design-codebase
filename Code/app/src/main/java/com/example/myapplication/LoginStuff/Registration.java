package com.example.myapplication.LoginStuff;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.myapplication.R;
import com.example.myapplication.Welcomescreen;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registration extends AppCompatActivity implements View.OnClickListener {

    private TextInputLayout editTextUsername, editTextPassword, editTextFname, editTextLname, editTextPhoneNumber, editTextEmail, editTextAddress, editTextCity, editTextState, editTextZipcode, editTextGender;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private ProgressBar progressBar;
    private TextView buttonText;

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
        radioGroup = findViewById(R.id.radio_gender_group);
        progressBar = findViewById(R.id.progressRegistrationSubmitButton);
        buttonText = findViewById(R.id.textViewRegistrationSubmitButton);

        //initialize click listener for register submit button and arrow back (<)
        findViewById(R.id.registration_close).setOnClickListener(this);
        findViewById(R.id.button_registration_submit).setOnClickListener(this);
    }

    //shows welcome screen on arrow back (<) clicked
    private void showWelcomeScreen() {
        finish();
        overridePendingTransition(R.anim.no_animation, R.anim.bottom_down);
    }

    /**
     * checks all the input is valid and not empty
     * @return 'true' if all input valid 'false' if any of the input in not valid
     */
    private Boolean checkInput(String userName, String password, String fName, String lName, String phoneNumber, String email, String address, String city, String state, String zipCode) {
        boolean check = false;
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        int lastChildPos = radioGroup.getChildCount() - 1;

        editTextUsername.setError(null);
        editTextPassword.setError(null);
        editTextFname.setError(null);
        editTextLname.setError(null);
        editTextPhoneNumber.setError(null);
        editTextEmail.setError(null);
        editTextAddress.setError(null);
        editTextCity.setError(null);
        editTextState.setError(null);
        editTextZipcode.setError(null);
        ((RadioButton) radioGroup.getChildAt(lastChildPos)).setError(null);

        if (userName.isEmpty()) {
            editTextUsername.setError("Username required");
            editTextUsername.requestFocus();
            check = false;
        }
        if (password.isEmpty()) {
            editTextPassword.setError("Password required");
            editTextPassword.requestFocus();
            check = false;
        }
        if (fName.isEmpty()) {
            editTextFname.setError("First Name required");
            editTextFname.requestFocus();
            check = false;
        }
        if (lName.isEmpty()) {
            editTextLname.setError("Last Name required");
            editTextLname.requestFocus();
            check = false;
        }
        if (phoneNumber.isEmpty()) {
            editTextPhoneNumber.setError("Phone Number required");
            editTextPhoneNumber.requestFocus();
            check = false;
        }
        if (!phoneNumber.isEmpty() && phoneNumber.length() != 10) {
            editTextPhoneNumber.setError("Must be 10 digits");
            editTextPhoneNumber.requestFocus();
            check = false;
        }
        if (email.isEmpty()) {
            editTextEmail.setError("Email required");
            editTextEmail.requestFocus();
            check = false;
        }
        if (!email.isEmpty() && !email.matches(emailPattern)) {
            editTextEmail.setError("Invalid email");
            editTextEmail.requestFocus();
            check = false;
        }
        if (address.isEmpty()) {
            editTextAddress.setError("Address required");
            editTextAddress.requestFocus();
            check = false;
        }
        if (city.isEmpty()) {
            editTextCity.setError("City required");
            editTextCity.requestFocus();
            check = false;
        }
        if (state.isEmpty()) {
            editTextState.setError("State required");
            editTextState.requestFocus();
            check = false;
        }
        if (zipCode.isEmpty()) {
            editTextZipcode.setError("ZipCode required");
            editTextZipcode.requestFocus();
            check = false;
        }
        if (!zipCode.isEmpty() && zipCode.length() != 5) {
            editTextZipcode.setError("Must be 5 digit");
            editTextZipcode.requestFocus();
            check = false;
        }
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            ((RadioButton) radioGroup.getChildAt(lastChildPos)).setError("Select One");
            check = false;
        } else if (!userName.isEmpty() && !password.isEmpty() && !fName.isEmpty() && !lName.isEmpty() && phoneNumber.length() == 10 && !email.isEmpty() && email.matches(emailPattern) && !address.isEmpty() &&
                !city.isEmpty() && !state.isEmpty() && zipCode.length() == 5 && radioGroup.getCheckedRadioButtonId() != -1) {
            check = true;
        }
        return check;
    }

    /** checks if all data is entered or not and processes it for submission */
    private void processRegistration() {
        RegisterUser userRequest = new RegisterUser();
        String username = editTextUsername.getEditText().getText().toString().trim();
        String password = editTextPassword.getEditText().getText().toString().trim();
        String fname = editTextFname.getEditText().getText().toString().trim();
        String lname = editTextLname.getEditText().getText().toString().trim();
        String phoneNumber = editTextPhoneNumber.getEditText().getText().toString().trim();
        String email = editTextEmail.getEditText().getText().toString().trim();
        String address = editTextAddress.getEditText().getText().toString().trim();
        String city = editTextCity.getEditText().getText().toString().trim();
        String state = editTextState.getEditText().getText().toString().trim();
        String zipcode = editTextZipcode.getEditText().getText().toString().trim();

        if (!checkInput(username, password, fname, lname, phoneNumber, email, address, city, state, zipcode)) {
            return;
        } else {
            int radioId = radioGroup.getCheckedRadioButtonId();
            radioButton = findViewById(radioId);
            String gender = (String) radioButton.getText();

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
            userRequest.setGender(gender);
            submit_Registration(userRequest);
        }
    }

    private void submit_Registration(RegisterUser userRequest) {

        progressBar.setVisibility(View.VISIBLE);
        buttonText.setText("Please Wait");

        Call<RegistrationResponse> registerResponse = RetrofitClient.getInstance().getApi().submitRegistration(userRequest);

        registerResponse.enqueue(new Callback<RegistrationResponse>() {
            @Override
            public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                if (response.isSuccessful()) {
                    RegistrationResponse registrationResponse = response.body();

                    if (registrationResponse.getStatus().equals("success")) {
                        showCustomAlertDialog("Registration", registrationResponse.getMessage(),true,false);

                        finish(); //finish the intent after succesful registration

                        startActivity(new Intent(Registration.this, com.example.myapplication.LoginStuff.Login.class));
                    }
                }
                else {
                    //handle the error response if the user credentials doesn't match
                    APIError message = new Gson().fromJson(response.errorBody().charStream(), APIError.class);
                    showCustomAlertDialog("Registration Error", message.getMessage(),false,true);
                    progressBar.setVisibility(View.GONE);
                    buttonText.setText("Submit");
                }
            }

            /** if unable to connect to the server shows failure message to the screen */
            @Override
            public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                buttonText.setText("Submit");
                showCustomAlertDialog("Server Error","Unable to Register\n Failed to connect to the server",false,true);
            }
        });
    }

    private void showCustomAlertDialog(String title, String message, Boolean registrationSuccessful, Boolean registrationUnSuccessful) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_custom_alert);
        TextView alertTitle = dialog.findViewById(R.id.alertTitle);
        TextView alertMessage = dialog.findViewById(R.id.alertMessage);
        ImageView imageView = dialog.findViewById(R.id.alertErrorImage);

        alertTitle.setText(title);
        alertMessage.setText(message);

        if(registrationUnSuccessful){
            imageView.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_error));
        }
        else if(registrationSuccessful){
            imageView.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_check));
        }

        dialog.setCanceledOnTouchOutside(true);

        dialog.show();

        //time to dismiss the dialog
        final Timer t = new Timer();
        t.schedule(new TimerTask() {
            public void run() {
                dialog.dismiss();
                t.cancel();
            }
        }, 2000);
    }

    /** listens to all click on Registration screen and calls appropriate function */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_registration_submit:
                processRegistration();
                break;
            case R.id.registration_close:
                showWelcomeScreen();
                break;
        }
    }
}