package xyz.eeckhout.smartcity.controller;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import xyz.eeckhout.smartcity.dataAccess.BikeParkingNamurAT;
import xyz.eeckhout.smartcity.dataAccess.BikeRouteNamurAT;
import xyz.eeckhout.smartcity.dataAccess.CarParkingNamurAT;
import xyz.eeckhout.smartcity.dataAccess.JCDecauxAT;
import xyz.eeckhout.smartcity.model.DisplayedRoutesDTO;
import xyz.eeckhout.smartcity.model.ServiceGetDTO;
import xyz.eeckhout.smartcity.model.jcdecaux.JCDecauxStation;
import xyz.eeckhout.smartcity.model.villeNamur.bikeRoute.BikeRouteNamur;

public class MapViewModel extends ViewModel implements JCDecauxAT.GetJCDecauxATResult, BikeParkingNamurAT.GetBikeParkingNamurATResult ,BikeRouteNamurAT.GetBikeRouteNamurATResult, CarParkingNamurAT.GetCarParkingNamurATResult {
    private MutableLiveData<List<ServiceGetDTO>> carParkingNamur;
    private MutableLiveData<List<ServiceGetDTO>> parkingVelo;
    private MutableLiveData<List<DisplayedRoutesDTO>> itineraireVelo;
    private MutableLiveData<List<ServiceGetDTO>> jcDecauxBikes;
    private CarParkingNamurAT carParkingNamurAT;
    private BikeRouteNamurAT bikeRouteNamurAT;
    private BikeParkingNamurAT bikeParkingNamurAT;
    private JCDecauxAT jcDecauxAT;


    public MutableLiveData<List<ServiceGetDTO>> getCarParkingNamur() {
        if(carParkingNamur == null){
            carParkingNamur = new MutableLiveData<>();
            carParkingNamur.setValue(new ArrayList<ServiceGetDTO>());
            carParkingNamurAT = new CarParkingNamurAT(this, 2);
            carParkingNamurAT.execute();
        }
        return carParkingNamur;
    }

    public MutableLiveData<List<ServiceGetDTO>> getBikeParking() {
        if(parkingVelo == null){
            parkingVelo = new MutableLiveData<>();
            parkingVelo.setValue(new ArrayList<ServiceGetDTO>());
            bikeParkingNamurAT = new BikeParkingNamurAT(this);
            bikeParkingNamurAT.execute();
        }
        return parkingVelo;
    }

    public MutableLiveData<List<DisplayedRoutesDTO>> getItineraireVelo() {
        if(itineraireVelo == null){
            itineraireVelo = new MutableLiveData<>();
            itineraireVelo.setValue(new ArrayList<DisplayedRoutesDTO>());
            bikeRouteNamurAT = new BikeRouteNamurAT(this);
            bikeRouteNamurAT.execute();
        }
        return itineraireVelo;
    }

    public MutableLiveData<List<ServiceGetDTO>> getJcDecauxBikes() {
        if(jcDecauxBikes == null){
            jcDecauxBikes = new MutableLiveData<>();
            jcDecauxBikes.setValue(new ArrayList<ServiceGetDTO>());
            jcDecauxAT = new JCDecauxAT(this);
            jcDecauxAT.execute();
        }
        return jcDecauxBikes;
    }


    @Override
    public void getStationsATResult(List<ServiceGetDTO> stations) {
        this.jcDecauxBikes.getValue().clear();
        this.jcDecauxBikes.postValue(stations);
    }

    @Override
    public void getStationsATResultError(Exception exception) {
        Log.i("error", exception.getMessage());
    }

    @Override
    public void getBikeRouteNamur(List<DisplayedRoutesDTO> bikeRouteNamur) {
        this.itineraireVelo.getValue().clear();
        this.itineraireVelo.postValue(bikeRouteNamur);
    }

    @Override
    public void getBikeRouteAtResultError(Exception exception) {
        Log.i("error", exception.getMessage());
    }

    @Override
    public void getAllCarParkingNamur(List<ServiceGetDTO> carParkingNamur) {
        this.carParkingNamur.getValue().clear();
        this.carParkingNamur.postValue(carParkingNamur);
    }

    @Override
    public void getCarParkingNamurATResultError(Exception exception) {
        Log.i("error", exception.getMessage());
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if(jcDecauxAT != null) jcDecauxAT.cancel(true);
        if(bikeParkingNamurAT != null) bikeParkingNamurAT.cancel(true);
        if(bikeRouteNamurAT != null)bikeRouteNamurAT.cancel(true);
        if(carParkingNamurAT != null)carParkingNamurAT.cancel(true);
    }

    @Override
    public void getBikeParkingNamur(List<ServiceGetDTO> bikeParkingNamur) {
        this.parkingVelo.getValue().clear();
        this.parkingVelo.postValue(bikeParkingNamur);
    }

    @Override
    public void getBikeParkingNamurATResultError(Exception exception) {
        Log.i("erreur", exception.getMessage());
    }
}
