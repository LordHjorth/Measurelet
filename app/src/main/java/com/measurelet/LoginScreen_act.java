package com.measurelet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.hjorth.measurelet.R;

public class LoginScreen_act extends AppCompatActivity implements View.OnClickListener {
    Button next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen_act);
        next = findViewById(R.id.nextbutton);
        next.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == next) {
            startActivity(new Intent(this, MainActivity.class));


        }
    }
}
