package xyz.eeckhout.smartcity.dataAccess;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import xyz.eeckhout.smartcity.api.ServicesApi;
import xyz.eeckhout.smartcity.model.ServiceDTO;

public class CarParkingNamurAT extends AsyncTask<Object, Void, List<ServiceDTO>> {
    private List<ServiceDTO> carParkingNamur;
    private Exception exception;
    private int category;

    private GetCarParkingNamurATResult getCarParkingNamurATResult;

    public CarParkingNamurAT(GetCarParkingNamurATResult getCarParkingNamurATResult, int category) {
        this.getCarParkingNamurATResult = getCarParkingNamurATResult;
        this.category = category;
    }

    @Override
    protected List<ServiceDTO> doInBackground(Object... params) {
            try {
                carParkingNamur = new ServicesApi().getServicesByCategory(category,0,200);
            } catch (Exception e) {
                Log.i("erreur", e.getMessage());
            }
            return carParkingNamur;
    }

    @Override
    protected void onPostExecute(List<ServiceDTO> carParkingNamur) {
        if(getCarParkingNamurATResult != null) {
            if (this.exception != null) {
                getCarParkingNamurATResult.getCarParkingNamurATResultError(exception);
            } else {
                getCarParkingNamurATResult.getAllCarParkingNamur(this.carParkingNamur);
            }
        }
    }

    public interface GetCarParkingNamurATResult{
        void getAllCarParkingNamur(List<ServiceDTO> carParkingNamur);
        void getCarParkingNamurATResultError(Exception exception);
    }
}


