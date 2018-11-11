package xyz.eeckhout.smartcity.dataAccess;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import xyz.eeckhout.smartcity.model.villeNamur.bikeParking.BikeParkingNamur;

public class BikeParkingNamurDAO {

    public BikeParkingNamur getAllParkingVeloVille() throws Exception
    {
        //URL url = new URL("https://api.jcdecaux.com/vls/v1/stations?contract=Namur&apiKey=7ec5a7ba2ddc9278fa2fe4682c02fbe08ec541c1");
        //URL url = new URL("https://jsonplaceholder.typicode.com/users");
        //URL url = new URL("https://api.androidhive.info/volley/person_array.json");
        URL url = new URL("https://data.namur.be/api/records/1.0/search/?dataset=namur-mobilite-stationnements-velo0");
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
        BikeParkingNamur bikeParkingNamur = new BikeParkingNamur();
        JSONObject jsonObject = new JSONObject(stringJSON);

        Gson object = new GsonBuilder().create();
        bikeParkingNamur = object.fromJson(jsonObject.toString(), BikeParkingNamur.class);

        return bikeParkingNamur;
    }
}
