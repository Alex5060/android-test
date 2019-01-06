package xyz.eeckhout.smartcity.dataAccess;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import xyz.eeckhout.smartcity.api.RoutesApi;
import xyz.eeckhout.smartcity.model.DisplayedRoutesDTO;

public class BikeRouteNamurAT extends AsyncTask<Object, Void, List<DisplayedRoutesDTO>> {
    private List<DisplayedRoutesDTO> bikeRouteNamur;
    private Exception exception;

    private GetBikeRouteNamurATResult getBikeRouteNamurATResult;

    public BikeRouteNamurAT(GetBikeRouteNamurATResult getBikeRouteNamurATResult) {
        this.getBikeRouteNamurATResult = getBikeRouteNamurATResult;
    }

    @Override
    protected List<DisplayedRoutesDTO> doInBackground(Object... params) {
        try {
            this.bikeRouteNamur = new RoutesApi().get();
        } catch (Exception e) {
            exception = e;
            Log.i("erreur", e.getMessage());
        }
        return bikeRouteNamur;
    }

    @Override
    protected void onPostExecute(List<DisplayedRoutesDTO> bikeRouteNamur) {
        if (this.getBikeRouteNamurATResult != null) {
            if (exception != null) {
                getBikeRouteNamurATResult.getBikeRouteAtResultError(exception);
            } else {
                getBikeRouteNamurATResult.getBikeRouteNamur(this.bikeRouteNamur);
            }
        }
    }

    public interface GetBikeRouteNamurATResult {
        void getBikeRouteNamur(List<DisplayedRoutesDTO> bikeRouteNamur);

        void getBikeRouteAtResultError(Exception exception);
    }
}