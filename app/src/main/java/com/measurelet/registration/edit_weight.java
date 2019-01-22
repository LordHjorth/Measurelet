package com.measurelet.registration;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.hjorth.measurelet.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.measurelet.MainActivity;

import androidx.fragment.app.DialogFragment;

public class edit_weight extends DialogFragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private Button gemReg;
    private ImageButton sletReg,close;

    private TextView dateWeight;
    private TextInputEditText amount_input;
    private TextInputLayout weightLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.edit_weight, container, false);

        Bundle bundle = getArguments();
        String date = bundle.getString("date");
        Double value = bundle.getDouble("value");

        if (bundle==null){
            //dismiss();
            return null;
        }


        sletReg = view.findViewById(R.id.deleteRegWeight);
        sletReg.setOnClickListener(this);
        gemReg = view.findViewById(R.id.saveChangesWeight);
        gemReg.setOnClickListener(this);
        close =view.findViewById(R.id.close_button_weight);
        close.setOnClickListener(this);

        dateWeight=view.findViewById(R.id.dateWeight);

        amount_input = view.findViewById(R.id.weight_input);
        weightLayout = view.findViewById(R.id.amount);



        amount_input.setText(value +"");
        dateWeight.setText(date);


        return view;
    }


    @Override
    public void onClick(View v) {
        if (v == sletReg) {
            Context context = getActivity();
            String title = "Slet";
            String message = "Er du sikker på du vil slette denne registrering?";
            String button1String = "Slet";
            String button2String = "Fortryd";

            AlertDialog.Builder ad = new AlertDialog.Builder(context);
            ad.setTitle(title);
            ad.setMessage(message);

            ad.setPositiveButton(
                    button1String,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int arg1) {
                           // App.currentUser.getRegistrations().remove(position);
                            ((MainActivity) getActivity()).getNavC().navigate(R.id.reg_weight_frag);
                            dismiss();                        }
                    }
            );
            ad.setNegativeButton(
                    button2String,
                    new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int arg1) {
                            // do nothing
                        }
                    }
            );
            ad.show();

        }
        if (v== close){
            dismiss();

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
