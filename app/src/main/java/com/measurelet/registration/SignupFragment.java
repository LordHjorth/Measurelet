package com.measurelet.registration;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hjorth.measurelet.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.measurelet.App;
import com.measurelet.Factories.PatientFactory;
import com.measurelet.MainActivity;
import com.measurelet.Model.Patient;

import java.io.IOException;

import androidx.fragment.app.Fragment;


public class SignupFragment extends Fragment implements View.OnClickListener {


    private View fragment;
    private TextInputEditText name, bed;

    Patient patient;

    public SignupFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragment = inflater.inflate(R.layout.fragment_signup, container, false);

        ((Button) fragment.findViewById(R.id.signup_btn)).setOnClickListener(this);
        ((MainActivity) getActivity()).getSupportActionBar().hide();
        return fragment;
    }

    @Override
    public void onClick(View view) {


        bed = fragment.findViewById(R.id.signup_bed_txt);
        TextInputLayout bed_l = fragment.findViewById(R.id.signup_bed_txt_layout);

        name = fragment.findViewById(R.id.signup_name_txt);
        TextInputLayout name_l = fragment.findViewById(R.id.signup_name_txt_layout);

        // Validate input fields
        boolean error = false;



        if(name.getText().toString().equalsIgnoreCase("")){
            name_l.setError("Du skal indtaste dit navn.");
            error = true;
        } else {
            name_l.setErrorEnabled(false);
        }

        // Bed
        // Empty
        if(bed.getText().toString().equalsIgnoreCase("")){
            bed_l.setError("Du skal indtaste dit senge nummer. Ved problemer spørg personalet");
            error = true;
        } else {
            bed_l.setErrorEnabled(false);
        }


        if(error){
            return;
        }

        if(!App.isOnline()){
            Toast toast = Toast.makeText(this.getContext(), "Ingen internet forbindelse.\nLog på internettet, og forsøg igen", Toast.LENGTH_LONG);
            TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
            if( v != null) {
                v.setGravity(Gravity.CENTER);
            }
            toast.show();
            return;
        }


         ((MainActivity) getActivity()).getAddAnimation(2).playAnimation();
         new AsyncThread().execute();

    }

    private class AsyncThread extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {


            PatientFactory.InsertPatient(patient);

            //App.referenceStartUp();
            return null;
        }

        @Override
        protected void onPreExecute() {
            patient = new Patient(name.getText().toString(), Integer.parseInt(bed.getText().toString()));
            App.preferenceManager.edit().putString("KEY", patient.uuid).commit();
            App.setupRef(App.getAppDatabase(), patient.uuid);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            App.loggedIn = true;
            MainActivity activity = ((MainActivity) getActivity());
            activity.getAddAnimation(2).cancelAnimation();
            activity.setupListeners();
            ((MainActivity) getActivity()).getNavC().navigate(R.id.action_global_dashboard_frag);
        }
    }
}
