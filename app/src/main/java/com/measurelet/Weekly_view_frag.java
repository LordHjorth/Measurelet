package com.measurelet;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hjorth.measurelet.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class Weekly_view_frag extends Fragment implements AdapterView.OnItemClickListener {


    ListView listView;
    private View view;
    private ArrayList<VæskeRegistrering> væskeList ;
    private BarChart barGraph;
    private BarData barData;
    private ArrayList<BarEntry> datapoints = new ArrayList<>();
    private XAxis xAxisDato;
    private ArrayList<String> dates = new ArrayList<>();

    final SimpleDateFormat format = new SimpleDateFormat("dd/MM");



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.weekly_view_frag, container, false);

        listView = view.findViewById(R.id.listWeek);
        listView.setOnItemClickListener(this);
        barGraph = view.findViewById(R.id.graph);

        createData();
        createGraph();


        MyAdapter adapter = new MyAdapter(getActivity(), væskeList);
        listView.setAdapter(adapter);

        return view;
    }


    private void createGraph() {

        for (int i = 0; i < væskeList.size(); i++) {
            datapoints.add(new BarEntry(i, væskeList.get(i).getMængde()));
            dates.add(format.format(væskeList.get(i).getDate()));
        }

        BarDataSet data = new BarDataSet(datapoints,"Væskeindtag ml");

        barData = new BarData(data);
        barData.setBarWidth(0.7f);

        barGraph.setData(barData);

        xAxisDato = barGraph.getXAxis();
        xAxisDato.setValueFormatter(getformatter());
        barGraph.setVisibleXRangeMaximum(7);
        barGraph.setVisibleXRangeMinimum(7);
        xAxisDato.setGranularity(1f);
        barGraph.centerViewTo(30.5f, 1f, YAxis.AxisDependency.RIGHT);
        barGraph.getAxisRight().setDrawGridLines(false);
        barGraph.getAxisLeft().setDrawGridLines(false);
        barGraph.getDescription().setEnabled(false);
        xAxisDato.setDrawGridLines(false);

        barGraph.invalidate();

    }

    private void createData(){
        væskeList = new ArrayList<VæskeRegistrering>();
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
        calendar.add(Calendar.DATE, 1);

        Date d6 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);

        Date d7 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);

        Date d8 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);

        Date d9 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);

        VæskeRegistrering væske = new VæskeRegistrering();
        væske.setDate(d1);
        væske.setMængde(2000);

        VæskeRegistrering væske2 = new VæskeRegistrering();
        væske2.setDate(d2);
        væske2.setMængde(1000);

        VæskeRegistrering væske3 = new VæskeRegistrering();
        væske3.setDate(d3);
        væske3.setMængde(1500);


        VæskeRegistrering væske4 = new VæskeRegistrering();
        væske4.setDate(d4);
        væske4.setMængde(2800);

        VæskeRegistrering væske5 = new VæskeRegistrering();
        væske5.setDate(d5);
        væske5.setMængde(3000);

        VæskeRegistrering væske6 = new VæskeRegistrering();
        væske6.setDate(d6);
        væske6.setMængde(2000);

        VæskeRegistrering væske7 = new VæskeRegistrering();
        væske7.setDate(d7);
        væske7.setMængde(5000);

        VæskeRegistrering væske8 = new VæskeRegistrering();
        væske8.setDate(d8);
        væske8.setMængde(2000);

        VæskeRegistrering væske9 = new VæskeRegistrering();
        væske9.setDate(d9);
        væske9.setMængde(3500);

        væskeList.add(0,væske);
        væskeList.add(1,væske2);
        væskeList.add(2,væske3);
        væskeList.add(3,væske4);
        væskeList.add(4,væske5);
        væskeList.add(5,væske6);
        væskeList.add(6,væske7);
        væskeList.add(7,væske8);
        væskeList.add(8,væske9);

    }

    private IAxisValueFormatter getformatter() {

        IAxisValueFormatter formatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {

                return dates.toArray(new String[dates.size()])[(int) value];
            }

        };
        return formatter;
    }




    public  ArrayList<VæskeRegistrering> getVæske() {
        return væskeList;
    }

    public ArrayList<VæskeRegistrering> getDatoVæskeliste(Date date) {
        ArrayList<VæskeRegistrering> daglig = new ArrayList<>();
        for (VæskeRegistrering hej : væskeList) {
            if (hej.getDate().getDate() == date.getDate()) {
                daglig.add(hej);
            }
        }
        return daglig;
    }

  /*  private ArrayList<VæskeRegistrering> getUgentligVæskesorteret() {
        ArrayList<VæskeRegistrering> ugentlig = new ArrayList<>();
        for (VæskeRegistrering hej : væskeList) {
        }
        return ugentlig;
    }
    */

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        ((MainActivity) getActivity()).getNavC().navigate(R.id.daily_view_frag);


    }

    private class MyAdapter extends ArrayAdapter<VæskeRegistrering> {
        private ArrayList<VæskeRegistrering> dataSet;
        Context mContext;

        public MyAdapter(@NonNull Context context, ArrayList<VæskeRegistrering> data) {
            super(context, R.layout.list_weeklyview, data);
            this.dataSet = data;
            this.mContext = context;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.list_weeklyview, parent, false);

            TextView dato = rowView.findViewById(R.id.dato);
            TextView mængde = rowView.findViewById(R.id.mængde);

            SimpleDateFormat format = new SimpleDateFormat("dd. MMM");


            dato.setText(format.format(dataSet.get(position).getDate()));

            mængde.setText(String.valueOf(dataSet.get(position).getMængde()));

            return rowView;
        }

    }
}






