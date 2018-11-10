package xyz.eeckhout.smartcity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
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

public class MapsFragment extends Fragment {

    private GoogleMap mMap;
    private SupportMapFragment mSupportMapFragment;
    private ArrayList<Marker> markers = new ArrayList<>();
    private ParkingAuto parkingAuto;
    private ParkingVeloVille parkingVelo;
    private ItineraireVeloVille itineraireVelo;

    public MapsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        mSupportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapDefault);
        if (mSupportMapFragment == null) {
            FragmentManager fragmentManager = getChildFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            mSupportMapFragment = SupportMapFragment.newInstance();
            fragmentTransaction.replace(R.id.mapDefault, mSupportMapFragment).commit();
        }

        if (mSupportMapFragment != null) {
            mSupportMapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    mMap = googleMap;
                    enableMyLocationIfPermitted();
                    mMap.getUiSettings().setZoomControlsEnabled(true);

                    /* Loading JCDecauxVelos */
                    new LoadJCDecaux().execute();

                    /* Loading Parking Voiture */
                    new LoadParkingAuto().execute();

                    /* Loading Parking Velo */
                    new LoadParkingVeloVille().execute();

                    /* Loading ItineraireVelo */
                    new LoadItineraireVeloVille().execute();

                    /* Move camera */
                    LatLng namur = new LatLng(50.469313, 4.862612);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(namur, 15));

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_maps, container, false);
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
                    mMap.setMinZoomPreference(15);
                    return false;
                }
            };

    private GoogleMap.OnMyLocationClickListener onMyLocationClickListener =
            new GoogleMap.OnMyLocationClickListener() {
                @Override
                public void onMyLocationClick(@NonNull Location location) {

                    mMap.setMinZoomPreference(12);

                    CircleOptions circleOptions = new CircleOptions();
                    circleOptions.center(new LatLng(location.getLatitude(),
                            location.getLongitude()));

                    circleOptions.radius(200);
                    circleOptions.fillColor(Color.RED);
                    circleOptions.strokeWidth(6);

                    mMap.addCircle(circleOptions);
                }
            };

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

    private class LoadJCDecaux extends AsyncTask<String, Void, ArrayList<JCDecauxVelos>> {
        @Override
        protected ArrayList<JCDecauxVelos> doInBackground(String... params) {
            JCDecauxDAO jcDecauxDAO = new JCDecauxDAO();
            ArrayList<JCDecauxVelos> velos = new ArrayList<>();
            try {
                velos = jcDecauxDAO.getAllJCDecaux();
            } catch (Exception e) {
                Log.i("erreur", e.getMessage());
            }
            return velos;
        }

        @Override
        protected void onPostExecute(ArrayList<JCDecauxVelos> velos) {
            for (JCDecauxVelos velo : velos) {
                LatLng latLng = new LatLng(velo.getPosition().getLat(), velo.getPosition().getLng());
                //BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                markers.add(
                        mMap.addMarker(
                                new MarkerOptions().position(latLng)
                                        .title(velo.getName())
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.libiavelo2))
                                        .snippet("Disponibilités : " + velo.getAvailableBikes()))
                );
                markers.get(markers.size() - 1).setTag(velo);
            }
        }
    }

    private class LoadParkingAuto extends AsyncTask<String, Void, ParkingAuto> {
        @Override
        protected ParkingAuto doInBackground(String... params) {
            ParkingAutoDAO parkingAutoDAO = new ParkingAutoDAO();
            ParkingAuto examples = new ParkingAuto();
            try {
                examples = parkingAutoDAO.getAllJCDecaux();
            } catch (Exception e) {
                Log.i("erreur", e.getMessage());
            }
            return examples;
        }

        @Override
        protected void onPostExecute(ParkingAuto parkingAuto) {
            parkingAuto = parkingAuto;
            for (Record record : parkingAuto.getRecords()) {
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

    private class LoadParkingVeloVille extends AsyncTask<String, Void, ParkingVeloVille> {
        @Override
        protected ParkingVeloVille doInBackground(String... params) {
            ParkingVeloVilleDAO parkingVeloVilleDAO = new ParkingVeloVilleDAO();
            ParkingVeloVille parkingVeloVille = new ParkingVeloVille();
            try {
                parkingVeloVille = parkingVeloVilleDAO.getAllParkingVeloVille();
            } catch (Exception e) {
                Log.i("erreur", e.getMessage());
            }
            return parkingVeloVille;
        }

        @Override
        protected void onPostExecute(ParkingVeloVille parkingVeloVille) {
            parkingVelo = parkingVeloVille;
            for (xyz.eeckhout.smartcity.Model.VilleNamur.ParkingVelo.Record record : parkingVeloVille.getRecords()) {
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

    private class LoadItineraireVeloVille extends AsyncTask<String, Void, ItineraireVeloVille> {
        @Override
        protected ItineraireVeloVille doInBackground(String... params) {
            ItineraireVeloVilleDAO itineraireVeloVilleDAO = new ItineraireVeloVilleDAO();
            ItineraireVeloVille itineraireVeloVille = new ItineraireVeloVille();
            try {
                itineraireVeloVille = itineraireVeloVilleDAO.getAllItineraireVeloVille();
            } catch (Exception e) {
                Log.i("erreur", e.getMessage());
            }
            return itineraireVeloVille;
        }

        @Override
        protected void onPostExecute(ItineraireVeloVille itineraireVeloVille) {
            itineraireVelo = itineraireVeloVille;
            for (xyz.eeckhout.smartcity.Model.ItineraireVelo.Record record : itineraireVeloVille.getRecords()) {
                PolylineOptions rectOptions = new PolylineOptions()
                        .clickable(true)
                        .width(30)
                        .color(Color.rgb(0, 205, 0))
                        .startCap(new RoundCap())
                        .endCap(new RoundCap())
                        .addAll(record.getFields().getGeoShape().getLatLng());

                Polyline polyline = mMap.addPolyline(rectOptions);
                polyline.setTag(record);
            }
        }
    }
}
