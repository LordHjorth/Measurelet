package com.measurelet.dashboard;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.measurelet.App;
import com.measurelet.BaseFragment;
import com.measurelet.factories.IntakeFactory;
import com.measurelet.MainActivity;
import com.measurelet.model.Intake;

import java.util.ArrayList;
import java.util.List;


public class RegistrationCustomFrag extends BaseFragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    private String liqtyp;
    private EditText inputML, inputType;
    private Button add;
    private boolean other;

    private List<Intake> intakes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View fragment = inflater.inflate(R.layout.registration_custom_frag, container, false);

        TextView title = fragment.findViewById(R.id.drink);
        title.setText(R.string.drink);

        inputML = fragment.findViewById(R.id.amountofliquid);
        inputType = fragment.findViewById(R.id.selftyped);

        fragment.findViewById(R.id.setsynligt).setVisibility(View.INVISIBLE);

        add = fragment.findViewById(R.id.plusbut);
        add.setOnClickListener(this);

        other = false;

        Spinner spin = fragment.findViewById(R.id.scrollvalg);

        String[] items = new String[]{"Sodavand", "Vand", "Kaffe", "Saftevand", "Andet"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(this);

        addListener(App.intakeRef, new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                intakes = new ArrayList();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    intakes.add(snapshot.getValue(Intake.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return fragment;
    }

    @Override
    public void onClick(View view) {

        if (view == add) {

            //handles size
            String size_string = inputML.getText().toString();
            String type = inputType.getText().toString();

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


            inputType.setVisibility(View.INVISIBLE);
            getView().findViewById(R.id.setsynligt).setVisibility(View.INVISIBLE);

            //inserts to DB
            Intake intake = new Intake(liqtyp, size);
            IntakeFactory.InsertNewIntake(intakes, intake);

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
