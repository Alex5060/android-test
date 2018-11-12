package xyz.eeckhout.smartcity.dataAccess;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import xyz.eeckhout.smartcity.model.villeNamur.bikeRoute.BikeRouteNamur;

public class BikeRouteNamurDAO {
    private static final String API_BASE_URL = "https://data.namur.be/api/records/1.0/search/";
    public BikeRouteNamur getAllItineraireVeloVille() throws Exception {
        URL url = new URL(getApiBaseUrl() + "?dataset=namur-mobilite-itineraires-velo&facet=iti_code_reg&facet=iti_code_eu&facet=iti_communal&facet=iti_code_com&rows=30");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setUseCaches(true);
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

        return jsonToItineraireVeloVille(stringJSON);
    }

    public BikeRouteNamur getItinerairesFromArea(LatLng center, float distance) throws Exception {
        URL url = new URL(getApiBaseUrl() + "?dataset=namur-mobilite-itineraires-velo&facet=iti_code_reg&facet=iti_code_eu&facet=iti_communal&facet=iti_code_com&rows=30");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setUseCaches(true);
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

        return jsonToItineraireVeloVille(stringJSON);
    }


    private BikeRouteNamur jsonToItineraireVeloVille(String stringJSON) throws Exception
    {
        JSONObject jsonObject = new JSONObject(stringJSON);
        Gson object = new GsonBuilder().create();
        return object.fromJson(jsonObject.toString(), BikeRouteNamur.class);
    }

    private static String getApiBaseUrl() {
        return API_BASE_URL;
    }
}
