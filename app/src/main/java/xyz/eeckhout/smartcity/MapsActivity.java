package xyz.eeckhout.smartcity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import xyz.eeckhout.smartcity.DataAccess.JCDecauxDAO;
import xyz.eeckhout.smartcity.Model.Example;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private ArrayList<Marker> markers = new ArrayList<Marker>();
    private ArrayList<Example> libiaVelos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET)) {
                //Permission déjà refusée
                Toast.makeText(this, "La localisation est nécessaire pour garentir une utilisation optimale !", Toast.LENGTH_LONG).show();
            } else {
                //Sinon demander la permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }

        new LoadJCDecaux().execute();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapDefault);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

//        LoadJCDecaux jcDecauxLoading = new LoadJCDecaux();
//        jcDecauxLoading.execute();

        // Add a marker in Sydney and move the camera
        LatLng namur = new LatLng(50.469313, 4.862612);
//        markers.add(mMap.addMarker(new MarkerOptions().position(namur).title("Namur")
//                .snippet("Population: 4,137,400")));
//        markers.get(markers.size() - 1).setTag("Stationnement vélos");
//        // icon with blue color
//        markers.add(mMap.addMarker(new MarkerOptions().position(new LatLng(50.471339, 4.854766))
//                .title("IESN")
//                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))));
//        markers.get(markers.size() - 1).setTag("Haute Ecole de Namur Liège Luxembourg, Implentation IESN");
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(namur,15));

        Example libiaVelo;
        for(int i = 0; i < libiaVelos.size(); i++){
            libiaVelo = libiaVelos.get(i);
            if(libiaVelo != null){
                LatLng latLng = new LatLng(libiaVelo.getPosition().getLat(), libiaVelo.getPosition().getLng());
                mMap.addMarker(new MarkerOptions().position(latLng).title(libiaVelo.getName())
                        .snippet("Disponibilités : " + libiaVelo.getAvailableBikes()));
            }
        }

        // Set a listener for marker click.
        mMap.setOnMarkerClickListener(this);
    }

    public boolean onMarkerClick(final Marker marker) {
        // Check if a click count was set, then display the click count.
         Toast.makeText(this,
                    marker.getTitle() +
                            " has been clicked :" + marker.getTag() + ".",
                    Toast.LENGTH_LONG).show();

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[],
                                           int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // La permission est garantie
                } else {
                    // La permission est refusée
                }
                return;
            }
        }
    }

    private class LoadJCDecaux extends AsyncTask<String, Void, ArrayList<Example>>
    {
        @Override
        protected ArrayList<Example> doInBackground(String ...params)
        {
            JCDecauxDAO jcDecauxDAO = new JCDecauxDAO();
            ArrayList<Example> examples = new ArrayList<>();
            try {
                examples = jcDecauxDAO.getAllJCDecaux();
            }
            catch (Exception e)
            {
                Log.i("erreur", e.getMessage());
            }
            return examples;
        }

        @Override
        protected void onPostExecute (ArrayList<Example> examples)
        {
            for(Example example : examples){
                MapsActivity.this.libiaVelos.add(example);
            }
        }
    }
}
