package xyz.eeckhout.smartcity.dataAccess;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.VisibleRegion;

import java.util.ArrayList;

import xyz.eeckhout.smartcity.controller.Utils;
import xyz.eeckhout.smartcity.model.jcdecaux.JCDecauxStation;
import xyz.eeckhout.smartcity.model.villeNamur.bikeParking.BikeParkingNamur;

public class BikeParkingNamurAT extends AsyncTask<Object, Void, BikeParkingNamur> {
    private BikeParkingNamur bikeParkingNamur;
    private Exception exception;

    private GetBikeParkingNamurATResult getBikeParkingNamurATResult;

    public BikeParkingNamurAT(GetBikeParkingNamurATResult getBikeParkingNamurATResult) {
        this.getBikeParkingNamurATResult = getBikeParkingNamurATResult;
    }

    @Override
    protected BikeParkingNamur doInBackground(Object... params) {
            try {
                this.bikeParkingNamur = BikeParkingNamurWS.getAllBikeParkingNamur();
            } catch (Exception e) {
                this.exception = e;
                Log.i("erreur", e.getMessage());
            }
            return bikeParkingNamur;
    }

    @Override
    protected void onPostExecute(BikeParkingNamur bikeParkingNamur) {
        if(getBikeParkingNamurATResult != null){
            if(exception != null){
                getBikeParkingNamurATResult.getBikeParkingNamur(this.bikeParkingNamur);
            }
            else{
                getBikeParkingNamurATResult.getBikeParkingNamurATResultError(exception);
            }
        }
    }

    public interface GetBikeParkingNamurATResult{
        void getBikeParkingNamur(BikeParkingNamur bikeParkingNamur);
        void getBikeParkingNamurATResultError(Exception exception);
    }
}