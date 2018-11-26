package com.measurelet;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.airbnb.lottie.LottieAnimationView;
import com.example.hjorth.measurelet.R;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

public class Dashboard_frag extends Fragment implements View.OnClickListener {
    ImageButton add_btn;
    private LottieAnimationView lw;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View dashboard = inflater.inflate(R.layout.dashboard_frag, container, false);
        add_btn = dashboard.findViewById(R.id.add_btn);
        add_btn.setOnClickListener(this);



        return dashboard;


    }

    @Override
    public void onClick(View view) {
        if (view == add_btn) {

            NavHostFragment.findNavController(this).navigate(R.id.registration_standard_frag);
        }
    }
}
