package xyz.eeckhout.smartcity.controller;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
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

import java.util.List;

import xyz.eeckhout.smartcity.R;
import xyz.eeckhout.smartcity.model.DisplayedRoutesDTO;
import xyz.eeckhout.smartcity.model.ServiceDTO;
import xyz.eeckhout.smartcity.model.ServiceGetDTO;
import xyz.eeckhout.smartcity.viewModel.BottomSheetViewModel;
import xyz.eeckhout.smartcity.viewModel.MapViewModel;


public class MapFragment extends Fragment implements OnMapReadyCallback {
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

    public static MapFragment newInstance() {
        return new MapFragment();
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
                    //Toast.makeText(getContext(), marker.getTitle() + " has been clicked :" + marker.getTag() + ".", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getContext(), ((DisplayedRoutesDTO) polyline.getTag()).getName(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void addAllData(){
        map.clear();
        if (PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("isJCDecauxLoadingEnable", true)) {
            final Observer<List<ServiceGetDTO>> jcDecauxStationObserver = new Observer<List<ServiceGetDTO>>() {
                @Override
                public void onChanged(@Nullable final List<ServiceGetDTO> jcDecauxStation) {
                    // Update the UI, in this case, a TextView.
                    addAllLibiaVeloMarkers(jcDecauxStation);
                }
            };

            // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
            model.getJcDecauxBikes().observe(this, jcDecauxStationObserver);
        }
        if (PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("isCarParkingNamurLoadingEnable", true)) {
            final Observer<List<ServiceGetDTO>> carParkingNamurObserver = new Observer<List<ServiceGetDTO>>() {
                @Override
                public void onChanged(@Nullable final List<ServiceGetDTO> carParkingNamur) {
                    // Update the UI, in this case, a TextView.
                    addCarParkings(carParkingNamur);
                }
            };

            // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
            model.getCarParkingNamur().observe(this, carParkingNamurObserver);
        }
        if (PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("isBikeParkingNamurLoadingEnable", true)) {
            final Observer<List<ServiceGetDTO>> bikeParkingNamurObserver = new Observer<List<ServiceGetDTO>>() {
                @Override
                public void onChanged(@Nullable final List<ServiceGetDTO> bikeParkingNamur) {
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

    private void addAllLibiaVeloMarkers(List<ServiceGetDTO> bikes){
        try{
            Log.i("erreur", "nb jcdecaux "+bikes.size());
            for (ServiceGetDTO bike : bikes) {
                JSONObject object = bike.getFacultativeValue() != null && !bike.getFacultativeValue().isEmpty() ? new JSONObject(bike.getFacultativeValue()) : null;
                int availableBikes = object != null ? object.has("AvailableBikes") ? object.getInt("AvailableBikes") : 0 : 0;
                LatLng latLng = new LatLng(bike.getLatitude(), bike.getLongitude());
                Marker marker = map.addMarker(
                        new MarkerOptions().position(latLng)
                                .title(bike.getServiceName()));
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

    private void addCarParkings(List<ServiceGetDTO> carParkingNamur){
        Log.i("erreur", "nb carPark "+ carParkingNamur.size());
        try{
            for (ServiceGetDTO service : carParkingNamur) {
                //JSONObject object = service.getFacultativeValue() != null ? new JSONObject(service.getFacultativeValue()) : null;
                LatLng latLng = new LatLng(service.getLatitude(), service.getLongitude());
                map.addMarker(
                        new MarkerOptions().position(latLng)
                                .title(service.getServiceName())
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.parking_voiture))
                        ).setTag(service);
            }
        }
        catch(Exception e){
            Log.i("error", e.getMessage());
        }
    }

    private void addBikeParkings(List<ServiceGetDTO> bikeParkingNamur){
        try{
            Log.i("erreur", "nb BikeParking : "+bikeParkingNamur.size());
            for (ServiceGetDTO service : bikeParkingNamur) {
                //JSONObject object = new JSONObject(service.getFacultativeValue());
                LatLng latLng = new LatLng(service.getLatitude(), service.getLongitude());
                map.addMarker(
                        new MarkerOptions().position(latLng)
                                .title(service.getServiceName())
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.parking_velo))
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

    public static class ServiceViewModel extends ViewModel {
        private ServiceDTO service;

        public ServiceDTO getService() {
            return service;
        }

        public void setService(ServiceDTO service) {
            this.service = service;
        }
    }

    public static class ServiceFragment extends Fragment {

        private ServiceViewModel mViewModel;

        public static ServiceFragment newInstance() {
            return new ServiceFragment();
        }

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.service_fragment, container, false);
        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            mViewModel = ViewModelProviders.of(this).get(ServiceViewModel.class);
            // TODO: Use the ViewModel
        }

    }

    public static class RegisterViewModel extends ViewModel {
        // TODO: Implement the ViewModel
    }

    public static class RegisterFragment extends Fragment {

        private RegisterViewModel mViewModel;

        public static RegisterFragment newInstance() {
            return new RegisterFragment();
        }

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.register_fragment, container, false);
        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            mViewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);
            // TODO: Use the ViewModel
        }

    }
}