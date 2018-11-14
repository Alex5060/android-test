//package xyz.eeckhout.smartcity.dataAccess;
//
//import android.os.AsyncTask;
//import android.util.Log;
//
//import com.google.android.gms.maps.model.VisibleRegion;
//
//import xyz.eeckhout.smartcity.controller.Utils;
//import xyz.eeckhout.smartcity.model.villeNamur.bikeRoute.BikeRouteNamur;
//
//private class BikeRouteNamurAT extends AsyncTask<Object, Void, BikeRouteNamur> {
//
//    @Override
//    protected BikeRouteNamur doInBackground(Object... params) {
//        if (params.length > 0) {
//            BikeRouteNamurWS bikeRouteNamurWS = new BikeRouteNamurWS();
//            BikeRouteNamur bikeRouteNamur = new BikeRouteNamur();
//            try {
//                VisibleRegion visibleRegion = (VisibleRegion) params[0];
//                bikeRouteNamur = bikeRouteNamurWS.getItinerairesFromArea(visibleRegion.latLngBounds.getCenter(), Utils.getDistanceVisibleRegion(visibleRegion));
//            } catch (Exception e) {
//                Log.i("erreur", e.getMessage());
//            }
//            return bikeRouteNamur;
//        }
//        return null;
//    }
//
//    @Override
//    protected void onPostExecute(BikeRouteNamur bikeRouteNamur) {
//        if (bikeRouteNamur != null) {
//            itineraireVelo = bikeRouteNamur;
//            addBikesRoutes(itineraireVelo);
//        }
//    }
//
//    @Override
//    protected void onCancelled() {
//        super.onCancelled();
//    }
//}