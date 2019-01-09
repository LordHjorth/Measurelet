package com.measurelet;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
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
import com.measurelet.Model.Patient;
import com.measurelet.registration.IntroSlidePager;

import java.util.List;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;


public class MainActivity extends AppCompatActivity {

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

/*        Patient patient = App.getCurrentPatient();
        if(patient != null && findViewById(R.id.nav_header_name) != null){
            ((TextView)findViewById(R.id.nav_header_name)).setText(patient.name);
        }*/
    }


    @Override
    public void onBackPressed() {

        Fragment f = getSupportFragmentManager().findFragmentById(R.id.nav_host);
        List<Fragment> a = f.getChildFragmentManager().getFragments();
        if(a.get(a.size()-1).getClass().equals(IntroSlidePager.class)){
            finishAffinity();
            return;
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            navC.navigateUp();


        }
    }

    public void getAddAnimation(){
        LottieAnimationView lw;

        lw=findViewById(R.id.lot_view);
        lw.setAnimation("checkm.zip");
        lw.setSpeed(0.8f);

        lw.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {nvH.setVisibility(View.INVISIBLE);
                lw.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) { nvH.setVisibility(View.VISIBLE);
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

}
