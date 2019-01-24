package com.measurelet.weight;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.measurelet.App;
import com.measurelet.BaseFragment;
import com.measurelet.factories.WeightFactory;
import com.measurelet.model.Intake;
import com.measurelet.model.Weight;

import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;


public class WeightFrag extends BaseFragment{

    private ListView lsView;
    private LineChart lineChart;
    private ArrayList<String> dates = new ArrayList<>();
    private ArrayList<Entry> datapoints = new ArrayList<>();

    public static final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.reg_weight_frag, container, false);

        lineChart = fragment.findViewById(R.id.graphvaegt);
        lsView = fragment.findViewById(R.id.listviewVaegt);


        addListener(App.weightRef, new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                datapoints.clear();
                dates.clear();

                ArrayList<Weight> weights =  new ArrayList();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    weights.add(snapshot.getValue(Weight.class));
                }

                if(!weights.isEmpty()){

                    weights = WeightFactory.getSortedWeights(weights);

                    for (int i = 0; i < weights.size(); i++) {

                        datapoints.add(new Entry(i, (float) weights.get(i).getWeightKG()));
                        dates.add(weights.get(i).getDatetime().format(format));

                    }

                    LineDataSet data = new LineDataSet(datapoints, "Vægt kg");
                    LineData lineData = new LineData(data);

                    lineChart.setData(lineData);

                    XAxis xAxisDato = lineChart.getXAxis();
                    xAxisDato.setValueFormatter(getFormatter());
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

                    data.setCircleColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
                    data.setCircleHoleColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
                    data.setColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));

                    lineChart.invalidate();



                } else {
                    lineChart.clear();
                }

                MyAdapter arrayAdapter = new MyAdapter(getActivity(), weights);
                lsView.setAdapter(arrayAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        return fragment;
    }

    private IAxisValueFormatter getFormatter() {
        return (value, axis) -> {

            if ((int) value < 0 || (int) value > dates.size() - 1) {
                return "";
            }

            return dates.get((int) value);
            // return null;
        };
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
                b.putString("uuid",dataSet.get(dataSet.size() - position - 1).getUuid());

                DialogFragment dialog = new EditWeightDialog();
                dialog.setArguments(b);
                dialog.show(getChildFragmentManager(),"dialog");

            });

            date.setText(dataSet.get(dataSet.size() - position - 1).getDatetime().format(format));

            weight.setText(String.valueOf(dataSet.get(dataSet.size() - position - 1).getWeightKG()) + " kg");

            return rowView;
        }
    }

}