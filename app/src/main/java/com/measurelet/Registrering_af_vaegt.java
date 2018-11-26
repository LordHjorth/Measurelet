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

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.example.hjorth.measurelet.R;




public class Registrering_af_vaegt extends AppCompatActivity implements View.OnClickListener {

    private EditText indtastningAfVaegt;
    private Button registrerVaegt;
    private ListView lsView;
    private String vægt;
    private ArrayList<String> vaegtListe = new ArrayList<>();
    private SimpleDateFormat fm;
    private Calendar cal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrering_af_vaegt);
        getSupportActionBar().hide();
        fm= new SimpleDateFormat("dd-MM-yyyy");
        cal= Calendar.getInstance();
        lsView = findViewById(R.id.listviewVaegt);
        indtastningAfVaegt= findViewById(R.id.indtastVaegt);

        registrerVaegt = findViewById(R.id.registrer);
        registrerVaegt.setOnClickListener(this);
        for(int i=0;i<10;i++){
          vaegtListe.add(fm.format(cal.getTime())+" "+(60
                  +i)+"kg");
        cal.add(Calendar.DATE,-1);

        }

                visVaegt();

    }



    public void visVaegt(){
        vægt = indtastningAfVaegt.getText().toString();
        if(vægt==""){
            vægt="56";
        }

        vaegtListe.add(fm.format(cal.getTime())+" "+vægt+"kg");


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, vaegtListe);




        lsView.setAdapter(arrayAdapter);

    }

    @Override
    public void onClick(View view) {
        visVaegt();
    }
}
