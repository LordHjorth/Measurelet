package com.measurelet;

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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Dashboard_frag extends Fragment implements MyRecyclerViewAdapter.ItemClickListener,View.OnClickListener {
    ImageButton add_btn;
    TextView overall;
    LinearLayout mllayout;
    Button fone,ftwo,ftree,ffour;



    static int ml=0;
    static int overallml=2000;

    EditText vaegt;
    Button vaegt_knap;
    ConstraintLayout vaegtLayout;
    ConstraintLayout vaegtRegistreret;

    MyRecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    public static ArrayList<VaeskeKnap> knapperSeneste=new ArrayList<>();
    Calendar calendar = Calendar.getInstance();



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View dashboard = inflater.inflate(R.layout.dashboard_frag, container, false);
        add_btn = dashboard.findViewById(R.id.add_btn);
        add_btn.setOnClickListener(this);
        mllayout= dashboard.findViewById(R.id.mllayout);
        mllayout.setOnClickListener(this);


        updateFavoritter();

        //createData();
        recyclerView = dashboard.findViewById(R.id.item_recycler_view);
        int numberOfColumns = 4;
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));
        adapter = new MyRecyclerViewAdapter(getActivity(), knapperSeneste);
        recyclerView.setAdapter(adapter);
        adapter.setClickListener(this);


        //vægt
        vaegt = dashboard.findViewById(R.id.vaegt_edit);
        vaegt_knap=dashboard.findViewById(R.id.vagt_knap);
        vaegt_knap.setOnClickListener(this);

        vaegtLayout=dashboard.findViewById(R.id.vaegt);
        vaegtRegistreret=dashboard.findViewById(R.id.vaegt_registreret);

        if(getArguments()!=null){
        //ml=ml+getArguments().getInt("liq");
        }

        overall=dashboard.findViewById(R.id.registrated_amount);
        overall.setText(ml+" ml"+"/"+overallml+" ml");

        ((MainActivity) getActivity()).getSupportActionBar().show();



        return dashboard;


    }

    private void updateFavoritter(){
        if (knapperSeneste.size()==0) {
            knapperSeneste.add(0,new VaeskeKnap("Vand", 175, R.drawable.ic_front_water));
            knapperSeneste.add(1,new VaeskeKnap("Kaffe", 175, R.drawable.ic_front_coffee));
            knapperSeneste.add(2,new VaeskeKnap("Saft", 175, R.drawable.ic_front_water));
            knapperSeneste.add(3,new VaeskeKnap("Juice", 150, R.drawable.ic_front_juice));
        }
        if(knapperSeneste.size()>4){
            knapperSeneste.remove(4);

        }
    }
    @Override
    public void onClick(View view) {

        if (view==vaegt_knap){
            vaegtLayout.setVisibility(View.INVISIBLE);
            ((MainActivity) getActivity()).getAddAnimation();

            vaegtRegistreret.setVisibility(View.VISIBLE);

            //vægt skal gemmes i databasen
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
        ((MainActivity)getActivity()).getAddAnimation();

        VæskeRegistrering registrering=new VæskeRegistrering();
        registrering.setType(knapperSeneste.get(position).getType());
        registrering.setMængde(knapperSeneste.get(position).getMængde());
        registrering.setDate(calendar.getTime());

        Daily_view_frag.væskelistProeve.add(0,registrering) ;


        ml=ml+knapperSeneste.get(position).getMængde();

        overall.setText(ml+" ml"+"/"+overallml+" ml");
    }

    private void createData(){
        Calendar calendar = Calendar.getInstance();
        Date d1 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d2 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d3 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d4 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d5 = calendar.getTime();

        VæskeRegistrering et =new VæskeRegistrering();
        et.setMængde(250);
        et.setDate(d1);
        et.setType("Vand");

        VæskeRegistrering to =new VæskeRegistrering();
        to.setMængde(250);
        to.setDate(d1);
        to.setType("Saft");

        VæskeRegistrering tre =new VæskeRegistrering();
        tre.setMængde(250);
        tre.setDate(d1);
        tre.setType("Vand");

        VæskeRegistrering fire =new VæskeRegistrering();
        fire.setMængde(250);
        fire.setDate(d1);
        fire.setType("Vand");


        VæskeRegistrering fem =new VæskeRegistrering();
        fem.setMængde(250);
        fem.setDate(d2);
        fem.setType("Vand");

        VæskeRegistrering seks =new VæskeRegistrering();
        seks.setMængde(250);
        seks.setDate(d2);
        seks.setType("Vand");

        VæskeRegistrering syv =new VæskeRegistrering();
        syv.setMængde(250);
        syv.setDate(d2);
        syv.setType("Vand");

        VæskeRegistrering syv2 =new VæskeRegistrering();
        syv2.setMængde(250);
        syv2.setDate(d2);
        syv2.setType("Vand");

        VæskeRegistrering otte =new VæskeRegistrering();
        otte.setMængde(250);
        otte.setDate(d3);
        otte.setType("Vand");


        VæskeRegistrering ni =new VæskeRegistrering();
        ni.setMængde(250);
        ni.setDate(d3);
        ni.setType("Vand");

        VæskeRegistrering ti =new VæskeRegistrering();
        ti.setMængde(250);
        ti.setDate(d3);
        ti.setType("Vand");

        VæskeRegistrering elleve =new VæskeRegistrering();
        elleve.setMængde(250);
        elleve.setDate(d3);
        elleve.setType("Vand");


        VæskeRegistrering tolv =new VæskeRegistrering();
        tolv.setMængde(250);
        tolv.setDate(d4);
        tolv.setType("Vand");


        VæskeRegistrering tretten =new VæskeRegistrering();
        tretten.setMængde(250);
        tretten.setDate(d4);
        tretten.setType("Vand");

        VæskeRegistrering fjorden =new VæskeRegistrering();
        fjorden.setMængde(250);
        fjorden.setDate(d4);
        fjorden.setType("Vand");

        VæskeRegistrering femten =new VæskeRegistrering();
        femten.setMængde(250);
        femten.setDate(d4);
        femten.setType("Vand");


        Daily_view_frag.væskelistProeve.add(et);
        Daily_view_frag.væskelistProeve.add(to);
        Daily_view_frag.væskelistProeve.add(tre);
        Daily_view_frag.væskelistProeve.add(fire);
        Daily_view_frag.væskelistProeve.add(fem);
        Daily_view_frag.væskelistProeve.add(seks);
        Daily_view_frag.væskelistProeve.add(syv);
        Daily_view_frag.væskelistProeve.add(syv2);
        Daily_view_frag.væskelistProeve.add(otte);
        Daily_view_frag.væskelistProeve.add(ni);
        Daily_view_frag.væskelistProeve.add(ti);
        Daily_view_frag.væskelistProeve.add(elleve);
        Daily_view_frag.væskelistProeve.add(tolv);
        Daily_view_frag.væskelistProeve.add(tretten);
        Daily_view_frag.væskelistProeve.add(fjorden);
        Daily_view_frag.væskelistProeve.add(femten);

    }
}
