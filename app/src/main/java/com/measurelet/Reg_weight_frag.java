package com.measurelet;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.hjorth.measurelet.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;


public class Reg_weight_frag extends Fragment implements View.OnClickListener {

    private EditText indtastningAfVaegt;
    private Button registrerVaegt;
    private ListView lsView;
    private String vægt;
    private ArrayList<String> vaegtListe = new ArrayList<>();
    private SimpleDateFormat fm;
    private Calendar cal;
    private ArrayAdapter<String> arrayAdapter;
    private Date today;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View regweight = inflater.inflate(R.layout.reg_weight_frag, container, false);
        fm= new SimpleDateFormat("dd-MM-yyyy");
        cal= Calendar.getInstance();
        lsView = regweight.findViewById(R.id.listviewVaegt);
        indtastningAfVaegt= regweight.findViewById(R.id.indtastVaegt);

        today=cal.getTime();
        registrerVaegt = regweight.findViewById(R.id.registrer);
        registrerVaegt.setOnClickListener(this);
        for(int i=0;i<10;i++){
          vaegtListe.add(fm.format(cal.getTime())+":                      "+(60
                  +i)+"kg");
         cal.add(Calendar.DATE,-1);


            arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, vaegtListe);
            lsView.setAdapter(arrayAdapter);

        }


        return regweight;
    }



    public void visVaegt(){
        vægt = indtastningAfVaegt.getText().toString();
        if(vægt.equals("")){
            vægt="66";
        }

        vaegtListe.add(0,fm.format(today)+":                      "+vægt+" kg");

        arrayAdapter.notifyDataSetChanged();


    }

    @Override
    public void onClick(View view) {
        ((MainActivity)getActivity()).getAddAnimation(); visVaegt();
    }
}
