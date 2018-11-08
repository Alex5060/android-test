package xyz.eeckhout.smartcity.DataAccess;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import xyz.eeckhout.smartcity.Model.JCDecauxVelos;

public class JCDecauxDAO {

    public ArrayList<JCDecauxVelos> getAllJCDecaux() throws Exception
    {
        URL url = new URL("https://api.jcdecaux.com/vls/v1/stations?contract=Namur&apiKey=7ec5a7ba2ddc9278fa2fe4682c02fbe08ec541c1");
        //URL url = new URL("https://jsonplaceholder.typicode.com/users");
        //URL url = new URL("https://api.androidhive.info/volley/person_array.json");
        //URL url = new URL("https://data.namur.be/api/records/1.0/search/?dataset=namur-mobilite-parking&facet=plsy_fonction&fbclid=IwAR3yPg5BkqS0oBqJbeeqcMKNzve-5dyekBHx0L9_XEUc9jRcPvxbgOqXoUw");
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

    private ArrayList<JCDecauxVelos> jsonToExample(String stringJSON) throws Exception
    {
        ArrayList<JCDecauxVelos> velos = new ArrayList<>();
        JCDecauxVelos velo;
        JSONArray jsonArray = new JSONArray(stringJSON);
        for (int i = 0; i < jsonArray.length(); i++)
        {
            JSONObject jsonJCDecauxPlace = jsonArray.getJSONObject(i);
            Gson object = new GsonBuilder().create();
            velo = object.fromJson(jsonJCDecauxPlace.toString(), JCDecauxVelos.class);
            Log.i("Samy", "jsonToExample: " + velo.toString());
            velos.add(velo);
        }
        return velos;
    }
}
