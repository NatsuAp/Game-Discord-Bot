package comandos;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

//Juego: Hollow Knight
//Precio actual: $7.49
//Precio normal: $14.99
//Descuento: 50%
//Tienda: Steam
public class precio {
    public static String comandoPrecio(String str){
        String botAns = "";
        try{
            String request = api.CurlRequest.curlRequests("https://www.cheapshark.com/api/1.0/games?title="+ str);
            JsonArray jsonArray = JsonParser.parseString(request).getAsJsonArray();
            botAns += "Juego: ";

            JsonElement Element = jsonArray.get(0);
                JsonObject jsonObject = Element.getAsJsonObject();
                botAns += jsonObject.get("external").getAsString();
                botAns += "\n";
                botAns += "Precio actual: ";
                botAns += jsonObject.get("precio").getAsString();


        }catch(Exception e){
            e.printStackTrace();
        }
        return "";
    }
}
