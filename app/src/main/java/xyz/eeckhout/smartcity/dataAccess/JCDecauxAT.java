package xyz.eeckhout.smartcity.dataAccess;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import xyz.eeckhout.smartcity.api.ServicesApi;
import xyz.eeckhout.smartcity.model.ServiceGetDTO;

public class JCDecauxAT extends AsyncTask<String, Void, List<ServiceGetDTO>> {
    private List<ServiceGetDTO> stations;
    private Exception exception;

    private GetJCDecauxATResult getJCDecauxATResult;

    public JCDecauxAT(GetJCDecauxATResult getJCDecauxATResult) {
        this.getJCDecauxATResult = getJCDecauxATResult;
    }

    @Override
    protected List<ServiceGetDTO> doInBackground(String... params) {
        try {
            this.stations = new ServicesApi().getServicesByCategory(1, 0, 2000);
        } catch (Exception e) {
            this.exception = e;
            Log.i("erreur", e.getMessage());
        }
        return stations;
    }

    @Override
    protected void onPostExecute(List<ServiceGetDTO> bikes) {
        if(getJCDecauxATResult != null) {
            if (exception != null) {
                getJCDecauxATResult.getStationsATResultError(exception);
            } else {
                getJCDecauxATResult.getStationsATResult(stations);
            }
        }
    }

    public interface GetJCDecauxATResult{
        void getStationsATResult(List<ServiceGetDTO> stations);
        void getStationsATResultError(Exception exception);
    }
}
