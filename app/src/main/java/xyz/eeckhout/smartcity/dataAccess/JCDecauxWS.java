package xyz.eeckhout.smartcity.dataAccess;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import okhttp3.Response;
import xyz.eeckhout.smartcity.model.jcdecaux.JCDecauxStation;

public class JCDecauxWS {
    private static final String API_BASE_URL = "https://api.jcdecaux.com/vls/v1/";
    private static final String API_KEY = "7ec5a7ba2ddc9278fa2fe4682c02fbe08ec541c1";

    private static final Gson GSON = new Gson();

    public static ArrayList<JCDecauxStation> getAllJCDecaux() throws Exception
    {
        Response response = OkHttpUtils.sendGetOkHttpRequest(getApiBaseUrl() + "stations?contract=Namur&apiKey=" + getApiKey());
        if(response.code() == HttpURLConnection.HTTP_OK) {
            InputStreamReader stream = new InputStreamReader(response.body().byteStream());
            ArrayList<JCDecauxStation> bikes = GSON.fromJson(stream, new TypeToken<ArrayList<JCDecauxStation>>() {
            }.getType());
            stream.close();
            return bikes;
        }
        else{
            throw new Exception("Réponse du serveur incorrecte : "+response.code());
        }
    }

    private static String getApiKey() {
        return API_KEY;
    }

    private static String getApiBaseUrl() {
        return API_BASE_URL;
    }
}
