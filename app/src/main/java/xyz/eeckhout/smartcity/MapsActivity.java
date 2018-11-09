package xyz.eeckhout.smartcity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
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
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;

import java.util.ArrayList;

import xyz.eeckhout.smartcity.DataAccess.ItineraireVeloVilleDAO;
import xyz.eeckhout.smartcity.DataAccess.JCDecauxDAO;
import xyz.eeckhout.smartcity.DataAccess.ParkingAutoDAO;
import xyz.eeckhout.smartcity.DataAccess.ParkingVeloVilleDAO;
import xyz.eeckhout.smartcity.Model.ItineraireVelo.ItineraireVeloVille;
import xyz.eeckhout.smartcity.Model.JCDecaux.JCDecauxVelos;
import xyz.eeckhout.smartcity.Model.VilleNamur.ParkingVelo.ParkingVeloVille;
import xyz.eeckhout.smartcity.Model.VilleNamur.ParkingVoiture.ParkingAuto;
import xyz.eeckhout.smartcity.Model.VilleNamur.ParkingVoiture.Record;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private ArrayList<Marker> markers = new ArrayList<Marker>();
    private ParkingAuto parkingAuto;
    private ParkingVeloVille parkingVelo;
    private ItineraireVeloVille itineraireVelo;

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

        /* Loading JCDecauxVelos */
        LoadJCDecaux jcDecauxLoading = new LoadJCDecaux();
        jcDecauxLoading.execute();

        /* Loading Parking Voiture */
        new LoadParkingAuto().execute();

        /* Loading Parking Velo */
        new LoadParkingVeloVille().execute();

        /* Loading ItineraireVelo */
        new LoadItineraireVeloVille().execute();

        /* Move camera */
        LatLng namur = new LatLng(50.469313, 4.862612);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(namur,15));

        // Set a listener for marker click.
        mMap.setOnMarkerClickListener(this);

        // Instantiates a new Polyline object and adds points to define a rectangle

//        PolylineOptions rectOptions = new PolylineOptions()
//                .clickable(true)
//                .add(new LatLng(50.4703316231, 4.8655017399)
//                , new LatLng(50.4701745972, 4.8652616016)
//                , new LatLng(50.4701317925,  4.8643163498))
//                .width(30)
//                .color(Color.rgb(255, 180, 0))
//                .startCap(new RoundCap())
//                .endCap(new RoundCap() )
//                ;
//        Polyline polyline = mMap.addPolyline(rectOptions);
//        polyline.setTag("A");

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

    private class LoadJCDecaux extends AsyncTask<String, Void, ArrayList<JCDecauxVelos>>
    {
        @Override
        protected ArrayList<JCDecauxVelos> doInBackground(String ...params)
        {
            JCDecauxDAO jcDecauxDAO = new JCDecauxDAO();
            ArrayList<JCDecauxVelos> velos = new ArrayList<>();
            try {
                velos = jcDecauxDAO.getAllJCDecaux();
            }
            catch (Exception e)
            {
                Log.i("erreur", e.getMessage());
            }
            return velos;
        }

        @Override
        protected void onPostExecute (ArrayList<JCDecauxVelos> velos)
        {
            for(JCDecauxVelos velo : velos){
                LatLng latLng = new LatLng(velo.getPosition().getLat(), velo.getPosition().getLng());
                markers.add(
                        mMap.addMarker(
                            new MarkerOptions().position(latLng)
                                    .title(velo.getName())
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                                    .snippet("Disponibilités : " + velo.getAvailableBikes()))
                );
                markers.get(markers.size() - 1).setTag(velo);
            }
        }
    }

    private class LoadParkingAuto extends AsyncTask<String, Void, ParkingAuto>
    {
        @Override
        protected ParkingAuto doInBackground(String ...params)
        {
            ParkingAutoDAO parkingAutoDAO  = new ParkingAutoDAO();
            ParkingAuto examples = new ParkingAuto();
            try {
                examples = parkingAutoDAO.getAllJCDecaux();
            }
            catch (Exception e)
            {
                Log.i("erreur", e.getMessage());
            }
            return examples;
        }

        @Override
        protected void onPostExecute (ParkingAuto parkingAuto)
        {
            MapsActivity.this.parkingAuto = parkingAuto;
            for(Record record : parkingAuto.getRecords()){
                LatLng latLng = new LatLng(record.getFields().getGeoPoint2d().get(0), record.getFields().getGeoPoint2d().get(1));
                markers.add(
                        mMap.addMarker(
                                new MarkerOptions().position(latLng)
                                        .title(record.getFields().getPlsyDescri())
                                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                                        .snippet("Nb places : "+ record.getFields().getPlaces()))
                );
                markers.get(markers.size() - 1).setTag(record);
            }
        }
    }

    private class LoadParkingVeloVille extends AsyncTask<String, Void, ParkingVeloVille>
    {
        @Override
        protected ParkingVeloVille doInBackground(String ...params)
        {

            ParkingVeloVilleDAO parkingVeloVilleDAO  = new ParkingVeloVilleDAO();
            ParkingVeloVille parkingVeloVille = new ParkingVeloVille();
            try {
                parkingVeloVille = parkingVeloVilleDAO.getAllParkingVeloVille();
            }
            catch (Exception e)
            {
                Log.i("erreur", e.getMessage());
            }
            return parkingVeloVille;
        }

        @Override
        protected void onPostExecute (ParkingVeloVille parkingVeloVille)
        {
            parkingVelo = parkingVeloVille;
            for(xyz.eeckhout.smartcity.Model.VilleNamur.ParkingVelo.Record record : parkingVeloVille.getRecords()){
                LatLng latLng = new LatLng(record.getFields().getGeoPoint2d().get(0), record.getFields().getGeoPoint2d().get(1));
                markers.add(
                        mMap.addMarker(
                                new MarkerOptions().position(latLng)
                                        .title(record.getFields().getNomStation())
                                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                                        .snippet("Nb places : "+ record.getFields().getNbreArceaux()))
                );
                markers.get(markers.size() - 1).setTag(record);
            }
        }
    }

    private class LoadItineraireVeloVille extends AsyncTask<String, Void, ItineraireVeloVille>
    {
        @Override
        protected ItineraireVeloVille doInBackground(String ...params)
        {
            ItineraireVeloVilleDAO itineraireVeloVilleDAO  = new ItineraireVeloVilleDAO();
            ItineraireVeloVille itineraireVeloVille = new ItineraireVeloVille();
            try {
                itineraireVeloVille = itineraireVeloVilleDAO.getAllItineraireVeloVille();
            }
            catch (Exception e)
            {
                Log.i("erreur", e.getMessage());
            }
            return itineraireVeloVille;
        }

        @Override
        protected void onPostExecute (ItineraireVeloVille itineraireVeloVille)
        {
            itineraireVelo = itineraireVeloVille;
            for(xyz.eeckhout.smartcity.Model.ItineraireVelo.Record record : itineraireVeloVille.getRecords()){
                PolylineOptions rectOptions = new PolylineOptions()
                        .clickable(true)
                        .width(30)
                        .color(Color.rgb(255, 180, 0))
                        .startCap(new RoundCap())
                        .endCap(new RoundCap() )
                        .addAll(record.getFields().getGeoShape().getLatLng())
                        ;

                Polyline polyline = mMap.addPolyline(rectOptions);
                polyline.setTag(record);
            }
        }
    }
}
