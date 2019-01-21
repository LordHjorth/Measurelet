package com.measurelet;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hjorth.measurelet.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.measurelet.Factories.PatientFactory;
import com.measurelet.Model.Patient;

public class Profile_frag extends Fragment implements View.OnClickListener {


    private View profil;
    private TextInputEditText name, bed;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         profil = inflater.inflate(R.layout.profile_frag, container, false);

        Patient user = App.currentUser;

        // Name
         name = profil.findViewById(R.id.profile_name_input);
         
         name.setText(user.getName());
         bed = profil.findViewById(R.id.profile_bed_input);

         bed.setText(user.getBedNum()+ "");
        (profil.findViewById(R.id.profile_btn)).setOnClickListener(this);

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


    }
}
