package xyz.eeckhout.smartcity.dataAccess;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import xyz.eeckhout.smartcity.api.ServicesApi;
import xyz.eeckhout.smartcity.model.ServiceDTO;
import xyz.eeckhout.smartcity.model.jcdecaux.JCDecauxStation;

public class JCDecauxAT extends AsyncTask<String, Void, List<ServiceDTO>> {
    private List<ServiceDTO> stations;
    private Exception exception;

    private GetJCDecauxATResult getJCDecauxATResult;

    public JCDecauxAT(GetJCDecauxATResult getJCDecauxATResult) {
        this.getJCDecauxATResult = getJCDecauxATResult;
    }

    @Override
    protected List<ServiceDTO> doInBackground(String... params) {
        try {
            this.stations = new ServicesApi().getServicesByCategory(1, 0, 2000);
        } catch (Exception e) {
            this.exception = e;
            Log.i("erreur", e.getMessage());
        }
        return stations;
    }

    @Override
    protected void onPostExecute(List<ServiceDTO> bikes) {
        if(getJCDecauxATResult != null) {
            if (exception != null) {
                getJCDecauxATResult.getStationsATResultError(exception);
            } else {
                getJCDecauxATResult.getStationsATResult(stations);
            }
        }
    }

    public interface GetJCDecauxATResult{
        void getStationsATResult(List<ServiceDTO> stations);
        void getStationsATResultError(Exception exception);
    }
}
