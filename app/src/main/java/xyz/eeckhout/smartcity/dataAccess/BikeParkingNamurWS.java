package xyz.eeckhout.smartcity.dataAccess;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.Response;
import xyz.eeckhout.smartcity.model.villeNamur.bikeParking.BikeParkingNamur;

public class BikeParkingNamurWS {
    private static final Gson GSON = new Gson();
    private static final String API_BASE_URL = "https://data.namur.be/api/records/1.0/search/";
    public static BikeParkingNamur getAllBikeParkingNamur() throws Exception {
        Response response = OkHttpUtils.sendGetOkHttpRequest(getApiBaseUrl() + "?dataset=namur-mobilite-stationnements-velo0&rows=-1");
        if(response.code() == HttpURLConnection.HTTP_OK){
            InputStreamReader stream = new InputStreamReader(response.body().byteStream());
            BikeParkingNamur bikeParkingNamur = GSON.fromJson(stream, BikeParkingNamur.class);
            stream.close();
            return bikeParkingNamur;
        }
        else {
            throw new Exception("Réponse du serveur incorrecte : " + response.code());
        }
    }

    public static BikeParkingNamur getBikesParkingNamurFromVisibleZone(LatLng center, float distance) throws Exception {
        StringBuilder urlBuilder = new StringBuilder(getApiBaseUrl());
        urlBuilder.append("?dataset=namur-mobilite-stationnements-velo0&rows=-1");
        urlBuilder.append("&geofilter.distance=");
        urlBuilder.append(center.latitude).append(",").append(center.longitude).append(",").append(distance);
        Response response = OkHttpUtils.sendGetOkHttpRequest(urlBuilder.toString());
        if(response.code() == HttpURLConnection.HTTP_OK){
            InputStreamReader stream = new InputStreamReader(response.body().byteStream());
            BikeParkingNamur bikeParkingNamur = GSON.fromJson(stream, BikeParkingNamur.class);
            stream.close();
            return bikeParkingNamur;
        }
        else {
            throw new Exception("Réponse du serveur incorrecte : " + response.code());
        }
    }

    private static String getApiBaseUrl() {
        return API_BASE_URL;
    }
}
