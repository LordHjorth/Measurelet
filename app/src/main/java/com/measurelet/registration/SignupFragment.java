package com.measurelet.registration;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.hjorth.measurelet.R;
import com.measurelet.App;
import com.measurelet.Database.Database_Online.ChildDatabase;
import com.measurelet.Database.Database_Online.IntakeFirebase;
import com.measurelet.Database.Database_Online.PatientFirebase;
import com.measurelet.MainActivity;
import com.measurelet.Model.Patient;

import java.util.Date;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignupFragment extends Fragment implements View.OnClickListener {


    private View fragment;

    public SignupFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragment = inflater.inflate(R.layout.fragment_signup, container, false);

        ((Button)fragment.findViewById(R.id.signup_btn)).setOnClickListener(this);
        ((MainActivity) getActivity()).getSupportActionBar().hide();
        return fragment;
    }

    @Override
    public void onClick(View view) {
        String b = ((EditText) fragment.findViewById(R.id.signup_bed_txt)).getText().toString();

        int bed = !b.equals("") ? Integer.parseInt(b): 0;

        String name = ((EditText)fragment.findViewById(R.id.signup_name_txt)).getText().toString();

        PatientFirebase patient = new PatientFirebase(name, bed);
        ChildDatabase.InsertPatient(patient);

        IntakeFirebase intake = new IntakeFirebase("water", 1000);
        ChildDatabase.InsertNewIntake(intake);

        //Patient patient = new Patient(name);
        //App.local_database.patientDao().insert(patient);

        ((MainActivity) getActivity()).getNavC().navigate(R.id.action_global_dashboard_frag);

    }
}
