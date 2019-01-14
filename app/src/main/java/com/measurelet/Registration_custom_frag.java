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

import java.util.ArrayList;
import java.util.Calendar;


public class Registration_custom_frag extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    private int ml;
    private String liqtyp;
    private EditText tastml;
    private EditText tastandet;
    private ImageButton add;
    private boolean andet;
    private Bundle bundle;

    Calendar calendar = Calendar.getInstance();



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

           // ml = Integer.parseInt(mil);

            bundle.putInt("liq",ml);


            tastandet.setVisibility(View.INVISIBLE);
            getView().findViewById(R.id.setsynligt).setVisibility(View.INVISIBLE);


            updateButtons(Dashboard_frag.knapperSeneste);
            updateButtons(Registration_standard_frag.knapper);


            Dashboard_frag.ml=Dashboard_frag.ml+ml;

            VæskeRegistrering registrering=new VæskeRegistrering();
            registrering.setType(liqtyp);
            registrering.setMængde(ml);
            registrering.setDate(calendar.getTime());

            Daily_view_frag.væskelistProeve.add(0,registrering) ;

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
            ((MainActivity) getActivity()).getAddAnimation(1);
            ((MainActivity) getActivity()).getNavC().navigate(R.id.action_global_dashboard_frag, bundle);
        }

    }

    private int getBillede(){
        int billede;
        if(liqtyp=="Sodavand"){
            billede=R.drawable.ic_soda;
        }
        else if(liqtyp=="Vand"){
            billede=R.drawable.ic_glass_of_water;
        }
        else if(liqtyp=="Kaffe"){
            billede=R.drawable.ic_coffee_cup;
        }
        else if(liqtyp=="Saftevand"){
            billede=R.drawable.ic_orange_juice;
        }
        else if(liqtyp=="Andet"){
            billede=R.drawable.ic_glass_of_water;
            liqtyp = tastandet.getText().toString();
        }
        else {
            billede= R.drawable.ic_glass_of_water;
        }
        return billede;
    }


    private void updateButtons(ArrayList<VaeskeKnap> knapper){

        VaeskeKnap knap2 = new VaeskeKnap();

        int billede = getBillede();
        boolean tilføj=false;

        for (VaeskeKnap knap :knapper) {
            if (knap.getMængde()==ml && knap.getType()==liqtyp) {
                tilføj = true;
                knap2=knap;
            }
        }

        if (tilføj==false){
            knapper.add(0, new VaeskeKnap(liqtyp,ml,billede));
            System.out.println(" 1: " + liqtyp +"  " + ml );
        }

        else if (tilføj=true){
            knapper.remove(knap2);
            knapper.add(0,knap2);
            System.out.println(" 2: " + knap2.getType() +knap2.getMængde() );
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
