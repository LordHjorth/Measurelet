package com.measurelet;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.hjorth.measurelet.R;

import java.util.ArrayList;
import java.util.Calendar;

public class Registration_standard_frag extends Fragment implements MyRecyclerViewAdapter.ItemClickListener,View.OnClickListener {
    private Button soda,juice,coffee,water,pitcher,other;
    private TextView tv;

    MyRecyclerViewAdapter adapter;
    public static ArrayList<VaeskeKnap> knapper=new ArrayList<>();

    Button knap;
    Calendar calendar = Calendar.getInstance();


    boolean hej=false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View standardfrag = inflater.inflate(R.layout.registration_standard_prove2, container, false);
       // tv=standardfrag.findViewById(R.id.cliquidtit);
       // tv.setText(R.string.choose);
       // tv.setPaintFlags(0);

        /*soda = standardfrag.findViewById(R.id.sodavand);
        soda.setOnClickListener(this);
        juice = standardfrag.findViewById(R.id.juice);
        juice.setOnClickListener(this);
        coffee = standardfrag.findViewById(R.id.kaffe);
        coffee.setOnClickListener(this);
        water = standardfrag.findViewById(R.id.vand);
        water.setOnClickListener(this);
        pitcher = standardfrag.findViewById(R.id.saftevand);
        pitcher.setOnClickListener(this);
        other = standardfrag.findViewById(R.id.andet);
        other.setOnClickListener(this);*/

        // data to populate the RecyclerView with

       updateKnapper();


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
    private void updateKnapper(){
        if(knapper.size()<5) {
            knapper.add(0, new VaeskeKnap("Juice", 125, R.drawable.ic_orange_juice));
            knapper.add(1, new VaeskeKnap("Vand", 250, R.drawable.ic_glass_of_water));
            knapper.add(2, new VaeskeKnap("Kaffe", 250, R.drawable.ic_coffee_cup));
            knapper.add(3, new VaeskeKnap("Sodavand", 500, R.drawable.ic_soda));
            knapper.add(4, new VaeskeKnap("Vand", 500, R.drawable.ic_glass_of_water));
        }
    }

    @Override
    public void onItemClick(View view, int position) {

        String type=knapper.get(position).getType();
        int mængde = knapper.get(position).getMængde();
        int thumbNail=knapper.get(position).getThumbnail();


        for (VaeskeKnap knap : Dashboard_frag.knapperSeneste) {
            if (knap.getMængde()==mængde && knap.getType()==type) {
               hej = true;
            }
        }
        if (hej==false){

            Dashboard_frag.knapperSeneste.add(0, new VaeskeKnap(type, mængde, thumbNail));
        }

        Dashboard_frag.ml=Dashboard_frag.ml+knapper.get(position).getMængde();

        VæskeRegistrering registrering=new VæskeRegistrering();
        registrering.setType(knapper.get(position).getType());
        registrering.setMængde(knapper.get(position).getMængde());
        registrering.setDate(calendar.getTime());

        Daily_view_frag.væskelistProeve.add(0,registrering) ;


        ((MainActivity)getActivity()).getAddAnimation();
        ((MainActivity) getActivity()).getNavC().navigate(R.id.action_global_dashboard_frag);

    }

    @Override
    public void onClick(View view) {
        if (view==knap) {
            ((MainActivity) getActivity()).getNavC().navigate(R.id.action_registration_standard_frag_to_registration_custom_frag);
        }
    }

    /*@Override
    public void onClick(View view) {
        if (view == other) {
            ((MainActivity) getActivity()).getNavC().navigate(R.id.action_registration_standard_frag_to_registration_custom_frag);

        } else {
            ((MainActivity)getActivity()).getAddAnimation();
            ((MainActivity) getActivity()).getNavC().navigate(R.id.action_global_dashboard_frag);



        }


    }
    */



}

