package com.gamebot.api;

import com.gamebot.main;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.http2.Header;
import java.io.IOException;

public class CurlRequest {
    public static String curlRequests(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .header("User-Agent", "GameBot_app/1.1 (" + main.dotenv.get("CORREO") + ")")
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();


        }
        catch (Exception e) {
            response = null;
            e.printStackTrace();
        }
        return response.body().string();
    }

}
