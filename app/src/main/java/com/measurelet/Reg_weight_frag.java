package com.measurelet;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hjorth.measurelet.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.measurelet.Model.Weight;
import com.measurelet.registration.edit_weight;

import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


public class Reg_weight_frag extends Fragment implements DialogInterface.OnDismissListener{

    private ListView lsView;
    private MyAdapter arrayAdapter;
    private LineChart lineChart;
    private LineData lineData;
    private XAxis xAxisDato;
    private ArrayList<String> dates = new ArrayList<>();
    private ArrayList<Entry> datapoints = new ArrayList<>();
    final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM");


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View regweight = inflater.inflate(R.layout.reg_weight_frag, container, false);

        lineChart = regweight.findViewById(R.id.graphvaegt);
        lsView = regweight.findViewById(R.id.listviewVaegt);

        render();

        return regweight;
    }


    private IAxisValueFormatter getformatter() {
        IAxisValueFormatter formatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {

                if ((int) value < 0 || (int) value > dates.size() - 1) {
                    return "";
                }

                return dates.get((int) value);
                // return null;
            }
        };
        return formatter;
    }

    private void createGraph() {

        datapoints.clear();
        dates.clear();

        if (!App.currentUser.getWeights().isEmpty()) {

            for (int i = 0; i < App.currentUser.getSortedWeights().size(); i++) {
                datapoints.add(new Entry(i, (float) App.currentUser.getSortedWeights().get(i).getWeightKG()));
                dates.add(App.currentUser.getSortedWeights().get(i).getDatetime().format(format));
            }

            LineDataSet data = new LineDataSet(datapoints, "Vægt kg");
            lineData = new LineData(data);

            lineChart.setData(lineData);

            xAxisDato = lineChart.getXAxis();
            xAxisDato.setValueFormatter(getformatter());
            data.setCircleRadius(5f);
            lineChart.setVisibleXRangeMaximum(7);
            lineChart.setVisibleXRangeMinimum(7);
            xAxisDato.setGranularity(1f);
            lineChart.centerViewTo(30.5f, 1f, YAxis.AxisDependency.LEFT);
            lineChart.getAxisRight().setDrawLabels(false);
            lineChart.getAxisRight().setDrawGridLines(false);
            lineChart.getAxisLeft().setDrawGridLines(false);
            lineChart.getDescription().setEnabled(false);
            lineChart.getAxisLeft().setAxisMinimum(data.getYMin() - 30);
            lineChart.getAxisLeft().setAxisMaximum(data.getYMax() + 30);
            xAxisDato.setSpaceMax(0.4f);
            xAxisDato.setSpaceMin(0.4f);
            xAxisDato.setDrawGridLines(false);
            data.setValueTextSize(10);

            data.setCircleColor(ContextCompat.getColor(this.getActivity(), R.color.colorPrimaryDark));
            data.setCircleHoleColor(ContextCompat.getColor(this.getActivity(), R.color.colorPrimaryDark));
            data.setColor(ContextCompat.getColor(this.getActivity(), R.color.colorPrimaryDark));


            lineChart.invalidate();
        }
        else {
            lineChart.clear();
        }


    }
    public void render(){
        createGraph();
        arrayAdapter = new MyAdapter(getActivity(), App.currentUser.getWeights());
        lsView.setAdapter(arrayAdapter);

    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        render();
    }

    private class MyAdapter extends ArrayAdapter<Weight> {
        private ArrayList<Weight> dataSet;
        Context mContext;

        public MyAdapter(@NonNull Context context, ArrayList<Weight> data) {
            super(context, R.layout.list_weight, data);
            this.dataSet = data;
            this.mContext = context;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.list_weight, parent, false);

            TextView date = rowView.findViewById(R.id.date_weight);
            TextView weight = rowView.findViewById(R.id.amount);
            ImageButton edit = rowView.findViewById(R.id.edit_weight);
            edit.setOnClickListener(v -> {
                Bundle b = new Bundle();
                b.putString("date",dataSet.get(dataSet.size() - position - 1).getDatetime().format(format));
                b.putDouble("value",dataSet.get(dataSet.size() - position - 1).getWeightKG());
                b.putString("uuid",dataSet.get(position).getUuid());

                DialogFragment dialog = new edit_weight();
                dialog.setArguments(b);
                dialog.show(getChildFragmentManager(),"dialog");

            });

            date.setText(dataSet.get(dataSet.size() - position - 1).getDatetime().format(format));

            weight.setText(String.valueOf(dataSet.get(dataSet.size() - position - 1).getWeightKG()) + " kg");

            return rowView;
        }
    }

}