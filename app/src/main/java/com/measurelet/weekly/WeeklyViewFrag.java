package com.measurelet.weekly;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hjorth.measurelet.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.measurelet.App;
import com.measurelet.BaseFragment;
import com.measurelet.MainActivity;
import com.measurelet.model.Intake;
import com.measurelet.model.Patient;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Map;
import java.util.SortedMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;


public class WeeklyViewFrag extends BaseFragment{

    private BarChart barGraph;
    private ArrayList<BarEntry> datapoints = new ArrayList<>();
    private ArrayList<String> dates = new ArrayList<>();
    private SortedMap<String, Integer> intakes;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.weekly_view_frag, container, false);

        ListView listView = fragment.findViewById(R.id.listWeek);


        barGraph = fragment.findViewById(R.id.graph);

        addListener(App.patientRef, new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Patient p = dataSnapshot.getValue(Patient.class);

                if(p == null){
                    return;
                }

                intakes = p.getIntakesForWeeks();

                createGraph(intakes);

                MyAdapter adapter = new MyAdapter(getActivity(), intakes);

                listView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return fragment;
    }


    private void createGraph(SortedMap<String, Integer> intakes) {

        if (!intakes.isEmpty()) {

            int i = 0;
            for (Map.Entry<String, Integer> entry : intakes.entrySet()) {
                datapoints.add(new BarEntry(i, entry.getValue()));
                dates.add(LocalDate.parse(entry.getKey()).format(DateTimeFormatter.ofPattern("dd/MM")));
                i++;
            }

            BarDataSet data = new BarDataSet(datapoints, "Væskeindtag ml");

            BarData barData = new BarData(data);
            barData.setBarWidth(0.7f);

            barGraph.setData(barData);

            XAxis xAxisDate = barGraph.getXAxis();
            xAxisDate.setValueFormatter(getFormatter());
            barGraph.setVisibleXRangeMaximum(7);
            barGraph.setVisibleXRangeMinimum(7);
            xAxisDate.setGranularity(1f);
            barGraph.centerViewTo(30.5f, 1f, YAxis.AxisDependency.RIGHT);
            barGraph.getAxisRight().setDrawGridLines(false);
            barGraph.getAxisLeft().setDrawGridLines(false);
            barGraph.getDescription().setEnabled(false);
            xAxisDate.setDrawGridLines(false);
            barGraph.getAxisLeft().setAxisMinimum(0);
            barGraph.getAxisLeft().setAxisMaximum(data.getYMax() + 500);
            data.setColor(ContextCompat.getColor(this.getActivity(), R.color.colorPrimaryDark));
            barGraph.getAxisRight().setEnabled(false);

            xAxisDate.setPosition(XAxis.XAxisPosition.BOTTOM);
            Legend l = barGraph.getLegend();
            l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
            data.setValueTextSize(10);
            barGraph.invalidate();
        }
        else{
            barGraph.clear();
        }
    }

    private IAxisValueFormatter getFormatter() {

        return (value, axis) -> dates.toArray(new String[dates.size()])[(int) value];
    }


    private class MyAdapter extends ArrayAdapter<Intake> {
        Context mContext;
        SortedMap<String, Integer> registrations;

        @Override
        public int getCount() {
            return registrations.size();
        }

        MyAdapter(@NonNull Context context, SortedMap<String, Integer> registrations) {
            super(context, R.layout.list_weeklyview);
            this.registrations = registrations;
            this.mContext = context;

        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.list_weeklyview, parent, false);


            String date = registrations.keySet().toArray()[registrations.size() - position - 1].toString();


            int a = (int) registrations.values().toArray()[registrations.size() - position - 1];

            TextView dato = rowView.findViewById(R.id.dato);
            TextView size = rowView.findViewById(R.id.mængde);

            rowView.setOnClickListener(view -> {
                Bundle b = new Bundle();
                b.putString("date", date);

                ((MainActivity) getActivity()).getNavC().navigate(R.id.action_weekly_to_daily, b);
            });


            dato.setText(LocalDate.parse(date).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

            size.setText(a + " ml");

            return rowView;
        }
    }
}






