package xyz.eeckhout.smartcity.controller;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
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
    LocationManager locationManager;

    public static xyz.eeckhout.smartcity.controller.MapsFragment newInstance() {
        return new xyz.eeckhout.smartcity.controller.MapsFragment();
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        model = ViewModelProviders.of(getActivity()).get(MapViewModel.class);
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

            enableMyLocationIfPermitted();

            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
                        map.animateCamera(cameraUpdate);
                        locationManager.removeUpdates(this);
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                }); //You can also use LocationManager.GPS_PROVIDER and LocationManager.PASSIVE_PROVIDER
            }

            // Create the observer which updates the UI.
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
            final Observer<ArrayList<JCDecauxStation>> jcDecauxStationObserver = new Observer<ArrayList<JCDecauxStation>>() {
                @Override
                public void onChanged(@Nullable final ArrayList<JCDecauxStation> jcDecauxStation) {
                    // Update the UI, in this case, a TextView.
                    addAllLibiaVeloMarkers(jcDecauxStation);
                }
            };

            // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
            model.getJcDecauxBikes().observe(this, jcDecauxStationObserver);
        }
        if (PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("isCarParkingNamurLoadingEnable", true)) {
            final Observer<CarParkingNamur> carParkingNamurObserver = new Observer<CarParkingNamur>() {
                @Override
                public void onChanged(@Nullable final CarParkingNamur carParkingNamur) {
                    // Update the UI, in this case, a TextView.
                    addCarParkings(carParkingNamur);
                }
            };

            // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
            model.getCarParkingNamur().observe(this, carParkingNamurObserver);
        }
        if (PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("isBikeParkingNamurLoadingEnable", true)) {
            final Observer<BikeParkingNamur> bikeParkingNamurObserver = new Observer<BikeParkingNamur>() {
                @Override
                public void onChanged(@Nullable final BikeParkingNamur bikeParkingNamur) {
                    // Update the UI, in this case, a TextView.
                    addBikeParkings(bikeParkingNamur);
                }
            };

            // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
            model.getBikeParking().observe(this, bikeParkingNamurObserver);
        }
        if (PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("isBikeRouteLoadingEnable", true)) {
            final Observer<BikeRouteNamur> bikeRouteNamurObserver = new Observer<BikeRouteNamur>() {
                @Override
                public void onChanged(@Nullable final BikeRouteNamur bikeRouteNamur) {
                    // Update the UI, in this case, a TextView.
                    addBikesRoutes(bikeRouteNamur);
                }
            };

            // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
            model.getItineraireVelo().observe(this, bikeRouteNamurObserver);
        }
    }

    private void enableMyLocationIfPermitted() {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        } else if (map != null) {
            map.setMyLocationEnabled(true);
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