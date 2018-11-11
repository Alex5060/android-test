//package xyz.eeckhout.smartcity;
//
//import android.Manifest;
//import android.content.pm.PackageManager;
//import android.graphics.Color;
//import android.location.Location;
//import android.net.http.HttpResponseCache;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.content.ContextCompat;
//import android.util.Log;
//import android.widget.Toast;
//
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.BitmapDescriptorFactory;
//import com.google.android.gms.maps.model.CircleOptions;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.Marker;
//import com.google.android.gms.maps.model.MarkerOptions;
//import com.google.android.gms.maps.model.Polyline;
//import com.google.android.gms.maps.model.PolylineOptions;
//import com.google.android.gms.maps.model.RoundCap;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//
//import xyz.eeckhout.smartcity.DataAccess.BikeRouteNamurDAO;
//import xyz.eeckhout.smartcity.DataAccess.JCDecauxDAO;
//import xyz.eeckhout.smartcity.DataAccess.CarParkingNamurDAO;
//import xyz.eeckhout.smartcity.DataAccess.BikeParkingNamurDAO;
//import xyz.eeckhout.smartcity.Model.ItineraireVelo.BikeRouteNamur;
//import xyz.eeckhout.smartcity.Model.JCDecaux.JCDecauxBikes;
//import xyz.eeckhout.smartcity.Model.VilleNamur.ParkingVelo.BikeParkingNamur;
//import xyz.eeckhout.smartcity.Model.VilleNamur.ParkingVoiture.CarParkingNamur;
//import xyz.eeckhout.smartcity.Model.VilleNamur.ParkingVoiture.Record;
//
//public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnPolylineClickListener  {
//
//    private GoogleMap mMap;
//    private ArrayList<Marker> markers = new ArrayList<>();
//    private CarParkingNamur parkingAuto;
//    private BikeParkingNamur parkingVelo;
//    private BikeRouteNamur itineraireVelo;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_maps);
//        try {
//            File httpCacheDir = new File(this.getCacheDir(), "http");
//            long httpCacheSize = 10 * 1024 * 1024; // 10 MiB
//            HttpResponseCache.install(httpCacheDir, httpCacheSize);
//        } catch (IOException e) {
//            Log.i("Samy", "HTTP response cache installation failed:" + e);
//        }
//
//        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.mapDefault);
//        mapFragment.getMapAsync(this);
//    }
//
//    protected void onStop() {
//        super.onStop();
//        HttpResponseCache cache = HttpResponseCache.getInstalled();
//        if (cache != null) {
//            cache.flush();
//        }
//    }
//
//    /**
//     * Manipulates the map once available.
//     * This callback is triggered when the map is ready to be used.
//     * This is where we can add markers or lines, add listeners or move the camera. In this case,
//     * we just add a marker near Sydney, Australia.
//     * If Google Play services is not installed on the device, the user will be prompted to install
//     * it inside the SupportMapFragment. This method will only be triggered once the user has
//     * installed Google Play services and returned to the app.
//     */
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//        enableMyLocationIfPermitted();
//        mMap.getUiSettings().setZoomControlsEnabled(true);
//
//        /* Loading JCDecauxBikes */
//        LoadJCDecaux jcDecauxLoading = new LoadJCDecaux();
//        jcDecauxLoading.execute();
//
//        /* Loading Parking Voiture */
//        new LoadParkingAuto().execute();
//
//        /* Loading Parking Velo */
//        new LoadParkingVeloVille().execute();
//
//        /* Loading ItineraireVelo */
//        new LoadItineraireVeloVille().execute();
//
//        /* Move camera */
//        LatLng namur = new LatLng(50.469313, 4.862612);
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(namur,15));
//
//        // Set a listener for marker click.
//        mMap.setOnMarkerClickListener(this);
//        mMap.setOnPolylineClickListener(this);
//    }
//
//    private void enableMyLocationIfPermitted() {
//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
//                            Manifest.permission.ACCESS_FINE_LOCATION},
//                    1);
//        } else if (mMap != null) {
//            mMap.setMyLocationEnabled(true);
//        }
//    }
//
//    private GoogleMap.OnMyLocationButtonClickListener onMyLocationButtonClickListener =
//            new GoogleMap.OnMyLocationButtonClickListener() {
//                @Override
//                public boolean onMyLocationButtonClick() {
//                    mMap.setMinZoomPreference(15);
//                    return false;
//                }
//            };
//
//    private GoogleMap.OnMyLocationClickListener onMyLocationClickListener =
//            new GoogleMap.OnMyLocationClickListener() {
//                @Override
//                public void onMyLocationClick(@NonNull Location location) {
//
//                    mMap.setMinZoomPreference(12);
//
//                    CircleOptions circleOptions = new CircleOptions();
//                    circleOptions.center(new LatLng(location.getLatitude(),
//                            location.getLongitude()));
//
//                    circleOptions.radius(200);
//                    circleOptions.fillColor(Color.RED);
//                    circleOptions.strokeWidth(6);
//
//                    mMap.addCircle(circleOptions);
//                }
//            };
//    public boolean onMarkerClick(final Marker marker) {
//        // Check if a click count was set, then display the click count.
//         Toast.makeText(this,
//                    marker.getTitle() +
//                            " has been clicked :" + marker.getTag() + ".",
//                    Toast.LENGTH_LONG).show();
//
//        // Return false to indicate that we have not consumed the event and that we wish
//        // for the default behavior to occur (which is for the camera to move such that the
//        // marker is centered and for the marker's info window to open, if it has one).
//        return false;
//    }
//
//    @Override
//    public void onPolylineClick(Polyline polyline) {
//        Toast.makeText(this, polyline.getTag().toString(), Toast.LENGTH_LONG).show();
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           String permissions[],
//                                           int[] grantResults) {
//        switch (requestCode) {
//            case 1: {
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    // La permission est garantie
//                } else {
//                    // La permission est refusée
//                }
//                return;
//            }
//        }
//    }
//
//    private class LoadJCDecaux extends AsyncTask<String, Void, ArrayList<JCDecauxBikes>>
//    {
//        @Override
//        protected ArrayList<JCDecauxBikes> doInBackground(String ...params)
//        {
//            JCDecauxDAO jcDecauxDAO = new JCDecauxDAO();
//            ArrayList<JCDecauxBikes> velos = new ArrayList<>();
//            try {
//                velos = jcDecauxDAO.getAllJCDecaux();
//            }
//            catch (Exception e)
//            {
//                Log.i("erreur", e.getMessage());
//            }
//            return velos;
//        }
//
//        @Override
//        protected void onPostExecute (ArrayList<JCDecauxBikes> velos)
//        {
//            for(JCDecauxBikes velo : velos){
//                LatLng latLng = new LatLng(velo.getPosition().getLat(), velo.getPosition().getLng());
//                //BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
//                markers.add(
//                        mMap.addMarker(
//                            new MarkerOptions().position(latLng)
//                                    .title(velo.getName())
//                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.libiavelo2))
//                                    .snippet("Disponibilités : " + velo.getAvailableBikes()))
//                );
//                markers.get(markers.size() - 1).setTag(velo);
//            }
//        }
//    }
//
//    private class LoadParkingAuto extends AsyncTask<String, Void, CarParkingNamur>
//    {
//        @Override
//        protected CarParkingNamur doInBackground(String ...params)
//        {
//            CarParkingNamurDAO parkingAutoDAO  = new CarParkingNamurDAO();
//            CarParkingNamur examples = new CarParkingNamur();
//            try {
//                examples = parkingAutoDAO.getAllJCDecaux();
//            }
//            catch (Exception e)
//            {
//                Log.i("erreur", e.getMessage());
//            }
//            return examples;
//        }
//
//        @Override
//        protected void onPostExecute (CarParkingNamur parkingAuto)
//        {
//            MapsActivity.this.parkingAuto = parkingAuto;
//            for(Record record : parkingAuto.getRecords()){
//                LatLng latLng = new LatLng(record.getFields().getGeoPoint2d().get(0), record.getFields().getGeoPoint2d().get(1));
//                markers.add(
//                        mMap.addMarker(
//                                new MarkerOptions().position(latLng)
//                                        .title(record.getFields().getPlsyDescri())
//                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.parking_voiture))
//                                        .snippet("Nb places : "+ record.getFields().getPlaces()))
//                );
//                markers.get(markers.size() - 1).setTag(record);
//            }
//        }
//    }
//
//    private class LoadParkingVeloVille extends AsyncTask<String, Void, BikeParkingNamur>
//    {
//        @Override
//        protected BikeParkingNamur doInBackground(String ...params)
//        {
//
//            BikeParkingNamurDAO parkingVeloVilleDAO  = new BikeParkingNamurDAO();
//            BikeParkingNamur parkingVeloVille = new BikeParkingNamur();
//            try {
//                parkingVeloVille = parkingVeloVilleDAO.getAllParkingVeloVille();
//            }
//            catch (Exception e)
//            {
//                Log.i("erreur", e.getMessage());
//            }
//            return parkingVeloVille;
//        }
//
//        @Override
//        protected void onPostExecute (BikeParkingNamur parkingVeloVille)
//        {
//            parkingVelo = parkingVeloVille;
//            for(xyz.eeckhout.smartcity.Model.VilleNamur.ParkingVelo.Record record : parkingVeloVille.getRecords()){
//                LatLng latLng = new LatLng(record.getFields().getGeoPoint2d().get(0), record.getFields().getGeoPoint2d().get(1));
//                markers.add(
//                        mMap.addMarker(
//                                new MarkerOptions().position(latLng)
//                                        .title(record.getFields().getNomStation())
//                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.parking_velo))
//                                        .snippet("Nb places : "+ record.getFields().getNbreArceaux()))
//                );
//                markers.get(markers.size() - 1).setTag(record);
//            }
//        }
//    }
//
//    private class LoadItineraireVeloVille extends AsyncTask<String, Void, BikeRouteNamur>
//    {
//        @Override
//        protected BikeRouteNamur doInBackground(String ...params)
//        {
//            BikeRouteNamurDAO itineraireVeloVilleDAO  = new BikeRouteNamurDAO();
//            BikeRouteNamur itineraireVeloVille = new BikeRouteNamur();
//            try {
//                itineraireVeloVille = itineraireVeloVilleDAO.getAllItineraireVeloVille();
//            }
//            catch (Exception e)
//            {
//                Log.i("erreur", e.getMessage());
//            }
//            return itineraireVeloVille;
//        }
//
//        @Override
//        protected void onPostExecute (BikeRouteNamur itineraireVeloVille)
//        {
//            itineraireVelo = itineraireVeloVille;
//            for(xyz.eeckhout.smartcity.Model.ItineraireVelo.Record record : itineraireVeloVille.getRecords()){
//                PolylineOptions rectOptions = new PolylineOptions()
//                        .clickable(true)
//                        .width(30)
//                        .color(Color.rgb(0, 205, 0))
//                        .startCap(new RoundCap())
//                        .endCap(new RoundCap() )
//                        .addAll(record.getFields().getGeoShape().getLatLng())
//                        ;
//
//                Polyline polyline = mMap.addPolyline(rectOptions);
//                polyline.setTag(record);
//            }
//        }
//    }
//}
