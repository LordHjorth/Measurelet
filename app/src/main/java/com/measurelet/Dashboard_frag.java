package com.measurelet;

import android.app.AlertDialog;
import android.os.AsyncTask;
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
import java.util.List;
import java.util.concurrent.FutureTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Dashboard_frag extends Fragment implements  View.OnClickListener {
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
    private List<Intake> knapper;
    private ValueEventListener listener;
    private View dashboard;
    private SparkView sparkView;

    //TODO: make it possible to add goals (overallml)

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         dashboard = inflater.inflate(R.layout.dashboard_frag, container, false);

        add_btn = dashboard.findViewById(R.id.add_btn);
        add_btn.setOnClickListener(this);
        mllayout = dashboard.findViewById(R.id.mllayout);
        mllayout.setOnClickListener(this);

        recyclerView = dashboard.findViewById(R.id.dashboardrecycle);
        int numberOfColumns = 4;
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));

        //vægt
        vaegt_input_box = dashboard.findViewById(R.id.card_view_weight_dashboard);
        vaegt_Registreret_tekst = dashboard.findViewById(R.id.vaegt_registreret);

        vaegt = dashboard.findViewById(R.id.vaegt_edit);
        vaegt_knap = dashboard.findViewById(R.id.vagt_knap);
        vaegt_knap.setOnClickListener(this);

        sparkView = dashboard.findViewById(R.id.sparkview);
        sparkView.setLineColor(getActivity().getColor(R.color.colorPrimary));
        sparkView.setAlpha(0.3F);
        sparkView.setLineWidth(6F);
        sparkView.setSparkAnimator(new MorphSparkAnimator());


        new AsyncTask(){

            @Override
            protected Object doInBackground(Object[] objects) {

                while(App.currentUser == null){
                    try {
                        Thread.sleep( 30 );
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                buildView();

                listener = App.patientRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        buildView();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        System.out.println("Cancelled");
                    }
                });
            }
        }.execute();


        ((MainActivity) getActivity()).getSupportActionBar().show();


        return dashboard;
    }

    public void buildView(){

        Patient patient = App.currentUser;

        ArrayList<Intake> intakes = patient.getIntakesForDate(LocalDate.now());
        overall = dashboard.findViewById(R.id.registrated_amount);

        int m = 0;
        for (Intake i : intakes) {
            m += i.getSize();
        }
        overall.setText(m + " ml");


        new AsyncTask(){


            @Override
            protected Object doInBackground(Object[] objects) {

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

                return list;
            }

            @Override
            protected void onPostExecute(Object o) {
                List<Integer> list = (List<Integer>) o;

                super.onPostExecute(o);

                sparkView.setAdapter(new MyAdapter(list.toArray(new Integer[0])));

            }
        }.execute();



        UpdateButtons(patient.getRegistrations());


        // vægt
        ArrayList<Weight> weights = App.currentUser.getWeights();

        boolean show = true;

        for (Weight w : weights) {
            if (w.getDatetime().getDayOfYear() == LocalDate.now().getDayOfYear() && w.getDatetime().getYear() == LocalDate.now().getYear()) {
               show = false;
                break;
            }
        }

        if(show){
            dashboard.findViewById(R.id.card_view_weight_dashboard).setVisibility(View.VISIBLE);
            dashboard.findViewById(R.id.vaegt_registreret).setVisibility(View.INVISIBLE);
        } else {
            dashboard.findViewById(R.id.card_view_weight_dashboard).setVisibility(View.INVISIBLE);
            dashboard.findViewById(R.id.vaegt_registreret).setVisibility(View.VISIBLE);
        }

    }

    //REMOVE VALUE EVENTLISTNER...
    @Override
    public void onDestroy()
    {

        if (listener != null) {
            App.patientRef.removeEventListener(listener);
        }
        super.onDestroy();
    }

    private void UpdateButtons(ArrayList<Intake> registrations) {


        new AsyncTask(){


            @Override
            protected Object doInBackground(Object[] objects) {

                ArrayList<Intake> result = IntakeFactory.getIntakesListWithDefaults(registrations);

                List<Intake> k = result.subList(0, 4);
                return k;
            }

            @Override
            protected void onPostExecute(Object o) {
                List<Intake> k = (List<Intake>) o;
                adapter = new MyRecyclerViewAdapter(getActivity(),k);
                adapter.setClickListener((view, position) -> onItemClick(view, position));
                recyclerView.setAdapter(adapter);

                knapper = k;

            }
        }.execute();

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

    public void onItemClick(View view, int position) {
        ((MainActivity) getActivity()).getAddAnimation(1).playAnimation();

        Intake intake = new Intake(knapper.get(position).getType(), knapper.get(position).getSize());

        IntakeFactory.InsertNewIntake(intake);
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