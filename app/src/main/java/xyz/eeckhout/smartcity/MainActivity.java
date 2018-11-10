package xyz.eeckhout.smartcity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private MapsActivity mapFragment = new MapsActivity();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getMapFragment();
    }

    private void getMapFragment(){
//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.content, mapFragment)
//                .commit();
    }
}
