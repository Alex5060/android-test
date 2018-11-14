package xyz.eeckhout.smartcity.dataAccess;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

import xyz.eeckhout.smartcity.model.jcdecaux.JCDecauxStation;

public class JCDecauxAT extends AsyncTask<String, Void, ArrayList<JCDecauxStation>> {
    private ArrayList<JCDecauxStation> stations;
    private Exception exception;

    private GetJCDecauxATResult getJCDecauxATResult;

    public JCDecauxAT(GetJCDecauxATResult getJCDecauxATResult) {
        this.getJCDecauxATResult = getJCDecauxATResult;
    }

    @Override
    protected ArrayList<JCDecauxStation> doInBackground(String... params) {
        ArrayList<JCDecauxStation> stations = new ArrayList<>();
        try {
            this.stations = JCDecauxWS.getAllJCDecaux();
        } catch (Exception e) {
            this.exception = e;
            Log.i("erreur", e.getMessage());
        }
        return stations;
    }

    @Override
    protected void onPostExecute(ArrayList<JCDecauxStation> bikes) {
        if(getJCDecauxATResult != null) {
            if (exception != null) {
                getJCDecauxATResult.getStationsATResultError(exception);
            } else {
                getJCDecauxATResult.getStations(stations);
            }
        }
    }

    public interface GetJCDecauxATResult{
        void getStations(ArrayList<JCDecauxStation> stations);
        void getStationsATResultError(Exception exception);
    }
}
