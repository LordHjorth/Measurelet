package com.measurelet.registration;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hjorth.measurelet.R;
import com.measurelet.App;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignupFragment extends Fragment implements View.OnClickListener {


    private View fragment;

    public SignupFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragment = inflater.inflate(R.layout.fragment_signup, container, false);

        ((Button)fragment.findViewById(R.id.signup_btn)).setOnClickListener(this);

        return fragment;
    }

    @Override
    public void onClick(View view) {
        String b = ((EditText) fragment.findViewById(R.id.signup_bed_txt)).getText().toString();

        int bed = !b.equals("") ? Integer.parseInt(b): 0;

        String name = ((EditText)fragment.findViewById(R.id.signup_name_txt)).getText().toString();

        App.HasloggedIn = true;

        getActivity().finish();

    }
}
