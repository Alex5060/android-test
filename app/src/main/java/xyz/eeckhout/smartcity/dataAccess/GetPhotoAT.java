package xyz.eeckhout.smartcity.dataAccess;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import xyz.eeckhout.smartcity.api.PhotoApi;
import xyz.eeckhout.smartcity.model.PhotoDTO;

public class GetPhotoAT extends AsyncTask<String, Void, List<PhotoDTO>> {
    private List<PhotoDTO> photos;
    private Exception exception;
    private int id;
    private int pageSize;
    private int pageIndex;
    private String accessToken;
    private GetPhotosATResult getPhotosATResult;

    public GetPhotoAT(int id, int pageIndex, int pageSize, String accessToken, GetPhotosATResult getPhotosATResult) {
        this.getPhotosATResult = getPhotosATResult;
        this.id = id;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.accessToken = accessToken;
    }

    @Override
    protected List<PhotoDTO> doInBackground(String... params) {
        try {
            PhotoApi api = new PhotoApi();
            api.getApiClient().setAccessToken(accessToken);
            this.photos = api.getByService(id, pageIndex, pageSize, true);
        } catch (Exception e) {
            this.exception = e;
            Log.i("erreur", e.getMessage());
        }
        return photos;
    }

    @Override
    protected void onPostExecute(List<PhotoDTO> photos) {
        if(getPhotosATResult != null) {
            if (exception != null) {
                getPhotosATResult.getPhotosATResultError(exception);
            } else {
                getPhotosATResult.getPhotosATResult(photos);
            }
        }
    }

    public interface GetPhotosATResult{
        void getPhotosATResult(List<PhotoDTO> service);
        void getPhotosATResultError(Exception exception);
    }
}
