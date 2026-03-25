package com.gamebot.helpers;
import com.gamebot.api.CurlRequest;
import com.google.gson.*;

import java.io.*;
import java.util.HashMap;

public class json_map {
    //Store ID, StoreName
    public static HashMap<String,String> jsonmap() throws IOException {
        HashMap<String,String> map = new HashMap<>();

        String request = CurlRequest.curlRequests("https://www.cheapshark.com/api/1.0/stores");
        JsonArray jsonArray = JsonParser.parseString(request).getAsJsonArray();
        for(JsonElement jsonElement : jsonArray){
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            String key = jsonObject.get("storeID").getAsString();
            String value = jsonObject.get("storeName").getAsString();
            map.put(key, value);

        }
        return map;
    }

    }

