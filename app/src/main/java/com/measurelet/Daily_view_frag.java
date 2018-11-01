package com.measurelet;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hjorth.measurelet.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Daily_view_frag extends Fragment implements View.OnClickListener {

    private TextView date;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View dailyView = inflater.inflate(R.layout.daily_view_frag, container, false);


        String  dateToday = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());

        date = dailyView.findViewById(R.id.date);
        date.setText(dateToday);


        return dailyView;
    }

    @Override
    public void onClick(View v) {

    }
}
