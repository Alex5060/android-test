package xyz.eeckhout.smartcity.controller;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.util.ArrayList;

import xyz.eeckhout.smartcity.dataAccess.BikeParkingNamurAT;
import xyz.eeckhout.smartcity.dataAccess.BikeRouteNamurAT;
import xyz.eeckhout.smartcity.dataAccess.CarParkingNamurAT;
import xyz.eeckhout.smartcity.dataAccess.JCDecauxAT;
import xyz.eeckhout.smartcity.model.jcdecaux.JCDecauxStation;
import xyz.eeckhout.smartcity.model.villeNamur.bikeParking.BikeParkingNamur;
import xyz.eeckhout.smartcity.model.villeNamur.bikeRoute.BikeRouteNamur;
import xyz.eeckhout.smartcity.model.villeNamur.carParking.CarParkingNamur;

public class MapViewModel extends ViewModel implements JCDecauxAT.GetJCDecauxATResult, BikeParkingNamurAT.GetBikeParkingNamurATResult, BikeRouteNamurAT.GetBikeRouteNamurATResult, CarParkingNamurAT.GetCarParkingNamurATResult {
    private MutableLiveData<CarParkingNamur> carParkingNamur;
    private MutableLiveData<BikeParkingNamur> parkingVelo;
    private MutableLiveData<BikeRouteNamur> itineraireVelo;
    private MutableLiveData<ArrayList<JCDecauxStation>> jcDecauxBikes;
    private CarParkingNamurAT carParkingNamurAT;
    private BikeRouteNamurAT bikeRouteNamurAT;
    private BikeParkingNamurAT bikeParkingNamurAT;
    private JCDecauxAT jcDecauxAT;


    public MutableLiveData<CarParkingNamur> getCarParkingNamur() {
        if(carParkingNamur == null){
            carParkingNamur = new MutableLiveData<>();
            carParkingNamurAT = new CarParkingNamurAT(this);
            carParkingNamurAT.execute();
        }
        return carParkingNamur;
    }

    public MutableLiveData<BikeParkingNamur> getBikeParking() {
        if(parkingVelo == null){
            parkingVelo = new MutableLiveData<>();
            bikeParkingNamurAT = new BikeParkingNamurAT(this);
            bikeParkingNamurAT.execute();
        }
        return parkingVelo;
    }

    public MutableLiveData<BikeRouteNamur> getItineraireVelo() {
        if(itineraireVelo == null){
            itineraireVelo = new MutableLiveData<>();
            bikeRouteNamurAT = new BikeRouteNamurAT(this);
            bikeRouteNamurAT.execute();
        }
        return itineraireVelo;
    }

    public MutableLiveData<ArrayList<JCDecauxStation>> getJcDecauxBikes() {
        if(jcDecauxBikes == null){
            jcDecauxBikes = new MutableLiveData<>();
            jcDecauxBikes.setValue(new ArrayList<JCDecauxStation>());
            jcDecauxAT = new JCDecauxAT(this);
            jcDecauxAT.execute();
        }
        return jcDecauxBikes;
    }


    @Override
    public void getStationsATResult(ArrayList<JCDecauxStation> stations) {
        this.jcDecauxBikes.getValue().clear();
        this.jcDecauxBikes.postValue(stations);
    }

    @Override
    public void getStationsATResultError(Exception exception) {
        Log.i("error", exception.getMessage());
    }

    @Override
    public void getBikeParkingNamur(BikeParkingNamur bikeParkingNamur) {
        this.parkingVelo.setValue(bikeParkingNamur);
    }

    @Override
    public void getBikeParkingNamurATResultError(Exception exception) {
        Log.i("error", exception.getMessage());
    }

    @Override
    public void getBikeRouteNamur(BikeRouteNamur bikeRouteNamur) {
        this.itineraireVelo.setValue(bikeRouteNamur);
    }

    @Override
    public void getBikeRouteAtResultError(Exception exception) {
        Log.i("error", exception.getMessage());
    }

    @Override
    public void getAllCarParkingNamur(CarParkingNamur carParkingNamur) {
        this.carParkingNamur.setValue(carParkingNamur);
    }

    @Override
    public void getCarParkingNamurATResultError(Exception exception) {
        Log.i("error", exception.getMessage());
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        jcDecauxAT.cancel(true);
        bikeParkingNamurAT.cancel(true);
        bikeRouteNamurAT.cancel(true);
        carParkingNamurAT.cancel(true);
    }
}
