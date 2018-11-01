package com.measurelet;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.hjorth.measurelet.R;

public class Dashboard_frag extends Fragment implements View.OnClickListener {
    ImageButton add_btn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View dashboard = inflater.inflate(R.layout.dashboard_frag, container, false);

        add_btn = dashboard.findViewById(R.id.add_btn);
        add_btn.setOnClickListener(this);


        return dashboard;


    }

    @Override
    public void onClick(View view) {
        if (view == add_btn) {
            Fragment standfrag = new Registration_standard_frag();
            getFragmentManager().beginTransaction().replace(R.id.mainfrag, standfrag, "standfrag").addToBackStack(null).commit();
        }
    }
}
