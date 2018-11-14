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
import xyz.eeckhout.smartcity.model.villeNamur.bikeRoute.BikeRouteNamur;

public class BikeRouteNamurWS {
    private static final Gson GSON = new Gson();
    private static final String API_BASE_URL = "https://data.namur.be/api/records/1.0/search/";
    public static  BikeRouteNamur getAllBikeRouteNamur() throws Exception {
        Response response = OkHttpUtils.sendGetOkHttpRequest(getApiBaseUrl() + "?dataset=namur-mobilite-itineraires-velo&facet=iti_code_reg&facet=iti_code_eu&facet=iti_communal&facet=iti_code_com&rows=-1");
        if(response.code() == HttpURLConnection.HTTP_OK){
            InputStreamReader stream = new InputStreamReader(response.body().byteStream());
            BikeRouteNamur bikeRouteNamur = GSON.fromJson(stream, BikeRouteNamur.class);
            stream.close();
            return bikeRouteNamur;
        }
        else {
            throw new Exception("Réponse du serveur incorrecte : " + response.code());
        }
    }

    private static String getApiBaseUrl() {
        return API_BASE_URL;
    }
}
