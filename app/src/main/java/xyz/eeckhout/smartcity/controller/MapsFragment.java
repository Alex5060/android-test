package xyz.eeckhout.smartcity.controller;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
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
import com.google.android.gms.maps.model.VisibleRegion;

import java.util.ArrayList;

import xyz.eeckhout.smartcity.R;
import xyz.eeckhout.smartcity.dataAccess.BikeParkingNamurDAO;
import xyz.eeckhout.smartcity.dataAccess.BikeRouteNamurDAO;
import xyz.eeckhout.smartcity.dataAccess.CarParkingNamurDAO;
import xyz.eeckhout.smartcity.dataAccess.JCDecauxDAO;
import xyz.eeckhout.smartcity.model.jcdecaux.JCDecauxBikes;
import xyz.eeckhout.smartcity.model.villeNamur.bikeParking.BikeParkingNamur;
import xyz.eeckhout.smartcity.model.villeNamur.bikeRoute.BikeRouteNamur;
import xyz.eeckhout.smartcity.model.villeNamur.carParking.CarParkingNamur;
import xyz.eeckhout.smartcity.model.villeNamur.carParking.Record;

public class MapsFragment extends Fragment {
    private static final int JCDECAUX_MIDDLE_LIMIT = 5;
    private static final int JCDECAUX_CRITICAL_LIMIT = 2;
    private GoogleMap mMap;
    private SupportMapFragment mSupportMapFragment;
    private LocationManager locationManager;
    private ArrayList<Marker> markers = new ArrayList<>();
    private CarParkingNamur carParkingNamur;
    private BikeParkingNamur parkingVelo;
    private BikeRouteNamur itineraireVelo;
    private LoadBikeParkingNamur loadBikeParkingNamur;
    private LoadBikeRouteNamur loadBikeRouteNamur;
    private LoadCarParkingNamur loadCarParkingNamur;
    private LoadJCDecaux loadJCDecaux;
    private static final long MIN_TIME = 400;
    private static final float MIN_DISTANCE = 100;

    public MapsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        if (Utils.isDataConnectionAvailable(getContext())) {
            mSupportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapDefault);
            if (mSupportMapFragment == null) {
                FragmentManager fragmentManager = getChildFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                mSupportMapFragment = SupportMapFragment.newInstance();
                fragmentTransaction.replace(R.id.mapDefault, mSupportMapFragment).commit();
            }

            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
                        mMap.animateCamera(cameraUpdate);
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

