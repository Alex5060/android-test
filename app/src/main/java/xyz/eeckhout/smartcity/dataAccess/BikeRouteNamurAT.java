package xyz.eeckhout.smartcity.dataAccess;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.VisibleRegion;

import java.util.ArrayList;

import xyz.eeckhout.smartcity.controller.Utils;
import xyz.eeckhout.smartcity.model.jcdecaux.JCDecauxStation;
import xyz.eeckhout.smartcity.model.villeNamur.bikeParking.BikeParkingNamur;
import xyz.eeckhout.smartcity.model.villeNamur.bikeRoute.BikeRouteNamur;

public class BikeRouteNamurAT extends AsyncTask<Object, Void, BikeRouteNamur> {
    private BikeRouteNamur bikeRouteNamur;
    private Exception exception;

    private GetBikeRouteNamurATResult getBikeRouteNamurATResult;

    public BikeRouteNamurAT(GetBikeRouteNamurATResult getBikeRouteNamurATResult) {
        this.getBikeRouteNamurATResult = getBikeRouteNamurATResult;
    }

    @Override
    protected BikeRouteNamur doInBackground(Object... params) {
            try {
                this.bikeRouteNamur = BikeRouteNamurWS.getAllBikeRouteNamur();
            } catch (Exception e) {
                exception = e;
                Log.i("erreur", e.getMessage());
            }
            return bikeRouteNamur;
    }

    @Override
    protected void onPostExecute(BikeRouteNamur bikeRouteNamur) {
        if (this.getBikeRouteNamurATResult != null) {
            if (exception != null) {
                getBikeRouteNamurATResult.getBikeRouteAtResultError(exception);
            } else {
                getBikeRouteNamurATResult.getBikeRouteNamur(this.bikeRouteNamur);
            }
        }
    }

    public interface GetBikeRouteNamurATResult{
        void getBikeRouteNamur(BikeRouteNamur bikeRouteNamur);
        void getBikeRouteAtResultError(Exception exception);
    }
}