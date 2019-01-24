package com.measurelet.weight;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.hjorth.measurelet.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.measurelet.App;
import com.measurelet.model.Weight;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class EditWeightDialog extends DialogFragment implements View.OnClickListener {

    private Button gemReg;
    private ImageButton sletReg,close;
    private TextInputEditText inputAmount;
    private int position;
    private TextView  dateWeight;
    private Weight weight = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.edit_weight, container, false);

        Bundle bundle = getArguments();

        if(bundle == null){
            dismiss();
            return view;
        }

        String uuid = bundle.getString("uuid");

        sletReg = view.findViewById(R.id.deleteRegWeight);
        sletReg.setOnClickListener(this);

        gemReg = view.findViewById(R.id.saveChangesWeight);
        gemReg.setOnClickListener(this);

        close =view.findViewById(R.id.close_button_weight);
        close.setOnClickListener(this);

        dateWeight = view.findViewById(R.id.dateWeight);
        inputAmount = view.findViewById(R.id.weight_input);


        App.weightRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                weight = null;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Weight w = snapshot.getValue(Weight.class);

                    if(w.uuid.equals(uuid)){
                        weight = w;
                        position = Integer.parseInt(snapshot.getKey());
                        break;
                    }
                }

                if (weight == null){
                    dismiss();
                    return;
                }

                inputAmount.setText(weight.getWeightKG() +"");
                dateWeight.setText(weight.getDatetime().format(WeightFrag.format));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return view;
    }


    @Override
    public void onClick(View v) {

        if (v==gemReg){

            weight.setWeightKG(Double.parseDouble(inputAmount.getText().toString()));

            App.weightRef.child(position+"").setValue(weight);

            dismiss();
        }
        else if (v == sletReg) {
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

                        App.weightRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                ArrayList<Weight> w = new ArrayList();

                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    w.add(snapshot.getValue(Weight.class));
                                }

                                w.remove(position);
                                App.weightRef.setValue(w);
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
            ad.show();

        }

        if (v== close){
            dismiss();
        }
    }
}
