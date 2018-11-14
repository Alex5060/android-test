package xyz.eeckhout.smartcity.dataAccess;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.VisibleRegion;

import java.util.ArrayList;

import xyz.eeckhout.smartcity.controller.MapsFragment;
import xyz.eeckhout.smartcity.controller.Utils;
import xyz.eeckhout.smartcity.model.jcdecaux.JCDecauxStation;
import xyz.eeckhout.smartcity.model.villeNamur.carParking.CarParkingNamur;

public class CarParkingNamurAT extends AsyncTask<Object, Void, CarParkingNamur> {
    private CarParkingNamur carParkingNamur;
    private Exception exception;

    private GetCarParkingNamurATResult getCarParkingNamurATResult;

    public CarParkingNamurAT(GetCarParkingNamurATResult getCarParkingNamurATResult) {
        this.getCarParkingNamurATResult = getCarParkingNamurATResult;
    }

    @Override
    protected CarParkingNamur doInBackground(Object... params) {
            try {
                carParkingNamur = CarParkingNamurWS.getAllCarParkingNamur();
            } catch (Exception e) {
                Log.i("erreur", e.getMessage());
            }
            return carParkingNamur;
    }

    @Override
    protected void onPostExecute(CarParkingNamur carParkingNamur) {
        if(getCarParkingNamurATResult != null) {
            if (this.exception != null) {
                getCarParkingNamurATResult.getCarParkingNamurATResultError(exception);
            } else {
                getCarParkingNamurATResult.getAllCarParkingNamur(this.carParkingNamur);
            }
        }
    }

    public interface GetCarParkingNamurATResult{
        void getAllCarParkingNamur(CarParkingNamur carParkingNamur);
        void getCarParkingNamurATResultError(Exception exception);
    }
}


