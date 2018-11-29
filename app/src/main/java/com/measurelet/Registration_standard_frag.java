package com.measurelet;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.hjorth.measurelet.R;

public class Registration_standard_frag extends Fragment implements View.OnClickListener {
    private Button soda,juice,coffee,water,pitcher,other;
    private TextView tv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View standardfrag = inflater.inflate(R.layout.registration_standard_frag, container, false);
        tv=standardfrag.findViewById(R.id.cliquidtit);
        tv.setText(R.string.choose);
        tv.setPaintFlags(0);
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
            ((MainActivity) getActivity()).getNavC().navigate(R.id.action_registration_standard_frag_to_registration_custom_frag);

        } else {
            ((MainActivity)getActivity()).getAddAnimation();
            ((MainActivity) getActivity()).getNavC().navigate(R.id.action_registration_standard_frag_to_dashboard_frag);



        }


    }



}
