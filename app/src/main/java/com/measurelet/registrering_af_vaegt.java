package com.measurelet;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;


import com.example.hjorth.measurelet.R;

import java.util.Date;
import java.util.List;

public class registrering_af_vaegt extends AppCompatActivity {

    private EditText indtastningAfVaegt;
    private Button registrerVaegt;
    private ListView lsView;
    public String vægt;
    public String dinVaegt;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrering_af_vaegt);
        getSupportActionBar().hide();

        lsView = findViewById(R.id.listviewVaegt);


        registrerVaegt = findViewById(R.id.registrer);
        registrerVaegt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                indtastningAfDinVaegt();
                visVaegt();
            }
        });


    }





    //indtastning af vægt
    public String indtastningAfDinVaegt(){
        String minVaegt = indtastningAfVaegt.getText().toString();


        return minVaegt;
    }


    public void visVaegt(){

        ArrayList<Integer> vaegtListe = new ArrayList<>();
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, vaegtListe);

        vægt = indtastningAfVaegt.getText().toString();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        dinVaegt = sharedPreferences.getString("MY_DATA", "");
        editor = sharedPreferences.edit();
        editor.putString("MY_DATA"+dinVaegt, vægt);
        editor.apply();
        editor.commit();


        lsView.setAdapter(arrayAdapter);

    }
}
