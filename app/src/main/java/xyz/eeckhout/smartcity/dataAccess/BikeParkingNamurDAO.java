package xyz.eeckhout.smartcity.dataAccess;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import xyz.eeckhout.smartcity.model.villeNamur.bikeParking.BikeParkingNamur;

public class BikeParkingNamurDAO {
    private static final String API_BASE_URL = "https://data.namur.be/api/records/1.0/search/";
    public BikeParkingNamur getAllParkingVeloVille() throws Exception {
        URL url = new URL(getApiBaseUrl() + "?dataset=namur-mobilite-stationnements-velo0&rows=30");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        BufferedReader buffer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder builder = new StringBuilder();

        String stringJSON = "";
        String line = buffer.readLine();
        while (line != null) {
            builder.append(line);
            line = buffer.readLine();
        }
        buffer.close();
        stringJSON = builder.toString();
        return jsonToParkingVeloVille(stringJSON);
    }

    public BikeParkingNamur getPakingVeloFromArez(LatLng center, float distance) throws Exception {
        StringBuilder urlBuilder = new StringBuilder(getApiBaseUrl());
        urlBuilder.append("?dataset=namur-mobilite-stationnements-velo0&rows=-1");
        urlBuilder.append("&geofilter.distance=");
        urlBuilder.append(center.latitude).append(",").append(center.longitude).append(",").append(distance);
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        BufferedReader buffer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder builder = new StringBuilder();

        String stringJSON = "";
        String line = buffer.readLine();
        while (line !=null) {
            builder.append(line);
            line = buffer.readLine();
        }
        buffer.close();
        stringJSON = builder.toString();
        return jsonToParkingVeloVille(stringJSON);
    }

    private BikeParkingNamur jsonToParkingVeloVille(String stringJSON) throws Exception
    {
        JSONObject jsonObject = new JSONObject(stringJSON);
        Gson object = new GsonBuilder().create();
        return object.fromJson(jsonObject.toString(), BikeParkingNamur.class);
    }

    private static String getApiBaseUrl() {
        return API_BASE_URL;
    }
}
