package xyz.eeckhout.smartcity.dataAccess;

import android.os.AsyncTask;
import android.util.Log;

import xyz.eeckhout.smartcity.api.ServiceOpinionApi;
import xyz.eeckhout.smartcity.model.ServiceOpinionDTO;

public class DeleteOpinionAT extends AsyncTask<String, Void, Boolean> {
    private ServiceOpinionDTO opinion;
    private Exception exception;
    private String accessToken;
    private DeleteOpinionsATResults getOpinionsATResults;

    public DeleteOpinionAT(ServiceOpinionDTO serviceOpinionDTO, String token, DeleteOpinionsATResults getOpinionsATResults) {
        this.getOpinionsATResults = getOpinionsATResults;
        this.accessToken = token;
        this.opinion = serviceOpinionDTO;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        try {
            ServiceOpinionApi api = new ServiceOpinionApi();
            api.getApiClient().setAccessToken(accessToken);
            api.delete(opinion.getId());
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
                getOpinionsATResults.deleteOpinionsATResultsError(exception);
            } else {
                getOpinionsATResults.deleteOpinionsATResults(updateSuccess);
            }
        }
    }

    public interface DeleteOpinionsATResults{
        void deleteOpinionsATResults(Boolean deleteSuccess);
        void deleteOpinionsATResultsError(Exception exception);
    }
}
