package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.animation.LinearInterpolator;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Welcomescreen extends AppCompatActivity {

    private Button login;
    private Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // On create start
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcomescreen);

        Window window = getWindow();
        Slide slide = new Slide();
        slide.setInterpolator(new LinearInterpolator());
        slide.setSlideEdge(Gravity.RIGHT);
        slide.excludeTarget(android.R.id.statusBarBackground, true);
        slide.excludeTarget(android.R.id.navigationBarBackground, true);
        window.setEnterTransition(slide);
        window.setReturnTransition(slide);

        login = findViewById(R.id.button_login);
        signup = findViewById(R.id.button_signup);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(Welcomescreen.this, com.example.myapplication.LoginStuff.Login.class));
                startActivity(new Intent(Welcomescreen.this, com.example.myapplication.homescreen.class));
                overridePendingTransition(R.anim.bottom_up,R.anim.no_animation);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Welcomescreen.this, com.example.myapplication.LoginStuff.Registration.class));
                overridePendingTransition(R.anim.bottom_up,R.anim.no_animation);
            }
        });
    }

    /** If the User is already logged in, take use straight to homescreen */
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
