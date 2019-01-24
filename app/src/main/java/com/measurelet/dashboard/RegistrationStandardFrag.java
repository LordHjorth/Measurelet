package com.measurelet.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hjorth.measurelet.R;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.measurelet.App;
import com.measurelet.BaseFragment;
import com.measurelet.factories.IntakeFactory;
import com.measurelet.MainActivity;
import com.measurelet.model.Intake;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RegistrationStandardFrag extends BaseFragment implements View.OnClickListener {
    MyRecyclerViewAdapter adapter;

    MaterialButton knap;

    private ArrayList<Intake> knapper = new ArrayList();
    private ArrayList<Intake> intakes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.registration_standard_prove2, container, false);

        // set up the RecyclerView
        RecyclerView recyclerView = fragment.findViewById(R.id.standrecycle);

        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));

        knap = fragment.findViewById(R.id.imageButton);
        knap.setOnClickListener(this);

        addListener(App.intakeRef, new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                intakes = new ArrayList();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    intakes.add(snapshot.getValue(Intake.class));
                }

                knapper = IntakeFactory.getIntakesListWithDefaults(intakes);
                adapter = new MyRecyclerViewAdapter(getActivity(), knapper);
                recyclerView.setAdapter(adapter);
                adapter.setClickListener((view, position) -> {

                    Intake intake = new Intake(knapper.get(position).getType(), knapper.get(position).getSize());

                    IntakeFactory.InsertNewIntake(intakes, intake);

                    ((MainActivity) getActivity()).getAddAnimation(1).playAnimation();
                    ((MainActivity) getActivity()).getNavC().navigate(R.id.action_global_dashboard_frag);
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return fragment;
    }

    @Override
    public void onClick(View view) {
        if (view == knap) {
            ((MainActivity) getActivity()).getNavC().navigate(R.id.action_registration_standard_frag_to_registration_custom_frag);
        }
    }
}

