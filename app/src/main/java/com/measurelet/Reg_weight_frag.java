package com.measurelet;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;


public class Reg_weight_frag extends Fragment implements View.OnClickListener {

    private EditText indtastningAfVaegt;
    private Button registrerVaegt;
    private ListView lsView;
    private String vægt;
    private ArrayList<String> vaegtListe = new ArrayList<>();
    private SimpleDateFormat fm;
    private Calendar cal;
    private ArrayAdapter<String> arrayAdapter;
    private Date today;

    private LineChart lineChart;
    private LineData lineData;
    private XAxis xAxisDato;

    private ArrayList<String> dates = new ArrayList<>();
    private ArrayList<Entry> datapoints = new ArrayList<>();
    final SimpleDateFormat format = new SimpleDateFormat("dd/MM");


    private ArrayList<VaegtRegistrering> vaegtListe2 = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View regweight = inflater.inflate(R.layout.reg_weight_frag, container, false);
        lineChart =regweight.findViewById(R.id.graphvaegt);


        fm = new SimpleDateFormat("dd-MM-yyyy");
        cal = Calendar.getInstance();
        lsView = regweight.findViewById(R.id.listviewVaegt);
        //indtastningAfVaegt = regweight.findViewById(R.id.indtastVaegt);

        today = cal.getTime();
        //registrerVaegt = regweight.findViewById(R.id.registrer);
//        registrerVaegt.setOnClickListener(this);
        for (int i = 0; i < 10; i++) {

            vaegtListe2.add(new VaegtRegistrering(cal.getTime(),60+i));

            vaegtListe.add(fm.format(cal.getTime()) + ":                      " + (60+ i) + "kg");

            cal.add(Calendar.DATE, -1);


            arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, vaegtListe);
            lsView.setAdapter(arrayAdapter);

        }
        createGraph();



        return regweight;
    }


    public void visVaegt() {
        vægt = indtastningAfVaegt.getText().toString();
        if (vægt.equals("")) {
            vægt = "66";
        }

        vaegtListe.add(0, fm.format(today) + ":                      " + vægt + " kg");

        arrayAdapter.notifyDataSetChanged();


    }

    @Override
    public void onClick(View view) {
        ((MainActivity) getActivity()).getAddAnimation();
        visVaegt();
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

        for (int i = 0; i < vaegtListe2.size(); i++) {
            datapoints.add(new Entry(i,vaegtListe2.get(i).getVaegt()));
            dates.add(format.format(vaegtListe2.get(i).getDate()));
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

    private class VaegtRegistrering {


        Date date;
        int vaegt;

        public VaegtRegistrering(Date date, int vaegt){
            this.date=date;
            this.vaegt=vaegt;

        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public int getVaegt() {
            return vaegt;
        }

        public void setVaegt(int vaegt) {
            this.vaegt = vaegt;
        }



    }
}


