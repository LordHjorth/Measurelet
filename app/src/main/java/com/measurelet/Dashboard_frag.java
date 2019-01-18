package com.measurelet;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hjorth.measurelet.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.measurelet.Factories.IntakeFactory;
import com.measurelet.Factories.WeightFactory;
import com.measurelet.Model.Intake;
import com.measurelet.Model.Patient;
import com.measurelet.Model.Weight;
import com.robinhood.spark.SparkAdapter;
import com.robinhood.spark.SparkView;
import com.robinhood.spark.animation.MorphSparkAnimator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Dashboard_frag extends Fragment implements MyRecyclerViewAdapter.ItemClickListener, View.OnClickListener {
    private MaterialButton add_btn;
    private TextView overall;
    private LinearLayout mllayout;
    public static int ml = 0;
    private EditText vaegt;
    private Button vaegt_knap;
    private MaterialCardView vaegt_input_box;
    private TextView vaegt_Registreret_tekst;
    private MyRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;

    //TODO: make it possible to add goals (overallml)

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View dashboard = inflater.inflate(R.layout.dashboard_frag, container, false);

        add_btn = dashboard.findViewById(R.id.add_btn);
        add_btn.setOnClickListener(this);
        mllayout = dashboard.findViewById(R.id.mllayout);
        mllayout.setOnClickListener(this);

        recyclerView = dashboard.findViewById(R.id.dashboardrecycle);
        int numberOfColumns = 4;
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));

        updateKnapper();

        //vægt
        vaegt_input_box = dashboard.findViewById(R.id.card_view_weight_dashboard);
        vaegt_Registreret_tekst = dashboard.findViewById(R.id.vaegt_registreret);


        Log.d("Test", "hej");

        vaegt = dashboard.findViewById(R.id.vaegt_edit);
        vaegt_knap = dashboard.findViewById(R.id.vagt_knap);
        vaegt_knap.setOnClickListener(this);


        SparkView sparkView = dashboard.findViewById(R.id.sparkview);
        sparkView.setLineColor(getActivity().getColor(R.color.colorPrimary));
        (sparkView).setAlpha(0.3F);
        sparkView.setLineWidth(6F);
        sparkView.setSparkAnimator(new MorphSparkAnimator());

        App.patientRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println(dataSnapshot.getValue());

                Patient patient = dataSnapshot.getValue(Patient.class);

                ArrayList<Intake> intakes = patient.getIntakesForDate(LocalDate.now());
                overall = dashboard.findViewById(R.id.registrated_amount);

                int m = 0;

                ArrayList<Float> values = new ArrayList<>();
                for (Intake i : intakes) {
                    m += i.getSize();
                    values.add((float) i.getSize());
                }

                overall.setText(m + " ml");

                HashMap<String, Integer> v = IntakeFactory.getIntakePrHour(intakes);


                ArrayList<Integer> list = new ArrayList<>();
                int amount = 0;
                for (int i = 0; i < 24; i++) {
                    String key = String.format("%02d", i);
                    if (v.containsKey(key)) {
                        amount += v.get(key);
                    }
                    list.add(amount);
                }
                // Lets draw some stuff
                sparkView.setAdapter(new MyAdapter(list.toArray(new Integer[0])));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("Cancelled");
            }
        });

        App.weightRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Weight> weights = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    weights.add(child.getValue(Weight.class));
                }

                for (Weight w : weights) {
                    if (w.getDatetime().getDayOfYear() == LocalDate.now().getDayOfYear() && w.getDatetime().getYear() == LocalDate.now().getYear()) {
                        dashboard.findViewById(R.id.card_view_weight_dashboard).setVisibility(View.INVISIBLE);
                        dashboard.findViewById(R.id.vaegt_registreret).setVisibility(View.VISIBLE);
                        break;
                    }
                    dashboard.findViewById(R.id.card_view_weight_dashboard).setVisibility(View.VISIBLE);
                    dashboard.findViewById(R.id.vaegt_registreret).setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("Cancelled");
            }
        });


        adapter = new MyRecyclerViewAdapter(getActivity(), new ArrayList<>(Registration_standard_frag.knapper.subList(0, 4)));
        recyclerView.setAdapter(adapter);
        adapter.setClickListener(this);


        ((MainActivity) getActivity()).getSupportActionBar().show();


        return dashboard;
    }

    private void updateKnapper() {
        if (Registration_standard_frag.knapper.size() < 5) {
            Registration_standard_frag.knapper.add(0, new VaeskeKnap("Juice", 125, R.drawable.ic_orange_juice));
            Registration_standard_frag.knapper.add(1, new VaeskeKnap("Vand", 250, R.drawable.ic_glass_of_water));
            Registration_standard_frag.knapper.add(2, new VaeskeKnap("Kaffe", 250, R.drawable.ic_coffee_cup));
            Registration_standard_frag.knapper.add(3, new VaeskeKnap("Sodavand", 500, R.drawable.ic_soda));
            Registration_standard_frag.knapper.add(4, new VaeskeKnap("Vand", 500, R.drawable.ic_glass_of_water));
        }
    }

    @Override
    public void onClick(View view) {
        if (view == vaegt_knap) {
            String weightkg = vaegt.getText().toString();
            if (weightkg.equals("")) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Intet indtastet")
                        .setMessage("Hvad vejer du? Skriv din vægt i kg.")
                        .setCancelable(true)
                        .show();
                return;
            }

            Weight weight = new Weight(Double.parseDouble(weightkg));
            WeightFactory.InsertNewWeight(weight);

            vaegt_input_box.setVisibility(View.INVISIBLE);
            ((MainActivity) getActivity()).getAddAnimation(1);

            vaegt_Registreret_tekst.setVisibility(View.VISIBLE);
        }

        if (view == add_btn) {
            ((MainActivity) getActivity()).getNavC().navigate(R.id.action_dashboard_frag_to_registration_standard_frag);
        }
        if (view == mllayout) {

            ((MainActivity) getActivity()).getNavC().navigate(R.id.daily_view_frag);


        }
    }

    @Override
    public void onItemClick(View view, int position) {
        ((MainActivity) getActivity()).getAddAnimation(1).playAnimation();

        ml = ml + Registration_standard_frag.knapper.get(position).getMængde();

        Intake intake = new Intake(Registration_standard_frag.knapper.get(position).getType(), Registration_standard_frag.knapper.get(position).getMængde());
        IntakeFactory.InsertNewIntake(intake);


        App.currentUser.getRegistrations().add(0, intake);

        overall.setText(ml + " ml");
        App.currentUser.getIntakesForDate(LocalDate.now());
    }
}

class MyAdapter extends SparkAdapter {
    private Integer[] yData;

    public MyAdapter(Integer[] yData) {
        this.yData = yData;
    }

    @Override
    public int getCount() {
        return yData.length;
    }

    @Override
    public Object getItem(int index) {
        return yData[index];
    }

    @Override
    public float getY(int index) {
        return yData[index];
    }
}