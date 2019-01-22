package com.measurelet;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hjorth.measurelet.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.measurelet.Model.Intake;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class Daily_view_frag extends Fragment implements View.OnClickListener, DialogInterface.OnDismissListener {


    private ListView list;
    private BarChart barGraph;
    private BarData barData;
    private ArrayList<BarEntry> datapoints = new ArrayList<>();
    private XAxis xAxisDato;
    private ArrayList<String> dates = new ArrayList<>();
    private ImageButton back, forward;
    LinearLayout next;
    private CardView hej;

    private TextView shownDate;
    Bundle b;
    private LocalDate date ;

    private final DateTimeFormatter formatHour = DateTimeFormatter.ofPattern("HH");

    private SortedMap<String, Integer> hourMap = new TreeMap<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View dailyView = inflater.inflate(R.layout.daily_view_frag, container, false);
        list = dailyView.findViewById(R.id.listDaily);
        barGraph = dailyView.findViewById(R.id.graph);
        //list.setDivider(getResources().getDrawable(R.drawable.divider));

        Bundle b = getArguments();
         date = null;
        //hej = dailyView.findViewById(R.id.card_view_graph_table);

        shownDate = dailyView.findViewById(R.id.dato_daglig);
        back =dailyView.findViewById(R.id.back_button);
        forward =dailyView.findViewById(R.id.forward_button);
        back.setOnClickListener(this);
        forward.setOnClickListener(this);
        next = dailyView.findViewById(R.id.next_date);

        b = getArguments();
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
        if (date.equals(LocalDate.now())){
            next.setVisibility(View.INVISIBLE);
        }

        render();


        shownDate.setText(LocalDate.parse(date.toString()).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));


        if (!App.currentUser.getIntakesForDate(date).isEmpty()){
            createGraph(App.currentUser.getIntakesForDate(date));
//            hej.setVisibility(View.VISIBLE);

        }
        else{
            //hej.setVisibility(View.INVISIBLE);

        }

        MyAdapter adapter = new MyAdapter(getActivity(), App.currentUser.getIntakesForDate(date));
        list.setAdapter(adapter);


        return dailyView;
    }

    private IAxisValueFormatter getformatter() {
        IAxisValueFormatter formatter = (value, axis) -> dates.toArray(new String[dates.size()])[(int) value];
        return formatter;
    }

    private void createGraph(ArrayList<Intake> dailyIntake) {

        datapoints.clear();
        dates.clear();
        hourMap.clear();

        //samler mængder efter time
        int mængde = 0;
        for (Intake intake : dailyIntake) {
            String hour = intake.getDateTime().format(formatHour);
            if (hourMap.containsKey(hour)) {
                mængde = mængde + intake.getSize();
            } else {
                mængde = intake.getSize();
            }

            hourMap.put(hour, mængde);
        }


        int i = 0;
        for (Map.Entry<String, Integer> entry : hourMap.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            datapoints.add(new BarEntry(i, value));
            dates.add(key);
            System.out.println(dates.toString());
            i++;
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
        barGraph.getAxisLeft().setAxisMinimum(0);
        barGraph.getAxisLeft().setAxisMaximum(data.getYMax() + 200);

        data.setColor(ContextCompat.getColor(this.getActivity(), R.color.colorPrimaryDark));
        barGraph.getAxisRight().setEnabled(false);
        xAxisDato.setPosition(XAxis.XAxisPosition.BOTTOM);
        Legend l = barGraph.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);

        xAxisDato.setDrawGridLines(false);

        barGraph.invalidate();

    }

    public void render(){
        createGraph(App.currentUser.getIntakesForDate(date));
        MyAdapter adapter = new MyAdapter(getActivity(), App.currentUser.getIntakesForDate(date));
        list.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        if (v==back || v==forward){
            LocalDate next_date=null;
            String next_date_string ;
            if (v == back){
                next_date = date.minusDays(1);

            }
            if (v == forward){
                next_date = date.plusDays(1);
            }

            next_date_string =next_date.toString();

            Bundle hej = new Bundle();
            hej.putString("date", next_date_string);

            ((MainActivity) getActivity()).getNavC().navigate(R.id.daily_view_frag, hej);
        }

    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        render();
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
                b.putString("uuid",dataSet.get(position).getUuid());
                DialogFragment dialog = new edit_liquid();
                dialog.setArguments(b);
                dialog.show(getChildFragmentManager(),"dialog");

            });
            DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");

            tid.setText(dataSet.get(position).getDateTime().format(format));
            type.setText(dataSet.get(position).getType());
            mængde.setText(String.valueOf(dataSet.get(position).getSize()) + " ml");

            return rowView;
        }
    }
}
