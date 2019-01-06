package xyz.eeckhout.smartcity.dataAccess;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import xyz.eeckhout.smartcity.api.ServiceOpinionApi;
import xyz.eeckhout.smartcity.model.ServiceOpinionDTO;

public class GetOpinionAt extends AsyncTask<String, Void, List<ServiceOpinionDTO>> {
    private List<ServiceOpinionDTO> photos;
    private Exception exception;
    private int id;
    private int pageSize;
    private int pageIndex;
    private String accessToken;
    private GetOpinionsATResults getOpinionsATResults;

    public GetOpinionAt(int id, int pageIndex, int pageSize, String accessToken, GetOpinionsATResults getOpinionsATResults) {
        this.getOpinionsATResults = getOpinionsATResults;
        this.id = id;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.accessToken = accessToken;
    }

    @Override
    protected List<ServiceOpinionDTO> doInBackground(String... params) {
        try {
            ServiceOpinionApi api = new ServiceOpinionApi();
            api.getApiClient().setAccessToken(accessToken);
            this.photos = api.getByService(id, pageIndex, pageSize, null, true);
        } catch (Exception e) {
            this.exception = e;
            Log.i("erreur", e.getMessage());
        }
        return photos;
    }

    @Override
    protected void onPostExecute(List<ServiceOpinionDTO> opinions) {
        if(getOpinionsATResults != null) {
            if (exception != null) {
                getOpinionsATResults.getOpinionsATResultsError(exception);
            } else {
                getOpinionsATResults.getOpinionsATResults(opinions);
            }
        }
    }

    public interface GetOpinionsATResults{
        void getOpinionsATResults(List<ServiceOpinionDTO> opnions);
        void getOpinionsATResultsError(Exception exception);
    }
}