            if (mSupportMapFragment != null) {
                mSupportMapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        mMap = googleMap;
                        enableMyLocationIfPermitted();
                        /* Move camera */
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(50.469313, 4.862612), 15));
                        mMap.getUiSettings().setZoomControlsEnabled(true);

                        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
                            @Override
                            public void onCameraIdle() {
                                VisibleRegion visibleRegion = mMap.getProjection().getVisibleRegion();
                                mMap.clear();
                                if (PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("isJCDecauxLoadingEnable", true)) {
                                    /* Loading JCDecauxBikes */
                                    loadJCDecaux = new LoadJCDecaux();
                                    loadJCDecaux.execute();
                                }
                                if (PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("isCarParkingNamurLoadingEnable", true)) {
                                    /* Loading Parking Voiture */
                                    loadCarParkingNamur = new LoadCarParkingNamur();
                                    loadCarParkingNamur.execute(visibleRegion);
                                }
                                if (PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("isBikeParkingNamurLoadingEnable", true)) {
                                    /* Loading Parking Velo */
                                    loadBikeParkingNamur = new LoadBikeParkingNamur();
                                    loadBikeParkingNamur.execute(visibleRegion);
                                }
                                if (PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("isBikeRouteLoadingEnable", true)) {
                                    /* Loading ItineraireVelo */
                                    loadBikeRouteNamur = new LoadBikeRouteNamur();
                                    loadBikeRouteNamur.execute(visibleRegion);
                                }
                            }
                        });

                        // Set a listener for marker click.
                        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
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
                        mMap.setOnPolylineClickListener(new GoogleMap.OnPolylineClickListener() {
                            @Override
                            public void onPolylineClick(Polyline polyline) {
                                Toast.makeText(getContext(), polyline.getTag().toString(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
            }
        } else {
            Toast.makeText(getContext(), R.string.error_internet_connection, Toast.LENGTH_LONG).show();
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_maps, container, false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (loadJCDecaux != null) {
            loadJCDecaux.cancel(true);
        }
        if (loadBikeParkingNamur != null) {
            loadBikeParkingNamur.cancel(true);
        }
        if (loadBikeRouteNamur != null) {
            loadBikeRouteNamur.cancel(true);
        }
        if (loadCarParkingNamur != null) {
            loadCarParkingNamur.cancel(true);
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
        } else if (mMap != null) {
            mMap.setMyLocationEnabled(true);
        }
    }

    private GoogleMap.OnMyLocationButtonClickListener onMyLocationButtonClickListener =
            new GoogleMap.OnMyLocationButtonClickListener() {
                @Override
                public boolean onMyLocationButtonClick() {
                    return false;
                }
            };

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

    private class LoadJCDecaux extends AsyncTask<String, Void, ArrayList<JCDecauxBikes>> {
        @Override
        protected ArrayList<JCDecauxBikes> doInBackground(String... params) {
            JCDecauxDAO jcDecauxDAO = new JCDecauxDAO();
            ArrayList<JCDecauxBikes> bikes = new ArrayList<>();
            try {
                bikes = jcDecauxDAO.getAllJCDecaux();
            } catch (Exception e) {
                Log.i("erreur", e.getMessage());
            }
            return bikes;
        }

        @Override
        protected void onPostExecute(ArrayList<JCDecauxBikes> bikes) {
            for (JCDecauxBikes bike : bikes) {
                LatLng latLng = new LatLng(bike.getPosition().getLat(), bike.getPosition().getLng());
                markers.add(
                        mMap.addMarker(
                                new MarkerOptions().position(latLng)
                                        .title(bike.getName())
                                        .snippet("Disponibilités : " + bike.getAvailableBikes()))
                );
                Marker marker = markers.get(markers.size() - 1);
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
    }

    private VisibleRegion getVisibleRegion() {
        return mMap.getProjection().getVisibleRegion();
    }

    private class LoadCarParkingNamur extends AsyncTask<Object, Void, CarParkingNamur> {
        @Override
        protected CarParkingNamur doInBackground(Object... params) {
            if (params.length > 0) {
                CarParkingNamurDAO carParkingNamurDAO = new CarParkingNamurDAO();
                CarParkingNamur examples = new CarParkingNamur();
                try {
                    VisibleRegion visibleRegion = (VisibleRegion) params[0];
                    //examples = carParkingNamurDAO.getAllJCDecaux();
                    examples = carParkingNamurDAO.getParkingFromArea(visibleRegion.latLngBounds.getCenter(), Utils.getDistanceVisibleRegion(visibleRegion));
                } catch (Exception e) {
                    Log.i("erreur", e.getMessage());
                }
                return examples;
            }
            return null;
        }

        @Override
        protected void onPostExecute(CarParkingNamur carParkingNamur) {
            if (carParkingNamur != null) {
                MapsFragment.this.carParkingNamur = carParkingNamur;
                for (Record record : carParkingNamur.getRecords()) {
                    LatLng latLng = new LatLng(record.getFields().getGeoPoint2d().get(0), record.getFields().getGeoPoint2d().get(1));
                    markers.add(
                            mMap.addMarker(
                                    new MarkerOptions().position(latLng)
                                            .title(record.getFields().getPlsyDescri())
                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.parking_voiture))
                                            .snippet("Nb places : " + record.getFields().getPlaces()))
                    );
                    markers.get(markers.size() - 1).setTag(record);
                }
            }
        }
    }

    private class LoadBikeParkingNamur extends AsyncTask<Object, Void, BikeParkingNamur> {
        @Override
        protected BikeParkingNamur doInBackground(Object... params) {
            if (params.length > 0) {
                BikeParkingNamurDAO bikeParkingNamurDAO = new BikeParkingNamurDAO();
                BikeParkingNamur bikeParkingNamur = new BikeParkingNamur();
                try {
                    VisibleRegion visibleRegion = (VisibleRegion) params[0];
                    //bikeParkingNamur = bikeParkingNamurDAO.getAllParkingVeloVille();
                    bikeParkingNamur = bikeParkingNamurDAO.getPakingVeloFromArez(visibleRegion.latLngBounds.getCenter(), Utils.getDistanceVisibleRegion(visibleRegion));
                } catch (Exception e) {
                    Log.i("erreur", e.getMessage());
                }
                return bikeParkingNamur;
            }
            return null;
        }

        @Override
        protected void onPostExecute(BikeParkingNamur bikeParkingNamur) {
            if (bikeParkingNamur != null) {
                parkingVelo = bikeParkingNamur;
                for (xyz.eeckhout.smartcity.model.villeNamur.bikeParking.Record record : bikeParkingNamur.getRecords()) {
                    LatLng latLng = new LatLng(record.getFields().getGeoPoint2d().get(0), record.getFields().getGeoPoint2d().get(1));
                    markers.add(
                            mMap.addMarker(
                                    new MarkerOptions().position(latLng)
                                            .title(record.getFields().getNomStation())
                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.parking_velo))
                                            .snippet("Nb places : " + record.getFields().getNbreArceaux()))
                    );
                    markers.get(markers.size() - 1).setTag(record);
                }
            }
        }
    }

    private class LoadBikeRouteNamur extends AsyncTask<Object, Void, BikeRouteNamur> {
        @Override
        protected BikeRouteNamur doInBackground(Object... params) {
            if (params.length > 0) {
                BikeRouteNamurDAO bikeRouteNamurDAO = new BikeRouteNamurDAO();
                BikeRouteNamur bikeRouteNamur = new BikeRouteNamur();
                try {
                    VisibleRegion visibleRegion = (VisibleRegion) params[0];
                    bikeRouteNamur = bikeRouteNamurDAO.getItinerairesFromArea(visibleRegion.latLngBounds.getCenter(), Utils.getDistanceVisibleRegion(visibleRegion));
                } catch (Exception e) {
                    Log.i("erreur", e.getMessage());
                }
                return bikeRouteNamur;
            }
            return null;
        }

        @Override
        protected void onPostExecute(BikeRouteNamur bikeRouteNamur) {
            if (bikeRouteNamur != null) {
                itineraireVelo = bikeRouteNamur;
                for (xyz.eeckhout.smartcity.model.villeNamur.bikeRoute.Record record : bikeRouteNamur.getRecords()) {
                    PolylineOptions rectOptions = new PolylineOptions()
                            .clickable(true)
                            .width(15)
                            .color(Color.rgb(0, 205, 0))
                            .startCap(new RoundCap())
                            .endCap(new RoundCap())
                            .addAll(record.getFields().getGeoShape().getLatLng());

                    Polyline polyline = mMap.addPolyline(rectOptions);
                    polyline.setTag(record);
                }
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }
}
