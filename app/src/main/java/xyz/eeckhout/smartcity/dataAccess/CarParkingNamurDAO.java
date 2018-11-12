package xyz.eeckhout.smartcity.dataAccess;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import xyz.eeckhout.smartcity.model.villeNamur.carParking.CarParkingNamur;

public class CarParkingNamurDAO {
    private static final String API_BASE_URL = "https://data.namur.be/api/records/1.0/search/";
    public CarParkingNamur getAllJCDecaux() throws Exception {
        URL url = new URL(getApiBaseUrl() + "?dataset=namur-mobilite-parking&facet=plsy_fonction&rows=30");
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

    public CarParkingNamur getParkingFromArea(LatLng center, float distance) throws Exception {
        StringBuilder urlBuilder = new StringBuilder(getApiBaseUrl());
        urlBuilder.append("?dataset=namur-mobilite-parking&facet=plsy_fonction&rows=30");
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
        return jsonToExample(stringJSON);
    }

    private CarParkingNamur jsonToExample(String stringJSON) throws Exception
    {
        JSONObject jsonObject = new JSONObject(stringJSON);
        Gson object = new GsonBuilder().create();
        return object.fromJson(jsonObject.toString(), CarParkingNamur.class);
    }

    private static String getApiBaseUrl() {
        return API_BASE_URL;
    }
}
