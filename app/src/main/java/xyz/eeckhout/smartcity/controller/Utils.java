package xyz.eeckhout.smartcity.controller;

import android.content.Context;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.google.android.gms.maps.model.VisibleRegion;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean isDataConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    public static float getDistanceVisibleRegion(VisibleRegion visibleRegion) {
        Log.i("samy", "Lat : " + visibleRegion.latLngBounds.getCenter().latitude + ", " + visibleRegion.latLngBounds.getCenter().longitude);


        Location center = new Location("");
        center.setLatitude(visibleRegion.latLngBounds.getCenter().latitude);
        center.setLongitude(visibleRegion.latLngBounds.getCenter().longitude);

//        Log.i("samy", "Lat : "+ bounds.getCenter().latitude);
//        Log.i("samy", "Long : "+ bounds.getCenter().longitude);

        Location border = new Location("");
        border.setLatitude(visibleRegion.latLngBounds.getCenter().latitude);
        border.setLongitude(visibleRegion.nearLeft.longitude);
//
//        Log.i("samy", "Lat : "+ bounds.);
//        Log.i("samy", "Long : "+bounds.northeast.longitude);

        return center.distanceTo(border);
//        LatLng farRight = visibleRegion.farRight;
//        LatLng farLeft = visibleRegion.farLeft;
//        LatLng nearRight = visibleRegion.nearRight;
//        LatLng nearLeft = visibleRegion.nearLeft;
//
//        float[] distanceWidth = new float[2];
//        Location.distanceBetween(
//                (farRight.latitude + nearRight.latitude) / 2,
//                (farRight.longitude + nearRight.longitude) / 2,
//                (farLeft.latitude + nearLeft.latitude) / 2,
//                (farLeft.longitude + nearLeft.longitude) / 2,
//                distanceWidth
//        );
//
//
//        float[] distanceHeight = new float[2];
//        Location.distanceBetween(
//                (farRight.latitude + nearRight.latitude) / 2,
//                (farRight.longitude + nearRight.longitude) / 2,
//                (farLeft.latitude + nearLeft.latitude) / 2,
//                (farLeft.longitude + nearLeft.longitude) / 2,
//                distanceHeight
//        );
//
//        if (distanceWidth[0] > distanceHeight[0]) {
//            return distanceWidth[0];
//        } else {
//            return distanceHeight[0];
//        }
    }
}
