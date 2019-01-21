package com.measurelet;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hjorth.measurelet.R;
import com.measurelet.Factories.IntakeFactory;
import com.measurelet.Model.Intake;

import java.util.ArrayList;
import java.util.Calendar;


public class Registration_custom_frag extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    private String liqtyp;
    private EditText input_ml, input_type_other;
    private Button add;
    private boolean other;

    private Calendar calendar = Calendar.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View customfrag = inflater.inflate(R.layout.registration_custom_frag, container, false);

        TextView druk = customfrag.findViewById(R.id.drink);
        druk.setText(R.string.drink);
        input_ml = customfrag.findViewById(R.id.amountofliquid);
        input_type_other = customfrag.findViewById(R.id.selftyped);

        customfrag.findViewById(R.id.setsynligt).setVisibility(View.INVISIBLE);

        add = customfrag.findViewById(R.id.plusbut);
        add.setOnClickListener(this);
        other = false;

        Spinner spin = customfrag.findViewById(R.id.scrollvalg);
        String[] items = new String[]{"Sodavand", "Vand", "Kaffe", "Saftevand", "Andet"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(this);


        return customfrag;
    }

    @Override
    public void onClick(View view) {

        if (view == add) {

            //handles size
            String size_string = input_ml.getText().toString();
            String type = input_type_other.getText().toString();

            if (size_string.equals("")) {

                emptyFieldAlert("Hvor mange milliliter har du drukket?");
                return;
            }

            if (other) {
                if (type.equals("")) {
                    emptyFieldAlert("Hvilken type væske har du drukket?");
                    return;
                }
                liqtyp = type;
            }

            int size = Integer.parseInt(size_string);


            input_type_other.setVisibility(View.INVISIBLE);
            getView().findViewById(R.id.setsynligt).setVisibility(View.INVISIBLE);

            //inserts to DB
            Intake intake = new Intake(liqtyp, size);
            IntakeFactory.InsertNewIntake(intake);


            //navigates back to dashboard
            ((MainActivity) getActivity()).getAddAnimation(1).playAnimation();
            ((MainActivity) getActivity()).getNavC().navigate(R.id.action_global_dashboard_frag);
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

        switch (position) {
            case 0:
                liqtyp = "Sodavand";
                break;
            case 1:
                liqtyp = "Vand";
                break;
            case 2:
                liqtyp = "Kaffe";
                break;
            case 3:
                liqtyp = "Saftevand";
                break;
            case 4:
                liqtyp = "Andet";
                break;
        }

        if (liqtyp.equals("Andet")) {
            other = true;
            getView().findViewById(R.id.setsynligt).setVisibility(View.VISIBLE);
        } else {

            other = false;
            getView().findViewById(R.id.setsynligt).setVisibility(View.INVISIBLE);
        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(getActivity(), "Ingen drikkevarer valgt!", Toast.LENGTH_LONG).show();
    }

    private void emptyFieldAlert(String errorMsg) {
        new AlertDialog.Builder(getActivity())
                .setTitle("Intet indtastet")
                .setMessage(errorMsg)
                .setCancelable(true)
                .show();
    }


}
