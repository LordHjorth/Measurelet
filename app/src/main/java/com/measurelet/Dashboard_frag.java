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
import com.measurelet.Database.Database_Online.ChildDatabase;
import com.measurelet.Model.Intake;

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

        ((MainActivity) getActivity()).getSupportActionBar().show();

        return dashboard;


    }

    @Override
    public void onClick(View view) {

        if (view == add_btn) {
            ((MainActivity) getActivity()).getNavC().navigate(R.id.action_dashboard_frag_to_registration_standard_frag);
        }
        if (view == mllayout) {
            ((MainActivity) getActivity()).getNavC().navigate(R.id.action_dashboard_frag_to_daily_view_frag);
        }
        if (view!=add_btn&&view != mllayout) {
            Intake intake = new Intake();
            if (view == ffour) {
                ml = ml + 1000;
                overall.setText(Integer.toString(ml) + "ml" + "/" + Integer.toString(overallml) + "ml");
                intake = new Intake("wasser", 1000);
            }
            if (view == ftree) {
                ml = ml + 125;
                overall.setText(Integer.toString(ml) + "ml" + "/" + Integer.toString(overallml) + "ml");
                intake = new Intake("wasser", 125);
            }
            if (view == ftwo) {
                ml = ml + 175;
                overall.setText(Integer.toString(ml) + "ml" + "/" + Integer.toString(overallml) + "ml");
                intake = new Intake("wasser", 175);
            }
            if (view == fone) {
                ml = ml + 500;
                overall.setText(Integer.toString(ml) + "ml" + "/" + Integer.toString(overallml) + "ml");
                intake = new Intake("wasser", 500);

            }
            ChildDatabase.InsertNewIntake(intake);
            ((MainActivity) getActivity()).getAddAnimation();
        }
    }

    public void add1000mlToReg(){
        ml = ml + 1000;
        overall.setText(Integer.toString(ml) + "ml" + "/" + Integer.toString(overallml) + "ml");
    }

    /*
    @Override
    public void onStart(){


        super.onStart();

        FirebaseApp.initializeApp(this.getActivity());

        DatabaseReference fireBase;

        fireBase = FirebaseDatabase.getInstance().getReference();

        fireBase.child("Rasmus").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                add1000mlToReg();
                Log.d("Jobs done", "ML:");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    */

}
