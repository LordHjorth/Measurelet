package com.measurelet;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hjorth.measurelet.R;
import com.measurelet.Factories.IntakeFactory;
import com.measurelet.Model.Intake;


public class Registration_custom_frag extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    private int ml;
    private String liqtyp;
    private EditText tastml;
    private EditText tastandet;
    private ImageButton add;
    private boolean andet;
    private Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View customfrag = inflater.inflate(R.layout.registration_custom_frag, container, false);

        TextView druk = customfrag.findViewById(R.id.drink);
        druk.setText(R.string.drink);
        TextView m = customfrag.findViewById(R.id.amounttitle);
        m.setText(R.string.amount);
        tastml = customfrag.findViewById(R.id.amountofliquid);
        tastandet = customfrag.findViewById(R.id.selftyped);
        //tastandet.setVisibility(View.INVISIBLE);
        customfrag.findViewById(R.id.setsynligt).setVisibility(View.INVISIBLE);

        add = customfrag.findViewById(R.id.plusbut);
        add.setOnClickListener(this);
        bundle = new Bundle();
        andet = false;

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
            String size = tastml.getText().toString();

            if (size.equals("")) {
                emptyFieldAlert("Hvor mange milliliter har du drukket?");
                return;
            }
            ml = Integer.parseInt(size);
            bundle.putInt("liq", ml);

            //handles type
            String type = tastandet.getText().toString();
            if (andet) {
                if (type.equals("")) {
                    emptyFieldAlert("Hvilken type væske har du drukket?");
                    return;
                }
                liqtyp = tastandet.getText().toString();
            }

            //inserts to DB
            Intake intake = new Intake(liqtyp, ml);
            IntakeFactory.InsertNewIntake(intake);

            //navigates back to dashboard
            ((MainActivity) getActivity()).getAddAnimation();
            ((MainActivity) getActivity()).getNavC().navigate(R.id.action_global_dashboard_frag, bundle);
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
            andet = true;
            getView().findViewById(R.id.setsynligt).setVisibility(View.VISIBLE);
        } else {

            andet = false;
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
