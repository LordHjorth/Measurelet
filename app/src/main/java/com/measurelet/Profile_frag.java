package com.measurelet;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hjorth.measurelet.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.measurelet.Model.Patient;

public class Profile_frag extends Fragment implements View.OnClickListener {

    Button search;
    TextView labelName, labelHeight;
    EditText inputName, inputWeight;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View profil = inflater.inflate(R.layout.profile_frag, container, false);


        search = profil.findViewById(R.id.save_changes);
        search.setOnClickListener(this);

        App.patientRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Patient p = dataSnapshot.getValue(Patient.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        labelName = profil.findViewById(R.id.profile_name_label);
        inputName = profil.findViewById(R.id.profile_name_input);

        labelHeight = profil.findViewById(R.id.profile_height_input);
        inputWeight = profil.findViewById(R.id.profile_height_label);

        return profil;

    }

    @Override
    public void onClick(View v) {

    }
}
