package xyz.eeckhout.smartcity.controller;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;
import com.google.android.gms.maps.model.VisibleRegion;

import java.util.ArrayList;

import xyz.eeckhout.smartcity.R;
import xyz.eeckhout.smartcity.dataAccess.BikeParkingNamurWS;
import xyz.eeckhout.smartcity.dataAccess.BikeRouteNamurWS;
import xyz.eeckhout.smartcity.dataAccess.CarParkingNamurWS;
import xyz.eeckhout.smartcity.model.jcdecaux.JCDecauxStation;
import xyz.eeckhout.smartcity.model.villeNamur.bikeParking.BikeParkingNamur;
import xyz.eeckhout.smartcity.model.villeNamur.bikeRoute.BikeRouteNamur;
import xyz.eeckhout.smartcity.model.villeNamur.carParking.CarParkingNamur;
import xyz.eeckhout.smartcity.model.villeNamur.carParking.Record;


public class MapsFragment extends Fragment implements OnMapReadyCallback {
    private View rootView;
    private GoogleMap map;
    private MapView mMapView;
    private static final int JCDECAUX_MIDDLE_LIMIT = 5;
    private static final int JCDECAUX_CRITICAL_LIMIT = 2;
    private CarParkingNamur carParkingNamur;
    private BikeParkingNamur parkingVelo;
    private BikeRouteNamur itineraireVelo;
    private ArrayList<JCDecauxStation> jcDecauxBikes;
    private static final long MIN_TIME = 400;
    private static final float MIN_DISTANCE = 100;
    private MapViewModel model;

    public static xyz.eeckhout.smartcity.controller.MapsFragment newInstance() {
        return new xyz.eeckhout.smartcity.controller.MapsFragment();
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        model = ViewModelProviders.of(this).get(MapViewModel.class);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        try {
            rootView = inflater.inflate(R.layout.maps_fragment, container, false);
            MapsInitializer.initialize(this.getActivity());
            mMapView = (MapView) rootView.findViewById(R.id.map);
            mMapView.onCreate(savedInstanceState);
            mMapView.getMapAsync(this);
            FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.button_loading_libiavelo);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addAllData();
                }
            });
        }
        catch (InflateException e){
            Log.e("samy", "Inflate exception");
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        model = ViewModelProviders.of(this).get(MapViewModel.class);
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        if (Utils.isDataConnectionAvailable(getContext())) {
            /* Move camera */
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(50.469313, 4.862612), 15));
            map.getUiSettings().setZoomControlsEnabled(true);

            addAllData();

            // Set a listener for marker click.
            map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(final Marker marker) {
                    // Check if a click count was set, then display the click count.
                    Toast.makeText(getContext(), marker.getTitle() + " has been clicked :" + marker.getTag() + ".", Toast.LENGTH_LONG).show();

                    // Return false to indicate that we have not consumed the event and that we wish
                    // for the default behavior to occur (which is for the camera to move such that the
                    // marker is centered and for the marker's info window to open, if it has one).
                    return false;
                }
            });
            map.setOnPolylineClickListener(new GoogleMap.OnPolylineClickListener() {
                @Override
                public void onPolylineClick(Polyline polyline) {
                    Toast.makeText(getContext(), polyline.getTag().toString(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void addAllData(){
        map.clear();
        if (PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("isJCDecauxLoadingEnable", true)) {
            addAllLibiaVeloMarkers(model.getJcDecauxBikes().getValue());
        }
        if (PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("isCarParkingNamurLoadingEnable", true)) {
            //if(carParkingNamur != null)
                addCarParkings(model.getCarParkingNamur().getValue());
        }
        if (PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("isBikeParkingNamurLoadingEnable", true)) {
           //if(parkingVelo != null)
                addBikeParkings(model.getBikeParking().getValue());
        }
        if (PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("isBikeRouteLoadingEnable", true)) {
            //if(itineraireVelo != null)
                addBikesRoutes(model.getItineraireVelo().getValue());
        }
    }

    private void addAllLibiaVeloMarkers(ArrayList<JCDecauxStation> bikes){
        for (JCDecauxStation bike : bikes) {
            LatLng latLng = new LatLng(bike.getPosition().getLat(), bike.getPosition().getLng());
            Marker marker = map.addMarker(
                    new MarkerOptions().position(latLng)
                            .title(bike.getName())
                            .snippet("Disponibilités : " + bike.getAvailableBikes()));
            if (bike.getAvailableBikes() > JCDECAUX_MIDDLE_LIMIT) {
                marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.libiavelo2_vert));
            } else {
                if (bike.getAvailableBikes() > JCDECAUX_CRITICAL_LIMIT) {
                    marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.libiavelo2_orange));
                } else {
                    marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.libiavelo2_rouge));
                }
            }
            marker.setTag(bike);
        }
    }

    private VisibleRegion getVisibleRegion() {
        return map.getProjection().getVisibleRegion();
    }

    private void addCarParkings(CarParkingNamur carParkingNamur){
        if(carParkingNamur != null && carParkingNamur.getRecords() != null) {
            for (Record record : carParkingNamur.getRecords()) {
                LatLng latLng = new LatLng(record.getFields().getGeoPoint2d().get(0), record.getFields().getGeoPoint2d().get(1));
                map.addMarker(
                        new MarkerOptions().position(latLng)
                                .title(record.getFields().getPlsyDescri())
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.parking_voiture))
                                .snippet("Nb places : " + record.getFields().getPlaces()))
                        .setTag(record);
            }
        }
    }

    private void addBikeParkings(BikeParkingNamur bikeParkingNamur){
        for (xyz.eeckhout.smartcity.model.villeNamur.bikeParking.Record record : bikeParkingNamur.getRecords()) {
            LatLng latLng = new LatLng(record.getFields().getGeoPoint2d().get(0), record.getFields().getGeoPoint2d().get(1));
            map.addMarker(
                    new MarkerOptions().position(latLng)
                            .title(record.getFields().getNomStation())
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.parking_velo))
                            .snippet("Nb places : " + record.getFields().getNbreArceaux())
            ).setTag(record);
        }
    }

    private void addBikesRoutes(BikeRouteNamur bikeRouteNamur){
        for (xyz.eeckhout.smartcity.model.villeNamur.bikeRoute.Record record : bikeRouteNamur.getRecords()) {
            PolylineOptions rectOptions = new PolylineOptions()
                    .clickable(true)
                    .width(15)
                    .color(Color.rgb(0, 205, 0))
                    .startCap(new RoundCap())
                    .endCap(new RoundCap())
                    .addAll(record.getFields().getGeoShape().getLatLng());

            Polyline polyline = map.addPolyline(rectOptions);
            polyline.setTag(record);
        }
    }
}

