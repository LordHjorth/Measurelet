package com.measurelet;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hjorth.measurelet.R;
import com.measurelet.Factories.IntakeFactory;
import com.measurelet.Model.Intake;

import java.util.Date;
import java.util.UUID;

public class Dashboard_frag extends Fragment implements View.OnClickListener {
    ImageButton add_btn;
    TextView overall;
    LinearLayout mllayout;
    Button fone,ftwo,ftree,ffour;
    static int ml=0;
    static int overallml=2000;

    EditText vaegt;
    Button vaegt_knap;
    ConstraintLayout vaegtLayout;
    ConstraintLayout vaegtRegistreret;


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

        //vægt
        vaegt = dashboard.findViewById(R.id.vaegt_edit);
        vaegt_knap=dashboard.findViewById(R.id.vagt_knap);
        vaegt_knap.setOnClickListener(this);

        vaegtLayout=dashboard.findViewById(R.id.vaegt);
        vaegtRegistreret=dashboard.findViewById(R.id.vaegt_registreret);

        if(getArguments()!=null){
            ml=ml+getArguments().getInt("liq");
        }

        overall=dashboard.findViewById(R.id.registrated_amount);
        overall.setText(Integer.toString(ml)+"ml"+"/"+Integer.toString(overallml)+"ml");

        ((MainActivity) getActivity()).getSupportActionBar().show();

        return dashboard;


    }

    @Override
    public void onClick(View view) {

        if (view==vaegt_knap){
            vaegtLayout.setVisibility(View.INVISIBLE);
            ((MainActivity) getActivity()).getAddAnimation();

            vaegtRegistreret.setVisibility(View.VISIBLE);

            //vægt skal gemmes i databasen
        }

        if (view == add_btn) {
            ((MainActivity) getActivity()).getNavC().navigate(R.id.action_dashboard_frag_to_registration_standard_frag);
        }
        if (view == mllayout) {
            ((MainActivity) getActivity()).getNavC().navigate(R.id.daily_view_frag);
        }


        if (view!=add_btn&&view != mllayout&&view!=vaegt_knap) {
            Intake intake;
            if (view == ffour) {
                ml = ml + 1000;
                overall.setText(Integer.toString(ml) + "ml" + "/" + Integer.toString(overallml) + "ml");
                intake = new Intake("wasser", 1000);
                IntakeFactory.InsertNewIntake(intake);
            }
            if (view == ftree) {
                ml = ml + 125;
                overall.setText(Integer.toString(ml) + "ml" + "/" + Integer.toString(overallml) + "ml");
                intake = new Intake("wasser", 125);
                IntakeFactory.InsertNewIntake(intake);
            }
            if (view == ftwo) {
                ml = ml + 175;
                overall.setText(Integer.toString(ml) + "ml" + "/" + Integer.toString(overallml) + "ml");
                intake = new Intake("wasser", 175);
                IntakeFactory.InsertNewIntake(intake);
            }
            if (view == fone) {
                ml = ml + 500;
                overall.setText(Integer.toString(ml) + "ml" + "/" + Integer.toString(overallml) + "ml");
                intake = new Intake("3e371fb7-af79-4f8d-a8bf-bd67a4095909","Cola", 500, new Date());
                IntakeFactory.UpdateNewIntake(intake);

            }
            ((MainActivity) getActivity()).getAddAnimation();
        }


        }
}
