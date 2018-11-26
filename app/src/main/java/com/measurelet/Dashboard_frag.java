package com.measurelet;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hjorth.measurelet.R;

import androidx.navigation.fragment.NavHostFragment;

public class Dashboard_frag extends Fragment implements View.OnClickListener {
    ImageButton add_btn;
    TextView overall;
    LinearLayout mllayout;
    Button fone,ftwo,ftree,ffour;
    static int ml=0;
    static int overallml=2000;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View dashboard = inflater.inflate(R.layout.dashboard_frag, container, false);
        add_btn = dashboard.findViewById(R.id.add_btn);
        add_btn.setOnClickListener(this);
        mllayout= dashboard.findViewById(R.id.mllayout);
        mllayout.setOnClickListener(this);
        ffour=dashboard.findViewById(R.id.fav4_img);
        ffour.setOnClickListener(this);
        ftree=dashboard.findViewById(R.id.fav3_img);
        ftree.setOnClickListener(this);
        ftwo=dashboard.findViewById(R.id.fav2_img);
        ftwo.setOnClickListener(this);
        fone=dashboard.findViewById(R.id.fav1_img);
        fone.setOnClickListener(this);

        if(getArguments()!=null){
        ml=ml+getArguments().getInt("liq");
        }

        overall=dashboard.findViewById(R.id.registrated_amount);
        overall.setText(Integer.toString(ml)+"ml"+"/"+Integer.toString(overallml)+"ml");


        return dashboard;


    }

    @Override
    public void onClick(View view) {
        if (view == add_btn) {

            NavHostFragment.findNavController(this).navigate(R.id.action_dashboard_frag_to_registration_standard_frag);
        }
        if(view==mllayout){
                NavHostFragment.findNavController(this).navigate(R.id.action_dashboard_frag_to_daily_view_frag);
        }

        if(view==ffour){
                ml=ml+1000;
            overall.setText(Integer.toString(ml)+"ml"+"/"+Integer.toString(overallml)+"ml");
        }
        if(view==ftree){
                ml=ml+125;
            overall.setText(Integer.toString(ml)+"ml"+"/"+Integer.toString(overallml)+"ml");
        }
        if(view==ftwo){
                ml=ml+175;
            overall.setText(Integer.toString(ml)+"ml"+"/"+Integer.toString(overallml)+"ml");
        }
        if(view==fone){
                ml=ml+500;
            overall.setText(Integer.toString(ml)+"ml"+"/"+Integer.toString(overallml)+"ml");
        }

        }

}
