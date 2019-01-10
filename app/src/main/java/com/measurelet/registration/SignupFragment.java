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

/**
 * A simple {@link Fragment} subclass.
 */
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
        String b = ((EditText) fragment.findViewById(R.id.signup_bed_txt)).getText().toString();

        bed = !b.equals("") ? Integer.parseInt(b) : 0;

        name = ((EditText) fragment.findViewById(R.id.signup_name_txt)).getText().toString();


        new AsyncThread().execute();


        ((MainActivity) getActivity()).getNavC().navigate(R.id.action_global_dashboard_frag);

    }

    private class AsyncThread extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            App.referenceStartUp(App.getAppDatabase(), patient.uuid);
            return null;
        }

        @Override
        protected void onPreExecute() {
            patient = new Patient(name, bed);
            App.preferenceManager.edit().putString("KEY", patient.uuid).commit();
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            PatientFactory.InsertPatient(patient);
        }

    }
}
