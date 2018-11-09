package xyz.eeckhout.smartcity.DataAccess;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import xyz.eeckhout.smartcity.Model.ItineraireVelo.ItineraireVeloVille;

public class ItineraireVeloVilleDAO {

    public ItineraireVeloVille getAllItineraireVeloVille() throws Exception
    {
        URL url = new URL("https://data.namur.be/api/records/1.0/search/?dataset=namur-mobilite-itineraires-velo&facet=iti_code_reg&facet=iti_code_eu&facet=iti_communal&facet=iti_code_com&q=not(recordid%3D2321372ec5bc0e62f1e999d9f290c82c73e24d51)");
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

    private ItineraireVeloVille jsonToItineraireVeloVille(String stringJSON) throws Exception
    {
        ItineraireVeloVille itineraireVeloVille = new ItineraireVeloVille();
        JSONObject jsonObject = new JSONObject(stringJSON);

        Gson object = new GsonBuilder().create();
        Log.i("Samy", ""+jsonObject.length());
        itineraireVeloVille = object.fromJson(jsonObject.toString(), xyz.eeckhout.smartcity.Model.ItineraireVelo.ItineraireVeloVille.class);

        return itineraireVeloVille;
    }
}
