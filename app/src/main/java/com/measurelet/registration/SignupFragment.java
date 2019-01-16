package com.measurelet.registration;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.hjorth.measurelet.R;
import com.measurelet.App;
import com.measurelet.Factories.PatientFactory;
import com.measurelet.MainActivity;
import com.measurelet.Model.Patient;

public class SignupFragment extends Fragment implements View.OnClickListener {


    private View fragment;
    int bed;
    String name;
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
        EditText bView = fragment.findViewById(R.id.signup_bed_txt);
        String b = bView.getText().toString();
        EditText nView = fragment.findViewById(R.id.signup_name_txt);
        String n = nView.getText().toString();

        // Validate input fields
        boolean error = false;

        // Name
        // Empty
        if(n.equalsIgnoreCase("")){
            nView.setError("Du skal indtaste dit navn.");
            error = true;
        }

        // Bed
        // Empty
        if(b.equalsIgnoreCase("")){
            bView.setError("Du skal indtaste dit senge nummer. Ved problemer spørg personalet");
            error = true;
        }


        if(error){
           return;
        }

        name = n;
        bed = !b.equals("") ? Integer.parseInt(b) : 0;




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
            patient = new Patient(name, bed);
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
