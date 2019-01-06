package xyz.eeckhout.smartcity.dataAccess;

import android.os.AsyncTask;
import android.util.Log;

import xyz.eeckhout.smartcity.api.ServicesApi;
import xyz.eeckhout.smartcity.model.ServiceDTO;

public class GetServiceAT extends AsyncTask<String, Void, ServiceDTO> {
    private ServiceDTO service;
    private Exception exception;
    private int id;
    private GetServiceATResult getServiceATResult;

    public GetServiceAT(int id, GetServiceATResult getJCDecauxATResult) {
        this.getServiceATResult = getJCDecauxATResult;
        this.id = id;
    }

    @Override
    protected ServiceDTO doInBackground(String... params) {
        try {
            this.service = new ServicesApi().getServiceById(id);
        } catch (Exception e) {
            this.exception = e;
            Log.i("erreur", e.getMessage());
        }
        return service;
    }

    @Override
    protected void onPostExecute(ServiceDTO bikes) {
        if(getServiceATResult != null) {
            if (exception != null) {
                getServiceATResult.getserviceATResultError(exception);
            } else {
                getServiceATResult.getserviceATResult(service);
            }
        }
    }

    public interface GetServiceATResult{
        void getserviceATResult(ServiceDTO service);
        void getserviceATResultError(Exception exception);
    }
}
