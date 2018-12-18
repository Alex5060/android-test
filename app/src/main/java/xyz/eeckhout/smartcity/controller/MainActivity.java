package xyz.eeckhout.smartcity.controller;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import xyz.eeckhout.smartcity.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private int init_fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        enableMyLocationIfPermitted();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if(savedInstanceState != null){
            startFragment( (int) savedInstanceState.getLong("fragment_actif", R.id.nav_camera));
        }
        else{
            startFragment();
        }

        getLoginFragment();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void enableMyLocationIfPermitted() {
        if (ContextCompat.checkSelfPermission(getBaseContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[],
                                           int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // La permission est garantie
                } else {
                    // La permission est refusée
                }
                break;
            }
        }
    }

    private void getMapFragment(){
        if (Utils.isDataConnectionAvailable(getApplicationContext())) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content, MapsFragment.newInstance())
                    .commit();
        } else {
            getInternetErrorFragment();
            Toast.makeText(getApplicationContext(), R.string.error_internet_connection, Toast.LENGTH_LONG).show();
        }
    }

    private void getLoginFragment(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, new LoginFragment())
                .commit();
    }


    private void getAccountFragment(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, new AccountFragment())
                .commit();
    }

    public void getMapPreferenceFragment(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, new Map_settingFragment())
                .commit();
    }

    private void getInternetErrorFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, new InternetConnectionErrorFragment())
                .commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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

    private void startFragment(int id){
        if (id == R.id.nav_camera) {
            init_fragment = R.id.nav_camera;
            getMapFragment();
        } else if (id == R.id.nav_gallery) {
            init_fragment = R.id.nav_gallery;
            getMapPreferenceFragment();

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {
            init_fragment = R.id.nav_manage;
            getAccountFragment();
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
    }

    private void startFragment(){
        startFragment(R.id.nav_camera);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        startFragment(id);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("fragment_actif", init_fragment);
    }
}
