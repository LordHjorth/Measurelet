package com.measurelet;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hjorth.measurelet.R;
import com.measurelet.Factories.IntakeFactory;
import com.measurelet.Factories.WeightFactory;
import com.measurelet.Model.Intake;
import com.measurelet.Model.Weight;

import java.util.ArrayList;
import java.util.Calendar;

public class Dashboard_frag extends Fragment implements MyRecyclerViewAdapter.ItemClickListener, View.OnClickListener {
    private ImageButton add_btn;
    private TextView overall;
    private LinearLayout mllayout;
    public static int ml = 0;
    private EditText vaegt;
    private Button vaegt_knap;
    private ConstraintLayout vaegtLayout;
    private ConstraintLayout vaegtRegistreret;
    private MyRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    private Calendar calendar = Calendar.getInstance();


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

        adapter = new MyRecyclerViewAdapter(getActivity(), new ArrayList<>(Registration_standard_frag.knapper.subList(0, 4)));
        recyclerView.setAdapter(adapter);
        adapter.setClickListener(this);

        //vægt
        vaegt = dashboard.findViewById(R.id.vaegt_edit);
        vaegt_knap = dashboard.findViewById(R.id.vagt_knap);
        vaegt_knap.setOnClickListener(this);

        vaegtLayout = dashboard.findViewById(R.id.vaegt);
        vaegtRegistreret = dashboard.findViewById(R.id.vaegt_registreret);

        overall = dashboard.findViewById(R.id.registrated_amount);
        overall.setText(ml + " ml");

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

            vaegtLayout.setVisibility(View.INVISIBLE);
            ((MainActivity) getActivity()).getAddAnimation(1);

            vaegtRegistreret.setVisibility(View.VISIBLE);
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

        VæskeRegistrering registrering = new VæskeRegistrering();
        registrering.setType(Registration_standard_frag.knapper.get(position).getType());
        registrering.setMængde(Registration_standard_frag.knapper.get(position).getMængde());
        registrering.setDate(calendar.getTime());

        Daily_view_frag.væskelistProeve.add(0, registrering);

        ml = ml + Registration_standard_frag.knapper.get(position).getMængde();

        Intake intake = new Intake(Registration_standard_frag.knapper.get(position).getType(), Registration_standard_frag.knapper.get(position).getMængde());
        IntakeFactory.InsertNewIntake(intake);

        overall.setText(ml + " ml");
    }
}
