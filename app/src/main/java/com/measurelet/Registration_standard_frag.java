package com.measurelet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hjorth.measurelet.R;
import com.google.android.material.button.MaterialButton;
import com.measurelet.Factories.IntakeFactory;
import com.measurelet.Model.Intake;

import java.util.ArrayList;
import java.util.Calendar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Registration_standard_frag extends Fragment implements MyRecyclerViewAdapter.ItemClickListener, View.OnClickListener {
    MyRecyclerViewAdapter adapter;
    public static ArrayList<VaeskeKnap> knapper = new ArrayList<>();

    MaterialButton knap;
    Calendar calendar = Calendar.getInstance();


    boolean hej = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View standardfrag = inflater.inflate(R.layout.registration_standard_prove2, container, false);

        // data to populate the RecyclerView with


        // set up the RecyclerView

        RecyclerView recyclerView = standardfrag.findViewById(R.id.standrecycle);
        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));

        adapter = new MyRecyclerViewAdapter(getActivity(), knapper);
        recyclerView.setAdapter(adapter);
        adapter.setClickListener(this);

        knap = standardfrag.findViewById(R.id.imageButton);
        knap.setOnClickListener(this);

        return standardfrag;
    }

    @Override
    public void onItemClick(View view, int position) {

        Dashboard_frag.ml = Dashboard_frag.ml + knapper.get(position).getMængde();

        VæskeRegistrering registrering = new VæskeRegistrering();
        registrering.setType(knapper.get(position).getType());
        registrering.setMængde(knapper.get(position).getMængde());
        registrering.setDate(calendar.getTime());

       // Daily_view_frag.væskelistProeve.add(0, registrering);

        Intake intake = new Intake(knapper.get(position).getType(), knapper.get(position).getMængde());
        IntakeFactory.InsertNewIntake(intake);

        VaeskeKnap temp = knapper.get(position);
        knapper.remove(position);
        knapper.add(0, temp);


        ((MainActivity) getActivity()).getAddAnimation(1).playAnimation();
        ((MainActivity) getActivity()).getNavC().navigate(R.id.action_global_dashboard_frag);

    }

    @Override
    public void onClick(View view) {
        if (view == knap) {
            ((MainActivity) getActivity()).getNavC().navigate(R.id.action_registration_standard_frag_to_registration_custom_frag);
        }
    }
}

