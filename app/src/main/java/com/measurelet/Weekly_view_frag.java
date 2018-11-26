package com.measurelet;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hjorth.measurelet.R;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Weekly_view_frag extends Fragment implements AdapterView.OnItemClickListener {


    ListView listView;
    private View view;
    static ArrayList<VæskeRegistrering> væskeList = new ArrayList<VæskeRegistrering>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.weekly_view_frag, container, false);

        listView = view.findViewById(R.id.listWeek);
        listView.setOnItemClickListener(this);

        Calendar calendar = Calendar.getInstance();
        Date d1 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d2 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d3 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d4 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d5 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);


        VæskeRegistrering væske = new VæskeRegistrering();
        væske.setDate(d1);
        væske.setMængde(2000);

        VæskeRegistrering væske2 = new VæskeRegistrering();
        væske2.setDate(d2);
        væske2.setMængde(1000);

        VæskeRegistrering væske3 = new VæskeRegistrering();
        væske3.setDate(d3);
        væske3.setMængde(1500);


        VæskeRegistrering væske4 = new VæskeRegistrering();
        væske4.setDate(d4);
        væske4.setMængde(2800);

        VæskeRegistrering væske5 = new VæskeRegistrering();
        væske5.setDate(d5);
        væske5.setMængde(3000);

        væskeList.add(væske);
        væskeList.add(væske2);
        væskeList.add(væske3);
        væskeList.add(væske4);
        væskeList.add(væske5);


        createGraph(view);

        MyAdapter adapter = new MyAdapter(getActivity(), væskeList);
        listView.setAdapter(adapter);


        return view;
    }


    private void createGraph(View v) {
        GraphView graph = v.findViewById(R.id.graph);


        DataPoint[] pointsArray = new DataPoint[væskeList.size()];

        for (int i = 0; i < væskeList.size(); i++) {
            pointsArray[i] = new DataPoint(væskeList.get(i).getDate(), væskeList.get(i).getMængde());
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(pointsArray);

        graph.addSeries(series);

        final SimpleDateFormat format = new SimpleDateFormat("dd/MM");

        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    return format.format(new Date((long) value));
                } else {
                    return super.formatLabel(value, isValueX);
                }
            }
        });

        graph.getGridLabelRenderer().setTextSize(50);
        graph.getGridLabelRenderer().setVerticalLabelsAlign(Paint.Align.CENTER);
        graph.getViewport().setMinY(0.0);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setYAxisBoundsManual(true);

        graph.getViewport().setScrollable(true); // enables horizontal scrolling
        graph.getViewport().setScrollableY(true); // enables vertical scrolling
        graph.getViewport().setScalable(true); // enables horizontal zooming and scrolling
        graph.getViewport().setScalableY(true);

        graph.getGridLabelRenderer().setHumanRounding(false);


    }

    public static ArrayList<VæskeRegistrering> getVæske() {
        return væskeList;
    }

    public ArrayList<VæskeRegistrering> getDatoVæskeliste(Date date) {
        ArrayList<VæskeRegistrering> daglig = new ArrayList<>();
        for (VæskeRegistrering hej : væskeList) {
            if (hej.getDate().getDate() == date.getDate()) {
                daglig.add(hej);
            }
        }
        return daglig;
    }

  /*  private ArrayList<VæskeRegistrering> getUgentligVæskesorteret() {
        ArrayList<VæskeRegistrering> ugentlig = new ArrayList<>();
        for (VæskeRegistrering hej : væskeList) {
        }
        return ugentlig;
    }
    */

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        FragmentManager fm = getFragmentManager();
       // fm.beginTransaction().replace(R.id.mainfrag, new Daily_view_frag(), "daily").addToBackStack(null).commit();


    }

    private class MyAdapter extends ArrayAdapter<VæskeRegistrering> {
        private ArrayList<VæskeRegistrering> dataSet;
        Context mContext;

        public MyAdapter(@NonNull Context context, ArrayList<VæskeRegistrering> data) {
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

            SimpleDateFormat format = new SimpleDateFormat("dd. MMM");


            dato.setText(format.format(dataSet.get(position).getDate()));

            mængde.setText(String.valueOf(dataSet.get(position).getMængde()));

            return rowView;
        }

    }
}