//
//public class MapsFragment extends Fragment implements OnMapReadyCallback{
//    private static final int JCDECAUX_MIDDLE_LIMIT = 5;
//    private static final int JCDECAUX_CRITICAL_LIMIT = 2;
//    private GoogleMap mMap;
//    private LocationManager locationManager;
//    private ArrayList<Marker> markers = new ArrayList<>();
//    private CarParkingNamur carParkingNamur;
//    private BikeParkingNamur parkingVelo;
//    private BikeRouteNamur itineraireVelo;
//    private LoadBikeParkingNamur loadBikeParkingNamur;
//    private LoadBikeRouteNamur loadBikeRouteNamur;
//    private LoadCarParkingNamur loadCarParkingNamur;
//    private LoadJCDecaux loadJCDecaux;
//    private static final long MIN_TIME = 400;
//    private static final float MIN_DISTANCE = 100;
//
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//        if (Utils.isDataConnectionAvailable(getContext())) {
//            /* Move camera */
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(50.469313, 4.862612), 15));
//            mMap.getUiSettings().setZoomControlsEnabled(true);
//
//            // Set a listener for marker click.
//            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//                @Override
//                public boolean onMarkerClick(final Marker marker) {
//                    // Check if a click count was set, then display the click count.
//                    Toast.makeText(getContext(), marker.getTitle() + " has been clicked :" + marker.getTag() + ".", Toast.LENGTH_LONG).show();
//
//                    // Return false to indicate that we have not consumed the event and that we wish
//                    // for the default behavior to occur (which is for the camera to move such that the
//                    // marker is centered and for the marker's info window to open, if it has one).
//                    return false;
//                }
//            });
//            mMap.setOnPolylineClickListener(new GoogleMap.OnPolylineClickListener() {
//                @Override
//                public void onPolylineClick(Polyline polyline) {
//                    Toast.makeText(getContext(), polyline.getTag().toString(), Toast.LENGTH_LONG).show();
//                }
//            });
//
//            mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
//                @Override
//                public void onCameraIdle() {
//                    VisibleRegion visibleRegion = mMap.getProjection().getVisibleRegion();
//                    mMap.clear();
//                    if (PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("isJCDecauxLoadingEnable", true)) {
//                        /* Loading JCDecauxStation */
//                        loadJCDecaux = new LoadJCDecaux();
//                        loadJCDecaux.execute();
//                    }
//                    if (PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("isCarParkingNamurLoadingEnable", true)) {
//                        /* Loading Parking Voiture */
//                        loadCarParkingNamur = new LoadCarParkingNamur();
//                        loadCarParkingNamur.execute(visibleRegion);
//                    }
//                    if (PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("isBikeParkingNamurLoadingEnable", true)) {
//                        /* Loading Parking Velo */
//                        loadBikeParkingNamur = new LoadBikeParkingNamur();
//                        loadBikeParkingNamur.execute(visibleRegion);
//                    }
//                    if (PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("isBikeRouteLoadingEnable", true)) {
//                        /* Loading ItineraireVelo */
//                        loadBikeRouteNamur = new LoadBikeRouteNamur();
//                        loadBikeRouteNamur.execute(visibleRegion);
//                    }
//                }
//            });
//
//            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
//                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, new LocationListener() {
//                    @Override
//                    public void onLocationChanged(Location location) {
//                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//                        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
//                        mMap.animateCamera(cameraUpdate);
//                        locationManager.removeUpdates(this);
//                    }
//
//                    @Override
//                    public void onStatusChanged(String provider, int status, Bundle extras) {
//
//                    }
//
//                    @Override
//                    public void onProviderEnabled(String provider) {
//
//                    }
//
//                    @Override
//                    public void onProviderDisabled(String provider) {
//
//                    }
//                }); //You can also use LocationManager.GPS_PROVIDER and LocationManager.PASSIVE_PROVIDER
//            }
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.activity_maps, container, false);
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        if (loadJCDecaux != null) {
//            loadJCDecaux.cancel(true);
//        }
//        if (loadBikeParkingNamur != null) {
//            loadBikeParkingNamur.cancel(true);
//        }
//        if (loadBikeRouteNamur != null) {
//            loadBikeRouteNamur.cancel(true);
//        }
//        if (loadCarParkingNamur != null) {
//            loadCarParkingNamur.cancel(true);
//        }
//    }
//
//    private class LoadJCDecaux extends AsyncTask<String, Void, ArrayList<JCDecauxStation>> {
//        @Override
//        protected ArrayList<JCDecauxStation> doInBackground(String... params) {
//            JCDecauxWS jcDecauxDAO = new JCDecauxWS();
//            ArrayList<JCDecauxStation> bikes = new ArrayList<>();
//            try {
//                bikes = jcDecauxDAO.getAllJCDecaux();
//            } catch (Exception e) {
//                Log.i("erreur", e.getMessage());
//            }
//            return bikes;
//        }
//
//        @Override
//        protected void onPostExecute(ArrayList<JCDecauxStation> bikes) {
//            for (JCDecauxStation bike : bikes) {
//                LatLng latLng = new LatLng(bike.getPosition().getLat(), bike.getPosition().getLng());
//                markers.add(
//                        mMap.addMarker(
//                                new MarkerOptions().position(latLng)
//                                        .title(bike.getName())
//                                        .snippet("Disponibilités : " + bike.getAvailableBikes()))
//                );
//                Marker marker = markers.get(markers.size() - 1);
//                if (bike.getAvailableBikes() > JCDECAUX_MIDDLE_LIMIT) {
//                    marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.libiavelo2_vert));
//                } else {
//                    if (bike.getAvailableBikes() > JCDECAUX_CRITICAL_LIMIT) {
//                        marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.libiavelo2_orange));
//                    } else {
//                        marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.libiavelo2_rouge));
//                    }
//                }
//                marker.setTag(bike);
//            }
//        }
//    }
//
//    private VisibleRegion getVisibleRegion() {
//        return mMap.getProjection().getVisibleRegion();
//    }
//
//    private class LoadCarParkingNamur extends AsyncTask<Object, Void, CarParkingNamur> {
//        @Override
//        protected CarParkingNamur doInBackground(Object... params) {
//            if (params.length > 0) {
//                CarParkingNamurWS carParkingNamurDAO = new CarParkingNamurWS();
//                CarParkingNamur examples = new CarParkingNamur();
//                try {
//                    VisibleRegion visibleRegion = (VisibleRegion) params[0];
//                    //examples = carParkingNamurDAO.getAllJCDecaux();
//                    examples = carParkingNamurDAO.getParkingFromArea(visibleRegion.latLngBounds.getCenter(), Utils.getDistanceVisibleRegion(visibleRegion));
//                } catch (Exception e) {
//                    Log.i("erreur", e.getMessage());
//                }
//                return examples;
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(CarParkingNamur carParkingNamur) {
//            if (carParkingNamur != null) {
//                MapsFragment.this.carParkingNamur = carParkingNamur;
//                for (Record record : carParkingNamur.getRecords()) {
//                    LatLng latLng = new LatLng(record.getFields().getGeoPoint2d().get(0), record.getFields().getGeoPoint2d().get(1));
//                    markers.add(
//                            mMap.addMarker(
//                                    new MarkerOptions().position(latLng)
//                                            .title(record.getFields().getPlsyDescri())
//                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.parking_voiture))
//                                            .snippet("Nb places : " + record.getFields().getPlaces()))
//                    );
//                    markers.get(markers.size() - 1).setTag(record);
//                }
//            }
//        }
//    }
//
//    private class LoadBikeParkingNamur extends AsyncTask<Object, Void, BikeParkingNamur> {
//        @Override
//        protected BikeParkingNamur doInBackground(Object... params) {
//            if (params.length > 0) {
//                BikeParkingNamurWS bikeParkingNamurDAO = new BikeParkingNamurWS();
//                BikeParkingNamur bikeParkingNamur = new BikeParkingNamur();
//                try {
//                    VisibleRegion visibleRegion = (VisibleRegion) params[0];
//                    //bikeParkingNamur = bikeParkingNamurDAO.getAllParkingVeloVille();
//                    bikeParkingNamur = bikeParkingNamurDAO.getPakingVeloFromArez(visibleRegion.latLngBounds.getCenter(), Utils.getDistanceVisibleRegion(visibleRegion));
//                } catch (Exception e) {
//                    Log.i("erreur", e.getMessage());
//                }
//                return bikeParkingNamur;
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(BikeParkingNamur bikeParkingNamur) {
//            if (bikeParkingNamur != null) {
//                parkingVelo = bikeParkingNamur;
//                for (xyz.eeckhout.smartcity.model.villeNamur.bikeParking.Record record : bikeParkingNamur.getRecords()) {
//                    LatLng latLng = new LatLng(record.getFields().getGeoPoint2d().get(0), record.getFields().getGeoPoint2d().get(1));
//                    markers.add(
//                            mMap.addMarker(
//                                    new MarkerOptions().position(latLng)
//                                            .title(record.getFields().getNomStation())
//                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.parking_velo))
//                                            .snippet("Nb places : " + record.getFields().getNbreArceaux()))
//                    );
//                    markers.get(markers.size() - 1).setTag(record);
//                }
//            }
//        }
//    }
//
//    private class LoadBikeRouteNamur extends AsyncTask<Object, Void, BikeRouteNamur> {
//        @Override
//        protected BikeRouteNamur doInBackground(Object... params) {
//            if (params.length > 0) {
//                BikeRouteNamurWS bikeRouteNamurDAO = new BikeRouteNamurWS();
//                BikeRouteNamur bikeRouteNamur = new BikeRouteNamur();
//                try {
//                    VisibleRegion visibleRegion = (VisibleRegion) params[0];
//                    bikeRouteNamur = bikeRouteNamurDAO.getItinerairesFromArea(visibleRegion.latLngBounds.getCenter(), Utils.getDistanceVisibleRegion(visibleRegion));
//                } catch (Exception e) {
//                    Log.i("erreur", e.getMessage());
//                }
//                return bikeRouteNamur;
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(BikeRouteNamur bikeRouteNamur) {
//            if (bikeRouteNamur != null) {
//                itineraireVelo = bikeRouteNamur;
//                for (xyz.eeckhout.smartcity.model.villeNamur.bikeRoute.Record record : bikeRouteNamur.getRecords()) {
//                    PolylineOptions rectOptions = new PolylineOptions()
//                            .clickable(true)
//                            .width(15)
//                            .color(Color.rgb(0, 205, 0))
//                            .startCap(new RoundCap())
//                            .endCap(new RoundCap())
//                            .addAll(record.getFields().getGeoShape().getLatLng());
//
//                    Polyline polyline = mMap.addPolyline(rectOptions);
//                    polyline.setTag(record);
//                }
//            }
//        }
//
//        @Override
//        protected void onCancelled() {
//            super.onCancelled();
//        }
//    }
//}
