package com.measurelet.daily;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.example.hjorth.measurelet.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.measurelet.App;
import com.measurelet.model.Intake;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

public class EditLiquidDialog extends DialogFragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private EditText typed, amount_input;
    private TimePicker timePicker;
    private Button  gemReg;
    private ImageButton sletReg,close;
    private Spinner spinner;

    private boolean other;

    private Intake intake;
    private int position;
    private View seltypedLayout;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.edit_liquid, container, false);


        sletReg = view.findViewById(R.id.deleteReg);
        sletReg.setOnClickListener(this);
        gemReg = view.findViewById(R.id.saveChanges);
        gemReg.setOnClickListener(this);
        close =view.findViewById(R.id.close_button);
        close.setOnClickListener(this);
        typed = view.findViewById(R.id.selftyped1);
        seltypedLayout = view.findViewById(R.id.layout_liquid);
        amount_input = view.findViewById(R.id.amount);

        timePicker = view.findViewById(R.id.timepicker);
        timePicker.setIs24HourView(true);
        spinner = view.findViewById(R.id.scrollvalg1);

        //timer = view.findViewById(R.id.timefield);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.fluidtypes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        Bundle bundle = getArguments();
        if (bundle==null){
            dismiss();
            return null;
        }

        String uuid = bundle.getString("uuid");

        App.intakeRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                intake = null;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Intake i = snapshot.getValue(Intake.class);
                    position = Integer.parseInt(snapshot.getKey());

                    if(i.uuid.equals(uuid)){
                        intake = i;
                        position = Integer.parseInt(snapshot.getKey());
                        break;
                    }
                }

                if(intake == null){
                    dismiss();
                    return;
                }

                amount_input.setText(intake.getSize()+ "");
                timePicker.setHour(intake.getDateTime().getHour());
                timePicker.setMinute(intake.getDateTime().getMinute());

                initScrollValues(adapter);

                if(other){
                    typed.setVisibility(View.VISIBLE);
                    seltypedLayout.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return view;
    }


    @Override
    public void onClick(View vv) {

        if (vv == gemReg) {
            LocalDateTime date = LocalDate.now().atTime(timePicker.getHour(),timePicker.getMinute());

            if (other){
                if(typed.getText().toString().equals("")){
                    intake.setType("Andet");
                }
                else {
                    intake.setType(typed.getText().toString());
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
                    (dialog, arg1) -> {
                        App.intakeRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                ArrayList<Intake> i = new ArrayList();

                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    i.add(snapshot.getValue(Intake.class));
                                }

                                i.remove(position);
                                App.intakeRef.setValue(i);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        dismiss();
                    }
            );

            ad.setNegativeButton(
                    button2String,
                    (dialog, arg1) -> {
                        // do nothing
                    }
            );

            //
            ad.show();

        }
        if (vv== close){
            dismiss();

        }
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
            typed.setVisibility(View.VISIBLE);
            seltypedLayout.setVisibility(View.VISIBLE);

        } else {
            other = false;
            typed.setVisibility(View.INVISIBLE);
            seltypedLayout.setVisibility(View.INVISIBLE);

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void initScrollValues(ArrayAdapter<CharSequence> adapter) {


        int scrollPosition = adapter.getPosition(intake.getType());

        if (scrollPosition == -1) {
            other = true;
            typed.setText(intake.getType());
            scrollPosition = adapter.getPosition("Andet");
        }
        spinner.setSelection(scrollPosition, true);

    }

}
