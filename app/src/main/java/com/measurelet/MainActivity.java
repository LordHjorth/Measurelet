package com.measurelet;

import android.animation.Animator;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.crashlytics.android.Crashlytics;
import com.example.hjorth.measurelet.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.measurelet.Model.Patient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import io.fabric.sdk.android.Fabric;


public class MainActivity extends AppCompatActivity implements NavController.OnNavigatedListener {

    private NavController navC;
    private DrawerLayout drawer;
    private View nvH;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.main_activity);

        navC = Navigation.findNavController(findViewById(R.id.nav_host));
        Toolbar toolbar = findViewById(R.id.toolbar);

        drawer = findViewById(R.id.drawer_layout);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        NavigationUI.setupActionBarWithNavController(this, navC, drawer);
        nvH = findViewById(R.id.nav_host);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        NavigationUI.setupWithNavController(navigationView, navC);

        navC.addOnNavigatedListener(this);

        if (!App.isLoggedIn()) {
            navC.navigate(R.id.action_global_introSlidePager);
            return;
        }

        setupListeners();

    }

    public void setupListeners() {

        App.currentUser = new Patient();

        App.patientRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(App.currentUser == null){
                    App.currentUser = dataSnapshot.getValue(Patient.class);
                    return;
                }


                new AsyncTask(){

                    @Override
                    protected Object doInBackground(Object[] objects) {

                        Patient p = dataSnapshot.getValue(Patient.class);
                        App.currentUser = p;

                        return null;
                    }

                    @Override
                    protected void onPostExecute(Object o) {
                        super.onPostExecute(o);

                        TextView bed = navigationView.getHeaderView(0).findViewById(R.id.bednumber);
                        TextView name = navigationView.getHeaderView(0).findViewById(R.id.patientname);

                        bed.setText(App.currentUser.getBedNum() + "");
                        name.setText(App.currentUser.getName());
                    }
                }.execute();

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
            case R.id.daily_view_frag:
                title = getString(R.string.daily_view);
                break;
            case R.id.edit_liquid:
                title = getString(R.string.edit_liquid);
                break;
            case R.id.profile_frag:
                title = getString(R.string.profil);
                break;
            case R.id.reg_weight_frag:
                title = getString(R.string.registration_weight);
                break;
            case R.id.registration_custom_frag:
                title = getString(R.string.registration_custom);
                break;
            case R.id.registration_standard_frag:
                title = getString(R.string.registration_standard);
                break;
            case R.id.settings_frag:
                title = getString(R.string.settings);
                break;
            case R.id.weekly_view_frag:
                title = getString(R.string.weekly_view);
                break;
            case R.id.dashboard_frag:
                title = getString(R.string.dashboard);
                break;
            default:
                title = getString(R.string.dashboard);
                break;
        }
        setActionBarTitle(title);
    }
}
