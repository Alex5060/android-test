package xyz.eeckhout.smartcity.controller;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;

import xyz.eeckhout.smartcity.model.jcdecaux.JCDecauxBikes;
import xyz.eeckhout.smartcity.model.villeNamur.bikeParking.BikeParkingNamur;
import xyz.eeckhout.smartcity.model.villeNamur.bikeRoute.BikeRouteNamur;
import xyz.eeckhout.smartcity.model.villeNamur.carParking.CarParkingNamur;

public class MapViewModel extends ViewModel {
    private MutableLiveData<CarParkingNamur> carParkingNamur;
    private MutableLiveData<BikeParkingNamur> parkingVelo;
    private MutableLiveData<BikeRouteNamur> itineraireVelo;
    private MutableLiveData<ArrayList<JCDecauxBikes>> jcDecauxBikes;


    public MutableLiveData<CarParkingNamur> getCarParkingNamur() {
        if(carParkingNamur == null){
            carParkingNamur = new MutableLiveData<>();
        }
        return carParkingNamur;
    }


    public MutableLiveData<BikeParkingNamur> getParkingVelo() {
        if(parkingVelo == null){
            parkingVelo = new MutableLiveData<>();
        }
        return parkingVelo;
    }

    public MutableLiveData<BikeRouteNamur> getItineraireVelo() {
        if(itineraireVelo == null){
            itineraireVelo = new MutableLiveData<>();
        }
        return itineraireVelo;
    }

    public MutableLiveData<ArrayList<JCDecauxBikes>> getJcDecauxBikes() {
        if(jcDecauxBikes == null){
            jcDecauxBikes = new MutableLiveData<>();
        }
        return jcDecauxBikes;
    }
}
