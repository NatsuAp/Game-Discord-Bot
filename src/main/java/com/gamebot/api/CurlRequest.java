package com.gamebot.api;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class CurlRequest {
    public static String curlRequests(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
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
