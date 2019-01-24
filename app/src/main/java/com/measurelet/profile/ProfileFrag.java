package com.measurelet.profile;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hjorth.measurelet.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.measurelet.App;
import com.measurelet.BaseFragment;
import com.measurelet.factories.PatientFactory;
import com.measurelet.model.Patient;

public class ProfileFrag extends BaseFragment implements View.OnClickListener {


    private View profil;
    private TextInputEditText name, bed;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         profil = inflater.inflate(R.layout.profile_frag, container, false);

        // Name
         name = profil.findViewById(R.id.profile_name_input);
         
         bed = profil.findViewById(R.id.profile_bed_input);

        profil.findViewById(R.id.profile_btn).setOnClickListener(this);

        addListener(App.patientRef, new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Patient user = dataSnapshot.getValue(Patient.class);
                name.setText(user.getName());
                bed.setText(user.getBedNum()+ "");



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return profil;

    }

    @Override
    public void onClick(View v) {


        // Name
        TextInputLayout name_l = profil.findViewById(R.id.profile_name_input_layout);

        // bed
        TextInputLayout bed_l = profil.findViewById(R.id.profile_bed_input_layout);

        // Validate input fields
        boolean error = false;

        // Name
        // Empty
        if(name.getText().toString().equalsIgnoreCase("")){
            name_l.setError("Du skal indtaste dit navn.");
            error = true;
        }

        // Bed
        // Empty
        if(bed.getText().toString().equalsIgnoreCase("")){
            bed_l.setError("Du skal indtaste dit senge nummer. Ved problemer spørg personalet");
            error = true;
        }

        if(error){
            return;
        }

        // Opdater bruger
        PatientFactory.UpdatePatient(name.getText().toString(),Integer.parseInt(bed.getText().toString()));
        Toast toast = Toast.makeText(getContext(), "Profil opdateret!", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0 ,0);
        toast.show();

    }
}
