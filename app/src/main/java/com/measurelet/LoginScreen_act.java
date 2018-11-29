package com.measurelet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.hjorth.measurelet.R;
import com.measurelet.registration.IntroSlidePager;

public class LoginScreen_act extends AppCompatActivity {


    private IntroSlidePager intro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen_act);

        intro = new IntroSlidePager();

        getSupportFragmentManager().beginTransaction().replace(R.id.login_fragment_frame, intro).commit();
        App.HasloggedIn=true;


    }


    @Override
    public void onBackPressed() {

        Fragment f = getSupportFragmentManager().findFragmentById(R.id.login_fragment_frame);
        
        if(f == intro){
            finishAffinity();
        } else {
            super.onBackPressed();
        }

    }
}
