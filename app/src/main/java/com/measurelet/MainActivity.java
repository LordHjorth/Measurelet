package com.measurelet;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.hjorth.measurelet.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.measurelet.Model.Patient;
import com.measurelet.registration.IntroSlidePager;

import java.util.List;

import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;


public class MainActivity extends AppCompatActivity implements NavController.OnNavigatedListener {

    private NavController navC;
    private  DrawerLayout drawer;
    private   View nvH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        setNavC(Navigation.findNavController(findViewById(R.id.nav_host))); //navC= ;
        Toolbar toolbar = findViewById(R.id.toolbar);

        drawer = findViewById(R.id.drawer_layout);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        NavigationUI.setupActionBarWithNavController(this, navC, drawer);
        navC.addOnNavigatedListener(this);
        nvH=findViewById(R.id.nav_host);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        NavigationUI.setupWithNavController(navigationView, navC);

       if(!App.isLoggedIn()) {
            navC.navigate(R.id.action_global_introSlidePager);
        }
/*
        Patient patient = App.getCurrentPatient();
        if(patient != null && findViewById(R.id.nav_header_name) != null){
            ((TextView)findViewById(R.id.nav_header_name)).setText(patient.name);
        }*/

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (App.patientRef != null) {
            App.patientRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    System.out.println("PATIENT FETCHED");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
          super.onBackPressed();


        }
    }

    public void getAddAnimation(){
        LottieAnimationView lw;

        lw=findViewById(R.id.lot_view);
        lw.setAnimation("checkm.zip");
        lw.setSpeed(1f);

        lw.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {nvH.animate().alpha(0.3f);
                lw.setVisibility(View.VISIBLE);
                lw.animate().alpha(0.9f);
            }

            @Override
            public void onAnimationEnd(Animator animation) { nvH.animate().alpha(1f);

                lw.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        lw.playAnimation();

    }

    public NavController getNavC() {
        return navC;
    }

    public void setNavC(NavController navC) {
        this.navC = navC;
    }

    @Override
    public void onNavigated(@NonNull NavController controller, @NonNull NavDestination destination) {
        if(destination.getId()==navC.getGraph().getStartDestination()){
            navC.popBackStack(navC.getGraph().getStartDestination(),false);

        }
    }
}
