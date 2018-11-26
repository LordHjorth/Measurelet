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

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;


public class Registration_custom_frag extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    private long ml;
    private String liqtyp;
    private EditText tastml;
    private EditText tastandet;
    private ImageButton add;
    private boolean andet = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View customfrag = inflater.inflate(R.layout.registration_custom_frag, container, false);
        TextView druk = customfrag.findViewById(R.id.drink);
        druk.setText(R.string.drink);
        TextView m = customfrag.findViewById(R.id.amounttitle);
        m.setText(R.string.amount);
        tastml = customfrag.findViewById(R.id.amountofliquid);
        tastandet = customfrag.findViewById(R.id.selftyped);
        tastandet.setVisibility(View.INVISIBLE);
        customfrag.findViewById(R.id.setsynligt).setVisibility(View.INVISIBLE);
        add = customfrag.findViewById(R.id.plusbut);
        add.setOnClickListener(this);


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
            String mil = tastml.getText().toString();

            if (mil.equals("")) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Intet indtastet")
                        .setMessage("Hvor mange milliliter har du drukket?")
                        .setCancelable(true)
                        .show();
                return;
            }
            ml = Integer.parseInt(mil);


            if (andet) {
                liqtyp = tastandet.getText().toString();
            }

            tastandet.setVisibility(View.INVISIBLE);
            getView().findViewById(R.id.setsynligt).setVisibility(View.INVISIBLE);

            ((MainActivity)getActivity()).getAddAnimation();
            NavHostFragment.findNavController(this).navigate(R.id.action_registration_custom_frag_to_dashboard_frag);
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        switch (position) {
            case 0:
                andet = false;
                liqtyp = "Sodavand";
                break;
            case 1:
                liqtyp = "Vand";
                andet = false;
                break;
            case 2:
                liqtyp = "Kaffe";
                andet = false;
                break;
            case 3:
                liqtyp = "Saftevand";
                andet = false;
                break;
            case 4:
                liqtyp = "Andet";
                andet = true;
                getView().findViewById(R.id.setsynligt).setVisibility(View.VISIBLE);
                tastandet.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(getActivity(), "Ingen drikkevarer valgt!", Toast.LENGTH_LONG).show();
    }


}
