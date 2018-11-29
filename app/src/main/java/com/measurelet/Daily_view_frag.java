package com.measurelet;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class Daily_view_frag extends Fragment implements View.OnClickListener {


    private ListView list;
    private BarChart barGraph;
    private BarData barData;
    private ArrayList<BarEntry> datapoints = new ArrayList<>();
    private XAxis xAxisDato;
    private ArrayList<VæskeRegistrering> væskeList ;
    private ArrayList<String> dates = new ArrayList<>();

    final SimpleDateFormat format = new SimpleDateFormat("HH:mm");





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View dailyView = inflater.inflate(R.layout.daily_view_frag, container, false);

        list=dailyView.findViewById(R.id.listDaily);
        barGraph = dailyView.findViewById(R.id.graph);

        createData();
        createGraph();

        MyAdapter adapter = new MyAdapter(getActivity(),væskeList);
        list.setAdapter(adapter);


        return dailyView;
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
        xAxisDato.setGranularity(1f);
        barGraph.setVisibleXRangeMaximum(7);
        barGraph.setVisibleXRangeMinimum(7);
        barGraph.centerViewTo(30.5f, 1f, YAxis.AxisDependency.RIGHT);
        barGraph.getAxisRight().setDrawGridLines(false);
        barGraph.getDescription().setEnabled(false);
        barGraph.getAxisLeft().setDrawGridLines(false);
        xAxisDato.setDrawGridLines(false);


        barGraph.invalidate();

    }

    @Override
    public void onClick(View v) {

    }

    private class MyAdapter extends ArrayAdapter<VæskeRegistrering> {
        private ArrayList<VæskeRegistrering> dataSet;
        Context mContext;

        public MyAdapter(@NonNull Context context, ArrayList<VæskeRegistrering> data) {
            super(context, R.layout.list_daily, data);
            this.dataSet=data;
            this.mContext=context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.list_daily, parent, false);

           TextView tid = rowView.findViewById(R.id.tid_daily);
           TextView mængde=rowView.findViewById(R.id.ml_daily);
           TextView type = rowView.findViewById(R.id.type_daily);


            SimpleDateFormat format = new SimpleDateFormat("HH:mm");

            tid.setText(format.format(dataSet.get(position).getDate()));
            type.setText(dataSet.get(position).getType());
            mængde.setText(String.valueOf(dataSet.get(position).getMængde()));


            return rowView;
        }
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
        væske.setMængde(300);
        væske.setType("Kaffe");

        VæskeRegistrering væske2 = new VæskeRegistrering();
        væske2.setDate(d2);
        væske2.setMængde(200);
        væske2.setType("Vand");

        VæskeRegistrering væske3 = new VæskeRegistrering();
        væske3.setDate(d3);
        væske3.setMængde(500);
        væske3.setType("Cola");


        VæskeRegistrering væske4 = new VæskeRegistrering();
        væske4.setDate(d4);
        væske4.setMængde(200);
        væske4.setType("Vand");

        VæskeRegistrering væske5 = new VæskeRegistrering();
        væske5.setDate(d5);
        væske5.setMængde(300);
        væske5.setType("kaffe");


        væskeList.add(0,væske);
        væskeList.add(1,væske2);
        væskeList.add(2,væske3);
        væskeList.add(3,væske4);
        væskeList.add(4,væske5);

    }
}
