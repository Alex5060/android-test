package xyz.eeckhout.smartcity.dataAccess;

import android.os.AsyncTask;
import android.util.Log;

import xyz.eeckhout.smartcity.api.ServiceOpinionApi;
import xyz.eeckhout.smartcity.model.ServiceOpinionDTO;

public class PutOpinionAT extends AsyncTask<String, Void, Boolean> {
    private ServiceOpinionDTO opinion;
    private Exception exception;
    private String accessToken;
    private GetOpinionsATResults getOpinionsATResults;

    public PutOpinionAT(ServiceOpinionDTO serviceOpinionDTO, String token, GetOpinionsATResults getOpinionsATResults) {
        this.getOpinionsATResults = getOpinionsATResults;
        this.accessToken = token;
        this.opinion = serviceOpinionDTO;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        try {
            ServiceOpinionApi api = new ServiceOpinionApi();
            api.getApiClient().setAccessToken(accessToken);
            api.editOpinion(opinion.getId(), opinion);
            return true;
        } catch (Exception e) {
            this.exception = e;
            Log.i("erreur", e.getMessage());
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean updateSuccess) {
        if(getOpinionsATResults != null) {
            if (exception != null) {
                getOpinionsATResults.getOpinionsATResultsError(exception);
            } else {
                getOpinionsATResults.getOpinionsATResults(updateSuccess);
            }
        }
    }

    public interface GetOpinionsATResults{
        void getOpinionsATResults(Boolean updateSuccess);
        void getOpinionsATResultsError(Exception exception);
    }
}
