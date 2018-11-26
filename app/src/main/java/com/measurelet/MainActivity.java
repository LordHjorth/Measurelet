package com.measurelet;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.hjorth.measurelet.R;
import com.measurelet.registration.IntroSlidePager;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (!App.HasloggedIn) {
            startActivity(new Intent(this, LoginScreen_act.class));
        }

        getSupportFragmentManager().beginTransaction().add(R.id.mainfrag, new Dashboard_frag(), "main").commit();




        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_registrering) {
            getSupportFragmentManager().beginTransaction().replace(R.id.mainfrag, getSupportFragmentManager().findFragmentByTag("main")).addToBackStack(null).commit();
        } else if (id == R.id.nav_ugentligtoverblik) {
            getSupportFragmentManager().beginTransaction().replace(R.id.mainfrag, new Weekly_view_frag(), "week").addToBackStack(null).commit();

        } else if (id == R.id.nav_dagligoverblik) {
            getSupportFragmentManager().beginTransaction().replace(R.id.mainfrag, new Daily_view_frag(), "day").addToBackStack(null).commit();

        } else if (id == R.id.nav_profil) {
            getSupportFragmentManager().beginTransaction().replace(R.id.mainfrag, new Profile_frag(), "prof").addToBackStack(null).commit();
        } else if (id == R.id.nav_indstillinger) {
            getSupportFragmentManager().beginTransaction().replace(R.id.mainfrag, new Settings_frag(), "settings").addToBackStack(null).commit();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

 /* FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

}
