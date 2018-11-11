package xyz.eeckhout.smartcity.dataAccess;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import xyz.eeckhout.smartcity.model.jcdecaux.JCDecauxBikes;

public class JCDecauxDAO {
    private static final String API_BASE_URL = "https://api.jcdecaux.com/vls/v1/";
    private static final String API_KEY = "7ec5a7ba2ddc9278fa2fe4682c02fbe08ec541c1";
    public ArrayList<JCDecauxBikes> getAllJCDecaux() throws Exception
    {
        URL url = new URL(getApiBaseUrl() + "stations?contract=Namur&apiKey=" + getApiKey());
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

    private ArrayList<JCDecauxBikes> jsonToExample(String stringJSON) throws Exception
    {
        ArrayList<JCDecauxBikes> bikes = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(stringJSON);
        for (int i = 0; i < jsonArray.length(); i++)
        {
            JSONObject jsonJCDecauxPlace = jsonArray.getJSONObject(i);
            Gson object = new GsonBuilder().create();
            bikes.add(object.fromJson(jsonJCDecauxPlace.toString(), JCDecauxBikes.class));
        }
        return bikes;
    }

    private static String getApiKey() {
        return API_KEY;
    }

    private static String getApiBaseUrl() {
        return API_BASE_URL;
    }
}
