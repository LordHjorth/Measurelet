package com.measurelet;

import androidx.appcompat.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.text.Layout;
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
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.measurelet.Model.Intake;
import com.measurelet.Model.Patient;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;

public class edit_liquid extends DialogFragment implements View.OnClickListener, AdapterView.OnItemSelectedListener, TimePicker.OnTimeChangedListener {

    private EditText selftyped, amount_input;
    private TextInputLayout seltypedLayout;
    private TimePicker timePicker;
    private Button  gemReg;
    private ImageButton sletReg,close;
    private Spinner spinner;

    int hour, minute;
    private boolean other;

    private Intake intake;
    private int position;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.edit_liquid, container, false);

        Bundle bundle = getArguments();
        if (bundle==null){
            dismiss();
            return null;
        }

        String uuid = bundle.getString("uuid");

        position = 0;

        for (int i = 0; i < App.currentUser.getRegistrations().size(); i++) {
            Intake t = App.currentUser.getRegistrations().get(i);
            if(t.getUuid().equals(uuid)){
                position = i;
                intake = t;
                break;
            }
        }

        if(intake == null){
            dismiss();
            return view;
        }

        sletReg = view.findViewById(R.id.deleteReg);
        sletReg.setOnClickListener(this);
        gemReg = view.findViewById(R.id.saveChanges);
        gemReg.setOnClickListener(this);
        close =view.findViewById(R.id.close_button);
        close.setOnClickListener(this);

        selftyped = view.findViewById(R.id.selftyped1);
        seltypedLayout = view.findViewById(R.id.layout_liquid);
        amount_input = view.findViewById(R.id.amount);
        amount_input.setText(intake.getSize()+ "");

        timePicker = view.findViewById(R.id.timepicker);
        timePicker.setIs24HourView(true);
        timePicker.setOnTimeChangedListener(this);
        timePicker.setHour(intake.getDateTime().getHour());
        timePicker.setMinute(intake.getDateTime().getMinute());

        spinner = view.findViewById(R.id.scrollvalg1);

        //timer = view.findViewById(R.id.timefield);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.fluidtypes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        initScrollValues(adapter);

        if(other == true){
            selftyped.setVisibility(View.VISIBLE);
            seltypedLayout.setVisibility(View.VISIBLE);
        }

        return view;
    }






    @Override
    public void onClick(View vv) {

        if (vv == gemReg) {
            LocalDateTime date = LocalDate.now().atTime(timePicker.getHour(),timePicker.getMinute());

            if (other){
                if(selftyped.getText().toString().equals("")){
                    intake.setType("Andet");
                }
                else {
                    intake.setType(selftyped.getText().toString());
                }

            }

            intake.setSize(Integer.parseInt(amount_input.getText().toString()));
            intake.setTimestamp(date.toString());

            App.intakeRef.child(position+"").setValue(intake);

            dismiss();
        } else if (vv == sletReg) {
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
                            App.intakeRef.child(position+"").removeValue();
                            dismiss();
                        }
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
                intake.setType("Vand");
                break;
            case 1:
                intake.setType("Kaffe");
                break;
            case 2:
                intake.setType("Sodavand");
                break;
            case 3:
                intake.setType("Saftevand");
                break;
            case 4:
                intake.setType("Andet");
                break;
        }

        if (intake.getType().equals("Andet")) {
            other = true;
            selftyped.setVisibility(View.VISIBLE);
            seltypedLayout.setVisibility(View.VISIBLE);

        } else {
            other = false;
            selftyped.setVisibility(View.INVISIBLE);
            seltypedLayout.setVisibility(View.INVISIBLE);

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


        int scrollPosition = adapter.getPosition(intake.getType());

        if (scrollPosition == -1) {
            other = true;
            selftyped.setText(intake.getType());
            scrollPosition = adapter.getPosition("Andet");
        }
        spinner.setSelection(scrollPosition, true);

    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);

        // https://stackoverflow.com/questions/23786033/dialogfragment-and-ondismiss
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof DialogInterface.OnDismissListener) {
            ((DialogInterface.OnDismissListener) parentFragment).onDismiss(dialog);
        }
    }
}
