package com.measurelet;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.Date;

public class Daily_view_frag extends Fragment implements View.OnClickListener {


    private ListView list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View dailyView = inflater.inflate(R.layout.daily_view_frag, container, false);

        list=dailyView.findViewById(R.id.listDaily);

        MyAdapter adapter = new MyAdapter(getActivity(),Weekly_view_frag.getVæske());
        list.setAdapter(adapter);

        createGraph(dailyView);

        return dailyView;
    }



    private void createGraph(View v){
        GraphView graph = v.findViewById(R.id.graph);



        ArrayList<VæskeRegistrering> list = Weekly_view_frag.væskeList;
        DataPoint[] pointsArray = new DataPoint[list.size()];

        final SimpleDateFormat format = new SimpleDateFormat("HH:mm");


        for (int i = 0; i < list.size(); i++) {
            pointsArray[i]=new DataPoint(list.get(i).getDate(), list.get(i).getMængde());
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(pointsArray);

        graph.addSeries(series);


        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(){
            @Override
            public String formatLabel(double value, boolean isValueX){
                if(isValueX){
                    return format.format(new Date((long) value));
                }
                else {
                    return super.formatLabel(value, isValueX);
                }
            }
        });        //graph.getGridLabelRenderer().setNumHorizontalLabels(3); // only 4 because of the space

        // set manual x bounds to have nice steps
        // graph.getViewport().setMinX(d1.getTime());
        // graph.getViewport().setMaxX(d5.getTime());
        graph.getViewport().setMinY(0.0);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setYAxisBoundsManual(true);

        graph.getViewport().setScrollable(true); // enables horizontal scrolling
        graph.getViewport().setScrollableY(true); // enables vertical scrolling
        graph.getViewport().setScalable(true); // enables horizontal zooming and scrolling
        graph.getViewport().setScalableY(true);

// as we use dates as labels, the human rounding to nice readable numbers
// is not necessary
        graph.getGridLabelRenderer().setHumanRounding(false);


    }

    @Override
    public void onClick(View v) {

    }

    private class MyAdapter extends ArrayAdapter<VæskeRegistrering> {
        private ArrayList<VæskeRegistrering> dataSet;
        Context mContext;

        public MyAdapter(@NonNull Context context, ArrayList<VæskeRegistrering> data) {
            super(context, R.layout.list_daily, data);
            this.dataSet=data;
            this.mContext=context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.list_daily, parent, false);

           TextView tid = rowView.findViewById(R.id.tid_daily);
           TextView mængde=rowView.findViewById(R.id.ml_daily);
           TextView type = rowView.findViewById(R.id.type_daily);


            SimpleDateFormat format = new SimpleDateFormat("HH:mm");

            tid.setText(format.format(dataSet.get(position).getDate()));
            type.setText(dataSet.get(position).getType());
            mængde.setText(String.valueOf(dataSet.get(position).getMængde()));


            return rowView;
        }
    }
}
