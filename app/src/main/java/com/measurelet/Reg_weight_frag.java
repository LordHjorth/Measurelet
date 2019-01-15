package com.measurelet;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hjorth.measurelet.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.measurelet.Model.Intake;
import com.measurelet.Model.Weight;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;


public class Reg_weight_frag extends Fragment {

    private ListView lsView;
    private MyAdapter arrayAdapter;

    private LineChart lineChart;
    private LineData lineData;
    private XAxis xAxisDato;

    private ArrayList<String> dates = new ArrayList<>();
    private ArrayList<Entry> datapoints = new ArrayList<>();
    final SimpleDateFormat format = new SimpleDateFormat("dd/MM");



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View regweight = inflater.inflate(R.layout.reg_weight_frag, container, false);

        lineChart =regweight.findViewById(R.id.graphvaegt);
        lsView = regweight.findViewById(R.id.listviewVaegt);
        arrayAdapter = new MyAdapter(getActivity(), App.currentUser.getWeights());
        lsView.setAdapter(arrayAdapter);

        createGraph();

        return regweight;
    }





    private IAxisValueFormatter getformatter() {
        IAxisValueFormatter formatter = (value, axis) -> dates.toArray(new String[dates.size()])[(int) value];
        return formatter;
    }


    private void createGraph() {

        for (int i = 0; i < App.currentUser.getWeights().size(); i++) {
            datapoints.add(new Entry(i, (float) App.currentUser.getWeights().get(i).getWeightKG()));
            dates.add(format.format(App.currentUser.getWeights().get(i).getTimestamp()));
        }

        LineDataSet data = new LineDataSet(datapoints, "Væskeindtag ml");

        lineData = new LineData(data);


        lineChart.setData(lineData);

        xAxisDato = lineChart.getXAxis();
        xAxisDato.setValueFormatter(getformatter());
        data.setCircleRadius(5f);
        data.setCircleColor(ColorTemplate.rgb("7cb5e4"));
        lineChart.setVisibleXRangeMaximum(7);
        lineChart.setVisibleXRangeMinimum(7);
        xAxisDato.setGranularity(1f);
        lineChart.centerViewTo(30.5f, 1f, YAxis.AxisDependency.RIGHT);
        lineChart.getAxisRight().setDrawGridLines(false);
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getDescription().setEnabled(false);
        xAxisDato.setDrawGridLines(false);
        data.setValueTextSize(10);
        data.setColor(ColorTemplate.rgb("7cb5e4"));
        lineChart.invalidate();

    }

    private class MyAdapter extends ArrayAdapter<Weight> {
        private ArrayList<Weight> dataSet;
        Context mContext;

        public MyAdapter(@NonNull Context context, ArrayList<Weight> data) {
            super(context, R.layout.list_weeklyview, data);
            this.dataSet = data;
            this.mContext = context;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.list_weeklyview, parent, false);

            TextView date = rowView.findViewById(R.id.dato);
            TextView weight = rowView.findViewById(R.id.mængde);

            date.setText(format.format(dataSet.get(position).getTimestamp()));

            weight.setText(String.valueOf(dataSet.get(position).getWeightKG())+" kg");

            return rowView;
        }
    }

}


