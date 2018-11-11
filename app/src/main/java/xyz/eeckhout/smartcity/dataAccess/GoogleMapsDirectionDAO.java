package xyz.eeckhout.smartcity.dataAccess;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import xyz.eeckhout.smartcity.model.graphhopper.Graphhopper;

public class GoogleMapsDirectionDAO {
    private static final String API_BASE_URL = "https://graphhopper.com/api/1/";

    public Graphhopper getDirection(ArrayList<LatLng> latLngs, String vehicle) throws Exception {
        StringBuilder urlBuilder = new StringBuilder(getApiBaseUrl());
        urlBuilder.append("route?");
        urlBuilder.append("vehicle=").append(vehicle);
        for (LatLng latLng : latLngs) {
            urlBuilder.append("&point=").append(latLng.latitude).append(",").append(latLng.longitude);
        }
        urlBuilder.append("&debug=true&key=684d7aa6-fd79-418a-bd24-84c9dc0da13d&locale=fr&type=json");
        URL url = new URL(urlBuilder.toString());
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
        return jsonToExample(stringJSON);
    }

    private Graphhopper jsonToExample(String stringJSON) throws Exception {
        JSONObject jsonObject = new JSONObject(stringJSON);
        Gson object = new GsonBuilder().create();
        return object.fromJson(jsonObject.toString(), Graphhopper.class);
    }

    private static String getApiBaseUrl() {
        return API_BASE_URL;
    }
}
