package xyz.eeckhout.smartcity.viewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import xyz.eeckhout.smartcity.dataAccess.GetOpinionAt;
import xyz.eeckhout.smartcity.dataAccess.GetPhotoAT;
import xyz.eeckhout.smartcity.dataAccess.GetServiceAT;
import xyz.eeckhout.smartcity.model.PhotoDTO;
import xyz.eeckhout.smartcity.model.ServiceDTO;
import xyz.eeckhout.smartcity.model.ServiceOpinionDTO;

public class ServiceViewModel extends ViewModel implements GetServiceAT.GetServiceATResult, GetPhotoAT.GetPhotosATResult, GetOpinionAt.GetOpinionsATResults{
    private MutableLiveData<ServiceDTO> service;
    private MutableLiveData<List<PhotoDTO>> photos;
    private MutableLiveData<List<ServiceOpinionDTO>> opinions;
    private GetServiceAT getServiceAT;
    private GetPhotoAT photosAT;
    private GetOpinionAt getOpinionAt;
    public MutableLiveData<ServiceDTO> getService(int category) {
        if(service == null){
            service = new MutableLiveData<>();
        }
        getServiceAT = new GetServiceAT(category, this);
        getServiceAT.execute();
        return service;
    }

    public MutableLiveData<List<PhotoDTO>> getPhotos(int serviceId, int pageIndex, int pageSize, String accessToken) {
        if(photos == null){
            photos = new MutableLiveData<>();
            photos.setValue(new ArrayList<PhotoDTO>());
        }
        photosAT = new GetPhotoAT(serviceId, pageIndex, pageSize, accessToken, this);
        photosAT.execute();
        return photos;
    }


    public MutableLiveData<List<ServiceOpinionDTO>> getOpinions(int serviceId, int pageIndex, int pageSize, String accessToken) {
        if(opinions == null){
            opinions = new MutableLiveData<>();
            opinions.setValue(new ArrayList<ServiceOpinionDTO>());
        }
        getOpinionAt = new GetOpinionAt(serviceId, pageIndex, pageSize, accessToken, this);
        getOpinionAt.execute();
        return opinions;
    }

    public MutableLiveData<ServiceDTO> getService() {
        return service;
    }

    @Override
    public void getserviceATResult(ServiceDTO service) {
        this.service.setValue(service);
    }

    @Override
    public void getserviceATResultError(Exception exception) {
        Log.i("error", exception.getMessage());
    }

    @Override
    public void getPhotosATResult(List<PhotoDTO> photos) {
        this.photos.postValue(photos);
    }

    @Override
    public void getPhotosATResultError(Exception exception) {
        Log.i("error", exception.getMessage());
    }

    @Override
    public void getOpinionsATResults(List<ServiceOpinionDTO> opinions) {
        this.opinions.postValue(opinions);
    }

    @Override
    public void getOpinionsATResultsError(Exception exception) {
        Log.i("error", exception.getMessage());
    }
}
