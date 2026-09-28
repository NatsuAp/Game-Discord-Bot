package com.gamebot.comandos;

import com.gamebot.api.CurlRequest;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.gamebot.helpers.json_map;

import java.io.IOException;
import java.util.HashMap;

//Juego: Hollow Knight
//Precio actual: $7.49
//Precio normal: $14.99
//Descuento: 50%
//Tienda: Steam
public class precio {
    public static HashMap<String, String> storeMap;
    public static String comandoPrecio(String str) throws IOException {
        String botAns = "";
        try{
            String request = CurlRequest.curlRequests("https://www.cheapshark.com/api/1.0/games?title="+ str);
            JsonArray jsonArray = JsonParser.parseString(request).getAsJsonArray();
            String juego;
            String gameId;

            //Tomar el primer sqlite.juego
            JsonElement element = jsonArray.get(0);
            //leer el elemento del arreglo como objeto
                JsonObject jsonObject = element.getAsJsonObject();
                //leer sqlite.juego
                juego = jsonObject.get("external").getAsString();
                //leer id tienda
                gameId = jsonObject.get("gameID").getAsString();
                request = CurlRequest.curlRequests("https://www.cheapshark.com/api/1.0/games?id=" + gameId);
                jsonObject = JsonParser.parseString(request).getAsJsonObject();
                JsonArray dealsArray = jsonObject.get("deals").getAsJsonArray();
                JsonObject dealsObject = dealsArray.get(0).getAsJsonObject();
                String precio =  dealsObject.get("retailPrice").getAsString();
                String precioDescuento = precio;
                String storeID = "";
                String dealID = "";
                float tempPrice;
                float min = Float.parseFloat(precio);
                boolean x = false;
                dealID = dealsArray.get(0).getAsJsonObject().get("dealID").getAsString();
                storeID = dealsArray.get(0).getAsJsonObject().get("storeID").getAsString();
                for(JsonElement deal: dealsArray){
                    tempPrice = deal.getAsJsonObject().get("price").getAsFloat();
                    if(tempPrice< min){
                        x = true;
                        min = tempPrice;
                        storeID = deal.getAsJsonObject().get("storeID").getAsString();
                        dealID = deal.getAsJsonObject().get("dealID").getAsString();
                    }
                }
                float ahorro = 0f;
                botAns += "Juego: " + juego +"\n";
                if(x){
                    precioDescuento = String.valueOf(min);
                    botAns += "Precio actual: $" +  precioDescuento + "\n";

                    ahorro = Float.parseFloat(precio) - min;

                }else{
                    botAns += "No se encontraron descuentos" + "\n";


                }
                botAns += "Precio normal: $" + precio + "\n";
                //  30 -> 100
                // 20 -> ?
                if(x) botAns += "Ahorras: $" + ahorro + "\n";
                botAns+= "Tienda: " + storeMap.get(storeID) + "\n"
                        + "https://www.cheapshark.com/redirect?dealID=" + dealID + "\n";


        }catch(Exception e){
            e.printStackTrace();
        }
        return botAns;
    }
}
