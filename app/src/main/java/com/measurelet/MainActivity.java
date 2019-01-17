package com.measurelet;

import android.animation.Animator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
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

import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;


public class MainActivity extends AppCompatActivity implements NavController.OnNavigatedListener {

    private NavController navC;
    private DrawerLayout drawer;
    private View nvH;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        navC = Navigation.findNavController(findViewById(R.id.nav_host));
        navC.addOnNavigatedListener(this);
        Toolbar toolbar = findViewById(R.id.toolbar);

        drawer = findViewById(R.id.drawer_layout);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        this.getSupportActionBar().setTitle("Dashboardeladen");

        NavigationUI.setupActionBarWithNavController(this, navC, drawer);
        nvH = findViewById(R.id.nav_host);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        NavigationUI.setupWithNavController(navigationView, navC);


        if (!App.isLoggedIn()) {
            navC.navigate(R.id.action_global_introSlidePager);
            return;
        }

        setupListeners();

    }

    public void setupListeners(){

        App.patientRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println(dataSnapshot.getValue());

                App.currentUser = dataSnapshot.getValue(Patient.class);

                System.out.println("Succeeded \n" + App.currentUser.getName());

                TextView bed = navigationView.getHeaderView(0).findViewById(R.id.bednumber);
                TextView name = navigationView.getHeaderView(0).findViewById(R.id.patientname);

                bed.setText(App.currentUser.getBedNum() + "");
                name.setText(App.currentUser.getName());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("Cancelled");
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (navC.getCurrentDestination().getId() == R.id.introSlidePager) {
            finish();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            super.onBackPressed();
        }
    }

    public LottieAnimationView getAddAnimation(int i) {

        LottieAnimationView lw;

        lw = findViewById(R.id.lot_view);
        lw.setAnimation("checkm.zip");

        switch (i) {
            case 1:
                lw.setAnimation("checkm.zip");
                lw.setSpeed(1f);
                break;

            case 2:
                lw.setAnimation("trail_loading.json");
                break;
        }

        lw.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                nvH.animate().alpha(0.3f);
                lw.setVisibility(View.VISIBLE);
                lw.animate().alpha(0.9f);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                nvH.animate().alpha(1f);
                lw.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                lw.setVisibility(View.INVISIBLE);
                nvH.animate().alpha(1f);

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        return lw;
    }

    public NavController getNavC() {
        return navC;
    }

    public void setNavC(NavController navC) {
        this.navC = navC;
    }

    public void setActionBarTitle(String title) {

        getSupportActionBar().setTitle(title);
    }

    @Override
    public void onNavigated(@NonNull NavController controller, @NonNull NavDestination destination) {
        String title = "";
        switch (destination.getId()) {
        }

    }
}
