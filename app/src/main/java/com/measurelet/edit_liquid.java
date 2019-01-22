package com.measurelet;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;


import com.example.hjorth.measurelet.R;
import com.measurelet.Model.Intake;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;

public class edit_liquid extends DialogFragment implements View.OnClickListener, AdapterView.OnItemSelectedListener, TimePicker.OnTimeChangedListener {

    private EditText indtag, selftyped, amount_input;
    private TextView timer;
    private TimePicker timePicker;
    private Button  gemReg;
    private ImageButton sletReg,close;
    private ScrollView scrollView;
    private Spinner spinner;

    private Calendar calender = Calendar.getInstance();
    int hour, minute;
    private boolean other;
    private String type;
    private int position;
    //private String date;

    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        Bundle bundle = getArguments();
        if (bundle==null){
            dismiss();
            return null;
        }
        View view = inflater.inflate(R.layout.edit_liquid, container, false);



        position = bundle.getInt("position");

        type = App.currentUser.getRegistrations().get(position).getType();

        sletReg = view.findViewById(R.id.deleteReg);
        sletReg.setOnClickListener(this);
        gemReg = view.findViewById(R.id.saveChanges);
        gemReg.setOnClickListener(this);
        close =view.findViewById(R.id.close_button);
        close.setOnClickListener(this);

        selftyped = view.findViewById(R.id.selftyped1);
        amount_input = view.findViewById(R.id.value_weight);
        System.out.println(amount_input.getText() + ": Amount_input tekst - før");
        amount_input.setText(Integer.toString(App.currentUser.getRegistrations().get(position).getSize()));
        System.out.println(amount_input.getText() + ": Amount_input tekst - efter");


        timePicker = view.findViewById(R.id.timepicker);
        timePicker.setIs24HourView(true);
        timePicker.setOnTimeChangedListener(this);
        timePicker.setHour(App.currentUser.getRegistrations().get(position).getDateTime().getHour());
        timePicker.setMinute(App.currentUser.getRegistrations().get(position).getDateTime().getMinute());

        spinner = view.findViewById(R.id.scrollvalg1);
        timer = view.findViewById(R.id.timefield);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.fluidtypes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        System.out.println(spinner.getSelectedItem().toString() + ": Spinner tekst - " + type);
        initScrollValues(adapter);
        if(other == true){
            selftyped.setVisibility(View.VISIBLE);
        }
        System.out.println(spinner.getSelectedItem().toString() + ": Spinner tekst - " + type);

        return view;
    }






    @Override
    public void onClick(View vv) {

        if (vv == gemReg) {
            LocalDateTime date = LocalDate.now().atTime(timePicker.getHour(),timePicker.getMinute());

            if (other){
                if(selftyped.getText().toString().equals("")){
                    type="Andet";
                }
                else {
                    type = selftyped.getText().toString();
                }

            }
            Intake intake = new Intake(type, Integer.parseInt(amount_input.getText().toString()) , App.currentUser.getRegistrations().get(position).getUuid(), date.toString());
            App.currentUser.getRegistrations().remove(position);
            App.currentUser.getRegistrations().add(position, intake);

            ((MainActivity) getActivity()).getNavC().navigate(R.id.daily_view_frag);
            dismiss();
        }
        if (vv == sletReg) {
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
                            App.currentUser.getRegistrations().remove(position);
                            ((MainActivity) getActivity()).getNavC().navigate(R.id.daily_view_frag);
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

            //
            ad.show();


        }
        if (vv== close){
            dismiss();

        }
    }

    private void getTypeReg() {

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.fluidtypes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

        switch (position) {
            case 0:
                type = "Vand";
                break;
            case 1:
                type = "Kaffe";
                break;
            case 2:
                type = "Sodavand";
                break;
            case 3:
                type = "Saftevand";
                break;
            case 4:
                type = "Andet";
                break;
        }

        if (type.equals("Andet")) {
            other = true;
            selftyped.setVisibility(View.VISIBLE);
        } else {

            other = false;
            selftyped.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        this.hour = hourOfDay;
        this.minute = minute;

    }

    private void initScrollValues(ArrayAdapter<CharSequence> adapter) {


        int scrollPosition = adapter.getPosition(type);

        if (scrollPosition == -1) {
            other = true;
            selftyped.setText(type);
            scrollPosition = adapter.getPosition("Andet");
        }
        spinner.setSelection(scrollPosition, true);

    }
}
