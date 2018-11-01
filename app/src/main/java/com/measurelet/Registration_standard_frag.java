package com.measurelet;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.hjorth.measurelet.R;

public class Registration_standard_frag extends Fragment implements View.OnClickListener {
    private Button soda;
    private Button juice;
    private Button coffee;
    private Button water;
    private Button pitcher;
    private Button other;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View standardfrag = inflater.inflate(R.layout.registration_standard_frag, container, false);
        soda = standardfrag.findViewById(R.id.sodavand);
        soda.setOnClickListener(this);
        juice = standardfrag.findViewById(R.id.juice);
        juice.setOnClickListener(this);
        coffee = standardfrag.findViewById(R.id.kaffe);
        coffee.setOnClickListener(this);
        water = standardfrag.findViewById(R.id.vand);
        water.setOnClickListener(this);
        pitcher = standardfrag.findViewById(R.id.saftevand);
        pitcher.setOnClickListener(this);
        other = standardfrag.findViewById(R.id.andet);
        other.setOnClickListener(this);


        return standardfrag;
    }

    @Override
    public void onClick(View view) {
        if (view == other) {
            Fragment custom = new Registration_custom_frag();
            FragmentManager fm = getFragmentManager();
            fm.beginTransaction().replace(R.id.mainfrag, custom, "cust").addToBackStack(null).commit();
        }




    }
}
