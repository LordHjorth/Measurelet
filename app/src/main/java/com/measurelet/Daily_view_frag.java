package com.measurelet;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
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
import com.measurelet.Model.Weight;

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
    private ArrayList<String> dates = new ArrayList<>();



    private final SimpleDateFormat format = new SimpleDateFormat("HH:mm");

    private final SimpleDateFormat format2 = new SimpleDateFormat("dd/MM");

    private Calendar calendar = Calendar.getInstance();

    private Date date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View dailyView = inflater.inflate(R.layout.daily_view_frag, container, false);

        list = dailyView.findViewById(R.id.listDaily);
        barGraph = dailyView.findViewById(R.id.graph);

        createGraph();
        MyAdapter adapter = new MyAdapter(getActivity(), App.currentUser.getRegistrations());
        list.setAdapter(adapter);

        return dailyView;
    }


    private IAxisValueFormatter getformatter() {


        IAxisValueFormatter formatter = (value, axis) -> dates.toArray(new String[dates.size()])[(int) value];
        return formatter;
    }

    private void createGraph() {
        int i=0;
        for (Intake intake :App.currentUser.getRegistrations() ){
            System.out.println(format2.format(calendar.getTime()) + "  "+format2.format(intake.getTimestamp()) );

            if (format2.format(calendar.getTime()).equals(format2.format(intake.getTimestamp()))){
                datapoints.add(new BarEntry(i, intake.getSize()));
                dates.add(format.format(intake.getTimestamp()));
                i++;
                System.out.println(i +" ");

            }
        }

        BarDataSet data = new BarDataSet(datapoints, "Væskeindtag ml");


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
        data.setValueTextSize(10);
        data.setColor(ColorTemplate.rgb("7cb5e4"));

        xAxisDato.setDrawGridLines(false);

        barGraph.invalidate();
    }

    @Override
    public void onClick(View v) {

    }

    private class MyAdapter extends ArrayAdapter<Intake> {
        private ArrayList<Intake> dataSet;
        Context mContext;

        public MyAdapter(@NonNull Context context, ArrayList<Intake> data) {
            super(context, R.layout.list_daily, data);
            this.dataSet = data;
            this.mContext = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.list_daily, parent, false);

            TextView tid = rowView.findViewById(R.id.tid_daily);
            TextView mængde = rowView.findViewById(R.id.ml_daily);
            TextView type = rowView.findViewById(R.id.type_daily);

            ImageButton edit_button = rowView.findViewById(R.id.edit_daily);

            edit_button.setOnClickListener(v -> {

                Bundle b = new Bundle();
                b.putInt("position", position);

                ((MainActivity) getActivity()).getNavC().navigate(R.id.action_global_edit_liquid, b);

            });

            SimpleDateFormat format = new SimpleDateFormat("HH:mm");

            tid.setText(format.format(dataSet.get(position).getTimestamp()));
            type.setText(dataSet.get(position).getType());
            mængde.setText(String.valueOf(dataSet.get(position).getSize())+" ml");


            return rowView;
        }
    }
}
