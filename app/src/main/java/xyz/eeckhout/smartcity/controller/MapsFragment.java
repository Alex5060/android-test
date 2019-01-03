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
import com.google.maps.android.PolyUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import xyz.eeckhout.smartcity.R;
import xyz.eeckhout.smartcity.model.*;
import xyz.eeckhout.smartcity.model.jcdecaux.JCDecauxStation;


public class MapsFragment extends Fragment implements OnMapReadyCallback {
    private View rootView;
    private GoogleMap map;
    private MapView mMapView;
    private static final int JCDECAUX_MIDDLE_LIMIT = 5;
    private static final int JCDECAUX_CRITICAL_LIMIT = 2;
    private static final long MIN_TIME = 400;
    private static final float MIN_DISTANCE = 100;
    private MapViewModel model;
    private BottomSheetViewModel bottomSheetViewModel;
    LocationManager locationManager;

    public static xyz.eeckhout.smartcity.controller.MapsFragment newInstance() {
        return new xyz.eeckhout.smartcity.controller.MapsFragment();
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        model = ViewModelProviders.of(getActivity()).get(MapViewModel.class);
        bottomSheetViewModel = ViewModelProviders.of(getActivity()).get(BottomSheetViewModel.class);
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
//            FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.button_loading_libiavelo);
//            fab.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                }
//            });
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

            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                Location location = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15));
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
                    bottomSheetViewModel.setMarker(marker);
                    // Check if a click count was set, then display the click count.
                    Toast.makeText(getContext(), marker.getTitle() + " has been clicked :" + marker.getTag() + ".", Toast.LENGTH_SHORT).show();
                    //TextView preview = getActivity().findViewById(R.id.preview);
                    //preview.setText(marker.getTitle());
                    //Toast.makeText(getContext(), preview.getText() + " has been clicked :" + marker.getTag() + ".", Toast.LENGTH_LONG).show();
                    ((MainActivity) getActivity()).showBottomSheetDialogFragment();

                    // Return false to indicate that we have not consumed the event and that we wish
                    // for the default behavior to occur (which is for the camera to move such that the
                    // marker is centered and for the marker's info window to open, if it has one).
                    return false;
                }
            });
            map.setOnPolylineClickListener(new GoogleMap.OnPolylineClickListener() {
                @Override
                public void onPolylineClick(Polyline polyline) {
                    Toast.makeText(getContext(), ((xyz.eeckhout.smartcity.model.villeNamur.bikeRoute.Record) polyline.getTag()).getFields().getNom(), Toast.LENGTH_LONG).show();
                }
            });

        }
    }

    private void addAllData(){
        map.clear();
        if (PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("isJCDecauxLoadingEnable", true)) {
            final Observer<List<ServiceDTO>> jcDecauxStationObserver = new Observer<List<ServiceDTO>>() {
                @Override
                public void onChanged(@Nullable final List<ServiceDTO> jcDecauxStation) {
                    // Update the UI, in this case, a TextView.
                    addAllLibiaVeloMarkers(jcDecauxStation);
                }
            };

            // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
            model.getJcDecauxBikes().observe(this, jcDecauxStationObserver);
        }
        if (PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("isCarParkingNamurLoadingEnable", true)) {
            final Observer<List<ServiceDTO>> carParkingNamurObserver = new Observer<List<ServiceDTO>>() {
                @Override
                public void onChanged(@Nullable final List<ServiceDTO> carParkingNamur) {
                    // Update the UI, in this case, a TextView.
                    addCarParkings(carParkingNamur);
                }
            };

            // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
            model.getCarParkingNamur().observe(this, carParkingNamurObserver);
        }
        if (PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("isBikeParkingNamurLoadingEnable", true)) {
            final Observer<List<ServiceDTO>> bikeParkingNamurObserver = new Observer<List<ServiceDTO>>() {
                @Override
                public void onChanged(@Nullable final List<ServiceDTO> bikeParkingNamur) {
                    // Update the UI, in this case, a TextView.
                    addBikeParkings(bikeParkingNamur);
                }
            };

            // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
            model.getBikeParking().observe(this, bikeParkingNamurObserver);
        }
        if (PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("isBikeRouteLoadingEnable", true)) {
            final Observer<List<DisplayedRoutesDTO>> bikeRouteNamurObserver = new Observer<List<DisplayedRoutesDTO>>() {
                @Override
                public void onChanged(@Nullable final List<DisplayedRoutesDTO> bikeRouteNamur) {
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

    private void addAllLibiaVeloMarkers(List<ServiceDTO> bikes){
        try{
            Log.i("erreur", "nb jcdecaux "+bikes.size());
            for (ServiceDTO bike : bikes) {
                //JSONObject object = new JSONObject(bike.getFacultativeValue());
                int availableBikes = 5;
                LatLng latLng = new LatLng(bike.getLatitude(), bike.getLongitude());
                Marker marker = map.addMarker(
                        new MarkerOptions().position(latLng)
                                .title(bike.getServiceName())
                                .snippet("Disponibilités : " + availableBikes));
                if (availableBikes> JCDECAUX_MIDDLE_LIMIT) {
                    marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.libiavelo2_vert));
                } else {
                    if (availableBikes > JCDECAUX_CRITICAL_LIMIT) {
                        marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.libiavelo2_orange));
                    } else {
                        marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.libiavelo2_rouge));
                    }
                }
                marker.setTag(bike);
            }
        }
        catch(Exception e){
            Log.i("erreur", e.getMessage());
        }
    }

    private VisibleRegion getVisibleRegion() {
        return map.getProjection().getVisibleRegion();
    }

    private void addCarParkings(List<ServiceDTO> carParkingNamur){
        Log.i("erreur", "nb carPark "+ carParkingNamur.size());
        try{
            for (ServiceDTO service : carParkingNamur) {
                JSONObject object = new JSONObject(service.getFacultativeValue());
                LatLng latLng = new LatLng(service.getLatitude(), service.getLongitude());
                map.addMarker(
                        new MarkerOptions().position(latLng)
                                .title(service.getServiceName())
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.parking_voiture))
                                .snippet("Nb places : " + object.getInt("Places"))).setTag(service);
            }
        }
        catch(Exception e){
            Log.i("error", e.getMessage());
        }
    }

    private void addBikeParkings(List<ServiceDTO> bikeParkingNamur){
        try{
            Log.i("erreur", "nb BikeParking : "+bikeParkingNamur.size());
            for (ServiceDTO service : bikeParkingNamur) {
                JSONObject object = new JSONObject(service.getFacultativeValue());
                LatLng latLng = new LatLng(service.getLatitude(), service.getLongitude());
                map.addMarker(
                        new MarkerOptions().position(latLng)
                                .title(service.getServiceName())
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.parking_velo))
                                .snippet("Nb places : " + object.getString("Places"))
                ).setTag(service);
            }
        }
        catch(Exception e){
            Log.i("error", e.getMessage());
        }
    }

    private void addBikesRoutes(List<DisplayedRoutesDTO> bikeRouteNamur){
        Log.i("erreur", "nb routes : "+ bikeRouteNamur.size());
        for (DisplayedRoutesDTO route : bikeRouteNamur) {
            PolylineOptions rectOptions = new PolylineOptions()
                    .clickable(true)
                    .width(15)
                    .color(Color.rgb(0, 205, 0))
                    .startCap(new RoundCap())
                    .endCap(new RoundCap())
                    .addAll(PolyUtil.decode(route.getEncodedCoordinates()));

            Polyline polyline = map.addPolyline(rectOptions);
            polyline.setTag(route);
        }
    }
}