package xyz.eeckhout.smartcity.dataAccess;

import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpUtils {

    public static Response sendGetOkHttpRequest(String url) throws Exception {
        Log.i("URL", url);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        return client.newCall(request).execute();
    }
}
