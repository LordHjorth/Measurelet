package com.measurelet;

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
import com.measurelet.model.Intake;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Map;
import java.util.SortedMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


public class Weekly_view_frag extends Fragment implements AdapterView.OnItemClickListener {


    ListView listView;
    private View view;
    private BarChart barGraph;
    private BarData barData;
    private ArrayList<BarEntry> datapoints = new ArrayList<>();
    private XAxis xAxisDato;
    private ArrayList<String> dates = new ArrayList<>();
    private String d;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.weekly_view_frag, container, false);

        listView = view.findViewById(R.id.listWeek);
        listView.setOnItemClickListener(this);
        barGraph = view.findViewById(R.id.graph);


        createGraph();

        MyAdapter adapter = new MyAdapter(getActivity(), App.currentUser.getIntakesForWeeks());
        listView.setAdapter(adapter);

        return view;
    }


    private void createGraph() {
        if (!App.currentUser.getIntakesForWeeks().isEmpty()) {

            int i = 0;
            for (Map.Entry<String, Integer> entry : App.currentUser.getIntakesForWeeks().entrySet()) {
                datapoints.add(new BarEntry(i, entry.getValue()));
                dates.add(LocalDate.parse(entry.getKey()).format(DateTimeFormatter.ofPattern("dd/MM")));
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
            barGraph.getAxisLeft().setAxisMinimum(0);
            barGraph.getAxisLeft().setAxisMaximum(data.getYMax() + 500);
            data.setColor(ContextCompat.getColor(this.getActivity(), R.color.colorPrimaryDark));
            barGraph.getAxisRight().setEnabled(false);

            xAxisDato.setPosition(XAxis.XAxisPosition.BOTTOM);
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

    private IAxisValueFormatter getformatter() {

        IAxisValueFormatter formatter = (value, axis) -> dates.toArray(new String[dates.size()])[(int) value];
        return formatter;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Bundle b = new Bundle();
        b.putString("date", d);
        ((MainActivity) getActivity()).getNavC().navigate(R.id.action_weekly_to_daily, b);
    }

    private class MyAdapter extends ArrayAdapter<Intake> {
        Context mContext;
        SortedMap<String, Integer> registrations;

        @Override
        public int getCount() {
            return registrations.size();
        }

        public MyAdapter(@NonNull Context context, SortedMap<String, Integer> registrations) {
            super(context, R.layout.list_weeklyview);
            this.registrations = registrations;
            this.mContext = context;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.list_weeklyview, parent, false);

             d = registrations.keySet().toArray()[registrations.size() - position - 1].toString();
            int a = (int) registrations.values().toArray()[registrations.size() - position - 1];

            TextView dato = rowView.findViewById(R.id.dato);
            TextView mængde = rowView.findViewById(R.id.mængde);

            /*dato.setOnClickListener(view -> {
                Bundle b = new Bundle();
                b.putString("date", d);

                ((MainActivity) getActivity()).getNavC().navigate(R.id.daily_view_frag, b);
            });
            */

            dato.setText(LocalDate.parse(d).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

            mængde.setText(a + " ml");

            return rowView;
        }
    }
}






