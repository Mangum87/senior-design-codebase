package com.example.myapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.LoginStuff.Login;

public class Welcomescreen extends AppCompatActivity {

    private Button login;
    private Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // On create start
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomescreen);

        login = findViewById(R.id.button_login);
        signup = findViewById(R.id.button_signup);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(Welcomescreen.this, com.example.myapplication.LoginStuff.Login.class));
                Intent intent= new Intent(Welcomescreen.this,homescreen.class);
                startActivity(intent);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Welcomescreen.this, com.example.myapplication.LoginStuff.Registration.class));
            }
        });
    }

//     commented to go to dashboard without login
//    If the User is already logged in, take use straight to homescreen
    @Override
    protected void onStart() {
        super.onStart();
        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            Intent intent = new Intent(this, homescreen.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}
