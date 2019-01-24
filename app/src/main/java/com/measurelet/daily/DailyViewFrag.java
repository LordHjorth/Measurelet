package com.measurelet.daily;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

public class DailyViewFrag extends BaseFragment implements View.OnClickListener {


    private ListView list;
    private BarChart barGraph;
    private ArrayList<BarEntry> datapoints = new ArrayList<>();
    private ArrayList<String> dates = new ArrayList<>();
    private LinearLayout next, previous;
    private LocalDate next_date = null;
    private LocalDate date;

    private final DateTimeFormatter formatHour = DateTimeFormatter.ofPattern("HH");


    private SortedMap<String, Integer> hourMap = new TreeMap<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View fragment = inflater.inflate(R.layout.daily_view_frag, container, false);

        list = fragment.findViewById(R.id.listDaily);
        barGraph = fragment.findViewById(R.id.graph);

        next = fragment.findViewById(R.id.next_date);
        next.setOnClickListener(this);
        previous = fragment.findViewById(R.id.previous_date);
        previous.setOnClickListener(this);

        Bundle b = getArguments();

        date = null;

        if (b != null) {
            String temp = b.getString("date");
            if (temp != null) {
                date = LocalDate.parse(temp);
            }
        }
        if (date == null) {
            date = LocalDate.now();

        }
        if (date.equals(LocalDate.now())) {
            next.setVisibility(View.INVISIBLE);
        }

        TextView shownDate = fragment.findViewById(R.id.dato_daglig);
        shownDate.setText(LocalDate.parse(date.toString()).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));


        addListener(App.patientRef, new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Patient p = dataSnapshot.getValue(Patient.class);

                // No patient exists. It might have been deleted
                if (p == null || getActivity() == null) {
                    return;
                }

                ArrayList<Intake> intakes = p.getIntakesForDate(date);

                createGraph(intakes);
                MyAdapter adapter = new MyAdapter(getActivity(), intakes);
                list.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return fragment;
    }

    private IAxisValueFormatter getFormatter() {
        return (value, axis) -> dates.toArray(new String[dates.size()])[(int) value];
    }

    private void createGraph(ArrayList<Intake> dailyIntake) {

        datapoints.clear();
        dates.clear();
        hourMap.clear();

        if (!dailyIntake.isEmpty()) {
            //samler mængder efter time
            int size = 0;
            for (Intake intake : dailyIntake) {
                String hour = intake.getDateTime().format(formatHour);
                if (hourMap.containsKey(hour)) {
                    size = size + intake.getSize();
                } else {
                    size = intake.getSize();
                }
                hourMap.put(hour, size);
            }

            int hours = 24;
            if (date.isEqual(LocalDate.now())) {
                hours = LocalDateTime.now().getHour();
            }

            for (int i = 0; i < hours; i++) {
                if (!hourMap.containsKey(String.format(Locale.getDefault(), "%02d", i))) {
                    hourMap.put(String.format(Locale.getDefault(), "%02d", i), 0);
                }
            }

            int i = 0;
            for (Map.Entry<String, Integer> entry : hourMap.entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue();
                datapoints.add(new BarEntry(i, value));
                dates.add(key);
                i++;
            }

            BarDataSet data = new BarDataSet(datapoints, "Væskeindtag ml");

            BarData barData = new BarData(data);
            data.setColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));

            barData.setBarWidth(0.7f);
            barData.setValueFormatter((value, entry, dataSetIndex, viewPortHandler) -> {

                if (value > 0) {
                    return new DecimalFormat().format(value);
                }

                return "";
            });
            barGraph.setData(barData);

            XAxis xAxisDate = barGraph.getXAxis();
            xAxisDate.setValueFormatter(getFormatter());
            xAxisDate.setGranularity(1f);
            barGraph.setVisibleXRangeMaximum(7);
            barGraph.setVisibleXRangeMinimum(7);
            barGraph.centerViewTo(30.5f, 1f, YAxis.AxisDependency.RIGHT);
            barGraph.getAxisRight().setDrawGridLines(false);
            barGraph.getDescription().setEnabled(false);
            barGraph.getAxisLeft().setDrawGridLines(false);
            data.setValueTextSize(10);
            barGraph.getAxisLeft().setAxisMinimum(0);
            barGraph.getAxisLeft().setAxisMaximum(data.getYMax() + 200);

            barGraph.getAxisRight().setEnabled(false);
            xAxisDate.setPosition(XAxis.XAxisPosition.BOTTOM);
            Legend l = barGraph.getLegend();
            l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);

            xAxisDate.setDrawGridLines(false);

            barGraph.invalidate();
        } else {
            barGraph.clear();
        }

    }

    @Override
    public void onClick(View v) {
        if (v == previous || v == next) {

            if (v == previous) {
                next_date = date.minusDays(1);

            }
            if (v == next) {
                next_date = date.plusDays(1);
            }

            String next_date_string = next_date.toString();

            Bundle b = new Bundle();
            b.putString("date", next_date_string);

            ((MainActivity) getActivity()).getNavC().popBackStack();
            ((MainActivity) getActivity()).getNavC().navigate(R.id.action_daily_navigation, b);

        }
    }

    private class MyAdapter extends ArrayAdapter<Intake> {
        private ArrayList<Intake> dataSet;
        Context mContext;

        MyAdapter(@NonNull Context context, ArrayList<Intake> data) {
            super(context, R.layout.list_daily, data);
            this.dataSet = data;
            this.mContext = context;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.list_daily, parent, false);

            TextView tid = rowView.findViewById(R.id.tid_daily);
            TextView size = rowView.findViewById(R.id.ml_daily);
            TextView type = rowView.findViewById(R.id.type_daily);

            ImageButton edit_button = rowView.findViewById(R.id.edit_daily);

            edit_button.setOnClickListener(v -> {

                Bundle b = new Bundle();
                b.putString("uuid", dataSet.get(position).getUuid());

                DialogFragment dialog = new EditLiquidDialog();
                dialog.setArguments(b);
                dialog.show(getChildFragmentManager(), "dialog");

            });
            DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");

            tid.setText(dataSet.get(position).getDateTime().format(format));
            type.setText(dataSet.get(position).getType());
            size.setText(String.valueOf(dataSet.get(position).getSize()) + " ml");

            return rowView;
        }
    }
}
