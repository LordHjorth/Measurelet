package com.measurelet;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.DialogFragment;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import android.widget.Toolbar;


import com.example.hjorth.measurelet.R;
import com.measurelet.Model.Intake;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class edit_liquid extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener, TimePicker.OnTimeChangedListener {

    private EditText indtag, selftyped, amount_input;
    private TextView timer;
    private TimePicker timePicker;
    private Button  gemReg;
    private ImageButton sletReg;
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

        View view = inflater.inflate(R.layout.edit_liquid, container, false);



        Bundle bundle = getArguments();
        position = bundle.getInt("position");

        type = App.currentUser.getRegistrations().get(position).getType();

        sletReg = view.findViewById(R.id.deleteReg);
        gemReg = view.findViewById(R.id.saveChanges);
        gemReg.setOnClickListener(this);

        selftyped = view.findViewById(R.id.selftyped1);
        amount_input = view.findViewById(R.id.amount_input);
        System.out.println(amount_input.getText() + ": Amount_input tekst - før");
        amount_input.setText(Integer.toString(App.currentUser.getRegistrations().get(position).getSize()));
        System.out.println(amount_input.getText() + ": Amount_input tekst - efter");


        timePicker = view.findViewById(R.id.timepicker);
        timePicker.setIs24HourView(true);
        timePicker.setOnTimeChangedListener(this);
        timePicker.setHour(App.currentUser.getRegistrations().get(position).getTimestamp().getHours());
        timePicker.setMinute(App.currentUser.getRegistrations().get(position).getTimestamp().getMinutes());

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
            Date date = new Date();
            date.setHours(timePicker.getHour());
            date.setMinutes(timePicker.getMinute());
            Intake intake = new Intake(selftyped.getText().toString(), Integer.parseInt(amount_input.getText().toString()) , App.currentUser.getRegistrations().get(position).getUuid(), date);
            App.currentUser.getRegistrations().remove(position);
            App.currentUser.getRegistrations().add(position, intake);
        }
        if (vv == sletReg) {
            App.currentUser.getRegistrations().remove(position);
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
