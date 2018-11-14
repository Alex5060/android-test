package xyz.eeckhout.smartcity.dataAccess;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.Response;
import xyz.eeckhout.smartcity.model.jcdecaux.JCDecauxStation;
import xyz.eeckhout.smartcity.model.villeNamur.carParking.CarParkingNamur;

public class CarParkingNamurWS {
    private static final String API_BASE_URL = "https://data.namur.be/api/records/1.0/search/";
    private static final Gson GSON = new Gson();
    public static CarParkingNamur getAllCarParkingNamur() throws Exception {
        Response response = OkHttpUtils.sendGetOkHttpRequest(getApiBaseUrl() + "?dataset=namur-mobilite-parking&facet=plsy_fonction&rows=-1");
        if(response.code() == HttpURLConnection.HTTP_OK) {
            InputStreamReader stream = new InputStreamReader(response.body().byteStream());
            CarParkingNamur carParkingNamur = GSON.fromJson(stream, CarParkingNamur.class);
            stream.close();
            return carParkingNamur;
        }
        else{
            throw new Exception("Réponse du serveur incorrecte : "+response.code());
        }
    }

    private static String getApiBaseUrl() {
        return API_BASE_URL;
    }
}
