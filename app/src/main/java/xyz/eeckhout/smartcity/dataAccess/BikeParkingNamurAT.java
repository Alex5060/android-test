package xyz.eeckhout.smartcity.dataAccess;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import xyz.eeckhout.smartcity.api.ServicesApi;
import xyz.eeckhout.smartcity.model.ServiceDTO;

public class BikeParkingNamurAT extends AsyncTask<Object, Void, List<ServiceDTO>> {
    private List<ServiceDTO> bikeParkingNamur;
    private Exception exception;

    private GetBikeParkingNamurATResult getBikeParkingNamurATResult;

    public BikeParkingNamurAT(GetBikeParkingNamurATResult getBikeParkingNamurATResult) {
        this.getBikeParkingNamurATResult = getBikeParkingNamurATResult;
    }

    @Override
    protected List<ServiceDTO> doInBackground(Object... params) {
            try {
                this.bikeParkingNamur = new ServicesApi().getServicesByCategory(3,0,200);
            } catch (Exception e) {
                this.exception = e;
                Log.i("erreur", e.getMessage());
            }
            return bikeParkingNamur;
    }

    @Override
    protected void onPostExecute(List<ServiceDTO> bikeParkingNamur) {
        if(getBikeParkingNamurATResult != null){
            if(exception != null){
                getBikeParkingNamurATResult.getBikeParkingNamurATResultError(exception);
            }
            else{
                getBikeParkingNamurATResult.getBikeParkingNamur(this.bikeParkingNamur);
            }
        }
    }

    public interface GetBikeParkingNamurATResult{
        void getBikeParkingNamur(List<ServiceDTO> bikeParkingNamur);
        void getBikeParkingNamurATResultError(Exception exception);
    }
}