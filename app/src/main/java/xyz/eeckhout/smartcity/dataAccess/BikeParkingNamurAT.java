package xyz.eeckhout.smartcity.dataAccess;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import xyz.eeckhout.smartcity.api.ServicesApi;
import xyz.eeckhout.smartcity.model.ServiceGetDTO;

public class BikeParkingNamurAT extends AsyncTask<Object, Void, List<ServiceGetDTO>> {
    private List<ServiceGetDTO> bikeParkingNamur;
    private Exception exception;

    private GetBikeParkingNamurATResult getBikeParkingNamurATResult;

    public BikeParkingNamurAT(GetBikeParkingNamurATResult getBikeParkingNamurATResult) {
        this.getBikeParkingNamurATResult = getBikeParkingNamurATResult;
    }

    @Override
    protected List<ServiceGetDTO> doInBackground(Object... params) {
            try {
                this.bikeParkingNamur = new ServicesApi().getServicesByCategory(3,0,200);
            } catch (Exception e) {
                this.exception = e;
                Log.i("erreur", e.getMessage());
            }
            return bikeParkingNamur;
    }

    @Override
    protected void onPostExecute(List<ServiceGetDTO> bikeParkingNamur) {
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
        void getBikeParkingNamur(List<ServiceGetDTO> bikeParkingNamur);
        void getBikeParkingNamurATResultError(Exception exception);
    }
}