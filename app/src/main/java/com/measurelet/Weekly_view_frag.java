package com.measurelet;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hjorth.measurelet.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.measurelet.Model.Intake;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Weekly_view_frag extends Fragment implements AdapterView.OnItemClickListener {


    ListView listView;
    private View view;
   private ArrayList<DagVaeske> væskeList = new ArrayList<>();
    private BarChart barGraph;
    private BarData barData;
    private ArrayList<BarEntry> datapoints = new ArrayList<>();
    private XAxis xAxisDato;
    private ArrayList<String> dates = new ArrayList<>();

    final SimpleDateFormat format = new SimpleDateFormat("dd/MM");

    HashMap<String, ArrayList<Intake>> ko = new HashMap<>();


    int mængde = 0;
    Date d1;

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


    private void createData(){

        for (Intake registrering: App.currentUser.getRegistrations()) {
            ArrayList<Intake> list=ko.get(format.format(registrering.getTimestamp()));

            if (list == null) {
                list = new ArrayList<>();
                ko.put(format.format(registrering.getTimestamp()), list);
            }
            list.add(registrering);
        }

    }


    private void createGraph() {

        int i=0;
        for (Map.Entry<String, ArrayList<Intake>> entry : ko.entrySet()) {
            int mængde=0;
            for (Intake intake : entry.getValue()){
                mængde=intake.getSize()+mængde;

            }
            System.out.println(mængde);
            datapoints.add(new BarEntry(i,mængde));
            dates.add(entry.getKey());

            væskeList.add(new DagVaeske(entry.getKey(),mængde));
            System.out.println(dates.toString());
            i++;
        }

        BarDataSet data = new BarDataSet(datapoints, "Væskeindtag ml");

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

        data.setColor(ColorTemplate.rgb("7cb5e4"));
        data.setValueTextSize(10);
        barGraph.invalidate();
    }

    private IAxisValueFormatter getformatter() {

        IAxisValueFormatter formatter = (value, axis) -> dates.toArray(new String[dates.size()])[(int) value];
        return formatter;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    private class MyAdapter extends ArrayAdapter<DagVaeske> {
        private ArrayList<DagVaeske> dataSet;
        Context mContext;

        public MyAdapter(@NonNull Context context, ArrayList<DagVaeske> data) {
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

            dato.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle b = new Bundle();
                    b.putString("date",dataSet.get(position).getDate());

                    ((MainActivity) getActivity()).getNavC().navigate(R.id.daily_view_frag,b);
                }
            });

            dato.setText(dataSet.get(position).getDate());

            mængde.setText(String.valueOf(dataSet.get(position).getMængde())+" ml");

            return rowView;
        }
    }
}






