package xyz.eeckhout.smartcity.controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import xyz.eeckhout.smartcity.R;

public class MainActivity extends AppCompatActivity {
    private Fragment mapFragment = new MapsFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Utils.isDataConnectionAvailable(getApplicationContext())) {
            getMapFragment();
        } else {
            getInternetErrorFragment();
            Toast.makeText(getApplicationContext(), R.string.error_internet_connection, Toast.LENGTH_LONG).show();
        }
    }

    private void getMapFragment(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, mapFragment)
                .commit();
    }


    private void getInternetErrorFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, new InternetConnectionErrorFragment())
                .commit();
    }
}
